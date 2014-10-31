package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Model;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class DataManager {

	private static DataManager instance;
	private IDataStore localDataStore;
	private IDataStore remoteDataStore;
	private List<Question> questionList;
	private List<UUID> favouriteQuestions;
	private List<UUID> favouriteAnswers;
	private List<UUID> recentVisit;
	private List<UUID> readLater;
	String Username;

	
	private DataManager(){
		
		//localDataStore = new localDataStore();
	}
	
	public static DataManager getInstance(){
		if (instance == null){
			instance = new DataManager();
		}
		return instance;
	}
	
	//View Interface Begins
	public void addQuestion(Question validQ) {
		questionList = localDataStore.getQuestionList();
		questionList.add(validQ);
		localDataStore.save(questionList);
		
	}

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

	public void addAnswer(UUID Qid, Answer A){
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Integer position = questionList.indexOf(question);
		question.addAnswer(A);
		questionList.set(position, question);
		localDataStore.save(questionList);
	}
	
	public Answer getAnswer(UUID Qid, UUID Aid) {
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Answer answer = question.getAnswer(Aid);
		return answer;
	}
	
	public void addQuestionComment(UUID Qid, Comment C){
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Integer position = questionList.indexOf(question);
		question.addComment(C);
		questionList.set(position, question);
		localDataStore.save(questionList);
	}

	public Comment getQuestionComment(UUID Qid, UUID cid) {
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Comment comment = question.getComment(cid);
		return comment;
	}
	
	public void addAnswerComment(UUID Qid, UUID Aid, Comment C){
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Integer position = questionList.indexOf(question);
		Answer answer = question.getAnswer(Aid);
		answer.addComment(C);
		question.setAnswer(Aid,answer);
		questionList.set(position, question);
		localDataStore.save(questionList);
	}
	
	public Comment getAnswerComment(UUID Qid, UUID Aid, UUID Cid){
		questionList = localDataStore.getQuestionList();
		Question question = getQuestion(Qid);
		Answer answer = question.getAnswer(Aid);
		Comment comment = answer.getComment(Cid);
		return comment;
	}
	
	public List<Question> load(){
		questionList = localDataStore.getQuestionList();
		return questionList;
	}

	public void favoriteQuestion(UUID questionId) {
		favouriteQuestions.add(questionId);
		//localdatamanager.save(favouriteQuestions);
	}

	public void favoriteAnswer(UUID Aid) {
		favouriteAnswers.add(Aid);
		//localdatamanager.save(favouriteAnswers)
	}
	
	public void readQuestionLater(UUID Qid) {
		readLater.add(Qid);
		//localdatamanager.save(readLater);
	}

	public String getUsername() {
		return localDataStore.getUsername();
	}

	//End View Interface
	
	public void disableNetworkAccess() {
		// TODO Auto-generated method stub
		
	}

	public void enableNetworkAccess() {
		// TODO Auto-generated method stub
		
	}
	
	public int getItemCount() {
		return 0;}
	
	public List<Model> getItems() { return null;}



	public void readLater(Question q) {
		// TODO Auto-generated method stub
		
	}

	public void readLater(Answer mAnswer) {
		// TODO Auto-generated method stub
	}

	public String getAuthor() {
		// TODO Auto-generated method stub
		return localDataStore.getUsername();
	}

	public Comment getComment(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
