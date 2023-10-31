// --== CS400 Project One File Header ==--
// Name: Ryan Hanson
// CSL Username: rhanson
// Email: rrhanson@wisc.edu
// Lecture #: 001
// Notes to Grader: 

import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * A HashtableMap implementation that uses hashing to gain a fast search time for Key Value pairs. User can add, remove, and do all of the methods associated with the MapADT. 
 * 
 * @author RyanR
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class HashtableMap<T> implements MapADT {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected LinkedList[] array;
	protected int capacity;
	public int size;

	/**
	 * Creates an instance of the hashtablemap. Collisions are handled using the
	 * chain method within an ArrayList data type.
	 * 
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")

	public HashtableMap(int capacity) {
		this.capacity = capacity;
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

	/**
	 * Creates an instance of the hashtablemap with default capacity of 20.
	 * Collisions are handled using the chain method within an ArrayList data type.
	 */
	@SuppressWarnings("unchecked")
	public HashtableMap() {
		this.capacity = 20;
		this.size = 0;
		this.array = new LinkedList[capacity];
		initializeLinkedLists(array);
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @return true if the put method added the given parameter values. False if the
	 *         given parameter values are null or duplicates. Calculates the
	 *         hashcode and inputs it at a position modulus the capacity. This
	 *         automatically rehashes if the load factor is met or exceeded.
	 */
	public boolean put(Object key, Object value) {
		//check for duplicate key value or null.
		if(key == null || value == null) {
			return false;
		}
		int index = key.hashCode()%capacity;
		LinkedList existingList = (LinkedList) array[index]; 
		for(int i = 0; i<existingList.size();i++) {
			if(((KeyValuePair) existingList.get(i)).getKey().equals(key)) {
				return false;
		}
		}
		
		//add successfully.
		this.size++;
		KeyValuePair newPair = new KeyValuePair(key, value);
		((LinkedList) array[index]).add(newPair);
		checkLoadFactor();
		
		return true;
	}

	/**
	 * Returns a reference to the object with the given key, unless it doesn't exist
	 * and then it throws NoSuchElementException.
	 */
	@Override
	public Object get(Object key) throws NoSuchElementException {
		int hashCode = key.hashCode() % capacity;
		LinkedList list = (LinkedList) array[hashCode];
		for (int i = 0; i < list.size(); i++) {
			if (((KeyValuePair) list.get(i)).getKey().equals(key)) {
				return ((KeyValuePair)list.get(i)).getValue();
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
	 * Clears all inputs from hashtablemap while keeping capacity the same.
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
	 * Doubles the capacity of the hashtablemap and rehashes all of the existing inputs. 
	 */
	private void reHash() {
		
		HashtableMap doubleSizeArray = new HashtableMap(capacity*2);
		
		for(int i = 0; i<capacity; i++) {
			LinkedList list = (LinkedList) array[i];
			for(int j = 0; j<list.size(); j++) {
				KeyValuePair keyValuePair = ((KeyValuePair) list.get(j));
				doubleSizeArray.put(keyValuePair.getKey(),keyValuePair.getValue());
						}
		}
	
	this.capacity=doubleSizeArray.capacity;
	this.array=doubleSizeArray.array;
}

}

