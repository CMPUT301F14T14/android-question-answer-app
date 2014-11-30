package ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Answer;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;

public class AnswerCommentPushDelayedEvent extends AbstractEvent {

	public Comment<Answer> ca = null;
	
	public AnswerCommentPushDelayedEvent(Comment<Answer> item) {
		ca = item;
	}

	@Override
	public void retry(DataManager dm) {
		dm.addAnswerComment(ca);
	}
	
}
