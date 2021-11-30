package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
/**
 * Tests the ArrayQueue class
 * @author Sydney Morisoli
 *
 */
class ArrayQueueTest {
	/** Represents the capacity of a queue */
	private static final int CAP = 5;
	
	/**
	 * Tests the ArrayQueue Constructor
	 */
	@Test
	void testArrayQueue() {
		ArrayQueue<String> arr = new ArrayQueue<>(CAP);
		assertEquals(0, arr.size());
		assertTrue(arr.isEmpty());
		assertThrows(IllegalArgumentException.class, () -> new ArrayQueue<String>(-1));
	}
	
	@Test
	void testEnqueue() {
		ArrayQueue<String> arr = new ArrayQueue<>(CAP);
		arr.enqueue("hello");
		arr.enqueue("testing");
		assertEquals(2, arr.size());
		assertFalse(arr.isEmpty());
		
		ArrayQueue<String> arr2 = new ArrayQueue<>(1);
		arr2.enqueue("full arr");
		assertThrows(IllegalArgumentException.class, () -> arr2.enqueue("hi!!"));

	}
	
	@Test
	void testDequeue() {
		ArrayQueue<String> arr = new ArrayQueue<>(CAP);
		assertThrows(NoSuchElementException.class, () -> arr.dequeue());
		arr.enqueue("hi");
		arr.enqueue("test test");
		assertEquals(2, arr.size());
		assertEquals("hi", arr.dequeue());
		assertEquals(1, arr.size());
		arr.dequeue();
		assertTrue(arr.isEmpty());
	}

}