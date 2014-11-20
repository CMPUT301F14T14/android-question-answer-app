package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.util.UUID;

import android.content.Context;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;

public class GetAnswerCommentTask extends AbstractDataManagerTask<UUID, Void, Comment<Answer>> {

	public GetAnswerCommentTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Comment<Answer> doInBackground(UUID... arg0) {
		UUID Cid = arg0[0];
		IDataStore remoteDataStore = DataManager.getInstance(this.getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(this.getContext())
				.getLocalDataStore();
	
		Comment<Answer> comment;
		if(remoteDataStore.hasAccess()){
			comment = remoteDataStore.getAComment(Cid);
			localDataStore.putAComment(comment);
		  	localDataStore.save();
		}
		else{
			comment = localDataStore.getAComment(Cid);
		}
		return comment;
	
	
	
	}
	
	@Override
	protected void onPostExecute(Comment<Answer> ca) {
		if (callback == null){
			return;
		}
		callback.run(ca);
	}

}

