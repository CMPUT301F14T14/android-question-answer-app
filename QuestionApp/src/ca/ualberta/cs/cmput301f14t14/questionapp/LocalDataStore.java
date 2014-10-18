package ca.ualberta.cs.cmput301f14t14.questionapp;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class LocalDataStore implements IDataStore {
	private String username;
	
	public void setUsername(String accountName){
		this.username = accountName;
	}
	public String getUsername(){
		return this.username;
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

	public boolean isFavorite(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isQuestion(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAnswer(Integer id) {
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
