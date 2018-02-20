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
	
	private JComboBox<MidiDevice.Info> midiDeviceInfosComboBox;
	
	
	public ContentPane() {
		// Initialize the state.
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
		
		// Initialize the UI.
		midiDeviceInfosComboBox = new JComboBox<>();
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
		
		JLabel midiDeviceInfosLabel = new JLabel("MIDI device:");
		midiDeviceInfosLabel.setLabelFor(midiDeviceInfosComboBox);
		
		JButton midiDeviceInfoButton = new JButton("Refresh");
		midiDeviceInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				refreshMidiDeviceInfos();
				
				// The combo box currently dictates the width of the window.
				SwingUtilities.getWindowAncestor(ContentPane.this).pack();
			}
		});
		
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
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(midiDeviceInfoButton))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(midiDeviceInfosLabel)
						.addComponent(midiDeviceInfosComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(midiDeviceInfoButton))
					.addContainerGap())
		);
		setLayout(groupLayout);
		// @formatter:on
		
		// Initialize the contents.
		loadModel();
		
		refreshMidiDeviceInfos();
	}
	
	
	private void refreshMidiDeviceInfos() {
		// Clear the combo box. This deselects (and hence closes) the currently open MIDI device.
		midiDeviceInfosComboBox.removeAllItems();
		
		// Refresh the MIDI devices.
		loadRageDeviceInfo();
		loadMidiDeviceInfos();
		
		// Set up the combo box.
		if(hasMidiDeviceInfos) {
			// Find the name of the configured MIDI device.
			String midiDeviceInfoName = null;
			
			synchronized(warehouse.getLock()) {
				midiDeviceInfoName = warehouse.getModel().getSettings().getMidiDeviceInfoName();
			}
			
			// Populate the combo box. This selects (and hence opens) the first MIDI device.
			// It also saves the selection (which is useful if there is there is no configured
			// MIDI device or if the configured MIDI device is not available).
			for(MidiDevice.Info midiDeviceInfo : midiDeviceInfos) {
				midiDeviceInfosComboBox.addItem(midiDeviceInfo);
			}
			
			// Now we can switch to the configured MIDI device (if it is available).
			// This also saves the selection (overwriting the previously saved default).
			// This seems a little excessive, but it saves a lot of code plus we don't
			// have to introduce special cases with state into the damn listener :P
			for(MidiDevice.Info midiDeviceInfo : midiDeviceInfos) {
				if(midiDeviceInfo.getName().equals(midiDeviceInfoName)) {
					midiDeviceInfosComboBox.setSelectedItem(midiDeviceInfo);
				}
			}
			
			// There are now MIDI devices to choose from.
			midiDeviceInfosComboBox.setEnabled(true);
		}
		else {
			// There are no MIDI devices to choose from.
			midiDeviceInfosComboBox.setEnabled(false);
		}
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
