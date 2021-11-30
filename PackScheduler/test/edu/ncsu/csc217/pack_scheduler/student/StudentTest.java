package edu.ncsu.csc217.pack_scheduler.student;
//push/

import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Tests the Student object.
 * @author SarahHeckman
 */
public class StudentTest {
	
	/** Test Student's first name. */
	private String firstName = "Harry";
	
	/** Test Student's last name */
	private String lastName = "Potter";
	
	/** Test Student's id */
	private String id = "hpotter";
	
	/** Test Student's email */
	private String email = "hpotter@ncsu.edu";
	
	/** Test Student's email */
	private String password = "percy";
	
	/** Test Student's hashed password */
	private String hashPW;
	
	/** Test Student's hashed password */
	private int maxCredits = 18;
	
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = new String(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}

	/**
	 * Tests Hashcode()
	 */
	@Test
	public void testHashCode() {
		User initialStudent = new Student(firstName, lastName, id, email, password, maxCredits);
		User studentOne = new Student(firstName, lastName, id, email, password, maxCredits);
		User studentTwo = new Student("percy", lastName, id, email, password, maxCredits);
		
		// Test for the same hash code for the same values
		assertEquals(initialStudent.hashCode(), studentOne.hashCode());
		// Test for each of the fields
		assertNotEquals(initialStudent.hashCode(), studentTwo.hashCode());
		assertNotEquals(studentOne.hashCode(), studentTwo.hashCode());
	}
	
	/**
	 * Tests Equals()
	 */
	@Test
	public void testEquals() {
		User studentInitial = new Student(firstName, lastName, id, email, password, maxCredits);
		User studentOne = new Student(firstName, lastName, id, email, password, maxCredits);
		User studentTwo = new Student("percy", lastName, id, email, password, maxCredits);
		assertTrue(studentInitial.equals(studentOne));
		assertFalse(studentInitial.equals(studentTwo));
		
		User studentSet = new Student(firstName, lastName, id, email, password);
		User studentSetTwo = new Student(firstName, lastName, id, email, password);
	    User studentSetThree = new Student(firstName, "jackson", id, email, password);
		assertTrue(studentSet.equals(studentSetTwo));
		assertFalse(studentSet.equals(studentSetThree));
		
		studentSet = new Student(firstName, lastName, id, email, password);
		studentSetTwo = new Student(firstName, lastName, id, email, password);
	    studentSetThree = new Student(firstName, lastName, "pjackson", email, password);
		assertTrue(studentSet.equals(studentSetTwo));
		assertFalse(studentSet.equals(studentSetThree));
	}
	
	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		User s1 = new Student(firstName, lastName, id, email, hashPW);
		assertEquals("Harry,Potter,hpotter,hpotter@ncsu.edu," + hashPW + ",18", s1.toString());
	}
	
	/**
	 * Tests Student.compareTo()
	 */
	@Test
	public void testCompareTo() {
		Student s1 = new Student(firstName, lastName, id, email, password, maxCredits);
		Student s2 = new Student("Brian", "Burns", id, email, password, maxCredits);
		Student s3 = new Student("Cam", "Newton", id, email, password, maxCredits);
		Student s4 = new Student(firstName, lastName, id, email, password, maxCredits);
		Student s5 = new Student(firstName, lastName, id, email, password, maxCredits);
		
		assertEquals(s2.compareTo(s1), -1);
		assertEquals(s1.compareTo(s4), 0);
		assertEquals(s1.compareTo(s5), 0);
		assertEquals(s1.compareTo(s2), 1);
		assertEquals(s3.compareTo(s2), 1);
		assertEquals(s2.compareTo(s3), -1);
		
		s1 = new Student(firstName, lastName, id, email, password);
		s2 = new Student("Brian", "Burns", id, email, password);
		s3 = new Student("Cam", "Newton", id, email, password);
		s4 = new Student(firstName, lastName, id, email, password);
		s5 = new Student(firstName, lastName, "cnewton", email, password);
		
		assertEquals(s2.compareTo(s3), -1);
		assertEquals(s2.compareTo(s1), -1);
		assertEquals(s1.compareTo(s4), 0);
		assertEquals(s1.compareTo(s5), 1);
		assertEquals(s1.compareTo(s2), 1);
		assertEquals(s3.compareTo(s2), 1);
	}
	
	
}
