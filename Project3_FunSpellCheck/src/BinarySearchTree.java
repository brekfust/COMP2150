/*
 * This class implements a binary search tree.  A BST is a binary tree where all nodes
 * satisfy the property that their data is greater than the data of all nodes in the
 * left subtree, and less than the data of all nodes in the right subtree.
 */
public class BinarySearchTree<E extends Comparable<E>> {
	// nested class to represent a single node in the tree
	// very similar to Node from LinkedList, but each node has two children instead of just one "next"
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
	//TODO delete size, getter, and references to size in add()/addPoorly()
	private int size;
	
	public int getSize() {
		return size;
	}
	
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
	
	//non-recursive add
	public void addPoorly(E newData) {
		if (root == null)  //empty list
			root = new Node<>(newData, null, null);
		else {
			//non empty list add, same process as add() without recursion
			boolean added = false;
			Node<E> curNode = root;
			int c;
			do {
				c = newData.compareTo(curNode.data);
				
				if (c < 0 && curNode.left == null) {	// there is space on where's left 
					curNode.left = new Node<>(newData, null, null);
					added = true;
					this.size++;
				}
				else if (c > 0 && curNode.right == null) {	// there is space on where's right
					curNode.right = new Node<>(newData, null, null);
					added = true;
					this.size++;
				}
				else if (c < 0)   //recursion would be here, instead update reference and continue
					curNode = curNode.left;
				else if (c > 0)
					curNode = curNode.right;
			} while (!added);
		}
	}
	
	// Wrapper method for add
	public void add(E newData) {
		if (root == null) {	// special case for adding to the root of the tree
			root = new Node<>(newData, null, null);
			size++;
		}
		else
			add(newData, root);
	}

	// Recursively adds newData to the subtree rooted at where.
	private void add(E newData, Node<E> where) {
		int c = newData.compareTo(where.data);
		
		if (c < 0 && where.left == null) {	// there is space on where's left 
			where.left = new Node<>(newData, null, null);
			this.size++;
		}
		else if (c > 0 && where.right == null) {	// there is space on where's right
			where.right = new Node<>(newData, null, null);
			this.size++;
		}
		else if (c < 0)
			add(newData, where.left);	// recursively add to where's left subtree
		else if (c > 0)
			add(newData, where.right);	// recursively add to where's right subtree
		//for testing
//		else if (c == 0) 
//			System.out.println("attempt at duplicate add " + newData);
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

	public E findPoorly(E someItem) {
		//same as find but in without recursion
		Node<E> curNode = root;
		int c;
		do {
			//return null if nothing here
			if (curNode == null) {
				return null;
			}
			
			c = someItem.compareTo(curNode.data);
			if (c == 0)	
				return curNode.data; 	//item found!
			else if (c < 0) {
				curNode = curNode.left; //lesser values on left node
			}
			else { //(c > 0)
				curNode = curNode.right; //greater values on the right
			}
		} while (true); //dangerous, but the null check at the start of the loop will keep us out of infinite loop
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

}
