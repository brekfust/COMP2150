import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BSTSpellChecker extends SpellChecker {

	BinarySearchTree<String> wordList;
	
	public BSTSpellChecker() {
		wordList = new BinarySearchTree<>();
	}

	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO use this for testing and delete at the end
		File testFile = new File("Project3_wordlist.txt");
		BSTSpellChecker fun = new BSTSpellChecker();
		//fun.readWordListPoorly(testFile);
		fun.readWordList(testFile);
		System.out.println("did it.");
		System.out.println("added " + fun.wordList.getSize() + " words.");

		ArrayList<String> guess = fun.closeMatches("slolth");
		for (String word : guess ) {
			System.out.println(word);
		}
		
		System.out.println("valid BST?: " + fun.wordList.isValidBST("a", "zzzzzzzzzzzzzz"));

		
	}
	
	public void add(String s) {
		wordList.add(s);
	}
	
	private void addPoorly(String s) {
		//non-recursive
		wordList.addPoorly(s);
	}
	
	public boolean contains(String s) {
		//only returns true if we get a null back from find
		return (wordList.find(s) != null);
	}
	
	private boolean containsPoorly(String s) {
		//use non-recursive find
		return (wordList.findPoorly(s) != null);
	}
	
	//read from file line by line, use non-recursive addPoorly().
	public void readWordListPoorly(File f) throws FileNotFoundException {
		Scanner reader = new Scanner(f);
		//just throw it all in there one by one, fam
		while (reader.hasNext())
			this.addPoorly(reader.nextLine());
	}
	
	//wrapper for recursive method
	public void readWordList(File f) throws FileNotFoundException {
		Scanner reader = new Scanner(f);
		//can't really get middle word from file, so empty into arraylist first
		ArrayList<String> tempList = new ArrayList<>();
		while (reader.hasNextLine())
			tempList.add(reader.nextLine());
		
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
			//3 and above should have a middle, and two sides
			//even numbers favor right side of middle with this setup but doesn't matter
			this.add(list.get(middleIndex)); //add middle of this portion of list
			this.readWordList(list, startIndex, middleIndex-1); //recurse to left side
			this.readWordList(list, middleIndex+1, endIndex); //recurse to right side
		}
	}
	
//	public ArrayList<String> closeMatches(String s) {
//		//TODO consolidate. Should be able to have 1 or 2 loops (actual indecies, and offsets, and do checks in one pass
//		//TODO this needs to actually return a Set. Fix this.
//		ArrayList<String> out = new ArrayList<>();
//		//each helper method will add to output list if it finds something
//		this.swapAdjChars(s, out);
//		this.insertCharAllPositions(s, out);
//		this.deleteCharAllPositions(s, out);
//		this.replaceCharAllPositions(s,  out);
//		this.insertSpaceAllPositions(s, out);
//		return out;
//	}
//
//	private void swapAdjChars(String s, ArrayList<String> suggestions) {
//		StringBuilder testWord = new StringBuilder(s);
//		//each char will swap to the char to it's left, this should check all adjacent swaps possible
//		for (int i=1; i < s.length(); i++) {
//			//hold our current char
//			char temp = testWord.charAt(i);
//			//swap with left char
//			testWord.setCharAt(i, testWord.charAt(i-1));
//			testWord.setCharAt(i-1, temp);
//			//check
//			if (this.contains(testWord.toString()))
//				suggestions.add(testWord.toString());
//			//swap back
//			testWord.setCharAt(i-1, testWord.charAt(i));
//			testWord.setCharAt(i, temp);
//		}
//	}
//	
//	private void insertCharAllPositions(String s, ArrayList<String> suggestions) {
//		//put string in stringbuilder, insert a char at each offset, loop through each character and check for a match
//		StringBuilder testWord = new StringBuilder(s);
//		for (int i=0; i <= s.length(); i++) {
//			testWord.insert(i, 'a');
//			for (int j=(int)'a'; j <= (int)'z'; j++ ) {
//				testWord.setCharAt(i, (char)j);
//				if (this.contains(testWord.toString()))
//					suggestions.add(testWord.toString());
//			}
//			testWord.deleteCharAt(i);
//		}
//	}
//	
//	private void deleteCharAllPositions(String s, ArrayList<String> suggestions) {
//		StringBuilder testWord = new StringBuilder(s);
//		char temp;
//		for (int i=0; i < s.length(); i++) {
//			temp = testWord.charAt(i);
//			testWord.deleteCharAt(i);
//			if (this.contains(testWord.toString()))
//				suggestions.add(testWord.toString());
//			testWord.insert(i, temp);
//		}
//	}
//	
//	private void replaceCharAllPositions(String s, ArrayList<String> suggestions) {
//		StringBuilder testWord = new StringBuilder(s);
//		char temp;
//		for (int i=0; i < s.length(); i++) {
//			//hold char
//			temp = testWord.charAt(i);
//			
//			//loop through letters and check
//			for (int j=(int)'a'; j <= (int)'z'; j++) {
//				//if replacement char matches original, don't worry about trying
//				if ((char)j == temp)
//					continue;
//				testWord.setCharAt(i, (char)j);
//				if (this.contains(testWord.toString()))
//					suggestions.add(testWord.toString());
//			}
//			
//			//fix word for next loop
//			testWord.setCharAt(i, temp);			
//		}
//	}
//	
//	private void insertSpaceAllPositions(String s, ArrayList<String> suggestions) {
//		String word1, word2;
//		for (int i=1; i < s.length(); i++) {
//			word1 = s.substring(0, i);
//			word2 = s.substring(i);
//			if (this.contains(word1) && this.contains(word2)) {
//				suggestions.add(word1 + " " + word2);
//			}
//		}
//	}
	
}
