package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;


public class DataManagerTest extends TestCase{

	private DataManager manager;
	private Question validQ;
	
	protected void setUp() throws Exception {
		super.setUp();
		manager = new DataManager();
		validQ = new Question("TITLE", "BODY", null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public void testFindQuestion() {
		
	}
	
	public void testAddQuestion() {
		manager.addQuestion(validQ);
		assertNotNull(manager.getQuestion(validQ.getId()));
	}
	
	public void testAddAnswer() {
		Answer validA = new Answer("aBody", null);
		validQ.addAnswer(validA);
		manager.putQuestion(validQ);
		assertNotNull(manager.getAnswer(validA.getId()));
	}
	
	public void testFavoriteQuestion() {
		
	}
	
	public void testUpvoteQuestion() {
		
	}
	
	public void testAddCommentQuestion() {
		
	}
	
	public void testAddCommentAnswer() {
		
	}

	public void testFavoriteAnswer() {
		
	}
	
	public void testUpvoteAnswer() {
		
	}
	
	public void testFindAnswer() {
		
	}
}
