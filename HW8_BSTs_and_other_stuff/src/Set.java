/*
 * Interface specifying the basic set operations.
 * 
 * A set is an unordered collection of unique objects.
 * 
 */
public interface Set<E> {
	// Returns whether someItem is contained within this set.
	boolean contains(E someItem);
	
	// Adds newItem to the set.  Does nothing if newItem already exists in the set.
	void add(E newItem);
	
	// Removes someItem from the set.  Does nothing if someItem is not in the set.
	void remove(E someItem);
}
