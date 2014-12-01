package ca.ualberta.cs.cmput301f14t14.questionapp.test.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		clientData = new ClientData(getInstrumentation().getTargetContext().getApplicationContext());
		clientData.clear();
	}

	/**
	 * Test that we can set a username from the initial state of 'null'
	 */
	public void testSetFirstUsername() {
		assertEquals(null, clientData.getUsername());
		clientData.setUsername("Username");
		assertEquals("Username", clientData.getUsername());
	}

	/**
	 * Test that we can change a username after the initial set
	 */
	public void testResetUsername() {
		clientData.setUsername("User");
		assertEquals("User", clientData.getUsername());
		clientData.setUsername("Someone else");
		assertEquals("Someone else", clientData.getUsername());
	}

	/**
	 * Test that the favorite list is not initialized to null
	 */
	public void testGetFavorite_emptyList() {
		List<UUID> list = clientData.getFavorites();
		assertEquals(new ArrayList<UUID>(), list);
	}

	/**
	 * Test that a single favorite item can be added
	 */
	public void testAddFavorite() {
		UUID id = UUID.randomUUID();
		List<UUID> list = new ArrayList<UUID>();
		list.add(id);
		clientData.saveFavorites(list);
		assertEquals(1, clientData.getFavorites().size());
		assertTrue(clientData.getFavorites().contains(id));
	}

	/**
	 * Test that multiple favorites can be added
	 */
	public void testAddFavorite_multiple() {
		List<UUID> list = new ArrayList<UUID>();
		for (int i = 0; i < 10; i++) {
			UUID id = UUID.randomUUID();
			list.add(id);
		}
		clientData.saveFavorites(list);
		assertEquals(10, clientData.getFavorites().size());
	}

	/**
	 * Test that duplicate favorites cannot be added
	 */
	public void testAddFavorite_duplicate() {
		UUID id = UUID.randomUUID();
		List<UUID> list = new ArrayList<UUID>();
		list.add(id);
		list.add(id);
		clientData.saveFavorites(list);
		assertEquals(1, clientData.getFavorites().size());
		assertTrue(clientData.getFavorites().contains(id));
	}

	/**
	 * Test that a single item can be marked as upvoted
	 */
	public void testSetUpvoted() {
		UUID id = UUID.randomUUID();
		assertFalse(clientData.isItemUpvoted(id));
		clientData.markItemUpvoted(id);
		assertTrue(clientData.isItemUpvoted(id));
	}

	/**
	 * Test marking multiple items as upvoted
	 */
	public void testSetUpvoted_multiple() {
		List<UUID> list = new ArrayList<UUID>();
		for (int i = 0; i < 10; i++) {
			UUID id = UUID.randomUUID();
			list.add(id);
		}
		for (UUID id: list) {
			assertFalse(clientData.isItemUpvoted(id));
		}
		for (UUID id: list) {
			clientData.markItemUpvoted(id);
		}
		for (UUID id: list) {
			assertTrue(clientData.isItemUpvoted(id));
		}
	}

	/**
	 * Test that an item cannot be marked as upvoted twice
	 */
	public void testMarkUpvoted_duplicate() {
		UUID id = UUID.randomUUID();
		clientData.markItemUpvoted(id);
		clientData.markItemUpvoted(id);
		assertTrue(clientData.isItemUpvoted(id));
	}

	/**
	 * Test that an empty read later list does not return null
	 */
	public void testGetReadLater_empty() {
		List<UUID> list = clientData.getReadLaterItems();
		assertEquals(new ArrayList<UUID>(), list);
	}

	/**
	 * Test that a single item can be marked as read later
	 */
	public void testMarkReadLater() {
		UUID id = UUID.randomUUID();
		assertFalse(clientData.isReadLater(id));
		clientData.markReadLater(id);
		assertTrue(clientData.isReadLater(id));
		assertEquals(1, clientData.getReadLaterItems().size());
		assertTrue(clientData.getReadLaterItems().contains(id));
		clientData.unmarkReadLater(id);
		assertFalse(clientData.getReadLaterItems().contains(id));
		assertEquals(0, clientData.getReadLaterItems().size());
	}

	/**
	 * Test marking multiple items as read later
	 */
	public void testMarkReadLater_multiple() {
		for (int i = 0; i < 10; i++) {
			UUID id = UUID.randomUUID();
			clientData.markReadLater(id);
		}
		assertEquals(10, clientData.getReadLaterItems().size());
	}

	/**
	 * Test that an item cannot be marked as read later twice
	 */
	public void testMarkReadLater_duplicate() {
		UUID id = UUID.randomUUID();
		clientData.markReadLater(id);
		clientData.markReadLater(id);
		assertEquals(1, clientData.getReadLaterItems().size());
		assertTrue(clientData.getReadLaterItems().contains(id));
	}

	/**
	 * Test that an item not on the read later list can safely be unmarked
	 */
	public void testUnmarkReadLater_notSet() {
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		clientData.markReadLater(id1);
		clientData.markReadLater(id2);
		assertEquals(2, clientData.getReadLaterItems().size());
		clientData.unmarkReadLater(id1);
		assertEquals(1, clientData.getReadLaterItems().size());
		clientData.unmarkReadLater(id1);
		assertEquals(1, clientData.getReadLaterItems().size());
		assertTrue(clientData.isReadLater(id2));
		assertFalse(clientData.isReadLater(id1));
	}
}
