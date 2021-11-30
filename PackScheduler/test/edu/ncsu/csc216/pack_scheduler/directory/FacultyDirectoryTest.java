package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the FacultyDirectory class
 * @author Sydney Morisoli
 *
 */
class FacultyDirectoryTest {
	/** Test Faculty's first name. */
	private String firstName = "first";
	/** Test Faculty's last name */
	private String lastName = "last";
	/** Test Faculty's id */
	private String id = "flast";
	/** Test Faculty's email */
	private String email = "first_last@ncsu.edu";
	//private String hashPW;
	/** Represents a faculty member's password */
	private String password = "secret";
	/** Represents the max number of courses a faculty member can teach */
	private int maxCourses = 2;
	//private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Tests the facultyDirectory constructor
	 */
	@Test
	void testFacultyDirectory() {
		FacultyDirectory f1 = new FacultyDirectory();
		assertEquals(0, f1.getFacultyDirectory().length);
	}
	
	/**
	 * Tests the loadFacultyFromFile method
	 */
	@Test
	void testLoadFacultyFromFile() {
		FacultyDirectory f1 = new FacultyDirectory();
		assertEquals(0, f1.getFacultyDirectory().length);
		//TODO write when IO is finished
	}
	
	/**
	 * Tests the addFaculty method
	 */
	@Test
	void testAddFaculty() {
		FacultyDirectory f1 = new FacultyDirectory();
		assertEquals(0, f1.getFacultyDirectory().length);
		assertTrue(f1.addFaculty(firstName, lastName, id, email, password, password, 2));
		assertEquals(1, f1.getFacultyDirectory().length);
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> f1.addFaculty("bill", "smith", id, "bsmith@ncsu.edu", "pw!!!", "pw!!!", maxCourses));
		assertEquals("Invalid id", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> f1.addFaculty("bill", "smith", "bsmith", "bsmith@ncsu.edu", "", "pw!!!", maxCourses));
		assertEquals("Invalid password", e2.getMessage());
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> f1.addFaculty("bill", "smith", "bsmith", "bsmith@ncsu.edu", "pw!!!", null, maxCourses));
		assertEquals("Invalid password", e3.getMessage());
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> f1.addFaculty("bill", "smith", "bsmith", "bsmith@ncsu.edu", "pw!!!", "diff pw", maxCourses));
		assertEquals("Passwords do not match", e4.getMessage());
	}
	
	/**
	 * Tests the removeFaculty methoduf
	 */
	@Test
	void testRemoveFaculty() {
		FacultyDirectory f1 = new FacultyDirectory();
		assertEquals(0, f1.getFacultyDirectory().length);
		assertTrue(f1.addFaculty(firstName, lastName, id, email, password, password, 2));
		assertTrue(f1.removeFaculty(id));
		assertEquals(0, f1.getFacultyDirectory().length);
	}
	
	/**
	 * Tests the getFacultyById method
	 */
	@Test
	void getFacultyById() {
		FacultyDirectory f1 = new FacultyDirectory();
		assertEquals(0, f1.getFacultyDirectory().length);
		assertTrue(f1.addFaculty(firstName, lastName, id, email, password, password, 2));
		f1.addFaculty("bill", "smith", "bsmith", "bsmith@ncsu.edu", "pw!!!", "pw!!!", maxCourses);
		assertEquals("bill", f1.getFacultyById("bsmith").getFirstName());
		assertNull(f1.getFacultyById("semoriso"));
	}
	
	/**
	 * Tests the laodFacultyFromDirectory method
	 */
	@Test
	void testLoadFacultyFromDirectory() {
		FacultyDirectory f1 = new FacultyDirectory();
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> f1.loadFacultyFromFile("test-files/notarealfile"));
		assertEquals("Unable to read file test-files/notarealfile", e1.getMessage());
		assertDoesNotThrow(() -> f1.loadFacultyFromFile("test-files/faculty_records.txt"));
	}
	
	/**
	 * Tests the saveFacultyDirectory method
	 */
	@Test
	void testSaveFacultyDirectory() {
		FacultyDirectory f1 = new FacultyDirectory();
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> f1.saveFacultyDirectory(""));
		assertEquals("Unable to write to file " + "", e1.getMessage());
		assertTrue(f1.addFaculty(firstName, lastName, id, email, password, password, 2));
		f1.addFaculty("bill", "smith", "bsmith", "bsmith@ncsu.edu", "pw!!!", "pw!!!", maxCourses);
		assertDoesNotThrow(() -> f1.saveFacultyDirectory("actual_faculty_results"));
	}
}
