package edu.ncsu.csc216.pack_scheduler.util;

/**
 * This interface represents a queue that will help utilize either an ArrayQueue
 * or LinkedQueue
 * 
 * @author Sydney Morisoli
 * @author Veerendra Gottiveeti
 *
 * @param <E> the generic object
 */
public interface Queue<E> {

	/**
	 * Adds element to back of Queue
	 * 
	 * @param element the element to be added to the back of the queue
	 * @throws IllegalArgumentException if the capacity has been reached
	 */
	void enqueue(E element);

	/**
	 * Removes and return the element at the front of the Queue
	 * 
	 * @return the element at the front of the queue
	 */
	E dequeue();

	/**
	 * Returns true if the Queue is empty
	 * 
	 * @return true if the queue is empty, false if not
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the Queue
	 * 
	 * @return the size of the queue
	 */
	int size();

	/**
	 * Sets the Queue's capacity
	 * 
	 * @param capacity the new capacity of the queue
	 * @throws IllegalArgumentException if the parameter is negative or less than
	 *                                  the number of elements in the Queue
	 */
	void setCapacity(int capacity);
	
}
