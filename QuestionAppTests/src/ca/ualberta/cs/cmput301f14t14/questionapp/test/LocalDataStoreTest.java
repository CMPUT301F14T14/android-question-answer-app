package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class LocalDataStoreTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private LocalDataStore mLocalStore;
	private Question mQuestion;
	private DataManager manager;
	private Answer mAnswer;
	private Comment mComment;

	public LocalDataStoreTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mLocalStore = new LocalDataStore();
		mQuestion = new Question("TITLE", "BODY", "AUTHOR", null);
		mAnswer = new Answer(mQuestion, "ANSWERBODY", "Author", null);
		mComment = new Comment<Answer>(mAnswer, "COMMENTBODY", "Boris");
		manager = DataManager.getInstance(getInstrumentation().getTargetContext().getApplicationContext());
		
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
		Answer retrieved = manager.getAnswer(null, mAnswer.getId());
		assertEquals(mAnswer, retrieved);
	}
	
	public void testPutComment() {
		mLocalStore.putComment(mComment);
		Comment retrieved = manager.getComment(mComment.getId());
		assertEquals(mComment, retrieved);
	}
	
}
