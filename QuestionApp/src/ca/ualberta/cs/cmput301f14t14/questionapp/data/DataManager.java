package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AbstractEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerCommentPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionCommentPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddAnswerCommentTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddAnswerTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddQuestionCommentTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddQuestionTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetAnswerCommentTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetAnswerListTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetAnswerTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetCommentListAnsTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetCommentListQuesTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetQuestionCommentTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetQuestionTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.UpvoteQuestionTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

/**
 * DataManager is a singleton that talks to local and remote data sources
 */
public class DataManager {

	private static DataManager instance;

	private ClientData clientData;
	private IDataStore localDataStore;
	private IDataStore remoteDataStore;
	private List<UUID> favouriteQuestions;
	private List<UUID> favouriteAnswers;
	private List<UUID> recentVisit;
	private List<UUID> readLater;
	//private List<UUID> pushOnline;
	//private List<UUID> upVoteOnline;
	private Context singletoncontext; //Needed for Threading instantiations
	String Username;
	
	private EventBus eventbus = EventBus.getInstance();

	
	private DataManager(Context context){
		this.clientData = new ClientData(context);
		this.localDataStore = new LocalDataStore(context);
		this.remoteDataStore = new RemoteDataStore(context);
		//Deprecated. Use the eventbus instead for events that need to happen
		//upon future internet access
		//this.pushOnline = new ArrayList<UUID>();
		//this.upVoteOnline = new ArrayList<UUID>();
		this.singletoncontext = context;
	}

	/**
	 * Singleton getter
	 * @param context
	 * @return Instance of DataManager
	 */
	public static DataManager getInstance(Context context){
		if (instance == null){
			instance = new DataManager(context.getApplicationContext());
		}
		
		return instance;
	}
	
	private void completeQueuedEvents() {
		//The singleton eventbus contains events that attempted to 
		//be posted to the internet. If posting failed, an event was created
		//on the eventbus. These queued events should regularly "tried again"
		//so that we are as frequently as possible trying to update the internet
		//with our new local information.
		//I believe this is the magic that is currently missing to make the DataManager
		//transparently update the local and remote stores.
		
		//For each event in the event bus, try and do it again.
		for (AbstractEvent e: eventbus.getEventQueue()){				
			/* Remove the current event from the eventbus. If "trying again" fails,
			 * it will happen in a separate thread, and it will again be added to the bus
			 */
			eventbus.removeEvent(e);
			
			if (e instanceof QuestionPushDelayedEvent) {
				//try pushing the question again
				addQuestion(((QuestionPushDelayedEvent) e).q);
			}
			if (e instanceof AnswerPushDelayedEvent) {
				addAnswer(((AnswerPushDelayedEvent) e).a);
			}
			if (e instanceof QuestionCommentPushDelayedEvent) {
				addQuestionComment(((QuestionCommentPushDelayedEvent) e).qc);
			}
			if (e instanceof AnswerCommentPushDelayedEvent) {
				addAnswerComment(((AnswerCommentPushDelayedEvent)e).ca);
			}
		}
	}
	
	//View Interface Begins
	public void addQuestion(Question validQ) {
		AddQuestionTask aqt = new AddQuestionTask(this.singletoncontext);
		aqt.execute(validQ);
	}

	/**
	 * Get a question by its UUID
	 * @param id
	 * @return
	 */
	public Question getQuestion(UUID id, Callback c) {
		//Need to add the question we got into the recentVisit list
		GetQuestionTask gqt = new GetQuestionTask(singletoncontext);
		if (c == null) {
			//User wants a question from within a thread, or doesn't care about threading
			gqt.setCallBack(null);
			try { return gqt.execute(id).get();}
				catch(Exception e){e.printStackTrace();}
		}
		gqt.setCallBack(new Callback() {
			@Override
			public void run(Object o) {
				Question q = (Question) o;
				recentVisit.add(q.getId());
			}
		});
		gqt.execute(id);
		//Now need to call the gqt with the callback the user actually wanted.
		gqt.setCallBack(c);
		gqt.execute(id);
		//Each caller of this method will have a callback that can grab the question.
		//the activities will do stuff so that this method call doesn't block
		//This method should not return anything. The callback should fetch it.
		return null;
		 
	}
	
	/**
	 * Add an answer record
	 * @param A Answer to add
	 */
	public void addAnswer(Answer A){
		AddAnswerTask aat = new AddAnswerTask(singletoncontext);
		aat.execute(A);
	}

	/**
	 * Get answer record
	 * @param Aid Answer ID
	 * @return
	 */
	public Answer getAnswer(UUID Aid, Callback c) {
		//Add this answer to the recentVisit list
		GetAnswerTask gat = new GetAnswerTask(singletoncontext);
		if (c == null) {
			//User wants an answer within a thread, or doesn't care about blocking.
			gat.setCallBack(null);
			try {
				return gat.execute(Aid).get();
			} catch (Exception e) {e.printStackTrace();}
		}
		gat.setCallBack(new Callback() {
			@Override
			public void run(Object o) {
				Answer a = (Answer)o;
				recentVisit.add(a.getId());
			}
		});
		gat.execute(Aid);
		//Now actually use the callback that the caller wanted
		gat.setCallBack(c);
		gat.execute(Aid);
		return null; //Hopefully eclipse will warn users this method always returns null
	}

