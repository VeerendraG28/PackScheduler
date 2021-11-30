package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Serves as the class for the InvalidTransitionException
 * when the course name is invalid due to not following
 * the FSM properly. This works with the other classes throw the invalid transition exception with the FSM
 * states and transitions with the schedule.
 * @author Susmitha Potu
 * @author Maverick Middleton
 * @author Veerendra Gottiveeti
 *
 */
public class InvalidTransitionException extends Exception {
	
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The ConflictException if there is a defined message.
	 * @param message The output from the exception.
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * The ConflictException if there is no defined message.
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}

}