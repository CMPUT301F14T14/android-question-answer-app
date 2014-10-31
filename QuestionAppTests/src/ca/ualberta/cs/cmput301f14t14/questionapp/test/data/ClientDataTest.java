package ca.ualberta.cs.cmput301f14t14.questionapp.test.data;

import ca.ualberta.cs.cmput301f14t14.questionapp.MainActivity;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import android.test.ActivityInstrumentationTestCase2;

public class ClientDataTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private ClientData clientData;

	public ClientDataTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		clientData = new ClientData(getActivity());
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

}
