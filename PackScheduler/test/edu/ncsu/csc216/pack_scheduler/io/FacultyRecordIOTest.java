package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * Makes sure the FacultyRecordIO returns an empty list if an error occurs.
 * @author Maverick Middleton
 *
 */
class FacultyRecordIOTest {
	
	/**
	 * Tries reading an invalid file.
	 * @throws FileNotFoundException If file isn't found.
	 */
	@Test
	void testException() throws FileNotFoundException {
		assertDoesNotThrow( () -> new FacultyRecordIO());
			assertEquals(0, FacultyRecordIO.readFacultyRecords("test-files/invalid_faculty_records.txt").size());
		
	}

}
