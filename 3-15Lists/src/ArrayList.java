
/**
 * Implements the List interface using an array as the underlying
 * data structure.  We keep an array of Objects as an instance
 * variable, creating a new larger array whenever it becomes full.
 * 
 * @author Top Malasri
 * @version Feb. 22, 2017
 */
import java.util.Scanner;

public class ArrayList implements List
{
    private Object[] data = new Object[5];
    private int size = 0;   // keeps track of how many items are currently in the list
    
    // Returns the item at the specified index in the list.
    public Object get(int index)
    {
        if (index >= 0 && index < size)
            return data[index];
        else
            throw new ArrayIndexOutOfBoundsException();
    }
    
    // Replaces the item at the specified index with the specified newItem.
    public void set(int index, Object newItem)
    {
        if (index >= 0 && index < size)
            data[index] = newItem;
        else
            throw new ArrayIndexOutOfBoundsException();
    }

    // Adds the specified newItem to the end of the list.
    public void add(Object newItem)
    {
        if (size == data.length)        // if the array is full...
        {
            Object[] newData = new Object[2*data.length];   // create a new array of twice the length
            for (int i = 0; i < data.length; i++)           // copy over all the old elements
                newData[i] = data[i];
            data = newData;     // redirect data to point to the new array
        }
        data[size] = newItem;   // add the newItem to the back of the array
        size++;
    }
    
    // Removes and returns the item at the specified index in the list.
    public Object remove(int index)
    {
        if (index >= 0 && index < size)
        {
            Object temp = data[index];
            for (int i = index; i < size - 1; i++)  // move back all elements from index to the end of the array
                data[i] = data[i+1];
            size--;
            return temp;
        }
        else
            throw new ArrayIndexOutOfBoundsException();
    }
    
    public String toString()
    {
        String r = "ArrayList (size = " + size + ", capacity = " + data.length + "), containing: ";
        for (int i = 0; i < size; i++)
            r += data[i] + ", ";
        r = r.substring(0, r.length() - 2); // remove the last comma, because we're picky like that
        return r;
    }
    
    public static void main(String[] args)
    {
        ArrayList myList = new ArrayList();
        myList.add(3);
        myList.add("batman");
        myList.add("sloth");
        myList.add(6.37);
        myList.add(1);
        System.out.println(myList);
        
        myList.add("flowers");
        System.out.println(myList);
        
        System.out.println(myList.remove(4));
        System.out.println(myList);
        System.out.println(myList.remove(4));
        System.out.println(myList);
        System.out.println(myList.remove(0));
        System.out.println(myList);
    }
}
