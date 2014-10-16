package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class ImageTest extends TestCase {

	Question mQuestion;
	Answer mAnswer;

	protected void setUp() throws Exception {
		super.setUp();
		mQuestion = new Question("Title", "QBody", null);
		mAnswer = new Answer("ABody", null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Test UC2 - Attach an Image
	 */
	
	public void testAddImage() {

		Image image = new Image();
	}
}
