package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddAnswerDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddCommentDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AnswerListAdapter;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.CommentListAdapter;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.ViewCommentDialogFragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends Activity {
	static final String TAB_ANSWERS = "answer";
	static final String TAB_COMMENTS = "comment";
	
	private AnswerListAdapter aListAdapter = null;
	private CommentListAdapter<Question> cListAdapter = null;
	private UUID questionId = null;
	private Question question;
	private TabHost tabs;
	private DataManager dataManager;
	
	private List<Comment<Question>> commentList;
	private List<Answer> answerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);

		tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();
		
		Intent intent = getIntent();
		dataManager = DataManager.getInstance(getApplicationContext());
		String qId = intent.getStringExtra("QUESTION_UUID");
		
		if (qId != null) {
			// we have a Question, grab it from dataManager
			questionId = UUID.fromString(qId);
		}
		else {
			// no Question, toss er back to the main screen
			Toast.makeText(getApplicationContext(), "Could not open specified question.", Toast.LENGTH_LONG).show();
			finish();
		}

		// Set up lists and adapters
		answerList = new ArrayList<Answer>();
		commentList = new ArrayList<Comment<Question>>();
		aListAdapter = new AnswerListAdapter(this, R.layout.list_answer, answerList);
		cListAdapter = new CommentListAdapter<Question>(this, R.layout.list_comment, commentList);

		// Set up list views
		ListView answerView = (ListView) findViewById(R.id.answerSummaryList);
		answerView.setAdapter(aListAdapter);
		ListView commentView = (ListView) findViewById(R.id.commentList);
		commentView.setAdapter(cListAdapter);

		TabHost.TabSpec aTab = tabs.newTabSpec(TAB_ANSWERS);
		aTab.setContent(R.id.answerSummaryList);
		aTab.setIndicator(String.format("%s (%d)",
				getString(R.string.tab_answers), aListAdapter.getCount()));
		tabs.addTab(aTab);

		TabHost.TabSpec cTab = tabs.newTabSpec(TAB_COMMENTS);
		cTab.setContent(R.id.commentList);
		cTab.setIndicator(getString(R.string.tab_comments));
		tabs.addTab(cTab);

		// when clicking an answer item, view the answer in a separate activity
		answerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Answer a = aListAdapter.getItem(position);
				UUID qId = question.getId();
				Intent intent = new Intent(getBaseContext(), AnswerViewActivity.class);
				intent.putExtra("QUESTION_UUID", qId.toString());
				intent.putExtra("ANSWER_UUID", a.getId().toString());
				startActivity(intent);
			}
		});
		
		commentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				final Comment<Question> c = cListAdapter.getItem(position);
				//Need questionId, commentId
				UUID qId = question.getId();
				UUID cId = c.getId();
				
				ViewCommentDialogFragment vcdf = new ViewCommentDialogFragment();
				Bundle argbundle = new Bundle();
				argbundle.putString("questionId", qId.toString());
				argbundle.putString("commentId", cId.toString());
				vcdf.setArguments(argbundle);
				vcdf.show(getFragmentManager(), "QuestionActivityViewCommentDF");
			}
		});
		
		
		
	}
	
	@Override 
	public void onResume(){
		super.onResume();
		// Get question data
		dataManager.getQuestion(questionId, new QuestionUpdateCallback());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch(id) {
			case R.id.action_settings:
				return true;
			case R.id.action_add_answer:
				addAnswer(null);
				break;
			case R.id.action_add_comment:
				addComment(null);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Handler for the add answer button
	 *
	 * Opens a new dialog in which to create an answer
	 *
	 * @param view
	 */
	public void addAnswer(View view) {
		FragmentManager fm = getFragmentManager();
		Bundle args = new Bundle();
		args.putString(AddAnswerDialogFragment.ARG_QUESTION_ID, question.getId().toString());
		AddAnswerDialogFragment dialogFragment = new AddAnswerDialogFragment();
		dialogFragment.setArguments(args);
		dialogFragment.show(fm, "addanswerdialogfragmentlayout");
	}

    // after adding comment or answer, reset and update the lists
    // associated with the question
    public void addComment(View view) {
    	/* Add a comment to this question */
		AddCommentDialogFragment acdf = new AddCommentDialogFragment();
		Bundle argbundle = new Bundle();
		try{
			argbundle.putString("questionId", question.getId().toString());
		} catch (NullPointerException e) {
			Log.d("QuestionActivity Add Comment", "NPE on addcomment. Question object null");
		}
		acdf.setArguments(argbundle);
		acdf.show(getFragmentManager(), "AVAaddcommentDF");
	}

	public void updateQuestion(Question q) {
		/*
		 * Adds all aspects of a new question to the adapters and updates the
		 * lists with the new Question
		 */
		DataManager dm = DataManager.getInstance(this);
		this.question = q;
		aListAdapter.clear();
		cListAdapter.clear();
		//TODO: null callbacks mean this will be blocking
		for (Answer a : dm.getAnswerList(question, null)) {
			aListAdapter.add(a);
		}
		//TODO: null callbacks mean this will be blocking
		for (Comment<Question> c : dm.getCommentList(question, null)) {
			cListAdapter.add(c);
		}
		aListAdapter.update();
		cListAdapter.update();

		// Update count on answer tab
		TextView aTabLabel = (TextView) tabs.getTabWidget().getChildAt(0)
				.findViewById(android.R.id.title);
		aTabLabel.setText(String.format("%s (%d)",
				getString(R.string.tab_answers), aListAdapter.getCount()));

		Toast.makeText(getApplicationContext(), "Item successfully added",
				Toast.LENGTH_LONG).show();
	}

	public void upvoteQuestion(View v) {
		// Adds upvotes and updates textview to show number of upvotes
		question.addUpvote();
		TextView upvotes = (TextView) findViewById(R.id.upvotes);
		upvotes.setText(question.getUpvotes().toString());
		DataManager.getInstance(this).upvoteQuestion(question);
	}
	
	public void favQuestion(View v){
		//Need to change icon
		//Perhaps have a HashMap holding whether the question
		//is favorited or not.
		
		ImageButton Favbutton = (ImageButton)findViewById(R.id.question_view_fav_button);
		Favbutton.setImageResource(R.drawable.ic_fav_highlighted);
		ClientData cd = new ClientData(this);
		if (cd.getFavoriteQuestions().contains(this.question.getId())) {
			//Question is already favorited.
			List<UUID> favq = cd.getFavoriteQuestions();
			favq.remove(question.getId());
			cd.saveFavoriteQuestions(favq);
			Favbutton.setImageResource(R.drawable.ic_fav_reg);
		} else {
			List<UUID> favq = cd.getFavoriteQuestions();
			favq.add(question.getId());
			cd.saveFavoriteQuestions(favq);
			Favbutton.setImageResource(R.drawable.ic_fav_highlighted);
		}

	}
	
	private class QuestionUpdateCallback implements Callback<Question> {

		@Override
		public void run(Question q) {
			question = q;
			TextView qTitle = (TextView) findViewById(R.id.questionTitle);
			qTitle.setText(question.getTitle());
			TextView qBody = (TextView) findViewById(R.id.questionBody);
			qBody.setText(question.getBody());
			TextView qUser = (TextView) findViewById(R.id.questionUser);
			qUser.setText(question.getAuthor());
			TextView upvotes = (TextView) findViewById(R.id.upvotes);
			upvotes.setText(question.getUpvotes().toString());
			TextView date = (TextView) findViewById(R.id.questionDate);
			date.setText(question.getDate().toString());
			
			// TODO: Callbacks needed once remote stuff is implemented
			answerList = dataManager.getAnswerList(question, null);
			commentList = dataManager.getCommentList(question, null);
			aListAdapter.update();
			cListAdapter.update();
			
			// Update count on answer tab
			TextView aTabLabel = (TextView) tabs.getTabWidget().getChildAt(0)
					.findViewById(android.R.id.title);
			aTabLabel.setText(String.format("%s (%d)",
					getString(R.string.tab_answers), aListAdapter.getCount()));
			
			// Set status of favorite button
			ClientData cd = new ClientData(getApplicationContext());
			if (cd.getFavoriteQuestions().contains(question.getId())) {
				//Question is already favorited.
				//Set the star to be highlighted on create.
				ImageButton Favbutton = (ImageButton)findViewById(R.id.question_view_fav_button);
				Favbutton.setImageResource(R.drawable.ic_fav_highlighted);	
			} else {
				//Question is not favorited.
				ImageButton Favbutton = (ImageButton)findViewById(R.id.question_view_fav_button);
				Favbutton.setImageResource(R.drawable.ic_fav_reg);
			}		
		}
		
	}
 
}
