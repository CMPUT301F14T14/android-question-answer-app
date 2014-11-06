package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class CommentTest extends ActivityInstrumentationTestCase2<MainActivity> {

	Question mQuestion;
	private Answer mAnswer;
	private Comment<Question> mComment;
	private Comment<Answer> aComment;
	private DataManager manager;
	private LocalDataStore local;
	private RemoteDataStore remote;

	public CommentTest() {
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
	 * UC6 TC6.1 - Add a Comment to both a Question and an Answer
	 */
	public void testAddComment() {
		// Add to Question
		assertFalse(mQuestion.hasComment(mComment));
		mQuestion.addComment(mComment);
		assertTrue(mQuestion.hasComment(mComment));
		
		// Add to Answer
		assertFalse(mAnswer.hasComment(aComment));
		mAnswer.addComment(aComment);
		assertTrue(mAnswer.hasComment(aComment));
	}
	
	/**
	 * UC6 TC6.2 - Invalid Comment body
	 */
	public void testInvalidBody() {
		// Test invalid body
		try {
			new Comment<Question>(mQuestion, null, "Author");
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		
		try {
			new Comment<Question>(mQuestion, "", "Author");
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
	}
	
	/**
	 * UC6 TC6.3- Create Local Comment, and push
	 * to remote server on network restoration
	 */
	
	public void testLocalAnswerCreate() {
		// adding to question
		local.putQComment(mComment);
		mQuestion.addComment(mComment);
		UUID id = mComment.getId();
		assertNotNull(mQuestion.getComment(mComment.getId()));
		remote.putQComment(mComment);
		remote.putQuestion(mQuestion);
		boolean inRemote = remote.isComment(id);
		assertTrue(inRemote);
		assertTrue(mQuestion.hasComment(mComment));
		
		// adding to answer
		Comment<Answer> secComment = new Comment<Answer>(mAnswer, "Comment has a body", "Userrrrname");
		local.putAComment(secComment);
		mAnswer.addComment(secComment);
		UUID secId = mComment.getId();
		assertNotNull(manager.getAnswerComment(mQuestion.getId(), mAnswer.getId(), secId));
		remote.putAComment(secComment);
		remote.putAnswer(mAnswer);
		boolean secInRemote = remote.isComment(secId);
		assertTrue(secInRemote);
		assertTrue(mQuestion.hasComment(mComment));
	}
	
	
}
