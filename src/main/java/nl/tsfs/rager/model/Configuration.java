package nl.tsfs.rager.model;


public class Configuration extends Persistable {
	private static final long serialVersionUID = 3714466867514132841L;
	
	
	private String name;
	
	private Events events;
	
	
	public Configuration() {
		setName("New Configuration");
		
		events = new Events();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Events getEvents() {
		return events;
	}
}
