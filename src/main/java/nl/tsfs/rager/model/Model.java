package nl.tsfs.rager.model;


public class Model extends Persistable {
	private static final long serialVersionUID = 4978136529616900931L;
	
	private Configurations configurations;
	private Settings settings;
	
	
	Model() {
		configurations = new Configurations();
		settings = new Settings();
	}
	
	
	public Configurations getConfigurations() {
		return configurations;
	}
	
	public Settings getSettings() {
		return settings;
	}
}
