//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P07 Folder Explorer
// Course: CS 300 Fall 2021
//
// Author: Alan Luo
// Email: aluo7@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: (name of your pair programming partner)
// Partner Email: (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: Shawn Zhu - discussed things at a high level, Yesui Ulziibayar, discussed things at a high level
// Online Sources: none
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Arrays;

public class FolderExplorerTester {

	/*
	 * tests getContents() for different inputs
	 * 
	 * @param File folder represents the directory we're working in
	 * 
	 * @returns false if the test is failed, true if the test is passed
	 */
	public static boolean testGetContents(File folder) {
		try {
			// Scenario 1
			// list the basic contents of the cs300 folder
			ArrayList<String> listContent = FolderExplorer.getContents(folder);
			// expected output must contain "exams preparation", "grades",
			// "lecture notes", "programs", "reading notes", "syllabus.txt",
			// and "todo.txt" only.
			String[] contents = new String[] { "exams preparation", "grades", "lecture notes", "programs",
					"reading notes", "syllabus.txt", "todo.txt" };
			List<String> expectedList = Arrays.asList(contents);
			// check the size and the contents of the output
			if (listContent.size() != 7) {
				System.out.println("Problem detected: cs300 folder must contain 7 elements.");
				return false;
			}
			for (int i = 0; i < expectedList.size(); i++) {
				if (!listContent.contains(expectedList.get(i))) {
					System.out.println("Problem detected: " + expectedList.get(i)
							+ " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}
			// Scenario 2 - list the contents of the grades folder
			File f = new File(folder.getPath() + File.separator + "grades");
			listContent = FolderExplorer.getContents(f);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: grades folder must be empty.");
				return false;
			}
			// Scenario 3 - list the contents of the p02 folder
			f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
			listContent = FolderExplorer.getContents(f);
			if (listContent.size() != 1 || !listContent.contains("FishTank.java")) {
				System.out.println("Problem detected: p02 folder must contain only one file named FishTank.java.");
				return false;
			}

			// Scenario 4 - List the contents of a file
			f = new File(folder.getPath() + File.separator + "todo.txt");
			try {
				listContent = FolderExplorer.getContents(f);
				System.out.println(
						"Problem detected: Your FolderExplorer.getContents() must throw a NotDirectoryException if it is provided an input which is not a directory.");
				return false;
			} catch (NotDirectoryException e) { // catch only the expected exception
				// no problem detected
			}
			// Scenario 5 - List the contents of not found directory/file
			f = new File(folder.getPath() + File.separator + "music.txt");
			try {
				listContent = FolderExplorer.getContents(f);
				System.out.println(
						"Problem detected: Your FolderExplorer.getContents() must throw a NotDirectoryException if the provided File does not exist.");
				return false;
			} catch (NotDirectoryException e) {
				// behavior expected
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * tests the base case of getDeepContents() for common input
	 * 
	 * @param File folder represents the directory we're working in
	 * 
	 * @returns false if the test is failed, true if the test is passed
	 */
	public static boolean testDeepGetContentsBaseCase(File folder) {

		// checks base case on non recursive file
		try {
			ArrayList<String> listContent = FolderExplorer.getDeepContents(folder);
			File f = new File(folder + File.separator + "programs" + File.separator + "p01");
			listContent = FolderExplorer.getDeepContents(f);
			if (listContent.size() != 2) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (NotDirectoryException e) {
			System.out.println(e.getMessage());
			return false;
		}

		catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * tests getContents() for different inputs
	 * 
	 * @param File folder represents the directory we're working in
	 * 
	 * @returns false if the test is failed, true if the test is passed
	 */
	public static boolean testDeepListRecursiveCase(File folder) {

		// lists the file contents of the directory programs
		try {
			ArrayList<String> listContent = FolderExplorer.getContents(folder);
			File f = new File(folder + File.separator + "programs");
			listContent = FolderExplorer.getDeepContents(f);
			if (listContent.size() != 8) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (NotDirectoryException e) {
			System.out.println(e.getMessage());
			return false;
		}

		// lists the file contents of the directory exams preparation
		try {
			ArrayList<String> listContent = FolderExplorer.getContents(folder);
			File f = new File(folder + File.separator + "exams preparation");
			listContent = FolderExplorer.getDeepContents(f);
			if (listContent.size() != 2) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (NotDirectoryException e) {
			System.out.println(e.getMessage());
			return false;
		}

		// lists the file contents of the directory grades (should be empty)
		try {
			ArrayList<String> listContent = FolderExplorer.getContents(folder);
			File f = new File(folder + File.separator + "grades");
			listContent = FolderExplorer.getDeepContents(f);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (NotDirectoryException e) {
			System.out.println(e.getMessage());
			return false;
		}

		// lists the file contents of the directory p02 (should contain one file)
		try {
			ArrayList<String> listContent = FolderExplorer.getContents(folder);
			File f = new File(folder + File.separator + "programs" + File.separator + "p02");
			listContent = FolderExplorer.getDeepContents(f);
			if (listContent.size() != 1) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (NotDirectoryException e) {
			System.out.println(e.getMessage());
			return false;
		}

		// tries inputting a file that does not exist
		try {
			File f = new File(folder + File.separator + "music.txt");
			FolderExplorer.getDeepContents(f);
		} catch (NotDirectoryException e) {
			// System.out.println(e.getMessage());
			// return false;
			// behavior expected
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * tests lookupByName() for different inputs
	 * 
	 * @param File folder represents the directory we're working in
	 * 
	 * @returns false if the test is failed, true if the test is passed
	 */
	public static boolean testLookupByFileName(File folder) {

		// tests the reading in a file
		try {
			String content = FolderExplorer.lookupByName(folder, "zyBooksCh1.txt");
			String expectedContent = "cs300" + File.separator + "reading notes" + File.separator + "zyBooksCh1.txt";
			if (!expectedContent.equals(content)) {
				System.out.println("Problem detected: path does not equal expectedPath");
				return false;
			}
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
			return false;
		}

		// deep search and returns the file location of FishTank.java in string format
		try {
			String content = FolderExplorer.lookupByName(folder, "FishTank.java");
			String expectedContent = "cs300" + File.separator + "programs" + File.separator + "p02" + File.separator
					+ "FishTank.java";
			if (!expectedContent.equals(content)) {
				System.out.println("Problem detected: path does not equal expectedPath");
				return false;
			}
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
			return false;
		}

		// deep search for directory, catches NoSuchElementException
		try {
			String content = FolderExplorer.lookupByName(folder, "programs");
			System.out.println(content);
			String expectedContent = "cs300" + File.separator + "reading notes" + File.separator + "zyBooksCh1.txt";
			if (!expectedContent.equals(content)) {
				System.out.println("Problem detected: path does not equal expectedPath");
				return false;
			}
		} catch (NoSuchElementException e) {
			// System.out.println(e.getMessage());
			// return false;
			// behavior expected
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.lookupByName() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * tests the base case of lookupByKey() for common input
	 * 
	 * @param File folder represents the directory we're working in
	 * 
	 * @returns false if the test is failed, true if the test is passed
	 */
	public static boolean testLookupByKeyBaseCase(File folder) {

		// checks base case on file that matches key, returns empty array
		try {
			String key = ".txt";
			File f = new File(folder + File.separator + "lecture notes" + File.separator + "unit3" + File.separator
					+ "Sorting.txt");
			ArrayList<String> listContent = FolderExplorer.lookupByKey(f, key);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		// checks base case on file with null key, returns empty arrayList
		try {
			String key = null;
			File f = new File(folder + File.separator + "lecture notes");
			ArrayList<String> listContent = FolderExplorer.lookupByKey(f, key);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * tests lookupByKey() for different inputs
	 * 
	 * @param File folder represents the directory we're working in
	 * 
	 * @returns false if the test is failed, true if the test is passed
	 */
	public static boolean testLookupByKey(File folder) {

		// checks directory lecture notes and if the arrayList returned contains the
		// correct .txt files
		try {
			String key = ".txt";
			File f = new File(folder + File.separator + "lecture notes");
			ArrayList<String> listContent = FolderExplorer.lookupByKey(f, key);
			if (listContent.size() != 9) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		// checks directory unit1 and if the arrayList returned contains the correct
		// .txt files
		try {
			String key = ".txt";
			File f = new File(folder + File.separator + "lecture notes" + File.separator + "unit1");
			ArrayList<String> listContent = FolderExplorer.lookupByKey(f, key);
			if (listContent.size() != 3) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		// checks directory grades and if the arrayList returned zero files
		try {
			String key = ".txt";
			File f = new File(folder + File.separator + "grades");
			ArrayList<String> listContent = FolderExplorer.lookupByKey(f, key);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		// checks directory of writeUps with non-matching key, checks if arrayList
		// returned zero files
		try {
			String key = ".txt";
			File f = new File(folder + File.separator + "programs" + File.separator + "writeUps");
			ArrayList<String> listContent = FolderExplorer.lookupByKey(f, key);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		// checks directory of writeUps with matching key (.pdf), checks if arrayList
		// returned zero files
		try {
			String key = ".pdf";
			File f = new File(folder + File.separator + "programs" + File.separator + "writeUps");
			ArrayList<String> listContent = FolderExplorer.lookupByKey(f, key);
			if (listContent.size() != 3) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}

		// checks directory of writeUps with matching key (.java), checks if arrayList
		// returned zero files
		try {
			String key = ".java";
			File f = new File(folder + File.separator + "programs" + File.separator + "p01");
			ArrayList<String> listContent = FolderExplorer.lookupByKey(f, key);
			if (listContent.size() != 2) {
				System.out.println("Problem detected: array is the wrong size, does not contain correct file names");
				return false;
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * tests all of the FolderExplorer methods by calling the methods
	 * 
	 * @prints a formatted string detailing whether the test was passed or failed
	 */
	public static void main(String[] args) {
		System.out.println("testGetContents: " + testGetContents(new File("cs300")));
		System.out.println("testDeepGetContentsBaseCase: " + testDeepGetContentsBaseCase(new File("cs300")));
		System.out.println("testDeepListRecursiveCase: " + testDeepListRecursiveCase(new File("cs300")));
		System.out.println("testLookupByFileName: " + testLookupByFileName(new File("cs300")));
		System.out.println("testLookupByKeyBaseCase: " + testLookupByKeyBaseCase(new File("cs300")));
		System.out.println("testLookupByKey: " + testLookupByKey(new File("cs300")));
	}

}
