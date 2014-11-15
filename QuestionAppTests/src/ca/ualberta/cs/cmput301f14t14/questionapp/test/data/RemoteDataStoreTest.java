package ca.ualberta.cs.cmput301f14t14.questionapp.test.data;

import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.test.mock.MockData;

public class RemoteDataStoreTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private RemoteDataStore remoteStore;
	private List<Question> mockData;

	public RemoteDataStoreTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		remoteStore = new RemoteDataStore();
		mockData = MockData.getMockData();
	}

	public void testPutQuestion() {
		Question q = mockData.get(0);
		remoteStore.putQuestion(q);
		Question retrievedQuestion = remoteStore.getQuestion(q.getId());
		assertEquals(q, retrievedQuestion);
	}

	/*
	public void testPutAnswer() {
		mRemoteStore.putAnswer(mAnswer);
		Answer retrieved = manager.getAnswer(mQuestion.getId(), mAnswer.getId());
		assertEquals(mAnswer, retrieved);
	}
	
	public void testPutComment() {
		mRemoteStore.putQComment(qComment);
		Comment<Question> retrieved = (Comment<Question>) manager.getQuestionComment(qComment.getId(), qComment.getId());
		assertEquals(qComment, retrieved);
	}
	
	 UC1 TC 1.2 
	public void testDispRemoteQ() {
		
		mRemoteStore.putQuestion(mQuestion);
		Question retrieved = manager.getQuestion(mQuestion.getId());
		assertEquals(mQuestion, retrieved);
	}*/
	
}
