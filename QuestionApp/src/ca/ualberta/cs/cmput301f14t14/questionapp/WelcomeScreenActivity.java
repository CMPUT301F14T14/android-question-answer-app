package ca.ualberta.cs.cmput301f14t14.questionapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.IView;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.UsernameFragment;

public class WelcomeScreenActivity extends Activity implements IView {

	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);
		findViewById(R.id.specifyUsernameButton).setOnClickListener(ocl);
		findViewById(R.id.googleAccountButton).setOnClickListener(ocl);
	}

	private View.OnClickListener ocl = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()){
			case R.id.specifyUsernameButton:
				onCreateUsername();
				
				 break;
			case R.id.googleAccountButton:
				
				//Toast.makeText(this, "Clicked the google button", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	public void onCreateUsername(){
		
		UsernameFragment userFragment = new UsernameFragment();
		userFragment.show(getFragmentManager(), "Add usernameF");
					
	}
	
	public void setUsername(UsernameFragment uf){
		Dialog d = uf.getDialog();
		EditText userText = (EditText) d.findViewById(R.id.usernameText);
		String username = userText.getText().toString();
		
		if (username.length() > 0) {
			Intent intent = new Intent();
			intent.putExtra("username", username);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
