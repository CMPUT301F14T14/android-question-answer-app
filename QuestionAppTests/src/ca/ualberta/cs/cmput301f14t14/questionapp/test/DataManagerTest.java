package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;


public class DataManagerTest extends TestCase{

	private DataManager manager;
	private Question validQ;
	private Answer validA;
	
	protected void setUp() throws Exception {
		super.setUp();
		manager = new DataManager();
		validQ = new Question("TITLE", "BODY", null);
		validA = new Answer("aBody", null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public void testFindQuestion() {
		
	}
	
	/**
	 * UC4 TC 4.1 - Create a Question
	 */
	
	public void testAddQuestion() {
		manager.addQuestion(validQ);
		assertNotNull(manager.getQuestion(validQ.getId()));
	}
	
	/**
	 * UC5 TC 5.1 - Answer a Question
	 */
	
	public void testAddAnswer() {
		
		validQ.addAnswer(validA);
		manager.putQuestion(validQ);
		assertNotNull(manager.getAnswer(validA.getId()));
	}
	
	/**
	 * UC12 TC 12.1 - Favorite a Question
	 */
	
	public void testFavoriteQuestion() {
		// user indicates that they wish to favorite a question
		manager.favoriteQuestion(validQ);
		LocalDataStore local = new LocalDataStore();
		boolean favorited = local.isFavorite(validQ.getId());
		assertTrue(favorited);
	}
	
	/**
	 * UC12 TC 12.2 - Favorite an Answer
	 */
	
	public void testFavoriteAnswer() {
		manager.favoriteAnswer(validA);
		LocalDataStore local = new LocalDataStore();
		boolean favorited = local.isFavorite(validA.getId());
		assertTrue(favorited);
	}
	
	public void testUpvoteQuestion() {
		
	}
	
	public void testAddCommentQuestion() {
		
	}
	
	public void testAddCommentAnswer() {
		
	}

	
	
	public void testUpvoteAnswer() {
		
	}
	
	public void testFindAnswer() {
		
	}
}
