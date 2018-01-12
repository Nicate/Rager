package nl.tsfs.rager.midi;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDeviceReceiver;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;

public class RageReceiver implements MidiDeviceReceiver {
	private RageDevice device;
	
	private Robot robot;
	
	private volatile boolean raging;
	private boolean shift;
	
	
	RageReceiver(RageDevice device) throws MidiUnavailableException {
		this.device = device;
		
		try {
			robot = new Robot();
		}
		catch(AWTException exception) {
			throw new MidiUnavailableException("Robot is not available.");
		}
		
		raging = false;
		shift = false;
	}
	
	
	@Override
	public MidiDevice getMidiDevice() {
		return device;
	}
	
	
	@Override
	public void close() {
		raging = false;
		
		if(shift) {
			robot.keyRelease(KeyEvent.VK_SHIFT);
			
			shift = false;
		}
		
		device.removeReceiver(this);
	}
	
	
	@Override
	public void send(MidiMessage message, long timeStamp) {
		if(device.isOpen()) {
			int length = message.getLength();
			int status = message.getStatus();
			byte[] values = message.getMessage();
			
			int event = values[0] & 0xFF;
			int note = values[1] & 0xFF;
			int velocity = values[2] & 0xFF;
			
			StringBuilder builder = new StringBuilder();
			
			builder.append(length);
			builder.append("; ");
			
			builder.append(status);
			builder.append("; ");
			
			builder.append(String.format("0x%02X", event));
			builder.append(", ");
			builder.append(String.format("%d", note));
			builder.append(", ");
			builder.append(String.format("%d", velocity));
			
			System.out.println(builder.toString());
			
			if(event >= 0x90 && event < 0xA0) {
				raging = true;
				
				if(velocity > 0x40) {
					robot.keyPress(KeyEvent.VK_SHIFT);
					
					shift = true;
				}
				
				robot.keyPress(KeyEvent.VK_N);
				robot.keyRelease(KeyEvent.VK_N);
				robot.keyPress(KeyEvent.VK_O);
				robot.keyRelease(KeyEvent.VK_O);
			}
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Robot robot = new Robot();
						
						robot.delay(150);
						
						while(raging) {
							robot.keyPress(KeyEvent.VK_O);
							robot.keyRelease(KeyEvent.VK_O);
							
							robot.delay(50);
						}
					}
					catch(Exception exception) {
						// Silent failure. Should not occur anyways, we managed to construct a Robot before.
					}
				}
			}).start();
			
			if(event >= 0x80 && event < 0x90) {
				raging = false;
				
				robot.keyPress(KeyEvent.VK_O);
				robot.keyRelease(KeyEvent.VK_O);
				robot.keyPress(KeyEvent.VK_B);
				robot.keyRelease(KeyEvent.VK_B);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				
				if(shift) {
					robot.keyRelease(KeyEvent.VK_SHIFT);
					
					shift = false;
				}
			}
		}
	}
}
