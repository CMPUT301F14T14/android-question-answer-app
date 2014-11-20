package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.util.UUID;

import android.content.Context;

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
		UUID Aid = uuid[0];
		IDataStore remoteDataStore = DataManager.getInstance(this.getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(this.getContext())
				.getLocalDataStore();
		Answer answer = null;
		if (remoteDataStore.hasAccess()){
			answer = remoteDataStore.getAnswer(Aid);
		}
		if (answer == null) {
			//We were unable to fetch it online
			return localDataStore.getAnswer(Aid);
		} 
		//Got the answer online, save to local for reading earlier.
		localDataStore.putAnswer(answer);
	  	localDataStore.save();
	  	return answer;
	}
	


}
