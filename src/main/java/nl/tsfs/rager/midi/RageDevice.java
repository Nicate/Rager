package nl.tsfs.rager.midi;


import java.util.*;

import javax.sound.midi.*;


public class RageDevice implements MidiDevice {
	private RageInfo info;
	
	private boolean isOpen;
	private boolean wasOpen;
	
	private ArrayList<Receiver> receivers;
	
	
	RageDevice(RageInfo info) {
		this.info = info;
		
		isOpen = false;
		wasOpen = false;
		
		receivers = new ArrayList<>();
	}
	
	
	@Override
	public boolean isOpen() {
		return isOpen;
	}
	
	@Override
	public void open() throws MidiUnavailableException {
		if(!wasOpen) {
			isOpen = true;
		}
		else {
			throw new MidiUnavailableException("Device has been closed.");
		}
	}
	
	@Override
	public void close() {
		isOpen = false;
		wasOpen = true;
		
		// Clone to prevent concurrent modification exceptions.
		for(Receiver receiver : new ArrayList<>(receivers)) {
			receiver.close();
		}
	}
	
	
	@Override
	public Info getDeviceInfo() {
		return info;
	}
	
	
	@Override
	public int getMaxReceivers() {
		return -1;
	}
	
	@Override
	public int getMaxTransmitters() {
		return 0;
	}
	
	
	@Override
	public long getMicrosecondPosition() {
		return -1L;
	}
	
	
	@Override
	public Receiver getReceiver() throws MidiUnavailableException {
		Receiver receiver = new RageReceiver(this);
		
		receivers.add(receiver);
		
		return receiver;
	}
	
	@Override
	public List<Receiver> getReceivers() {
		return new ArrayList<>(receivers);
	}
	
	
	@Override
	public Transmitter getTransmitter() throws MidiUnavailableException {
		throw new MidiUnavailableException("You are the one that has to rage!");
	}
	
	@Override
	public List<Transmitter> getTransmitters() {
		return new ArrayList<>();
	}
	
	
	void removeReceiver(Receiver receiver) {
		receivers.remove(receiver);
	}
}
