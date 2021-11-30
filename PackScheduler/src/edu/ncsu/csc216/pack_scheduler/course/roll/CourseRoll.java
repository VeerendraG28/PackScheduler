package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * The class uses the LinkedAbstractList of students and checks whether the
 * given student can be added to the list or not. It can also drop the student
 * from the list.
 * 
 * @author Susmitha Potu
 * @author divyamathur
 * @author Maverick Middleton
 * @author Veerendra Gottiveeti
 *
 */
public class CourseRoll {

	/** A custom LinkedAbstractList of student */
	private LinkedAbstractList<Student> students;

	/** A LinkedQueue that contains all the students waiting to join the course */
	private LinkedQueue<Student> waitlist;

	/** Serves as the Course the roll is based on */
	private Course course;

	/** The roll's enrollment capacity */
	private int enrollmentCap;

	/** A constant for the smallest class size */
	private static final int MIN_ENROLLMENT = 10;

	/** A constant representing the largest class size */
	private static final int MAX_ENROLLMENT = 250;

	/**
	 * A constructor that creates a Student LinkedAbstractList based on the capacity
	 * provided in the constructor parameter.
	 * 
	 * @param capacity the capacity of the list to be constructed.
	 * @param c        The course being linked to the roll.
	 */
	public CourseRoll(Course c, int capacity) {
		waitlist = new LinkedQueue<Student>(10);
		setCourse(c);
		setEnrollmentCap(capacity);
		students = new LinkedAbstractList<Student>(enrollmentCap);

	}

	/**
	 * Sets the course of the roll equal to this.
	 * 
	 * @param course The course being linked to this roll.
	 * @throws IllegalArgumentException if the course is null.
	 */
	private void setCourse(Course course) {
		if (course == null) {
			throw new IllegalArgumentException();
		}
		this.course = course;
	}

	/**
	 * Sets the enrollmentCap with the capacity
	 * 
	 * @param capacity the capacity of the list.
	 * @throws IllegalArgumentException if the capacity is out of the min and max
	 *                                  enrollment.
	 */
	public void setEnrollmentCap(int capacity) { 
		if (capacity < MIN_ENROLLMENT || capacity > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}

		if (students != null && capacity < students.size())
			throw new IllegalArgumentException();

		this.enrollmentCap = capacity;
		if (students != null) {
			this.students.setCapacity(capacity);
		}
	}

	/**
	 * Returns the enrollment capacity
	 * 
	 * @return enrollment Cap the capacity
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Enrolls the student in the course roll. It adds the student to the end of the
	 * roll. If the student is unable to join the course, it is added to the
	 * waitlist's back if there's room.
	 * 
	 * @param s the student to be added.
	 * @throws IllegalArgumentException if the student is null or if the student
	 *                                  can't be enrolled.
	 */
	public void enroll(Student s) {
		if (s == null || students.contains(s)) {
			throw new IllegalArgumentException();
		}
		if (students.size() == enrollmentCap) {
			waitlist.enqueue(s);
		} else {
			try {
				int size = students.size();
				students.add(size, s);
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}

	}

	/**
	 * Drops/Removes the student from the list.
	 * 
	 * @param s the student to be dropped.
	 * @throws IllegalArgumentException if student is null
	 */
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		if (students == null || students.size() == 0) {
			throw new IllegalArgumentException();
		}
		
		if (students.contains(s)) {
			try {
				students.remove(s);
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
 
			if (!waitlist.isEmpty()) {
				Student fromList = waitlist.dequeue();
				students.add(students.size(), fromList);
				fromList.getSchedule().addCourseToSchedule(course);
			}
			
		} 
		
		else {
			
			for (int i = 0; i < waitlist.size(); i++) {
				Student inWaitList = waitlist.dequeue();
				
				if (!s.equals(inWaitList)) {
					waitlist.enqueue(inWaitList);
				}
			}
		}
	}

	/**
	 * Checks if the student can be enrolled or not. Takes in a student as a
	 * parameter and returns a boolean of whether the given student can be added to
	 * the list or not.
	 * 
	 * @param s the student to be added
	 * @return addBoolean representing whether the student can be added or not.
	 */
	public boolean canEnroll(Student s) {
		if (students.size() == enrollmentCap && waitlist.size() == 10) {
			return false;
		}
		boolean listContains = false;
		LinkedQueue<Student> newWaitList = waitlist;
		int looper = newWaitList.size() - 1;
		for (int i = 0; i < looper; i++) {
			if (newWaitList.dequeue().equals(s)) {
				listContains = true;
			}
		}
		return !(students.contains(s) || listContains);
	}

	/**
	 * Provides the number of spots available for students to join with.
	 * 
	 * @return The number of available seats.
	 */
	public int getOpenSeats() {
		return enrollmentCap - students.size();
	}

	/**
	 * Gets the number of students currently in the wait list.
	 * 
	 * @return The size of the waitlist queue.
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

}