package nl.tsfs.rager.ui;


import java.awt.event.*;

import javax.swing.*;


public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = -4797368779787938506L;
	
	
	public MenuBar() {
		JMenu fileMenu = new JMenu("File");
		add(fileMenu);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				UserInterface userInterface = (UserInterface) SwingUtilities.getWindowAncestor(MenuBar.this);
				
				userInterface.exit();
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenu helpMenu = new JMenu("Help");
		add(helpMenu);
		
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				About about = new About();
				
				about.setVisible(true);
			}
		});
		helpMenu.add(aboutMenuItem);
	}
}
