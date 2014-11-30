package ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.content.Intent;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AbstractEvent;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.threading.UploaderService;

public class EventBus {
	//This is a singleton event bus that contains events.
	private static final EventBus bus = new EventBus();
	
	private AbstractEvent youngestevent = null;
	
	public static EventBus getInstance() {
		return bus;
	}
	
	public synchronized void addEvent(AbstractEvent e){ 
		//Need addEvent to be a synchronized event (one thread at a time) because youngestevent
		//needs to be consistent.		
		try {
			queue.put(e);
			youngestevent = e;
		} catch (InterruptedException e1) {
			// queue.put() blocks. However, it could be interrupted.
			//We don't want Tasks up the good chain to have to deal with this.
			e1.printStackTrace();
		}
	}
	
	public AbstractEvent getYoungestEvent() {
		//Get the item last added to the queue
		return youngestevent;
	}
	
	public BlockingQueue<AbstractEvent> getEventQueue() {
		return queue;
	}
	
	//Need a list of events
	BlockingQueue<AbstractEvent> queue = new LinkedBlockingQueue<AbstractEvent>();
}
