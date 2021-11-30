package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * The student class represents an indiviual's student record. Stored fields
 * include the student's first name, last name, ID number, email, password, and
 * potentially max credits. The methods included are constructors to create a
 * student, getters and setters to access student information, and an equals,
 * hash code, and toString to test equality and output the student's
 * information. This class takes in the information from StudentRecordIO and
 * processes it as a new student to be stored in the StudentDirectory's array
 * list of students.
 * 
 * @author Susmitha Potu
 * @author Veerendra Gottiveeti
 */
public class Student extends User implements Comparable<Student> {

	/** Maximum Amount of Credits for Student */
	private int maxCredits;

	/** Default Maximum of Credits for Student */
	public static final int MAX_CREDITS = 18;

	/** schedule new schedule */
	private Schedule schedule = new Schedule();

	/**
	 * Constructs a Student object with values for all fields.
	 * 
	 * @param firstName  First Name of Student
	 * @param lastName   Last Name of Student
	 * @param id         Student ID
	 * @param email      Student's Email
	 * @param password   Student's Password
	 * @param maxCredits Student's Credit Maximum
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
	}

	/**
	 * Constructs a Student object with values for all fields and default Credit
	 * Maximum.
	 * 
	 * @param firstName First Name of Student
	 * @param lastName  Last Name of Student
	 * @param id        Student ID
	 * @param email     Student's Email
	 * @param password  Student's Password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}

	/**
	 * Returns the maximum amount of credits.
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the maximum amount of credits.
	 * 
	 * @param maxCredits value.
	 * @throws IllegalArgumentException if the maximum credits exceeds the default
	 *                                  limits.
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;

//		if (maxCredits < 3 || maxCredits > MAX_CREDITS) {
//			throw new IllegalArgumentException("Invalid max credits");
//			
//		}
//		this.maxCredits = maxCredits;
//		

	}

	/**
	 * Returns a comma separated value String of all Student fields.
	 * 
	 * @return String representation of Student.
	 */
	@Override
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + ","
				+ this.getPassword() + "," + maxCredits;
	}

	/**
	 * Compares two students to tell which would be ordered before the other.
	 * Compares last name, first name, and student id.
	 * 
	 * @param s Student compared to the initial student used in the method.
	 * @return returnVal with -1 if it's less than the initial student, 1 if it is
	 *         greater than the student, and 0 if the students are equal.
	 */
	@Override
	public int compareTo(Student s) {
		// Getting String variables for the last names.
		String lastA = this.getLastName();
		String lastB = s.getLastName();
		int output;
		int returnVal = 0;
		// Setting output equal to whatever we get from the comparison.
		output = lastA.compareTo(lastB);
		// -1 = Less than A
		// 1 = Greater than A
		// 0 = Equals to A
		// If the output is 0, meaning they are equal, move onto the next if statement.
		if (output == 0) {
			// Getting String variables for the first names.
			String firstA = this.getFirstName();
			String firstB = s.getFirstName();
			// Setting output equal to whatever we get from the comparison.
			output = firstA.compareTo(firstB);
			// If the output is 0, meaning they are equal, move onto the next if statement.
			if (output == 0) {
				// Getting String variables for the IDs.
				String idA = this.getId();
				String idB = s.getId();
				// Setting output equal to whatever we get from the comparison.
				output = idA.compareTo(idB);
				// If the output is 0, meaning they are equal, return 0.
				if (output == 0) {
					returnVal = 0;
				}
				// If the output is is less than 0, meaning they are not equal, return -1.
				else if (output < 0) {
					returnVal = -1;
				}
				// If the output is is greater than 0, meaning they are not equal, return 1.
				else if (output > 0) {
					returnVal = 1;
				}
			}
			// If the output is is less than 0, meaning they are not equal, return -1.
			else if (output < 0) {
				returnVal = -1;
			}
			// If the output is is greater than 0, meaning they are not equal, return 1.
			else if (output > 0) {
				returnVal = 1;
			}
		}
		// If the output is is less than 0, meaning they are not equal, return -1.
		else if (output < 0) {
			returnVal = -1;
		}
		// If the output is is greater than 0, meaning they are not equal, return 1.
		else if (output > 0) {
			returnVal = 1;
		}
		return returnVal;
	}

	/**
	 * Generates a hashCode for Activity using all fields. Override because course
	 * and events are not the same and we have now have to deal with events.
	 * 
	 * @return hashCode for course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields. Override
	 * because course and events are not the same and we have now have to deal with
	 * events.
	 * 
	 * @param obj the Object to compare
	 * @return true if the objects are the same on all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}
	
	/**
	 * A method that states if the course can be added to the schedule or not
	 * It calls the schedule's canAdd() method to check the parameters and then 
	 * checks whether adding the given course could potentially cause the 
	 * total credits to exceed max credits or not.
	 * @param course the course to be added
	 * @return add a boolean of whether the course can be added or not.
	 */
	public boolean canAdd(Course course) {
		boolean add = true;
		
		add = schedule.canAdd(course);
		if(!add) {
			return add;
		}
		else if(course.getCredits() + schedule.getScheduleCredits() > maxCredits) {
		   add = false;
		}
		return add;

	}

	/**
	 * The method gets the student schedule
	 * 
	 * @return the student schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}
}