package nl.tsfs.rager.model;


import java.util.*;


public class Combinations implements Iterable<Combination> {
	private ArrayList<Combination> combinations;
	
	
	public Combinations() {
		combinations = new ArrayList<>();
	}
	
	
	void load() {
		
	}
	
	void save() {
		
	}
	
	
	public boolean hasCombinations() {
		return !combinations.isEmpty();
	}
	
	public int getNumberOfCombinations() {
		return combinations.size();
	}
	
	
	public List<Combination> getCombinations() {
		return new ArrayList<>(combinations);
	}
	
	@Override
	public Iterator<Combination> iterator() {
		return combinations.iterator();
	}
	
	
	public void addCombination(Combination combination) {
		combinations.add(combination);
	}
	
	public void removeCombination(Combination combination) {
		combinations.remove(combination);
	}
}
