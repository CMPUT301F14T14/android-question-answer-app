package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;


public class DataManagerTest extends TestCase{

	private DataManager manager;
	private Question validQ;
	private Answer validA;
	
	protected void setUp() throws Exception {
		super.setUp();
		manager = DataManager.getInstance();
		validQ = new Question("TITLE", "BODY", null);
		validA = new Answer("aBody", null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	
	/**
	 * UC12 TC 12.1 - Favorite a Question
	 */
	
	public void testFavoriteQuestion() {
		// user indicates that they wish to favorite a question
		manager.favoriteQuestion(validQ.getId());
		LocalDataStore local = new LocalDataStore();
		boolean favorited = local.isFavorite(validQ.getId());
		assertTrue(favorited);
	}
	
	/**
	 * UC12 TC 12.2 - Favorite an Answer
	 */
	
	public void testFavoriteAnswer() {
		manager.favoriteAnswer(validA.getId());
		LocalDataStore local = new LocalDataStore();
		boolean favorited = local.isFavorite(validA.getId());
		assertTrue(favorited);
	}
	
}
