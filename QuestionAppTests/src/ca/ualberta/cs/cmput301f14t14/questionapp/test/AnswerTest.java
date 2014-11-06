package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class AnswerTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Question mQuestion;
	private Answer mAnswer;
	private DataManager manager;
	private LocalDataStore local;
	private RemoteDataStore remote;

	public AnswerTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mQuestion = new Question("Title", "Body", "Author", null);
		mAnswer = new Answer(mQuestion, "Answer body.", "Author", null);
		manager = DataManager.getInstance(getInstrumentation().getTargetContext().getApplicationContext());
		local =  new LocalDataStore(getInstrumentation().getTargetContext().getApplicationContext());
		remote = new RemoteDataStore();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * UC5 TC5.1 - Add an Answer normally
	 */
	public void testAddAnswer() {
		
		assertFalse(mQuestion.hasAnswer(mAnswer));
		mQuestion.addAnswer(mAnswer);
		assertTrue(mQuestion.hasAnswer(mAnswer));
	}
	
	/**
	 * UC5 TC5.2 - Invalid Answer body
	 */
	public void testInvalidBody() {
		// Test invalid body
		Image image = new Image(null,null);
		try {
			new Answer(mQuestion, null, "Author", image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		
		try {
			new Answer(mQuestion, "", "Author", image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
	}
	
	/**
	 * UC5 TC5.3- Create Local Answer, and push
	 * to remote server on network restoration
	 */
	
	public void testLocalAnswerCreate() {
		local.putAnswer(mAnswer);
		mQuestion.addAnswer(mAnswer);
		UUID id = mAnswer.getId();
		assertNotNull(manager.getAnswer(id, mQuestion.getId()));
		
		remote.putAnswer(mAnswer);
		remote.putQuestion(mQuestion);
		assertNotNull(remote.getAnswer(id));
		assertTrue(mQuestion.hasAnswer(mAnswer));
	}
	
	
	/**
	 * UC9 TC9.2- Upvote an Answer
	 */

	public void testUpvoteAnswer() {
		int oldVotes = mAnswer.getUpvotes();
		mAnswer.addUpvote();
		int newVotes = mAnswer.getUpvotes();
		assertEquals(oldVotes + 1, newVotes);
	}
	
	/**
	 * UC9 TC9.4- Multiple Upvotes on an Answer
	 */
	
	public void testMultipleUpvoteQuestion() {
		int oldVotes = mAnswer.getUpvotes();
		// notice multiple upvotes
		mAnswer.addUpvote();
		mAnswer.addUpvote();
		mAnswer.addUpvote();
		int newVotes = mAnswer.getUpvotes();
		assertEquals(oldVotes + 1, newVotes);
	}
	
	/**
	 * U11 TC11.2 - Indicate read Answer later
	 */
	
	public void testReadQuestionLater() {
		manager.readLater(mAnswer);
		UUID id = mAnswer.getId();
		assertNotNull(local.getAnswer(id));
	}
}
