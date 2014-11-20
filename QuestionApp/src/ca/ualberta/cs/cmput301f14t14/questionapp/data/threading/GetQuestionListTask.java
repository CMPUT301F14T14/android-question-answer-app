package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetQuestionListTask extends AbstractDataManagerTask<Void, Void, List<Question>> {

	public GetQuestionListTask(Context c) {
		super(c);
	}

	@Override
	protected List<Question> doInBackground(Void... arg0) {
		List<Question> questionList = null;
		IDataStore localDataStore = DataManager.getInstance(context).getLocalDataStore();
		IDataStore remoteDataStore = DataManager.getInstance(context).getRemoteDataStore();
		try {
			questionList = remoteDataStore.getQuestionList();
		} catch (IOException e) {
			Log.e("DataManager", "Failed to get data from network");
		}
		if (questionList == null) {
			try {
				questionList = localDataStore.getQuestionList();
			} catch (IOException e) {
				Log.e("DataManager", "Failed to get any question list");
			}
		}
		return questionList;
	}
	
	@Override
	protected void onPostExecute(List<Question> la) {
		if (callback == null){
			return;
		}
		callback.run(la);
	}

}
