package nl.tsfs.rager.model;


import com.google.gson.*;


/**
 * A warehouse is like a factory, but it returns (roughly) the same instance every time ;)
 */
public class Warehouse {
	private static Warehouse instance = new Warehouse();
	
	
	public static Warehouse getInstance() {
		return instance;
	}
	
	
	private Model model;
	
	private final Object lock;
	
	private Gson gson;
	
	
	private Warehouse() {
		model = new Model();
		
		lock = new Object();
		
		gson = new Gson();
	}
	
	
	public Model getModel() {
		return model;
	}
	
	
	/**
	 * One should retrieve and monitor this lock while accessing the model
	 * from the Event Dispatch Thread or the Java Sound MIDI Device Thread.
	 */
	public Object getLock() {
		return lock;
	}
	
	
	public void load() {
		// TODO Load from file.
		String json = "";
		
		// Load the model.
		model = gson.fromJson(json, Model.class);
		
		// Run the post-loading hooks.
		model.load();
	}
	
	public void save() {
		// Run the pre-saving hooks.
		model.save();
		
		// Save the model.
		String json = gson.toJson(model);
		
		// TODO Save to file.
	}
}
