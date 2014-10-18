package ca.ualberta.cs.cmput301f14t14.questionapp;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class DataManager {
	private LocalDataStore localDataStore;
	
	public void addQuestion(Question validQ) {
		// TODO Auto-generated method stub
		
	}

	public Question getQuestion(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public Answer getAnswer(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void putQuestion(Question validQ) {
		// TODO Auto-generated method stub
		
	}

	public Comment getComment(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAuthor() {
		// TODO Auto-generated method stub
		return localDataStore.getUsername();
	}

}
