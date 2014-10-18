package ca.ualberta.cs.cmput301f14t14.questionapp.test;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class MockElasticSearch {

	private List<Integer> allItems;

	public MockElasticSearch() {
		Question q = new Question("", "", null);
		Answer a = new Answer("",null);
		allItems.add(q.getId());
		allItems.add(a.getId());
	}

	public List<Integer> query(String keywords) {
		//comma separate keywords
		String[] words = keywords.split(",");
		List<Integer> results = new ArrayList<Integer>();
		for(int item : allItems){
			for(String word : words){
			//if elastic search finds match, add to results
			}
		}
		return results;
	}
}
