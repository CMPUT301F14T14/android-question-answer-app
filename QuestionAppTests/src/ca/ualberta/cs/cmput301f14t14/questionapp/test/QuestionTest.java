package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class QuestionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test UC1 - Add a Question
	 */
	public void testAddQuestion() {
		String title = "Question Title";
		String body = "Question body?";
		Image image = null;
		
		// Test successful creation of a question
		Question q = new Question(title, body, image);
		assertEquals(title, q.getTitle());
		assertEquals(body, q.getBody());
		assertEquals(image, q.getImage());
		
		// Test invalid title
		try {
			new Question("", body, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		try {
			new Question(null, body, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		
		// Test invalid body
		try {
			new Question(title, "", image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
		try {
			new Question(title, null, image);
			fail();
		} catch (IllegalArgumentException ex) {
			// Passed
		}
	}
	
}
