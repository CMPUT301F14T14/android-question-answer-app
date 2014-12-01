package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.content.Context;
import android.util.Log;

public class AddQuestionTask extends AbstractDataManagerTask<Question, Void, Void>{

	public AddQuestionTask(Context c) {
		super(c);
	}

	@Override
	protected Void doInBackground(Question... qin) {
		Question q = qin[0]; // Ignore other questions inputted
		IDataStore remote = DataManager.getInstance(getContext()).getRemoteDataStore();
		IDataStore local = DataManager.getInstance(getContext()).getLocalDataStore();
		
		try {
			remote.putQuestion(q);
		} catch (IOException e) {
			//Need to check if this exact question is already queued for uploading. If not, 
			//then upload. Else, silently return.
			if (EventBus.getInstance().getEventQueue().contains(new QuestionPushDelayedEvent(q))){
				return null;
			}
			tryPushLater(new QuestionPushDelayedEvent(q));
		}
		try {
			// All questions created by the user should be saved locally
			local.putQuestion(q);
			local.save();
		} catch (IOException e) {
			Log.e("AddQuestionTask", "Failed to create question.");
		}
		return null;
	}
	


}
