package ca.ualberta.cs.cmput301f14t14.questionapp.test.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.test.ActivityInstrumentationTestCase2;

public class ClientDataTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private ClientData clientData;

	public ClientDataTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		clientData = new ClientData(getInstrumentation().getTargetContext().getApplicationContext());
		clientData.clear();
		clientData.setUsername("Dummy Username");
	}
	
	public void testUsername() {
		assertEquals("Dummy Username", clientData.getUsername());
		clientData.setUsername("Somebody Else");
		assertEquals("Somebody Else", clientData.getUsername());
		clientData.clear();
		assertEquals(null, clientData.getUsername());
		clientData.setUsername("User");
		assertEquals("User", clientData.getUsername());
	}
	
	public void testAddFavorite() {
		//Create new question and save it as a favorite
		Question q = new Question("Test", "Body", "Boris", null);
		List<UUID> l = new ArrayList<UUID>();
		l.add(q.getId());
		clientData.saveFavorites(l);
		
		assertTrue(clientData.getFavorites().contains(q.getId()));
	}
	
	public void testGetFavorite() {
		//Get a favorite from the ClientData
		Question q = new Question("Test", "Body", "Boris", null);
		List<UUID> l = new ArrayList<UUID>();
		l.add(q.getId());
		clientData.saveFavorites(l);
		assertTrue(clientData.getFavorites().contains(q.getId()));
		
	}

}
