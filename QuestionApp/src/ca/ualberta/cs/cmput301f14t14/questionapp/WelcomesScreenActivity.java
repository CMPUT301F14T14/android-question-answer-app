package ca.ualberta.cs.cmput301f14t14.questionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import ca.ualberta.cs.cmput301f14t14.questionapp.view.UsernameFragment;

public class WelcomesScreenActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);
	}

	public void onCreateUsername(View view){
		Intent intent = new Intent(getApplicationContext(), UsernameFragment.class);
		startActivityForResult(intent, Activity.RESULT_OK);
	}
	
}
