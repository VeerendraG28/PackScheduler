package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedQueue class
 * 
 * @author Sydney Morisoli
 *
 */
class LinkedQueueTest {
	/** Represents the capacity of a queue */
	private static final int CAP = 5;

	/**
	 * Tests the LinkedQueue constructor
	 */
	@Test
	void testLinkedQueue() {
		LinkedQueue<String> link = new LinkedQueue<>(CAP);
		assertEquals(0, link.size());
		assertTrue(link.isEmpty());
		assertThrows(IllegalArgumentException.class, () -> new LinkedQueue<String>(-1));
	}

	/**
	 * Tests the Enqeue method
	 */
	@Test
	void testEnqueue() {
		LinkedQueue<String> link = new LinkedQueue<>(CAP);
		link.enqueue("hello");
		link.enqueue("testing");
		assertEquals(2, link.size());
		assertFalse(link.isEmpty());
		assertThrows(IllegalArgumentException.class, () -> link.setCapacity(1));
		
		LinkedQueue<String> link2 = new LinkedQueue<>(1);
		link2.enqueue("I love lab");
		assertThrows(IllegalArgumentException.class, () -> link2.enqueue("this is illegal"));

	}

	/**
	 * Tests the dequeue method
	 */
	@Test
	void testDequeue() {
		LinkedQueue<String> link = new LinkedQueue<>(CAP);
		assertThrows(NoSuchElementException.class, () -> link.dequeue());
		link.enqueue("hello");
		link.enqueue("there");
		assertEquals(2, link.size());
		assertEquals("hello", link.dequeue());
		assertEquals(1, link.size());
		link.dequeue();
		assertTrue(link.isEmpty());
	}

}