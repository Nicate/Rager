package nl.tsfs.rager.model;


import java.util.*;


public class Events extends Persistable implements Iterable<Event> {
	private static final long serialVersionUID = -9221670162661475607L;
	
	
	private ArrayList<Event> events;
	
	
	Events() {
		events = new ArrayList<>();
	}
	
	
	public boolean hasEvents() {
		return !events.isEmpty();
	}
	
	public int getNumberOfEvents() {
		return events.size();
	}
	
	
	public List<Event> getEvents() {
		return new ArrayList<>(events);
	}
	
	@Override
	public Iterator<Event> iterator() {
		return events.iterator();
	}
	
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void removeEvent(Event event) {
		events.remove(event);
	}
}
