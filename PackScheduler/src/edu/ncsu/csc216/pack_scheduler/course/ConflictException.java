/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * The ConflictException class extends the Exception, and this class is used to
 * handle conflicts that can occur during scheduling. It works with the other
 * method when building a schedule that is best for the student in terms of no
 * conflicting events/activities or courses. The fields includes a constant
 * which is the ID used for serialization, which is set to 1L. The custom
 * exception requires the author to create two constructors. One of the
 * constructors is a parameterized constructor with a String specifying a
 * message for the Exception object. Another is a parameterless constructor that
 * calls the parameterized constructor with specified default message. Works
 * with other classes with exception throwing when there is a conflict.
 * 
 * @author Maverick Middleton
 * @author Veerendra Gottiveeti
 *
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The first is a parameterized constructor with a String specific message for
	 * the Exception object. The message is passed to the parent’s constructor.
	 * 
	 * @param message custom error message
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * A parameterless constructor that calls the parameterized constructor with an
	 * author specified default message which is "Schedule conflict."
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}

}
