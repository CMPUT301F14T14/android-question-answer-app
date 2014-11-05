package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public interface IDataStore {

	public void putQuestion(Question question);
	public Question getQuestion(UUID id);

	public void putAnswer(Answer answer);
	public Answer getAnswer(UUID id);

	public void putQComment(Comment<Question> comment);
	public void putAComment(Comment<Answer> comment);
	public Comment<Question> getQComment(UUID id);
	public Comment<Answer> getAComment(UUID id);

	public List<Question> getQuestionList();

	public void save();

}
