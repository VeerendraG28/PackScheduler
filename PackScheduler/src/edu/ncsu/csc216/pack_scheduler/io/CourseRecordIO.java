package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files. Writes a set of CourseRecords to a file
 * in comma separated format. Works with reading and writing files that stores
 * information about given semester's courses. Method throw exception if there
 * are extra tokens other than those such as name, title, section, and credits.
 * When reading, courses are added to an empty ArrayList that has been created.
 * 
 * 
 * @author Sarah Heckman
 * @author Connor Bunch
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses.
	 * @throws FileNotFoundException if the file cannot be found or read.
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file.
		SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects.
		while (fileReader.hasNextLine()) { // While we have more lines in the file.
			try { // Attempt to do the following.
					// Read the line, process it in readCourse, and get the object.
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below.
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						// It's a duplicate!
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the ArrayList.
				} // Otherwise ignore
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it.
			}
		}
		// Close the Scanner b/c we're responsible with our file handles.
		fileReader.close();
		// Return the ArrayList with all the courses we read.
		return courses;
	}

	/**
	 * Process the lines from the scanner and break them up into tokens and return a
	 * newly constructed course object.
	 * 
	 * @param line to process from a scanner.
	 * @return newly constructed course object.
	 * @throws IllegalArgumentException if the token is invalid.
	 */
	private static Course readCourse(String line) {
		// Creates a scanner and uses the delimiter in order to break up a large string.
		Scanner s = new Scanner(line);
		s.useDelimiter(",");
		Course courseInfo;
		try {
			// Reading in the tokens for name, title, section, credits, and meetingDays.
			String name = s.next();
			String title = s.next();
			String section = s.next();
			int credits = s.nextInt();
			String instructorId = s.next();
			int capacity = s.nextInt();
			String meetingDays = s.next();
			// If meetingDays is equal to A, enter this if statement.
			if ("A".equals(meetingDays)) {
				// If there are more tokens, close the scanner and throw IAE.
				if (s.hasNext()) {
					s.close();
					throw new IllegalArgumentException("Invalid Token.");
				}
				// Closing the scanner.
				s.close();
				// Returning a newly constructed Course object.
				courseInfo = new Course(name, title, section, credits, null, capacity, meetingDays);

				//// faculty functionality added
				if (RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId) != null) {

					RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId).getSchedule()
							.addCourseToSchedule(courseInfo);

				}
				return courseInfo;
			} else {
				// Reading in the tokens for startTime and endTime.
				int startTime = s.nextInt();
				int endTime = s.nextInt();
				// If there are more tokens, close the scanner and throw IAE.
				if (s.hasNext()) {
					s.close();
					throw new IllegalArgumentException("Invalid Token.");
				}
				// Closing the scanner.
				s.close();
				// Returning a newly constructed Course object.
				courseInfo = new Course(name, title, section, credits, null, capacity, meetingDays, startTime, endTime);

				//// faculty functionality added
				if (RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId) != null) {

					RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId).getSchedule()
							.addCourseToSchedule(courseInfo);

				}

				return courseInfo;
			}

		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid Token.");
		}
	}

	/**
	 * Writes the given list of Courses to a file.
	 * 
	 * @param fileName file to write schedule of Courses to a file.
	 * @param catalog  list of Courses to write.
	 * @throws IOException if cannot write to a file.
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> catalog) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		// A for loop to iterate through the course ArrayList and print the contents
		// using the file writer.
		for (int i = 0; i < catalog.size(); i++) {
			fileWriter.println(catalog.get(i).toString());
		}
		// Closes the fileWriter.
		fileWriter.close();

	}

}