package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * This class creates a Faculty Directory, which helps maintain a list of
 * faculty members
 * 
 * @author Sydney Morisoli
 * @author Veerendra Gottiveeti
 *
 */
public class FacultyDirectory {
	/** Constant to represent hashing algorithm */
	public static final String HASH_ALGORITHM = "SHA-256";
	/** LinkedList representing the faculty directory for the university */
	private LinkedList<Faculty> facultyDirectory;

	/**
	 * Constructor for a Faculty Directory, utilizes the newFacultyDirectory method
	 * to set up the directory
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}

	/**
	 * Supports functionality for new faculty directory, updates LinkedList to a new
	 * LinkedList
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<>();
	}

	/**
	 * Loads list of faculty records from a file
	 * 
	 * @param file the filename to read files from
	 * @throws IllegalArgumentException if there is an issue loading the file and a
	 *                                  FileNotFoundException is caught
	 */
	public void loadFacultyFromFile(String file) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(file);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + file);
		}
	}

	/**
	 * Adds a Faculty member to the list of faculty
	 * 
	 * @param firstName  the first name of the faculty
	 * @param lastName   the last name of the faculty
	 * @param id         the ID of the faculty
	 * @param email      the email of the faculty
	 * @param password   the password of the faculty
	 * @param repeatPW   the repeated password to test for accuracy
	 * @param maxCourses the number of max courses of the faculty
	 * @return true if faculty could be added, false if not
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password,
			String repeatPW, int maxCourses) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPW == null || "".equals(password) || "".equals(repeatPW)) {
			throw new IllegalArgumentException("Invalid password");
		}
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			hashPW = new String(digest1.digest());
			
			MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest2.update(repeatPW.getBytes());
			repeatHashPW = new String(digest2.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
		
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equalsIgnoreCase(id)) {
				throw new IllegalArgumentException("Invalid id");
			}
		}
		return facultyDirectory.add(new Faculty(firstName, lastName, id, email, hashPW, maxCourses));
	}

	/**
	 * Removes a faculty from the faculty directory
	 * 
	 * @param id the ID of the faculty member to be removed
	 * @return true if possible, false if not found
	 */
	public boolean removeFaculty(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equalsIgnoreCase(id)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a 2D array, each row is a faculty and columns are first, last, and id
	 * 
	 * @return the 2D Array to help with GUI functionality
	 */
	public String[][] getFacultyDirectory() {
		if (facultyDirectory == null) {
			return null;
		}
		String[][] faculty = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			faculty[i][0] = facultyDirectory.get(i).getFirstName();
			faculty[i][1] = facultyDirectory.get(i).getLastName();
			faculty[i][2] = facultyDirectory.get(i).getId();
		}
		return faculty;
	}

	/**
	 * Saves the current faculty directory to the given file
	 * 
	 * @param file the name of the file to be saved to
	 * @throws IllegalArgumentException if an IOException is caught
	 */
	public void saveFacultyDirectory(String file) {
		try {
			FacultyRecordIO.writeFacultyRecords(file, facultyDirectory);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + file);
		}
	}

	/**
	 * Given an ID, finds and returns the Faculty member associated with that ID
	 * @param id the id of the faculty member to be found
	 * @return the faculty member with that ID value, null if can't be found
	 */
	public Faculty getFacultyById(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equalsIgnoreCase(id)) {
				return facultyDirectory.get(i);
			}
		}
		return null;
	}
}
