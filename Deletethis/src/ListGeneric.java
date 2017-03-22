/*
 * Revised version of our List interface that uses generics.  The generic type parameter E
 * is a placeholder for the data type that you want to store in the list.  When you instantiate
 * the list, replace E with whatever type you want to store.
 * 
 */
public interface ListGeneric<E> {
    // Returns the item at the specified index in the list.
    E get(int index);
    
    // Replaces the item at the specified index with the specified newItem.
    void set(int index, E newItem);

    // Adds the specified newItem to the end of the list.
    void add(E newItem);
    
    // Removes and returns the item at the specified index in the list.
    E remove(int index);
}
