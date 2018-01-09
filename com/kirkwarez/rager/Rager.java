package com.kirkwarez.rager;

import javax.swing.SwingUtilities;

import com.kirkwarez.rager.ui.UserInterface;

public class Rager {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UserInterface userInterface = new UserInterface();
				
				userInterface.setVisible(true);
			}
		});
	}
	
	
	public static String getApplicationName() {
		return "Rager";
	}
	
	public static String getApplicationVersion() {
		return "1.0.0";
	}
	
	public static String getApplicationDescription() {
		return "Rage, RAGE, against the dying of the light.";
	}
	
	public static String getApplicationAuthor() {
		return "Kirk";
	}
	
	public static String getApplicationLicense() {
		return "Copyright Kirk 2017. All rights reserved.";
	}
}
