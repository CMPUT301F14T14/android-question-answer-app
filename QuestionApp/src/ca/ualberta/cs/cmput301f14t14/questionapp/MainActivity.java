package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddQuestionDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.QuestionListAdapter;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.SearchQueryDialogFragment;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class MainActivity extends Activity {

	private DataManager dataManager;
	private QuestionListAdapter qla = null;
	private List<Question> qList = null;
	
	private ClientData cd = null;
	private Callback<List<Question>> listCallback = null;
	private Callback<Question> favouriteQuestionCallback = null;
	
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE =  100;
	private Uri Urifile;
	ImageView img;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       	setContentView(R.layout.activity_main);

       	//Instantiate Client Data for username stuff
       	cd = new ClientData(this);
       	
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
        
        // if first time logging in, prompt user to set username
        if(cd.getUsername() == null){

        	Intent intent = new Intent(this.getBaseContext(), WelcomeScreenActivity.class);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	startActivity(intent);
        }
        
        qList = new ArrayList<Question>();
        qla = new QuestionListAdapter(this, R.layout.list_question, qList);
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
        
        listCallback = new Callback<List<Question>>() {

			@Override
			public void run(List<Question> list) {
		        qList.clear();

		        if (list != null) {
		        	qList.addAll(list);
		        }
		        qla.update();
			}
        };
        
        //create the list of questions
        dataManager.getQuestionList(listCallback);
    }
    
    @Override 
	public void onResume(){
		super.onResume();
		qla.update();
	}
    
    public OnNavigationListener changeSort() {
    	//This is the callback that is called when the user chooses 
    	//a sorting order from the spinner in the action bar.
    	final Context activitycontext = this;
    	return new OnNavigationListener() {
			@Override
			public boolean onNavigationItemSelected(int itemposition, long itemid) {
				// change way of sorting based on way selected
				ClientData cd = new ClientData(activitycontext);
				sortList(itemposition, dataManager, cd);
				qla.update();
				return true;
				
			}
		};
    }


    protected void sortList(int itemposition, final DataManager dm, final ClientData cd) {
		switch (itemposition){
		case 1:{
			// Sort by date
			Collections.sort(qList, new Comparator<Question>(){
			
				@Override
				public int compare(Question q1, Question q2) {
					
					return q2.getDate().compareTo(q1.getDate());
				}
			
			
			});
			break;
		}	
		case 2:{
			// Sort by most upvoted
			Collections.sort(qList, new Comparator<Question>(){

				@Override
				public int compare(Question q1, Question q2) {
					if(q1.getUpvotes() == q2.getUpvotes()){
						return q1.getDate().compareTo(q2.getDate());
					}
					
					return q2.getUpvotes() - q1.getUpvotes();
				}
								
				
				});
			break;
			}
		case 3:{
			final List<UUID> favQ = cd.getFavoriteQuestions();
			favouriteQuestionCallback = new Callback<Question>(){

				@Override
				public void run(Question o) {
					if(!qList.contains(o)){
						qList.add(o);
						
					}
				}
				
			};

			for(UUID q: favQ){
				dataManager.getQuestion(q, favouriteQuestionCallback);
			}
			Collections.sort(qList, new Comparator<Question>(){

				@Override
				public int compare(Question arg0, Question arg1) {
					if(favQ.contains(arg0.getId()) == favQ.contains(arg1.getId())){
						//Sort by date if both in or not in favourites
						return arg0.getDate().compareTo(arg1.getDate());
					}
					else if(favQ.contains(arg0.getId()) && !favQ.contains(arg1.getId())){
						return -1;
					}
					else{
						return 1;
					}
				}
				
			});
			break;
		}
		case 4:{
			
			// Sort by current user posts
			Collections.sort(qList, new Comparator<Question>(){
				
				@Override
				public int compare(Question q1, Question q2) {
					//Instantiate ClientData here
					
					if(q1.getAuthor().equals(cd.getUsername()) == q2.getAuthor().equals(cd.getUsername())){
						return q1.getDate().compareTo(q2.getDate());
					}
					else if(q1.getAuthor().equals(cd.getUsername()) && !q2.getAuthor().equals(cd.getUsername()))
						return -1;
					else{
						return 1;
					}
				}
				
			});
			break;
		}
		}
	
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
	        case R.id.action_question_list_search:
	        	FragmentManager fm = getFragmentManager();
	        	SearchQueryDialogFragment sQ = new SearchQueryDialogFragment();
	        	sQ.show(fm, "search");
	        	break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void addQuestion(View view){
    	// open dialog to create a question
    	FragmentManager fm = getFragmentManager();
    	AddQuestionDialogFragment aQ = new AddQuestionDialogFragment();
    	aQ.show(fm, "addquestiondialogfragmentlayout");
    }
    
    public void updateList(Question q) {
    	// update the list
    	qla.add(q);
    	qla.update();
    	Toast.makeText(getApplicationContext(), "Question successfully added", Toast.LENGTH_LONG).show();
    }
    
    public void searchQuestions(String q) {
    	Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
		intent.putExtra("QUERY_STRING", q);
		startActivity(intent);
    }
	
	public void takeAPhoto(View v){
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QAImages";
		File folder = new File(path);
		
		if (!folder.exists()){
			folder.mkdir();
		}
			
		String imagePathAndFileName = path + File.separator + 
				String.valueOf(System.currentTimeMillis()) + ".jpg" ;
		
		File imageFile = new File(imagePathAndFileName);
		Urifile = Uri.fromFile(imageFile);
		LayoutInflater inflater = getLayoutInflater();
		final View text = inflater.inflate(R.layout.addquestiondialogfragmentlayout , null);
		img = (ImageView)text.findViewById(R.id.imageView1);  
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Urifile);
		startActivityForResult(intent, 0);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
		super.onActivityResult(requestCode, resultCode, data);
		//Bitmap bp = (Bitmap) data.getExtras().get("data");
		//img.setImageBitmap(bp);
		img.setImageDrawable((Drawable.createFromPath(Urifile.getPath())));
		/*if (requestcode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			if (resultcode == Activity.RESULT_OK){
				Image img = new Image(file, );
			}
			else{
				Toast.makeText(getActivity(), "Image Capture Failed", Toast.LENGTH_LONG).show();
			}
		}*/
		
	}
	public void addImage(View v){
		
	}
    
}
