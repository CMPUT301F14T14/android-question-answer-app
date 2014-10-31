package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
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
		manager = DataManager.getInstance();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testRemoteAccess() {
		assertTrue(mRemoteStore.hasAccess());
	}
	
	public void testPutQuestion() {
		mRemoteStore.putQuestion(mQuestion);
		Question retrieved = manager.getQuestion(mQuestion.getId());
		assertEquals(mQuestion, retrieved);
	}
	
	public void testPutAnswer() {
		mRemoteStore.putAnswer(mAnswer);
		Answer retrieved = manager.getAnswer(mAnswer.getId(), null);
		assertEquals(mAnswer, retrieved);
	}
	
	public void testPutComment() {
		mRemoteStore.putComment(mComment);
		Comment retrieved = manager.getComment(mComment.getId());
		assertEquals(mComment, retrieved);
	}
	
	/* UC1 TC 1.2 */
	public void testDispRemoteQ() {
		
		mRemoteStore.putQuestion(mQuestion);
		Question retrieved = manager.getQuestion(mQuestion.getId());
		assertEquals(false, true);
	}
	
}
