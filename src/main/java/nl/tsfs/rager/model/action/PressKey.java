package nl.tsfs.rager.model.action;


import java.awt.event.*;

import nl.tsfs.rager.model.*;


public class PressKey extends Action {
	private static final long serialVersionUID = -4103948280914003616L;
	
	
	private int code;
	
	
	public PressKey() {
		code = KeyEvent.VK_ENTER;
	}
	
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
}
