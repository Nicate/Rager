package nl.tsfs.rager.model;


import java.util.*;


public class Combination extends Persistable {
	private static final long serialVersionUID = 5320616573248353431L;
	
	
	private HashMap<Integer, String> channels;
	
	
	public Combination() {
		channels = new HashMap<>();
	}
}
