import java.util.HashMap;

//Implemented with HashMap. These 
public class StringTrieTree { //no need to make TrieTree generic, we are sure of the data types
	
	private static class Node { //this is a very specific node, no need to make generic
		private HashMap<String, Node> nodes;  	//no data variable needed, data is stored in the hash table that points to the node
		private static int nodeCount = 0;		//Realized nodecount is the reason Trie is slower to add words and wanted to know how many nodes there are
		
		private Node() {
			this.nodes = new HashMap<>();
			nodeCount++;
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
		return;
	}
	
	public boolean contains(String s) {
		String firstChar;
		String curString = s;
		Node curNode = root;
		
		while (!curString.isEmpty()) {
			firstChar = curString.substring(0, 1);
			
			if (curNode.nodes.containsKey(firstChar))
				curNode = curNode.nodes.get(firstChar);
			else {
				return false;
			}
			curString = curString.substring(1);
		}
		
		return true;
	}
	
}
