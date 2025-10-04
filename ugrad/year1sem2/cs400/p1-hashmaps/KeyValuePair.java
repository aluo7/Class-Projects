import java.util.LinkedList;

// --== CS400 Project One File Header ==--
// Name: Ryan Hanson
// CSL Username: rhanson
// Email: rrhanson@wisc.edu
// Lecture #: 001
// Notes to Grader: 

/**
 * Data type that can hold a key object and a data object together as a pair. Has a get() method for both. 
 * 
 * @author RyanR
 *
 */
public class KeyValuePair<K, V>{
	public K key;
	public LinkedList<V> values;
	
	KeyValuePair(K key, V value){
		this.key = key;
		this.values = new LinkedList<V>();
		values.add(value);
	}
	
	public K getKey(){
		return key;
	}
	
	public LinkedList<V> getValue(){
		return (LinkedList<V>) values;
	}
}

