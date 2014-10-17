package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class AnswerTest extends TestCase {

	Question mQuestion;
	private Answer mAnswer;
	private DataManager manager;
	private LocalDataStore local;
	private RemoteDataStore remote;

	protected void setUp() throws Exception {
		super.setUp();
		mQuestion = new Question("Title", "Body", null);
		mAnswer = new Answer("Answer body.", null);
		manager = new DataManager();
		local =  new LocalDataStore();
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
			new Answer(null, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		
		try {
			new Answer("", image);
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
		manager.disableNetworkAccess();
		local.putAnswer(mAnswer);
		mQuestion.addAnswer(mAnswer);
		Integer id = mAnswer.getId();
		assertNotNull(manager.getAnswer(id));
		manager.enableNetworkAccess();
		remote.putAnswer(mAnswer);
		remote.putQuestion(mQuestion);
		boolean inRemote = remote.isAnswer(id);
		assertTrue(inRemote);
		assertTrue(mQuestion.hasAnswer(mAnswer));
	}
}
