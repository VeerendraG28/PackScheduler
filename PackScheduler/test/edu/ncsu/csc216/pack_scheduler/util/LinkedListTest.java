package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListTest {

	/**
	 * The method tests the constructor.
	 */
	@Test
	void constructorTest() {
		LinkedList<String> tester = new LinkedList<String>();
		assertEquals(tester.size(), 0);
	}

	/**
	 * The method tests the LinkedList in terms of size
	 */
	@Test
	void testLinkedList() {
		LinkedList<Object> a = new LinkedList<Object>();
		
		assertEquals(0, a.size(), "incorrect array list size");
	}
	
	/**
	 * The method tests the remove method with size and .remove.
	 */
	@Test
	void testRemove() {
		LinkedList<Object> a = new LinkedList<Object>();
		Object object = new Object();
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.remove(-1));
		
		a.add(0, object);
		assertEquals(1, a.size());
		a.remove(0);
		assertEquals(0, a.size());
	}
	
	/**
	 * Tests the add function by adding elements and looking at size
	 */
	@Test
	void testAdd() {
		LinkedList<Object> a = new LinkedList<Object>();
		Object object = new Object();
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(-1, object));
		
		assertThrows(NullPointerException.class, () -> a.add(0, null));
			
		LinkedList<Object> a1 = new LinkedList<Object>();
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
		
		
		
		LinkedList<String> tester = new LinkedList<String>();
		
		String hi = "hi";
		
		String howdy = "howdy!";
		
		String desperado = "desperado";
		
		tester.add(0, howdy);
		assertEquals(tester.get(0), "howdy!");
		tester.add(1, hi);
		
		assertNotNull(tester.set(0, desperado));
		assertThrows(IndexOutOfBoundsException.class, () -> tester.set(-1, howdy));
		assertThrows(IllegalArgumentException.class, () -> tester.set(0, desperado));
		assertThrows(NullPointerException.class, () -> tester.set(0, null));

	}
	
	/**
	 * Tests the Linkedlist object's set() method
	 */
	@Test
	public void testSetIntE() {
		
		LinkedList<String> linkedList = new LinkedList<String>();
		
		String s1 = "North Carolina";
		String s2 = "South Carolina";
		String s3 = "California";
		
		try {
			linkedList.set(0, s1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, linkedList.size());
		}

		linkedList.add(0, s1);
		assertEquals("North Carolina", s1);
		linkedList.add(1, s2);

		assertEquals(2, linkedList.size());
		linkedList.set(0, s3);
		
	}
	
	/**
	 * Tests unused
	 */
	@Test
	public void testIteratorReturns() {
		LinkedList<String> linkedList = new LinkedList<String>();
		String s1 = "North Carolina";
		String s2 = "South Carolina";
		linkedList.add(0, s1);
		linkedList.add(1, s2);
		assertFalse(linkedList.listIterator(0).hasPrevious());
		assertEquals(linkedList.listIterator(0).nextIndex(), -1);
		assertEquals(linkedList.listIterator(0).previousIndex(), -1);
		assertEquals(linkedList.listIterator(1).nextIndex(), 0);
		assertEquals(linkedList.listIterator(1).previousIndex(), 0);
		assertEquals(linkedList.listIterator(2).previous(), "North Carolina");
		

		
	}
	
	
}
