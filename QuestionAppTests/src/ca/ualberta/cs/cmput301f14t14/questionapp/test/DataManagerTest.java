package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;


public class DataManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public DataManagerTest() {
		super(MainActivity.class);
	}

	private DataManager manager;
	private Question validQ;
	private Answer validA;

	protected void setUp() throws Exception {
		super.setUp();
		manager = DataManager.getInstance(getInstrumentation().getTargetContext().getApplicationContext());
		manager.clearClientData();
		manager.setUsername("User");
		validQ = new Question("TITLE", "BODY", "AUTHOR", null);
		validA = new Answer(validQ, "aBody", "aAuthor", null);
	}

	public void testSetUsername() {
		assertEquals("User", manager.getUsername());
		manager.setUsername("Different user");
		assertEquals("Different user", manager.getUsername());
	}

	/**
	 * UC12 TC 12.1 - Favorite a Question
	 */
	
	public void testFavoriteQuestion() {
		// user indicates that they wish to favorite a question
		manager.favoriteQuestion(validQ.getId());
		LocalDataStore local = new LocalDataStore(getInstrumentation().getTargetContext().getApplicationContext());
		//boolean favorited = local.isFavorite(validQ.getId());
		//assertTrue(favorited);
	}
	
	/**
	 * UC12 TC 12.2 - Favorite an Answer
	 */
	
	public void testFavoriteAnswer() {
		manager.favoriteAnswer(validA.getId());
		LocalDataStore local = new LocalDataStore(getInstrumentation().getTargetContext().getApplicationContext());
		//boolean favorited = local.isFavorite(validA.getId());
		//assertTrue(favorited);
	}
	
}
