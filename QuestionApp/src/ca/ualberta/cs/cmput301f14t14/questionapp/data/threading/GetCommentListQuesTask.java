package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetCommentListQuesTask extends AbstractDataManagerTask<Question, Void, List<Comment<Question>>> {

	public GetCommentListQuesTask(Context c) {
		super(c);
	}

	@Override
	protected List<Comment<Question>> doInBackground(Question... arg0) {
		Question question = arg0[0];
		List<Comment<Question>> commentList = null;
		IDataStore localDataStore = DataManager.getInstance(context).getLocalDataStore();
		IDataStore remoteDataStore = DataManager.getInstance(context).getRemoteDataStore();
		try {
			commentList = remoteDataStore.getQCommentList(question);
		} catch (IOException e) {
			Log.e("DataManager", "Failed to get data from network");
		}
		if (commentList == null) {
			try {
				commentList = localDataStore.getQCommentList(question);
			} catch (IOException e) {
				Log.e("DataManager", "Failed to get any comment list");
			}
		}
		return commentList;
	}
	

}
