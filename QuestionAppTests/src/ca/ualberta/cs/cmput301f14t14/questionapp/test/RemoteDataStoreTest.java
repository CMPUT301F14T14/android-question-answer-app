package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.RemoteDataStore;
import junit.framework.TestCase;

public class RemoteDataStoreTest extends TestCase{

	private RemoteDataStore mRemoteStore;
	private Question mQuestion;
	private DataManager manager;
	
	protected void setUp() throws Exception {
		super.setUp();
		mRemoteStore = new RemoteDataStore();
		mQuestion = new Question("TITLE", "BODY", null);
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
		assertEquals(mQuestion, manager.getQuestion(mQuestion.getId()));
	}
	
	public void testPushAnswer() {
		
	}
	
	public void testPushComment() {
		
	}
	
}
