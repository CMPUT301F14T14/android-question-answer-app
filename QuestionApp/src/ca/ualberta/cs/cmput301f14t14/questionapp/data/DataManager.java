package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddAnswerCommentTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddAnswerTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddQuestionCommentTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddQuestionTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetAnswerCommentTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetAnswerListTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetAnswerTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetCommentListTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetQuestionCommentTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetQuestionListTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.GetQuestionTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.UploaderService;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

/**
 * DataManager is a singleton that talks to local and remote data sources
 */
public class DataManager {
	
	private static DataManager instance;

	private IDataStore localDataStore;
	private IDataStore remoteDataStore;
	private List<UUID> recentVisit;
	private List<UUID> readLater;
	private Context context;
	String Username;
	static final String favQ = "fav_Que";
	static final String favA = "fav_Ans";
	static final String recV = "rec_Vis";
	static final String redL = "red_Lat";
	static final String pusO = "pus_Onl";
	static final String upvO = "upv_Onl";

	
	private EventBus eventbus = EventBus.getInstance();


	
	private DataManager(Context context) {
		this.context = context;
	}

	/**
	 * Create data stores
	 * 
	 * This must be done after the constructor, because some data stores
	 * refer back to DataManager, and cannot do so until it is constructed.
	 */
	private void initDataStores() {
		this.localDataStore = new LocalDataStore(context);
		this.remoteDataStore = new RemoteDataStore(context);
	}

	/**
	 * Singleton getter
	 * @param context
	 * @return Instance of DataManager
	 */
	public static DataManager getInstance(Context context){
		if (instance == null){
			instance = new DataManager(context.getApplicationContext());
			instance.initDataStores();
		}
		
		return instance;
	}
	/**
	 * Starts the uploader service that copies cached creations in local to remote.
	 */
	public void startUploaderService() {
		//Hackery. No idea how to start a service only once and not get into threading problems.
		if (UploaderService.isServiceAlreadyRunning == false) {
			Intent i = new Intent(context, UploaderService.class);
			context.startService(i);
		}
	}
	
	
	//View Interface Begins
	public void addQuestion(Question validQ, Callback<Void> c) {
		AddQuestionTask aqt = new AddQuestionTask(context);
		aqt.setCallBack(c);
		aqt.execute(validQ);
	}

	/**
	 * Get a question by its UUID
	 * @param id
	 * @return
	 */
	public Question getQuestion(UUID id, Callback<Question> c) {
		GetQuestionTask task = new GetQuestionTask(context);
		if (c == null) {
			return task.blockingRun(id);
		}
		task.setCallBack(c);
		task.execute(id);
		return null;
		 
	}
	
	/**
	 * Add an answer record
	 * @param A Answer to add
	 */
	public void addAnswer(Answer A){
		AddAnswerTask aat = new AddAnswerTask(context);
		aat.execute(A);
	}

	/**
	 * Get answer record
	 * @param Aid Answer ID
	 * @return
	 */
	public Answer getAnswer(UUID Aid, Callback<Answer> c) {
		//Add this answer to the recentVisit list
		GetAnswerTask gat = new GetAnswerTask(context);
		Answer anull = null;
		if (c == null) {
			//User wants an answer within a thread, or doesn't care about blocking.
			return gat.blockingRun(Aid);
		}
		gat.setCallBack(c);
		gat.execute(Aid);
		return anull; //Hopefully eclipse will warn users this method always returns null
	}

	/**
	 * Add comment record to question
	 * @param C
	 */
	public void addQuestionComment(Comment<Question> C){
		AddQuestionCommentTask aqct = new AddQuestionCommentTask(context);
		aqct.execute(C); //May have a problem here. Look here first if crashing.
	}

