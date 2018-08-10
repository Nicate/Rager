package nl.tsfs.rager.model.action;


import nl.tsfs.rager.model.*;


public class Delay extends Action {
	private static final long serialVersionUID = -3051146688303839325L;
	
	private int duration;
	
	
	public Delay() {
		duration = 0;
	}
	
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
