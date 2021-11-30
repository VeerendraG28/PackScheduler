package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * This class helps create a faculty member, who has a max number of courses.
 * 
 * @author Sydney Morisoli
 * @author Veerendra Gottiveeti
 *
 */
public class Faculty extends User {
	/** The maximum number of courses a faculty member has */
	private int maxCourses;
	/** Represents a schedule of a faculty member */
	private FacultySchedule schedule;
	/** Represents the minimum number of courses faculty can have */
	public static final int MIN_COURSES = 1;
	/** Represents the maximum number of courses faculty can have */
	public static final int MAX_COURSES = 3;

	/**
	 * Constructor for a faculty member, utilizes the superclass to set up most
	 * parameters
	 * 
	 * @param firstName  the first name of the faculty member
	 * @param lastName   the last name of the faculty member
	 * @param id         the ID of the faculty member
	 * @param email      the email address of the faculty member
	 * @param password   the faculty member's password
	 * @param maxCourses the max number of courses the faculty member can teach
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}

	/**
	 * Getter method to return the faculty's schedule
	 * 
	 * @return schedule the faculty's schedule
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}

	/**
	 * Sets the maximum number of courses a teacher can teach
	 * 
	 * @param maxCourses the max number of courses a faculty member can teach
	 * @throws IllegalArgumentException if the parameter is outside of the given
	 *                                  constants for min/max courses
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Getter method for the maxCourses of a faculty member
	 * 
	 * @return the max number of courses for a faculty member
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Returns true if the number of scheduled courses is greater than the faculty
	 * member's max number of courses
	 * 
	 * @return true if the number of courses is greater than the max number of
	 *         courses allowed
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}

	/**
	 * Serves as the hashCode method for the Faculty
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Serves as the equals method generated for Faculty.
	 * 
	 * @param obj the object to be tested for equality
	 * @return Whether they are the same object or not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return !(maxCourses != other.maxCourses);
	}

	/**
	 * Returns a String representation of a Faculty member and all of its fields
	 * 
	 * @return the String representation of a Faculty member and its fields
	 */
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + ","
				+ this.getPassword() + "," + maxCourses;
	}

}
