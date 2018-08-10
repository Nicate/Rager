package nl.tsfs.rager.model;


public interface Container<T> extends Iterable<T> {
	public void contain(T contained);
}
