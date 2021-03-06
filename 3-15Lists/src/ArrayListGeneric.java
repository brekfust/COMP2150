/*
 * Revised version of our ArrayList class that uses generics.  The generic type parameter E
 * is a placeholder for the data type that you want to store in the list.  When you instantiate
 * the list, replace E with whatever type you want to store.
 * 
 */
public class ArrayListGeneric<E> implements ListGeneric<E> {

    private E[] data = (E[])(new Object[5]);	// Java doesn't support generic array instantiation, so this is a workaround
    private int size = 0;   // keeps track of how many items are currently in the list
    
    //BEGIN HOMEWORK METHODS
    public ArrayListGeneric<E> slice(int beginIndex, int endIndex) {
    	if (beginIndex < 0 || endIndex > this.size || beginIndex >= endIndex) {
    		throw new IndexOutOfBoundsException();
    	}
    	ArrayListGeneric<E> outList = new ArrayListGeneric<>();
    	for (int i = beginIndex; i < endIndex; i++) {
    		outList.add(this.get(i));
    	}
    	return outList;
    }
    
    public void addAll(ArrayListGeneric<E> anotherList) {
    	//can't get arraylist size, it's private, so go until we get an exception and
    	//just deal with it
    	int index = 0;
    	try {
    		while(true){
    			this.add(anotherList.get(index));
    			index++;
    		}
    	}
    	catch (ArrayIndexOutOfBoundsException e) {
    		//it's fine! we're done!
    	}
    }
    
    public void trimToSize() {
    	E[] newData = (E[])(new Object[size]);
    	for (int i = 0; i < size; i++) {
    		newData[i] = data[i];
    	}
    	data = newData;
    }
    
    // Returns the item at the specified index in the list.
    // Big-O: O(1)
    public E get(int index) {
        if (index >= 0 && index < size)
            return data[index];
        else
            throw new ArrayIndexOutOfBoundsException();
    }
    
    // Replaces the item at the specified index with the specified newItem.
    // Big-O: O(1)
    public void set(int index, E newItem) {
        if (index >= 0 && index < size)
            data[index] = newItem;
        else
            throw new ArrayIndexOutOfBoundsException();
    }

    // Adds the specified newItem to the end of the list.
    // Big-O: O(n) if array reallocation is needed, O(1) if not.  However,
    //  array reallocation is fairly rare since we double the list's capacity
    //  each time.  If we think about "averaging out" the rare O(n) calls
    //  over all the other O(1) calls, this turns out to be "amortized constant
    //  time" - O(1) overall.
    public void add(E newItem) {
        if (size == data.length) {			// if the array is full...
            E[] newData = (E[])(new Object[2*data.length]);	// create a new array of twice the length
            for (int i = 0; i < data.length; i++)           // copy over all the old elements
                newData[i] = data[i];
            data = newData;     // redirect data to point to the new array
        }
        data[size] = newItem;   // add the newItem to the back of the array
        size++;
    }
    

    
    // Removes and returns the item at the specified index in the list.
    // Big-O: O(n)
    public E remove(int index) {
        if (index >= 0 && index < size) {
            E temp = data[index];
            for (int i = index; i < size - 1; i++)  // move back all elements from index to the end of the array
                data[i] = data[i+1];
            size--;
            return temp;
        }
        else
            throw new ArrayIndexOutOfBoundsException();
    }
    
    public String toString() {
        String r = "ArrayListGeneric (size = " + size + ", capacity = " + data.length + "), containing: ";
        for (int i = 0; i < size; i++)
            r += data[i] + ", ";
        r = r.substring(0, r.length() - 2); // remove the last comma, because we're picky like that
        return r;
    }
    
    public static void main(String[] args) {
    	// myBetterList can store only Strings
        ArrayListGeneric<String> myBetterList = new ArrayListGeneric<>();
        myBetterList.add("3");
        myBetterList.add("batman");
        myBetterList.add("sloth");
        myBetterList.add("" + 6.37);
        myBetterList.add("1");
        System.out.println(myBetterList);
        
        myBetterList.add("flowers");
        System.out.println(myBetterList);
        
        System.out.println(myBetterList.remove(4));
        System.out.println(myBetterList);
        System.out.println(myBetterList.remove(4));
        System.out.println(myBetterList);
        System.out.println(myBetterList.remove(0));
        System.out.println(myBetterList);
        
        System.out.println("HOMEWORK TESTS");
        ArrayListGeneric<String> newList = new ArrayListGeneric<>();
        newList.addAll(myBetterList);
        newList.addAll(myBetterList);

        System.out.println(newList);
        newList.trimToSize();
        System.out.println(newList);
        ArrayListGeneric<String> slicedList = newList.slice(-1,1);
        slicedList.trimToSize();
        System.out.println(slicedList);

        // if you really wanted to, you could create a list within a list!
        ListGeneric<ListGeneric<String>> hugeList = new ArrayListGeneric<>();
        hugeList.add(myBetterList);
    }
}
