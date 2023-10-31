
// --== CS400 Project One File Header ==--
// Name: Ryan Hanson
// CSL Username: rhanson
// Email: rrhanson@wisc.edu
// Lecture #: 001
// Notes to Grader: 

import java.util.NoSuchElementException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A HashTableSortedSets implementation that uses hashing to gain a fast search
 * time
 * for Key Value pairs. User can add, remove, and do all of the methods
 * associated with the MapADT.
 * 
 * @author RyanR
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class HashTableSortedSets<KeyType, ValueType extends Comparable<ValueType>> extends HashtableMap
		implements IHashTableSortedSets {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected LinkedList[] array;
	protected int capacity;
	public int size;

	/**
	 * Creates an instance of the IHashTableSets. Collisions are handled using the
	 * chain method within an ArrayList data type.
	 * 
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")

	public HashTableSortedSets(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.array = new LinkedList[capacity];
		initializeLinkedLists(array);
	}

	/**
	 * Creates a default instance with default capacity of 20.
	 */
	public HashTableSortedSets() {
		this.capacity = 20;
		this.size = 0;
		this.array = new LinkedList[capacity];
		initializeLinkedLists(array);
	}

	/**
	 * initializes an empty linkedList at every spot in the array.
	 * 
	 * @param the array that should be initialize.
	 */
	private void initializeLinkedLists(LinkedList[] array2) {
		for (int i = 0; i < capacity; i++) {
			array2[i] = new LinkedList<Object>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Adds the key-value pair into the hash table. This ALLOWS for
	 * repeat keys if
	 * there's different values.
	 * The value will be stored in the same list as the other
	 * value in a given that
	 * there is a repeated key
	 * with different values.
	 * 
	 * @return True if it was added. Or false if the key value pair
	 *         is not added due to invalid inputs.
	 */
	public void add(Object key, Comparable value) {
		// check for duplicate key value or null.
		if (key == null || value == null) {
			return;
		}
		int index = key.hashCode() % capacity;
		LinkedList existingList = (LinkedList) array[index];
		for (int i = 0; i < existingList.size(); i++) {
			if (((KeyValuePair) existingList.get(i))
					.getKey().equals(key)) {
				// search for duplicate value in the same
				// key. do not add if found.
				for (int j = 0; j < ((KeyValuePair) existingList.get(i)).getValue().size(); j++) {
					if (value.equals(((KeyValuePair) existingList.get(i)).getValue().get(j))) {
						return;
					}
				}
				this.size++;
				((KeyValuePair) existingList.get(i)).getValue().add(value);
				sortList((LinkedList<ValueType>) ((KeyValuePair) existingList.get(i)).getValue());
				checkLoadFactor();
				return;
			}
		}

		// add successfully without duplicates.
		this.size++;
		KeyValuePair newPair = new KeyValuePair(key, value);
		((LinkedList) array[index]).add(newPair);
		checkLoadFactor();

		return;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Not to be used for this class. It is largely obsolete for IHash and included
	 * because its the Add() method for HashTableMap project. Use Add() to do the
	 * desired implementation for a generic class.
	 * 
	 * @return true if the put method
	 *         added the given parameter values. False if the given parameter values
	 *         are
	 *         null or duplicates. Calculates the hashcode and inputs it at a
	 *         position
	 *         modulus the capacity. This automatically rehashes if the load factor
	 *         is met
	 *         or exceeded.
	 */
	public boolean put(KeyType key, ValueType value) {
		// check for duplicate key value or null.
		if (key == null || value == null) {
			return false;
		}
		int index = key.hashCode() % capacity;
		LinkedList existingList = (LinkedList) array[index];
		for (int i = 0; i < existingList.size(); i++) {
			if (((KeyValuePair) existingList.get(i)).getKey()
					.equals(key)) {
				return false;
			}
		}

		// add successfully.
		this.size++;
		KeyValuePair newPair = new KeyValuePair(key, value);
		((LinkedList) array[index]).add(newPair);
		checkLoadFactor();

		return true;
	}

	/**
	 * Returns a reference to the list of values with the given key,
	 * unless it doesn't exist
	 * and then it throws NoSuchElementException.
	 */
	@Override
	public LinkedList<ValueType> get(Object key) throws NoSuchElementException {
		int hashCode = key.hashCode() % capacity;
		LinkedList list = (LinkedList) array[hashCode];
		for (int i = 0; i < list.size(); i++) {
			if (((KeyValuePair) list.get(i)).getKey().equals(key)) {
				return ((KeyValuePair) list.get(i)).getValue();
			}
		}
		throw new NoSuchElementException("Key not found.");
	}

	@Override
	/**
	 * Returns the number of inputs of the hashtablemap.
	 */
	public int size() {
		return size;
	}

	@Override
	/**
	 * Returns true if key is found, returns false if key is not already found.
	 */
	public boolean containsKey(Object key) {
		if (((LinkedList) array[key.hashCode() % capacity]).size() > 0) {
			LinkedList listAtIndex = (LinkedList) array[key.hashCode() % capacity];
			for (int i = 0; i < listAtIndex.size(); i++) {
				if (((KeyValuePair) listAtIndex.get(i)).getKey().equals(key)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	/**
	 * Obsolete for this data type because it removes all of the values associated
	 * with that key. Use removeValue instead.
	 * 
	 * If found, the key is removed and a reference to the value associated with it
	 * is returned. If the key cannot be found, null is returned.
	 */
	public Object remove(Object key) {
		if (containsKey(key)) {
			LinkedList listAtIndex = (LinkedList) array[key.hashCode() % capacity];
			for (int i = 0; i < listAtIndex.size(); i++) {
				if (((KeyValuePair) listAtIndex.get(i)).getKey().equals(key)) {
					KeyValuePair savedKeyValue = (KeyValuePair) listAtIndex.get(i);
					listAtIndex.remove(i);
					this.size--;
					return savedKeyValue;
				}
			}
		}
		return null;
	}

	@Override
	/**
	 * Clears all inputs from IHashTableSortedSets while keeping capacity the same.
	 */
	public void clear() {
		this.size = 0;
		this.array = new LinkedList[capacity];
		initializeLinkedLists(array);
	}

	/**
	 * Checks if the load factor exceeds or is equal to .75, if so it calls the
	 * rehash method. Otherwise, it does nothing.
	 */
	private void checkLoadFactor() {
		if (((double) size / capacity) >= .75) {
			reHash();
		}
	}

	/**
	 * Doubles the capacity of the hashtablemap and rehashes all of the existing
	 * inputs.
	 */
	private void reHash() {

		HashTableSortedSets doubleSizeArray = new HashTableSortedSets(capacity * 2);

		for (int i = 0; i < capacity; i++) {
			LinkedList list = (LinkedList) array[i];
			for (int j = 0; j < list.size(); j++) {
				KeyValuePair keyValuePair = ((KeyValuePair) list.get(j));
				LinkedList<ValueType> values = keyValuePair.getValue();
				for (int k = 0; k < values.size(); k++)
					doubleSizeArray.add(keyValuePair.getKey(), values.get(k));
			}
		}

		this.capacity = doubleSizeArray.capacity;
		this.array = doubleSizeArray.array;
	}

	public void sortList(LinkedList<ValueType> values) {
		Collections.sort(values, new Comparator<ValueType>() {

			@SuppressWarnings("unchecked")
			@Override
			public int compare(ValueType o1, ValueType o2) {
				if (o1 instanceof String) {
					return ((String) o1).compareTo((String) o2);
				}

				if (o1 instanceof Integer) {
					return ((Integer) o1).compareTo((Integer) o2);
				}
				if (o1 instanceof Character) {
					return ((Character) o1).compareTo((Character) o2);
				} else
					return ((Comparable) o1).compareTo(o2);
			}

		});
	}

	public int compareTo(ValueType o1, ValueType o2) {
		if (o1 instanceof String) {
			return ((String) o1).compareTo((String) o2);
		}

		if (o1 instanceof Integer) {
			return ((Integer) o1).compareTo((Integer) o2);
		}
		if (o1 instanceof Character) {
			return ((Character) o1).compareTo((Character) o2);
		} else
			return ((Comparable) o1).compareTo(o2);
	}
}
