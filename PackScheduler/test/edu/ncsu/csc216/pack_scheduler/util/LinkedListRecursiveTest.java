/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedListRecursive class
 * @author Veerendra Gottiveeti
 * @author Sydney Morisoli
 *
 */
class LinkedListRecursiveTest {

	/**
	 * The method tests the constructor.
	 */
	@Test
	void constructorTest() {
		LinkedListRecursive<String> tester = new LinkedListRecursive<String>();
		assertEquals(tester.size(), 0);
	}

	/**
	 * The method tests the LinkedList in terms of size
	 */
	@Test
	void testLinkedList() {
		LinkedListRecursive<Object> a = new LinkedListRecursive<Object>();
		
		assertEquals(0, a.size(), "incorrect array list size");
	}
	
	/**
	 * The method tests the remove method with size and .remove.
	 */
	@Test
	void testRemove() {
		LinkedListRecursive<Object> a = new LinkedListRecursive<Object>();
		String howdy = "howdy";
		
		String man = "man";
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.remove(-1));
		
		a.add(0, howdy);
		assertEquals(1, a.size());
		a.add(1, man);
		a.remove(1);
		assertEquals(1, a.size());
		a.remove(0);
		assertEquals(0, a.size());
	}
	
	/**
	 * Tests the add function by adding elements and looking at size
	 */
	@Test
	void testAdd() {
		LinkedListRecursive<Object> a = new LinkedListRecursive<Object>();
		Object object = new Object();
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(-1, object));
		
		assertThrows(NullPointerException.class, () -> a.add(0, null));
			
		LinkedList<Object> a1 = new LinkedList<Object>();
		Object car = new Object();
		Object truck = new Object();
		
		a1.add(0, car);
		a1.add(0, truck);
		assertEquals(2, a1.size());
		
		LinkedListRecursive<String> linkedList = new LinkedListRecursive<String>();
		
		String s0 = null;
		String s1 = "North Carolina";

		try {
			linkedList.add(0, s0);
			fail();
		} catch (NullPointerException e) {
			assertEquals(0, linkedList.size());
		}
		try {
			linkedList.add(-1, s1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, linkedList.size());
		}
		try {
			linkedList.add(2, s1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, linkedList.size());
		}
	}
	
	
	
	/**
	 * The method tests the set method of the array list class.
	 */
	@Test
	void testSet() {
		
		
		
		LinkedListRecursive<String> tester = new LinkedListRecursive<String>();
		
		String hi = "hi";
		
		String howdy = "howdy!";
		
		String desperado = "desperado";
		
		String despacito = "despacito";
		
		tester.add(0, howdy);
		assertEquals(tester.get(0), "howdy!");
		tester.add(1, hi);
		
		assertNotNull(tester.set(0, desperado));
		assertNotNull(tester.set(1, despacito));
		assertThrows(IndexOutOfBoundsException.class, () -> tester.set(-1, howdy));
		assertThrows(IllegalArgumentException.class, () -> tester.set(0, desperado));
		assertThrows(NullPointerException.class, () -> tester.set(0, null));

	}
	
	/**
	 * Tests the Linkedlist object's set() method
	 */
	@Test
	public void testSetIntE() {
		
		LinkedListRecursive<String> linkedList = new LinkedListRecursive<String>();
		
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
	 * Tests the remove method
	 */
	@Test
	public void testRemoveE() {
		
		LinkedListRecursive<String> linkedList = new LinkedListRecursive<String>();
		
		String s1 = "North Carolina";
		String s2 = "South Carolina";
		String s3 = "California";
		
		assertFalse(linkedList.remove(s1));
		linkedList.add(s1);
		linkedList.add(s2);
		linkedList.add(s3);

		try {
			
			linkedList.remove(s3);
			
			assertEquals(2, linkedList.size());
			
			assertEquals(s1, linkedList.get(0));
			
			assertEquals(s2, linkedList.get(1));

		} catch (Exception e) {
			fail();
		}

		try {
			linkedList.remove(s3);
			
			assertEquals(2, linkedList.size());
			
			assertEquals(s1, linkedList.get(0));
			
			assertEquals(s2, linkedList.get(1));
		} catch (Exception e) {
			fail();
		}

		try {
			linkedList.remove(s1);
			
			assertEquals(1, linkedList.size());
			
			assertEquals(s2, linkedList.get(0));
			
		} catch (Exception e) {
			fail();
		}

	}
	
	/**
	 * Tests the contains() method
	 */
	@Test
	public void testContains() {
		
		LinkedListRecursive<String> linkedList = new LinkedListRecursive<String>();
		
		String s1 = "North Carolina";
		String s2 = "South Carolina";
		String s3 = "California";
		String s0 = null;

		
		assertFalse(linkedList.contains(s0));
		assertFalse(linkedList.contains(s1)); 
		linkedList.add(s1);
		linkedList.add(s2);
		linkedList.add(s3);

		
		assertTrue(linkedList.contains(s1));
		assertTrue(linkedList.contains(s3));
		assertTrue(linkedList.contains(s2));
	}
	
	/**
	 * Tests the get() method
	 */
	@Test 
	public void testGet() {
		
		LinkedListRecursive<String> linkedList = new LinkedListRecursive<String>();
		
		String s1 = "North Carolina";
		String s2 = "South Carolina";
		String s3 = "California";

		linkedList.add(s1);
		linkedList.add(s2);
		linkedList.add(s3);


		try {
			linkedList.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, linkedList.size());
		}

		try {
			linkedList.get(4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Empty
		}

		assertEquals(s3, linkedList.get(2));
		assertEquals(s1, linkedList.get(0));
		assertEquals(s2, linkedList.get(1));

	}

}
