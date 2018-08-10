package nl.tsfs.rager.model;


public abstract class Action extends Persistable {
	private static final long serialVersionUID = 2505847013687290383L;
	
	
	private String type;
	
	
	protected Action() {
		// Private constructor.
		setType(getClass().getSimpleName());
	}
	
	
	public String getType() {
		return type;
	}
	
	private void setType(String type) {
		this.type = type;
	}
}
