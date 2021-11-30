package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Creates a singleton with a built in course catalog and student directory.
 * Allows the user to log students in and out based upon passwords and provided
 * ids, along with the registrar. The registeration manager works with the other
 * classes through the login and logout feature being added for each user or
 * student to create a schedule.
 * 
 * @author Susmitha Potu
 * @author Maverick Middleton
 *
 */
public class RegistrationManager {

	/** Serves as the singleton of the Registration Manager */
	private static RegistrationManager instance;

	/** Serves as the single instance of the Course Catalog */
	private CourseCatalog courseCatalog;

	/** Serves as the Student Directory's single instance */
	private StudentDirectory studentDirectory;

	/** Serves as the basic registrar */
	private User registrar;

	/** Serves as the user who is logged in currently */
	private User currentUser;

	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/** Properties file used for the registrar */
	private static final String PROP_FILE = "registrar.properties";

	/** The directory of faculty */
	private FacultyDirectory facultyDirectory;

	/**
	 * Constructor for the RegistrationManager
	 */
	private RegistrationManager() {
		createRegistrar();
		studentDirectory = new StudentDirectory();
		courseCatalog = new CourseCatalog();
		facultyDirectory = new FacultyDirectory();
	}

	/**
	 * Returns the faculty directory
	 * 
	 * @return the faculty directory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Creates a brand new Registrar using the Properties file. It uses file input
	 * stream
	 * 
	 * @throws IllegalArgumentException if register cannot be created.
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Generates a hashPW
	 * 
	 * @param pw The password provided
	 * @return The new HashedPassword
	 * @throws IllegalArgumentException when cannot hash password
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * The method gets an instance of the Registration manager
	 * 
	 * @return instance of the registration manager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * the method returns the course catalog.
	 * 
	 * @return course catalog of the course catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * The get student directory uses student directory class to acquire all the
	 * students
	 * 
	 * @return student directory which consists of the students
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Logs the user in based upon provided input for user/password.
	 * 
	 * @param id       The student's Id
	 * @param password The student's password
	 * @return True if the user logged in, or false if not.
	 * @throws IllegalArgumentException when the user does not exist
	 */
	public boolean login(String id, String password) {

		if (currentUser != null) {
			return false;
		}

		if (registrar.getId().equals(id)) {

			MessageDigest digest;

			try {

				digest = MessageDigest.getInstance(HASH_ALGORITHM);

				digest.update(password.getBytes());

				String localHashPW = new String(digest.digest());

				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				}

				else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		} else if (studentDirectory.getStudentById(id) != null) {
			User s = studentDirectory.getStudentById(id);
			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());

				if (s.getPassword().equals(localHashPW)) {
					currentUser = s;
					return true;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}

		} else if (facultyDirectory.getFacultyById(id) != null) {
			User f = facultyDirectory.getFacultyById(id);
			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (f.getPassword().equals(localHashPW) && (currentUser == registrar || currentUser == null)) {
					currentUser = f;
					return true;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException("User doesn't exist.");
		}

		return false;
	}

	/**
	 * Logs the user out by returning it to the registrar
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Serves as a getter for the Current User
	 * 
	 * @return The current user.
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Resets the fields for the CourseCatalog and Directory.
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
		currentUser = null;
	}

	/**
	 * Serves as the basic registrar object whenever it's needed.
	 * 
	 * @author Susmitha Potu
	 * @author Maverick Middleton
	 * @author Divya Mathur
	 *
	 */
	private static class Registrar extends User {

		/**
		 * Serves as the constructor for a Registrar.
		 * 
		 * @param firstName The Registrar's first name
		 * @param lastName  The Registrar's last name
		 * @param id        The Registrars's id
		 * @param email     The Registrar's email
		 * @param hashPW    The hashedPassword of the Registrar.
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c Course to drop
	 * @return true if dropped, false if an exception was caught
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every course
	 * and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}

	/**
	 * Checks to see if the current user is the registrar and then adds the course
	 * to the faculty member's schedule
	 * 
	 * @param c the course to be added to the schedule
	 * @param f the faculty member whose schedule is being added to
	 * @return true when you are able to add faculty to course
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if (currentUser != registrar)  {
			
			throw new IllegalArgumentException();
		}
		
		if(currentUser != null && currentUser == registrar ) {
			
			f.getSchedule().addCourseToSchedule(c);
			
			return true;
		}
		
		return false;
	} 

	/**
	 * Checks to see if the current user is the registrar and then removes the
	 * course from the faculty member's schedule
	 * 
	 * @param c the course being removed from the schedule
	 * @param f the faculty member whose schedule is being altered
	 * @throws IllegalArgumentException if the currentUser is null or is not the registrar
	 * @return true if able to remove faculty from course
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if(currentUser == null || currentUser != registrar ) {
			throw new IllegalArgumentException();
		} else {
			f.getSchedule().removeCourseFromSchedule(c);
			return true;
		}
	}

	/**
	 * Checks to see if current user is registrar, then resets the given faculty
	 * member's schedule
	 * 
	 * @param f the faculty member whose schedule is being reset
	 * @throws IllegalArgumentException if the currentUser is not the registrar
	 */
	public void resetFacultySchedule(Faculty f) {
		
		if (currentUser != registrar)  {
			throw new IllegalArgumentException();
		}
		
		if(currentUser != null && currentUser == registrar ) {
			f.getSchedule().resetSchedule();
		}
		
	}
}