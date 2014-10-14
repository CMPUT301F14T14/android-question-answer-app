package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.view.IView;

public abstract class Model {
	protected List<IView> mViews;
	
	public Model() {
		mViews = new ArrayList<IView>();
	}
	
	abstract public void registerView(IView v);
	
	abstract public void unregisterView(IView v);
	
	public void notifyViews() {
		for (IView v: mViews) {
			v.update();
		}
	}

}
