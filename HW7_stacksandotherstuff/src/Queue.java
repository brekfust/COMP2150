/*
 * Interface that specifies the basic queue operations.  A queue is a linear data structure
 * where elements are added to the back, but removed from the front.  This makes it a FIFO
 * (first-in, first-out) data structure, as opposed to the LIFO structure of a stack.
 * 
 * We will implement this interface using an array and a linked list, similar to what we
 * did with Stack.
 * 
 */
public interface Queue<E> {
	// Returns whether the queue is empty.
	boolean isEmpty();
	
	// Returns (but does not remove) the element at the front of the queue
	E peek();
	
	// Adds the specified newItem to the back of the queue.
	void enqueue(E newItem);

	// Removes and returns the element at the front of the queue.
	E dequeue();
}
