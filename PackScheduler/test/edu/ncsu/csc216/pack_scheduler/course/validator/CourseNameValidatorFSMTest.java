package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import edu.ncsu.csc216.product_backlog.model.task.Task;

class CourseNameValidatorFSMTest {
	/** Declaration of the CourseNameValidator Class*/
	private static CourseNameValidatorFSM tester;

	@BeforeEach
	void setUp() throws Exception {
		try {
			tester = new CourseNameValidatorFSM();
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Something went wrong");
		}
		
	}

	@Test
	void testValidTransition() {
		try {
			assertTrue(tester.isValid("CSC216"));
			assertTrue(tester.isValid("CS216"));
			assertTrue(tester.isValid("CSCT216"));
			assertTrue(tester.isValid("C216"));
			assertTrue(tester.isValid("A123A"));
			
		} catch (InvalidTransitionException e) {
			//
		}
		
	}
	
	
	@Test
	void testInvalidTransition() {
		assertThrows(InvalidTransitionException.class, () -> tester.isValid("234 CSC"));

		assertThrows(InvalidTransitionException.class, () -> tester.isValid("2 CSC"));
		
		assertThrows(InvalidTransitionException.class, () -> tester.isValid("CCCCCC"));
		
		assertThrows(InvalidTransitionException.class, () -> tester.isValid("22222"));

		assertThrows(InvalidTransitionException.class, () -> tester.isValid("###"));
		
		assertThrows(InvalidTransitionException.class, () -> tester.isValid("C2D"));
		
		assertThrows(InvalidTransitionException.class, () -> tester.isValid("C22D"));
		
		assertThrows(InvalidTransitionException.class, () -> tester.isValid("C2222"));
		
		assertThrows(InvalidTransitionException.class, () -> tester.isValid("C216DD"));
		
		assertThrows(InvalidTransitionException.class, () -> tester.isValid("C216D2"));
		
	}

}