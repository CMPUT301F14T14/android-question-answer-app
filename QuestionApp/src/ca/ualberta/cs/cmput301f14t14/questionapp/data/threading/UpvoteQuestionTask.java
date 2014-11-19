package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;
import android.content.Context;

public class UpvoteQuestionTask extends AbstractDataManagerTask<Question, Void, Void> {

	public UpvoteQuestionTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Void doInBackground(Question... arg0) {
		Question q = arg0[0];
		IDataStore remoteDataStore = DataManager.getInstance(getContext()).getRemoteDataStore();
		IDataStore localDataStore  = DataManager.getInstance(getContext()).getLocalDataStore();
		
		if(remoteDataStore.hasAccess()){
			remoteDataStore.putQuestion(q);
		  	remoteDataStore.save();
		}
		else{
			//We are not online. Need to log this failure to the eventbus
			//We do not need to have list of "things to upvote online" and "questions
			// to push online". What actually happens is that the whole question is pushed
			//again anyways.
			EventBus.getInstance().addEvent(new QuestionPushDelayedEvent(q));
		}  
		//In both cases (online and not), update what we have locally
		localDataStore.putQuestion(q);
		localDataStore.save();
		return null;
	}

}
