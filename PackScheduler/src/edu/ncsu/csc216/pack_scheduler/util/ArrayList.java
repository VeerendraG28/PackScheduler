package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Creates a general ArrayList class that accomplishes all the basic
 * requirements of a normal ArrayList.
 * 
 * @author Maverick Middleton
 *
 * @param <E> The Type of Object worked with in the ArrayList
 */
public class ArrayList<E> extends AbstractList<E> {

	/**
	 * Initial size of the ArrayList's list.
	 */
	private static final int INIT_SIZE = 10;

	/**
	 * Array containing all the objects.
	 */
	private E[] list;

	/**
	 * The size represents the number of objects within the array.
	 */
	private int size;

	/**
	 * Returns the object at the given index.
	 * @param index is the location of the object in the array.
	 * @return the object found at that index.
	 * @throws IndexOutOfBoundsException if the index is less than zero and index is greater than or equal to size.
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}

	/**
	 * Provides the current size field's value.
	 * 
	 * @return the number of items in the list.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Serves as the general constructor for the ArrayList, providing the list
	 * object a number of values = to 10 initially.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
		size = 0;
	}

	/**
	 * Adds an object to the array, and moves all objects past the index point down,
	 * expanding the list's maximum size.
	 * @param index the location the object is being added.
	 * @param object is the object being added to the ArrayList.
	 * @throws IllegalArgumentException if the list of i equals the element.
	 * @throws IndexOutOfBoundsException if the index is less than zero or greater 
	 */
	@Override
	public void add(int index, E object) {
		
		if (object == null) {
			throw new NullPointerException();
		}
		E temporary = object;
		for (int i = 0; i < size(); i++) {
			if (list[i].equals(object)) {
				throw new IllegalArgumentException();
			}
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (list[index] == null) {
			list[index] = object;
		} else {
			temporary = list[index];
			list[index] = object;
		}
		for (int i = size; i > index; i--) {
			if (i == index + 1) {
				list[i] = temporary;
				break;
			}
			list[i] = list[i - 1];
		}
		size++;
		if (size == list.length) {
			growArray();
		} 
		// Past AddMethod
		
//		if (object == null) {
//			throw new NullPointerException();
//		}
//		for (int i = 0; i < size; i++) {
//			if (list[i].equals(object)) {
//				throw new IllegalArgumentException();
//			}
//		}
//		if (index < 0 || index > size()) {
//			throw new IndexOutOfBoundsException();
//		}
//		
//		if(size == list.length - 1) {
//			E[] temp = (E[]) new Object[size * 2];
//			for (int i = 0; i < size; i++) {
//				temp[i] = list[i];
//			}
//			list = temp;
//		}
//		
//		if(index == 0) {
//			for (int i = 0; i >= 0; i--) {
//				list[i + 1] = list[i];
//			}
//			list[0] = object;
//			size++;
//		}
//		
//		else if(index > 0 || index < size - 1) {
//			for(int i = size; i >= index; i--) {
//				list[i + 1] = list[i];
//			}
//			list[0] = object;
//			size++;
//		}
//		
//		else if(index >= size && index != 0) {
//			list[index] = object;
//			size++;
//		}
	}
	
	/**
	 * Removes an object at the given index before returning the removed value.
	 * @param index the location the object is being added.
	 * @return the object being removed.
	 * @throws IndexOutOfBoundsException if the index is less than zero or greater 
	 */
	@Override
	public E remove(int index) {
//		E output = null;
//		if (index < 0 || index >= size()) {
//			throw new IndexOutOfBoundsException();
//		}
//		output = list[index];
//		if (index == size - 1) {
//			list[index] = null;
//		} else {
//			for (int i = index; i < size; i++) {
//				if (i == size - 1) {
//					list[i] = null;
//				} else {
//					list[i] = list[i + 1];
//				}
//
//			}
//		}
//		size--;
//		return output;
		
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E output = list[index];
		
		for(int i = index; i < size; i++) {
			list[i] = list[i + 1];
		}
		list[size - 1] = null;
		size--;
		return output;
	}
	
	/**
	 * Sets a specific value equal to a new value provided.
	 * @param object being set to the index in the ArrayList.
	 * @param index the location the object is being added.
	 * @return E the object being replaced.
	 * @throws IndexOutOfBoundsException if the index is less than zero or greater 
	 * @throws IllegalArgumentException if the list of i equals the element.
	 * 
	 */
	@Override
	public E set(int index, E object) {
		if (object == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size; i++) {
			if (list[i].equals(object)) {
				throw new IllegalArgumentException();
			}
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E output = get(index);
		list[index] = object;
		return output;
	}
	
	/**
	 * Doubles the maximum size of the list so more values can be added.
	 * The method will grow the array 
	 */
	private void growArray() {
		@SuppressWarnings("unchecked")
		E[] list2 = (E[]) new Object[size * 2];
		for (int i = 0; i < list.length; i++) {
			list2[i] = list[i];
		}
		list = list2;
	}

}