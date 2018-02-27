package nl.tsfs.rager.model.action;


import java.awt.event.*;

import nl.tsfs.rager.model.*;


public class ReleaseKey extends Action {
	private static final long serialVersionUID = -4103948280914003616L;
	
	
	private int code;
	
	
	public ReleaseKey() {
		code = KeyEvent.VK_ENTER;
	}
}
