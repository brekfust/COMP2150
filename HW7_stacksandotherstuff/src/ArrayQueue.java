/*
 * Implements a queue using an array as the underlying data structure.  We
 * maintain the front and back indices in the array, and we allow both to change
 * as elements are enqueued/dequeued.  This prevents having to shift elements around.
 * 
 * enqueue = increase back, then add the new element (O(1))
 * dequeue = increase front (O(1))
 * 
 * Both front and back are allowed to reset to 0 if needed to make use of all
 * available space in the array.  If the array's space is exhausted, we reallocate
 * it, similar to what we did for ArrayList and ArrayStack.
 * 
 */
import java.util.NoSuchElementException;

public class ArrayQueue<E> implements Queue<E> {

	private E[] data = (E[])(new Object[5]);
	private int front, back = data.length - 1;
	private int size;	// how many elements are in the queue
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E peek() {
		if (!isEmpty())
			return data[front];
		else
			throw new NoSuchElementException();
	}

	@Override
	public void enqueue(E newItem) {
		if (size == data.length) {	// array is full!
			E[] newData = (E[])(new Object[2*data.length]);
			
			// i is the index in newData, j is the index in data
			// the j = (j + 1) % data.length allows j to automatically reset itself to 0 if it goes beyond the bounds of the array
			for (int i = 0, j = front; i < size; i++, j = (j + 1) % data.length)
				newData[i] = data[j];

			data = newData;
			front = 0;		// front is reset to 0 in newData
			back = size - 1;
		}
		back = (back + 1) % data.length;
		data[back] = newItem;
		size++;
	}

	@Override
	public E dequeue() {
		if (!isEmpty()) {
			E toReturn = data[front];
			front = (front + 1) % data.length;
			size--;
			return toReturn;
		} else
			throw new NoSuchElementException();
	}
	
	public String toString() {
		String r = "ArrayQueue (size = " + size + ", front = " + front + ", back = " + back + "), containing (front to back): ";
		for (int i = 0, j = front; i < size; i++, j = (j + 1) % data.length)
			r += data[j] + " ";
		return r;
	}
	
	public static void main(String[] args) {
		Queue<String> q = new ArrayQueue<>();
		System.out.println(q);
		q.enqueue("Player One");
		System.out.println(q);
		q.enqueue("A paying player");
		q.enqueue("A free to play player");
		q.enqueue("A cheater");
		q.enqueue("A try hard");
		q.enqueue("A 1337 h4x0r");
		System.out.println(q);
		
		while (!q.isEmpty()) {
			System.out.println("removing: " + q.dequeue());
			System.out.println(q);
		}
		
		q.enqueue("Player Three");
		System.out.println(q);
	}
}
