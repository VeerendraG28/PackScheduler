package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;


/**
 * Testing class for the CourseCatalog class.
 * 
 * @author Luke Getzen
 * @author Connor Bunch
 * @author Maverick Middleton
 */
public class CourseCatalogTest {
		
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	/** Number of Students able to join the class*/
	private static final int CAPACITY = 10;
	
	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {		
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests CourseCatalog().
	 */
	@Test
	public void testCourseCatalog() {
		//Test that the StudentDirectory is initialized to an empty list
		CourseCatalog sd = new CourseCatalog();
		assertFalse(sd.removeCourseFromCatalog(NAME, SECTION));
		assertEquals(0, sd.getCourseCatalog().length);
	}

	/**
	 * Tests CourseCatalog.newCourseCatalog().
	 */
	@Test
	public void testNewCourseCatalog() {
		//Test that if there are students in the directory, they 
		//are removed after calling newCourseCatalog().
		CourseCatalog sd = new CourseCatalog();
		
		sd.loadCoursesFromFile(validTestFile);
		assertEquals(13, sd.getCourseCatalog().length);
		
		sd.newCourseCatalog();
		assertEquals(0, sd.getCourseCatalog().length);
	}

	/**
	 * Tests CourseCatalog.loadStudentsFromFile().
	 */
	@Test
	public void testLoadCoursesFromFile() {
		
		CourseCatalog sd = new CourseCatalog();
		
		//Test valid file
		sd.loadCoursesFromFile(validTestFile);
		assertEquals(13, sd.getCourseCatalog().length);
		try {
			CourseCatalog x = new CourseCatalog();
			
			//Test a file that does not exist.
			x.loadCoursesFromFile("DNE.txt");
			fail("Should have thrown an exception.");
		} catch (IllegalArgumentException e) {
			//test pass
		}
		assertThrows(IllegalArgumentException.class, 
				() -> {
					CourseCatalog x = new CourseCatalog();
					
					//Test a file that does not exist.
					x.loadCoursesFromFile("DNE.txt");
				}
				);
		
	}
	
	/**
	 * Test CourseCatalog.getCourseFromCatalog().
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog cc = new CourseCatalog();
		
		//Attempt to get a course that doesn't exist
		assertNull(cc.getCourseFromCatalog("CSC492", "001"));
		
		//Attempt to get a course that does exist
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAPACITY, MEETING_DAYS, START_TIME, END_TIME);
		assertTrue(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAPACITY, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals(c, cc.getCourseFromCatalog("CSC216", "001"));
	}
	

	/**
	 * Tests CourseCatalog.addStudent().
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog cc = new CourseCatalog();
		assertTrue(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAPACITY, MEETING_DAYS, START_TIME, END_TIME));
		assertTrue(cc.addCourseToCatalog(NAME, TITLE, "004", CREDITS, INSTRUCTOR_ID, CAPACITY, MEETING_DAYS, START_TIME, END_TIME));
		
		
		assertFalse(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAPACITY, MEETING_DAYS, START_TIME, END_TIME));		
	}

	/**
	 * Tests CourseCatalog.removeCourseFromCatalog().
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		
		CourseCatalog cc = new CourseCatalog();
		
		//Attempt to remove from empty schedule
		assertFalse(cc.removeCourseFromCatalog(NAME, SECTION));
		
		//Add some courses and remove them
		assertTrue(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAPACITY, MEETING_DAYS, START_TIME, END_TIME));
		
		assertTrue(cc.addCourseToCatalog("CSC226", "Discrete Math", "001", 4, "cebunch", 10, "TH", 1400, 1500));
		
		assertTrue(cc.addCourseToCatalog("CSC216", TITLE, "002", CREDITS, INSTRUCTOR_ID, CAPACITY, MEETING_DAYS, START_TIME, END_TIME));
		//Remove CSC116
		assertTrue(cc.removeCourseFromCatalog("CSC216", "002"));
		//Remove CSC216
		assertTrue(cc.removeCourseFromCatalog(NAME, SECTION));
		//Remove CSC226
		assertTrue(cc.removeCourseFromCatalog("CSC226", "001"));
		
		
		//Check that removing all doesn't break future adds
		assertTrue(cc.addCourseToCatalog("CSC230", TITLE, "001", CREDITS, INSTRUCTOR_ID, CAPACITY, MEETING_DAYS, START_TIME, END_TIME ));
		
	}

	/**
	 * Tests CourseCatalog.saveCourseCatalog().
	 */
	@Test
	public void testSaveCourseCatalog() {
		CourseCatalog sd = new CourseCatalog();
		
		//Add a student
		sd.addCourseToCatalog("MA141", "Calculus 1", "001", 3, "cebunch", 14, "MW", 1445, 1545);
		assertEquals(1, sd.getCourseCatalog().length);
		sd.saveCourseCatalog("test-files/course_records_save_test.txt");
		checkFiles("test-files/expected_course_records_save.txt", "test-files/course_records_save_test.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	
	

}