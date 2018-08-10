package nl.tsfs.rager.midi;


import java.awt.*;
import java.io.*;

import javax.sound.midi.*;

import nl.tsfs.rager.model.*;
import nl.tsfs.rager.model.Event;
import nl.tsfs.rager.model.action.*;


public class RageReceiver implements MidiDeviceReceiver {
	private static final int ChannelVoiceNoteOn = 0x90;
	private static final int ChannelVoiceNoteOff = 0x80;
	private static final int ChannelModeControlChange = 0xB0;
	
	private static final int NumberOfChannels = 16;
	
	private static final int NumberOfNotes = 128;
	private static final int NumberOfControllers = 128;
	
	
	private RageDevice device;
	
	private int[] noteVelocities;
	private int[] controllerVelocities;
	
	private Object velocitiesLock;
	
	private Warehouse warehouse;
	
	
	RageReceiver(RageDevice device) throws MidiUnavailableException {
		this.device = device;
		
		noteVelocities = new int[NumberOfNotes];
		controllerVelocities = new int[NumberOfControllers];
		
		velocitiesLock = new Object();
		
		warehouse = Warehouse.getInstance();
		
		try {
			synchronized(warehouse.getLock()) {
				warehouse.load();
			}
		}
		catch(IOException exception) {
			throw new MidiUnavailableException("Model is not available.");
		}
		
		// TODO By making sure we can create robots here, we don't have to handle the exceptions everywhere else.
		try {
			new Robot();
		}
		catch(AWTException exception) {
			throw new MidiUnavailableException("Robot is not available.");
		}
	}
	
	
	@Override
	public MidiDevice getMidiDevice() {
		return device;
	}
	
	
	@Override
	public void close() {
		device.removeReceiver(this);
	}
	
	
	@Override
	public void send(MidiMessage message, long timeStamp) {
		if(device.isOpen()) {
			// Retrieve the MIDI message.
			int length = message.getLength();
			int status = message.getStatus();
			byte[] values = message.getMessage();
			
			// There is always an event.
			int event = getValue(values, 0);
			
			// TODO Print the MIDI message for debug purposes.
			StringBuilder builder = new StringBuilder();
			
			builder.append(length);
			builder.append("; ");
			
			builder.append(status);
			builder.append("; ");
			
			builder.append(String.format("0x%02X", event));
			
			for(int index = 1; index < length; index += 1) {
				int value = getValue(values, index);
				
				builder.append(", ");
				builder.append(String.format("%d", value));
			}
			
			System.out.println(builder.toString());
			
			// Handle the MIDI message.
			if(event >= ChannelVoiceNoteOn && event < ChannelVoiceNoteOn + NumberOfChannels) {
				// Parse the MIDI message.
				Event.Type type = Event.Type.On;
				int channel = event - ChannelVoiceNoteOn;
				int note = getValue(values, 1);
				int velocity = getValue(values, 2);
				
				// Cache the velocity.
				setNoteVelocity(note, velocity);
				
				// Perform the appropriate actions.
				// TODO Delay actions should not run on the MIDI thread.
				// TODO Actions should be queued so they always run sequentially.
				performActions(type, channel, note, velocity);
				
				// Provide the sustain.
				// TODO This runs a thread for every note. It might be better to use a single thread for all notes.
				new Thread(new Runnable() {
					@Override
					public void run() {
						// Make sure to check the cached velocity, so note off events can stop the sustain.
						while(getNoteVelocity(note) > 0) {
							// TODO Actions should be queued so they always run sequentially.
							performActions(Event.Type.Sustain, channel, note, velocity);
						}
					}
				}).start();
			}
			else if(event >= ChannelVoiceNoteOff && event < ChannelVoiceNoteOff + NumberOfChannels) {
				// Parse the MIDI message. Use the original velocity.
				Event.Type type = Event.Type.Off;
				int channel = event - ChannelVoiceNoteOff;
				int note = getValue(values, 1);
				int velocity = getNoteVelocity(note);
				
				// Reset the velocity.
				setNoteVelocity(note, 0);
				
				// Perform the appropriate actions.
				// TODO Delay actions should not run on the MIDI thread.
				// TODO Actions should be queued so they always run sequentially.
				performActions(type, channel, note, velocity);
			}
			else if(event >= ChannelModeControlChange && event < ChannelModeControlChange + NumberOfChannels) {
				// Parse the MIDI message.
				// TODO Currently includes the reserved channel mode messages (120-127).
				Event.Type type = Event.Type.Mode;
				int channel = event - ChannelModeControlChange;
				int note = getValue(values, 1);
				int velocity = getValue(values, 2);
				
				// Update the velocities.
				setControllerVelocity(note, velocity);
				
				// Perform the appropriate actions.
				// TODO Delay actions should not run on the MIDI thread.
				// TODO Actions should be queued so they always run sequentially.
				performActions(type, channel, note, velocity);
			}
		}
	}
	
	
	private int getValue(byte[] values, int index) {
		return values[index] & 0xFF;
	}
	
	
	private int getNoteVelocity(int note) {
		synchronized(velocitiesLock) {
			return noteVelocities[note];
		}
	}
	
	private void setNoteVelocity(int note, int velocity) {
		synchronized(velocitiesLock) {
			noteVelocities[note] = velocity;
		}
	}
	
	
	private int getControllerVelocity(int note) {
		return controllerVelocities[note];
	}
	
	private void setControllerVelocity(int controller, int velocity) {
		controllerVelocities[controller] = velocity;
	}
	
	
	private void performActions(Event.Type type, int channel, int note, int velocity) {
		synchronized(warehouse.getLock()) {
			Model model = warehouse.getModel();
			
			for(Configuration configuration : model.getConfigurations()) {
				if(channel >= configuration.getMinimumChannel() && channel <= configuration.getMaximumChannel()) {
					for(Event event : configuration.getEvents()) {
						if(type == event.getType()) {
							if(note >= event.getMinimumNote() && note <= event.getMaximumNote()) {
								if(velocity >= event.getMinimumVelocity() && velocity <= event.getMaximumVelocity()) {
									for(Action action : event.getActions()) {
										performAction(action, type, channel, velocity, note);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void performAction(Action action, Event.Type type, int channel, int note, int velocity) {
		// TODO Come up with something more generic.
		if(action instanceof Delay) {
			Delay delay = (Delay) action;
			
			delay(delay.getDuration());
		}
		else if(action instanceof PressKey) {
			PressKey pressKey = (PressKey) action;
			
			pressKey(pressKey.getCode());
		}
		else if(action instanceof ReleaseKey) {
			ReleaseKey releaseKey = (ReleaseKey) action;
			
			releaseKey(releaseKey.getCode());
		}
		else if(action instanceof TypeKey) {
			TypeKey typeKey = (TypeKey) action;
			
			pressKey(typeKey.getCode());
			releaseKey(typeKey.getCode());
		}
	}
	
	
	private void pressKey(int code) {
		getRobot().keyPress(code);
	}
	
	private void releaseKey(int code) {
		getRobot().keyRelease(code);
	}
	
	
	private void delay(int duration) {
		getRobot().delay(duration);
	}
	
	
	private Robot getRobot() {
		try {
			// Return a new robot every time to work around threading issues.
			// TODO After generalization robot usage will be even more distributed so we may have to do something like this anyways.
			return new Robot();
		}
		catch(AWTException exception) {
			// This should not occur, as we have managed to create a test Robot during construction.
			throw new Error();
		}
	}
}
