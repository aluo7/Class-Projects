// --== CS400 Project One File Header ==--
// Name: Ryan Hanson
// CSL Username: rhanson
// Email: rrhanson@wisc.edu
// Lecture #: 001
// Notes to Grader: 

import java.util.LinkedList;

/**
 * Tester method for HashTableSortedSets and the KeyValuePair data type.
 * 
 * @author RyanR
 *
 */
public class AlgorithmEngineerTests {

	/**
	 * Searches for the pair and verifies it is at the correct index.
	 * 
	 * @param map  the map that should be searched
	 * @param pair - the pair that should be searched for
	 */

	// Tests that the reHash and checkLoadFactor methods work properly when the
	// loadFactor of .75 is met or exceeded.
	public static boolean test1() {
		try {
			// When the .75 load factor mark is reaached, the capacity should
			// double and the hashes should be in their new correct place.
			HashTableSortedSets<String, String> smallTable = new HashTableSortedSets<String, String>(8);
			smallTable.add("a", "def");
			smallTable.add("b", "def");
			smallTable.add("c", "def");
			smallTable.add("d", "def");
			smallTable.add("e", "def");
			if (smallTable.capacity != 8) {
				return false;
			}

			smallTable.add("f", "def");
			if (smallTable.capacity != 16) {
				return false;
			}

			KeyValuePair check1 = new KeyValuePair("e", "def");
			KeyValuePair check2 = new KeyValuePair("d", "def");

			if (!searchForPair(smallTable, check1)) {
				return false;
			}

			if (!searchForPair(smallTable, check2)) {
				return false;
			}

			return true;

			// if any unexpected error is thrown throughout testing the methods this
			// indicates that the method is not correctly implemented.
		} catch (Exception E) {
			return false;
		}
	}

	/**
	 * Tests that the add method is placing the valid keyvalue pairs into the
	 * correct place and not allowing null or duplicate keyvalues to be added. NOTE:
	 * duplicate keys with different values is allowed.
	 * 
	 * @return the result of the test
	 */
	public static boolean test2() {
		try {
			// null value test
			HashTableSortedSets<String, String> smallTable = new HashTableSortedSets<String, String>(8);
			smallTable.add("a", "def");

			if (!(((LinkedList) smallTable.array["a".hashCode() % smallTable.capacity]).size() == (1))) {
				return false;
			}

			smallTable.add("a", "def");
			if(smallTable.array[("a".hashCode())%smallTable.capacity].size()!=1) {
				return false;
			}
			
			smallTable.add("a", "different");
			if(smallTable.array[("a".hashCode())%smallTable.capacity].size()!=1) {
				return false;
			}
			
			smallTable.add("b", null);
			if (smallTable.containsKey("b")){
				return false;
			}
			
			return true;
			// if any unexpected error is thrown throughout testing the methods this
			// indicates that the method is not correctly implemented.
		} catch (Exception E) {
			return false;
		}
	}

	/**
	 * Tests that the containsKey works properly.
	 * 
	 * 
	 * @return the result of the test
	 */
	public static boolean test3() {
		try {
			HashTableSortedSets smallTable = new HashTableSortedSets(8);
			smallTable.add("a", "def");
			smallTable.add("b", "def");
			KeyValuePair check1 = new KeyValuePair("a", "def");
			KeyValuePair check2 = new KeyValuePair("b", "def");

			if (!smallTable.containsKey(check2.getKey())) {
				return false;
			}
			if (!smallTable.containsKey(check1.getKey())) {
				return false;
			}

			if (smallTable.containsKey(new KeyValuePair("c", "def"))) {
				return false;
			}

			return true;

			// if any unexpected error is thrown throughout testing the methods this
			// indicates that the method is not correctly implemented.
		} catch (Exception E) {
			return false;
		}
	}

	/**
	 * Tests that the clear method clears all of the LinkedLists but keeps capacity.
	 * 
	 * 
	 * @return the result of the test
	 */
	public static boolean test4() {
		try {
			HashTableSortedSets smallTable = new HashTableSortedSets(8);
			smallTable.add("b", "def");
			KeyValuePair check1 = new KeyValuePair("b", "def");

			// save the capacity then clear the existing table to verify results of clear.
			int originalCapacity = smallTable.capacity;
			smallTable.clear();

			if (smallTable.containsKey(check1)) {
				return false;
			}

			if (smallTable.capacity != originalCapacity) {
				return false;
			}
			if (smallTable.size != 0) {
				return false;
			}

			return true;
			// if any unexpected error is thrown throughout testing the methods this
			// indicates that the method is not correctly implemented.
		} catch (Exception E) {
			return false;
		}
	}

	/**
	 * Tests if the sort alogrithm designed to keep the linkedlist of values sorted
	 * works to sort the values inside of the value list of the key/value pair.
	 */
	public static boolean test5() {
		try {
			HashTableSortedSets<Integer, Integer> test1 = new HashTableSortedSets<Integer, Integer>();
			test1.add(100, 10011);
			test1.add(100, 12);
			test1.add(100, 12325);
			test1.add(100, 21323);
			test1.add(200, 1);

			LinkedList<Integer> stored = (LinkedList<Integer>) test1.get(100);

			for (int i = 0; i < stored.size() - 1; i++) {
				if (stored.get(i + 1).compareTo(stored.get(i)) <= 0) {
					return false;
				}
			}
			return true;

		} catch (Exception E) {
			return false;
		}
	}
	
	/**
	 * Helper method that verifies that the pair is in the correct index of the
	 * hashmap.
	 * 
	 * @param map  the hashmap to be searched
	 * @param pair the pair to be looked for
	 * @return result of test
	 */
	private static boolean searchForPair(HashTableSortedSets map, KeyValuePair pair) {
		if (((LinkedList) map.array[pair.getKey().hashCode() % map.capacity]).size() > 0) {
			LinkedList listAtIndex = (LinkedList) map.array[pair.getKey().hashCode() % map.capacity];
			for (int i = 0; i < listAtIndex.size(); i++) {
				if (((KeyValuePair) listAtIndex.get(i)).getKey().equals(pair.getKey())) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean runAlgorithmEngineerTests() {
		if (!test1()) {
			return false;
		}
		if (!test2()) {
			return false;
		}
		if (!test3()) {
			return false;
		}
		if (!test4()) {
			return false;
		}
		if (!test5()) {
			return false;
		}
		return true;
	}

	public static void main(String[] Args) {
		System.out.println(runAlgorithmEngineerTests());
	}

}
