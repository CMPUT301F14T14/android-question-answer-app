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
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddAnswerDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddCommentDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AddImage;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AnswerListAdapter;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.CommentListAdapter;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.ViewCommentDialogFragment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends Activity
implements AddCommentDialogFragment.AddCommentDialogCallback {
	public static final String ARG_QUESTION_ID = "QUESTION_UUID";

	static final String TAB_ANSWERS = "answer";
	static final String TAB_COMMENTS = "comment";
	
	private AnswerListAdapter aListAdapter = null;
	private CommentListAdapter<Question> cListAdapter = null;
	private UUID questionId = null;
	private Question question;
	private TabHost tabs;
	private DataManager dataManager;
	private ClientData clientData;
	private List<Comment<Question>> commentList;
	private List<Answer> answerList;
	
	private AddImage AI = new AddImage();
	private static final int CAMERA =  1;
	private static final int ADD_IMAGE = 2;
	public Image img;
	private long MAX_SIZE = 64000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);

		tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();
		
		Intent intent = getIntent();
		dataManager = DataManager.getInstance(getApplicationContext());
		clientData = new ClientData(this);
		String qId = intent.getStringExtra(ARG_QUESTION_ID);
		
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
				intent.putExtra(ARG_QUESTION_ID, qId.toString());
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
		sortAnswers();
		aListAdapter.update();
	}
	
	public void sortAnswers(){
		Collections.sort(answerList, new Comparator<Answer>(){

			@Override
			public int compare(Answer lhs, Answer rhs) {
				if(lhs.getUpvotes().equals(rhs.getUpvotes())){
					return lhs.getDate().compareTo(rhs.getDate());
				}
				return lhs.getUpvotes()-rhs.getUpvotes();
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
		AddAnswerDialogFragment dialogFragment = new AddAnswerDialogFragment();
		dialogFragment.show(getFragmentManager(), "addanswerdialogfragmentlayout");
	}
	
	public void addAnswerCallback(String body, Image img, Location location) {
		ClientData cd = new ClientData(this);
		Answer answer = null;
		try {
			answer = new Answer(question.getId(), body, cd.getUsername(), img);
			if(location != null){
				answer.setLocation(location);
			}
		} catch (IllegalArgumentException e) {
			Toast.makeText(getApplicationContext(), R.string.add_answer_err_invalid, Toast.LENGTH_SHORT).show();
		}
		dataManager.addAnswer(answer);
		question.addAnswer(answer.getId());
		dataManager.addQuestion(question, null);
		updateQuestion(question);
	}

    /**
     * Handler for the add comment button
     * 
     * Opens a new dialog in which to create a comment
     * 
     * @param view
     */
    public void addComment(View view) {
		AddCommentDialogFragment dialogFragment = new AddCommentDialogFragment();
		dialogFragment.show(getFragmentManager(), "AVAaddcommentDF");
	}

    @Override
	public void addCommentCallback(String text, Location loc) {
		ClientData cd = new ClientData(this);
		Comment<Question> comment = null;
		try {
			comment = new Comment<Question>(question.getId(), text, cd.getUsername());
			if(loc != null){
				comment.setLocation(loc);
			}
		} catch (IllegalArgumentException e) {
			Toast.makeText(getApplicationContext(), R.string.add_comment_err_invalid, Toast.LENGTH_SHORT).show();
		}
		dataManager.addQuestionComment(comment);
		question.addComment(comment.getId());
		dataManager.addQuestion(question, null);
		updateQuestion(question);
    }

	public void updateQuestion(Question q) {
		new QuestionUpdateCallback().run(q);
	}

	public void upvoteQuestion(View v) {
		// Adds upvotes and updates textview to show number of upvotes
		if (question.getAuthor().equals(clientData.getUsername())) {
			Toast.makeText(this, "Unable to upvote your own question", Toast.LENGTH_SHORT).show();
			return;
		}
		if (clientData.isItemUpvoted(question.getId())) {
			Toast.makeText(this, "Cannot upvote a question more than once", Toast.LENGTH_SHORT).show();
			return;
		}
		question.addUpvote();
		TextView upvotes = (TextView) findViewById(R.id.upvotes);
		upvotes.setText(question.getUpvotes().toString());
		DataManager.getInstance(this).addQuestion(question, null);
		clientData.markItemUpvoted(question.getId());
		}
	
	public void favQuestion(View v){
		//Need to change icon
		//Perhaps have a HashMap holding whether the question
		//is favorited or not.
		
		ImageButton Favbutton = (ImageButton)findViewById(R.id.question_view_fav_button);
		Favbutton.setImageResource(R.drawable.ic_fav_highlighted);
		ClientData cd = new ClientData(this);
		if (cd.getFavorites().contains(question.getId())) {
			//Question is already favorited.
			List<UUID> favq = cd.getFavorites();
			favq.remove(question.getId());
			cd.saveFavorites(favq);
			Favbutton.setImageResource(R.drawable.ic_fav_reg);
		} else {
			List<UUID> favq = cd.getFavorites();
			favq.add(question.getId());
			cd.saveFavorites(favq);
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
			dataManager.getAnswerList(question, new AnswerListUpdateCallback());
			dataManager.getCommentList(question, new CommentListUpdateCallback());
			if(q.getImage() != null){
			ImageView imgV = (ImageView) findViewById(R.id.questionImage);
			imgV.setImageBitmap(q.getImage().getBitmap());
			}
			// Set status of favorite button
			ClientData cd = new ClientData(getApplicationContext());
			if (cd.getFavorites().contains(question.getId())) {
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
	
	private class AnswerListUpdateCallback implements Callback<List<Answer>> {

		@Override
		public void run(List<Answer> list) {
			answerList.clear();
			answerList.addAll(list);
			aListAdapter.update();
			
			// Update count on answer tab
			TextView aTabLabel = (TextView) tabs.getTabWidget().getChildAt(0)
					.findViewById(android.R.id.title);
			aTabLabel.setText(String.format("%s (%d)",
					getString(R.string.tab_answers), aListAdapter.getCount()));
		}
		
	}
	
	private class CommentListUpdateCallback implements Callback<List<Comment<Question>>> {

		@Override
		public void run(List<Comment<Question>> list) {
			commentList.clear();
			commentList.addAll(list);
			cListAdapter.update();
			cListAdapter.sort(new Comparator<Comment<Question>>() {

				@Override
				public int compare(Comment<Question> c1, Comment<Question> c2) {
					return c2.getDate().compareTo(c1.getDate());
				}
				
			});
		}
	}
	
	public void takeAPhoto(View v){
		Intent intent = AI.takeAPhoto();
		startActivityForResult(intent, CAMERA);
	}
	
	public void addImage(View v){
		Intent intent = AI.addPhoto();
		startActivityForResult(intent.createChooser(intent, "Select Image"), ADD_IMAGE);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			if (requestCode == CAMERA){
				img = new Image(this, AI.getImgUri());
			}
			else if(requestCode == ADD_IMAGE){
				Uri uri = data.getData();
				img = new Image(this, uri);

				}
			}
		while(img.getSize() > MAX_SIZE){
			img.setImageData(img.compress(img.getBitmap()));
		}
		
		
	}
}
 