	/**
	 * Add comment record to question
	 * @param C
	 */
	public void addQuestionComment(Comment<Question> C){
		AddQuestionCommentTask aqct = new AddQuestionCommentTask(singletoncontext);
		aqct.execute(C); //May have a problem here. Look here first if crashing.
	}

	/**
	 * Get comment record from question
	 * @param cid
	 * @return
	 */
	//Wtf, when I added a Callback parameter, nothing broke... Is this 
	//method actually called anywhere in the app?
	public Comment<Question> getQuestionComment(UUID cid, Callback c) {
		GetQuestionCommentTask gqct = new GetQuestionCommentTask(singletoncontext);
		if (c == null){
			//User does not care about blocking
			gqct.setCallBack(null);
			Comment<Question> cq = null;
			try {cq = gqct.execute(cid).get();} catch(Exception e){e.printStackTrace();}
			return cq;
		}
		//User cares about threading
		//Add this questionComment to the recentVisit list
		gqct.setCallBack(new Callback() {
			@Override
			public void run(Object o) {
				Comment<Question> cq = (Comment<Question>)o; //Yep, this is evil
				readLater.add(cq.getId());
				
			}
		});
		gqct.execute(cid);
		//Now run with the callback the user wanted
		gqct.setCallBack(c);
		gqct.execute(cid);
		//If the user is using threading, they will care to extract their result from the callback
		return null;
		
	}

	/**
	 * Add comment record for answer
	 * @param C
	 */
	public void addAnswerComment(Comment<Answer> C){
		AddAnswerCommentTask aact = new AddAnswerCommentTask(singletoncontext);
		aact.execute(C);  //Possibly trouble here.
	}

	/**
	 * Get comment record from answer
	 * @param Cid
	 * @return
	 */
	//Another case where adding a callback to the function signature didn't break the app
	//Are we using this?
	public Comment<Answer> getAnswerComment(UUID Cid, Callback c){
		GetAnswerCommentTask gact = new GetAnswerCommentTask(singletoncontext);
		if (c == null) {
			//User doesn't care about threading and expects this to be blocking.
			gact.setCallBack(null);
			Comment<Answer> rca = null;
			try{ gact.execute(Cid).get(); } 
				catch (Exception e) { e.printStackTrace();}
			return rca;
		}
		//Need to add this to the recentVisit list.
		gact.setCallBack(new Callback() {
			@Override
			public void run(Object o) {
				Comment<Answer> ca = (Comment<Answer>)o; //Yep, more evil casting
				recentVisit.add(ca.getId());
				
			}
		});
		gact.execute(Cid);
		//Now run with the callback the user actually wanted
		gact.setCallBack(c);
		gact.execute(Cid);
		//The user, by not setting a null callback, should know to fetch the result 
		//out of the callback, and should not be surprised at an NPE.
		return null;
	}

	/**
	 * Get a list of all existing questions.
	 * 
	 * This list is not returned with any particular order.
	 * @return
	 */
	public List<Question> load(){
		
		//TO DELETE AFTER 
		//getQuestionList is done.
		List<Question> questionList;
		if(remoteDataStore.hasAccess()){
			questionList = remoteDataStore.getQuestionList();
		}
		else{
			questionList = localDataStore.getQuestionList();	
		}
		return questionList;
	}
	//Changing function signature didn't break things. Are we using this?
	public List<Comment<Answer>> getCommentList(Answer a, Callback c){
		GetCommentListAnsTask gclat = new GetCommentListAnsTask(singletoncontext);
		if (c == null) {
			//User doesn't care this is blocking
			gclat.setCallBack(null);
			List<Comment<Answer>> lca = null;
			try{
			lca = gclat.execute(a).get();
			} catch (Exception e){ e.printStackTrace();}
			return lca;
		}
		gclat.setCallBack(c);
		gclat.execute(a);
		//User should expect this to be null, since the result should be pulled out of the callback
		return null;
	}
	
	//Function signature change didn't break things. Are we using this?
	public List<Comment<Question>> getCommentList(Question q, Callback c){
		GetCommentListQuesTask gclqt = new GetCommentListQuesTask(singletoncontext);
		if (c == null) {
			//User doesn't care this is blocking
			gclqt.setCallBack(null);
			List<Comment<Question>> lcq = null;
			try { lcq = gclqt.execute(q).get(); }
				catch (Exception e){ e.printStackTrace();}
			return lcq;
		}
		gclqt.setCallBack(c);
		gclqt.execute(q);
		//User should pull result out of callback
		return null;
	}
	
	public List<Answer> getAnswerList(Question q, Callback c){
		GetAnswerListTask galt = new GetAnswerListTask(singletoncontext);
		if (c == null) {
			//User does not care this is blocking
			galt.setCallBack(null);
			List<Answer> la = null;
			try { la = galt.execute(q).get(); }
				catch (Exception e) {e.printStackTrace(); }
			return la;
		}
		galt.setCallBack(c);
		galt.execute(q);
		//User should pull result out of callback
		return null;
	}
	
	public void upvoteQuestion(Question q){
		UpvoteQuestionTask uqt = new UpvoteQuestionTask(singletoncontext);
		uqt.execute(q);
		
	}

	public IDataStore getLocalDataStore() {
		return localDataStore;
	}

	public IDataStore getRemoteDataStore() {
		return remoteDataStore;
	}

}
