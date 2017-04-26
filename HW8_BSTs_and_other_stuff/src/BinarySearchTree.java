import java.util.LinkedList;

/*
 * This class implements a binary search tree.  A BST is a binary tree where all nodes
 * satisfy the property that their data is greater than the data of all nodes in the
 * left subtree, and less than the data of all nodes in the right subtree.
 */
public class BinarySearchTree<E extends Comparable<E>> {
	// nested class to represent a single node in the tree
	//  very similar to Node from LinkedList, but each node has two children instead of just one "next"
	private static class Node<E> {
		private E data;
		private Node<E> left, right;
		
		private Node(E data, Node<E> left, Node<E> right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}
	
	private Node<E> root;

	// Wrapper method for inOrderTraversal
	public void inOrderTraversal() {
		System.out.println("In-order traversal:");
		inOrderTraversal(root);
	}

	// Performs an in-order traversal of the subtree rooted at where.
	private void inOrderTraversal(Node<E> where) {
		if (where != null) {
			inOrderTraversal(where.left);
			System.out.println(where.data);
			inOrderTraversal(where.right);
		}
	}

	// Wrapper method for add
	public void add(E newData) {
		if (root == null)	// special case for adding to the root of the tree
			root = new Node<>(newData, null, null);
		else
			add(newData, root);
	}

	// Recursively adds newData to the subtree rooted at where.
	private void add(E newData, Node<E> where) {
		int c = newData.compareTo(where.data);
		
		if (c < 0 && where.left == null)	// there is space on where's left
			where.left = new Node<>(newData, null, null);
		else if (c > 0 && where.right == null)	// there is space on where's right
			where.right = new Node<>(newData, null, null);
		else if (c < 0)
			add(newData, where.left);	// recursively add to where's left subtree
		else if (c > 0)
			add(newData, where.right);	// recursively add to where's right subtree
	}

	// Wrapper for book's add method.
	public void add_book(E newData) {
		root = add_book(newData, root);
	}
	
	// Recursive version of add, as presented in your textbook.
	// Returns a reference to where, with the newData already added
	private Node<E> add_book(E newData, Node<E> where) {
		if (where == null)
			return new Node<>(newData, null, null);
		else {
			int c = newData.compareTo(where.data);
			
			if (c < 0)
				where.left = add_book(newData, where.left);
			else if (c > 0)
				where.right = add_book(newData, where.right);
			
			return where;
		}
	}

	// Wrapper method for delete
	public void delete(E someItem) {
		System.out.println("deleting " + someItem);
		root = delete(someItem, root);
	}

	// Recursively deletes someItem from the subtree rooted at where.
	//  Returns a reference to where, with someItem already deleted.
	private Node<E> delete(E someItem, Node<E> where) {
		if (where == null) {		// empty subtree - no changes to make
			return null;
		} else {
			int c = someItem.compareTo(where.data);
			
			if (c < 0) {	// recursively delete from where's left subtree
				where.left = delete(someItem, where.left);
				return where;
			} else if (c > 0) {	// recursively delete from where's right subtree
				where.right = delete(someItem, where.right);
				return where;
			} else {	// where contains the someItem to delete
				if (where.left == null && where.right == null) {	// case 1: where has no children
					System.out.println(" (no children)");
					return null;
				} else if (where.left == null && where.right != null) {	// case 2a: where has only a right child
					System.out.println(" (right child)");
					return where.right;
				} else if (where.left != null && where.right == null) {	// case 2b: where has only a left child
					System.out.println(" (left child)");
					return where.left;
				} else {			// case 3: oh noes, where has TWO children
					System.out.println(" (two children)");
					where.data = findAndDeleteIOP(where);
					return where;
				}
			}
		}
	}
	
	// Finds and deletes the in-order predecessor of the node where.
	// Returns the value of the IOP that was removed.
	private E findAndDeleteIOP(Node<E> where) {
		Node<E> temp = where.left;
		Node<E> parent = where;		// keeps track of temp's parent node
		while (temp.right != null) {	// move temp right until it can go no further
			parent = temp;
			temp = temp.right;
		}

		if (parent == where)	// if the while loop has not run...
			parent.left = temp.left;
		else
			parent.right = temp.left;
		
		return temp.data;
	}
	
	
	// Wrapper method for find.
	public E find(E someItem) {
		return find(someItem, root);
	}

	// Recursively searches the subtree rooted at where for someItem.  Returns
	//  the item from the tree if found, or null if not found.
	private E find(E someItem, Node<E> where) {
		if (where == null)	// empty subtree - item not found!
			return null;
		else {
			int c = someItem.compareTo(where.data);
			if (c == 0)		// item found!
				return where.data;
			else if (c < 0)
				return find(someItem, where.left);	// recursively search where's left subtree
			else
				return find(someItem, where.right);	// recursively search where's right subtree
		}
	}
	
	//non-recusive find
	public E findIterative(E someItem) {
		Node<E> curNode = this.root;
		int compare;
		do {
			compare = someItem.compareTo(curNode.data);
			if (compare < 0)
				curNode = curNode.left;
			else if (compare > 0)
				curNode = curNode.right;
			else
				return curNode.data;
		} while (curNode != null);
		return null;
	}
	
	//recursive wrapper
	public int findHeight() {
		if (root == null)
			return 0;
		else
			return findHeight(root, 1);
	}
	
	private int findHeight(Node<E> curNode, int curLevel) {
		int l = 0;
		int r = 0;
		//base case
		if (curNode.left == null && curNode.right == null)
			return curLevel;
		else {
			//if we can move on to left side, keep the level we get from it
			if (curNode.left != null)
				l = findHeight(curNode.left, curLevel+1);
			//if we can move on to the right side, keep the level
			if (curNode.right != null)
				r = findHeight(curNode.right, curLevel+1);
		}
		
		//we'll pass the largest level we got up the stack
		//if left side level is greater than right side level, return it, otherwise return right, doesn't matter if equal
		return (l > r) ? l : r;
	}
	
	public void levelOrderTraversal() {
		LinkedList<Node<E>> queue = new LinkedList<>();
		queue.add(root);
		System.out.println("Level order traversal:");
		
		while(!queue.isEmpty()) {
			//get reference
			Node<E> curNode = queue.remove();
			
			//skip if null
			if (curNode == null)
				continue;
			
			//add children for next loop
			queue.add(curNode.left);
			queue.add(curNode.right);
			
			//print current node
			System.out.println("" + curNode.data + " ");
		}
		
	}
	
	// Wrapper method for toString
	public String toString() {
		return toString(root, "");
	}
	
	// Returns a string representing the subtree starting from where.
	//  indent keeps track of how much to indent the root of this subtree
	private String toString(Node<E> where, String indent) {
		if (where == null)
			return indent + "null";
		else
			return indent + where.data + "\n" + toString(where.left, indent + " ") + "\n" + toString(where.right, indent + " ");
	}
	
	
	public static void main(String[] args) {
		BinarySearchTree<String> theTree = new BinarySearchTree<>();
		String[] x = {"5", "7", "0", "9", "4", "1"};
		for (String d : x)
			theTree.add_book(d);
		System.out.println(theTree);
		theTree.inOrderTraversal();
		System.out.println("height: " + theTree.findHeight());
		theTree.levelOrderTraversal();
		System.out.println("Find 5: " + theTree.findIterative("5"));
		
//		for (String d : x) {
//			theTree.delete(d);
//			System.out.println(theTree);
//		}
	}
}
