package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * The course catalog class is similar to the WolfScheduler class, which cannot be used in the packScheduler.
 * The WolfScheduler class used to hold the catalog and to schedule courses. Only the catalog functionality is needed in the PackScheduler.
 * CourseCatalog class and the WolfScheduler project WolfScheduler class and the StudentDirectory class to implement CourseCatalog.
 * The method include loading courses, adding courses, remove courses, get courses from catalog, and get course catalog, and save course catalog
 * @author Susmitha Potu
 * @author Veerendra Gottiveeti
 */
public class CourseCatalog {
	
	/** List of courses in the catalog */
	private SortedList<Course> catalog;
	
	
	/**
	 * Constructs a Sortedlist catalog with courses.
	 */
	public CourseCatalog() {
		this.catalog = new SortedList<Course>();
	}
	
	/**
	 * Constructs a Sortedlist catalog with courses.
	 */
	public void newCourseCatalog() {
		this.catalog = new SortedList<Course>();
	}
	
	/**
	 * Loads contents from a file.
	 * 
	 * @param fileName the name of the file to load the contents of.
	 * @throws IllegalArgumentException if the file cannot be found.
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			this.catalog = CourseRecordIO.readCourseRecords(fileName);
		}
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}
	
	/**
	 * Adds a new course to the Catalog as long as the course
	 * does not already exist. Returns true if it is added, 
	 * and false if it already exists.
	 * @param name of course
	 * @param title of course
	 * @param section of course
	 * @param credits of course
	 * @param instructorId of course
	 * @param capacity Number of positions available in the course.
	 * @param meetingDays of course
	 * @param startTime of course
	 * @param endTime of course
	 * @return true if the course can be added or false if the course is already in the catalog
	 * @throws IllegalArgumentException if there's an Error within constructing/adding the course.
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int capacity, String meetingDays, int startTime, int endTime) {
		
		//A temporary Course variable that get all the information for a course.
		Course temp = new Course (name, title, section, credits, instructorId, capacity, meetingDays, startTime, endTime);
		//A for loop to iterate through the schedule.
		for (int i = 0; i < catalog.size(); i++) {
			//A try/catch block to catch any errors in constructing.
			try {
				//If a certain course already exists then return false.
				if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)){
					return false;
				}
				//If an error is caught, an IAE is thrown.
				} catch (Exception e) {
					throw new IllegalArgumentException("Error in constructing.");		
				}
			} 
				
		//Add the class to the catalog and returns true.
		catalog.add(temp);
		return true;
	}
	
	
	
	/**
	 * Removes a course existing in the catalog with a certain name
	 * and section. Returns true if the course is removed, and 
	 * false if it isn't able to be removed.
	 * @param name of course
	 * @param section of course
	 * @return true if the course is removed.
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			if (c.getName().equals(name) && c.getSection().equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		
		return false;
	}
		
	
	/**
	 * Locates a course from the catalog if it exists,
	 * but returns null if it does not.
	 * @param name of course
	 * @param section of course
	 * @return course from catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if(name.equals(catalog.get(i).getName()) && section.equals(catalog.get(i).getSection())) {
				return catalog.get(i);
				}
			}
		return null;
	
	}
	
	/**
	 * Returns a 2d string array of the catalog
	 * @return courseCatalog String array of the catalog.
	 */
	public String [][] getCourseCatalog() {
		
		String [][] courseCatalog = new String [catalog.size()][5];
		for (int i = 0; i < catalog.size(); i++) {	
			Course c = catalog.get(i);
			courseCatalog[i] = c.getShortDisplayArray();
			
			if (catalog.get(i) == null) {
				return new String [0][0]; 
			}
			
			}
		return courseCatalog;
				

	}
	/**
	 * Saves contents to a file.
	 * @param fileName the name of the file.
	 * @throws IllegalArgumentException if the file is unable to be saved.
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}
	
	
	

}