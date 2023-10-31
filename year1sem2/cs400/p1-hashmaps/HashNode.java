// --== CS400 Project One File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: 001
// Notes to Grader: <any optional extra notes to your grader>

public class HashNode<KeyType, ValueType> {
    protected KeyType key; // generic for key
    protected ValueType value; // generic for value
    
    /**
     * constructor for HashNode class, creates a new key-value pair
     * 
     * @param key represents the key
     * @param value represents the value value
     */
    public HashNode(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

    /**
     * getter for key
     * 
     * @return key of type KeyType
     */
    public KeyType getKey() {
        return key;
    }

    /**
     * getter for value corresponding to key
     * 
     * @return value corresponding to key
     */
    public ValueType getValue() {
        return value;
    }
}
