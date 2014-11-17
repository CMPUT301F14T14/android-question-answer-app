package ca.ualberta.cs.cmput301f14t14.questionapp.test.data;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.test.mock.MockData;

public class RemoteDataStoreTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private RemoteDataStore remoteStore;

	public RemoteDataStoreTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		remoteStore = new RemoteDataStore(getInstrumentation().getTargetContext().getApplicationContext());
		MockData.initMockData();
	}

	/**
	 * Verify that a question object can be sent to ElasticSearch
	 */
	public void testPutQuestion() {
		Question q = MockData.questions.get(0);
		remoteStore.putQuestion(q);
		Question retrievedQuestion = remoteStore.getQuestion(q.getId());
		assertEquals(q, retrievedQuestion);
	}

	/**
	 * Verify that an answer object can be sent to ElasticSearch
	 */
	public void testPutAnswer() {
		Answer a = MockData.answers.get(0);
		remoteStore.putAnswer(a);
		Answer retrievedAnswer = remoteStore.getAnswer(a.getId());
		assertEquals(a, retrievedAnswer);
	}

	/**
	 * Verify that an answer comment object can be sent to ElasticSearch
	 */
	public void testPutAnswerComment() {
		Comment<Answer> c = MockData.acomments.get(0);
		remoteStore.putAComment(c);
		Comment<Answer> retrievedComment = remoteStore.getAComment(c.getId());
		assertEquals(c, retrievedComment);
	}

	/**
	 * Verify that a question comment object can be sent to ElasticSearch
	 */
	public void testPutQuestionComment() {
		Comment<Question> c = MockData.qcomments.get(0);
		remoteStore.putQComment(c);
		Comment<Question> retrievedComment = remoteStore.getQComment(c.getId());
		assertEquals(c, retrievedComment);
	}
}
