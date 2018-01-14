package nl.tsfs.rager.model;


public class Settings {
	private String midiDeviceInfoName;
	
	private boolean closeToTray;
	
	
	public Settings() {
		midiDeviceInfoName = null;
		
		closeToTray = false;
	}
	
	
	void load() {
		
	}
	
	void save() {
		
	}
	
	
	public String getMidiDeviceInfoName() {
		return midiDeviceInfoName;
	}
	
	public boolean getCloseToTray() {
		return closeToTray;
	}
}
