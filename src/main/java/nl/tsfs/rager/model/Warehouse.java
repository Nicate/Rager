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
	
	
	private static final String fileName = "model.json";
	
	
	private Model model;
	private final Object lock;
	
	private Gson gson;
	private File file;
	private String encoding;
	
	
	private Warehouse() {
		model = new Model();
		lock = new Object();
		
		gson = new Gson();
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
	
	
	public void load() throws IOException, JsonIOException, JsonSyntaxException {
		// Make sure we can load the file.
		ensureFile();
		
		// Load the model.
		try(Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding))) {
			model = gson.fromJson(reader, Model.class);
		}
		
		// Run the post-loading hooks.
		model.load();
	}
	
	public void save() throws IOException, JsonIOException {
		// Make sure we can save the file.
		ensureDirectory();
		
		// Run the pre-saving hooks.
		model.save();
		
		// Save the model.
		try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding))) {
			gson.toJson(model, writer);
		}
	}
	
	
	private void ensureDirectory() {
		File directory = file.getParentFile();
		
		if(!directory.exists()) {
			directory.mkdirs();
		}
	}
	
	private void ensureFile() {
		if(!file.exists()) {
			// TODO Copy a default model.json from the JAR instead.
			try {
				save();
			}
			catch(Exception exception) {
				// Tough luck.
			}
		}
	}
}
