import java.util.HashMap;
import java.util.HashSet;

//no need to make TrieTree generic, we are sure of the data types
public class StringTrieTree {
	
	//no need to make this Node generic either, it will specifically hold strings
	private static class Node {
		private String data;
		//map containing all connected nodes
		private HashMap<String, Node> nodes;
		
		//create leaf node, 26 connecting nodes is unlikely, but should cover all cases so we don't
		//have to consider array growth, and we're a little faster than a list/set/whatever
		private Node(String data) {
			this.data = data;
			this.nodes = new HashMap<>();
		}
		
		//create leaf and add one connecting node
//		private Node(String data, Node node) {
//			this.data = data;
//			this.nodes = new HashMap<>();
//			this.nodes.put(node.data, node);
//		}
		
	}
	
	public static void main(String[] args) {
		StringTrieTree test = new StringTrieTree();
		test.add("testing");
		test.add("tent");
		test.add("bob");
		System.out.println(test.contains("hi"));
		System.out.println(test.contains("testing"));
		System.out.println(test.contains("tent"));
		System.out.println(test.contains("bobs"));
		
	}
	
	//root node cannot be null, just use empty string as data, it won't be used
	private Node root = new Node("");
	
	//TODO: Clean up
	public void add(String s) {
		//add(root, s);
		
		//the character we're dealing with currently
		String firstChar;// = s.substring(0,1);
		//what we'll be passing on
		//String theRest = s.substring(1);
		Node curNode = root;
		String curString = s;
		//while we still have some string to work with
		while(/*!theRest.isEmpty()*/!curString.isEmpty()) {
			firstChar = curString.substring(0, 1);
			if (curNode.nodes.containsKey(firstChar)) {
				//if the first letter of this string already has an existing node, we'll use that node next loop
				curNode = curNode.nodes.get(firstChar);
			} else {
				//if it doesn't exist, create the node and add it to this nodes list
				Node newNode = new Node(firstChar);
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
	
	//old recursive implementation
//	private Node add(Node curNode, String s) {
//		//the character we're dealing with currently
//		String firstChar = s.substring(0,1);
//		//what we'll be passing on
//		String theRest = s.substring(1);
//		
//		//if a node exists with this character, move on to it and do nothing in this call
//		if (curNode.nodes.containsKey(firstChar)) {
//			return add(curNode.nodes.get(firstChar), theRest);
//		} else {
//			//if it doesn't exist, create the node and add it to this nodes list
//			Node nextNode = new Node(firstChar);
//			curNode.nodes.put(firstChar, add(new Node(firstChar), theRest));
//		}
//
//		
//		
//	}
	
}
