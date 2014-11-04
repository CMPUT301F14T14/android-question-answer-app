package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class LocalDataStoreTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private LocalDataStore mLocalStore;
	private Question mQuestion;
	private Answer mAnswer;
	private Comment<Answer> mComment;

	public LocalDataStoreTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mLocalStore = new LocalDataStore();
		mQuestion = new Question("TITLE", "BODY", "AUTHOR", null);
		mAnswer = new Answer(mQuestion, "ANSWERBODY", "Author", null);
		mComment = new Comment<Answer>(mAnswer, "COMMENTBODY", "Boris");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPutQuestion() {
		mLocalStore.putQuestion(mQuestion);
		Question retrieved = mLocalStore.getQuestion(mQuestion.getId());
		assertEquals(mQuestion, retrieved);
	}
	
	public void testPutAnswer() {
		mLocalStore.putAnswer(mAnswer);
		Answer retrieved = mLocalStore.getAnswer(mAnswer.getId());
		assertEquals(mAnswer, retrieved);
	}
	
	public void testPutComment() {
		mLocalStore.putAComment(mComment);
		Comment<Answer> retrieved = mLocalStore.getAComment(mComment.getId());
		assertEquals(mComment, retrieved);
	}
	
}
