package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

class ArrayStackTest {

	@Test
	void test() {
		ArrayStack<String> tester = assertDoesNotThrow(() -> new ArrayStack<String>(3));
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