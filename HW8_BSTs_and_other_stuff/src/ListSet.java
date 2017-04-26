/*
 * Implements the Set<E> interface using a java.util.ArrayList as the
 * underlying data structure.
 * 
 */
import java.util.ArrayList;

public class ListSet<E> implements Set<E> {
	private ArrayList<E> data = new ArrayList<>();

	// Big-O: O(n) since contains requires a linear search of the array.
	@Override
	public boolean contains(E someItem) {
		return data.contains(someItem);
	}

	// Big-O: O(n) due to the call to contains.
	@Override
	public void add(E newItem) {
		if (!contains(newItem))	// prevent adding a duplicate of newItem
			data.add(newItem);
	}

	// Big-O: O(n) since remove has to search the array to find someItem
	@Override
	public void remove(E someItem) {
		data.remove(someItem);
	}
	
	
	//These two actually HW8, oops
	public ListSet<E> union(ListSet<E> s) {
		ListSet<E> out = new ListSet<>();
		//foreach through the arraylists and add all to new ListSet
		for (E e : this.data)
			out.add(e);
		for(E e : s.data)
			out.add(e);
		return out;
	}
	
	public ListSet<E> intersection(ListSet<E> s) {
		ListSet<E> out = new ListSet<>();
		//only one foreach should be necessary, we'll have already seen all data that intersects
		for (E e : data)
			if (s.contains(e))
				out.add(e);
		return out;
	}
	
	public String toString() { 
		String out = "{";
		for (E e : this.data) {
			out += "" + e;
			//if this isn't the last item in the array, append comma
			if (!e.equals(data.get(data.size()-1)))
				out += ", ";
		}
		out += "}";
		return out;
	}
	
	public static void main(String[] args) {
		String[] test1 = {"1", "3", "5", "9"};
		String[] test2 = {"3", "6", "7", "9"};
		ListSet<String> one = new ListSet<>();
		ListSet<String> two = new ListSet<>();
		
		for (String s : test1)
			one.add(s);
		for (String s : test2)
			two.add(s);
		
		System.out.println("list 1: " + one);
		System.out.println("list 2: " + two);
		System.out.println("union: " + one.union(two));
		System.out.println("intersection" + one.intersection(two));
		
		
	}
}
