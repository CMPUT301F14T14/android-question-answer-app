package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;

public class AddCommentDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View viewComment = inflater.inflate(R.layout.addcommentdialogfragmentlayout, null);
		builder.setView(viewComment)
		.setPositiveButton(R.string.OK, 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Add Commment
							/*
							 * EditText body = (EditText)findViewById(R.id.add_comment_body);
							 * DataManager datamanager = getInstance();
							 * datamanager.addComment();
							 * setCommentText
							 * 
							 * */
							
						}
					}
			).setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Return to previous View
						}
					}
					);
		
	return builder.create();				
	}
}
