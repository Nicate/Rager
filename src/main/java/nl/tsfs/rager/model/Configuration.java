package nl.tsfs.rager.model;


public class Configuration extends Persistable {
	private static final long serialVersionUID = 3714466867514132841L;
	
	private static final int MinimumChannel = 0;
	private static final int MaximumChannel = 15;
	
	
	private String name;
	
	private int minimumChannel;
	private int maximumChannel;
	
	private Events events;
	
	
	public Configuration() {
		setName("New Configuration");
		
		setMinimumChannel(MinimumChannel);
		setMaximumChannel(MaximumChannel);
		
		events = new Events();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getMinimumChannel() {
		return minimumChannel;
	}
	
	public void setMinimumChannel(int minimumChannel) {
		this.minimumChannel = minimumChannel;
	}
	
	
	public int getMaximumChannel() {
		return maximumChannel;
	}
	
	public void setMaximumChannel(int maximumChannel) {
		this.maximumChannel = maximumChannel;
	}
	
	
	public Events getEvents() {
		return events;
	}
}
