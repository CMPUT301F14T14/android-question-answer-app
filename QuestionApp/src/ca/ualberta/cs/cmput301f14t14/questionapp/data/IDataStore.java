package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.io.IOException;
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
	 * @throws IOException
	 */
	public void putQuestion(Question question) throws IOException;

	/**
	 * Get a question record from the data store. Its children will
	 * also be fetched.
	 * @param id
	 * @return Question with the given ID, or null.
	 * @throws IOException
	 */
	public Question getQuestion(UUID id) throws IOException;

	/**
	 * Stores an answer record and its children in the data store.
	 * @param answer
	 * @throws IOException 
	 */
	public void putAnswer(Answer answer) throws IOException;

	/**
	 * Get an answer record from the data store. Its children will
	 * also be fetched.
	 * @param id
	 * @return Answer record, or null
	 * @throws IOException
	 */
	public Answer getAnswer(UUID id) throws IOException;

	/**
	 * Stores a question comment record in the data store.
	 * @param comment
	 * @throws IOException
	 */
	public void putQComment(Comment<Question> comment) throws IOException;

	/**
	 * Stores an answer comment record in the data store.
	 * @param comment
	 */
	public void putAComment(Comment<Answer> comment) throws IOException;

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
	public List<Question> getQuestionList() throws IOException;

	/**
	 * Get a list of all answer children of a question.
	 *
	 * @param q Parent question
	 * @return
	 * @throws IOException
	 */
	public List<Answer> getAnswerList(Question q) throws IOException;

	/**
	 * Get a list of all comment children of a question.
	 *
	 * @param q Parent question
	 * @return
	 * @throws IOException
	 */
	public List<Comment<Question>> getCommentList(Question q) throws IOException;
	
	/**
	 * Get a list of all comment children of an answer.
	 *
	 * @param q Parent answer
	 * @return
	 * @throws IOException
	 */
	public List<Comment<Answer>> getCommentList(Answer q) throws IOException;

	/**
	 * Persist the state of the data store.
	 */
	public void save();

}
