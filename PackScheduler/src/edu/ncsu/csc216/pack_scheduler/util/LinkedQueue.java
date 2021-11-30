package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Uses the Queue interface to create a Queue that operates as a LinkedList
 * 
 * @author Sydney Morisoli
 * @author Veerendra Gottiveeti
 * @param <E> the generic object
 *
 */
public class LinkedQueue<E> implements Queue<E> {
	/** Represents a LinkedAbstractList */
	private LinkedAbstractList<E> queue;

	/**
	 * Constructor for a LinkedQueue
	 * @param capacity the capacity of the new LinkedQueue
	 */
	public LinkedQueue(int capacity) {
		queue = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}
	
	/**
	 * Adds element to back of Queue
	 * 
	 * @param element the element to be added to the back of the queue
	 * @throws IllegalArgumentException if the capacity has been reached
	 */
	public void enqueue(E element) {
		queue.add(queue.size(), element);
	}

	/**
	 * Removes and return the element at the front of the Queue
	 * 
	 * @throws NoSuchElementException if the queue is empty
	 * @return the element at the front of the queue
	 */
	@SuppressWarnings("unchecked")
	public E dequeue() {
		if(queue.isEmpty()) {
			throw new NoSuchElementException();
		}
		Object front = queue.get(0);
		queue.remove(0);
		return (E) front;
	}

	/**
	 * Returns true if the Queue is empty
	 * 
	 * @return true if the queue is empty, false if not
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * Returns the number of elements in the Queue
	 * 
	 * @return the size of the queue
	 */
	public int size() {
		return queue.size();
	}

	/**
	 * Sets the Queue's capacity
	 * 
	 * @param capacity the new capacity of the queue
	 * @throws IllegalArgumentException if the parameter is negative or less than
	 *                                  the number of elements in the Queue
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < queue.size()) {
			throw new IllegalArgumentException();
		}
		
		queue.setCapacity(capacity);
	}


}