	/**
	 * Get comment record from question
	 * @param cid
	 * @return
	 */
	//Wtf, when I added a Callback parameter, nothing broke... Is this 
	//method actually called anywhere in the app?
	public Comment<Question> getQuestionComment(UUID cid, Callback<Comment<? extends ICommentable>> c) {
		GetQuestionCommentTask gqct = new GetQuestionCommentTask(context);
		if (c == null){
			//User does not care about blocking
			return (Comment<Question>) gqct.blockingRun(cid);
		}
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
		AddAnswerCommentTask aact = new AddAnswerCommentTask(context);
		aact.execute(C);  //Possibly trouble here.
	}

	/**
	 * Get comment record from answer
	 * @param Cid
	 * @return
	 */
	//Another case where adding a callback to the function signature didn't break the app
	//Are we using this?
	public Comment<Answer> getAnswerComment(UUID Cid, Callback<Comment<? extends ICommentable>> c){
		GetAnswerCommentTask gact = new GetAnswerCommentTask(context);
		if (c == null) {
			//User doesn't care about threading and expects this to be blocking.
			return (Comment<Answer>) gact.blockingRun(Cid);
		}
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
	public List<Question> getQuestionList(Callback callback) {
		GetQuestionListTask task = new GetQuestionListTask(context);
		if (callback == null) {
			//User doesn't care this is blocking
			return task.blockingRun();
		}
		task.setCallBack(callback);
		task.execute();
		//User should expect this to be null, since the result should be pulled out of the callback
		return null;
	}

	/**
	 * Get a list of comments from an answer asynchronously
	 * @param a
	 * @param c
	 * @return
	 */
	public List<Comment<Answer>> getCommentList(Answer a, Callback<List<Comment<Answer>>> c){
		GetCommentListTask<Answer> gclat = new GetCommentListTask<Answer>(context);
		if (c == null) {
			//User doesn't care this is blocking
			return gclat.blockingRun(a);


		}
		gclat.setCallBack(c);
		gclat.execute(a);
		//User should expect this to be null, since the result should be pulled out of the callback
		return null;
	}
	
	/**
	 * Get a list of comments from a question asynchronously
	 * @param q
	 * @param c
	 * @return
	 */
	public List<Comment<Question>> getCommentList(Question q, Callback<List<Comment<Question>>> c){
		GetCommentListTask<Question> gclqt = new GetCommentListTask<Question>(context);
		if (c == null) {
			//User doesn't care this is blocking
			return gclqt.blockingRun(q);
		}
		gclqt.setCallBack(c);
		gclqt.execute(q);
		//User should pull result out of callback
		return null;
	}
	
	public List<Answer> getAnswerList(Question q, Callback<List<Answer>> c){
		GetAnswerListTask galt = new GetAnswerListTask(context);
		if (c == null) {
			//User does not care this is blocking
			return galt.blockingRun(q);
			
		}
		galt.setCallBack(c);
		galt.execute(q);
		//User should pull result out of callback
		return null;
	}

	public IDataStore getLocalDataStore() {
		return localDataStore;
	}

	public IDataStore getRemoteDataStore() {
		return remoteDataStore;


	}
	// Cache old location lookups for speed
	private HashMap<Location, String> oldloclookups = new HashMap<Location, String>();
	
	public String getCityFromLocation(Location l) {
		//Go to here: http://stackoverflow.com/questions/2296377/how-to-get-city-name-from-latitude-and-longitude-coordinates-in-google-maps
		
		//Cache old locations for speed
		if (oldloclookups.containsKey(l)) {
			return oldloclookups.get(l);
		}
		
		Geocoder g = new Geocoder(context, Locale.getDefault());
		if (Geocoder.isPresent()){
			//ROck and roll bitches we can find the place!
			List<Address> la = null;
			try {
				la = g.getFromLocation(l.getLatitude(), l.getLongitude(), 1);
			} catch (Exception e) {
				return "a Universe";
			}
			if (la != null && la.size() > 0){
				String city = la.get(0).getLocality();
				oldloclookups.put(l, city);
				return city;
			}
		} 
		return "a Universe";
	
		
	}

}
