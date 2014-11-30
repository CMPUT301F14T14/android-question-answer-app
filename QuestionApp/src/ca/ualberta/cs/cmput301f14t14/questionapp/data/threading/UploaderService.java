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
	
	//Had to make this static. Little bit of evil hackery to get "singleton"-like behavior.
	//See DataManager.java:startUploaderService() for the other half of this spaghetti.
	public static boolean isServiceAlreadyRunning = false;
	
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
		/* Set */ isServiceAlreadyRunning = true;
	}
	
	@Override
	public void onDestroy(){
		//Clean up.
		isServiceAlreadyRunning = false;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    // Flags is useful for services restarted. 
		// startId is for A unique integer representing this 
		//    specific request to start. Use with stopSelfResult(int)


		
		//We need to create a thread to run completeQueuedEvents in.
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
			while (true) {
				int queuesize = 0;
				queuesize = completeQueuedEvents();
				if (queuesize > 0) {
					//We've retried what we could, but there are still things left 
					//in the eventbus that we cannot do. 
					//It's okay to sleep and simply try again.
							//Arbitrary sleep time. Could be 1ms, but that would be equivalent to busy-waiting.
							try {
								Thread.sleep(20000);
							} catch (InterruptedException e) {
								// It's fine to be interrupted while sleeping.
							} 
						
						
				} else {
					//We have cleared out everything in the queue. This service no longer needs to exist.
					//(The service is recreated in EventBus.addEvent()).
					stopSelf();
				}
			}	
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

			/** There is quite a bit of fancy footwork below, so pay attention.
			 * 
			 *  We have a BlockingQueue with AbstractEvent elements
			 *  
			 *  [] [] [] [] []
			 *  H      T     T'
			 *  
			 *  Where H = head, T = tail, T' = tail after retrying events.
			 *  Recall the head is the oldest, and the tail is the youngest in a queue.
			 *  
			 *  We want the loop below to only run from H-->T. If we are offline, running through
			 *  the entire queue will lead to an infinite loop and a significant drain on battery life.
			 * 
			 */
			AbstractEvent youngestevent = eventbus.getYoungestEvent();
			
			while(!eventbus.getEventQueue().isEmpty() 
					&& !eventbus.getEventQueue().peek().equals(youngestevent) ){	//Sort circuit logic should prevent .peek() from failing.			
				try {
					//Take the event from the head of the queue 
					//(block this thread if needed), and retry it.
					eventbus.getEventQueue().take().retry(dm);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			// Return number of elements still in queue
			return eventbus.getEventQueue().size();	
			
		}		
	}
	
	




	
}
