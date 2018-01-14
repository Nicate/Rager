package nl.tsfs.rager.model;


import java.util.*;


public class Configurations implements Iterable<Configuration> {
	private ArrayList<Configuration> configurations;
	
	
	public Configurations() {
		configurations = new ArrayList<>();
	}
	
	
	void load() {
		
	}
	
	void save() {
		
	}
	
	
	public boolean hasConfigurations() {
		return !configurations.isEmpty();
	}
	
	public int getNumberOfConfigurations() {
		return configurations.size();
	}
	
	
	public List<Configuration> getConfigurations() {
		return new ArrayList<>(configurations);
	}
	
	@Override
	public Iterator<Configuration> iterator() {
		return configurations.iterator();
	}
	
	
	public void addConfiguration(Configuration configuration) {
		configurations.add(configuration);
	}
	
	public void removeConfiguration(Configuration configuration) {
		configurations.remove(configuration);
	}
}
