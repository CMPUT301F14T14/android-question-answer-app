package ca.ualberta.cs.cmput301f14t14.questionapp.data;

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

	public boolean isComment(UUID id) {
		// TODO Auto-generated method stub
		return false;
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

	@Override
	public void putQComment(Comment<Question> comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putAComment(Comment<Answer> comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Question getQuestion(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Answer getAnswer(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment<Question> getQComment(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment<Answer> getAComment(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}
}
