package nl.tsfs.rager.midi;


import java.awt.*;
import java.awt.event.*;

import javax.sound.midi.*;


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


	@Override public MidiDevice getMidiDevice() {
		return device;
	}


	@Override public void close() {
		raging = false;

		if(shift) {
			robot.keyRelease(KeyEvent.VK_SHIFT);

			shift = false;
		}

		device.removeReceiver(this);
	}


	@Override public void send(MidiMessage message, long timeStamp) {
		if(device.isOpen()) {
			int length = message.getLength();
			int status = message.getStatus();
			byte[] values = message.getMessage();

			// There is always an event.
			int event = values[0] & 0xFF;

			StringBuilder builder = new StringBuilder();

			builder.append(length);
			builder.append("; ");

			builder.append(status);
			builder.append("; ");

			builder.append(String.format("0x%02X", event));

			for(int index = 1; index < length; index++) {
				int value = values[index] & 0xFF;

				builder.append(", ");
				builder.append(String.format("%d", value));
			}

			System.out.println(builder.toString());

			if(event >= 0x90 && event < 0xA0 && length == 3) {
				raging = true;

				int velocity = values[2] & 0xFF;

				if(velocity > 0x40) {
					robot.keyPress(KeyEvent.VK_SHIFT);

					shift = true;
				}

				sendToRobot("no");
			}

			new Thread(() -> {
				try {
					Robot robot = new Robot();

					robot.delay(150);

					while(raging) {
						sendToRobot("o");
						robot.delay(50);
					}
				}
				catch(Exception exception) {
					// Silent failure. Should not occur anyways, we managed to construct a Robot before.
				}
			}).start();

			if(event >= 0x80 && event < 0x90 && length == 3) {
				raging = false;

				sendToRobot("ob");

				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);

				if(shift) {
					robot.keyRelease(KeyEvent.VK_SHIFT);

					shift = false;
				}
			}
		}
	}

	private void sendToRobot(String string) {
		char[] characters = string.toCharArray();
		int length = characters.length;
		for(int i = 0; i < length; i++) {
			char character = characters[i];
			int event = getKeyEvent(character);

			robot.keyPress(event);
			robot.keyRelease(event);
		}
	}

	private int getKeyEvent(char c) {
		switch(c) {
			case 'a':
				return KeyEvent.VK_A;
			case 'b':
				return KeyEvent.VK_B;
			case 'c':
				return KeyEvent.VK_C;
			case 'd':
				return KeyEvent.VK_D;
			case 'e':
				return KeyEvent.VK_E;
			case 'f':
				return KeyEvent.VK_F;
			case 'g':
				return KeyEvent.VK_G;
			case 'h':
				return KeyEvent.VK_H;
			case 'i':
				return KeyEvent.VK_I;
			case 'j':
				return KeyEvent.VK_J;
			case 'k':
				return KeyEvent.VK_K;
			case 'l':
				return KeyEvent.VK_L;
			case 'm':
				return KeyEvent.VK_M;
			case 'n':
				return KeyEvent.VK_N;
			case 'o':
				return KeyEvent.VK_O;
			case 'p':
				return KeyEvent.VK_P;
			case 'q':
				return KeyEvent.VK_Q;
			case 'r':
				return KeyEvent.VK_R;
			case 's':
				return KeyEvent.VK_S;
			case 't':
				return KeyEvent.VK_T;
			case 'u':
				return KeyEvent.VK_U;
			case 'v':
				return KeyEvent.VK_V;
			case 'w':
				return KeyEvent.VK_W;
			case 'x':
				return KeyEvent.VK_X;
			case 'y':
				return KeyEvent.VK_Y;
			case 'z':
				return KeyEvent.VK_A;
			default:
				return 0;
		}

	}
}
