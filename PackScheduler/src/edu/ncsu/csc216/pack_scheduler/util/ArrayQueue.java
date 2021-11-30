package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * This class creates an ArrayQueue, which implements the Queue interface to
 * design an array-based queue
 * 
 * @author Sydney Morisoli
 * @param <E> the generic object
 *
 */
public class ArrayQueue<E> implements Queue<E> {
	/** Represents a queue arrayList */
	private ArrayList<E> queue;
	/** The capacity of the ArrayList */
	private int capacity;
	
	/**
	 * Constructor for an ArrayQueue
	 * @param capacity the capacity of the new LinkedQueue
	 */
	public ArrayQueue(int capacity) {
		queue = new ArrayList<>();
		setCapacity(capacity);
	}
	
	/**
	 * Adds element to back of Queue
	 * 
	 * @param element the element to be added to the back of the queue
	 * @throws IllegalArgumentException if the capacity has been reached
	 */
	@Override
	public void enqueue(E element) {
		if(capacity == queue.size()) {
			throw new IllegalArgumentException();
		}
		queue.add(queue.size(), element);
	}

	/**
	 * Removes and return the element at the front of the Queue
	 * 
	 * @throws NoSuchElementException if the queue is empty
	 * @return the element at the front of the queue
	 */
	@Override
	public E dequeue() {
		if(queue.isEmpty()) {
			throw new NoSuchElementException();
		}
		return queue.remove(0);
	}

	/**
	 * Returns true if the Queue is empty
	 * 
	 * @return true if the queue is empty, false if not
	 */
	@Override
	public boolean isEmpty() {
		return queue.size() == 0;
	}

	/**
	 * Returns the number of elements in the Queue
	 * 
	 * @return the size of the queue
	 */
	@Override
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
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < queue.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

}