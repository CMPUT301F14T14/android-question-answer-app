package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AbstractEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionCommentPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddAnswerTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddQuestionCommentTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddQuestionTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetAnswerTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetQuestionTask;
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
	private List<UUID> pushOnline;
	private List<UUID> upVoteOnline;
	private Context singletoncontext; //Needed for Threading instantiations
	String Username;
	
	private EventBus eventbus = EventBus.getInstance();

	
	private DataManager(Context context){
		this.clientData = new ClientData(context);
		this.localDataStore = new LocalDataStore(context);
		this.remoteDataStore = new RemoteDataStore(context);
		this.pushOnline = new ArrayList<UUID>();
		this.upVoteOnline = new ArrayList<UUID>();
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
	public Comment<Question> getQuestionComment(UUID cid) {
		Comment<Question> comment;
		if(remoteDataStore.hasAccess()){
			comment = remoteDataStore.getQComment(cid);
			recentVisit.add(cid);
			localDataStore.putQComment(comment);
		  	localDataStore.save();
		}
		else{
			comment = localDataStore.getQComment(cid);
		}
		return comment;
	}

	/**
	 * Add comment record for answer
	 * @param C
	 */
	public void addAnswerComment(Comment<Answer> C){
		Answer answer = getAnswer(C.getParent());
		answer.addComment(C.getId());
		if(remoteDataStore.hasAccess()){
			remoteDataStore.putAComment(C);
			remoteDataStore.putAnswer(answer);
			remoteDataStore.save();
		}
		else{
			pushOnline.add(C.getId());
		}
		
		localDataStore.putAComment(C);
		localDataStore.putAnswer(answer);
		localDataStore.save();
	}

	/**
	 * Get comment record from answer
	 * @param Cid
	 * @return
	 */
	public Comment<Answer> getAnswerComment(UUID Cid){
		Comment<Answer> comment;
		if(remoteDataStore.hasAccess()){
			comment = remoteDataStore.getAComment(Cid);
			recentVisit.add(Cid);
			localDataStore.putAComment(comment);
		  	localDataStore.save();
		}
		else{
			comment = localDataStore.getAComment(Cid);
		}
		return comment;
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
	
	public List<Comment<Answer>> getCommentList(Answer a){
		List<Comment<Answer>> comments = new ArrayList<Comment<Answer>>();
		for(UUID c : a.getCommentList()){
			comments.add(getAnswerComment(c));
		}
		return comments;
	}
	
	public List<Comment<Question>> getCommentList(Question q){
		List<Comment<Question>> comments = new ArrayList<Comment<Question>>();
		for(UUID c : q.getCommentList()){
			comments.add(getQuestionComment(c));
		}
		return comments;
	}
	
	public List<Answer> getAnswerList(Question q){
		List<Answer> answers = new ArrayList<Answer>();
		for(UUID a : q.getAnswerList()){
			answers.add(getAnswer(a));
		}
		return answers;
	}
	
	public void upvoteQuestion(Question q){
		if(remoteDataStore.hasAccess()){
			remoteDataStore.putQuestion(q);
		  	remoteDataStore.save();
		}
		else{
			pushOnline.add(q.getId());
			upVoteOnline.add(q.getId());
		}  
		localDataStore.putQuestion(q);
		localDataStore.save();
		
		
	}

	public IDataStore getLocalDataStore() {
		return localDataStore;
	}

	public IDataStore getRemoteDataStore() {
		return remoteDataStore;
	}

}
