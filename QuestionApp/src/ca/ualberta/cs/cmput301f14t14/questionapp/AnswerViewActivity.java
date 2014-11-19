package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddCommentDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.CommentListAdapter;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.ViewCommentDialogFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerViewActivity extends Activity {

	private Answer answer = null;
	String qId = null;
	String aId = null;
	
	/* These need to be class variables so that update
	 * updateAnswer() can access them. ucla is initialized
	 * as null right now, later initialized to a final.
	 */
	private List<Comment<Answer>> cl = new ArrayList<Comment<Answer>>();
	CommentListAdapter<Answer> ucla = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answerviewactivitylayout);
		
		
		/* Need to pull answer UUID from the bundle, populate form fields */
		Intent intent = getIntent();
		DataManager dataManager = DataManager.getInstance(getApplicationContext());
		this.qId = intent.getStringExtra("QUESTION_UUID");
		this.aId = intent.getStringExtra("ANSWER_UUID");
		if (qId != null || aId != null) {
			UUID Qid = UUID.fromString(qId);
			UUID Aid = UUID.fromString(aId);
			//TODO: callback is null. GetAnswer is blocking UI thread.
			answer = dataManager.getAnswer(Aid, null);
		}
		else {
			// no Question, toss er back to somewhere
			Toast.makeText(getApplicationContext(), "Could not open specified question. Question or Answer IDs were null.", Toast.LENGTH_LONG).show();
			finish();
		}
		
		/* Populate the answer text into the top part of the form */
		((TextView) findViewById(R.id.answer_body)).setText(this.answer.getBody());
		((TextView) findViewById(R.id.answer_username)).setText(this.answer.getAuthor());
		((TextView) findViewById(R.id.answer_upvotes)).setText(answer.getUpvotes().toString());
		/* Populate comment list */
		//created as class variable.
		//TODO: null callbacks mean this will be blocking
		cl.addAll(dataManager.getCommentList(answer, null));
			//Set list adapter
		final CommentListAdapter<Answer> cla = new CommentListAdapter<Answer>(this, R.layout.list_comment, cl);
		ucla = cla; // set UCLA to be a reference to the final cla.
		ListView commentView = (ListView) findViewById(R.id.answer_view_comment_list);
		commentView.setAdapter(cla);
		
		//Comment view needs an onItemClick Listener.
		commentView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				 //Open a new ViewCommentDialogFragment 
				ViewCommentDialogFragment vcdf = new ViewCommentDialogFragment();
				Bundle argbundle = new Bundle();
				argbundle.putString("questionId", qId);
				argbundle.putString("answerId", aId);
				
				Comment<Answer> comment = cla.getItem(position);			
				argbundle.putString("commentId", comment.getId().toString());
				vcdf.setArguments(argbundle);
				
				vcdf.show(getFragmentManager(), "AVCommentViewDF");
				cla.update();
			}
		});
		((ImageButton)findViewById(R.id.answer_view_add_comment))
				.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Add a comment to this answer 
						AddCommentDialogFragment acdf = new AddCommentDialogFragment();
						Bundle argbundle = new Bundle();
						argbundle.putString("questionId", qId);
						argbundle.putString("answerId", aId);
						acdf.setArguments(argbundle);
						acdf.show(getFragmentManager(), "AVAaddcommentDF");
						//Need to update AddComentDialogFragment to do an update on the views. (this)
						cla.update();
					}
				});
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//Update list adapters.
		
	}
	/**
	 * Updates the comment list adapter in the AnswerView
	 * activity once an AddCommentDialogFragment adds a comment
	 * to this answer.
	 * @param a an answer object 
	 */
	public void updateAnswer(Answer a) {
		//Need to update the list adapters.
		//move to class variables so I can access them.
		DataManager dataManager = DataManager.getInstance(getApplicationContext());
		ucla.clear();
		//TODO: null callbacks mean this will be blocking
		ucla.addAll(dataManager.getCommentList(a, null));
		ucla.update();
    	//Toast.makeText(getApplicationContext(), "Item successfully added", Toast.LENGTH_LONG).show();

	}
	/** 
	 * Adds an upvote to the answer, increments the counter
	 * in the view. Also updates the model.
	 * @param v View needed for xml hook to run.
	 */
	public void addAnsUpvote(View v){
		answer.addUpvote();
		TextView upvotes = (TextView) findViewById(R.id.answer_upvotes);
		upvotes.setText(answer.getUpvotes().toString());
	}
}
