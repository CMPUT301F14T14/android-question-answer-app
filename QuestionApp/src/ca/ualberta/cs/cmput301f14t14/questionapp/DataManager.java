package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Model;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class DataManager {
	private LocalDataStore localDataStore;
	private List<Question> questionList;
	private List<Integer> favouriteQuestions;
	private List<Integer> favouriteAnswers;
	private List<Integer> recentVisit;
	private List<Integer> readLater;
	String Username;
	
	public DataManager getInstance(){
		return this;
	}
	
	//View Interface Begins
	public void addQuestion(Question validQ) {
		//questionList = localDataStore.getQuestionList();
		/*Implement Method*/
		//localDataStore.save(questionList);
		
	}

	public Question getQuestion(Integer id) {
		//questionList = localDataStore.getQuestionList();
		/*Implement Method*/
		return null;
	}

	public void addAnswer(Integer Qid, Answer A){
		//questionList = localDataStore.getQuestionList();
		/*Implement Method*/
		//localDataStore.save(questionList);
	}
	
	public Answer getAnswer(Integer Qid, Integer Aid) {
		//questionList = localDataStore.getQuestionList();
				/*Implement Method*/
		return null;
	}
	
	public void addQuestionComment(Integer Qid, Comment C){
		//questionList = localDataStore.getQuestionList();
		/*Implement Method*/
		//localDataStore.save(questionList);
	}

	public Comment getQuestionComment(Integer Qid, Integer cid) {
		//questionList = localDataStore.getQuestionList();
				/*Implement Method*/
		return null;
	}
	
	public void addAnswerComment(Integer Qid, Integer Aid, Comment C){
		//questionList = localDataStore.getQuestionList();
		/*Implement Method*/
		//localDataStore.save(questionList);
	}
	
	public Comment getAnswerComment(Integer Qid, Integer Aid, Integer Cid){
		//questionList = localDataStore.getQuestionList();
				/*Implement Method*/
		return null;
	}
	
	public List<Question> load(){
		//questionList = localDataStore.getQuestionList();
		return questionList;
	}
	

	public void favoriteQuestion(Integer Qid) {
		// TODO Auto-generated method stub
		
	}

	public void favoriteAnswer(Integer Aid) {
		// TODO Auto-generated method stub
		
	}
	
	public void readQuestionLater(Integer Qid) {
		// TODO Auto-generated method stub
		
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

}
