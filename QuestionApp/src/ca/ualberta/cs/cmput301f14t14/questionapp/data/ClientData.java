package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This class handles data that only exists on the client, and is
 * never transferred to the web service.
 * @author Stephen Just
 */
public class ClientData {
	private static final String PREF_SET = "cs.ualberta.cs.cmput301f14t14.questionapp.prefs";
	private static final String VAL_USERNAME = "username";
	
	private SharedPreferences prefs;
	
	public ClientData(Context context) {
		this.prefs = context.getSharedPreferences(PREF_SET, Context.MODE_PRIVATE);
	}

	public String getUsername() {
		return this.prefs.getString(VAL_USERNAME, null);
	}
	
	public void setUsername(String username) {
		username = username.trim();
		if (username.length() == 0) {
			throw new IllegalArgumentException("New username may not be blank.");
		}
		Editor e = prefs.edit();
		e.putString(VAL_USERNAME, username);
		e.apply();
	}
	
	public void clear() {
		Editor e = prefs.edit();
		e.clear();
		e.apply();
	}
}
