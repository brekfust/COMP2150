import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//This class is just BSTSpellChecker with non-recursive methods to implement the same thing but unbalanced. Much simpler.
public class BadBSTSpellChecker extends SpellChecker {

	private BinarySearchTree<String> wordList;
	
	public BadBSTSpellChecker() {
		wordList = new BinarySearchTree<>();
	}
	
	public void add(String s) {
		//non-recursive
		wordList.addPoorly(s);
	}
	
	public boolean contains(String s) {
		//use non-recursive find
		return (wordList.findPoorly(s) != null);
	}
	
	public void readWordList(File f) throws FileNotFoundException {
		Scanner reader = new Scanner(f);
		//just throw it all in there one by one, fam
		while (reader.hasNext())
			this.add(reader.nextLine());
		reader.close();
	}

}
