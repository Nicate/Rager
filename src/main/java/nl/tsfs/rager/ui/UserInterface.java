package nl.tsfs.rager.ui;


import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

import nl.tsfs.rager.*;
import nl.tsfs.rager.model.*;


public class UserInterface extends JFrame {
	private static final long serialVersionUID = 2548502086624175357L;
	
	private static final String[] iconResources = new String[] {
		"/resources/icons/icon16.png",
		"/resources/icons/icon24.png",
		"/resources/icons/icon32.png",
		"/resources/icons/icon48.png",
		"/resources/icons/icon64.png",
		"/resources/icons/icon256.png"
	};
	
	
	private Model model;
	
	private JMenuBar menuBar;
	private ContentPane contentPane;
	
	private boolean hasTrayIcon;
	private TrayIcon trayIcon;
	
	
	public UserInterface() {
		model = Model.getInstance();
		
		loadIcons();
		
		setTitle(Rager.getInstance().getName());
		
		menuBar = new MenuBar();
		setJMenuBar(menuBar);
		
		contentPane = new ContentPane();
		setContentPane(contentPane);
		
		setCloseOperation();
		
		pack();
		
		Rectangle screenBounds = getGraphicsConfiguration().getBounds();
		Rectangle windowBounds = getBounds();
		
		setLocation((screenBounds.width - windowBounds.width) / 2, (screenBounds.height - windowBounds.height) / 2);
	}
	
	
	private void loadIcons() {
		ArrayList<Image> icons = new ArrayList<>();
		
		for(String iconResource : iconResources) {
			try {
				URL url = getClass().getResource(iconResource);
				
				Image image = ImageIO.read(url);
				
				icons.add(image);
			}
			catch(Exception exception) {
				// Just skip this one.
			}
		}
		
		setIconImages(icons);
	}
	
	
	private void setCloseOperation() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		hasTrayIcon = model.getSettings().getCloseToTray() && SystemTray.isSupported() && !getIconImages().isEmpty();
		
		if(hasTrayIcon) {
			trayIcon = new TrayIcon(getIconImages().get(0), Rager.getInstance().getDescription());
			
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					SystemTray.getSystemTray().remove(trayIcon);
					
					setVisible(true);
				}
			});
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				if(hasTrayIcon) {
					try {
						setVisible(false);
						
						SystemTray.getSystemTray().add(trayIcon);
					}
					catch(AWTException exception) {
						exit();
					}
				}
				else {
					exit();
				}
			}
		});
	}
	
	
	public void exit() {
		contentPane.close();
		
		dispose();
	}
}
