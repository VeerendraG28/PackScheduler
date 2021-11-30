package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Serves as the tests for the CourseRoll's various methods.
 * @author susmithapotu
 *
 */
class CourseRollTest {

	/**
	 * Tests the CourseRoll constructor
	 */
	@Test
	final void testCourseRoll() {
		//valid
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 50, "A");
		CourseRoll cr = c.getCourseRoll();
		assertEquals(50, cr.getEnrollmentCap());
		

		try {
			Course c2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 450, "A");
			CourseRoll cr2 = c2.getCourseRoll();
			cr2.getEnrollmentCap();
		}
		catch (Exception e) {
			//empty
		}


	}
	/**
	 * Tests the drop method
	 */
	@Test
	final void testDrop() {
		//valid
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll cr = c.getCourseRoll();
		Student s = new Student("Susmitha", "Potu", "spotu", "spotu@ncsu.edu", "niceday");
		cr.enroll(s);
		cr.drop(s);
		cr.enroll(s);
		Student s3 = new Student("susmitha", "potu", "spotu", "spotu@ncsu.edu", "hello");
		Student s4 = new Student("susmitha", "me", "sme", "sme@ncsu.edu", "pen");
		Student s5 = new Student("susmitha", "john", "sjohn", "sjohn@ncsu.edu", "waves");
		Student s6 = new Student("susmitha", "mat", "smat", "smat@ncsu.edu", "hello");
		Student s7 = new Student("susmitha", "cat", "scat", "scat@ncsu.edu", "hello", 12);
		Student s8 = new Student("potu", "bat", "sbat", "sbat@ncsu.edu", "me", 14);
		Student s9 = new Student("potu", "low", "slow", "slow@ncsu.edu", "hello");
		Student s10 = new Student("potu", "mark", "smark", "smark@ncsu.edu", "hello");
		Student s11 = new Student("potu", "joe", "sjoe", "sjoe@ncsu.edu", "pencil", 16);
		Student s12 = new Student("potu", "swift", "sswift", "sswift@ncsu.edu", "spoon", 15); 
		
		cr.enroll(s3);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		cr.enroll(s10);
		cr.enroll(s11);
		cr.enroll(s12);
		cr.enroll(new Student("Mav", "Mav", "Mav", "lazyemail@ncsu.edu", "Mav", 15));
		cr.enroll(new Student("Bill", "Bill", "Bill", "bill@ncsu.edu", "Bill", 15));
		cr.drop(new Student("Bill", "Bill", "Bill", "bill@ncsu.edu", "Bill", 15));
		
		//invalid - null;
		assertThrows(IllegalArgumentException.class, () -> cr.drop(null));
	}
	
	/**
	 * Tests the enroll method
	 */
	@Test
	final void testEnroll() {
		//valid
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 50, "A");
		CourseRoll cr = c.getCourseRoll();
		Student s = new Student("susmitha", "potu", "spotu", "spotu@ncsu.edu", "niceday");
		assertTrue(cr.canEnroll(s));
		cr.enroll(s);
		assertEquals(49, cr.getOpenSeats());
		
		//invalid - duplicate
		assertThrows(IllegalArgumentException.class, () -> cr.enroll(new Student("susmitha", "potu", "spotu", "spotu@ncsu.edu", "niceday")));
		assertThrows(IllegalArgumentException.class, () -> cr.enroll(null));

		
		Course c2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll cr2 = c2.getCourseRoll();
		Student s2 = new Student("susmitha", "potu", "spotu", "spotu@ncsu.edu", "niceday");
		assertTrue(cr2.canEnroll(s2));
		cr2.enroll(s2);
		assertEquals(49, cr.getOpenSeats());
		
		//invalid - no space to add student
		Student s3 = new Student("susmitha", "potu", "spotu", "spotu@ncsu.edu", "hello");
		Student s4 = new Student("susmitha", "me", "sme", "sme@ncsu.edu", "pen");
		Student s5 = new Student("susmitha", "john", "sjohn", "sjohn@ncsu.edu", "waves");
		Student s6 = new Student("susmitha", "mat", "smat", "smat@ncsu.edu", "hello");
		Student s7 = new Student("susmitha", "cat", "scat", "scat@ncsu.edu", "hello", 12);
		Student s8 = new Student("potu", "bat", "sbat", "sbat@ncsu.edu", "me", 14);
		Student s9 = new Student("potu", "low", "slow", "slow@ncsu.edu", "hello");
		Student s10 = new Student("potu", "mark", "smark", "smark@ncsu.edu", "hello");
		Student s11 = new Student("potu", "joe", "sjoe", "sjoe@ncsu.edu", "pencil", 16);
		Student s12 = new Student("potu", "swift", "sswift", "sswift@ncsu.edu", "spoon", 15);
		
		cr2.enroll(s3);
		cr2.enroll(s4);
		cr2.enroll(s5);
		cr2.enroll(s6);
		cr2.enroll(s7);
		cr2.enroll(s8);
		cr2.enroll(s9);
		cr2.enroll(s10);
		cr2.enroll(s11);
		
		assertTrue(cr2.canEnroll(s12));
		
	}
	
	/**
	 * Tests the waitList
	 */
	@Test
	final void testWaitList() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll cr = c.getCourseRoll();
		
		Student s3 = new Student("susmitha", "potu", "spotu", "spotu@ncsu.edu", "hello");
		Student s4 = new Student("susmitha", "me", "sme", "sme@ncsu.edu", "pen");
		Student s5 = new Student("susmitha", "john", "sjohn", "sjohn@ncsu.edu", "waves");
		Student s6 = new Student("susmitha", "mat", "smat", "smat@ncsu.edu", "hello");
		Student s7 = new Student("susmitha", "cat", "scat", "scat@ncsu.edu", "hello", 12);
		Student s8 = new Student("potu", "bat", "sbat", "sbat@ncsu.edu", "me", 14);
		Student s9 = new Student("potu", "low", "slow", "slow@ncsu.edu", "hello");
		Student s10 = new Student("potu", "mark", "smark", "smark@ncsu.edu", "hello");
		Student s11 = new Student("potu", "joe", "sjoe", "sjoe@ncsu.edu", "pencil", 16);
		Student s12 = new Student("potu", "swift", "sswift", "sswift@ncsu.edu", "spoon", 15);
		
		cr.enroll(s3);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		cr.enroll(s10);
		cr.enroll(s11);
		cr.enroll(s12);
		
		assertEquals(cr.getNumberOnWaitlist(), 0);
		
		Student s13 = new Student("Mark", "David", "mdavid", "mdavid@ncsu.edu", "hello");
		Student s14 = new Student("James", "Robinson", "jrobinson", "jrobinson@ncsu.edu", "hello");
		Student s15 = new Student("Sean", "Reagan", "sreagan", "sreagan@ncsu.edu", "pencil", 16);
		
		cr.enroll(s13);
		cr.enroll(s14);
		cr.enroll(s15);
		
		assertEquals(cr.getNumberOnWaitlist(), 3);
		
		cr.drop(s3);
		assertEquals(cr.getNumberOnWaitlist(), 2);
		
		
		
	}
	
	
	/**
	 * Tests the capacity setter method
	 */
	@Test
	final void testCapacity() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 50, "A");
		CourseRoll cr = c.getCourseRoll();
		assertThrows(IllegalArgumentException.class, () -> cr.setEnrollmentCap(-1));
		cr.enroll(new Student("susmitha", "potu", "spotu", "spotu@ncsu.edu", "hello"));
		assertThrows(IllegalArgumentException.class, () -> cr.setEnrollmentCap(0));
		
	}
	
	

}