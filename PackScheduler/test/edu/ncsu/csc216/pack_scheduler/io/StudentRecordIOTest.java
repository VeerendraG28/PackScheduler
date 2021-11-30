package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Tests StudentRecordIO.
 * @author Connor Bunch
 * @author Maverick Middleton
 * @author Luke Getzen
 */

class StudentRecordIOTest {
	
	/** Valid student records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Invalid student records */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	/** Expected results for valid courses in student_records.txt - line 1 */	
	private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
			
	/** Expected results for valid courses in student_records.txt - line 2 */	
	private String validStudent1 =  "Lane,Berg,lberg,sociis@non.org,pw,14";
			
	/** Expected results for valid courses in student_records.txt - line 3 */	
	private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
			
	/** Expected results for valid courses in student_records.txt - line 4 */	
	private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** Expected results for valid courses in student_records.txt - line 5 */	
	private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** Expected results for valid courses in student_records.txt - line 6 */	
	private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** Expected results for valid courses in student_records.txt - line 7 */	
	private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** Expected results for valid courses in student_records.txt - line 8 */	
	private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	/** Expected results for valid courses in student_records.txt - line 9 */	
	private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** Expected results for valid courses in student_records.txt - line 10 */	
	private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
			
	/** A string array with the validStudent variables being saved to it. */
	private final String [] validStudents = {validStudent0, validStudent1, validStudent2, validStudent3, validStudent4, validStudent5, validStudent6, validStudent7,
			validStudent8, validStudent9};
	/** Private String Array with all the names of the validStudents.*/
	private final String [] validStudentsNames = {"Demetrius", "Lane", "Raymond", "Emerald", "Shannon", "Althea", "Zahir", "Dylan",
			"Cassandra", "Griffith"};
	/**In order to preserve security, a string variable called hashPW is created to protect the students passwords.*/
	private String hashPW;
	/**A Hash Algorithm variable. */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Gets a new password for each student before the test starts.
	 */
	@BeforeEach
	void setUp() {
		 try {
		        String password = "pw";
		        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
		        digest.update(password.getBytes());
		        hashPW = new String(digest.digest());
		        
		        for (int i = 0; i < validStudents.length; i++) {
		            validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
		        }
		    } catch (NoSuchAlgorithmException e) {
		        fail("Unable to create hash during setup");
		    }
	}

	/**
	 * Tests readStudentRecords().
	 */
	@Test
	void testReadStudentRecords() {
		
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, students.size());
			
			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudentsNames[i], students.get(i).getFirstName());
				
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}
	/**
	 * Tests readInvalidStudentRecords().
	 */
	@Test
	public void testReadInvalidStudentRecords() {
		SortedList<Student> students;
		try {
			students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests writeStudentRecords()
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
		} catch (IOException e) {
			fail("Cannot write to student records file");
		}
		
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	} 
	
	///**
	// * Tests writeStudentRecords() with no permissions.
	// */
	//@Test
	//public void testWriteStudentRecordsNoPermissions() {
	//	SortedList<Student> students = new SortedList<Student>();
	//	students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
	//	
	//	Exception exception = assertThrows(IOException.class, 
	//			() -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students));
	//	assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", exception.getMessage());
	//}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
			 Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
			
			while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act); 
				//The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}