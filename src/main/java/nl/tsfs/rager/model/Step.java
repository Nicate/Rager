package nl.tsfs.rager.model;


public abstract class Step extends Persistable {
	private static final long serialVersionUID = 2505847013687290383L;
	
	
	Step() {
		// Private constructor.
	}
	
	
	public abstract void perform();
}
