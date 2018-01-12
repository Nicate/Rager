package nl.tsfs.rager.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import nl.tsfs.rager.Rager;

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
	
	
	private JMenuBar menuBar;
	private ContentPane contentPane;
	
	private boolean hasTrayIcon;
	private TrayIcon trayIcon;
	
	
	public UserInterface() {
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
		
		hasTrayIcon = SystemTray.isSupported() && !getIconImages().isEmpty();
		
		if(hasTrayIcon) {
			trayIcon = new TrayIcon(getIconImages().get(0),Rager.getInstance().getDescription());
			
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
