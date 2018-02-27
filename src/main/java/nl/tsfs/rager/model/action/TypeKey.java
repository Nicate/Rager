package nl.tsfs.rager.model.action;


import java.awt.event.*;

import nl.tsfs.rager.model.*;


public class TypeKey extends Action {
	private static final long serialVersionUID = -4103948280914003616L;
	
	
	private int code;
	
	
	public TypeKey() {
		code = KeyEvent.VK_ENTER;
	}
}
