package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * The class serves as a LinkedList with a capacity. The class extends the
 * abstract list. The class consists of the methods get, size, add, remove, and
 * set. The fields of the class include front and size and capacity. The class
 * works with other classes by utilizing a linked list and extending the
 * abstract list.
 * 
 * @author Maverick Middleton
 * @author Susmitha Potu
 * @author Veerendra Gottiveeti
 *
 * @param <E> Type of Object Provided
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/**
	 * Front of the LinkedAbstractList
	 */
	private ListNode front;

	/**
	 * Back of the LinkedAbstractList
	 */
	private ListNode back;

	/**
	 * Size of the LinkedAbstractList
	 */
	private int size;

	/**
	 * Capacity of the LinkedAbstractList
	 */
	private int capacity;

	/**
	 * Serves as LinkedNode in LinkedAbstractList
	 * 
	 * @author Maverick Middleton
	 *
	 */
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
		 * Serves as a constructor for the front value.
		 * 
		 * @param data Data being given to the node.
		 */
		public ListNode(E data) {
			this(data, null);
		}

		/**
		 * Serves as the constructor for values with a provided next value.
		 * 
		 * @param data The data provided for the null.
		 * @param next The link to the next node in the list (or null if there isn't
		 *             one).
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

	}

	/**
	 * The method get gets at a particular index, and for that index there is errors
	 * checking and if it is out of those bounds then you throw the index out of
	 * bounds exception. There is a use of an if else for if the index is 0 or
	 * other, which in the cause the index is 0 it is the first element making it
	 * the front.data.
	 * 
	 * @param index the index at which the linked list is getting.
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   or equal to the size.
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			return front.data;
		} else {
			ListNode current = front;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
			return current.data;
		}
	}

	/**
	 * This is an override method to return the size of the list.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Serves as the basic constructor of the LinkedAbstractList
	 * 
	 * @param capacity Allowed capacity of the LinkedAbstractList
	 * @throws IllegalArgumentException if the capacity is less than 0.
	 */
	public LinkedAbstractList(int capacity) {
		front = null;
		size = 0;
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * This is an override add method with the parameters as index and elements. If
	 * the size is equal to the capacity then an IllegalArgumentException is thrown
	 * and if the element is null then a null pointer is thrown. The size in
	 * incremented at the end.
	 * 
	 * @throws IllegalArgumentException  if the size is equal to the capacity.
	 * @throws NullPointerException      if the element is null.
	 * @throws IndexOutOfBoundsException fi the index is less than 0 or greater than
	 *                                   the size.
	 */
	@Override
	public void add(int index, E element) {
		if (size == capacity) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		
		for(int i = 0; i < size; i++) {
			if(element.equals(get(i))) {
				throw new IllegalArgumentException();
			}
		}
		
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		

		if(front == null){
			//Add to Empty List
			front = new ListNode((E) element);
		} else if(index == 0) {
			//Add to Front
			ListNode temp = front;
			front = new ListNode((E) element, temp);
		} else if(index == size){
			//Add to end
			ListNode current = front;
			while(current.next != null) {
				current = current.next;
				
			}
			back = current;
			back.next = new ListNode((E) element);
		} else {
			//Add to middle
			ListNode current = front;
			ListNode temp = null;
		
			for(int i = 0; i <= index - 1; i++) {
				if(i < index - 1){
					current = current.next;
				} else {
					temp = current;
					current.next = new ListNode((E) element, temp.next);
				} 
			}
		}
		size++;
	}

	/**
	 * The method involves the remove at a certain index which is the parameter. The
	 * size is decremented and value is returned
	 * 
	 * @param idx index at which the linked list is to remove.
	 * @return value which is the current.next.data
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   or equal to the size.
	 */
	@Override
	public E remove(int idx) {
		ListNode current = front;
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E value = null;
		if (idx == 0) {
			value = front.data;
			front = front.next;
		} else if(idx == size) {
			back = null;
		}
		else {
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			value = current.next.data;
			current.next = current.next.next;
		}
		size--;
		current = front;
		for (int i = 0; i < size; i++) {
			current = current.next;
		}
		back = current;
		return value;
	}

	/**
	 * This is an override method which is called set and uses the parameters index
	 * and element. There is error checking.
	 * 
	 * @param index   at which the element is being set.
	 * @param element which is the element that is being set.
	 * @throws IllegalArgumentException  if the current data and element are equal.
	 * @throws NullPointerException      if the element is null.
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   the size.
	 * @return data which is the data that is being searched and then set.
	 */
	@Override
	public E set(int index, E element) {
		E data = null;
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		while (current != null) {
			if (current.data.equals(element)) {
				throw new IllegalArgumentException();
			}
			current = current.next;
		}
		if (index == 0) {
			if (front.data != null) {
				data = front.data;
			}
			front.data = element;
		} else {
			int searcher = 0;
			ListNode searcherr = front;
			while (searcher < index) {
				searcherr = searcherr.next;
				searcher++;
			}
			data = searcherr.data;
			searcherr.data = element;
		}
		return data;

	}

	/**
	 * Sets the capacity of the list equal to the new input if it's valid.
	 * 
	 * @param capacity The provided capacity
	 * @throws IllegalArgumentException if it's less than the size or 0.
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < this.size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
}