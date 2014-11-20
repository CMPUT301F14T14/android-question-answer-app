package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetQuestionTask extends AbstractDataManagerTask<UUID, Void, Question> {

	public GetQuestionTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Question doInBackground(UUID... uuid) {
		UUID id = uuid[0];
		IDataStore remoteDataStore = DataManager.getInstance(this.getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(this.getContext())
				.getLocalDataStore();
		Question q = null;
		try {
			q = remoteDataStore.getQuestion(id);
			// Cache visited question
			localDataStore.putQuestion(q);
			localDataStore.save();
		} catch (IOException e) {
			//We could not access the question online.
			Log.d("GetQuestionTask", "Getting local record");
			try {
				return localDataStore.getQuestion(id);
			} catch (IOException e1) {
				Log.e("GetQuestionTask", "Failed to get question");
				e1.printStackTrace();
			}
		}
	  	return q;
	}
	

	

}
