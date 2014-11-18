package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.AddQuestionTask;
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
	private Context context; //Needed for Threading instantiations
	String Username;

	
	private DataManager(Context context) {
		this.pushOnline = new ArrayList<UUID>();
		this.upVoteOnline = new ArrayList<UUID>();
		this.context = context;
	}

	/**
	 * Create data stores
	 * 
	 * This must be done after the constructor, because some data stores
	 * refer back to DataManager, and cannot do so until it is constructed.
	 */
	private void initDataStores() {
		this.clientData = new ClientData(context);
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
	
	//View Interface Begins
	public void addQuestion(Question validQ) {
		if(remoteDataStore.hasAccess()){
			
			AddQuestionTask aqt = new AddQuestionTask(this.context);
			aqt.execute(validQ);
			
		}
		else{
			pushOnline.add(validQ.getId());
		}  
		localDataStore.putQuestion(validQ);
		localDataStore.save();
		
	}

	/**
	 * Get a question by its UUID
	 * @param id
	 * @return
	 */
	public Question getQuestion(UUID id) {
		Question q;
		if(remoteDataStore.hasAccess()){
			q = remoteDataStore.getQuestion(id);
			recentVisit.add(id);
			localDataStore.putQuestion(q);
		  	localDataStore.save();
		}
		else{
			q = localDataStore.getQuestion(id);
		}
		return q;
		 
	}
	
	/**
	 * Add an answer record
	 * @param A Answer to add
	 */
	public void addAnswer(Answer A){
		Question question = getQuestion(A.getParent());
		question.addAnswer(A.getId());
		if(remoteDataStore.hasAccess()){
			remoteDataStore.putAnswer(A);
			remoteDataStore.putQuestion(question);
			remoteDataStore.save();
		}
		else{
			pushOnline.add(A.getId());
		}
		localDataStore.putAnswer(A);
		localDataStore.putQuestion(question);
		localDataStore.save();
	}

	/**
	 * Get answer record
	 * @param Aid Answer ID
	 * @return
	 */
	public Answer getAnswer(UUID Aid) {
		Answer answer;
		if(remoteDataStore.hasAccess()){
			answer = remoteDataStore.getAnswer(Aid);
			recentVisit.add(Aid);
			localDataStore.putAnswer(answer);
		  	localDataStore.save();
		}
		else{
			answer = localDataStore.getAnswer(Aid);
		}
		return answer;
	}

	/**
	 * Add comment record to question
	 * @param C
	 */
	public void addQuestionComment(Comment<Question> C){
		Question question = getQuestion(C.getParent());
		question.addComment(C.getId());
		if(remoteDataStore.hasAccess()){
			remoteDataStore.putQComment(C);
			remoteDataStore.putQuestion(question);
			remoteDataStore.save();
		}
		else{
			pushOnline.add(C.getId());
		}
		
		localDataStore.putQComment(C);
		localDataStore.putQuestion(question);
		localDataStore.save();
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
		List<Question> questionList;
		if(remoteDataStore.hasAccess()){
			questionList = remoteDataStore.getQuestionList();
		}
		else{
			questionList = localDataStore.getQuestionList();	
		}
		return questionList;
	}

	/**
	 * Mark a question as a favorite
	 * @param questionId
	 */
	public void favoriteQuestion(UUID questionId) {
		favouriteQuestions = clientData.getFavoriteQuestions();
		favouriteQuestions.add(questionId);
		clientData.saveFavoriteQuestions(favouriteQuestions);
	
	}
	
	public List<UUID> getFavoritedQuestions() {
		return clientData.getFavoriteQuestions();
	}
	
	public void unfavoriteQuestion(UUID questionId) {
		favouriteQuestions = clientData.getFavoriteQuestions();
		favouriteQuestions.remove(questionId);
		clientData.saveFavoriteQuestions(favouriteQuestions);
	}

	/**
	 * Mark an answer as a favorite
	 * @param Aid
	 */
	public void favoriteAnswer(UUID Aid) {
		favouriteAnswers = clientData.getFavoriteAnswers();
		favouriteAnswers.add(Aid);
		//localdatamanager.save(favouriteAnswers)
	}
	
	public void readQuestionLater(UUID Qid) {
		readLater = clientData.getReadLater();
		readLater.add(Qid);
		//localdatamanager.save(readLater);
	}

	public String getUsername() {
		return clientData.getUsername();
	}
	
	public void setUsername(String username) {
		clientData.setUsername(username);
	}


	public void readAnswerLater(Answer mAnswer) {
		// TODO Auto-generated method stub
	}
	
	public void clearClientData() {
		clientData.clear();
	}
	
	public Question getReadLaterQuestion(UUID qId){
		if(readLater.contains(qId)){
			
			return getQuestion(qId);
		}
		else{
			throw new NullPointerException("id is not in the list");
		}
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
