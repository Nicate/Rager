package nl.tsfs.rager.model;


public class Event extends Persistable {
	private static final long serialVersionUID = 1408310938894959259L;
	
	private static final int MinimumNote = 0;
	private static final int MaximumNote = 127;
	
	private static final int MinimumVelocity = 0;
	private static final int MaximumVelocity = 127;
	
	
	public enum Type {
		On, Sustain, Off, Mode
	}
	
	
	private Type type;
	
	private int minimumNote;
	private int maximumNote;
	
	private int minimumVelocity;
	private int maximumVelocity;
	
	private Actions actions;
	
	
	public Event() {
		setType(Type.On);
		
		setMinimumNote(MinimumNote);
		setMaximumNote(MaximumNote);
		
		setMinimumVelocity(MinimumVelocity);
		setMaximumVelocity(MaximumVelocity);
		
		actions = new Actions();
	}
	
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	
	public int getMinimumNote() {
		return minimumNote;
	}
	
	public void setMinimumNote(int minimumNote) {
		this.minimumNote = minimumNote;
	}
	
	
	public int getMaximumNote() {
		return maximumNote;
	}
	
	public void setMaximumNote(int maximumNote) {
		this.maximumNote = maximumNote;
	}
	
	
	public int getMinimumVelocity() {
		return minimumVelocity;
	}
	
	public void setMinimumVelocity(int minimumVelocity) {
		this.minimumVelocity = minimumVelocity;
	}
	
	
	public int getMaximumVelocity() {
		return maximumVelocity;
	}
	
	public void setMaximumVelocity(int maximumVelocity) {
		this.maximumVelocity = maximumVelocity;
	}
	
	
	public Actions getActions() {
		return actions;
	}
}
