package ca.ualberta.cs.cmput301f14t14.questionapp;

import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
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
			
			switch (v.getId()){
			case R.id.specifyUsernameButton:
				onCreateUsername();
				
				 break;
			case R.id.googleAccountButton:
				googleUsername();
				
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
	
	public void googleUsername(){
	    AccountManager manager = AccountManager.get(this); 
	    Account[] accounts = manager.getAccountsByType("com.google");
	    
	    if(accounts.length > 1){
	    	List<String> possibleEmails = new ArrayList<String>();

	    	for (Account account : accounts) {
	    		if(!account.equals(null) && !account.equals("")){
	    			possibleEmails.add(account.name);
	    		}
	    	}

	    	if(possibleEmails.size() == 0){
	    		Toast.makeText(getBaseContext(), "No google account", Toast.LENGTH_SHORT).show();
	    		return;
	    	}
	        else if (possibleEmails.size() == 1){
	            String user = possibleEmails.get(0);
				Intent intent = new Intent();
				intent.putExtra("username", user);
				setResult(Activity.RESULT_OK, intent);
				finish();
				return;
	        }
	    }
	    else if (accounts.length == 1 && !accounts[0].equals(null) && !accounts[0].equals("")){
	    	String user = accounts[0].name;
			Intent intent = new Intent();
			intent.putExtra("username", user);
			setResult(Activity.RESULT_OK, intent);
			finish();
			return;
	    }
	    Toast.makeText(getBaseContext(), "No google account", Toast.LENGTH_SHORT).show();
		return;
	}
	
	
	
}
