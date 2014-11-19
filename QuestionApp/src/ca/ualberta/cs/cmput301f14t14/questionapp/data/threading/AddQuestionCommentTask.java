package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionCommentPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.content.Context;

public class AddQuestionCommentTask extends AbstractDataManagerTask<Comment<Question>, Void, Void> {

	public AddQuestionCommentTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Void doInBackground(Comment<Question>... arg0) {
		Comment<Question> C = arg0[0];
		IDataStore remoteDataStore = DataManager.getInstance(this.getContext())
				.getRemoteDataStore();
		IDataStore localDataStore = DataManager.getInstance(this.getContext())
				.getLocalDataStore();
		
		//Need to fetch the question.
		//Its okay to block a non-UI thread.
		
		
		GetQuestionTask gqt = new GetQuestionTask(getContext());
		Question question = gqt.blockingRun(C.getParent());
		
		question.addComment(C.getId());
		
		if(remoteDataStore.hasAccess()){
			remoteDataStore.putQComment(C);
			remoteDataStore.putQuestion(question);
			remoteDataStore.save();
		} else {
			//We are offline. Add failure to push onto eventbus.
			//Store into local datastore for now.
			//Can't have duplicate keys in a map, so we don't have to worry about doing this twice.
			localDataStore.putQComment(C);
			localDataStore.putQuestion(question);
			localDataStore.save();
			//Push to event bus
			EventBus.getInstance().addEvent(new QuestionCommentPushDelayedEvent(C));
		}
		return null;
	}

}