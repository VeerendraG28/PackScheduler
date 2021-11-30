package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * The class CourseNameValidator will ensure that the course name is accurate in different states.
 * For example, in this class we check using different state with a different and accurate number of digits and amount of letter
 * that a course can have. One such example is State L, State LL, and State L, which shows the different state names with the various amount of letters
 * a course can hold to be accurate which is verified through this class.
 * @author Maverick Middleton
 * @author Divya
 * @author susmithapotu
 */
public class CourseNameValidator {
	
	/** Initial State used for iteration*/
	private State stateInitial;
	
	/** State when there's 1 letter*/
	private StateL stateL = new StateL();
	
	/** State when there's 2 letters*/
	private StateLL stateLL = new StateLL();
	
	/** State when there's 3 letters*/
	private StateLLL stateLLL = new StateLLL();
	
	/** State when there's 4 letters*/
	private StateLLLL stateLLLL = new StateLLLL();
	
	/** State when there's 1 number*/
	private StateD stateD = new StateD();
	
	/** State when there's 2 numbers*/
	private StateDD stateDD = new StateDD();
	
	/** State when there's 3 numbers*/
	private StateDDD stateDDD = new StateDDD();
	
	/** State when there's a suffix*/
	private StateSuffix stateSuffix = new StateSuffix();
	
	/**
	 * Serves as the base state with all possible FSM states
	 * declared as classes extended from this.
	 * @author Maverick Middleton
	 *
	 */
	private abstract class State {
		
		/**
		 * Checks if the next character being a letter is legal to the format.
		 * @throws InvalidTransitionException if it does not fit the expected format.
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 * @throws InvalidTransitionException if it does not fit the expected format.
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * Throws an error for providing an illegal format
		 * @throws InvalidTransitionException due to the name not being able to have these characters.
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}
	
	/**
	 * State when there's only one letter
	 * The state L extends the state class
	 * @author Maverick Middleton
	 *
	 */
	private class StateL extends State {
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onLetter() {
			stateInitial = stateLL;
			
		}
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onDigit() {
			stateInitial = stateD;
		}
		
	}
	
	/**
	 * State when there's only two letters
	 * he state LL extends the state class
	 * @author Maverick Middleton
	 *
	 */
	private class StateLL extends State {
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onLetter() {
			stateInitial = stateLLL;
		}
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onDigit() {
			stateInitial = stateD;
			
		}
		
	}
	
	/**
	 * State when there's three letters
	 * he state LLL extends the state class
	 * @author Maverick Middleton
	 *
	 */
	private class StateLLL extends State {
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onLetter() {
			stateInitial = stateLLLL;
			
		}
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onDigit() {
			stateInitial = stateD;
			
		}
		
	}
	
	/**
	 * State when there's four letters
	 * he state LLLL extends the state class
	 * @author Maverick Middleton
	 *
	 */
	private class StateLLLL extends State {
		
		/**
		 * Checks if the next character being a letter is legal to the format.
		 * @throws InvalidTransitionException if it does not fit the expected format.
		 */
		@Override
		public void onLetter() throws InvalidTransitionException{
			throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			
		}
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onDigit() {
			stateInitial = stateD;
			
		}
		
	}
	
	/**
	 * State when there's one number
	 * The state D extends the state class
	 * @author Maverick Middleton
	 *
	 */
	private class StateD extends State {
		
		/**
		 * Checks if the next character being a letter is legal to the format.
		 * @throws InvalidTransitionException if it does not fit the expected format.
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must have 3 digits.");
			
		}
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onDigit() {
			stateInitial = stateDD;
			
		}
		
	}
	
	/**
	 * State when there's two numbers
	 * he state DD extends the state class
	 * @author Maverick Middleton
	 *
	 */
	private class StateDD extends State {
		
		/**
		 * Checks if the next character being a letter is legal to the format.
		 * @throws InvalidTransitionException if it does not fit the expected format.
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must have 3 digits.");
			
		}
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onDigit() {
			stateInitial = stateDDD;
			
		}
		
	}
	
	/**
	 * State when there's three numbers
	 * he state DDD extends the state class
	 * @author Maverick Middleton
	 *
	 */
	private class StateDDD extends State {
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 */
		@Override
		public void onLetter() {
			stateInitial = stateSuffix;
			
		}
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 * @throws InvalidTransitionException if it does not fit the expected format.
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have 3 digits.");
			
		}
		
	}
	
	/**
	 * State when there's a suffix
	 * he state suffix extends the state class
	 * @author Maverick Middleton
	 *
	 */
	private class StateSuffix extends State {
		
		/**
		 * Checks if the next character being a letter is legal to the format.
		 * @throws InvalidTransitionException if it does not fit the expected format.
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
			
		}
		
		/**
		 * Checks if the next character being a number is legal to the format.
		 * @throws InvalidTransitionException if it does not fit the expected format.
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
			
		}
		
	}

	/**
	 * Checks the string's validity through running each character of it through the various states.
	 * @param courseName provided string being checked for validity.
	 * @throws InvalidTransitionException if the string does not fit the expected format.
	 * @return True if the name is valid, and false otherwise.
	 */
	public boolean isValid(String courseName ) throws InvalidTransitionException {
		char c;
			for (int i = 0; i < courseName.length(); i++) {
				//Initially sets the state or throws an error from the first character being invalid.
				if (i == 0) {
					c = courseName.charAt(i);
					if(Character.isLetter(c)) {
						stateInitial = stateL;
					}
					else if(Character.isDigit(c)){
						throw new InvalidTransitionException("Course name must start with a letter.");
					}
					else {
						throw new InvalidTransitionException("Course name can only contain letters and digits.");
					}
				}
				else {
					c = courseName.charAt(i);
					if(Character.isLetter(c)) {
						stateInitial.onLetter();
					}
					else if(Character.isDigit(c)) {
						stateInitial.onDigit();
					}
					else {
						stateInitial.onOther();
					}
				}
				
				
			}
			if (courseName.length() < 4) {
				return false;
			}
			return stateInitial == stateDDD || stateInitial == stateSuffix;
			
	}
	
}