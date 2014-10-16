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
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Test UC2 - Attach an Image
	 */
	
	public void testAddImage() {
		Image image = new Image("localPath", "remotePath");
		mAnswer = new Answer("ABody", image);
		mQuestion = new Question("Title", "QBody", image);
		assertNotNull(mAnswer.getImage());
		assertNotNull(mQuestion.getImage());
	}
}
