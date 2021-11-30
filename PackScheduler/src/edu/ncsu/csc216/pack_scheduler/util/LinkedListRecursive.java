/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Serves as a list using recursive methods within the ListNode to access/modifu
 * values within this LinkedList.
 * 
 * @author Maverick Middleton
 * @author Veerendra Gottiveeti
 * @param <E> generic type parameter
 *
 */
public class LinkedListRecursive<E> {

	/**
	 * Front of the LinkedAbstractList
	 */
	private ListNode front;

	/**
	 * Size of the LinkedAbstractList
	 */
	private int size;

	/**
	 * Serves as the constructor of the LinkedList with a null front
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}

	/**
	 * Checks if the list is empty or not.
	 * 
	 * @return If the size is equal to 0
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Provides the number of items in the list.
	 * 
	 * @return The size field's value.
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks for if the object is null or if it's already in the list, and then
	 * adds it to the end if it's not there right now.
	 * 
	 * @param element Element being added.
	 * @throws IllegalArgurmentException if the element is null or is already in the
	 *                                   list
	 * @return True if the element is added.
	 */
	public boolean add(E element) {

		if (element == null) {
			throw new IllegalArgumentException();
		}

		if (contains(element)) {
			throw new IllegalArgumentException();
		}

		add(this.size(), element);
		return true;

	}

	/**
	 * Adds the element to the list at the specified index.
	 * 
	 * @param i       The index of the added element.
	 * @param element The element being added.
	 * @throws NullPointerException      if the element is null
	 * @throws IllegalArgumentException  if the element is already in the list
	 * @throws IndexOutOfBoundsException if the index parameter is out of bounds for
	 *                                   the list's size
	 */
	public void add(int i, E element) {

		if (element == null) {
			throw new NullPointerException();
		}
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (i < 0 || i > size) {
			throw new IndexOutOfBoundsException();
		}
		if (i == 0) {
			front = new ListNode(element, front);
			size++;

		} else {
			front.add(i - 1, element);
		}
	}

	/**
	 * Provides the value at the specified index.
	 * 
	 * @param i The index of the value being returned.
	 * @throws IndexOufOfBoundsException if the index parameter is out of bounds for
	 *                                   the list's size
	 * @return The data of the node at the found index.
	 */
	public E get(int i) {

		if (i < 0 || i > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if (i == 0) {
			return front.data;
		}
		return front.get(i);

	}

	/**
	 * Removes the element from the list.
	 * 
	 * @param element The data of the node being removed.
	 * @return True if the element is removed, and false if not.
	 */
	public boolean remove(E element) {

		if (element == null || isEmpty()) {
			return false;
		}
		if (front.data.equals(element)) {
			front = front.next;
			size--;
			return true;
		} else {
			return front.remove(element);
		}

	}

	/**
	 * Removes the element at the given index.
	 * 
	 * @param i The index of the element being removed.
	 * @throws IndexOutOfBoundsException if the index parameter is out of bounds for
	 *                                   the list's size
	 * @return The value of the element being removed.
	 */
	public E remove(int i) {
		if (i < 0 || i > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if (i == 0) {

			E temp = front.data;

			front = front.next;

			size--;

			return temp;
		}

		else {
			return front.remove(i - 1);
		}
	}

	/**
	 * Sets the value at the specified index equal to the element.
	 * 
	 * @param i       The index of the object being replaced.
	 * @param element The element replacing the other one at the index.
	 * @throws IndexOutOfBoundsException if the index parameter is out of bounds for
	 *                                   the list's size
	 * @throws NullPointerException      if the element parameter is null
	 * @throws IllegalArgumentException  if the element parameter is already in the
	 *                                   list
	 * @return The element being replaced.
	 */
	public E set(int i, E element) {

		if (i < 0 || i > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (i == 0) {
			E returnValue = front.data;
			front.data = element;
			return returnValue;
		} else {
			return front.set(i - 1, element);
		}
	}

	/**
	 * Tells the user if the element already exists in the list.
	 * 
	 * @param element Element being searched for.
	 * @return True if the element already exists in the list.
	 */
	public boolean contains(E element) {
		if (element == null) {
			return false;
		} else if (isEmpty()) {
			return false;
		} else if (front.data.equals(element)) {
			return true;
		} else {
			return front.contains(element);
		}
	}

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

		/**
		 * Recursively goes through each element until it finds the right index, and
		 * then adds the element in.
		 * 
		 * @param i       The index of the element being added.
		 * @param element The element being added.
		 */
		public void add(int i, E element) {
			if (i == 0) {
				next = new ListNode(element, next);
				size++;
			} else {
				next.add(i - 1, element);
			}
		}

		/**
		 * Recursively loops through the list with a getter.
		 * 
		 * @param i Index of the element being returned.
		 * @return The element's value at the index.
		 */
		public E get(int i) {
			if (i == 1) {
				return next.data;
			} else {
				return next.get(i - 1);
			}
		}

		/**
		 * Removes the object at the provided index.
		 * 
		 * @param i The index of the object being removed.
		 * @return The value of the removed object.
		 */
		public E remove(int i) {
			if (next != null) {
				if (i == 0) {
					E returnValue = next.data;
					size--;
					next = next.next;
					return returnValue;
				} else {
					next.remove(i - 1);
				}
			}
			return null;
		}

		/**
		 * Removes the element with the given value.
		 * 
		 * @param element Element being removed
		 * @return True if the value was removed, or false if not.
		 */
		public boolean remove(E element) {
			if (next == null) {
				return false;
			} else {
				if (next.data.equals(element)) {
					next = next.next;
					size--;
					return true;
				} else {
					return next.remove(element);
				}
			}
		}

		/**
		 * Sets the value of the element at the index.
		 * 
		 * @param i       The index of the element.
		 * @param element The value replacing the one currently at the index.
		 * @return The element that was replaced.
		 */
		public E set(int i, E element) {
			if (i == 0) {
				E temp = next.data;
				next.data = element;
				return temp;
			}

			else {
				return next.set(i - 1, element);
			}
		}

		/**
		 * Recursively loops through each element to see if any of them contain the
		 * element.
		 * 
		 * @param element The element being searched for.
		 * @return If the element exists there or not.
		 */
		public boolean contains(E element) {
			if (next == null) {
				return false;
			} else if (next.data == element) {
				return true;
			} else {
				return next.contains(element);
			}
		}

	}

}
