package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddCommentDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.CommentListAdapter;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.ViewCommentDialogFragment;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

public class AnswerViewActivity extends Activity
implements AddCommentDialogFragment.AddCommentDialogCallback {

	private Answer answer = null;
	private DataManager dataManager = null;
	private ClientData clientData;
	String aId = null;
	
	/* These need to be class variables so that update
	 * updateAnswer() can access them. ucla is initialized
	 * as null right now, later initialized to a final.
	 */
	private List<Comment<Answer>> commentList = new ArrayList<Comment<Answer>>();
	CommentListAdapter<Answer> commentListAdapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dataManager = DataManager.getInstance(this);
		
		clientData = new ClientData(this);
		
		setContentView(R.layout.answerviewactivitylayout);

		UUID Aid = null;
		
		/* Need to pull answer UUID from the bundle, populate form fields */
		Intent intent = getIntent();
		this.aId = intent.getStringExtra("ANSWER_UUID");
		if (aId != null) {
			Aid = UUID.fromString(aId);
		}
		else {
			// no Answer, toss er back to somewhere
			Toast.makeText(getApplicationContext(), "Could not open specified answer. Answer ID was null.", Toast.LENGTH_LONG).show();
			finish();
		}
	
		commentListAdapter = new CommentListAdapter<Answer>(this, R.layout.list_comment, commentList);
		ListView commentView = (ListView) findViewById(R.id.answer_view_comment_list);
		commentView.setAdapter(commentListAdapter);
		
		dataManager.getAnswer(Aid, new UpdateAnswerCallback());
		
		//Comment view needs an onItemClick Listener.
		commentView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				 //Open a new ViewCommentDialogFragment 
				ViewCommentDialogFragment vcdf = new ViewCommentDialogFragment();
				Bundle argbundle = new Bundle();
				argbundle.putString("answerId", aId);
				
				Comment<Answer> comment = commentListAdapter.getItem(position);			
				argbundle.putString("commentId", comment.getId().toString());
				vcdf.setArguments(argbundle);
				
				vcdf.show(getFragmentManager(), "AVCommentViewDF");
				commentListAdapter.update();
			}
		});
		((ImageButton)findViewById(R.id.answer_view_add_comment))
				.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						addComment();
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
		UpdateAnswerCallback callback = new UpdateAnswerCallback();
		callback.run(a);
	}

	/** 
	 * Adds an upvote to the answer, increments the counter
	 * in the view. Also updates the model.
	 * @param v View needed for xml hook to run.
	 */
	public void addAnsUpvote(View v){
		if (answer.getAuthor().equals(clientData.getUsername())) {
			Toast.makeText(this, "Unable to upvote your own answer", Toast.LENGTH_SHORT).show();
			return;
		}
		if (clientData.isItemUpvoted(answer.getId())) {
			Toast.makeText(this, "Cannot upvote an answer more than once", Toast.LENGTH_SHORT).show();
			return;
		}
		answer.addUpvote();
		TextView upvotes = (TextView) findViewById(R.id.answer_upvotes);
		upvotes.setText(answer.getUpvotes().toString());
		clientData.markItemUpvoted(answer.getId());
	}
	
	private class UpdateAnswerCallback implements Callback<Answer> {

		@Override
		public void run(Answer a) {
			answer = a;

			// Populate the answer text into the top part of the form
			((TextView) findViewById(R.id.answer_body)).setText(answer.getBody());
			((TextView) findViewById(R.id.answer_username)).setText(answer.getAuthor());
			((TextView) findViewById(R.id.answer_upvotes)).setText(answer.getUpvotes().toString());
			if(answer.getImage() != null){
				((ImageView) findViewById(R.id.answerImage)).setImageBitmap(answer.getImage().getBitmap());
			}
			
			dataManager.getCommentList(answer, new UpdateCommentListCallback());
		}
		
	}
	
	private class UpdateCommentListCallback implements Callback<List<Comment<Answer>>> {

		@Override
		public void run(List<Comment<Answer>> list) {
			commentList.clear();
			commentList.addAll(list);
			commentListAdapter.update();
			commentListAdapter.sort(new Comparator<Comment<Answer>>() {

				@Override
				public int compare(Comment<Answer> c1, Comment<Answer> c2) {
					return c2.getDate().compareTo(c1.getDate());
				}
				
			});
		}
		
	}

	public void addComment() {
		// Add a comment to this answer 
		AddCommentDialogFragment dialogFragment = new AddCommentDialogFragment();
		dialogFragment.show(getFragmentManager(), "AVAaddcommentDF");
	}

	@Override
	public void addCommentCallback(String text, Location loc) {
		ClientData cd = new ClientData(this);
		Comment<Answer> comment = null;
		try {
			comment = new Comment<Answer>(answer.getId(), text, cd.getUsername());
			if(loc != null){
				comment.setLocation(loc);
			}
		} catch (IllegalArgumentException e) {
			Toast.makeText(getApplicationContext(), R.string.add_comment_err_invalid, Toast.LENGTH_SHORT).show();
		}
		dataManager.addAnswerComment(comment);
		answer.addComment(comment.getId());
		dataManager.addAnswer(answer);
		updateAnswer(answer);
	}
}
