package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ICommentable;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetQuestionCommentTask extends AbstractDataManagerTask<UUID, Void, Comment<? extends ICommentable>> {

	public GetQuestionCommentTask(Context c) {
		super(c);
	}

	@Override
	protected Comment<Question> doInBackground(UUID... arg0) {
		UUID cid = arg0[0];
		IDataStore remoteDataStore = DataManager.getInstance(getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(getContext())
				.getLocalDataStore();
		
		Comment<Question> comment = null;
		try {
			comment = remoteDataStore.getQComment(cid);
			// Cache visited comment locally
			localDataStore.putQComment(comment);
			localDataStore.save();
		} catch (IOException e) {
			comment = localDataStore.getQComment(cid);
		}
		return comment;
	}

}

