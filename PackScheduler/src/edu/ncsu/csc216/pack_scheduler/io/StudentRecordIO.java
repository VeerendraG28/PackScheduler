package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * This program takes in a file of students, processes the information, saves it to a new student and adds
 * it to a student directory sorted list. The methods read and write student records using scanner and file writer.
 * The read student method that uses scanner to read each line of information about student or student records.
 * The student record IO deals with the input and output needed for developing the scheduler.
 * 
 * @author Connor Bunch
 * @author Luke Getzen
 * @author Maverick Middleton
 */

public class StudentRecordIO {

	/**
     * Reads student records from a file and generates a list of valid Students.  Any invalid
     * Students are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Student records from
     * @return a list of valid Students
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		//Creates a scanner that reads through the file.
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		//An empty SortedList of Student objects is created.
		SortedList<Student> students = new SortedList<Student>();
		//While there is another line in the file, this while loop will continue.
		while (fileReader.hasNextLine()) {
			//A try catch method to catch any possible errors within the files.
			try {
				Student student = processStudent(fileReader.nextLine());
				// A boolean flag is created to see if there are any duplicate student Objects.
				boolean duplicate = false;
				//Iterates through the students list.
				for (int i = 0; i < students.size(); i++) {
					//Gets the student that is at the current index.
					User currentIndex = students.get(i);
					//Checks to see if there is a duplicate student by checking
					//their first and last name.
					if (student.getFirstName().equals(currentIndex.getFirstName()) &&
							student.getLastName().equals(currentIndex.getLastName())) {
						//If there is a duplicate, the flag will be set to true and 
						//it will break out of the loop.
						duplicate = true;
						break;
					}
				
				}
				//If there is not a duplicate then the student will be added to the object.
				if (!duplicate) {
					students.add(student);
				}
			} catch (IllegalArgumentException e) {
				//The line is invalid because we couldn't create a student.
			}
		 }
		//Closing the file reader and returning the new students object.
		fileReader.close();
		return students;
	}
	
	/**
	 * Process the lines from the scanner and break them up into tokens and return a newly constructed
	 * student object.
	 * 
	 * @param line to process from a scanner.
	 * @return newly constructed student object.
	 * @throws IllegalArgumentException if there is too many tokens, or if the token is invalid.
	 */
	private static Student processStudent(String line) {
		//Creates a scanner and uses a delimiter to break up big stings.
		Scanner scan = new Scanner(line);
		scan.useDelimiter(",");
		Student info;
		try {
			//Reading in tokens for firstName, lastName, id, email, password and maxCredits.
			String firstName = scan.next();
			String lastName = scan.next();
			String id = scan.next();
			String email = scan.next();
			String password = scan.next();
			int maxCredits = scan.nextInt();
			//If there is another token after these, throw IAE.
			if (scan.hasNext()) {
				scan.close();
				throw new IllegalArgumentException("Too many tokens.");
			}
			//Closing the scanner.
			scan.close();
			//Constructing a new Student object and returning it.
			info = new Student(firstName, lastName, id, email, password, maxCredits);
			return info;
			
			//Catches any exceptions and throws IAE.
		} catch(Exception e) {
			throw new IllegalArgumentException("Invalid token or tokens.");
		}
		
	}
	
	/**
     * Writes the given list of Students  
     * @param fileName file to write students 
     * @param studentDirectory list of Students to write
     * @throws IOException if cannot write to file
     */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		// A for loop to iterate through the student SortedList and print the contents
    	//using the file writer.
		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}
		//Closes the fileWriter.
		fileWriter.close();
	}

}