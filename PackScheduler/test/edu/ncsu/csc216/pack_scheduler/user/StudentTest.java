package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;

/**
 * Tests Student.
 * @author Connor Bunch
 * @author Maverick Middleton
 * @author Luke Getzen
 * @author Veerendra Gottiveeti
 */

class StudentTest {

	/** Students first name */
	private static final String FIRST_NAME = "Billy";
	/** Students last name */
	private static final String LAST_NAME = "Bob";
	/** Students ID */
	private static final String ID = "bebob2";
	/** Students email */
	private static final String EMAIL = "bbob@ncsu.edu";
	/** Students password */
	private static final String PASSWORD = "drowssap";
	/** Students max credits */
	private static final int MAXCREDITS = 5;

	@Test
	void testHashCode() {
		User b1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		User b2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		User b3 = new Student("different", LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		User b4 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAXCREDITS);
		User b5 = new Student(FIRST_NAME, LAST_NAME, "hello", EMAIL, PASSWORD, MAXCREDITS);
		User b6 = new Student(FIRST_NAME, LAST_NAME, ID, "bbill@ncsu.edu", PASSWORD, MAXCREDITS);
		User b7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "password", MAXCREDITS);
		User b8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 6);

		// Test for the same hash code for the same values.
		assertEquals(b1.hashCode(), b2.hashCode());

		// Test for each of the fields
		assertNotEquals(b1.hashCode(), b3.hashCode());
		assertNotEquals(b1.hashCode(), b4.hashCode());
		assertNotEquals(b1.hashCode(), b5.hashCode());
		assertNotEquals(b1.hashCode(), b6.hashCode());
		assertNotEquals(b1.hashCode(), b7.hashCode());
		assertNotEquals(b1.hashCode(), b8.hashCode());
	}

	/**
	 * Tests studentStringStringStringStringStringInt.
	 */
	@Test
	void testStudentStringStringStringStringStringInt() {
		// Test a valid construction
		Student b = assertDoesNotThrow(() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS),
				"Should not throw exception");
		assertEquals("Billy", b.getFirstName());
		assertEquals("Bob", b.getLastName());
		assertEquals("bebob2", b.getId());
		assertEquals("bbob@ncsu.edu", b.getEmail());
		assertEquals("drowssap", b.getPassword());
		assertEquals(5, b.getMaxCredits());
		b = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

	}

	/**
	 * Tests studentStringStringStringStringString.
	 */
	@Test
	void testStudentStringStringStringStringString() {
		// Test a valid construction
		Student b = assertDoesNotThrow(() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD),
				"Should not throw exception");
		assertEquals("Billy", b.getFirstName());
		assertEquals("Bob", b.getLastName());
		assertEquals("bebob2", b.getId());
		assertEquals("bbob@ncsu.edu", b.getEmail());
		assertEquals("drowssap", b.getPassword());
		assertEquals(18, b.getMaxCredits());
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD));
		assertEquals("Invalid id", e1.getMessage()); // Check correct exception message
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD));
		assertEquals("Invalid id", e2.getMessage()); // Check correct exception message

	}

	/**
	 * Tests setFirstName.
	 */
	@Test
	void testSetFirstName() {
		// Construct a valid Student
		User b = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> b.setFirstName(null));
		assertEquals("Invalid first name", e1.getMessage()); // Check correct exception message
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> b.setFirstName(""));
		assertEquals("Invalid first name", e2.getMessage()); // Check correct exception message
		assertEquals("Billy", b.getFirstName()); // Check that first name didn't change

	}

	/**
	 * Tests setLastName.
	 */
	@Test
	void testSetLastName() {
		// Construct a valid Student
		User b = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> b.setLastName(null));
		assertEquals("Invalid last name", e1.getMessage()); // Check correct exception message
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> b.setLastName(""));
		assertEquals("Invalid last name", e2.getMessage()); // Check correct exception message
		assertEquals("Bob", b.getLastName()); // Check that first name didn't change
	}
	
	/**
	 * Tests setEmail.
	 */
	@Test
	void testSetEmail() {
		// Construct a valid Student
		User b = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> b.setEmail(null));
		assertEquals("Invalid email", e1.getMessage()); // Check correct exception message
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> b.setEmail("howdy.lmao"));
		assertEquals("Invalid email", e2.getMessage()); // Check correct exception message
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> b.setEmail("howdy@lmao"));
		assertEquals("Invalid email", e3.getMessage()); // Check correct exception message
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> b.setEmail("howdy.lmao@lmao"));
		assertEquals("Invalid email", e4.getMessage()); // Check correct exception message
		Exception e5 = assertThrows(IllegalArgumentException.class, () -> b.setEmail(""));
		assertEquals("Invalid email", e5.getMessage()); // Check correct exception message
		assertEquals("bbob@ncsu.edu", b.getEmail()); // Check that first name didn't change
	}

	/**
	 * Tests setPassword.
	 */
	@Test
	void testSetPassword() {
		// Construct a valid Student
		User b = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> b.setPassword(null));
		assertEquals("Invalid password", e1.getMessage()); // Check correct exception message
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> b.setPassword(""));
		assertEquals("Invalid password", e2.getMessage()); // Check correct exception message
		assertEquals("drowssap", b.getPassword()); // Check that first name didn't change
	}

	/**
	 * Tests setMaxCredits.  
	 */
	@Test
	void testSetMaxCredits() {
		// Construct a valid Student
		Student b = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> b.setMaxCredits(2));
		assertEquals("Invalid max credits", e1.getMessage()); // Check correct exception message
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> b.setMaxCredits(19));
		assertEquals("Invalid max credits", e2.getMessage()); // Check correct exception message
		assertEquals(18, b.getMaxCredits()); // Check that first name didn't change*/
	}

	/**
	 * Tests a valid amount of max credits
	 */
	@Test
	public void testSetValidMaxCredits(){
		Student student = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		assertEquals(MAXCREDITS, student.getMaxCredits(), "Failed test with valid max credits" + MAXCREDITS);
	}
