package ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f14t14.questionapp.data.eventbus.events.AbstractEvent;

public class EventBus {
	//This is a singleton event bus that contains events.
	private static final EventBus bus = new EventBus();
	
	public static EventBus getInstance() {
		return bus;
	}
	
	public void addEvent(AbstractEvent e){
		queue.add(e);
	}
	
	public void removeEvent(AbstractEvent e) {
		queue.remove(e);
	}
	
	public List<AbstractEvent> getEventQueue() {
		return queue;
	}
	
	//Need a list of events
	List<AbstractEvent> queue = new ArrayList<AbstractEvent>();
}
