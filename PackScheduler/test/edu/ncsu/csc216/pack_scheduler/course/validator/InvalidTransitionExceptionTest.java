/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Class to test the class InvalidTransitionException To check if the right message is outputted
 * @author Veerendra Gottiveeti
 * @author Anika Patel
 *
 */
class InvalidTransitionExceptionTest {
	
	/**
	 * Tests whether the message that will be outputted is Invalid FSM Transition.
	 */
	@Test
	public void testInvalidTransitionException() {
	InvalidTransitionException exceptionError = new InvalidTransitionException();
	assertEquals("Invalid FSM Transition.", exceptionError.getMessage());
	}

	/**
	 * Test method for knowing that someone will get a string from the input
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException exceptionError = new InvalidTransitionException("Designed message");
		assertEquals("Designed message", exceptionError.getMessage());
	}

}
