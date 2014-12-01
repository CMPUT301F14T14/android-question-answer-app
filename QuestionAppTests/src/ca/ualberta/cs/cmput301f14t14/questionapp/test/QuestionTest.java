package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import java.io.IOException;
import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class QuestionTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private String title;
	private String body;
	private String author;
	private Image image;
	private DataManager manager;
	private LocalDataStore local;
	private RemoteDataStore remote;

	public QuestionTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		title = "Question Title";
		body = "Question body?";
		image = null;
		author = "boris";
		manager = DataManager.getInstance(getInstrumentation().getTargetContext().getApplicationContext());
		local =  new LocalDataStore(getInstrumentation().getTargetContext().getApplicationContext());
		remote = new RemoteDataStore(getInstrumentation().getTargetContext().getApplicationContext());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * UC4 TC4.1- Add a Question while online
	 */
	public void testAddQuestion() {
		// Test successful creation of a question
		Question q = new Question(title, body, author, image);
		assertNotNull(q.getId());
		assertEquals(title, q.getTitle());
		assertEquals(body, q.getBody());
		assertEquals(author, q.getAuthor());
		assertEquals(image, q.getImage());
		
		Question q2 = new Question();
		assertEquals(new UUID(0L, 0L), q2.getId());
		assertEquals("", q2.getTitle());
		assertEquals("", q2.getBody());
		assertEquals("", q2.getAuthor());
		assertNull(q2.getImage());
		
		assertFalse(q.equals(q2));
	}
	
	/**
	 * UC4 TC4.2- Invalid Question Title
	 */
	public void testInvalidBody() {
		// Test invalid body
		try {
			new Question(title, "", author, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		try {
			new Question(title, null, author, image);
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
			new Question("", body, author, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		try {
			new Question(null, body, author, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
	}
	
	/**
	 * UC4 TC4.4- Create Local Question, and push
	 * to remote server on network restoration
	 * @throws IOException 
	 */
	
	public void testLocalQuestionCreate() throws IOException {
		Question q = new Question(title, body, author, image);
		local.putQuestion(q);
		UUID id = q.getId();
		assertNotNull(manager.getQuestion(id, null));
		try {
			remote.putQuestion(q);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(remote.getQuestion(id));
	}
	
	/**
	 * UC9 TC9.1- Upvote a Question
	 */

	public void testUpvoteQuestion() {
		Question q = new Question(title, body, author, null);
		int oldVotes = q.getUpvotes();
		q.addUpvote();
		int newVotes = q.getUpvotes();
		assertEquals(oldVotes + 1, newVotes);
	}
	
	/**
	 * UC9 TC9.3- Multiple Upvotes on a Question
	 */
	
	public void testMultipleUpvoteQuestion() {
		Question q = new Question(title, body, author, null);
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
		Question q = new Question(title, body, author, null);
		ClientData cd = new ClientData(getActivity().getApplicationContext());
		cd.markReadLater(q.getId());
		UUID id = q.getId();
		assertNotNull(local.getQuestion(id));
	}

}
