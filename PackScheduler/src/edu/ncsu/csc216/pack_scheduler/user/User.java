package edu.ncsu.csc216.pack_scheduler.user;
/**
 * The user class consists of the information of the user. It has the methods to get the first name, 
 * getting the first name, and getting and setting the last name, and getting the id and setting the id,
 * and you can also get the email and set the email as well as set and get the password,. This class works with the other classes
 * because it handles the information about each user that is wanted to login and make a schedule of their choice. The fields 
 * include the first name last name , id, and email and password all as a string. The class is an abstract class, and it cannot be instantiated.
 * @author Maverick Middleton
 * @author Susmitha Potu
 *
 */
public abstract class User {

	/** First Name of user */
	private String firstName;
	/** Last Name of user */
	private String lastName;
	/** ID of user */
	private String id;
	/** Email of user */
	private String email;
	/** Password of user */
	private String password;

	
	/**
	 * Serves as the basic constructor of the user.
	 * @param firstName The user's first name.
	 * @param lastName The user's last name.
	 * @param id The user's Id.
	 * @param email The user's email.
	 * @param password The user's password for logging in.
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Gets the user's first name.
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the user firstName.
	 * @param firstName value from user object.
	 * @throws IllegalArgumentException if the first Name is null/empty.
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		
		this.firstName = firstName;
	}

	/**
	 * Gets the user's last name.
	 * @return lastName of user
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of user.
	 * @param lastName value from user object.
	 * @throws IllegalArgumentException if the last name is null/empty.
	 */
	public void setLastName(String lastName) {
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		
		this.lastName = lastName;
	}

	/**
	 * Gets the user ID
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the user ID.
	 * @param id value in user object.
	 * @throws IllegalArgumentException if the id is empty/null.
	 */
	protected void setId(String id) {
		if (id == null || "".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Gets the user email.
	 * @return email value in user object.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email value for the user.
	 * @param email value in user object.
	 * @throws IllegalArgumentException if email is written incorrectly/null.
	 */
	public void setEmail(String email) {
		if (email == null || "".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (!(email.contains("@"))) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (!(email.contains("."))) {
			throw new IllegalArgumentException("Invalid email");
		}
		else {
			int periodLocation = email.lastIndexOf(".");
			int atSignLocation = email.indexOf("@");
			if (atSignLocation > periodLocation) {
				throw new IllegalArgumentException("Invalid email");
			}
		}
		this.email = email;
		
	}

	/**
	 * Returns the user's password.
	 * @return password in user object.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's Password.
	 * @param password in user object.
	 * @throws IllegalArgumentException if the user password is empty/null.
	 */
	public void setPassword(String password) {
		if (password == null || "".equals(password)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
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
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	
	

}