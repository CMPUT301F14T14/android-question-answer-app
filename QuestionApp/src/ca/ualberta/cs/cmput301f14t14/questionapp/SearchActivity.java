package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ESSearch;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.GenericSearchItem;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.GenericSearchItemAdapter;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.QuestionListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SearchActivity extends Activity {

	private DataManager dataManager;
	private QuestionListAdapter qla = null;
	private GenericSearchItemAdapter gla = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       	setContentView(R.layout.activity_search);             
        dataManager = DataManager.getInstance(this);
    	Intent intent = getIntent();
    	String query = intent.getStringExtra("QUERY_STRING");

        if(dataManager.getRemoteDataStore().hasAccess()){
        	ESSearch eSSearch = new ESSearch(this);
        	List<GenericSearchItem> result = null;
        	try {
				result = eSSearch.search(query);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	if(result == null){
        		return;
        	}
        	gla = new GenericSearchItemAdapter(this, R.layout.list_generic, result);  
        	ListView questionView = (ListView) findViewById(R.id.question_list);
        	questionView.setAdapter(gla);
        	questionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        		@Override
        		public void onItemClick(AdapterView<?> parent, View view,
        				int position, long id) {
        			// run off to the question view if you tap an item
        			final GenericSearchItem gItem = gla.getItem(position);
        			if(gItem.getType().toLowerCase().equals("question")){
        				UUID qId = gItem.getId();
        				Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        				intent.putExtra("QUESTION_UUID", qId.toString());
        				startActivity(intent);
        			}
        			else if(gItem.getType().toLowerCase().equals("answer")){
        				UUID aId = gItem.getId();
        				Intent intent = new Intent(getApplicationContext(), AnswerViewActivity.class);
        				intent.putExtra("ANSWER_UUID", aId.toString());
        				startActivity(intent);
        			}
        		}
        	});
        	
        }
        else{
        	DataManager dataManager = DataManager.getInstance(getApplicationContext());
        	String[] words = query.split(" ");
        	//build a question list from this query
        
        	List<Question> qList = dataManager.getQuestionList(null);
        	List<Question> qSearchList = new ArrayList<Question>();
        	Iterator<Question> list = qList.iterator();
        	while(list.hasNext()){
        		Question question = list.next();
        		// if the body or title contains any search word, add question (if not already added)
        		for(String word : words) {
        			if((question.getBody().contains(word) || question.getTitle().contains(word)) && !qSearchList.contains(question)){
        				qSearchList.add(question);
        			}
        		}
        	}
        	qla = new QuestionListAdapter(this, R.layout.list_question, qSearchList);  
        	ListView questionView = (ListView) findViewById(R.id.question_list);
        	questionView.setAdapter(qla);
        	questionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        		@Override
        		public void onItemClick(AdapterView<?> parent, View view,
        				int position, long id) {
        			// run off to the question view if you tap an item
        			final Question question = qla.getItem(position);
        			UUID qId = question.getId();
        			Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        			intent.putExtra("QUESTION_UUID", qId.toString());
        			startActivity(intent);
        		}
        	});
        }
    }
}
