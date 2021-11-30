package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Schedule class
 * @author divyamathur
 * @author Veerendra Gottiveeti
 *
 */
public class ScheduleTest {

	/**
	 * The method tests the constructor of the schedule with the array list size and the title of the schedule.
	 */
	@Test
	public void testSchedule() {
		Schedule schedule = new Schedule();
		
		assertEquals(0, schedule.getScheduledCourses().length, "incorrect array list size");
		assertEquals("My Schedule", schedule.getTitle(), "Incorrect title");
	}
	
	/**
	 * Tests the addCourseToSchedule method.
	 */
	@Test
	public void testaddCourseToSchedule() {
		CourseCatalog coursecatalog = new CourseCatalog();
		coursecatalog.loadCoursesFromFile("test-files/course_records.txt");
		Course course = coursecatalog.getCourseFromCatalog("CSC116", "001");
		Course course1 = coursecatalog.getCourseFromCatalog("CSC216", "001");
		Course course2 = coursecatalog.getCourseFromCatalog("CSC230", "001");
		Course course3 = coursecatalog.getCourseFromCatalog("CSC316", "001");
		Course course4 = null;
		
		
		Schedule s = new Schedule();
		assertTrue(s.addCourseToSchedule(course));
		String[][] schedule = s.getScheduledCourses();
		assertEquals("CSC116", schedule[0][0]);
		assertTrue(s.addCourseToSchedule(course1));
		schedule = s.getScheduledCourses();
		assertEquals("CSC216", schedule[1][0]);
		assertTrue(s.addCourseToSchedule(course2));
		schedule = s.getScheduledCourses();
		assertEquals("CSC230", schedule[2][0]);
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(course1));
		assertEquals(e.getMessage(), "You are already enrolled in " + course1.getName());

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(course3));
		assertEquals(e1.getMessage(), "The course cannot be added due to a conflict.");
		
		assertThrows(NullPointerException.class, () -> s.addCourseToSchedule(course4));
		
	}
	
	/**
	 * Tests the removeCourseFromSchedule method.
	 */
	@Test
	public void testremoveCourseFromSchedule() {
		CourseCatalog coursecatalog = new CourseCatalog();
		coursecatalog.loadCoursesFromFile("test-files/course_records.txt");
		Course course = coursecatalog.getCourseFromCatalog("CSC116", "001");
		Course course1 = coursecatalog.getCourseFromCatalog("CSC216", "001");
		Course course2 = coursecatalog.getCourseFromCatalog("CSC230", "001");
		Course course3 = coursecatalog.getCourseFromCatalog("CSC116", "002");
		
		Schedule s = new Schedule();
		s.addCourseToSchedule(course);
		s.addCourseToSchedule(course1);
		s.addCourseToSchedule(course2);
		
		assertTrue(s.removeCourseFromSchedule(course1));
		String[][] newSchedule = s.getScheduledCourses();
		for (int  i = 0; i < newSchedule.length; i++) {
			assertNotEquals(course1.getName(), newSchedule[i][0]);
		}
		
		assertFalse(s.removeCourseFromSchedule(course3));
	}
	
	/**
	 * Tests the resetSchedule method
	 */
	@Test
	public void testresetSchedule() {
		CourseCatalog coursecatalog = new CourseCatalog();
		coursecatalog.loadCoursesFromFile("test-files/course_records.txt");
		Course course = coursecatalog.getCourseFromCatalog("CSC116", "001");
		Course course1 = coursecatalog.getCourseFromCatalog("CSC216", "001");
		Course course2 = coursecatalog.getCourseFromCatalog("CSC230", "001");
		
		Schedule s = new Schedule();
		s.addCourseToSchedule(course);
		s.addCourseToSchedule(course1);
		s.addCourseToSchedule(course2);
		
		s.resetSchedule();
		String[][]newSchedule = s.getScheduledCourses();
		assertEquals(newSchedule.length, 0 );
		assertEquals(s.getTitle(), "My Schedule");
		
	}
	
	/**
	 * Tests the getScheduledCourses method
	 */
	@Test
	public void testgetScheduledCourse() {
		CourseCatalog coursecatalog = new CourseCatalog();
		coursecatalog.loadCoursesFromFile("test-files/course_records.txt");
		Course course = coursecatalog.getCourseFromCatalog("CSC116", "001");
		Course course1 = coursecatalog.getCourseFromCatalog("CSC216", "001");
		Course course2 = coursecatalog.getCourseFromCatalog("CSC230", "001");
		
		Schedule s = new Schedule();
		s.addCourseToSchedule(course);
		s.addCourseToSchedule(course1);
		s.addCourseToSchedule(course2);
		
		//Testing for course 
		String[][] newSchedule = s.getScheduledCourses();
		for(int i = 0; i < newSchedule[0].length; i++) {
			assertEquals(newSchedule[0][i], course.getShortDisplayArray()[i]);
		}
		
		
		
	}
	
	/**
	 * Tests the setTitle() method
	 */
	@Test
	public void testSetTitle() {
		Schedule s = new Schedule();
		s.setTitle("My Super Awesome Schedule");
		assertEquals("My Super Awesome Schedule", s.getTitle());
		assertThrows(IllegalArgumentException.class, () -> s.setTitle(null));
		
	}
	
	/**
	 * Tests the setTitle() method
	 */
	@Test
	public void testCanAdd() {
		Schedule s = new Schedule();
		CourseCatalog coursecatalog = new CourseCatalog();
		coursecatalog.loadCoursesFromFile("test-files/course_records.txt");
		
		assertFalse(s.canAdd(null));
		assertTrue(s.canAdd(coursecatalog.getCourseFromCatalog("CSC216", "001")));
		s.addCourseToSchedule(coursecatalog.getCourseFromCatalog("CSC216", "001"));
		assertFalse(s.canAdd(coursecatalog.getCourseFromCatalog("CSC216", "001")));
		
	}
	
	/**
	 * Tests the setTitle() method
	 */
	@Test
	public void testGetScheduledCredits() {
		Schedule s = new Schedule();
		CourseCatalog coursecatalog = new CourseCatalog();
		coursecatalog.loadCoursesFromFile("test-files/course_records.txt");
		
		assertEquals(0, s.getScheduleCredits());
		s.addCourseToSchedule(coursecatalog.getCourseFromCatalog("CSC216", "001"));
		s.addCourseToSchedule(coursecatalog.getCourseFromCatalog("CSC116", "001"));
		s.addCourseToSchedule(coursecatalog.getCourseFromCatalog("CSC230", "001"));
		
		assertEquals(9, s.getScheduleCredits());
	}
}