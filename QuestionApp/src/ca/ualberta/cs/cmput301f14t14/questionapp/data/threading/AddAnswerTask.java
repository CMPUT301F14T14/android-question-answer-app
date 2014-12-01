package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerPushDelayedEvent;

import android.content.Context;
import android.util.Log;

/**
 * This task updates or creates an answer record.
 * The user is responsible for ensuring that the related records are updated.
 */
public class AddAnswerTask extends AbstractDataManagerTask<Answer, Void, Void>{

	public AddAnswerTask(Context c) {
		super(c);
	}
	
	public AddAnswerTask(Context c, Callback<Void> callback) {
		super(c, callback);
	}

	@Override
	protected Void doInBackground(Answer... args) {
		IDataStore remote = DataManager.getInstance(getContext()).getRemoteDataStore();
		IDataStore local = DataManager.getInstance(getContext()).getLocalDataStore();
		Answer ans = args[0];

		// Save the answer record remotely
		try {
			remote.putAnswer(ans);
		} catch (IOException e) {
			Log.d("AddAnswerTask", "Failed to push answer remotely");
			if (EventBus.getInstance().getEventQueue().contains(new AnswerPushDelayedEvent(ans))){
				return null;
			}
			tryPushLater(new AnswerPushDelayedEvent(ans));
		}
		try {
			local.putAnswer(ans);
			local.save();
		} catch (IOException e) {
			Log.e("AddAnswerTask", "Failed to save answer record");
		}

		return null;
	}

}
