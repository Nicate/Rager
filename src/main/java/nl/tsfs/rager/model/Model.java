package nl.tsfs.rager.model;


public class Model extends Persistable {
	private static final long serialVersionUID = 4978136529616900931L;
	
	private Combinations combinations;
	private Configurations configurations;
	private Settings settings;
	
	
	Model() {
		combinations = new Combinations();
		configurations = new Configurations();
		settings = new Settings();
	}
	
	
	public Combinations getCombinations() {
		return combinations;
	}
	
	public Configurations getConfigurations() {
		return configurations;
	}
	
	public Settings getSettings() {
		return settings;
	}
}
