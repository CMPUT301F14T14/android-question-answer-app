package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.RemoteDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import junit.framework.TestCase;

public class SearchTest extends TestCase {

	private DataManager manager;
	private LocalDataStore local;
	private RemoteDataStore remote;
	private MockElasticSearch remoteSearch;

	protected void setUp() throws Exception {
		super.setUp();
		manager = DataManager.getInstance();
		local = new LocalDataStore();
		remote = new RemoteDataStore();
		remoteSearch = new MockElasticSearch();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * UC10 TC10.1 - Empty Search
	 */

	public void testEmptySearch() {
		// given query results, data manager can get answers and questions
		List<UUID> results = remoteSearch.query("");
		assertEquals(0, results.size());
	}

	/**
	 * UC10 TC10.2 - Offline Search
	 */

	public void testOfflineSearch() {
		try {
			manager.disableNetworkAccess();
			List<UUID> results = remoteSearch.query("");
			fail();
		} catch (Exception e) {
			// Passed
		}
	}

	/**
	 * UC10 TC10.3 - Proper Search
	 */

	public void testRealSearch() {
		List<UUID> results = remoteSearch.query("jeans");
		List<Answer> resultAnswers = new ArrayList<Answer>();
		List<Question> resultQuestions = new ArrayList<Question>();
		for (UUID i : results) {
			if (manager.getAnswer(i, null) != null) {
				resultAnswers.add(manager.getAnswer(i, null));
			} else {
				resultQuestions.add(manager.getQuestion(i));
			}
		}
		assertTrue(resultQuestions.size() > 0 || resultAnswers.size() > 0);
	}

}