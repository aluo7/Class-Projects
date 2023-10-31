// --== CS400 Project One File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: <any optional extra notes to your grader>

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * placeholder class for HashTableSortedSets
 * 
 * @author Alan Luo
 */
public class HashTableSortedSetsPlaceholder<KeyType, ValueType> implements IHashTableSortedSets, MapADT {
    
    // not used
    @Override
    public boolean put(Object key, Object value) {
        return false;
    }

    /**
     * hardcoded implementation to return specific shows when called with a specific
     * key (used to make sure that the searchByTitleWord and searchByYear methods
     * worked properly)
     */
    @Override
    public Object get(Object key) throws NoSuchElementException {
        String keyObj = key + ""; // changes the key to a String to check its value

        // checks if the word searched for is "The"
        if (keyObj.equals("The")) {
            // adds shows with the word "The" in its title to a list
            ShowPlaceholder s1 = new ShowPlaceholder("The Incredibles", 2004, 97);
            ShowPlaceholder s2 = new ShowPlaceholder("The American", 2010, 66);
            ShowPlaceholder s3 = new ShowPlaceholder("The Astronaut Farmer", 2007, 58);

            IShow[] toReturn = new IShow[] { s1, s2, s3 };

            return Arrays.asList(toReturn); // returns list of shows that fit the criteria
        }

        // checks if the year searched for is 2004
        if (keyObj.equals("2004")) {
            // adds shows that came out in 2004 to a list
            ShowPlaceholder s1 = new ShowPlaceholder("The Incredibles", 2004, 97);
            ShowPlaceholder s2 = new ShowPlaceholder("White Chicks", 2004, 15);

            IShow[] toReturn = new IShow[] { s1, s2 };

            return Arrays.asList(toReturn); // returns list of shows that fit the criteria
        }

        return null;
    }
    
    // not used
    @Override
    public int size() {
        return 0;
    }
    
    // not used
    @Override
    public boolean containsKey(Object key) {
        return false;
    }
    
    // not used
    @Override
    public Object remove(Object key) {
        return null;
    }
    
    // not used
    @Override
    public void clear() {

    }
    
    // not used
    @Override
    public void add(Object key, Comparable value) {

    }

}
