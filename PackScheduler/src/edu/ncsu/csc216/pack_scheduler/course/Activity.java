package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Serves as the abstract superclass for course and event. Also works as a class
 * connected to events, to be able to create both types of objects for this
 * project, leading to a more detailed scheduler software.
 * 
 * @author Maverick Middleton
 *
 */
public abstract class Activity implements Conflict {

	/** Amount of hours in a day. */
	private static final int UPPER_HOUR = 24;
	/** Amount of minutes in an hour. */
	private static final int UPPER_MINUTE = 60;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	/**
	 * Serves as the basic constructor for the activity object, that can be called
	 * upon by Course.
	 * 
	 * @param title       title of Course
	 * @param meetingDays meeting days for Course as series of chars
	 * @param startTime   start time for Course
	 * @param endTime     end time for Course
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);

	}

	/**
	 * Obtains the array values of the student schedule and catalog. Only includes 4
	 * values.
	 * 
	 * @return String[] Array of some Catalog/Schedule fields.
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Obtains values to compiled into the finished schedule. Includes all values.
	 * 
	 * @return String[] Final Copy of the Schedule.
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Checks if duplicates of activity exist in schedule already.
	 * 
	 * @param activity is the object being tested in the schedule.
	 * @return boolean value on whether event already exists or not.
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Obtains values to compiled into the finished schedule.
	 * 
	 * @param meetingDays the meetingDays to set
	 * @param startTime   the startTime to set
	 * @param endTime     the endTime to set
	 * @throws IllegalArgumentException if the information is invalid.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// Checks if meeting days is null or empty string
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		// Declares values to break up the 2 times into minutes/hours.
		int tempStart = startTime;
		int tempEnd = endTime;
		int startHours;
		int endHours;
		int startMinutes;
		int endMinutes;
		int num;

		// Checks to make sure it isn't an overnight activity.
		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		// Start Time Breakup
		startMinutes = tempStart % 10;
		tempStart /= 10;
		num = tempStart % 10;
		tempStart /= 10;
		num *= 10;
		startMinutes += num;
		startHours = tempStart % 10;
		tempStart /= 10;
		num = tempStart % 10;
		num *= 10;
		startHours += num;

		// End Time Breakup
		endMinutes = tempEnd % 10;
		tempEnd /= 10;
		num = tempEnd % 10;
		tempEnd /= 10;
		num *= 10;
		endMinutes += num;
		endHours = tempEnd % 10;
		tempEnd /= 10;
		num = tempEnd % 10;
		num *= 10;
		endHours += num;

		// Checks to make sure time fits military standards.
		if (startHours >= UPPER_HOUR || startHours < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endHours >= UPPER_HOUR || endHours < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (startMinutes >= UPPER_MINUTE || startMinutes < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endMinutes >= UPPER_MINUTE || endMinutes < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;

	}

	/**
	 * Returns the Activity's title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity's title. Activity title cannot be null or an empty value.
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title parameter is invalid
	 */
	public void setTitle(String title) {
		// Check that the title fits the base requirements.
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}

		this.title = title;
	}

	/**
	 * Returns the Activity's meeting days.
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Activity's start time.
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Activity's end time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Generates a string summarizing the schedule for the Activity. Output is
	 * converted from military time to standard.
	 * 
	 * @return String of the Activity object.
	 */
	public String getMeetingString() {
		// Checks if it's Arranged or not.
		String output = "";
		if ("A".equals(meetingDays)) {
			return "Arranged";
		} else {
			output += meetingDays + " ";

			// Start Time Conversion
			int startHour = startTime / 100;
			int startMinute = startTime % 100;
			boolean isPM = false;
			if (startHour == 12) {
				isPM = true;
			} else if (startHour > 12) {
				isPM = true;
				startHour -= 12;
			}
			output += startHour + ":";
			if (startMinute < 10) {
				output += "0";
			}
			output += startMinute;
			if (isPM) {
				output += "PM-";
			} else {
				output += "AM-";
			}

			// End Time Conversion
			int endHour = endTime / 100;
			int endMinute = endTime % 100;
			isPM = false;
			if (endHour == 12) {
				isPM = true;
			} else if (endHour > 12) {
				isPM = true;
				endHour -= 12;
			}
			output += endHour + ":";
			if (endMinute < 10) {
				output += "0";
			}
			output += endMinute;
			if (isPM) {
				output += "PM";
			} else {
				output += "AM";
			}

			return output;
		}

	}

	/**
	 * Generates a hashCode for Activity using all fields. It overrides because now
	 * we don't just have courses we also have events to deal with when handling
	 * scheduling and in the future their conflicts.
	 * 
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields. Override
	 * because course and events are not the same and we have now have to deal with
	 * events (activity).
	 * 
	 * @param obj the Object to compare
	 * @return true if the objects are the same on all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * It is an instance method within the Activity class, and it checks for a
	 * conflict by checking the current instance with a parameter called
	 * possibleConflictingActivity. Something is considered a conflict when there is
	 * an overlap in its day and time. In terms of time, no event or course should
	 * start at the time another ends by the same minute. This is an override
	 * because when it comes to conflict we have to deal with both event as well as
	 * course.
	 * 
	 * @param possibleConflictingActivity is the object being compared with the
	 *                                    other activity.
	 * @throws ConflictException if there is an issue with the 2 activities.
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		//Gets the meeting days from activities.
		String meetupA = this.getMeetingDays();
		String meetupB = possibleConflictingActivity.getMeetingDays();
		boolean overlappingDays = false;
		char temp;
		//Checks the meeting day strings to see if they share any of the same characters.
		for (int i = 0; i < meetupA.length(); i++) {
			temp = meetupA.charAt(i);
			for (int j = 0; j < meetupB.length(); j++) {
				if (temp == meetupB.charAt(j) && temp != 'A') {
						overlappingDays = true;
				}
			}
		}
		//Through a few logic gates, checks to see if any of the times overlap.
		if (overlappingDays) {
			int startA = this.getStartTime();
			int endA = this.getEndTime();
			int startB = possibleConflictingActivity.getStartTime();
			int endB = possibleConflictingActivity.getEndTime();
			if (startA == startB || startA == endB || endA == startB || endA == endB) {
				throw new ConflictException();
			}
			if (startA > startB && startA < endB) {
				throw new ConflictException();
			}
			if (startB > startA && startB < endA) {
				throw new ConflictException();
			}
			if (endA > startB && endA < endB) {
				throw new ConflictException();
			}
			if (endB > startA && endB < endA) {
				throw new ConflictException();
			}
		}
		

	}

}