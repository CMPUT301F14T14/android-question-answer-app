package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class QuestionTest extends TestCase {

	
	private String title;
	private String body;
	private Image image;
	private DataManager manager;
	private LocalDataStore local;
	private RemoteDataStore remote;
	protected void setUp() throws Exception {
		super.setUp();
		title = "Question Title";
		body = "Question body?";
		image = null;
		manager = DataManager.getInstance();
		local =  new LocalDataStore();
		remote = new RemoteDataStore();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * UC4 TC4.1- Add a Question while online
	 */
	public void testAddQuestion() {
		
		
		// Test successful creation of a question
		Question q = new Question(title, body, image);
		assertEquals(title, q.getTitle());
		assertEquals(body, q.getBody());
		assertEquals(image, q.getImage());
	}
	
	/**
	 * UC4 TC4.2- Invalid Question Title
	 */
	public void testInvalidBody() {
		// Test invalid body
		try {
			new Question(title, "", image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		try {
			new Question(title, null, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
	}
	
	/**
	 * UC4 TC4.3- Invalid Question Title
	 */
	
	public void testInvalidTitle() {
		// Test invalid title
		try {
			new Question("", body, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		try {
			new Question(null, body, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
	}
	
	/**
	 * UC4 TC4.4- Create Local Question, and push
	 * to remote server on network restoration
	 */
	
	public void testLocalQuestionCreate() {
		manager.disableNetworkAccess();
		Question q = new Question(title, body, image);
		local.putQuestion(q);
		UUID id = q.getId();
		assertNotNull(manager.getQuestion(id));
		manager.enableNetworkAccess();
		remote.putQuestion(q);
		boolean inRemote = remote.isQuestion(id);
		assertTrue(inRemote);
	}
	
	/**
	 * UC9 TC9.1- Upvote a Question
	 */

	public void testUpvoteQuestion() {
		Question q = new Question(title, body, null);
		int oldVotes = q.getUpvotes();
		q.addUpvote();
		int newVotes = q.getUpvotes();
		assertEquals(oldVotes + 1, newVotes);
	}
	
	/**
	 * UC9 TC9.3- Multiple Upvotes on a Question
	 */
	
	public void testMultipleUpvoteQuestion() {
		Question q = new Question(title, body, null);
		int oldVotes = q.getUpvotes();
		// notice multiple upvotes added here
		q.addUpvote();
		q.addUpvote();
		q.addUpvote();
		int newVotes = q.getUpvotes();
		assertEquals(oldVotes + 1, newVotes);
	}
	
	/**
	 * U11 TC11.1 - Indicate read Question later
	 */
	
	public void testReadQuestionLater() {
		Question q = new Question(title, body, null);
		manager.readLater(q);
		UUID id = q.getId();
		assertTrue(local.isQuestion(id));
	}

}
