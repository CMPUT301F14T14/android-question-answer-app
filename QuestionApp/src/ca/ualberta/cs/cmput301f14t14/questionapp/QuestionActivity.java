package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddAnswerDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AnswerListAdapter;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.CommentListAdapter;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends Activity {
	static final String TAB_ANSWERS = "answer";
	static final String TAB_COMMENTS = "comment";
	
	private AnswerListAdapter ala = null;
	private CommentListAdapter<Question> cla = null;
	private Question question;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);

		TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();
		
		Intent intent = getIntent();
		DataManager dataManager = DataManager.getInstance(getApplicationContext());
		String qId = intent.getStringExtra("QUESTION_UUID");
		if (qId != null) {
			// we have a Question, grab it from dataManager
			UUID id = UUID.fromString(qId);
			question = dataManager.getQuestion(id);
		}
		else {
			// no Question, toss er back to the main screen
			Toast.makeText(getApplicationContext(), "Could not open specified question.", Toast.LENGTH_LONG).show();
			finish();
		}
		
		TabHost.TabSpec aTab = tabs.newTabSpec(TAB_ANSWERS);
		aTab.setContent(R.id.answerSummaryList);
		aTab.setIndicator(getString(R.string.tab_answers));
		tabs.addTab(aTab);

		TabHost.TabSpec cTab = tabs.newTabSpec(TAB_COMMENTS);
		cTab.setContent(R.id.commentList);
		cTab.setIndicator(getString(R.string.tab_comments));
		tabs.addTab(cTab);

		// set text on view with relevant data from question
		TextView qTitle = (TextView) findViewById(R.id.questionTitle);
		TextView upvotes = (TextView) findViewById(R.id.upvotes);
		qTitle.setText(question.getTitle());
		TextView qBody = (TextView) findViewById(R.id.questionBody);
		qBody.setText(question.getBody());
		TextView qUser = (TextView) findViewById(R.id.questionUser);
		qUser.setText(question.getAuthor());
		upvotes.setText(question.getUpvotes().toString());
		
		// set arrayLists of relevant comments and answers
		List<Answer> al = new ArrayList<Answer>();
		for(Answer a: question.getAnswerList()) {
			al.add(a);
		}
		
		List<Comment<Question>> cl = new ArrayList<Comment<Question>>();
		for(Comment<Question> c: question.getCommentList()) {
			cl.add(c);
		}

		// create the array adapters to show information
		ala = new AnswerListAdapter(this, R.layout.list_answer, al);
		ListView answerView = (ListView) findViewById(R.id.answerSummaryList);
		answerView.setAdapter(ala);
		
		cla = new CommentListAdapter<Question>(this, R.layout.list_comment, cl);
		ListView commentView = (ListView) findViewById(R.id.commentList);
		commentView.setAdapter(cla);
		
		// when clicking an answer item, view the answer in a separate activity
		answerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Answer a = ala.getItem(position);
				UUID qId = question.getId();
				Intent intent = new Intent(getBaseContext(), AnswerViewActivity.class);
				intent.putExtra("QUESTION_UUID", qId.toString());
				intent.putExtra("ANSWER_UUID", a.getId().toString());
				startActivity(intent);
			}
		});
		
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// method for the add answer button, to given question
    public void addAnswer(View view){
    	FragmentManager fm = getFragmentManager();
    	Bundle QuesBox = new Bundle();
    	QuesBox.putString( "Qid",question.getId().toString());
    	AddAnswerDialogFragment aA = new AddAnswerDialogFragment();
    	aA.setArguments(QuesBox);
    	aA.show(fm, "addanswerdialogfragmentlayout");
    }
    
    // after adding comment or answer, reset and update the lists
    // associated with the question
    public void updateQuestion(Question q) {
    	this.question = q;
    	ala.clear();
    	cla.clear();
    	for(Answer a: question.getAnswerList()) {
			ala.add(a);
		}
    	for(Comment<Question> c: question.getCommentList()) {
			cla.add(c);
		}
    	ala.update();
    	cla.update();
    	Toast.makeText(getApplicationContext(), "Item successfully added", Toast.LENGTH_LONG).show();
    }
    
    // Upvote DAT QUESTION
    public void upvoteQuestion(View v){
    	question.addUpvote();
    	TextView upvotes = (TextView) findViewById(R.id.upvotes);
    	upvotes.setText(question.getUpvotes().toString());
    }
}
