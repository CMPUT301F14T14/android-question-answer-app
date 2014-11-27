package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import java.io.IOException;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class QuestionListTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private LocalDataStore mLocalStore;
	private RemoteDataStore mRemoteStore;

	private Question mQuestion;
	private DataManager manager;
	private Answer mAnswer;
	private Comment mComment;

	public QuestionListTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mLocalStore = new LocalDataStore(getInstrumentation().getTargetContext().getApplicationContext());
		mRemoteStore = new RemoteDataStore(getInstrumentation().getTargetContext().getApplicationContext());

		mQuestion = new Question("TITLE", "BODY", "AUTHOR", null);
		mAnswer = new Answer(mQuestion.getId(), "ANSWERBODY", "AUTHOR", null);
		mComment = new Comment<Question>(mQuestion.getId(), "COMMENTBODY", "Boris");
		manager = DataManager.getInstance(getInstrumentation().getTargetContext().getApplicationContext());
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	/* UC1 TC1.1 */
	public void testViewNoItems() {
		/*
		mLocalStore.clear();
		manager.disableNetworkAccess();
		assertEquals(manager.getItemCount(), 1);
		assertEquals(manager.getItems().get(0).toString().equals("List is empty"), true);
		*/
		
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */
	}

	/** UC1 TC1.2 */
	public void testViewRemotelyStoredItems() {
		try {
			mRemoteStore.putQuestion(mQuestion);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */
		assertEquals(false, true);
	}
	/** UC1 TC1.3 */
	public void testViewLocalStoredItems() {
		mLocalStore.putQuestion(mQuestion);
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */
		assertEquals(false, true);
	}
	
	/** UC2 TC2.1 */
	public void testViewQuestionOnline() {
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */
		assertEquals(false, true);
	}
	
	/** UC2 TC2.2 */
	public void testViewQuestionOffline() {
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */
		//create a question online
		//go into listview
		//pick a question
		//see if data matches
		assertEquals(false, true);
	}
	
	/** UC2 TC2.3 */
	public void testViewFavoriteOffline() {
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */
		//go to favorite view
		//find some views by ids
		assertEquals(false, true);
	}
	
	/** UC3 TC 3.1 */
	public void testViewQuestionCommentsOffline() {
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */

		//Naviagate to a question view.
		//Navigate to comments.
		//findviewby id and make sure it's successful
		
		assertEquals(false, true);
	}
	
	/** UC3 TC 3.2 */
	public void testViewAnswerCommentsOffline() {
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */

		//Naviagate to an answer view.
		//Navigate to comments.
		//findviewby id and make sure it's successful
		
		assertEquals(false, true);
	}	
	
	/** UC3 TC 3.3 */
	public void testViewQuestionCommentsOnline() {
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */

		//Naviagate to a question view.
		//Navigate to comments.
		//findviewby id and make sure it's successful
		
		assertEquals(false, true);
	}
	
	/** UC3 TC 3.4 */
	public void testViewAnswerCommentsOnline() {
		/*
		 * Need to create a list adapter, list view, then use
		 * AtivityInstrumentationTestCase2 to get Views by Id
		 * then compare.
		 */

		//Naviagate to an answer view.
		//Navigate to comments.
		//findviewby id and make sure it's successful
		
		assertEquals(false, true);
	}	
	
}
