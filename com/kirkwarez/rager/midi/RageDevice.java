package com.kirkwarez.rager.midi;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public class RageDevice implements MidiDevice {
	private RageInfo info;
	
	private boolean isOpen;
	private boolean wasOpen;
	
	private ArrayList<Receiver> receivers;
	private ArrayList<Transmitter> transmitters;
	
	
	RageDevice(RageInfo info) {
		this.info = info;
		
		isOpen = false;
		wasOpen = false;
		
		receivers = new ArrayList<>();
		transmitters = new ArrayList<>();
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
			throw new MidiUnavailableException();
		}
	}
	
	@Override
	public void close() {
		isOpen = false;
		wasOpen = true;
		
		for(Receiver receiver : receivers) {
			receiver.close();
		}
		
		for(Transmitter transmitter : transmitters) {
			transmitter.close();
		}
	}
	
	
	@Override
	public Info getDeviceInfo() {
		return info;
	}
	
	
	@Override
	public int getMaxReceivers() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public int getMaxTransmitters() {
		return Integer.MAX_VALUE;
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
		Transmitter transmitter = new RageTransmitter(this);
		
		transmitters.add(transmitter);
		
		return transmitter;
	}
	
	@Override
	public List<Transmitter> getTransmitters() {
		return new ArrayList<>(transmitters);
	}
	
	
	void removeReceiver(Receiver receiver) {
		receivers.remove(receiver);
	}
	
	void removeTransmitter(Transmitter transmitter) {
		transmitters.remove(transmitter);
	}
}
