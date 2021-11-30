package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Tests all the basic methods within RegistrationManager, while also testing
 * connections to CourseCatalog and StudentDirectory.
 * 
 * @author Maverick Middleton
 * @author Susmitha Potu
 *
 */
public class RegistrationManagerTest {

	/** Valid course records */
	private final String validTestFile = "test-files/student_records2.txt";
	/** Instance for RegistrationManager */
	private RegistrationManager manager;
	/** Registrar user name */
	private String registrarUsername;
	/** Registrar password */
	private String registrarPassword;
	/** Properties file */
	private static final String PROP_FILE = "registrar.properties";
	/** Invalid Student ID */
	private static final String INVALID_ID = "sunshine";
	/** Invalid Student Password */
	private static final String INVALID_PW = "computer";
	/** Valid course records */
	private final String courseCatalogLoader = "test-files/course_records.txt";

	/**
	 * Sets up the CourseManager and clears the data.
	 * 
	 * @throws Exception if error
	 */
	@BeforeEach
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.logout();
		manager.clearData();

		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			registrarUsername = prop.getProperty("id");
			registrarPassword = prop.getProperty("pw");
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot process properties file.");
		}

	}

	/**
	 * Gets courseCatalog and loads a file, checking the contents of it.
	 */
	@Test
	public void testGetCourseCatalog() {
		manager.getCourseCatalog().loadCoursesFromFile(courseCatalogLoader);
		CourseCatalog tester = manager.getCourseCatalog();
		CourseCatalog expected = new CourseCatalog();
		expected.loadCoursesFromFile(courseCatalogLoader);
		assertEquals(expected.getCourseCatalog().length, tester.getCourseCatalog().length);
	}

	/**
	 * Tests the StudentDirectory getter after it loads a file.
	 */
	@Test
	public void testGetStudentDirectory() {
		manager.getStudentDirectory().loadStudentsFromFile(validTestFile);
		StudentDirectory tester = manager.getStudentDirectory();
		assertEquals(tester.getStudentById("cgeorge"), manager.getStudentDirectory().getStudentById("cgeorge"));
	}

	/**
	 * Tests the login method with a valid and invalid login.
	 */
	@Test
	public void testLogin() {
		manager.getStudentDirectory().loadStudentsFromFile(validTestFile);
		String id;
		String pw;
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			id = prop.getProperty("id");
			pw = prop.getProperty("pw");

		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}

		assertTrue(manager.login(id, pw));
		assertFalse(manager.login(id, pw));
		manager.clearData();
		manager.getStudentDirectory().loadStudentsFromFile(validTestFile);
		// wrong id and password
		assertThrows(IllegalArgumentException.class, () -> manager.login(INVALID_ID, INVALID_PW));
		manager.clearData();
		manager.getStudentDirectory().loadStudentsFromFile(validTestFile);
		pw = "computer";
		assertFalse(manager.login(id, pw));

		try {
			manager.login("invaidId", "invalidPassword");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "User doesn't exist.");
		}

		// Test Registrar LogIn//
		manager.login(registrarUsername, registrarPassword);
		assertEquals(registrarUsername, manager.getCurrentUser().getId());
		manager.logout();

		// Test Student LogIn
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		manager.login("zking", "pw");
		assertEquals("zking", manager.getCurrentUser().getId());

		// Test Duplicate LogIn
		try {
			manager.login("zking", "pw");
		} catch (IllegalArgumentException e) {
			// empty
		}

		assertFalse(manager.login(registrarUsername, registrarPassword));
		assertEquals(registrarUsername, registrarUsername);
		manager.logout();
		assertNull(manager.getCurrentUser());

		manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");
		String password = manager.getFacultyDirectory().getFacultyById("awitt").getPassword();
		manager.login("awitt", password);
		manager.clearData();
		assertEquals(manager.getFacultyDirectory().getFacultyDirectory().length, 0);

	}

	/**
	 * Logs the user out, checking that the user is still the Registrar.
	 */
	@Test
	public void testLogout() {
		manager.logout();

		assertNull(manager.getCurrentUser());

	}

	/**
	 * Tests the getter for the current user, making sure it's null by default.
	 */
	@Test
	public void testGetCurrentUser() {
		User tester = manager.getCurrentUser();
		assertNull(tester);

	}

	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser(),
					"RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.");
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(registrarUsername, manager.getCurrentUser().getId(),
					"RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.");
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.");

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(3, scheduleFrost.getScheduleCredits(), "User: efrost\nCourse: CSC216-001\n");
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(1, scheduleFrostArray.length, "User: efrost\nCourse: CSC216-001\n");
		assertEquals("CSC216", scheduleFrostArray[0][0], "User: efrost\nCourse: CSC216-001\n");
		assertEquals("001", scheduleFrostArray[0][1], "User: efrost\nCourse: CSC216-001\n");
		assertEquals("Software Development Fundamentals", scheduleFrostArray[0][2],
				"User: efrost\nCourse: CSC216-001\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleFrostArray[0][3], "User: efrost\nCourse: CSC216-001\n");
		assertEquals("9", scheduleFrostArray[0][4], "User: efrost\nCourse: CSC216-001\n");

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits");

		// Check Student Schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(9, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length, "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC216", scheduleHicksArray[0][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[0][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Software Development Fundamentals", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("8", scheduleHicksArray[0][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC226", scheduleHicksArray[1][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[1][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[1][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC116", scheduleHicksArray[2][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("003", scheduleHicksArray[2][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[2][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser(),
					"RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.");
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(registrarUsername, manager.getCurrentUser().getId(),
					"RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.");
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.");

		assertFalse(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: drop\nUser: efrost\nCourse: CSC217-211\nResults: False - student not enrolled in the course");
		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True");

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits(), "User: efrost\nCourse: CSC226-001, then removed\n");
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length, "User: efrost\nCourse: CSC226-001, then removed\n");

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits");

		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(9, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length, "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC216", scheduleHicksArray[0][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[0][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Software Development Fundamentals", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[0][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC226", scheduleHicksArray[1][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[1][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[1][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC116", scheduleHicksArray[2][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("003", scheduleHicksArray[2][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[2][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");

		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: drop\nUser: ahicks\nCourse: CSC226-001\nResults: True");

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(6, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(2, scheduleHicksArray.length,
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("CSC216", scheduleHicksArray[0][0],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("001", scheduleHicksArray[0][1],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("Software Development Fundamentals", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("9", scheduleHicksArray[0][4],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("CSC116", scheduleHicksArray[1][0],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("003", scheduleHicksArray[1][1],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("9", scheduleHicksArray[1][4],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");

		assertFalse(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: drop\nUser: ahicks\nCourse: CSC226-001\nResults: False - already dropped");

		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: drop\nUser: ahicks\nCourse: CSC216-001\nResults: True");

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(3, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(1, scheduleHicksArray.length,
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("CSC116", scheduleHicksArray[0][0],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("003", scheduleHicksArray[0][1],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("9", scheduleHicksArray[0][4],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");

		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: drop\nUser: ahicks\nCourse: CSC116-003\nResults: True");

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001, CSC116-003\n");
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length,
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001, CSC116-003\n");

		manager.logout();

	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// Test if not logged in
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser(),
					"RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.");
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(registrarUsername, manager.getCurrentUser().getId(),
					"RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.");
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.");

		manager.resetSchedule();
		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits(), "User: efrost\nCourse: CSC226-001, then schedule reset\n");
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length, "User: efrost\nCourse: CSC226-001, then schedule reset\n");
		assertEquals(10, catalog.getCourseFromCatalog("CSC226", "001").getCourseRoll().getOpenSeats(),
				"Course should have all seats available after reset.");

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits");

		manager.resetSchedule();
		// Check Student schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, then schedule reset\n");
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length,
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, then schedule reset\n");
		assertEquals(10, catalog.getCourseFromCatalog("CSC226", "001").getCourseRoll().getOpenSeats(),
				"Course should have all seats available after reset.");
		assertEquals(10, catalog.getCourseFromCatalog("CSC216", "001").getCourseRoll().getOpenSeats(),
				"Course should have all seats available after reset.");
		assertEquals(10, catalog.getCourseFromCatalog("CSC116", "003").getCourseRoll().getOpenSeats(),
				"Course should have all seats available after reset.");

		manager.logout();

		manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");

		assertEquals(8, manager.getFacultyDirectory().getFacultyDirectory().length);

		try {
			manager.login(null, null);
		} catch (IllegalArgumentException e) {
			// empty
		}

		try {
			manager.login("brian", "burns");
		} catch (IllegalArgumentException e) {
			// empty
		}

		manager.clearData();

		assertEquals(manager.getFacultyDirectory().getFacultyDirectory().length, 0);

		manager.logout();
	}

	/**
	 * Tests the getFacultyDirectory() method, along with other faculty methods.
	 */
	@Test
	public void testFacultyDirectory() {
		manager.login(registrarUsername, registrarPassword);
		manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");
		assertEquals(8, manager.getFacultyDirectory().getFacultyDirectory().length);
		Course a1 = new Course("CSC116", "Computer Science- Introduction to programming", "225", 5, null, 10, "A");
		assertDoesNotThrow(() -> manager.addFacultyToCourse(a1, manager.getFacultyDirectory().getFacultyById("awitt")));
		assertEquals(manager.getFacultyDirectory().getFacultyById("awitt").getSchedule().getScheduledCourses().length,
				1);
		assertDoesNotThrow(() -> manager.removeFacultyFromCourse(a1, manager.getFacultyDirectory().getFacultyById("awitt")));
		assertEquals(manager.getFacultyDirectory().getFacultyById("awitt").getSchedule().getScheduledCourses().length,
				0);
		assertDoesNotThrow(() -> manager.addFacultyToCourse(a1, manager.getFacultyDirectory().getFacultyById("awitt")));
		manager.resetFacultySchedule(manager.getFacultyDirectory().getFacultyById("awitt"));
		assertEquals(manager.getFacultyDirectory().getFacultyById("awitt").getSchedule().getScheduledCourses().length,
				0);

	}

}