package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UsernameFragment extends Fragment {
	
	public UsernameFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		View newView = inflater.inflate(R.layout.welcome_screen, container, false);
		
		Button submitUsernameButton = (Button) newView.findViewById(R.id.enterUsernameButton);
		
		submitUsernameButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				EditText userText = (EditText) arg0.findViewById(R.id.usernameText);
				String username = userText.getText().toString();
				
				Toast.makeText(getActivity(), "Welcome " + username + " to Qasper", Toast.LENGTH_SHORT).show();
				
			}
			
		});
		return newView;
	}

}
