package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

/**
 * Tests for SortedList.java
 * @author Jake McLaughlin
 * @author Toan Pham
 * @author Veerendra Gottiveeti
 *
 */
public class SortedListTest {

	/**
	 * Tests that the list grows by adding additional elements
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		
		//TODO Test that the list grows by adding at least 11 elements
		//Remember the list's initial capacity is 10
		
	}

	/**
	 * Tests different adding possibilities
	 */
	@Test
	public void testAdd2() {
		SortedList<String> list = new SortedList<String>();
		 
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		//TODO Test adding to the front, middle and back of the list
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		list.add("attack on titan");
		assertEquals(3, list.size());
		assertEquals("attack on titan", list.get(1));
		list.add("zzz sleeping zzzz");
		assertEquals(4, list.size());
		assertEquals("zzz sleeping zzzz", list.get(3));		
		
		//TODO Test adding a null element

		assertThrows(NullPointerException.class, () -> list.add(null));
		assertEquals(4, list.size());
		//TODO Test adding a duplicate element
		assertThrows(IllegalArgumentException.class, () -> list.add("apple"));
		assertEquals(4, list.size());
		
	}
	
	/**
	 * Tests different get errors
	 */
	@Test
	public void testGet2() {
		SortedList<String> list = new SortedList<String>();
		
		try {
				list.get(0);
				fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
			//This is expected
		}
		
		list.add("animals");
		list.add("songs");
		list.add("socks");
		try {
			list.get(-1);
			fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
			//this is expected
		}
		
		try {
			list.get(list.size());
			fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
			//this is expected
		}
	
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		//TODO Test getting an element from an empty list
		
		//TODO Add some elements to the list
		
		//TODO Test getting an element at an index < 0
		
		//TODO Test getting an element at size
		
	}
	
	/**
	 * Tests removing different elements
	 */
	@Test
	public void testRemove2() {
		SortedList<String> list = new SortedList<String>();
		
		try {
			list.remove(0);
			fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
			//this is expected
		}
		
		list.add("animals");
		list.add("bees");
		list.add("chickens");
		list.add("ducks");
		
		try {
			list.remove(-1);
			fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
		

		assertFalse(list.contains("apple"));	
	}
	}

	/**
	 * Tests different adding possibilities
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		 
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		//TODO Test adding to the front, middle and back of the list
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		list.add("attack on titan");
		assertEquals(3, list.size());
		assertEquals("attack on titan", list.get(1));
		list.add("zzz sleeping zzzz");
		assertEquals(4, list.size());
		assertEquals("zzz sleeping zzzz", list.get(3));		
		
		//TODO Test adding a null element

		assertThrows(NullPointerException.class, () -> list.add(null));
		assertEquals(4, list.size());
		//TODO Test adding a duplicate element
		assertThrows(IllegalArgumentException.class, () -> list.add("apple"));
		assertEquals(4, list.size());
		
	}
	
	/**
	 * Tests different get errors
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		try {
				list.get(0);
				fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
			return;
		}
		
		list.add("animals");
		list.add("songs");
		list.add("socks");
		try {
			list.get(-1);
			fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
			return;
		}
		
		try {
			list.get(list.size());
			fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
			return;
		}
	
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		//TODO Test getting an element from an empty list
		
		//TODO Add some elements to the list
		
		//TODO Test getting an element at an index < 0
		
		//TODO Test getting an element at size
		
	}
	
	/**
	 * Tests removing different elements
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		try {
			list.remove(0);
			fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
			return;
		}
		
		list.add("animals");
		list.add("bees");
		list.add("chickens");
		list.add("ducks");
		
		try {
			list.remove(-1);
			fail("Should throw exception but not");
		}
		catch (IndexOutOfBoundsException e){
			return;

		}
		
		list.remove(2);
		assertEquals(3, list.size());
		assertEquals("ducks", list.get(2));
		
		list.remove(2);
		assertEquals(2, list.size());
		assertEquals("bees", list.get(1));
		
		list.remove(0);
		assertEquals(1, list.size());
		assertEquals("bees", list.get(0));
		
		list.remove(0);
		assertEquals(0, list.size());
		

	
		
	
	}
	
	/**
	 * Tests IndexOf()
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		//TODO Test indexOf on an empty list
		list.indexOf("animals");
		assertEquals(0, list.size());
		assertEquals(-1, list.indexOf("animals"));
		//TODO Add some elements
		list.add("animals");
		list.add("bees");
		list.add("chickens");
		list.add("ducks");
		
		list.indexOf("animals");
		assertEquals(4, list.size());
		assertEquals(0, list.indexOf("animals"));
		
		list.indexOf("bees");
		assertEquals(4, list.size());
		assertEquals(1, list.indexOf("bees"));
		//TODO Test various calls to indexOf for elements in the list
		//and not in the list
		list.indexOf("Charlotte rapper DaBaby");
		assertEquals(4, list.size());
		assertEquals(-1, list.indexOf("Charlotte rapper DaBaby"));
		//TODO Test checking the index of null
		assertThrows(NullPointerException.class, () -> list.indexOf(null));
		assertEquals(4, list.size());
	}
	
	/**
	 * Tests clearing the list
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		//TODO Add some elements
		list.add("animals");
		list.add("bees");
		list.add("chickens");
		list.add("ducks");
		//TODO Clear the list
		list.clear();
		assertEquals(0, list.size());
		
		//TODO Test that the list is empty
	}

	/**
	 * Tests to detect that a list is empty
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		//TODO Test that the list starts empty
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		//TODO Add at least one element
		list.add("ape");
		//TODO Check that the list is no longer empty
		assertFalse(list.isEmpty());
	}

	/**
	 * Tests to check if a list contains an element or not
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		//TODO Test the empty list case
		assertFalse(list.contains("ape"));
		//TODO Add some elements
		list.add("banana");
		//TODO Test some true and false cases
		assertTrue(list.contains("banana"));
		assertFalse(list.contains("ape"));
	}
	
	/**
	 * Tests that lists are the same
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		SortedList<String> list2 = new SortedList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		SortedList<String> list3 = new SortedList<String>();
		list3.add("a");
		list3.add("b");
		list3.add("d");
		//TODO Make two lists the same and one list different
		
		//TODO Test for equality and non-equality
		assertTrue(list1.equals(list2));
		assertFalse(list2.equals(list3));
	}
	
	/**
	 * Tests HashCode()
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		SortedList<String> list2 = new SortedList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		SortedList<String> list3 = new SortedList<String>();
		list3.add("a");
		list3.add("b");
		list3.add("d");
		//TODO Make two lists the same and one list different
		assertEquals(list1.hashCode(), list2.hashCode());
		//TODO Test for the same and different hashCodes
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list2.hashCode(), list3.hashCode());
	}

}
 
