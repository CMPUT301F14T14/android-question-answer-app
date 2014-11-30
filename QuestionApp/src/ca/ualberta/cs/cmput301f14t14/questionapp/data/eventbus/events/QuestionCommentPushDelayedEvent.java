package ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Comment;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Question;

public class QuestionCommentPushDelayedEvent extends AbstractEvent {
	
	public Comment<Question> qc = null;
	
	public QuestionCommentPushDelayedEvent(Comment<Question> item) {
		qc = item;
	}

	@Override
	public void retry(DataManager dm) {
		dm.addQuestionComment(qc);
	}

}
