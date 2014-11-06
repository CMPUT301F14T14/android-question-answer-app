package ca.ualberta.cs.cmput301f14t14.questionapp.test.data;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class RemoteDataStoreTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private RemoteDataStore mRemoteStore;
	private Question mQuestion;
	private DataManager manager;
	private Answer mAnswer;
	private Comment<Question> qComment;
	private Comment<Answer> aComment;

	public RemoteDataStoreTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mRemoteStore = new RemoteDataStore();
		mQuestion = new Question("TITLE", "BODY", "Author", null);
		mAnswer = new Answer(mQuestion, "ANSWERBODY", "Author", null);
		qComment = new Comment<Question>(mQuestion, "COMMENTBODY", "Boris");
		aComment = new Comment<Answer>(mAnswer, "CBody", "Natasha");
		manager = DataManager.getInstance(getInstrumentation().getTargetContext().getApplicationContext());
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
		mRemoteStore.putQComment(qComment);
		Comment<Question> retrieved = (Comment<Question>) manager.getQuestionComment(qComment.getId(), qComment.getId());
		assertEquals(qComment, retrieved);
	}
	
	/* UC1 TC 1.2 */
	public void testDispRemoteQ() {
		
		mRemoteStore.putQuestion(mQuestion);
		Question retrieved = manager.getQuestion(mQuestion.getId());
		assertEquals(mQuestion, retrieved);
	}
	
}
