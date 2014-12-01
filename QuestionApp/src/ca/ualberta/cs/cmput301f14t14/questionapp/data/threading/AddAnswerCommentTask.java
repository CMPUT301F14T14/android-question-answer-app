package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;

import android.content.Context;
import android.util.Log;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerCommentPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;

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
		
		// Get parent answer record
		Answer answer = null;
		GetAnswerTask aTask = new GetAnswerTask(getContext());
		answer = aTask.blockingRun(C.getParent());
		
		answer.addComment(C.getId());
		
		try {
			remoteDataStore.putAComment(C);
		} catch (IOException e) {
			if (EventBus.getInstance().getEventQueue().contains(new AnswerCommentPushDelayedEvent(C))){
				return null;
			}
			tryPushLater(new AnswerCommentPushDelayedEvent(C));
		}
		try {
			remoteDataStore.putAnswer(answer);
		} catch (IOException e) {
			tryPushLater(new AnswerPushDelayedEvent(answer));
		}
		try {
			localDataStore.putAComment(C);
			localDataStore.save();
		} catch (IOException e) {
			Log.e("AddAnswerCommentTask", "Failed to save comment record");
		}

		return null;
	}

}