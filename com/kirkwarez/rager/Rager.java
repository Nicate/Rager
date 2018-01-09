package com.kirkwarez.rager;

import java.awt.AWTException;
import java.awt.Robot;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public class Rager {
	public static void main(String[] args) {
		try {
			Robot robot = new Robot();
			
			MidiDevice.Info deviceInfo = null;
			MidiDevice.Info ragerInfo = null;
			
			for(MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
				System.out.println(info.getName());
				
				// TODO Change this to your device name (run it once to see a printed list).
				if(info.getName().equals("Launchkey Mini")) {
					deviceInfo = info;
				}
				
				if(info.getName().equals("Rager")) {
					ragerInfo = info;
				}
			}
			
			try(MidiDevice device = MidiSystem.getMidiDevice(deviceInfo); MidiDevice rager = MidiSystem.getMidiDevice(ragerInfo)) {
				device.open();
				rager.open();
				
				System.out.println("now open for business");
				
				Receiver receiver = rager.getReceiver();
				
				Transmitter transmitter = device.getTransmitter();
				
				transmitter.setReceiver(receiver);
				
				// TODO Change this if you want more time to play.
				while(device.getMicrosecondPosition() < 1000000 * 10) {
					robot.delay(1000);
				}
				
				receiver.close();
				transmitter.close();
				
				device.close();
				
				System.out.println("now closed for business");
			}
		}
		catch(AWTException exception) {
			exception.printStackTrace();
		}
		catch(MidiUnavailableException exception) {
			exception.printStackTrace();
		}
	}
}
