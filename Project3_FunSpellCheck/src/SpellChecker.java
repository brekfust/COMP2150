import java.util.ArrayList;

public abstract class SpellChecker {

	//these abstracts chain to the SpellCheckers data structure
	abstract void add(String s);
	
	abstract boolean contains(String s);
	
	public ArrayList<String> closeMatches(String s) {
		//TODO consolidate. Should be able to have 1 or 2 loops (actual indecies, and offsets, and do checks in one pass
		//TODO this needs to actually return a Set. Fix this.
		ArrayList<String> out = new ArrayList<>();
		//each helper method will add to output list if it finds something
		this.swapAdjChars(s, out);
		this.insertCharAllPositions(s, out);
		this.deleteCharAllPositions(s, out);
		this.replaceCharAllPositions(s,  out);
		this.insertSpaceAllPositions(s, out);
		return out;
	}

	private void swapAdjChars(String s, ArrayList<String> suggestions) {
		StringBuilder testWord = new StringBuilder(s);
		//each char will swap to the char to it's left, this should check all adjacent swaps possible
		for (int i=1; i < s.length(); i++) {
			//hold our current char
			char temp = testWord.charAt(i);
			//swap with left char
			testWord.setCharAt(i, testWord.charAt(i-1));
			testWord.setCharAt(i-1, temp);
			//check
			if (this.contains(testWord.toString()))
				suggestions.add(testWord.toString());
			//swap back
			testWord.setCharAt(i-1, testWord.charAt(i));
			testWord.setCharAt(i, temp);
		}
	}
	
	private void insertCharAllPositions(String s, ArrayList<String> suggestions) {
		//put string in stringbuilder, insert a char at each offset, loop through each character and check for a match
		StringBuilder testWord = new StringBuilder(s);
		for (int i=0; i <= s.length(); i++) {
			testWord.insert(i, 'a');
			for (int j=(int)'a'; j <= (int)'z'; j++ ) {
				testWord.setCharAt(i, (char)j);
				if (this.contains(testWord.toString()))
					suggestions.add(testWord.toString());
			}
			testWord.deleteCharAt(i);
		}
	}
	
	private void deleteCharAllPositions(String s, ArrayList<String> suggestions) {
		StringBuilder testWord = new StringBuilder(s);
		char temp;
		for (int i=0; i < s.length(); i++) {
			temp = testWord.charAt(i);
			testWord.deleteCharAt(i);
			if (this.contains(testWord.toString()))
				suggestions.add(testWord.toString());
			testWord.insert(i, temp);
		}
	}
	
	private void replaceCharAllPositions(String s, ArrayList<String> suggestions) {
		StringBuilder testWord = new StringBuilder(s);
		char temp;
		for (int i=0; i < s.length(); i++) {
			//hold char
			temp = testWord.charAt(i);
			
			//loop through letters and check
			for (int j=(int)'a'; j <= (int)'z'; j++) {
				//if replacement char matches original, don't worry about trying
				if ((char)j == temp)
					continue;
				testWord.setCharAt(i, (char)j);
				if (this.contains(testWord.toString()))
					suggestions.add(testWord.toString());
			}
			
			//fix word for next loop
			testWord.setCharAt(i, temp);			
		}
	}
	
	private void insertSpaceAllPositions(String s, ArrayList<String> suggestions) {
		String word1, word2;
		for (int i=1; i < s.length(); i++) {
			word1 = s.substring(0, i);
			word2 = s.substring(i);
			if (this.contains(word1) && this.contains(word2)) {
				suggestions.add(word1 + " " + word2);
			}
		}
	}

}
