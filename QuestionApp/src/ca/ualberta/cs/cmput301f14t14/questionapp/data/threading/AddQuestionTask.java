package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.content.Context;
import android.util.Log;

public class AddQuestionTask extends AbstractDataManagerTask<Question, Void, Boolean>{

	public AddQuestionTask(Context c) {
		super(c);
	}

	@Override
	protected Boolean doInBackground(Question... qin) {
		boolean remoteSuccess = false;
		Question q = qin[0]; // Ignore other questions inputted
		IDataStore remote = DataManager.getInstance(getContext()).getRemoteDataStore();
		IDataStore local = DataManager.getInstance(getContext()).getLocalDataStore();
		
		try {
			remote.putQuestion(q);
			remoteSuccess = true;
		} catch (IOException e) {
			EventBus.getInstance().addEvent(new QuestionPushDelayedEvent(q));
		}
		try {
			// All questions created by the user should be saved locally
			local.putQuestion(q);
			local.save();
		} catch (IOException e) {
			Log.e("AddQuestionTask", "Failed to create question.");
		}
		return remoteSuccess;
	}
	
	@Override
	protected void onPostExecute(Boolean finished) {
		if (callback == null) {
			return;
		}
		callback.run(finished);
	}

}
