package nl.tsfs.rager;


import java.io.*;
import java.nio.charset.*;
import java.util.*;

import javax.swing.*;

import nl.tsfs.rager.ui.*;


public class Rager {
	public static void main(String[] args) {
		instance = new Rager();
		
		instance.start();
	}
	
	
	private static Rager instance;
	
	public static Rager getInstance() {
		return instance;
	}
	
	
	private static final String propertiesResource = "/resources/properties/rager.properties";
	
	private static final String defaultPropertyValue = "Unknown";
	
	
	private Properties properties;
	
	
	private Rager() {
		properties = new Properties();
		
		try {
			properties.load(new InputStreamReader(getClass().getResourceAsStream(propertiesResource), StandardCharsets.UTF_8.name()));
		}
		catch(IOException exception) {
			System.err.println("Unable to load application properties.");
		}
	}
	
	
	private void start() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UserInterface userInterface = new UserInterface();
				
				userInterface.setVisible(true);
			}
		});
	}
	
	
	public String getName() {
		return properties.getProperty("name", defaultPropertyValue);
	}
	
	public String getVersion() {
		return properties.getProperty("version", defaultPropertyValue);
	}
	
	public String getDescription() {
		return properties.getProperty("description", defaultPropertyValue);
	}
	
	public String getAuthor() {
		return properties.getProperty("author", defaultPropertyValue);
	}
	
	public String getLicense() {
		return properties.getProperty("license", defaultPropertyValue);
	}
}
