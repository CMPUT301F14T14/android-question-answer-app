package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.view.IView;

public abstract class Model {
	protected transient List<IView> mViews;
	
	public Model() {
		mViews = new ArrayList<IView>();
	}

	/**
	 * Register a view with the model
	 * @param v
	 */
	public void registerView(IView v) {
		if (!mViews.contains(v)) {
			mViews.add(v);
		}
	}

	/**
	 * Unregister a view with the model
	 * @param v
	 */
	public void unregisterView(IView v) {
		mViews.remove(v);
	}

	/**
	 * Notify all registered views to update
	 */
	public void notifyViews() {
		for (IView v: mViews) {
			v.update();
		}
	}

}
