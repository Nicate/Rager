package nl.tsfs.rager.model;


import java.util.*;


public class Configurations extends Persistable implements Container<Configuration> {
	private static final long serialVersionUID = 1929875488589259081L;
	
	
	private ArrayList<Configuration> configurations;
	
	
	Configurations() {
		configurations = new ArrayList<>();
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
	
	
	@Override
	public void contain(Configuration contained) {
		addConfiguration(contained);
	}
	
	
	public void addConfiguration(Configuration configuration) {
		configurations.add(configuration);
	}
	
	public void removeConfiguration(Configuration configuration) {
		configurations.remove(configuration);
	}
}
