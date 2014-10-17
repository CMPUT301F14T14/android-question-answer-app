package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.RemoteDataStore;
import junit.framework.TestCase;

public class RemoteDataStoreTest extends TestCase{

	private RemoteDataStore mRemoteStore;
	private Question mQuestion;
	private DataManager manager;
	private Answer mAnswer;
	private Comment mComment;
	
	protected void setUp() throws Exception {
		super.setUp();
		mRemoteStore = new RemoteDataStore();
		mQuestion = new Question("TITLE", "BODY", null);
		mAnswer = new Answer("ANSWERBODY", null);
		mComment = new Comment("COMMENTBODY", "Boris");
		manager = new DataManager();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testRemoteAccess() {
		assertTrue(mRemoteStore.hasAccess());
	}
	
	public void testPushQuestion() {
		mRemoteStore.pushQuestion(mQuestion);
		Question retrieved = manager.getQuestion(mQuestion.getId());
		assertEquals(mQuestion, retrieved);
	}
	
	public void testPushAnswer() {
		mRemoteStore.pushAnswer(mAnswer);
		Answer retrieved = manager.getAnswer(mAnswer.getId());
		assertEquals(mAnswer, retrieved);
	}
	
	public void testPushComment() {
		mRemoteStore.pushComment(mComment);
		Comment retrieved = manager.getComment(mComment.getId());
		assertEquals(mComment, retrieved);
	}
	
}
