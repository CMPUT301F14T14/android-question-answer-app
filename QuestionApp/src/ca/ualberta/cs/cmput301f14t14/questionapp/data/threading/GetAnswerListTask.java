package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetAnswerListTask extends AbstractDataManagerTask<Question, Void, List<Answer>> {

	public GetAnswerListTask(Context c) {
		super(c);
	}

	@Override
	protected List<Answer> doInBackground(Question... arg0) {
		Question question = arg0[0];
		List<Answer> answerList = null;
		IDataStore localDataStore = DataManager.getInstance(context).getLocalDataStore();
		IDataStore remoteDataStore = DataManager.getInstance(context).getRemoteDataStore();
		try {
			answerList = remoteDataStore.getAnswerList(question);
		} catch (IOException e) {
			Log.e("DataManager", "Failed to get data from network");
		}
		if (answerList == null) {
			try {
				answerList = localDataStore.getAnswerList(question);
			} catch (IOException e) {
				Log.e("DataManager", "Failed to get answer list");
			}
		}
		return answerList;
	}
	


}
