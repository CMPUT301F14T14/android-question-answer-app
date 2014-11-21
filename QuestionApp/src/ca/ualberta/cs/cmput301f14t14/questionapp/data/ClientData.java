package ca.ualberta.cs.cmput301f14t14.questionapp.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

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
	

	public void saveFavoriteQuestions(List<UUID> list){
		saveItems(list, "favqlist");
	}
	
	public ArrayList<UUID> getFavoriteQuestions() {
		return getItems("favqlist");
	}
	
	
	String readlaterfilename = "readlaterlist";
	
	public void markQuestionReadLater(UUID u) {

		
		List<UUID> appendlist = getItems(readlaterfilename);
		appendlist.add(u);
		saveItems(appendlist, readlaterfilename);
	}
	public boolean isQuestionReadLater(UUID id) {
		/** Call this to see if your desired question is
		 *  marked as read later. After that, the user will
		 *  call the datamanager to get the question itself
		 */  
		List<UUID> rllist = getItems(readlaterfilename);
		if (rllist.contains(id)){
			return true; 
		} else {
			return false;
		}

	}
	
	
	public ArrayList<UUID> getItems(String filename) {
		//Supposed to return a list of favorite questions
		//We can then add to this list
		//Pull a list of favorited questions from SharedPrefs		
		Set<String> favset = prefs.getStringSet(filename, null);

		if (favset == null ){ //Only on first run of the app this will be null
			return new ArrayList<UUID>();
		}
		//Need to build up a new set of UUIDs before converting to List
		Set<UUID> intset = new HashSet<UUID>();
		for (String s : favset) {
			try {
				intset.add(UUID.fromString(s));
			} catch (NullPointerException e){
				Log.e("ClientData", "Error converting UUID from String to UUID");
			}
		}
		ArrayList<UUID> returnlist = new ArrayList<UUID>();
		returnlist.addAll(intset);
		return returnlist;
	}
	
	public void saveItems(List<UUID> list, String filename) {
		//Saves the list to sharedPreferences.
		
		Editor e = prefs.edit();
		Set<String> favset = new HashSet<String>();
		
		for (UUID i : list) {
			favset.add(i.toString());
		}
		e.putStringSet(filename, favset);
		e.commit();
	}

}
