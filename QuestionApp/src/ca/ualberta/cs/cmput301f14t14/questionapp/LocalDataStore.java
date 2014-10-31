package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class LocalDataStore implements IDataStore {
	private String username;
	private String textSave = "localSave";
	private List<Question> questionList;
	
	public void setUsername(String accountName){
		this.username = accountName;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public List<Question> getQuestionList(){
		return questionList;
	}
	
	public void save(List<Question> questionList){
		this.questionList = questionList;
	}

	public void putQuestion(Question mQuestion) {
		// TODO Auto-generated method stub
		
	}

	public void putAnswer(Answer mAnswer) {
		// TODO Auto-generated method stub
		
	}

	public void putComment(Comment mComment) {
		// TODO Auto-generated method stub
		
	}

	public boolean isFavorite(UUID uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isQuestion(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAnswer(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		// Empty the data store
	}

	public String getAccountUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
