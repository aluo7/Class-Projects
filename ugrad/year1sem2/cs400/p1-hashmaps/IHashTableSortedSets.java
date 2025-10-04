import java.util.List;

/**
 * This class is implemented by a hashtable that stores a list of values 
 * associated with each unique key.  These lists of values are sorted
 * according to the compareTo() defined within the ValueType.
 */
public interface IHashTableSortedSets <KeyType,ValueType extends Comparable<ValueType>> extends MapADT<KeyType,List<ValueType>> {
    
    /**
     * This add method is different from the put() method in that it appends a
     * single value to the end of the list associated with a given key.  If a
     * key does not yet have a list of values associated with it, then a new 
     * one will be created when this method is called.
     * @param key used to later lookup the list containing this value
     * @param value associated with the previous key
     */
    public void add(KeyType key, ValueType value);

}