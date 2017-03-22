/*
 * Implements the ListGeneric<E> interface using a linked list.  A linked list consists
 * of nodes, each of which has some data and a reference to the next node.  The nodes
 * do not have to be contiguous in memory, since each node knows how to get to the next node.
 * We need to keep track of only the first node (head) of the list.
 * ....
 */
public class LinkedListGeneric<E> implements ListGeneric<E> {

	private Node<E> head;		// the first node in the list
	private int size;			// how many nodes are in the list - needs to be updated when we call add or remove
	
	//HOMEWORK PROBLEMS
	public void reverse(){
		//check for single/empty list
		if (head == null || head.getNext() == null)
			return;
		Node<E> current = head;
		Node<E> next = head.getNext();
		Node<E> temp;
		while (next != null && next.getNext() != null) {
			temp = next.getNext();
			next.setNext(current);
			current = next;
			next = temp;
		}
		head.setNext(null);
		head = next;
		head.setNext(current);
	}
	
	public E remove(E item) {
		//keep count so we can use remove(index)
		int curIndex = 0;
		Node<E> current = head;
		while (current != null) {
			if (current.getData().equals(item)){
				this.remove(curIndex);
				return current.getData();
			}
			current = current.getNext();
			curIndex++;
		}
		return null;
	}
	
	public ArrayListGeneric<E> toArrayList()
	{
		ArrayListGeneric<E> out = new ArrayListGeneric<>();
		Node<E> current = head;
		while( current != null) {
			out.add(current.getData());
			current = current.getNext();
		}

		return out;
	}
	
	@Override
	public E get(int index) {
		return nodeAt(index).getData();
	}

	@Override
	public void set(int index, E newItem) {
		nodeAt(index).setData(newItem);
	}

	@Override
	public void add(E newItem) {
		Node<E> newNode = new Node<>(newItem, null);	// create a new node containing the newItem, and whose next points to null
		if (size == 0)		// add to an empty list
			head = newNode;
		else
			nodeAt(size - 1).setNext(newNode);	// get to the last node of the list, then add the new node after that
		size++;
	}

	@Override
	public E remove(int index) {
		if (index >= 0 && index < size) {
			E toReturn;		// temp variable to store data to return
			if (index == 0)	{	// remove head node
				toReturn = head.getData();
				head = head.getNext();
			} else {	// remove some other node
				Node<E> nodeBefore = nodeAt(index - 1);	// this is the node right before the node to remove
				toReturn = nodeBefore.getNext().getData();
				nodeBefore.setNext(nodeBefore.getNext().getNext());
			}
			size--;
			return toReturn;
		} else
			throw new IndexOutOfBoundsException();
	}

	// This method returns the node at the specified index.
	// Throws an IndexOutOfBoundsException if an invalid index is supplied.
	private Node<E> nodeAt(int index) {
		if (index >= 0 && index < size) {
			Node<E> temp = head;	// here, temp is not a new node!  it's simply a second reference to the existing head node
			for (int i = 0; i < index; i++)
				temp = temp.getNext();	// move temp down the list, for index times
			return temp;
		} else
			throw new IndexOutOfBoundsException();
	}

	// test main
	public static void main(String[] args) {
		LinkedListGeneric<String> theList = new LinkedListGeneric<>();
		theList.add("Belle");
		theList.add("the Beast");
		theList.add("Gaston");
		theList.add("Luminaire");
		theList.add("Cogsworth");
		theList.add("Chip");

		// usually we wouldn't be able to access size like this, since it's private - however,
		//  we're still in the LinkedListGeneric class right now!
		for (int i = 0; i < theList.size; i++)
			System.out.println(theList.get(i));
		
//		System.out.println("removing " + theList.remove(5));
//		
//		for (int i = 0; i < theList.size; i++)
//			System.out.println(theList.get(i));
//
//		System.out.println("removing " + theList.remove(0));
//		
//		for (int i = 0; i < theList.size; i++)
//			System.out.println(theList.get(i));
//
//		System.out.println("removing " + theList.remove(2));
//		
//		for (int i = 0; i < theList.size; i++)
//			System.out.println(theList.get(i));
		//Homework test
		System.out.println("\nHomework tests:");
		theList.reverse();
		for (int i = 0; i < theList.size; i++)
			System.out.println(theList.get(i));
		System.out.println("Remove Cogsworth");
		theList.remove("Cogsworth");
		System.out.print(theList.toArrayList());
	}
	

	
}
