import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FolderExplorer {

	/*
	 * takes the given File object and gets the names of all the folders and files
	 * inside it without going deeper
	 * 
	 * @param currentDirectory of object File, represents the directory the files
	 * are being taken from
	 * 
	 * @returns an arrayList of names with the names of the folders and files inside
	 * of it
	 * 
	 * @throws NotDirectoryException if the given parameter is not a directory
	 */
	public static ArrayList<String> getContents(File currentDirectory) throws NotDirectoryException {

		// Declares and initializes a new ArrayList names representing the names of all
		// files and directories
		ArrayList<String> names = new ArrayList<>();
		// local variable with the files
		File[] contents = currentDirectory.listFiles();

		// Throws NotDirectoryException with a description error message if
		// the provided currentDirectory does not exist or if it is not a directory
		if (!currentDirectory.isDirectory() || !currentDirectory.exists()) {
			throw new NotDirectoryException(currentDirectory + " is not a directory");
		} else {
			// Returns a list of the names of all files and directories in
			// the the given folder currentDirectory.
			for (int i = 0; i < contents.length; i++) {
				names.add(contents[i].getName());
			}
			return names;
		}
	}

	/*
	 * takes the given File object and gets the names of all the files within the
	 * given directory
	 * 
	 * @param currentDirectory of object File, represents the directory the files
	 * are being taken from
	 * 
	 * @returns arrayList names representing the files within the directory
	 * 
	 * @throws NotDirectoryException if currentDirectory is a file or if it doesn't
	 * exist
	 */
	public static ArrayList<String> getDeepContents(File currentDirectory) throws NotDirectoryException {

		// Declares and initializes a new ArrayList names representing the names of all
		// files and directories
		ArrayList<String> names = new ArrayList<>();
		// local variables with the files
		File[] contents = currentDirectory.listFiles();

		// Recursive method that lists the names of all the files (not directories)
		// in the given directory and its sub-directories.
		// Throws NotDirectoryException with a description error message if
		// the provided currentDirectory does not exist or if it is not a directory
		if (!currentDirectory.isDirectory() || !currentDirectory.exists()) {
			throw new NotDirectoryException(currentDirectory + " is not a directory");
		} else {
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isFile()) {
					names.add(contents[i].getName());
				} else if (contents[i].isDirectory()) {
					names.addAll(getDeepContents(contents[i]));
				}
			}
			return names;
		}
	}

	/*
	 * takes the given File object and gets the names of all the files that match
	 * the given name within the given directory
	 * 
	 * @param currentDirectory of object File, represents the directory the files
	 * are being taken from, fileName of type String which represents the file we're
	 * searching for
	 * 
	 * @returns a String representing the path to the file that matches the file
	 * name
	 * 
	 * @throws NoSuchElementException if the given fileName is null or if the
	 * currentDirectory is a file
	 */
	public static String lookupByName(File currentDirectory, String fileName) throws NoSuchElementException {

		// local variable representing the path to the file to be returned
		String result = "";
		// local variable with the files
		File[] contents = currentDirectory.listFiles();

		// Throws NoSuchElementException with a descriptive error message if the
		// search operation returns with no results found (including the case if
		// fileName is null or currentDirectory does not exist, or was not a directory)
		// Searches the given directory and all of its sub-directories for
		// an exact match to the provided fileName. This method must be
		// recursive or must use a recursive private helper method to operate.
		// This method returns a path to the file, if it exists.
		if (fileName == null || !currentDirectory.isDirectory()) {
			throw new NoSuchElementException("Error, element does not exist");
		} else {
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].getName().equals(fileName) && contents[i].isFile()) {
					result += currentDirectory.getName() + File.separator + fileName;
				} else if (contents[i].isDirectory()) {
					try {
						result += currentDirectory.getName() + File.separator + lookupByName(contents[i], fileName);
					} catch (NoSuchElementException n) {
					}
				}
			}
		}
		if (result.equals("")) {
			throw new NoSuchElementException("Error, element does not exist");
		} else {
			return result;
		}
	}

	/*
	 * takes the given File object and gets the names of all the files that match
	 * the given key within the directory
	 * 
	 * @param currentDirectory of object File, represents the directory the files
	 * are being taken from, key of type String which represents the key of the
	 * files we're searching for
	 * 
	 * @returns an arrayList representing the files within the directory that
	 * contains the key
	 * 
	 * @throws NoSuchElementException if the given key is null or if the
	 * currentDirectory is a file
	 */
	public static ArrayList<String> lookupByKey(File currentDirectory, String key) {

		// Declares and initializes a new ArrayList names representing the names of all
		// files and directories
		ArrayList<String> names = new ArrayList<>();
		// local variable with the files
		File[] contents = currentDirectory.listFiles();

		// Recursive method that searches the given folder and its sub-directories
		// for ALL files that contain the given key in part of their name.
		// Returns An arraylist of all the names of files that match and an empty
		// arraylist when the operation returns with no results found (including
		// the case where currentDirectory is not a directory).
		if (!currentDirectory.isDirectory() || key == null || !currentDirectory.exists()) {
			return names;
		} else {
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].getName().contains(key)) {
					names.add(contents[i].getName());
				} else if (contents[i].isDirectory()) {
					names.addAll(lookupByKey(contents[i], key));
				}
			}
		}
		return names;
	}

	/*
	 * takes the given File object and gets the names of all the files that have a
	 * byte size within a certain range
	 * 
	 * @param currentDirectory of object File, represents the directory the files
	 * are being taken from, sizeMin of type long which represents the lower bounds
	 * of the bytesize range, sizeMax of type long which represents the upper bounds
	 * of the bytesize range
	 * 
	 * @returns an arrayList representing the files within the directory that has a
	 * byte size within the range sizeMin<bytesize<sizeMax
	 */
	public static ArrayList<String> lookupBySize(File currentDirectory, long sizeMin, long sizeMax) {

		// local variable representing the path to the file to be returned
		ArrayList<String> names = new ArrayList<>();
		// local variable with the files
		File[] contents = currentDirectory.listFiles();

		if (currentDirectory.isDirectory() != true || currentDirectory.exists() != true || sizeMin > sizeMax
				|| sizeMin < 0 || sizeMax < 0) {
			return names;
		}
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].length() > sizeMin && contents[i].length() < sizeMax && contents[i].isFile()) {
				names.add(contents[i].getName());
			} else if (contents[i].isDirectory()) {
				names.addAll(lookupBySize(contents[i], sizeMin, sizeMax));
			}

		}
		return names;
	}

}
