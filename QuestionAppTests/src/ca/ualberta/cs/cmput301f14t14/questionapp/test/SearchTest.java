package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.RemoteDataStore;
import junit.framework.TestCase;

public class SearchTest extends TestCase {

	private DataManager manager;
	private LocalDataStore local;
	private RemoteDataStore remote;

	protected void setUp() throws Exception {
		super.setUp();
		manager = new DataManager();
		local =  new LocalDataStore();
		remote = new RemoteDataStore();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * UC10 TC10.1 - Empty Search
	 */
	
	public void testEmptySearch() {
		List<Object> results = manager.queryKeywords("1-9xkjshdiused999999999fihskdfjfsfkj");
		assertNull(results);
	}
	
	/**
	 * UC10 TC10.2 - Offline Search
	 */
	
	public void testOfflineSearch() {
		try {
		manager.disableNetworkAccess();
		List<Object> results = manager.queryKeywords("");
		fail();
		}
		catch(Exception e) {
			//Passed
		}
	}
	
	/**
	 * UC10 TC10.3 - Proper Search
	 */
	
	public void testRealSearch() {
		List<Object> results = manager.queryKeywords("jeans");
		assertTrue(results.size() > 0);
	}
	
}