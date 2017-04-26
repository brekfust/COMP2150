import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TrieSpellChecker extends SpellChecker {

	StringTrieTree wordList;
	private static int count = 0;
	public TrieSpellChecker() {
		wordList = new StringTrieTree();
	}
	
	public void add(String s) {
		wordList.add(s);
	}
	
	public boolean contains(String s) {
		return wordList.contains(s);
	}
	
	public int getSize() {
		return count;
	}
	
	//same as readWordListPoorly() from BST Spell Checker. Add order doesn't matter here.
	public void readWordList(File f) throws FileNotFoundException {
		Scanner reader = new Scanner(f);
		//just throw it all in there one by one, fam
		while (reader.hasNext()) {
			this.add(reader.nextLine());
			count++;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO use this for testing and delete at the end
		File testFile = new File("Project3_wordlist.txt");
		TrieSpellChecker fun = new TrieSpellChecker();
		//fun.readWordListPoorly(testFile);
		fun.readWordList(testFile);
		System.out.println("did it.");
		System.out.println("added " + fun.getSize() + " words.");

		ArrayList<String> guess = fun.closeMatches("slolth");
		for (String word : guess ) {
			System.out.println(word);
		}
		
		//System.out.println("valid BST?: " + fun.wordList.isValidBST("a", "zzzzzzzzzzzzzz"));

		
	}
	
}
