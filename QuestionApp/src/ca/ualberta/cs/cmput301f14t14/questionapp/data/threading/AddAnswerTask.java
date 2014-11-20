package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.Callback;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.IDataStore;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

import android.content.Context;

public class AddAnswerTask extends AbstractDataManagerTask<Answer, Void, Void>{

	
	public AddAnswerTask(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Void doInBackground(Answer... args) {
		ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer ans = args[0];
		
		
		//Question question = DataManager.getInstance(context).getQuestion(ans.getParent());
		Question question = null;
		
		
		
		//Get the parent question
		GetQuestionTask gqt = new GetQuestionTask(getContext());
		question = gqt.blockingRun(ans.getParent());
		
		
		question.addAnswer(ans.getId());
		IDataStore remote = DataManager.getInstance(this.getContext())
				.getRemoteDataStore();
		IDataStore local = DataManager.getInstance(getContext()).getLocalDataStore();
		if(remote.hasAccess()){
			remote.putAnswer(ans);
			remote.putQuestion(question);
			remote.save();
		}else{
			//We are offline. Put the answer into local data store, and keep the task as incomplete
			local.putAnswer(ans);
			local.putQuestion(question);
			local.save();
		
			EventBus.getInstance().addEvent(new AnswerPushDelayedEvent(ans));
		}

		
		
		return null;
	}
	

}
