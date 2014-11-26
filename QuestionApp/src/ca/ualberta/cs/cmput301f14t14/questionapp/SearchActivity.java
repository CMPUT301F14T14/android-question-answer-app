package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.GenericSearchItem;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.ESSearchTask;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.GenericSearchItemAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SearchActivity extends Activity {

	private GenericSearchItemAdapter gla = null;
	private Callback<List<GenericSearchItem>> searchResultCallback;
	private List<GenericSearchItem> searchResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       	setContentView(R.layout.activity_search);             
    	Intent intent = getIntent();
    	String query = intent.getStringExtra("QUERY_STRING");
        
    
  		searchResultCallback = new Callback<List<GenericSearchItem>>(){
  			
  		@Override
  		public void run(List<GenericSearchItem> o) {
  				searchResult = o;
  				gla.update();
			}
        		
  		};
        	
  		ESSearchTask esTask = new ESSearchTask(this);
  		esTask.equals(query);
  		esTask.setCallBack(searchResultCallback);
  		
  		
  		if(searchResult == null){
  			return;
        }
  		gla = new GenericSearchItemAdapter(this, R.layout.list_generic, searchResult);  
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
}
