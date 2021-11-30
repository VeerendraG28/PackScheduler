package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Serves as the tests for LinkedStack to make sure it functions properly.
 * @author Maverick Middleton
 *
 */
class LinkedStackTest {
	
	/**
	 * Tests all the methods with a single Stack, looking for each of the errors that can be caused.
	 */
	@Test
	void testLinkedStack() {
		LinkedStack<String> tester = assertDoesNotThrow(() -> new LinkedStack<String>(3));
		assertDoesNotThrow(() -> tester.push("HI!"));
		tester.push("There!");
		tester.push("you!");
		assertThrows(IllegalArgumentException.class, () -> tester.setCapacity(2));
		assertDoesNotThrow(() -> tester.setCapacity(4));
		assertEquals(tester.pop(), "you!");
		assertEquals(tester.pop(), "There!");
		assertEquals(tester.pop(), "HI!");
		assertEquals(0, tester.size());
		assertThrows(EmptyStackException.class, () -> tester.pop());
		
	}

}