package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ICommentable;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetCommentListTask<T extends ICommentable> extends AbstractDataManagerTask<T, Void, List<Comment<T>>> {

	public GetCommentListTask(Context c) {
		super(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Comment<T>> doInBackground(T... arg0) {
		T parent = arg0[0];
		List<?> commentList = null;
		IDataStore localDataStore = DataManager.getInstance(context).getLocalDataStore();
		IDataStore remoteDataStore = DataManager.getInstance(context).getRemoteDataStore();
		try {
			if (parent instanceof Question)
				commentList = remoteDataStore.getCommentList((Question) parent);
			else if (parent instanceof Answer)
				commentList = remoteDataStore.getCommentList((Answer) parent);
		} catch (IOException e) {
			Log.e("DataManager", "Failed to get data from network");
		}
		if (commentList == null) {
			try {
				if (parent instanceof Question)
					commentList = localDataStore.getCommentList((Question) parent);
				else if (parent instanceof Answer)
					commentList = localDataStore.getCommentList((Answer) parent);
			} catch (IOException e) {
				Log.e("DataManager", "Failed to get any comment list");
			}
		}
		return (List<Comment<T>>) commentList;
	}
	

}
