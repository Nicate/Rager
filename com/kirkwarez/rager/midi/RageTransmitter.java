package com.kirkwarez.rager.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDeviceTransmitter;
import javax.sound.midi.Receiver;

/**
 * TODO This class doesn't really do anything. I could probably send stuff to my LaunchKey to make it light up, if I knew the API.
 */
public class RageTransmitter implements MidiDeviceTransmitter {
	private RageDevice device;
	
	private Receiver receiver;
	
	
	RageTransmitter(RageDevice device) {
		this.device = device;
		
		receiver = null;
	}
	
	
	@Override
	public void close() {
		device.removeTransmitter(this);
	}
	
	
	@Override
	public Receiver getReceiver() {
		return receiver;
	}
	
	@Override
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	
	
	@Override
	public MidiDevice getMidiDevice() {
		return device;
	}
}
