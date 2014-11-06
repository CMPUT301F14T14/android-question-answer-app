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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerViewActivity extends Activity {

	private Answer answer = null;
	String qId = null;
	String aId = null;
	
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
			answer = dataManager.getAnswer(Qid, Aid);
		}
		else {
			// no Question, toss er back to somewhere
			Toast.makeText(getApplicationContext(), "Could not open specified question. Question or Answer IDs were null.", Toast.LENGTH_LONG).show();
			finish();
		}
		
		/* Populate the answer text into the top part of the form */
		((TextView) findViewById(R.id.answer_body)).setText(this.answer.getBody());
		((TextView) findViewById(R.id.answer_username)).setText(this.answer.getAuthor());
		
		/* Populate comment list */
		List<Comment<Answer>> cl = new ArrayList<Comment<Answer>>();
		cl.addAll(answer.getCommentList());
			//Set list adapter
		final CommentListAdapter<Answer> cla = new CommentListAdapter<Answer>(this, R.layout.list_comment, cl);
		ListView commentView = (ListView) findViewById(R.id.answer_view_comment_list);
		commentView.setAdapter(cla);
		
		//Comment view needs an onItemClick Listener.
		commentView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				/* Open a new ViewCommentDialogFragment */
				ViewCommentDialogFragment vcdf = new ViewCommentDialogFragment();
				Bundle argbundle = new Bundle();
				argbundle.putString("questionId", qId);
				argbundle.putString("answerId", aId);
				
				final Comment<Answer> comment = cla.getItem(position);			
				argbundle.putString("commentId", comment.getId().toString());
				vcdf.setArguments(argbundle);
				
				vcdf.show(getFragmentManager(), "AVCommentViewDF");
			}
		});
		
		/* Onclick listeners for upvote, add comment */
		((Button)findViewById(R.id.answer_view_upvote_answer))
				.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						/* Upvote this answer */
						DataManager dm = DataManager.getInstance(getApplicationContext());
						dm.getAnswer(UUID.fromString(qId), UUID.fromString(aId)).addUpvote();
						//TODO: Once we start displaying upvote count, need to do a view update.
					}
				});
		((Button)findViewById(R.id.answer_view_add_comment))
				.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						/* Add a comment to this answer */
						AddCommentDialogFragment acdf = new AddCommentDialogFragment();
						Bundle argbundle = new Bundle();
						argbundle.putString("questionId", qId);
						argbundle.putString("answerId", aId);
						acdf.setArguments(argbundle);
						acdf.show(getFragmentManager(), "AVAaddcommentDF");
						//Need to update AddComentDialogFragment to do an update on the views. (this)
					}
				});
		
	}
	
	//Need Onclick Listeners for Upvote, Comment.
	//Need to populate ListView with comments for this answer.
	//Need to have an onitemclicked listener on the list view to open the ViewCommentDialogFragment.
	
	@Override
	protected void onResume() {
		super.onResume();
		//
	}
}
