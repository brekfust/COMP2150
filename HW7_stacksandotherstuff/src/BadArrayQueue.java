import java.util.NoSuchElementException;

public class BadArrayQueue<E> implements Queue<E> {

	//if we're doing it bad, let's do it really bad, array size always reflects data size
	//no need to track anything
	 private E[] data = (E[])(new Object[0]);
	

	@Override
	public boolean isEmpty() {
		return (data.length == 0);
	}

	@Override
	public E peek() {
		if (!isEmpty())
			return data[0];
		else
			throw new NoSuchElementException();
	}

	@Override
	public void enqueue(E newItem) {
		E[] newData = (E[])(new Object[data.length+1]);
		//new item is always head
		newData[0] = newItem;
		//may as well have used standard for loop, since I have counter, but I haven't used
		//foreach much before and wanted to
		int i = 1;
		for (E item : data) {
			newData[i++] = item;
		}
		data = newData;
	}

	@Override
	public E dequeue() {
		E[] newData = (E[])(new Object[data.length-1]);
		//store head so we can return it
		E out = data[0];
		//copy array, but skip first element
		for (int i = 1; i < data.length; i++) {
			newData[i-1] = data[i];
		}
		data = newData;
		return out;
	}

	public static void main(String[] args) {
		Queue<Integer> n00bs = new ArrayQueue<>();
		
		System.out.println("TotallyNotPokemonOnlineVRMMOFPS Tactics has launched, accepting logins");
		
		//logins with arrayqueue
		long timer = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) 
			n00bs.enqueue(new Integer(i));
		timer = System.currentTimeMillis() - timer;
		System.out.println("100,000 ArrayQueue logins queued in " + timer + " milliseconds.");
		
		//dequeue logins arrayqueue
		System.out.println("Processing...");
		timer = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++)
			n00bs.dequeue();
		timer = System.currentTimeMillis() - timer;
		System.out.println("Banned 100,000 players for hax in " + timer + " milliseconds.");
		
		//logins with BadArrayQueue
		n00bs = new BadArrayQueue<>();
		System.out.println("Switching to slower anti-haxx system. You did this to yourselves.");
		timer = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) 
			n00bs.enqueue(new Integer(i));
		timer = System.currentTimeMillis() - timer;
		System.out.println("100,000 new plebs accepted in " + timer + " milliseconds.\nBuffering..");
		
		//dequeue with BadArrayQueue
		timer = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++)
			n00bs.dequeue();
		timer = System.currentTimeMillis() - timer;
		System.out.println("It took " + timer + " to ban 100,000 more H4x0r2. Pools closed, go home.");

	}

	
}
