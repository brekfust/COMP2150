/*
 * Class representing a linked list, using recursion for some of its operations.
 * 
 */
import java.util.NoSuchElementException;

public class RecursiveLL<E> {
	// Same nested Node class we used in LinkedListGeneric
	private static class Node<E> {
		private E data;
		private Node<E> next;

		// constructor
		private Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
		
		public String toString() {
			if (data == null) {
				return "null";
			} else {
				return "" + this.data;
			}
		}
	}
	
	private Node<E> head;
	
	// Non-recursive method that adds a newItem to the head of the list
	public void addToHead(E newItem) {
		head = new Node<>(newItem, head);
	}
	
	// wrapper method for size
	public int size() {
		return size(head);
	}

	// size of the list from node where = 1 + size of the rest of the list
	private int size(Node<E> where) {
		if (where == null)	// base case - we've reached the end of the list!
			return 0;
		else
			return 1 + size(where.next);
	}

	// wrapper method for toString
	public String toString() {
		return "head -> " + toString(head);
	}

	// toString from node where = where's data + toString of the rest of the list
	private String toString(Node<E> where) {
		if (where == null)	// base case - we've reached the end of the list!
			return "null";
		else
			return where.data + " -> " + toString(where.next);
	}

	// wrapper method for addToTail
	public void addToTail(E newData) {
		if (head == null)	// handles special case of adding to an empty list
			head = new Node<>(newData, null);
		else
			addToTail(newData, head);
	}

	private void addToTail(E newData, Node<E> where) {
		if (where.next == null)	// base case - where is the last node in the list
			where.next = new Node<>(newData, null);
		else
			addToTail(newData, where.next);	// if where is not the last node, recursively call the method starting from one node later
	}
	
	// wrapper method for get - checks for valid index
	public E get(int index) {
		if (index >= 0 && index < size())
			return get(index, head);
		else
			throw new NoSuchElementException();
	}

	// decrement index until we get to the desired location
	private E get(int index, Node<E> where) {
		if (index == 0)
			return where.data;
		else
			return get(index - 1, where.next);
	}
	
	//wrapper for contains
	public boolean contains(E someItem) {
		//empty
		if (head == null)
			return false;
		//get started
		else
			return contains(someItem, head);
	}
	
	private boolean contains(E someItem, Node<E> curNode) {
		//found it
		if (curNode.data == someItem)
			return true;
		//move on if we can
		else if (curNode.next != null)
			return contains(someItem, curNode.next);
		//it's not here if we can't
		else
			return false;
	}
	
	//search wrapper
	public int search(E someItem) {
		//empty
		if (head == null)
			return -1;
		//get started
		else
			return search(someItem, head, 0);
	}
	
	private int search(E someItem, Node<E> curNode, int curIndex) {
		//found it
		if (curNode.data == someItem)
			return curIndex;
		//move on if we can
		else if (curNode.next != null)
			return search(someItem, curNode.next, ++curIndex);
		//it's not here if we can't
		else
			return -1;
	}
	
	public Node<E> reverse() {
		//empty
		if (head == null)
			return null;
		//only 1 element
		else if (head.next == null)
			return head;
		//gogogogo
		else {
			Node<E> nextNode = head.next;
			head.next = null;
			return reverse(head, nextNode); 
		}
	}
	
	private Node<E> reverse(Node<E> prevNode, Node<E> curNode) {
		//we at tail/newhead, clean up and finish
		if (curNode.next == null) {
			curNode.next = prevNode;
			head = curNode;
			return head;
		//get reference to next node, point to previous, keep going
		} else {
			Node<E> nextNode = curNode.next;
			curNode.next = prevNode;
			return reverse(curNode, nextNode);
		}
	}
	public static void main(String[] args) {
		RecursiveLL<Integer> l = new RecursiveLL<>();
		System.out.println(l);
		l.addToTail(4);
		System.out.println(l);
		l.addToTail(3);
		System.out.println(l);
		l.addToTail(2);
		System.out.println(l);
		System.out.println(l.reverse().data);
		System.out.println(l);
	}
}
