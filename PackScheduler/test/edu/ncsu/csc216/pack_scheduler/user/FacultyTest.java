package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

/**
 * Tests the Faculty class
 * 
 * @author Sydney Morisoli
 *
 */
class FacultyTest {
	/** Test Faculty's first name. */
	private String firstName = "first";
	/** Test Faculty's last name */
	private String lastName = "last";
	/** Test Faculty's id */
	private String id = "flast";
	/** Test Faculty's email */
	private String email = "first_last@ncsu.edu";
	/** Test Faculty's hashed password */
	private String hashPW;
	/** Represents a faculty member's password */
	private String password = "secret";
	/** Represents the max number of courses a faculty member can teach */
	private int maxCourses = 2;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	// This is a block of code that is executed when the StudentTest object is
	// created by JUnit. Since we only need to generate the hashed version
	// of the plaintext password once, we want to create it as the StudentTest
	// object is
	// constructed. By automating the hash of the plaintext password, we are
	// not tied to a specific hash implementation. We can change the algorithm
	// easily.
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
	 * Tests the faculty constructor
	 */
	@Test
	void testFaculty() {
		Faculty f1 = assertDoesNotThrow(() -> new Faculty(firstName, lastName, id, email, password, maxCourses));
		assertEquals(firstName, f1.getFirstName());
		assertEquals(lastName, f1.getLastName());
		assertEquals(id, f1.getId());
		assertEquals(email, f1.getEmail());
		assertEquals(password, f1.getPassword());
		assertEquals(2, f1.getMaxCourses());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Faculty(null, lastName, id, email, password, maxCourses));
		assertEquals("Invalid first name", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Faculty(firstName, null, id, email, password, maxCourses));
		assertEquals("Invalid last name", e2.getMessage());
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> new Faculty(firstName, lastName, null, email, password, maxCourses));
		assertEquals("Invalid id", e3.getMessage());
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> new Faculty(firstName, lastName, id, email, null, maxCourses));
		assertEquals("Invalid password", e4.getMessage());
		Exception e5 = assertThrows(IllegalArgumentException.class, () -> new Faculty(firstName, lastName, id, null, password, maxCourses));
		assertEquals("Invalid email", e5.getMessage());
		Exception e6 = assertThrows(IllegalArgumentException.class, () -> new Faculty(firstName, lastName, id, email, password, 4));
		assertEquals("Invalid max courses", e6.getMessage());
		Exception e7 = assertThrows(IllegalArgumentException.class, () -> new Faculty(firstName, lastName, id, email, password, 0));
		assertEquals("Invalid max courses", e7.getMessage());
	}
	
	/**
	 * Tests the toString() method
	 */
	@Test
	void testToString() {
		User f1 = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",2", f1.toString());
	}
	
	/**
	 * Tests the hashCode() method
	 */
	@Test
	void testHash() {
		User f1 = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		assertDoesNotThrow(() -> f1.hashCode());
	}
	
	/**
	 * Tests the equals() method
	 */
	 @Test
	 void testEquals() {
		 User f1 = assertDoesNotThrow(() -> new Faculty(firstName, lastName, id, email, password, maxCourses));
		 User f2 = assertDoesNotThrow(() -> new Faculty(firstName, lastName, id, email, password, maxCourses));
		 User f3 = assertDoesNotThrow(() -> new Faculty("a first name", lastName, id, email, password, maxCourses));
		 User f4 = assertDoesNotThrow(() -> new Faculty(firstName, lastName, id, email, password, 1));
		 
		 //equality is true
		 assertTrue(f1.equals(f2));
		 assertTrue(f2.equals(f1));
		 assertTrue(f2.equals(f2));
		 
		 //equality is false
		 assertFalse(f1.equals(f4));
		 assertFalse(f1.equals(f3));
		 
	 }

}
