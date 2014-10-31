package ca.ualberta.cs.cmput301f14t14.questionapp.test;


import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class AccountTests extends TestCase{
	
	private LocalDataStore mLocalStore;
	
	protected void setUp() throws Exception {
		mLocalStore = new LocalDataStore();
		
		;
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetAccountUsernames() {
		String username = mLocalStore.getAccountUsername();
		assertTrue("There are no accounts",(username != null));
	}
	
	public void testSetUsername(){
		String username = mLocalStore.getAccountUsername();
		assertNotNull("There are no usernames in the list", username);
		mLocalStore.setUsername("Boris");
		assertTrue("Boris is not the username!", mLocalStore.getUsername().equals("Boris"));
		
		try{
			mLocalStore.setUsername(null);
			fail();
		}catch(IllegalArgumentException e){
			//test passed
		}
	}
	
	public void testAuthorship(){
		mLocalStore.setUsername("Boris");
		Question testQuestion = new Question("stuff title", "stuff", null);
		assertTrue("Question author is not Boris!", testQuestion.getAuthor().equals("Boris"));
	}
		
}
