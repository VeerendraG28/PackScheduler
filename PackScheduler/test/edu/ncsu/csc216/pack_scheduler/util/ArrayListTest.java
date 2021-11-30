package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * The class ArrayList test tests the custom array list class. The class tests using various methods which invlude
 * testing the constructor, we also test array list and test remove and add and set. This ensures
 * the custom array list works in relation to the other classes in terms of accuracy. 
 * @author  Maverick Middleton
 * @author Susmitha Potu
 * @author Veerendra Gottiveeti
 *
 */
class ArrayListTest {

	/**
	 * The method tests the constructor.
	 */
	@Test
	void constructorTest() {
		ArrayList<String> tester = new ArrayList<String>();
		assertEquals(tester.size(), 0);
	}

	/**
	 * The method tests the Arraylist in terms of size.
	 */
	@Test
	void testArrayList() {
		ArrayList<Object> a = new ArrayList<Object>();
		
		assertEquals(0, a.size(), "incorrect array list size");
	}
	
	/**
	 * The method tests the remove method with size and .remove.
	 */
	@Test
	void testRemove() {
		ArrayList<Object> a = new ArrayList<Object>();
		Object object = new Object();
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.remove(-1));
		
		a.add(0, object);
		a.remove(0);
		assertEquals(0, a.size());
	}
	
	/**
	 * Tests the add function by adding elements and looking at size
	 */
	@Test
	void testAdd() {
		ArrayList<Object> a = new ArrayList<Object>();
		Object object = new Object();
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(-1, object));
		
		assertThrows(NullPointerException.class, () -> a.add(1, null));
			
		ArrayList<Object> a1 = new ArrayList<Object>();
		Object car = new Object();
		Object truck = new Object();
		
		a1.add(0, car);
		a1.add(0, truck);
		assertEquals(2, a1.size());
	}
	
	/**
	 * The method tests the set method of the array list class.
	 */
	@Test
	void testSet() {
		
		
		
		ArrayList<String> tester = new ArrayList<String>();
		
		String hi = "hi";
		
		String howdy = "howdy!";
		
		String desperado = "desperado";
		
		tester.add(0, howdy);
		tester.add(1, hi);
		
		assertNotNull(tester.set(0, desperado));
		assertThrows(IndexOutOfBoundsException.class, () -> tester.set(-1, howdy));
		assertThrows(IllegalArgumentException.class, () -> tester.set(0, desperado));
		assertThrows(NullPointerException.class, () -> tester.set(0, null));
		
		
		
	}
	
}