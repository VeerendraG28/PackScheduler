package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Creates a course class for WolfScheduler to allow for student's to choose
 * best schedule. Maintains information about course including name, credits,
 * start and end time along with days. Meeting days and time are set along with
 * Instructor ID, name, title , section and credits. Course keeps track of
 * information about the course to make the schedule including time. It is the
 * subclass of activity.
 * 
 * @author Susmitha Potu
 * @author Maverick Middleton
 * @author Veerendra Gottiveeti
 */
public class Course extends Activity implements Comparable<Course> {
	
	/** Length of the section */
	private static final int SECTION_LENGTH = 3;
	/** Maximum amount of credits. */
	private static final int MAX_CREDITS = 5;
	/** Minimum amount of credits. */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	
	/** Validator Used for the Course Names*/
	private CourseNameValidator validator  = new CourseNameValidator(); 
	/** course roll of roll */
	private CourseRoll roll;

	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap the capacity of students able to join a course.
	 * @param meetingDays  meeting days for Course as series of chars
	 * @param startTime    start time for Course
	 * @param endTime      end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		roll = new CourseRoll(this, enrollmentCap);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap the capacity of students able to join a course.
	 * @param meetingDays  meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 5 or more
	 * than 8, does not contain a space between letter characters and number
	 * characters, has less than 1 or more than 4 letter characters, and not exactly
	 * three trailing digit characters, an IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		// Throw exception if the name is null
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		boolean valid = false;
		try {
			valid = validator.isValid(name);
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		if (!valid) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		this.name = name;
	}

