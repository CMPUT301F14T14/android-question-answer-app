package ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;

public class AnswerPushDelayedEvent extends AbstractEvent {

	public Answer a = null;
	
	public AnswerPushDelayedEvent(Answer ans) {
		a = ans;
	}

	@Override
	public void retry(DataManager dm) {
		dm.addAnswer(a);
	}
}
