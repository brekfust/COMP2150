/*
 * Demonstrates use of the built-in List<E> interface and ArrayList<E> class in java.util.
 */

import java.util.List;
import java.util.ArrayList;

public class BuiltInArrayList {

	public static void main(String[] args) {
		String[] stuffToAdd = {	"sloths",
								"not sloths",
								"sometimes sloths",
								"mostly sloths",
								"partially sloths"
		};
		
		List<String> someList = new ArrayList<>();
		for (String s : stuffToAdd)
			someList.add(s);	// see the API for all the methods that ArrayList supports

		System.out.println(someList);
	}
}
