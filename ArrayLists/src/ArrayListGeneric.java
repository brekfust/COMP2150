/*
 * Revised version of our ArrayList class that uses generics.  The generic type parameter E
 * is a placeholder for the data type that you want to store in the list.  When you instantiate
 * the list, replace E with whatever type you want to store.
 * 
 */
public class ArrayListGeneric<E> implements ListGeneric<E> {

    private E[] data = (E[])(new Object[5]);	// Java doesn't support generic array instantiation, so this is a workaround
    private int size = 0;   // keeps track of how many items are currently in the list
    
    // Returns the item at the specified index in the list.
    public E get(int index) {
        if (index >= 0 && index < size)
            return data[index];
        else
            throw new ArrayIndexOutOfBoundsException();
    }
    
    // Replaces the item at the specified index with the specified newItem.
    public void set(int index, E newItem) {
        if (index >= 0 && index < size)
            data[index] = newItem;
        else
            throw new ArrayIndexOutOfBoundsException();
    }

    // Adds the specified newItem to the end of the list.
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
        ListGeneric<String> myBetterList = new ArrayListGeneric<>();
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

        // if you really wanted to, you could create a list within a list!
        ListGeneric<ListGeneric<String>> hugeList = new ArrayListGeneric<>();
        hugeList.add(myBetterList);
    }
}
