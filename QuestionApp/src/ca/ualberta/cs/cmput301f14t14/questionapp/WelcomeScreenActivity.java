package ca.ualberta.cs.cmput301f14t14.questionapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ClientData;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.AboutBoxDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.IView;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.SearchQueryDialogFragment;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.UsernameFragment;

public class WelcomeScreenActivity extends Activity implements IView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);
		findViewById(R.id.specifyUsernameButton).setOnClickListener(ocl);
	}

	
	//Private required onClickListener to start fragment 
	private View.OnClickListener ocl = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			
			switch (v.getId()){
			case R.id.specifyUsernameButton:
				onCreateUsername();
				 break;

			}
		}
	};
	
	public void onCreateUsername(){
		//Create the fragment
		UsernameFragment userFragment = new UsernameFragment();
		userFragment.show(getFragmentManager(), "Add usernameF");
					
	}
	
	public void setUsername(UsernameFragment uf){
		// Grab the result from the user entering their username, if successful return to main activity and
		// send the username.
		Dialog d = uf.getDialog();
		EditText userText = (EditText) d.findViewById(R.id.usernameText);
		String username = userText.getText().toString();
		
		if (username.length() > 0) {
			// return to main activity
			DataManager dm = DataManager.getInstance(this);
			ClientData cd = new ClientData(this);
			cd.setUsername(username);
			Toast.makeText(this, "Welcome " + username + " to Qasper", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
		}
		
		
	}
	

	@Override
	public void update() {
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcomes_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        switch (id) {
	        case R.id.welcomescreen_showaboutbox:
	        	FragmentManager fm = getFragmentManager();
	        	AboutBoxDialogFragment ab = new AboutBoxDialogFragment();
	        	ab.show(fm, "WelcomeSCreenAboutBoxDF");
	        	break;
        }
        return super.onOptionsItemSelected(item);
    }
	
	
}