	/**
	 * Returns the Course's section.
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section. Course section cannot be a null value or have
	 * more/less than 3 digits.
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if section parameter is invalid.
	 */
	public void setSection(String section) {
		// Check that the section is not null.
		if (section == null) {
			throw new IllegalArgumentException("Invalid section.");
		}

		// Check that the section has the right amount of digits.
		if (section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}

		// Check that the section has only digits.
		char temp;
		for (int i = 0; i < SECTION_LENGTH; i++) {
			temp = section.charAt(i);
			if (!Character.isDigit(temp)) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		this.section = section;
	}

	/**
	 * Returns the Course's credits.
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credits. The credit count has to be in between a range of
	 * credit values, and has to be numerical.
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if credits parameter is invalid.
	 */
	public void setCredits(int credits) {
		// Checks if the value fits the required range.
		if (credits > MAX_CREDITS || credits < MIN_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}

	/**
	 * Returns the Instructor ID.
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {

		return instructorId;
	}

	/**
	 * Sets the Instructor ID. ID cannot be null or an empty string.
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructorId parameter is invalid.
	 */
	public void setInstructorId(String instructorId) {

		try {
			if("".equals(instructorId)){
				throw new IllegalArgumentException("Invalid instructor id.");
			}	
		} catch(NullPointerException e) {
			//empty
		}
		
		this.instructorId = instructorId;
	}


	/**
	 * Returns a comma separated value String of all Course fields. If it is
	 * arranged then the meeting string does not have the start time and end time If
	 * the meeting is not arranged, then in the comma separated list includes the
	 * start time and end time along with the standard details including title,
	 * section, credits , name, id and meeting days. It overrides because now we
	 * don't just have courses we also have events to deal with when handling
	 * scheduling and in the future their conflicts. It also includes the enrollment cap.
	 * 
	 * @return String representation of Course that is separated with comments, if
	 *         not arranged includes the start and end time.
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays()
				+ "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Starts the program.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Creates a unique code based upon the values of the fields.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
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
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Obtains the array values of the student schedule and catalog.
	 * 
	 * @return String[] Array of some Catalog/Schedule fields.
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] output = new String[5];
		output[0] = this.name;
		output[1] = this.section;
		output[2] = getTitle();
		output[3] = getMeetingString();
		output[4]  = String.valueOf(roll.getOpenSeats());
		return output;
	}

	/**
	 * Obtains values to compiled into the finished schedule.
	 * 
	 * @return String[] Final Copy of the Schedule.
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] output = new String[7];
		output[0] = this.name;
		output[1] = this.section;
		output[2] = getTitle();
		output[3] = Integer.toString(this.credits);
		output[4] = this.instructorId;
		output[5] = getMeetingString();
		output[6] = "";
		return output;
	}

	/**
	 * Compares a course object to another an
	 * 
	 * @param activity is the object being compared to the main course.
	 * @return boolean value if they're the same course.
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		// Checks to see if the activity object is a course.
		if (activity instanceof Course) {
			Course a = (Course) activity;
			// Compares the names of the courses to see if they're the same.
			return a.getName().equals(this.getName());
		}
		return false;
	}

	/**
	 * Override setMeetingDaysAndTime() in Course. Checks if meeting days is null or
	 * empty to throw exception. If meeting time is arranged and end and start time
	 * is not zero then exception is thrown. For arranged, the start and end time
	 * should be set to zero. Otherwise, the counter of each day goes up and
	 * exceptions are thrown if start time is greater than end time as well as if
	 * any of the day counters such as mondayCount or tuesdayCount are greater than
	 * 1 with an exception called "Invalid meeting days and times."
	 * Activity.setMeetingDaysAndTime() handle the common checks and final
	 * assignment. The counter goes up if the character matches to the certain day,
	 * which for monday is it M, Tuesday T, Wednesday W, Thursday H, and Friday is
	 * F. If is is this char, then the particular day counter goes up because there
	 * can't be duplicate days in it. It is override because now we have events as
	 * well as courses to handle in terms of scheduling and in the future conflicts.
	 * 
	 * @param meetingDays meeting days of Course
	 * @param startTime   start time of Course
	 * @param endTime     end time of Course
	 * @throws IllegalArgumentException invalid meeting days and times in which
	 * meeting days is empty or null. If start time or end time not equal to zero for arranged
	 * meetings. Throws if anything other than A,M,T,W,H,F. Also throws if any of the day
	 * counters are greater than one. If the start time is greater than the end time then the
	 * exception is also thrown with the message "Invalid meeting days and times."
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// Checks if meeting days is null or empty string
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		// Checks if the meeting days are arranged. If not, documents all dates.
		if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			super.setMeetingDaysAndTime(meetingDays, 0, 0);
		} else {
			int monday = 0;
			int tuesday = 0;
			int wednesday = 0;
			int thursday = 0;
			int friday = 0;
			char temp;
			for (int i = 0; i < meetingDays.length(); i++) {
				temp = meetingDays.charAt(i);
				if (temp == 'M') {
					monday++;
				} else if (temp == 'T') {
					tuesday++;
				} else if (temp == 'W') {
					wednesday++;
				} else if (temp == 'H') {
					thursday++;
				} else if (temp == 'F') {
					friday++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			// Checks for duplicates
			if (monday > 1 || tuesday > 1 || wednesday > 1 || thursday > 1 || friday > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			// Sets the values after testing each of them.
			super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		}
	}
	
	/**
	 * Compares two courses to tell which would be ordered before the other.
	 * Compares last name and section.
	 * 
	 * @param arg0 Course compared to the initial course used in the method.
	 * @return returnVal with -1 if it's less than the initial course, 1 if it
	 * is greater than the course, and 0 if the courses are equal.
	 */
	@Override
	public int compareTo(Course arg0) {
		//Getting String variables for the names.
				String nameA = this.getName();
				String nameB = arg0.getName();
				int output;
				int returnVal = 0;
				//Setting output equal to whatever we get from the comparison.
				output = nameA.compareTo(nameB);
				//-1 = Less than A
				//1 = Greater than A
				//0 = Equals to A
				//If the output is 0, meaning they are equal, move onto the next if statement.
				if (output == 0) {
					//Getting String variables for the first names.
					String sectionA = this.getSection();
					String sectionB = arg0.getSection();
					//Setting output equal to whatever we get from the comparison.
					output = sectionA.compareTo(sectionB);
					//If the output is 0, meaning they are equal, move onto the next if statement.
					if (output == 0) {
						//If the output is 0, meaning they are equal, return 0.
						returnVal = 0;
					}
					//If the output is is less than 0, meaning they are not equal, return -1.
					else if (output < 0) {
						returnVal = -1;
					}
					//If the output is is greater than 0, meaning they are not equal, return 1.
					else if (output > 0) {
						returnVal = 1;
					}
				}
				//If the output is is less than 0, meaning they are not equal, return -1.
				else if (output < 0) {
					returnVal = -1;
				}
				//If the output is is greater than 0, meaning they are not equal, return 1.
				else if (output > 0) {
					returnVal = 1;
				}
				return returnVal;
		
	}
	/**
	 * The method gets the course roll using the course roll class. 
	 * @return roll of the course roll.
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

}