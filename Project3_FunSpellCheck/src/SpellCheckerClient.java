import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SpellCheckerClient {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		String strIn; //temp string variable for input
		int intIn;    //temp int variable for input
		
		//start of main loop, just break out of loop when we're done instead of tracking another boolean
		while(true) {
			System.out.println("Super Happy Funtime SpellCheck!");
			System.out.println("MAIN MENU");
			System.out.println("1. Balanced BinarySearchTree");
			System.out.println("2. Trie");
			System.out.println("3. Unbalance BinarySearchTree");
			System.out.print("What what of data structure would you like to use for your word dictionary? Choose one: ");
			try {
				intIn = Integer.parseInt(input.nextLine());
			} catch (Exception e) {
				System.out.println("What? Try that again.");
				continue;
			}
			
			SpellChecker checker;
			//check for valid selection
			switch (intIn) {
				case 1: checker = new BSTSpellChecker(); break;
				case 2: checker = new TrieSpellChecker(); break;
				case 3: checker = new BadBSTSpellChecker(); break;
				default: System.out.println("That doesn't mean anything to me. One more time?"); continue;
			}
			
			System.out.println("Alright, if you're going to be picky about it I'll use that one.");
			
			//filename selection, loop so we don't have to start over if there is an exception
			boolean loopIt = true;
			while (loopIt) {
				try {
					System.out.print("Type the file name of your word list, or type \"halp\" for the default: ");
					strIn = input.nextLine();
					if (strIn.equals("halp"))
						strIn = "Project3_wordlist.txt";
					File theFile = new File(strIn);
					
					//Attempt to read file and time it
					long timer = System.currentTimeMillis();
					checker.readWordList(theFile);
					timer = System.currentTimeMillis() - timer;
					System.out.println("Got it. It took " + timer + " milliseconds to read that, by the way.");
					
				} catch (Exception FileNotFoundException) {
					System.out.println("Is that even a thing? Try something else.");
					continue;
				}
				loopIt = false;
			}
			
			//ask for first word to check
			System.out.print("Type you up a sentence and I'll check it out: ");
			
			//start final loop
			while(true) {
				String[] wordsOfSentence;
				HashSet<String> wordsMaybe;
				
				try {
					strIn = input.nextLine();
				} catch (Exception e) {
					System.out.println("What did you say?: ");
					continue;
				}
				
				//user provided empty string
				if (strIn.equals("")) {
					System.out.println("Could you give me a little something more to work with?");
					continue;
					
				//user doesn't know about these options until the next loop, but easiest to check these here
				} else if (strIn.equals("!"))
					return;
				else if (strIn.equals("?"))
					break;
					
				//split input sentence by whitespace
				wordsOfSentence = strIn.split("\\s");
				
				boolean misspelled = false; 
				for (String word : wordsOfSentence) {
					if (word.equals("") || checker.contains(word)) //no action for empty strings and words in the dictionary
						continue;
					else {
						//misspelled word actions
						wordsMaybe = checker.closeMatches(word);
						misspelled = true;
						System.out.print(word + " is mispelled. It could be ");
						if (wordsMaybe.size() > 0){
							System.out.print("one of these: ");
							for (String newWord : wordsMaybe)	//a third nested loop? Ugh. Loop through each suggested word
								System.out.print(newWord + " ");
							System.out.println();
						} else {
							System.out.println("....Actually I'm not sure what that could be.");	//couldn't find any suggestions
						}
					}
				}
				
				 //made it through without misspelling anything
				if (!misspelled)
					System.out.println("That sentence looks pretty good, surprisingly.");
				
				System.out.println("Now you can type another word to check, or type \"!\" to quit, or \"?\" to start over");
				System.out.print("What's it going to be?: ");
//				try {
//					strIn = input.nextLine();
//				} catch (Exception e) {
//					System.out.println("Let's just try again");
//					break;
//				}
				
				//only need to check for quitting or starting over, strIn will carry to next loop to check for accuracy otherwise
//				if (strIn.equals("!"))
//					return; 	//exits program completely
//				else if (strIn.equals("?"))
//					break; 		//breaks out of loop to start main loop over again.
			}
		}
	}
}
