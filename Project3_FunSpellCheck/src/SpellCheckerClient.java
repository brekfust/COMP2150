import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

public class SpellCheckerClient {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO use this for testing and delete at the end
		File testFile = new File("Project3_wordlist.txt");
		TrieSpellChecker fun = new TrieSpellChecker();
		BSTSpellChecker test2 = new BSTSpellChecker();
		
		long timer = System.currentTimeMillis();
		fun.readWordList(testFile);
		timer = System.currentTimeMillis() - timer;
		System.out.println("TrieSpellChecker added " + fun.getSize() + " words in " + timer + " milliseconds.");
		System.out.println("It took " + StringTrieTree.getNodeCount() + " nodes to represent those words.");
		
		timer = System.currentTimeMillis();
		test2.readWordList(testFile);
		timer = System.currentTimeMillis() - timer;
		System.out.println("BSTSpellChecker added " + fun.getSize() + " words in " + timer + " milliseconds.");
		HashSet<String> guess = fun.closeMatches("slolth");
		HashSet<String> guess2 = test2.closeMatches("slolth");
		for (String word : guess ) {
			System.out.println(word);
		}
		
		for (String word : guess2 ) {
			System.out.println(word);
		}

		//START ACTUAL CODE HERE
		
	}

}
