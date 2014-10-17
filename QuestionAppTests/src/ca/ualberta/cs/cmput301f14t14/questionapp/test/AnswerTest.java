package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class AnswerTest extends TestCase {

	Question mQuestion;

	protected void setUp() throws Exception {
		super.setUp();
		mQuestion = new Question("Title", "Body", null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test UC3 - Add an Answer
	 */
	public void testAddAnswer() {
		String body = "Answer body.";
		Image image = null;
		Answer answer = new Answer(body, image);
		assertFalse(mQuestion.hasAnswer(answer));
		mQuestion.addAnswer(answer);
		assertTrue(mQuestion.hasAnswer(answer));
		
		// Test invalid body
		try {
			new Answer(null, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		
		try {
			new Answer("", image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
	}
}
