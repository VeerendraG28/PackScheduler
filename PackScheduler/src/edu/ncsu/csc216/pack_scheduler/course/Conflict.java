/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Conflict is an interface for which the classes will implement, which will allow for treating those classes as the interface type.
 * The conflict interface defines what the program can do, while the other classes define what the program is.
 * The checkConflict method is used to check if there a conflict by comparing a certain activity that could be in conflict with another activity, and 
 * if there is a conflict then the activity cannot be added and it will throw a conflict exception.
 * @author Maverick Middleton
 * @author Susmitha Potu
 */
public interface Conflict {
	
	/**
	 * Checks for a conflict with an activity object. This is checked by throwing an exception if there is a 
	 * conflict present in the schedule of the user.
	 * @param possibleConflictingActivity Activity object being checked.
	 * @throws ConflictException Error that occurs when there is a conflict of activities.
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}