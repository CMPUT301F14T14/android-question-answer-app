package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class RemoteDataStore implements IDataStore {

	public boolean hasAccess() {
		// TODO Auto-generated method stub
		return false;
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

	public boolean isQuestion(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAnswer(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isComment(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> getQuestionList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(List<Question> questionList) {
		// TODO Auto-generated method stub
		
	}
}
