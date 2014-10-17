package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class ImageTest extends TestCase {

	Question mQuestion;
	Answer mAnswer;

	protected void setUp() throws Exception {
		super.setUp();;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * UC7 TC7.1, 7.2 - Attach an Image
	 */
	
	public void testAddImage() {
		Image image = new Image(Uri.parse("android.resource://QuestionAppTests/drawable/under64"), null);
		mAnswer = new Answer("ABody", image);
		mQuestion = new Question("Title", "QBody", image);
		assertNotNull(mAnswer.getImage());
		assertNotNull(mQuestion.getImage());
	}
	
	/**
	 * UC7 TC7.3 - Image too big
	 */
	public void testPathFileTooBig() {
		try {
			Uri local = null;
			local = Uri.parse("android.resource://QuestionAppTests/drawable/over64");
			Image tooBig = new Image(local, null);
			fail("Try giving a smaller image.");
		}
		catch(IllegalArgumentException e){
			//Passed
		}
	}


	
}
