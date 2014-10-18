package ca.ualberta.cs.cmput301f14t14.questionapp;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public interface IDataStore {

	public void putQuestion(Question question);

	public void putAnswer(Answer answer);

	public void putComment(Comment comment);

	public boolean isQuestion(Integer id);

	public boolean isAnswer(Integer id);

}
