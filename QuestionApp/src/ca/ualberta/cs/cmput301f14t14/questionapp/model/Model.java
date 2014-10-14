package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

public abstract class Model {
	protected List<View> mViews;
	
	public Model() {
		mViews = new ArrayList<View>();
	}
	
	abstract public void registerView(View v);
	
	abstract public void unregisterView(View v);
	
	abstract public void notifyViews();

}
