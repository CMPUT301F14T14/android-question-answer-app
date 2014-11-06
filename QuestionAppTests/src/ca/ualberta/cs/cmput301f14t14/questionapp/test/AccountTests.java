package ca.ualberta.cs.cmput301f14t14.questionapp.test;


import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class AccountTests extends ActivityInstrumentationTestCase2<MainActivity> {

	private DataManager manager;
	
	public AccountTests() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		manager = DataManager.getInstance(getInstrumentation().getTargetContext().getApplicationContext());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAuthorship(){
		manager.setUsername("Boris");
		Question testQuestion = new Question("stuff title", "stuff", manager.getUsername(), null);
		assertTrue("Question author is not Boris!", testQuestion.getAuthor().equals("Boris"));	

	}
		
}
