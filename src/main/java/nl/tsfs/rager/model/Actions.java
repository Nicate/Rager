package nl.tsfs.rager.model;


import java.util.*;


public class Actions extends Persistable implements Container<Action> {
	private static final long serialVersionUID = -3823948716054862067L;
	
	
	private LinkedList<Action> actions;
	
	
	Actions() {
		actions = new LinkedList<>();
	}
	
	
	public boolean hasActions() {
		return !actions.isEmpty();
	}
	
	public int getNumberOfActions() {
		return actions.size();
	}
	
	
	public List<Action> getActions() {
		return new LinkedList<>(actions);
	}
	
	@Override
	public Iterator<Action> iterator() {
		return actions.iterator();
	}
	
	
	@Override
	public void contain(Action contained) {
		addAction(contained);
	}
	
	
	public void addAction(Action action) {
		actions.add(action);
	}
	
	public void removeAction(Action action) {
		actions.remove(action);
	}
}
