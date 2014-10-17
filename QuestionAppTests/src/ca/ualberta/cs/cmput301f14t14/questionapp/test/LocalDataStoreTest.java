package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class LocalDataStoreTest extends TestCase {

	private LocalDataStore mLocalStore;
	private Question mQuestion;
	private DataManager manager;
	private Answer mAnswer;
	private Comment mComment;
	
	protected void setUp() throws Exception {
		super.setUp();
		mLocalStore = new LocalDataStore();
		mQuestion = new Question("TITLE", "BODY", null);
		mAnswer = new Answer("ANSWERBODY", null);
		mComment = new Comment("COMMENTBODY", "Boris");
		manager = new DataManager();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPutQuestion() {
		mLocalStore.putQuestion(mQuestion);
		Question retrieved = manager.getQuestion(mQuestion.getId());
		assertEquals(mQuestion, retrieved);
	}
	
	public void testPutAnswer() {
		mLocalStore.putAnswer(mAnswer);
		Answer retrieved = manager.getAnswer(mAnswer.getId());
		assertEquals(mAnswer, retrieved);
	}
	
	public void testPutComment() {
		mLocalStore.putComment(mComment);
		Comment retrieved = manager.getComment(mComment.getId());
		assertEquals(mComment, retrieved);
	}
	
}
