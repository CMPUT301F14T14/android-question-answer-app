package ca.ualberta.cs.cmput301f14t14.questionapp.test;


import java.util.ArrayList;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class AccountTests extends ActivityInstrumentationTestCase2<Activity> {
	public AccountTests(Class<Activity> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}
	private LocalDataStore mLocalStore;
	private DataManager manager;
	private Activity blankActivity;
	
	protected void setUp() throws Exception {
		super.setUp();
		blankActivity = getActivity();
		Context context = blankActivity;
		mLocalStore = new LocalDataStore();
		manager = new DataManager();
		;
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetAccountUsernames() {
		ArrayList<String> usernames = mLocalStore.getAccountUsernames();
		assertTrue("There are no accounts",(usernames != null));
	}
	
	public void testSetUsername(){
		ArrayList<String> usernames = mLocalStore.getAccountUsernames();
		assertNotNull("There are no usernames in the list", usernames.get(0));
		mLocalStore.setUsername("Boris");
		assertTrue("Boris is not the username!", mLocalStore.getUsername().equals("Boris"));
	}
	
		
}
