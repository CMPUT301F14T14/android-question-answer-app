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
	    // Flags is useful for services restarted. 
		// startId is for A unique integer representing this 
		//    specific request to start. Use with stopSelfResult(int)
		
		//TODO: We should allow this service to be started only once. Allowing multiple things
		//to start this service is evil. 
		
		//Next, we need to create a thread to run completeQueuedEvents in.
		(new Thread(new QueueClearer())).start();
		
	    // We want this service to continue running until it is explicitly
	    // stopped, so return sticky.
	    return START_STICKY;
	}

	/**
	 * Private inner class that implements Runnable. This is needed so that
	 * we can create a thread above (within the service). This service itself
	 * should not implement Runnable as we don't want people willy-nilly running
	 * and Android service in a thread. The service itself should exist in the UI
	 * thread and run its own stuff in other threads.
	 * @author Stefan
	 *
	 */
	private class QueueClearer implements Runnable {

		@Override
		public void run() {
			//In here we can have the logic for the thread itself. 
			//(Such as checking for connectvitiy, sleeping, etc.)
			completeQueuedEvents();
			
		}

		private synchronized int completeQueuedEvents() {
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
				 * it will happen in a the AsyncTask threadpool, and it will again be added to the bus
				 */
				eventbus.removeEvent(e);
				e.retry(dm); //dm is from the outer class.
				
			}
			
			// Return number of elements still in queue
			return eventbus.getEventQueue().size();
			
			
		}		
	}
	
	




	
}
