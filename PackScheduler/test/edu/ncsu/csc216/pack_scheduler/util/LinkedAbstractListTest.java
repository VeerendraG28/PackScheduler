package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedAbstract class
 * @author Susmitha Potu
 * @author divyamathur
 * @author Maverick Middleton
 *
 */
public class LinkedAbstractListTest {
	
	/**
	 * Tests the setMethod()
	 */
	@Test
	public void testSet() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		assertThrows(IllegalArgumentException.class, () -> new LinkedAbstractList<String>(-1));
		
		list.add(0, "Schedule 1");
		list.add(1, "Schedule 2");
		list.add(2, "Schedule 3");
		list.add(3, "Schedule 4");
		list.add(4, "Schedule 5");
		
		assertThrows(NullPointerException.class, () -> list.set(0, null));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "Schedule"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(6, "Schedule"));
		list.set(1, "Schedule");
		assertEquals(5, list.size());
		assertEquals("Schedule 1", list.get(0));
		assertEquals("Schedule", list.get(1));
		assertEquals("Schedule 3", list.get(2));
		assertEquals("Schedule 4", list.get(3));
		assertEquals("Schedule 5", list.get(4));
		
		
		list.set(0, "Schedule10");
		assertEquals(5, list.size());
		assertEquals("Schedule10", list.get(0));
		assertEquals("Schedule", list.get(1));
		assertEquals("Schedule 3", list.get(2));
		assertEquals("Schedule 4", list.get(3));
		assertEquals("Schedule 5", list.get(4));
		
	}
	
	/**
	 * Tests the remove() method
	 */
	@Test
	public void testRemove() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add(0, "Schedule 1");
		list.add(1, "Schedule 2");
		list.add(2, "Schedule 3");
		list.add(3, "Schedule 4");
		list.add(4, "Schedule 5");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(6));
		
		list.remove(0);
		assertEquals(4, list.size());
		assertEquals("Schedule 2", list.get(0));
		assertEquals("Schedule 3", list.get(1));
		assertEquals("Schedule 4", list.get(2));
		assertEquals("Schedule 5", list.get(3));
		
		list.remove(2);
		assertEquals(3, list.size());
		assertEquals("Schedule 2", list.get(0));
		assertEquals("Schedule 3", list.get(1));
		assertEquals("Schedule 5", list.get(2));
		
	}
	
	/**
	 * Tests the add() method
	 */
	@Test
	public void testAdd() {
		LinkedAbstractList<String> list1 = new LinkedAbstractList<String>(7);
		list1.add(0, "Schedule 1");
		list1.add(1, "Schedule 2");
		list1.add(2, "Schedule 3");
		list1.add(3, "Schedule 4");
		list1.add(4, "Schedule 5");
		
		assertEquals(5, list1.size());
		assertEquals("Schedule 1", list1.get(0));
		assertEquals("Schedule 2", list1.get(1));
		assertEquals("Schedule 3", list1.get(2));
		assertEquals("Schedule 4", list1.get(3));
		assertEquals("Schedule 5", list1.get(4));
		
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add("Schedule 1");
		list.add("Schedule 2");
		list.add("Schedule 3");
		list.add("Schedule 4");

		
		list.add(0, "Schedule");
		assertEquals(5, list.size());

		assertEquals("Schedule", list.get(0));
		assertEquals("Schedule 1", list.get(1));
		assertEquals("Schedule 2", list.get(2));
		assertEquals("Schedule 3", list.get(3));
		assertEquals("Schedule 4", list.get(4));

		assertThrows(IllegalArgumentException.class, () -> list.add(0, "extra element"));
		
		assertThrows(IllegalArgumentException.class, () -> list1.add(4, "Schedule 1"));
		assertThrows(IndexOutOfBoundsException.class, () -> list1.add(-1, "XYZ"));
		assertThrows(IndexOutOfBoundsException.class, () -> list1.add(6, "XY"));
		assertThrows(NullPointerException.class, () -> list1.add(4, null));
		
		
		
		
	}
	
	
}