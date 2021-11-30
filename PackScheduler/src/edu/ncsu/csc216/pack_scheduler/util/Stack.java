package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Serves as the interface for a Stack class using either LinkedLists or
 * ArrayLists
 * 
 * @author Maverick Middleton
 * @author Veerendra Gottiveeti
 * @param <E> Any sort of object provided.
 *
 */
public interface Stack<E> {
	/**
	 * Adds the element to the top of the stack
	 * 
	 * @param element Provided element to add to the top.
	 */
	void push(E element);

	/**
	 * Removes the top element of the stack and returns it.
	 * 
	 * @return The top element in the stack.
	 */
	E pop();

	/**
	 * Checks if the Stack is empty.
	 * 
	 * @return True if it's empty, false if it's not.
	 */
	boolean isEmpty();

	/**
	 * Returns the number of objects in the stack.
	 * 
	 * @return An integer of how many objects exist in the stack.
	 */
	int size();

	/**
	 * Sets the capacity of the Stack.
	 * 
	 * @param capacity The number of objects that can be in the stack.
	 * @throws IllegalArgumentException if the parameter is invalid.
	 */
	void setCapacity(int capacity);

}