/*
 * This class represents a single node in a linked list.  Each node contains some data,
 * and a reference to the next node in the list.
 */
public class Node<E> {

	private E data;
	private Node<E> next;

	// Getters and setters automatically created by Eclipse
	//  (right click > Source > Generate Getters and Setters)
	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public Node<E> getNext() {
		return next;
	}

	public void setNext(Node<E> next) {
		this.next = next;
	}

	// constructor
	public Node(E data, Node<E> next) {
		this.data = data;
		this.next = next;
	}
}
