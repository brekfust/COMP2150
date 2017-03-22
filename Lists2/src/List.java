
/**
 * This interface specifies the basic list operations.
 * We'll implement it in two different ways: using an array list
 * and a linked list.
 * 
 * @author Top Malasri
 * @version Feb. 22, 2017
 */
public interface List
{
    // Returns the item at the specified index in the list.
    Object get(int index);
    
    // Replaces the item at the specified index with the specified newItem.
    void set(int index, Object newItem);

    // Adds the specified newItem to the end of the list.
    void add(Object newItem);
    
    // Removes and returns the item at the specified index in the list.
    Object remove(int index);
}
