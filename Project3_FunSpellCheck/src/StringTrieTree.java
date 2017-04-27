import java.util.HashMap;

//Trie implemented with HashMap.
public class StringTrieTree { //no need to make TrieTree generic, we are sure of the data types
	
	private static class Node {		//Node data is handled by HashMap, no need to make generic
		private boolean endOfWord;		//endOfWord boolean needed for contains() to work properly
		private HashMap<String, Node> nodes;  	//no data variable needed, data is stored in the hash table that points to the node
		private static int nodeCount = 0;	//not necessary but curious of this total
		
		private Node() {
			this.nodes = new HashMap<>();
			endOfWord = false;
			nodeCount++;
		}
		
		
		public void setEndOfWord() {
			this.endOfWord = true;
		}
		
		public boolean isEndOfWord() {
			return endOfWord;
		}
	}
	
	//root node cannot be null, populate it now
	private Node root = new Node();
	
	public static int getNodeCount() {
		return Node.nodeCount;
	}
	
	//look at the string one character at a time, traverse the tree and see if each character exists or needs
	//to be created at each appropriate level
	public void add(String s) {
		String firstChar;  	 //the character we're dealing with currently, moves up the string each loop
		String curString = s;
		Node curNode = root;
		
		while(!curString.isEmpty()) {
			firstChar = curString.substring(0, 1);
			if (curNode.nodes.containsKey(firstChar)) { //do nothing if we already have this character in this node
				curNode = curNode.nodes.get(firstChar);
			} else {
				Node newNode = new Node();		//if it doesn't exist, create the node and add it to this nodes list
				curNode.nodes.put(firstChar, newNode);
				curNode = newNode;
			}
			
			//set up for next loop
			curString = curString.substring(1);
		}
		curNode.setEndOfWord();
		return;
	}
	
	public boolean contains(String s) {
		String firstChar;
		String curString = s;
		Node curNode = root;
		
		//check each character of the string for a corresponding node
		while (!curString.isEmpty()) {
			firstChar = curString.substring(0, 1);
			
			if (curNode.nodes.containsKey(firstChar))
				curNode = curNode.nodes.get(firstChar);
			else {
				return false;
			}
			curString = curString.substring(1);
		}
		
		//the string is in the tree at this point, but we still need to check if it's the end of a word or not
		return curNode.isEndOfWord();
	}
	
}
