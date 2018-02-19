package nl.tsfs.rager.model;


public class Settings extends Persistable {
	private static final long serialVersionUID = 7427810269233803696L;
	
	
	private String midiDeviceInfoName;
	
	private boolean closeToTray;
	
	
	Settings() {
		midiDeviceInfoName = null;
		
		closeToTray = false;
	}
	
	
	public String getMidiDeviceInfoName() {
		return midiDeviceInfoName;
	}
	
	public void setMidiDeviceInfoName(String midiDeviceInfoName) {
		this.midiDeviceInfoName = midiDeviceInfoName;
	}
	
	
	public boolean getCloseToTray() {
		return closeToTray;
	}
	
	public void setCloseToTray(boolean closeToTray) {
		this.closeToTray = closeToTray;
	}
}
