import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class BSTSpellChecker extends SpellChecker {

	BinarySearchTree<String> wordList;
	
	public BSTSpellChecker() {
		wordList = new BinarySearchTree<>();
	}

	
	public void add(String s) {
		wordList.add(s);
	}
	
//	private void addPoorly(String s) {
//		//non-recursive
//		wordList.addPoorly(s);
//	}
	
	public boolean contains(String s) {
		//only returns true if we get a null back from find
		return (wordList.find(s) != null);
	}
	
//	private boolean containsPoorly(String s) {
//		//use non-recursive find
//		return (wordList.findPoorly(s) != null);
//	}
//	
//	//read from file line by line, use non-recursive addPoorly().
//	public void readWordListPoorly(File f) throws FileNotFoundException {
//		Scanner reader = new Scanner(f);
//		//just throw it all in there one by one, fam
//		while (reader.hasNext())
//			this.addPoorly(reader.nextLine());
//		reader.close();
//	}
	
	//wrapper for recursive method
	public void readWordList(File f) throws FileNotFoundException {
		Scanner reader = new Scanner(f);
		//can't really get middle word from file, so empty into arraylist first
		ArrayList<String> tempList = new ArrayList<>();
		while (reader.hasNextLine())
			tempList.add(reader.nextLine());
		reader.close();
		
		//kick off recursive add
		this.readWordList(tempList, 0, tempList.size()-1);
	}
	
	private void readWordList(ArrayList<String> list, int startIndex, int endIndex) {
		//was going to create new arrayList and send to next call, but that's not fast
		//just track start and end (inclusive) indexes instead.
		int size = (endIndex - startIndex) + 1;
		int middleIndex = startIndex + (size / 2);
		
		if (size == 1) //we've reached a bottom leaf
			this.add(list.get(startIndex));
		else if (size == 2) { //can't split into left and right, just finish it off
			this.add(list.get(startIndex));
			this.add(list.get(endIndex));
		}
		else {
			//3 and above should have a middle, left and right without issue
			//even numbers favor right side of middle with this setup but doesn't matter
			this.add(list.get(middleIndex)); //add middle of this portion of list
			this.readWordList(list, startIndex, middleIndex-1); //recurse to left side
			this.readWordList(list, middleIndex+1, endIndex); //recurse to right side
		}
	}
	
}
