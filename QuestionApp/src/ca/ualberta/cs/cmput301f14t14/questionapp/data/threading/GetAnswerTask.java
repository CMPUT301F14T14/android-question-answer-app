package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;

public class GetAnswerTask extends AbstractDataManagerTask<UUID, Void, Answer> {

	public GetAnswerTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Answer doInBackground(UUID... uuid) {
		UUID id = uuid[0];
		IDataStore remoteDataStore = DataManager.getInstance(this.getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(this.getContext())
				.getLocalDataStore();
		Answer a = null;
		try {
			a = remoteDataStore.getAnswer(id);
			// Cache visited question
			localDataStore.putAnswer(a);
			localDataStore.save();
		} catch (IOException e) {
			//We could not access the question online.
			Log.d("GetAnswerTask", "Getting local record");
			try {
				return localDataStore.getAnswer(id);
			} catch (IOException e1) {
				Log.e("GetAnswerTask", "Failed to get answer");
				e1.printStackTrace();
			}
		}
	  	return a;
	}
	


}
