package nl.tsfs.rager.model;


/**
 * One should monitor this lock while accessing the model from the Event Dispatch Thread or the Java Sound MIDI Device Thread.
 */
public class Lock {
	private static Lock instance = new Lock();
	
	
	public static Lock getInstance() {
		return instance;
	}
	
	
	private Lock() {
		// Private constructor.
	}
}
