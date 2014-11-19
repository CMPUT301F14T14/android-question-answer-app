package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import android.content.Context;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerCommentPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class AddAnswerCommentTask extends AbstractDataManagerTask<Comment<Answer>, Void, Void> {

	public AddAnswerCommentTask(Context c) {
		super(c);
	}

	@Override
	protected Void doInBackground(Comment<Answer>... arg0) {
		Comment<Answer> C = arg0[0];
		IDataStore remoteDataStore = DataManager.getInstance(this.getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(this.getContext())
				.getLocalDataStore();
		
		Answer answer = null;
		GetAnswerTask gat = new GetAnswerTask(getContext());
		gat.setCallBack(null);
		
		try { 
			answer = gat.execute(C.getParent()).get();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		answer.addComment(C.getId());
		if(remoteDataStore.hasAccess()){
			//We are online, make it so
			remoteDataStore.putAComment(C);
			remoteDataStore.putAnswer(answer);
			remoteDataStore.save();
		}
		else{
			//We are offline. Need to post to Local DataStore.
			localDataStore.putAComment(C);
			localDataStore.putAnswer(answer);
			localDataStore.save();
			//Log failure to post onto EventBus
			EventBus.getInstance().addEvent(new AnswerCommentPushDelayedEvent(C));
		}

		return null;
	}

}