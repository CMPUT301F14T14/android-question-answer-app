package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ICommentable;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;

public class GetAnswerCommentTask extends AbstractDataManagerTask<UUID, Void, Comment<? extends ICommentable>> {

	public GetAnswerCommentTask(Context c) {
		super(c);
	}

	@Override
	protected Comment<Answer> doInBackground(UUID... arg0) {
		UUID cid = arg0[0];
		IDataStore remoteDataStore = DataManager.getInstance(getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(getContext())
				.getLocalDataStore();
		
		Comment<Answer> comment = null;
		try {
			comment = remoteDataStore.getAComment(cid);
			// Cache visited comment locally
			localDataStore.putAComment(comment);
			localDataStore.save();
		} catch (IOException e) {
			comment = localDataStore.getAComment(cid);
		}
		return comment;
	}

}

