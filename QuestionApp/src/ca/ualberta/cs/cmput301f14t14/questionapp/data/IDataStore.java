package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

/**
 * Interface for Data Stores
 */
public interface IDataStore {

	/**
	 * Stores a question record and its children in the data store.
	 * @param question
	 */
	public void putQuestion(Question question);

	/**
	 * Get a question record from the data store. Its children will
	 * also be fetched.
	 * @param id
	 * @return Question with the given ID, or null.
	 */
	public Question getQuestion(UUID id);

	/**
	 * Stores an answer record and its children in the data store.
	 * @param answer
	 */
	public void putAnswer(Answer answer);

	/**
	 * Get an answer record from the data store. Its children will
	 * also be fetched.
	 * @param id
	 * @return Answer record, or null
	 */
	public Answer getAnswer(UUID id);

	/**
	 * Stores a question comment record in the data store.
	 * @param comment
	 */
	public void putQComment(Comment<Question> comment);

	/**
	 * Stores an answer comment record in the data store.
	 * @param comment
	 */
	public void putAComment(Comment<Answer> comment);

	/**
	 * Get a question comment record from the data store.
	 * @param id
	 * @return Question comment, or null
	 */
	public Comment<Question> getQComment(UUID id);

	/**
	 * Get an answer comment record from the data store.
	 * @param id
	 * @return Answer comment, or null
	 */
	public Comment<Answer> getAComment(UUID id);

	/**
	 * Get a list of all question objects in the data store.
	 * 
	 * The result is not ordered, and must be sorted prior to use.
	 * @return Question list
	 */
	public List<Question> getQuestionList();

	/**
	 * Persist the state of the data store.
	 */
	public void save();
	
	public boolean hasAccess();

}
