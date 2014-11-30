package ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class QuestionPushDelayedEvent extends AbstractEvent {
	
	//This is the question that needs to be pushed.
	public Question q = null;
	
	public QuestionPushDelayedEvent(Question question) {
		q = question;
	}

	@Override
	public void retry(DataManager dm) {
		dm.addQuestion(q, null);
	}
	

}
