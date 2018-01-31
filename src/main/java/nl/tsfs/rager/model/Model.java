package nl.tsfs.rager.model;


import nl.tsfs.rager.json.*;


public class Model {
	private static Model instance = new Model();
	
	public static Model getInstance() {
		return instance;
	}
	
	
	private Combinations combinations;
	private Configurations configurations;
	private Settings settings;
	
	private GsonProvider gsonProvider;
	
	
	private Model() {
		combinations = new Combinations();
		configurations = new Configurations();
		settings = new Settings();
		
		gsonProvider = new GsonProvider();
		gsonProvider.register(Combination.class);
		gsonProvider.register(Combinations.class);
		gsonProvider.register(Configuration.class);
		gsonProvider.register(Configurations.class);
		gsonProvider.register(Event.class);
		gsonProvider.register(Model.class);
		gsonProvider.register(Sequence.class);
		gsonProvider.register(Settings.class);
		gsonProvider.register(Step.class);
		gsonProvider.create();
		
		load();
	}
	
	
	public void load() {
		
	}
	
	public void save() {
		
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
