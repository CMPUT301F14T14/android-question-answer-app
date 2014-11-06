package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddAnswerDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddQuestionDialogFragment;
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

		TextView qTitle = (TextView) findViewById(R.id.questionTitle);
		qTitle.setText(question.getTitle());
		TextView qBody = (TextView) findViewById(R.id.questionBody);
		qBody.setText(question.getBody());
		TextView qUser = (TextView) findViewById(R.id.questionUser);
		qUser.setText(question.getAuthor());
		
		List<Answer> al = new ArrayList<Answer>();
		for(Answer a: question.getAnswerList()) {
			al.add(a);
			Log.d("bob",a.getBody());
		}
		
		List<Comment<Question>> cl = new ArrayList<Comment<Question>>();
		for(Comment<Question> c: question.getCommentList()) {
			cl.add(c);
		}

		ala = new AnswerListAdapter(this, R.layout.list_answer, al);
		ListView answerView = (ListView) findViewById(R.id.answerSummaryList);
		answerView.setAdapter(ala);
		
		cla = new CommentListAdapter<Question>(this, R.layout.list_comment, cl);
		ListView commentView = (ListView) findViewById(R.id.commentList);
		commentView.setAdapter(cla);
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
	
    public void addAnswer(View view){
    	FragmentManager fm = getFragmentManager();
    	Bundle QuesBox = new Bundle();
    	QuesBox.putString( "Qid",question.getId().toString());
    	AddAnswerDialogFragment aA = new AddAnswerDialogFragment();
    	aA.setArguments(QuesBox);
    	aA.show(fm, "addanswerdialogfragmentlayout");
    }
    
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
}
