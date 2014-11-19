package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.util.UUID;

import android.content.Context;

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
		if (remoteDataStore.hasAccess()){
			q = remoteDataStore.getQuestion(id);
		}
		if (q == null) {
			//We could not access the question online.
			return localDataStore.getQuestion(id);
		}
		localDataStore.putQuestion(q);
	  	localDataStore.save();
	  	return q;
	}
	
	@Override
	protected void onPostExecute(Question question) {
		if (callback == null) {
			return;
		}
		callback.run(question);
	}
	

}
