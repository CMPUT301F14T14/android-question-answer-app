package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class GetQuestionCommentTask extends AbstractDataManagerTask<UUID, Void, Comment<Question>> {

	public GetQuestionCommentTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Comment<Question> doInBackground(UUID... arg0) {
		UUID cid = arg0[0];
		IDataStore remoteDataStore = DataManager.getInstance(this.getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(this.getContext())
				.getLocalDataStore();
		
		Comment<Question> comment;
		if(remoteDataStore.hasAccess()){
			comment = remoteDataStore.getQComment(cid);
			try {
				localDataStore.putQComment(comment);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  	localDataStore.save();
		}
		else{
			comment = localDataStore.getQComment(cid);
		}
		return comment;

		
	}

}

