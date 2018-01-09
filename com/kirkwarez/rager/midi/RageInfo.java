package com.kirkwarez.rager.midi;

import javax.sound.midi.MidiDevice.Info;

import com.kirkwarez.rager.Rager;

public class RageInfo extends Info {
	public RageInfo() {
		super(Rager.getApplicationName(), Rager.getApplicationAuthor(), Rager.getApplicationDescription(), Rager.getApplicationVersion());
	}
}
