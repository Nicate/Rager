package nl.tsfs.rager.model;


public class Event extends Persistable {
	private static final long serialVersionUID = 1408310938894959259L;
	
	
	public enum Type {
		On, Sustain, Off,
	}
	
	
	private Type type;
	private int note;
	
	private Actions actions;
	
	
	public Event() {
		setType(Type.On);
		setNote(0);
		
		actions = new Actions();
	}
	
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	
	public int getNote() {
		return note;
	}
	
	public void setNote(int note) {
		this.note = note;
	}
	
	
	public Actions getActions() {
		return actions;
	}
}
