package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.DataManager;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.EventBus;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AbstractEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerCommentPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AnswerPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionCommentPushDelayedEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.QuestionPushDelayedEvent;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class UploaderService extends Service {

	private DataManager dm = null;
	private Context context = null;
	
	@Override
	public IBinder onBind(Intent intent) {
		// This service does not need to communicate with the rest of the app 
		// in our design, so it's okay to return null (according to docs)
		return null;
	}
	
	@Override
	public void onCreate() {
		//Set up class variables on creation of service
		context = getApplicationContext();
		dm = DataManager.getInstance(context);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    handleCommand(intent);
	    // We want this service to continue running until it is explicitly
	    // stopped, so return sticky.
	    return START_STICKY;
	}

	private synchronized void completeQueuedEvents(DataManager dm) {
		/* The singleton eventbus contains events that attempted to 
		 * be posted to the internet. If posting failed, an event was created
		 * on the eventbus. These queued events should regularly "tried again"
		 * so that we are as frequently as possible trying to update the internet
		 * with our new local information.
		 */
		EventBus eventbus = EventBus.getInstance();
		//For each event in the event bus, try and do it again.
		for (AbstractEvent e: eventbus.getEventQueue()){				
			/* Remove the current event from the eventbus. If "trying again" fails,
			 * it will happen in a separate thread, and it will again be added to the bus
			 */
			eventbus.removeEvent(e);
			
			if (e instanceof QuestionPushDelayedEvent) {
				//try pushing the question again
				dm.addQuestion(((QuestionPushDelayedEvent) e).q, null);
			}
			if (e instanceof AnswerPushDelayedEvent) {
				dm.addAnswer(((AnswerPushDelayedEvent) e).a);
			}
			if (e instanceof QuestionCommentPushDelayedEvent) {
				dm.addQuestionComment(((QuestionCommentPushDelayedEvent) e).qc);
			}
			if (e instanceof AnswerCommentPushDelayedEvent) {
				dm.addAnswerComment(((AnswerCommentPushDelayedEvent)e).ca);
			}
		}
	}








	
}
