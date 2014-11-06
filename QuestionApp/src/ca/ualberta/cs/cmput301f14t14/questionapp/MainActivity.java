package ca.ualberta.cs.cmput301f14t14.questionapp;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddAnswerDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddCommentDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddQuestionDialogFragment;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class MainActivity extends Activity {

	private DataManager dataManager;
	
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
        
        /* Set a listener for the Add Question button so it loads the add question dialog fragment */
        findViewById(R.id.action_add_question).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AddQuestionDialogFragment aqdf = new AddQuestionDialogFragment();
				aqdf.show(getFragmentManager(), "AddQuestionDF");
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
	        case R.id.dummy_add_answer:
	        	AddAnswerDialogFragment aadf = new AddAnswerDialogFragment();
	        	aadf.show(getFragmentManager(), "AddAnswerDF");
	        	break;
	        case R.id.dummy_answerview:
	        	Intent intent = new Intent(this.getBaseContext(), AnswerViewActivity.class);
	        	startActivity(intent);
	        	break;
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
    
    /* TODO: Add the Callback needed for AddQuestionDialogFragment to actually do something */
    /* TODO: Add the callback needed for AddAnswerDialogFragment to do something. 
     * this callback should take into account where the AddAnswerDialogFragment was called from 
     * 
     * Should be in the QuestionActivity. (and the .show() call too)
     * */
     
}
