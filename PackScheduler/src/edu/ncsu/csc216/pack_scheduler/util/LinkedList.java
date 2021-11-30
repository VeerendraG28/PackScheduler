package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Serves as the new LinkedList for the faculty.
 * 
 * @author Maverick Middleton
 * @author Veerendra Gottiveeti
 *
 * @param <E> Object provided that's held inside the list.
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	private class ListNode {
		/**
		 * Data used in the ListNode.
		 */
		public E data;

		/**
		 * Serves as the link for the next ListNode to allow the SortedList to have a
		 * virtually infinite length.
		 */
		public ListNode next;

		/**
		 * Serves as the link for the previous ListNode to allow the LinkedList to go
		 * backwards when looking for a value as well.
		 */
		public ListNode prev;

		/**
		 * Serves as a constructor for the front value.
		 * 
		 * @param data Data being given to the node.
		 */
		public ListNode(E data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}

		/**
		 * Serves as the constructor for values with a provided next value.
		 * 
		 * @param data The data provided for the null.
		 * @param next The link to the next node in the list (or null if there isn't
		 *             one).
		 * @param prev The link to the prev node in the list (or null if there isn't
		 *             one).
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}

	}

	/**
	 * Serves as the functionality for all the Standard List Methods
	 * 
	 * @author Maverick Middleton
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {

		/**
		 * List node that would be returned to previous.
		 */
		public ListNode previous;

		/**
		 * List node that would be returned to next.
		 */
		public ListNode next;

		/**
		 * Index that would be returned on a call to previous index.
		 */
		public int previousIndex;

		/**
		 * The index that would be returned from nextIndex()
		 */
		public int nextIndex;

		/**
		 * The last listNode used in the methods.
		 */
		private ListNode lastRetrieved;

		/**
		 * Serves as the constructor for the LinkedListIterator at the specific index in
		 * the list.
		 * 
		 * @param index being used for next values.
		 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
		 *                                   the size of the list
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			nextIndex = index;
			previousIndex = index - 1;
			previous = front;
			next = previous.next;

			if (index != 0) {
				for (int i = 0; i < index; i++) {
					previous = next;
					next = next.next;
				}
			}

		}

		/**
		 * Adds the specified item into the list
		 * 
		 * @param arg0 the item to be added to the list
		 * @throws NullPointerException if the parameter is null
		 */
		@Override
		public void add(E arg0) {

			if (arg0 == null) {
				throw new NullPointerException();
			}

			ListNode addedNode = new ListNode(arg0);
			next.prev = addedNode;
			addedNode.next = next;
			addedNode.prev = previous;
			previous.next = addedNode;

			lastRetrieved = null;

			size++;

		}

		/**
		 * Returns whether or not the next node contains any data
		 * 
		 * @return true if the next node has data, false if not
		 */
		@Override
		public boolean hasNext() {
			return !(next.data == null);
		}

		/**
		 * Returns whether or not the previous node contains any data
		 * 
		 * @return true if the previous node has data, false if not
		 */
		@Override
		public boolean hasPrevious() {
			return !(previous.data == null);
		}

		/**
		 * Returns the next node's data
		 * 
		 * @throws NoSuchElementException if the next node does not have any data
		 * @return the next node's data
		 */
		@Override
		public E next() {
			if (hasNext()) {
				lastRetrieved = next;
				E output = next.data;
				nextIndex++;
				previousIndex++;
				next = next.next;
				return output;
			} else {
				throw new NoSuchElementException();
			}
		}

		/**
		 * Returns the nextIndex - 1
		 * 
		 * @return size if the next node is null, nextIndex - 1 if not
		 */
		@Override
		public int nextIndex() {
			if (next == null) {
				return size;
			}
			return nextIndex - 1;
		}

		/**
		 * Returns the previous node's data, if any exists
		 * 
		 * @throws NoSuchElementException if the previous data does not exist
		 * @return the previous node's data
		 */
		@Override
		public E previous() {
			if (hasPrevious()) {
				lastRetrieved = previous;
				nextIndex--;
				previousIndex--;
				previous = previous.prev;
				return previous.data;
			} else {
				throw new NoSuchElementException();
			}
		}

		/**
		 * Returns the previousIndex
		 * 
		 * @return -1 if the previous node is null, previousIndex if otherwise
		 */
		@Override
		public int previousIndex() {
			if (previous == null) {
				return -1;
			}
			return previousIndex;
		}

		/**
		 * Removes a node from the list
		 * 
		 * @throws IllegalStateException if the lastRetreived node is null
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}

			lastRetrieved.prev.next = next;
			next = next.next;
			size--;

		}

		/**
		 * Sets the lastRetrieved node to be the element parameter
		 * 
		 * @param element the new node to be set in the list
		 * @throws IllegalStateException if the lastRetrieved node is null
		 * @throws NullPointerException  if the element parameter is null
		 */
		@Override
		public void set(E element) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}

			if (element == null) {
				throw new NullPointerException();
			}

			lastRetrieved.data = element;
		}

	}

	/**
	 * First listNode in the LinkedList
	 */
	private ListNode front;

	/**
	 * Last listNode in the LinkedList
	 */
	private ListNode back;

	/**
	 * Number of elements inside the list.
	 */
	private int size;

	/**
	 * Serves as the constructor for the LinkedList
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null, front, null);
		front.next = back;
		size = 0;

	}

	/**
	 * Creates a new LinkedListIterator with the parameter given
	 * 
	 * @param arg0 the parameter used to create the LinkedListIterator
	 * @return the newly created LinkedListIterator
	 */
	@Override
	public ListIterator<E> listIterator(int arg0) {
		return new LinkedListIterator(arg0);
	}

	/**
	 * Adds an element at the given index
	 * 
	 * @param index   the index of the element to be added
	 * @param element the object to be added to the list
	 * @throws IndexOutOfBoundsException if the given index does not fall in the
	 *                                   valid index bounds
	 * @throws IllegalArgumentException  if the element is already in the list
	 */
	@Override
	public void add(int index, E element) {

		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size; i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		LinkedListIterator iterator = new LinkedListIterator(index);
		iterator.add(element);
	}

	/**
	 * Sets the given element at the specified index
	 * @param index the index for the object to be set at
	 * @param element the object to be set to the list
	 * @throws IndexOutOfBoundsException if the index parameter does not meet the list's size boundaries
	 * @throws IllegalArgumentException if the object already exists in the list
	 * @return output the next object in the iterator
	 */
	@Override
	public E set(int index, E element) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i < size; i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		LinkedListIterator iterator = new LinkedListIterator(index);
		E output = iterator.next();
		iterator.set(element);
		return output;
	}

	/**
	 * Returns the number of objects inside the LinkedList.
	 * @return size the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

}
