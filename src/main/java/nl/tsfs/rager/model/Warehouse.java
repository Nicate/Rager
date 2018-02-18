package nl.tsfs.rager.model;


import java.io.*;
import java.nio.charset.*;

import com.google.gson.*;

import nl.tsfs.rager.*;


/**
 * A warehouse is like a factory, but it returns (roughly) the same instance every time ;)
 */
public class Warehouse {
	private static Warehouse instance = new Warehouse();
	
	public static Warehouse getInstance() {
		return instance;
	}
	
	
	private static final String defaultResource = "/resources/models/default.json";
	
	private static final String fileName = "model.json";
	
	
	private Model model;
	private final Object lock;
	
	private Gson gson;
	private File file;
	private String encoding;
	
	
	private Warehouse() {
		model = new Model();
		lock = new Object();
		
		gson = new GsonBuilder().setPrettyPrinting().create();
		file = new File(Rager.getInstance().getDirectory(), fileName);
		encoding = StandardCharsets.UTF_8.name();
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
	
	
	public void load() throws IOException {
		// Make sure we can load the file.
		ensureFile();
		
		// Load the model.
		model = loadModel(new FileInputStream(file));
	}
	
	public void save() throws IOException {
		// Make sure we can save the file.
		ensureFile();
		
		// Save the model.
		saveModel(model, new FileOutputStream(file));
	}
	
	
	private void ensureFile() throws IOException {
		// Make sure the file exists.
		if(!file.exists()) {
			// Make sure the directory exists.
			file.getParentFile().mkdirs();
			
			// Load the default model.
			Model defaultModel = loadModel(getClass().getResourceAsStream(defaultResource));
			
			// Save the default model as the application model.
			saveModel(defaultModel, new FileOutputStream(file));
		}
	}
	
	
	private Model loadModel(InputStream stream) throws IOException {
		Model modelToLoad;
		
		// Load the model.
		try(Reader reader = new BufferedReader(new InputStreamReader(stream, encoding))) {
			modelToLoad = gson.fromJson(reader, Model.class);
		}
		catch(JsonIOException | JsonSyntaxException exception) {
			throw new IOException("Could not load the JSON.", exception);
		}
		
		// Run the post-loading hooks.
		modelToLoad.load();
		
		return modelToLoad;
	}
	
	private void saveModel(Model modelToSave, OutputStream stream) throws IOException {
		// Run the pre-saving hooks.
		modelToSave.save();
		
		// Save the model.
		try(Writer writer = new BufferedWriter(new OutputStreamWriter(stream, encoding))) {
			gson.toJson(modelToSave, writer);
		}
		catch(JsonIOException exception) {
			throw new IOException("Could not save the JSON.", exception);
		}
	}
}