//	/**
//	 * Tests a valid amount of max credits
//	 */
//	@Test
//	public void testSetInvalidMaxCredits() {
//		Student student = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
//	
//		Exception e1 = assertThrows(IllegalArgumentException.class,
//				() -> student.setMaxCredits(2));
//		assertEquals("Invalid max credits", e1.getMessage()); //Check correct exception message
//		assertEquals(MAXCREDITS, student.getMaxCredits());
//	
//		//testing no credits
//		Exception e2 = assertThrows(IllegalArgumentException.class,
//				() -> student.setMaxCredits(0));
//		assertEquals("Invalid max credits", e2.getMessage()); //Check correct exception message
//		assertEquals(MAXCREDITS, student.getMaxCredits());
//		
//		//testing more than 18
//		Exception e3 = assertThrows(IllegalArgumentException.class,
//				() -> student.setMaxCredits(20));
//		assertEquals("Invalid max credits", e3.getMessage()); //Check correct exception message
//		assertEquals(MAXCREDITS, student.getMaxCredits());
//	}
	
	/**
	 * Tests the equals method.
	 */
	@Test
	void testEqualsObject() {
		User b1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		User b2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		User b3 = new Student("different", LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		User b4 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAXCREDITS);
		User b5 = new Student(FIRST_NAME, LAST_NAME, "hello", EMAIL, PASSWORD, MAXCREDITS);
		User b6 = new Student(FIRST_NAME, LAST_NAME, ID, "bbill@ncsu.edu", PASSWORD, MAXCREDITS);
		User b7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "password", MAXCREDITS);
		User b8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 6);

		// Test for equality in both directions
		assertTrue(b1.equals(b2));
		assertTrue(b2.equals(b1));

		// Test for each of the fields
		assertFalse(b1.equals(b3));
		assertFalse(b1.equals(b4));
		assertFalse(b1.equals(b5));
		assertFalse(b1.equals(b6));
		assertFalse(b1.equals(b7));
		assertFalse(b1.equals(b8));
	}

	/**
	 * Tests toString.
	 */
	@Test
	void testToString() {
		User b1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		String s1 = "Billy,Bob,bebob2,bbob@ncsu.edu,drowssap,5";
		assertEquals(s1, b1.toString());
	}
	
	/**
	 * Tests the canAdd() method
	 */
	@Test
	void canAdd() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		CourseCatalog coursecatalog = new CourseCatalog();
		coursecatalog.loadCoursesFromFile("test-files/course_records.txt");
		
		assertFalse(s1.canAdd(null));
		assertTrue(s1.canAdd(coursecatalog.getCourseFromCatalog("CSC216", "001")));
		s1.getSchedule().addCourseToSchedule(coursecatalog.getCourseFromCatalog("CSC216", "001"));
		assertFalse(s1.canAdd(coursecatalog.getCourseFromCatalog("CSC216", "001")));
		
		assertTrue(s1.canAdd(coursecatalog.getCourseFromCatalog("CSC217", "202")));
		assertFalse(s1.canAdd(coursecatalog.getCourseFromCatalog("CSC116", "002")));
		assertFalse(s1.canAdd(coursecatalog.getCourseFromCatalog("CSC226", "001")));
	}
	
	/**
	 * Tests compareTo.
	 * last name, then first name, then their unity id
	 */
	@Test
	void testCompareTo() {
		Student b1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAXCREDITS);
		Student b2 = new Student("Jack", "Sparrow", "jesparrow", "jesparrow@ncsu.edu", "arggh", 17 );
		Student b3 = new Student("Bruce", "Wayne", "bewayne", "bewayne@ncsu.edu", "batman", 15 );
		Student b4 = new Student("Billy", "Bob", "bebob2", "bbob@ncsu.edu", "drowssap", 5);
		Student b5 = new Student("James", "Bob", "jabob2", "jbob@ncsu.edu", "drowssap", 5);
		Student b6 = new Student("Billy", "Bob", "babob2", "bbob@ncsu.edu", "drowssap", 5);
		assertEquals(1, b2.compareTo(b1));
		assertEquals(1, b3.compareTo(b1));
		assertEquals(-1, b1.compareTo(b3));
		assertEquals(0, b4.compareTo(b1));
		assertEquals(1, b5.compareTo(b4));
		assertEquals(-1, b4.compareTo(b5));
		assertEquals(-1, b6.compareTo(b4));
		assertEquals(1, b4.compareTo(b6));
		
	}
	
	

}