package nl.tsfs.rager.midi;


import javax.sound.midi.*;
import javax.sound.midi.MidiDevice.*;
import javax.sound.midi.spi.*;


public class RageProvider extends MidiDeviceProvider {
	private RageInfo info;
	private Info[] infos;
	
	
	public RageProvider() {
		info = new RageInfo();
		infos = new Info[] {
			info
		};
	}
	
	
	@Override
	public Info[] getDeviceInfo() {
		return infos;
	}
	
	
	@Override
	public boolean isDeviceSupported(Info info) {
		return info.getName().equals(this.info.getName());
	}
	
	
	@Override
	public MidiDevice getDevice(Info info) {
		if(isDeviceSupported(info)) {
			return new RageDevice(this.info);
		}
		else {
			throw new IllegalArgumentException("This does not rage.");
		}
	}
}
