package nl.tsfs.rager.midi;

import javax.sound.midi.MidiDevice.Info;

import nl.tsfs.rager.Rager;

public class RageInfo extends Info {
	public RageInfo() {
		super(Rager.getInstance().getName(), Rager.getInstance().getAuthor(), Rager.getInstance().getDescription(), Rager.getInstance().getVersion());
	}
}
