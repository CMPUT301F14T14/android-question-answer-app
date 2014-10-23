package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.DataManager;
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
		
		/*Integer commentId = savedInstanceState.getInt("commentId");
		
		DataManager datamanager = getInstance();
		Comment comment = datamanager.getComment(id);
		  
		TextView username = (TextView) findViewById(R.id.comment_username);
		username.setText(comment.getUserName());
		
		TextView body = (TextView) findViewById(R.id.comment_body);
		body.setText(comment.getBody());*/

		View viewComment = inflater.inflate(R.layout.viewcommentdialogfragmentlayout, null);
		builder.setView(viewComment);
		
	return builder.create();				
	}
	
}
