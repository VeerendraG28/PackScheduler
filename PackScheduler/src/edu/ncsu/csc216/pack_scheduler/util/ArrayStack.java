package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Serves as a stack object that uses an ArrayList.
 * 
 * @author Maverick Middleton
 * @author Veerendra Gottiveeti
 *
 * @param <E> The element provided.
 */
public class ArrayStack<E> implements Stack<E> {

	/**
	 * Serves as the ArrayList of objects used in the ArrayStack.
	 */
	private ArrayList<E> list;

	/**
	 * The allowed number of objects that can be added to the Stack.
	 */
	private int capacity;

	/**
	 * Serves as the ArrayStack's constructor.
	 * 
	 * @param capacity The number of objects that can be within the Stack.
	 */
	public ArrayStack(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds the element to the top of the stack
	 * 
	 * @param element Provided element to add to the top.
                            reached).
     * @throws IllegalArgumentException if the list is at capacity
	 */
	@Override
	public void push(E element) {
		if(list.size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(0, element);

	}

	/**
	 * Removes the top element of the stack and returns it.
	 * 
	 * @return The top element in the stack.
	 * @throws EmptyStackException If there is no elements left to pop.
	 */
	@Override
	public E pop() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		return list.remove(0);
	}

	/**
	 * Checks if the Stack is empty.
	 * 
	 * @return True if it's empty, false if it's not.
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	/**
	 * Returns the number of objects in the stack.
	 * 
	 * @return An integer of how many objects exist in the stack.
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the capacity of the Stack.
	 * 
	 * @param capacity The number of objects that can be in the stack.
	 * @throws IllegalArgumentException if the parameter is invalid.
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

}