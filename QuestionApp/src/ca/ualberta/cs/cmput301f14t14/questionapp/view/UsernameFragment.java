package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.WelcomeScreenActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class UsernameFragment extends DialogFragment {
	
	public UsernameFragment(){
		
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstance){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		builder.setTitle(R.string.please_enter_a_username_below_);
		builder.setView(inflater.inflate(R.layout.username_fragment, null))
		.setPositiveButton(R.string.enter, 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//Callback to WelcomeScreenActivity
						((WelcomeScreenActivity) getActivity()).setUsername(UsernameFragment.this);
						
					}
						
						
		}
		
		
		).setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {	
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//Do nothing
					}
		
			
		});
		
		return builder.create();
	}



}
