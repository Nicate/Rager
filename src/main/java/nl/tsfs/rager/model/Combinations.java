package nl.tsfs.rager.model;


import java.util.*;


public class Combinations extends Persistable implements Iterable<Combination> {
	private static final long serialVersionUID = -6439850231931936750L;
	
	
	private ArrayList<Combination> combinations;
	
	
	Combinations() {
		combinations = new ArrayList<>();
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
