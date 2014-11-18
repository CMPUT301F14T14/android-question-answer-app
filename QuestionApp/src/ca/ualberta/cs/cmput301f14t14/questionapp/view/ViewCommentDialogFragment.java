package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;



public class ViewCommentDialogFragment extends DialogFragment  {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		//UUID.fromString NPEs if property is missing. Need to fix this.
		UUID questionId = null;
		UUID answerId = null;
		UUID commentId = null;
		
		//These two should never be null/empty.
		questionId = UUID.fromString(getArguments().getString("questionId"));
		commentId = UUID.fromString(getArguments().getString("commentId"));
		try {answerId = UUID.fromString(getArguments().getString("answerId"));
		} catch(NullPointerException e){}
		
		DataManager datamanager = DataManager.getInstance(getActivity());
		
		Comment<?> comment = null;
		if(answerId != null){
			 comment = datamanager.getAnswerComment(commentId);
		}else{
			 comment = datamanager.getQuestionComment(commentId);
		}
		 
		builder.setTitle(R.string.viewing_comments);
		View viewComment = inflater.inflate(R.layout.viewcommentdialogfragmentlayout, null);
		builder.setView(viewComment);
		
		TextView username = (TextView) viewComment.findViewById(R.id.comment_username);
		username.setText(comment.getUsername());
		
		TextView body = (TextView) viewComment.findViewById(R.id.comment_body);
		body.setText(comment.getBody());

		
		
	return builder.create();				
	}
	
}
