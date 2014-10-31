package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public interface IDataStore {

	public void putQuestion(Question question);

	public void putAnswer(Answer answer);

	public void putComment(Comment comment);

	public boolean isQuestion(UUID id);

	public boolean isAnswer(UUID id);

	public List<Question> getQuestionList();

	public void save(List<Question> questionList);

}
