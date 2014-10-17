package ca.ualberta.cs.cmput301f14t14.questionapp.test;

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
	 * Test UC2 - Attach an Image
	 */
	
	public void testAddImage() {
		Image image = new Image(null, null);
		mAnswer = new Answer("ABody", image);
		mQuestion = new Question("Title", "QBody", image);
		assertNotNull(mAnswer.getImage());
		assertNotNull(mQuestion.getImage());
	}
	
	public void testPathFileTooBig() {
		try {
			Uri local = null;
			local = Uri.parse("~\\QuestionAppTests\\res\\drawable-hdpi\\over64.jpg");
			Image tooBig = new Image(local, null);
			fail("Try giving a smaller image.");
		}
		catch(IllegalArgumentException e){
			//Passed
		}
	}
	
	public void testPathFileRightSize() {
			Uri local = null;
			local = Uri.parse("~\\QuestionAppTests\\res\\drawable-hdpi\\under64.jpg");
			Image rightSize = new Image(local, null);
			assertNotNull(rightSize);
			assertEquals(rightSize.getLocalUrl(), local );
	}
	
	public void testRemoteTooBig() {
		try {
			Uri remote = null;
			remote = Uri.parse("/REMOTED/q/");
			Image tooBig = new Image(null, remote);
			fail("Try giving a smaller image.");
		}
		catch(IllegalArgumentException e){
			//Passed
		}
		
	}
	
	public void testRemoteRightSize() {
		Uri remote = null;
		remote = Uri.parse("/STILLREMOTED/n/");
		Image rightSize = new Image(null, remote);
		assertNotNull(rightSize);
		assertEquals(rightSize.getRemoteUrl(), remote );
	}

	
}
