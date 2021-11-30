package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Reads in information for faculty and is able to write it into files as well.
 * 
 * @author Maverick Middleton
 * @author Veerendra Gottiveeti
 *
 */
public class FacultyRecordIO {

	/**
	 * Serves as an empty constructor.
	 */
	public FacultyRecordIO() {
		// Empty Constructor
	}

	/**
	 * Takes a file and reads in all the Faculty members into a list.
	 * 
	 * @param fileName The path to the file being read.
	 * @return a LinkedList containing all the faculty members within it.
	 * @throws FileNotFoundException If the file isn't found.
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		LinkedList<Faculty> output = new LinkedList<Faculty>();

		while (fileReader.hasNextLine()) {
			try {
				Faculty faculty = processFaculty(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < output.size(); i++) {
					Faculty currentIndex = output.get(i);
					if (currentIndex.equals(faculty)) {
						duplicate = true;
						break;
					}
				}
				
				if (!duplicate) {
					output.add(faculty);
				}
			} catch (Exception e) {
				// The Faculty isn't created simply.
			}
		}

		return output;
	}
	
	/**
	 * Returns a faculty obtained from the string provided.
	 * @param line The line being provided.
	 * @return The Faculty from the line.
	 */
	private static Faculty processFaculty(String line) {
		Scanner scan = new Scanner(line);
		scan.useDelimiter(",");
		Faculty output;
		try {
			String firstName = scan.next();
			String lastName = scan.next();
			String id = scan.next();
			String email = scan.next();
			String password = scan.next();
			int maxCourses = scan.nextInt();
			if (scan.hasNext()) {
				scan.close();
				throw new IllegalArgumentException("Too many tokens.");
			}
			scan.close();
			output = new Faculty(firstName, lastName, id, email, password, maxCourses);
			return output;
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid token or tokens.");
		}
	}

	/**
	 * Saves a list of faculty into a file.
	 * 
	 * @param fileName         Path of the file being saved.
	 * @param facultyDirectory The LinkedList of faculty being saved.
	 * @throws FileNotFoundException Throws an exception if the file output can't be found.
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws FileNotFoundException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < facultyDirectory.size(); i++) {
			fileWriter.println(facultyDirectory.get(i).toString());
		}
		fileWriter.close();
	}
}
