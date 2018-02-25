package nl.tsfs.rager.model;


import java.util.*;


public class Steps extends Persistable implements Iterable<Step> {
	private static final long serialVersionUID = -3823948716054862067L;
	
	
	private LinkedList<Step> steps;
	
	
	Steps() {
		steps = new LinkedList<>();
	}
	
	
	public boolean hasSteps() {
		return !steps.isEmpty();
	}
	
	public int getNumberOfSteps() {
		return steps.size();
	}
	
	
	public List<Step> getSteps() {
		return new LinkedList<>(steps);
	}
	
	@Override
	public Iterator<Step> iterator() {
		return steps.iterator();
	}
	
	
	public void addStep(Step step) {
		steps.add(step);
	}
	
	public void removeStep(Step step) {
		steps.remove(step);
	}
}
