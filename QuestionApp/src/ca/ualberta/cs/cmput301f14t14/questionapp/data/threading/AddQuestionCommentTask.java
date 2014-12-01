package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionCommentPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.content.Context;
import android.util.Log;

public class AddQuestionCommentTask extends AbstractDataManagerTask<Comment<Question>, Void, Void> {

	public AddQuestionCommentTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Void doInBackground(Comment<Question>... arg0) {
		Comment<Question> comment = arg0[0];
		IDataStore remoteDataStore = DataManager.getInstance(this.getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(this.getContext())
				.getLocalDataStore();
		
		// Push comment to remote server
		try {
			remoteDataStore.putQComment(comment);
		} catch (IOException e) {
			Log.e("AddQuestionCommentTask", "Failed to upload question comment");
			//Push to event bus
			if (EventBus.getInstance().getEventQueue().contains(new QuestionCommentPushDelayedEvent(comment))){
				return null;
			}
			tryPushLater(new QuestionCommentPushDelayedEvent(comment));
		}
		// Push comment to local store
		try {
			localDataStore.putQComment(comment);
			localDataStore.save();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("AddQuestionCommentTask", "Failed to save question comment locally");
		}
		return null;
	}

}