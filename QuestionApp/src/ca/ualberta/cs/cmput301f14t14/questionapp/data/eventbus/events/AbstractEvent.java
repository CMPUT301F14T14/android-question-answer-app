package ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events;

import android.app.Activity;
import android.app.Application;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import android.content.Context;
public abstract class AbstractEvent {
	public abstract void retry(DataManager dm);
}
