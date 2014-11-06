package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddQuestionDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.QuestionListAdapter;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DataManager dataManager;
	private QuestionListAdapter qla = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       	setContentView(R.layout.activity_main);
       
        //Create a spinner adapter for sorting choices
        ArrayAdapter<CharSequence> sortAdapter = 
        		ArrayAdapter.createFromResource(
        					getActionBar().getThemedContext(),
        					R.array.sort_spinner_data, 
        					android.R.layout.simple_spinner_item
        				);
        // Specify the layout to use when the list of choices appears
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setListNavigationCallbacks(sortAdapter, changeSort());
                
        dataManager = DataManager.getInstance(this);
        
        if(dataManager.getUsername() == null){

        	Intent intent = new Intent(this.getBaseContext(), WelcomeScreenActivity.class);
        	startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        }
        
        List<Question> qList = dataManager.load();
        qla = new QuestionListAdapter(this, R.layout.list_question, qList);  
        ListView questionView = (ListView) findViewById(R.id.question_list);
        questionView.setAdapter(qla);
        questionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Question question = qla.getItem(position);
				UUID qId = question.getId();
				Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
				intent.putExtra("QUESTION_UUID", qId.toString());
				startActivity(intent);
			}
		});
    }
    
    public OnNavigationListener changeSort() {
    	//This is the callback that is called when the user chooses 
    	//a sorting order from the spinner in the action bar.
    	return new OnNavigationListener() {
			@Override
			public boolean onNavigationItemSelected(int itemposition, long itemid) {
				// Auto-generated method stub
				return false;
			}
		};
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        switch (id) {
	        case R.id.action_settings:
	        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void onActivityResult(int requestCode, int resultCode,Intent intent){
    	
    	if (requestCode == Activity.RESULT_FIRST_USER && resultCode == Activity.RESULT_OK){
    		String username = intent.getStringExtra("username");
    		Toast.makeText(this, "Welcome " + username + " to Qasper", Toast.LENGTH_SHORT).show();
    		dataManager.setUsername(username);
    		
    	}
    	
    }
    
    public void addQuestion(View view){
    	FragmentManager fm = getFragmentManager();
    	AddQuestionDialogFragment aQ = new AddQuestionDialogFragment();
    	aQ.show(fm, "addquestiondialogfragmentlayout");
    }
    
    public void updateList(Question q) {
    	qla.add(q);
    	qla.update();
    	Toast.makeText(getApplicationContext(), "Question successfully added", Toast.LENGTH_LONG).show();
    }
}
