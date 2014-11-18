package ca.ualberta.cs.cmput301f14t14.questionapp.test.data;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.test.mock.MockData;

public class LocalDataStoreTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private LocalDataStore localStore;

	private final Question new_q = new Question("Geometry troubles", "Why does my triangle have four sides?", "Jim", null);
	private final Answer new_a = new Answer(new_q.getId(), "You are looking at this from the wrong dimension.", "John", null);
	private final Comment<Question> new_cq = new Comment<Question>(new_q.getId(), "Are you sure you're looking at a triangle?", "Joe");
	private final Comment<Answer> new_ca = new Comment<Answer>(new_a.getId(), "I don't think so...", "Jane");
	
	public LocalDataStoreTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		localStore = new LocalDataStore(getInstrumentation().getTargetContext().getApplicationContext());
		localStore.clear();
		localStore.save();
		for (Question q: MockData.getMockData()) {
			localStore.putQuestion(q);
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPutQuestion() {
		assertNull(localStore.getQuestion(new_q.getId()));
		localStore.putQuestion(new_q);
		assertEquals(localStore.getQuestion(new_q.getId()), new_q);
	}
	
	public void testPutAnswer() {
		assertNull(localStore.getQuestion(new_q.getId()));
		assertNull(localStore.getAnswer(new_a.getId()));
		localStore.putAnswer(new_a);
		assertEquals(new_q, localStore.getQuestion(new_q.getId()));
		assertEquals(new_a, localStore.getAnswer(new_a.getId()));
	}
	
	public void testPutComment() {
		assertNull(localStore.getAComment(new_ca.getId()));
		localStore.putAComment(new_ca);
		assertEquals(new_ca, localStore.getAComment(new_ca.getId()));
		assertNull(localStore.getQComment(new_cq.getId()));
		localStore.putQComment(new_cq);
		assertEquals(new_cq, localStore.getQComment(new_cq.getId()));
	}
	
	public void testSerialize() {
		localStore.putQuestion(new_q);
		localStore.save();
		LocalDataStore localStoreClone = new LocalDataStore(getInstrumentation().getTargetContext().getApplicationContext());
		assertEquals(localStore.getQuestionList().size(), localStoreClone.getQuestionList().size());
		assertNotNull(localStoreClone.getQuestion(new_q.getId()));
		assertEquals(localStore.getQuestion(new_q.getId()), localStoreClone.getQuestion(new_q.getId()));
		assertEquals(localStore, localStoreClone);
	}
	
}
