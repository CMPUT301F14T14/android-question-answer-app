package ca.ualberta.cs.cmput301f14t14.questionapp;

import android.app.Activity;
import android.os.Bundle;

public class AnswerViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answerviewactivitylayout);
		
	}
	
	//Need Onclick Listeners for Upvote, Comment.
	//Need to populate ListView with comments for this answer.
	//Need to have an onitemclicked listener on the list view to open the ViewCommentDialogFragment.
	
}
