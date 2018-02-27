package nl.tsfs.rager.robot;


import java.awt.*;


public class RageRobot {
	private Robot robot;
	
	
	public RageRobot() throws Exception {
		try {
			robot = new Robot();
		}
		catch(AWTException exception) {
			throw new Exception("Robot is not available.");
		}
	}
}
