package ca.ualberta.cs.cmput301f14t14.questionapp.test.data;

import java.io.IOException;
import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.test.mock.MockData;

public class RemoteDataStoreTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private LocalDataStore localStore;
	private RemoteDataStore remoteStore;

	public RemoteDataStoreTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		DataManager dm = DataManager.getInstance(getInstrumentation().getTargetContext().getApplicationContext());
		localStore = (LocalDataStore) dm.getLocalDataStore();
		remoteStore = (RemoteDataStore) dm.getRemoteDataStore();
		MockData.initMockData();
	}

	/**
	 * Verify that a question object can be sent to ElasticSearch
	 */
	public void testPutQuestion() {
		Question q = MockData.questions.get(0);
		localStore.putQuestion(q);
		try {
			remoteStore.putQuestion(q);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Question retrievedQuestion = null;
		try {
			retrievedQuestion = remoteStore.getQuestion(q.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(q, retrievedQuestion);
	}
	
	/**
	 * Verify that a question list can be fetched from ElasticSearch
	 */
	public void testGetQuestionList() {
		List<Question> ql = null;
		Question q = MockData.questions.get(1);
		localStore.putQuestion(q);
		try {
			remoteStore.putQuestion(q);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ql = remoteStore.getQuestionList();
		} catch (IOException e) {
			fail();
		}
		assertTrue(ql.size() != 0);
		assertTrue(ql.contains(q));
	}

	/**
	 * Verify that an answer object can be sent to ElasticSearch
	 */
	public void testPutAnswer() {
		Answer a = MockData.answers.get(0);
		try {
			localStore.putAnswer(a);
			remoteStore.putAnswer(a);
		} catch (IOException e) {
			
		}
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
		try {
			remoteStore.putQComment(c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Comment<Question> retrievedComment = remoteStore.getQComment(c.getId());
		assertEquals(c, retrievedComment);
	}
}
