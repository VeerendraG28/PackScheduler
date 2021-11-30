package edu.ncsu.csc216.pack_scheduler.user.schedule;


import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * The schedule class works very similarly to the WolfScheduler class from the
 * guided projects. The class works with other classes controlling the
 * scheduling process. This is done through the use of several methods which are
 * the add course and remove course to and from schedule Additionally, there is
 * a reset schedule and set title and get title method along with get scheduled
 * courses. The fields of this class are the string title which is of the
 * schedule and there is an arraylist of courses of the schedule.
 * 
 * @author Susmitha Potu
 *
 */
public class Schedule {
 
	/** title of the schedule */
	private String title;
	/** Array list of courses of schedule */
	private ArrayList<Course> schedule;

	/**
	 * Constructor of the schedule class. The constructor of Schedule creates an
	 * empty ArrayList of Courses. The title should be initialized to â€œMy Scheduleâ€�
	 */
	public Schedule() {
		title = "My Schedule";
		schedule = new ArrayList<Course>();
	}

	/**
	 * This method should add the Course to the end of the schedule and return true
	 * if the Course was added. if the Course is a duplicate to one already in the
	 * list through either equals() or via the isDuplidate() method, then an
	 * IllegalArgumentException should be thrown If there is a conflict with an
	 * existing Course in the schedule, a ConflictException is thrown from the call
	 * to Course.checkConflict().
	 * 
	 * @param course course in the schedule
	 * @return true if the course was added to the schedule with no exception thrown
	 * @throws IllegalArgumentException if the course cannot be added or student is
	 *                                  already enrolled in course.
	 */
	public boolean addCourseToSchedule(Course course) {
		if (course == null) {
			throw new NullPointerException();
		}

		try {
			for (int i = 0; i < schedule.size(); i++) {
				if (course.isDuplicate(schedule.get(i))) {
					throw new IllegalArgumentException("You are already enrolled in " + course.getName());
				}
				course.checkConflict(schedule.get(i));
			}
			schedule.add(schedule.size(), course);
		} catch (ConflictException e) {
			throw new IllegalArgumentException("The course cannot be added due to a conflict.");
		}
		return true;
	}

	/**
	 * Schedule.removeCourseFromSchedule(Course) should remove the Course from the
	 * schedule and return true if the Course was removed and false if there was not
	 * a Course to remove.
	 * 
	 * @param course course of the schedule that wants to be removed
	 * @return return true if the Course was removed and false if there was not a
	 *         Course to remove.
	 */
	public boolean removeCourseFromSchedule(Course course) {
		if (course == null) {
			//throw new NullPointerException();
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			if (course.equals(schedule.get(i))) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Schedule.resetSchedule() should create a new schedule ArrayList and reset the
	 * title to the default value.
	 */
	public void resetSchedule() {
		title = "My Schedule";
		schedule = new ArrayList<Course>();
	}

	/**
	 * Schedule.getScheduledCourses() should return a 2D array of Course
	 * information. Each row should be a Course and the columns are name, section,
	 * title, and the meeting string (i.e., Course.getShortDisplayArray()).
	 * 
	 * @return the course schedule as a 2D array with details of the course
	 */
	public String[][] getScheduledCourses() {
		String[][] newSchedule = new String[schedule.size()][5];
		for (int i = 0; i < schedule.size(); i++) {
			String[] output = schedule.get(i).getShortDisplayArray();
			for (int j = 0; j < newSchedule[i].length; j++) {
				newSchedule[i][j] = output[j];

			}
		}
		return newSchedule;
	}

	/**
	 * This is the setter method for the title of the schedule
	 * 
	 * @param title title of the schedule
	 * @throws IllegalArgumentException if the title is null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}

	/**
	 * This method gets the current title of the schedule
	 * 
	 * @return title the current title of the schedule
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * The method will get the schedule credits using a for loop. This will be used
	 * in the schedule to be able to get the credits that are in a particular schedule of 
	 * enrolled courses.
	 * @return creditsTotal in an integer of credits of schedule.
	 */
	public int getScheduleCredits() {
		int creditsTotal = 0;
		for(int i = 0; i < schedule.size(); i++) {
			creditsTotal += schedule.get(i).getCredits();
		}
		return creditsTotal;
	}
	/**
	 * This is a boolean method that states if the course that is desired can be added to the
	 * student schedule by returning true if it can and false if it is determined it cannot be added to the 
	 * schedule
	 * @param course course to be added to schedule
	 * @return true if the course can be added to the schedule.
	 */
	public boolean canAdd(Course course) {
		if (course == null) {
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			if(schedule.get(i).isDuplicate(course)) {
				return false;
			}
			try {
				schedule.get(i).checkConflict(course);
			} catch (ConflictException e) {
				return false;
			}
		}
		return true;
		
	}
}