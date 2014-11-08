package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import android.content.Context;

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
	private List<Question> questionList;
	private List<UUID> favouriteQuestions;
	private List<UUID> favouriteAnswers;
	private List<UUID> recentVisit;
	private List<UUID> readLater;
	String Username;

	
	private DataManager(Context context){
		this.clientData = new ClientData(context);
		this.localDataStore = new LocalDataStore(context);
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
	
	//View Interface Begins
	public void addQuestion(Question validQ) {
		questionList = localDataStore.getQuestionList();
		questionList.add(validQ);
		localDataStore.putQuestion(validQ);
		localDataStore.save();
		
	}

	/**
	 * Get a question by its UUID
	 * @param id
	 * @return
	 */
	public Question getQuestion(UUID id) {
		questionList = localDataStore.getQuestionList();
		Iterator<Question> list = questionList.iterator();
		while(list.hasNext()){
			Question question = list.next();
			UUID qid = question.getId();
			if(id.equals(qid)){
				return question;
			}
		}
		return null;
	}
	
	/**
	 * Add an answer record
	 * @param Qid Parent question ID
	 * @param A Answer to add
	 */
	public void addAnswer(UUID Qid, Answer A){
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Integer position = questionList.indexOf(question);
		question.addAnswer(A);
		localDataStore.putAnswer(A);
		questionList.set(position, question);
		localDataStore.save();
	}

	/**
	 * Get answer record
	 * @param Qid Parent question ID
	 * @param Aid Answer ID
	 * @return
	 */
	public Answer getAnswer(UUID Qid, UUID Aid) {
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Answer answer = question.getAnswer(Aid);
		return answer;
	}

	/**
	 * Add comment record to question
	 * @param Qid Parent question
	 * @param C
	 */
	public void addQuestionComment(UUID Qid, Comment<Question> C){
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Integer position = questionList.indexOf(question);
		question.addComment(C);
		questionList.set(position, question);
		localDataStore.putQComment(C);
		localDataStore.save();
	}

	/**
	 * Get comment record from question
	 * @param Qid Parent question
	 * @param cid
	 * @return
	 */
	public Comment<Question> getQuestionComment(UUID Qid, UUID cid) {
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Comment<Question> comment = question.getComment(cid);
		return comment;
	}

	/**
	 * Add comment record for answer
	 * @param Qid Parent question
	 * @param Aid Parent answer
	 * @param C
	 */
	public void addAnswerComment(UUID Qid, UUID Aid, Comment<Answer> C){
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Integer position = questionList.indexOf(question);
		Answer answer = question.getAnswer(Aid);
		answer.addComment(C);
		question.setAnswer(Aid,answer);
		questionList.set(position, question);
		localDataStore.putAComment(C);
		localDataStore.save();
	}

	/**
	 * Get comment record from answer
	 * @param Qid Parent question
	 * @param Aid Parent answer
	 * @param Cid
	 * @return
	 */
	public Comment<Answer> getAnswerComment(UUID Qid, UUID Aid, UUID Cid){
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Answer answer = question.getAnswer(Aid);
		Comment<Answer> comment = answer.getComment(Cid);
		return comment;
	}

	/**
	 * Get a list of all existing questions.
	 * 
	 * This list is not returned with any particular order.
	 * @return
	 */
	public List<Question> load(){
		questionList = localDataStore.getQuestionList();
		return questionList;
	}

	/**
	 * Mark a question as a favorite
	 * @param questionId
	 */
	public void favoriteQuestion(UUID questionId) {
		favouriteQuestions = clientData.getFavoriteQuestions();
		favouriteQuestions.add(questionId);
		//localdatamanager.save(favouriteQuestions);
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
	
	public void updateQuestion(Question upQuestion){
		questionList = localDataStore.getQuestionList();
		Iterator<Question> list = questionList.iterator();
		while(list.hasNext()){
			Question question = list.next();
			UUID qid = question.getId();
			if(upQuestion.getId().equals(qid)){
				questionList.remove(question);
				questionList.add(upQuestion);
				localDataStore.save();
				return;
			}
		}
	}
	
	public Question getReadLaterQuestion(UUID qId){
		if(readLater.contains(qId)){
			
			return getQuestion(qId);
		}
		else{
			throw new NullPointerException("id is not in the list");
		}
	}

}
