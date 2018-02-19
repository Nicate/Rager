package nl.tsfs.rager.ui;


import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;

import nl.tsfs.rager.*;
import nl.tsfs.rager.model.*;


public class ContentPane extends JPanel {
	private static final long serialVersionUID = 7436816061624365132L;
	
	private static final String realTimeSequencerMidiDeviceInfoName = "Real Time Sequencer";
	
	
	private Warehouse warehouse;
	
	private boolean hasRageDeviceInfo;
	private boolean hasMidiDeviceInfos;
	
	private MidiDevice.Info rageDeviceInfo;
	private MidiDevice.Info[] midiDeviceInfos;
	
	private boolean hasRageDevice;
	private boolean hasMidiDevice;
	
	private MidiDevice rageDevice;
	private MidiDevice midiDevice;
	
	private boolean hasReceiver;
	private boolean hasTransmitter;
	
	private Receiver receiver;
	private Transmitter transmitter;
	
	
	public ContentPane() {
		warehouse = Warehouse.getInstance();
		
		hasRageDeviceInfo = false;
		hasMidiDeviceInfos = false;
		
		rageDeviceInfo = null;
		midiDeviceInfos = null;
		
		hasRageDevice = false;
		hasMidiDevice = false;
		
		rageDevice = null;
		midiDevice = null;
		
		hasReceiver = false;
		hasTransmitter = false;
		
		receiver = null;
		transmitter = null;
		
		loadRageDeviceInfo();
		loadMidiDeviceInfos();
		
		loadModel();
		
		JComboBox<MidiDevice.Info> midiDeviceInfosComboBox = new JComboBox<>(midiDeviceInfos);
		
		midiDeviceInfosComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.DESELECTED) {
					closeMidiDevice();
				}
				else if(event.getStateChange() == ItemEvent.SELECTED) {
					MidiDevice.Info midiDeviceInfo = (MidiDevice.Info) event.getItem();
					
					openMidiDevice(midiDeviceInfo);
					
					synchronized(warehouse.getLock()) {
						warehouse.getModel().getSettings().setMidiDeviceInfoName(midiDeviceInfo.getName());
					}
					
					saveModel();
				}
			}
		});
		
		if(hasMidiDeviceInfos) {
			// Make sure all state is correctly initialized.
			openMidiDevice((MidiDevice.Info) midiDeviceInfosComboBox.getSelectedItem());
			
			// Find the name of the configured MIDI device.
			String midiDeviceInfoName = null;
			
			synchronized(warehouse.getLock()) {
				midiDeviceInfoName = warehouse.getModel().getSettings().getMidiDeviceInfoName();
			}
			
			// Now we can switch to the configured MIDI device (if it is available).
			for(MidiDevice.Info midiDeviceInfo : midiDeviceInfos) {
				if(midiDeviceInfo.getName().equals(midiDeviceInfoName)) {
					midiDeviceInfosComboBox.setSelectedItem(midiDeviceInfo);
				}
			}
			
			// If we didn't switch (it wasn't configured or it wasn't available), save our initial state.
			MidiDevice.Info midiDeviceInfo = (MidiDevice.Info) midiDeviceInfosComboBox.getSelectedItem();
			
			synchronized(warehouse.getLock()) {
				warehouse.getModel().getSettings().setMidiDeviceInfoName(midiDeviceInfo.getName());
			}
			
			saveModel();
		}
		else {
			midiDeviceInfosComboBox.setEnabled(false);
		}
		
		JLabel midiDeviceInfosLabel = new JLabel("MIDI device:");
		midiDeviceInfosLabel.setLabelFor(midiDeviceInfosComboBox);
		
		// @formatter:off
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(midiDeviceInfosLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(midiDeviceInfosComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(midiDeviceInfosLabel)
						.addComponent(midiDeviceInfosComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		setLayout(groupLayout);
		// @formatter:on
	}
	
	
	private void loadRageDeviceInfo() {
		for(MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if(info.getName().equals(Rager.getInstance().getName())) {
				hasRageDeviceInfo = true;
				
				rageDeviceInfo = info;
			}
		}
	}
	
	
	private void loadMidiDeviceInfos() {
		ArrayList<MidiDevice.Info> infos = new ArrayList<>();
		
		for(MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			try {
				// Skip MIDI devices that can't transmit as well as the Java built-in sequencer.
				boolean isTransmitter = MidiSystem.getMidiDevice(info).getMaxTransmitters() != 0;
				boolean isRealTimeSequencer = info.getName().equals(realTimeSequencerMidiDeviceInfoName);
				
				if(isTransmitter && !isRealTimeSequencer) {
					infos.add(info);
				}
			}
			catch(MidiUnavailableException exception) {
				// Skip this one as well.
				JOptionPane.showMessageDialog(this, "Unable to retrieve information about MIDI device: " + info.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		midiDeviceInfos = infos.toArray(new MidiDevice.Info[infos.size()]);
		
		hasMidiDeviceInfos = !infos.isEmpty();
	}
	
	
	private void openMidiDevice(MidiDevice.Info midiDeviceInfo) {
		if(!hasRageDeviceInfo) {
			JOptionPane.showMessageDialog(this, "Unable to find MIDI device: " + Rager.getInstance().getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		try {
			rageDevice = MidiSystem.getMidiDevice(rageDeviceInfo);
			
			hasRageDevice = true;
		}
		catch(MidiUnavailableException exception) {
			JOptionPane.showMessageDialog(this, "Unable to acquire MIDI device: " + rageDeviceInfo.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		try {
			midiDevice = MidiSystem.getMidiDevice(midiDeviceInfo);
			
			hasMidiDevice = true;
		}
		catch(MidiUnavailableException exception) {
			JOptionPane.showMessageDialog(this, "Unable to acquire MIDI device: " + midiDeviceInfo.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		try {
			rageDevice.open();
		}
		catch(MidiUnavailableException exception) {
			JOptionPane.showMessageDialog(this, "Unable to open MIDI device: " + rageDeviceInfo.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		try {
			midiDevice.open();
		}
		catch(MidiUnavailableException exception) {
			JOptionPane.showMessageDialog(this, "Unable to open MIDI device: " + midiDeviceInfo.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		try {
			receiver = rageDevice.getReceiver();
			
			hasReceiver = true;
		}
		catch(MidiUnavailableException exception) {
			JOptionPane.showMessageDialog(this, "Unable to connect MIDI device: " + rageDeviceInfo.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		try {
			transmitter = midiDevice.getTransmitter();
			
			hasTransmitter = true;
		}
		catch(MidiUnavailableException exception) {
			JOptionPane.showMessageDialog(this, "Unable to connect MIDI device: " + midiDeviceInfo.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		transmitter.setReceiver(receiver);
	}
	
	private void closeMidiDevice() {
		if(hasTransmitter) {
			transmitter.close();
			
			transmitter = null;
			
			hasTransmitter = false;
		}
		
		if(hasReceiver) {
			receiver.close();
			
			receiver = null;
			
			hasReceiver = false;
		}
		
		if(hasMidiDevice) {
			midiDevice.close();
			
			midiDevice = null;
			
			hasMidiDevice = false;
		}
		
		if(hasRageDevice) {
			rageDevice.close();
			
			rageDevice = null;
			
			hasRageDevice = false;
		}
	}
	
	
	public void close() {
		closeMidiDevice();
	}
	
	
	private void loadModel() {
		try {
			synchronized(warehouse.getLock()) {
				warehouse.load();
			}
		}
		catch(IOException exception) {
			JOptionPane.showMessageDialog(this, "Unable to load configuration: " + exception.getMessage() + ".", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void saveModel() {
		try {
			synchronized(warehouse.getLock()) {
				warehouse.save();
			}
		}
		catch(IOException exception) {
			JOptionPane.showMessageDialog(this, "Unable to save configuration: " + exception.getMessage() + ".", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
