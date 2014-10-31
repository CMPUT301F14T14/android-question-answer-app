package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class QuestionListTest extends ActivityInstrumentationTestCase2<Activity> {

	public QuestionListTest(Class<Activity> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}
	private LocalDataStore mLocalStore;
	public RemoteDataStore mRemoteStore = new RemoteDataStore();

	private Question mQuestion;
	private DataManager manager;
	private Answer mAnswer;
	private Comment mComment;
	
	protected void setUp() throws Exception {
		super.setUp();
		mLocalStore = new LocalDataStore();

		mQuestion = new Question("TITLE", "BODY", null);
		mAnswer = new Answer("ANSWERBODY", null);
		mComment = new Comment("COMMENTBODY", "Boris");
		manager = DataManager.getInstance();
		
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
		mRemoteStore.putQuestion(mQuestion);
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
		manager.disableNetworkAccess();
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
		manager.disableNetworkAccess();
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
		manager.disableNetworkAccess();
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
		manager.disableNetworkAccess();
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
		manager.enableNetworkAccess();
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
		manager.enableNetworkAccess();
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
