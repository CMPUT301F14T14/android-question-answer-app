package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.LocalDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.RemoteDataStore;
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
		manager = new DataManager();
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
		List<Integer> results = remoteSearch.query("");
		assertEquals(0, results.size());
	}

	/**
	 * UC10 TC10.2 - Offline Search
	 */

	public void testOfflineSearch() {
		try {
			manager.disableNetworkAccess();
			List<Integer> results = remoteSearch.query("");
			fail();
		} catch (Exception e) {
			// Passed
		}
	}

	/**
	 * UC10 TC10.3 - Proper Search
	 */

	public void testRealSearch() {
		List<Integer> results = remoteSearch.query("jeans");
		List<Answer> resultAnswers = new ArrayList<Answer>();
		List<Question> resultQuestions = new ArrayList<Question>();
		for (int i : results) {
			if (manager.getAnswer(i) != null) {
				resultAnswers.add(manager.getAnswer(i));
			} else {
				resultQuestions.add(manager.getQuestion(i));
			}
		}
		assertTrue(resultQuestions.size() > 0 || resultAnswers.size() > 0);
	}

}