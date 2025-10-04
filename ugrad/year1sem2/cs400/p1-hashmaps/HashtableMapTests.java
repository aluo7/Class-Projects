// --== CS400 Project One File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: 001
// Notes to Grader: <any optional extra notes to your grader>

import java.util.NoSuchElementException;

public class HashtableMapTests {

    public static void main(String[] args) {
        runAllTests();
    }

    /**
     * tester for the put method, tests to see if values are added to the hashtable
     * properly and the size is incremented properly
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test1() {
        boolean oneVal = false; // tests if the put method stores one key-value pair properly
        boolean multipleVal = false; // tests if the put method stores multiple key-value pairs properly, also checks
                                     // to see if it was rehashed properly
        boolean nullOrContained = false; // tests if the method returns false when the key is null and when the key
                                         // already exists in the hashtable

        HashtableMap hashtable = new HashtableMap(4);

        hashtable.put(1, "fish");

        oneVal = (hashtable.size() == 1 && hashtable.get(1) == "fish");

        hashtable.put(2, "car");
        hashtable.put(3, "tree");

        multipleVal = (hashtable.size() == 6 && hashtable.get(2) == "car" && hashtable.get(3) == "tree");

        nullOrContained = !hashtable.put(null, "mask") && !hashtable.put(2, "face");

        return oneVal && multipleVal && nullOrContained;
    }

    /**
     * tester for the get method, tests to see if the value retrieved at the
     * inputted key is retrieved properly
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test2() {
        boolean oneVal = false; // tests if the put method stores one key-value pair properly
        boolean multipleVal = false; // tests if the put method stores multiple key-value pairs properly
        boolean noVal = false; // tests if an exception is thrown properly when the key being searched for
                               // doesn't exist

        HashtableMap hashtable = new HashtableMap();

        try {
            hashtable.put(1, "fish");

            oneVal = (hashtable.get(1) == "fish");

            hashtable.put(2, "car");
            hashtable.put(3, "tree");
            // hashtable.put(2, "drink");

            multipleVal = (hashtable.get(2) == "car" && hashtable.get(3) == "tree");

            hashtable.get(5);

        } catch (NoSuchElementException e) {
            noVal = true;
        }

        return oneVal && multipleVal && noVal;
    }

    /**
     * tester for the containsKey method, tests to see if the method checks for the
     * inputted key properly
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test3() {
        boolean oneVal = false; // tests to see if the method checks for a singular key properly
        boolean multipleVal = false; // makes sure that the test can check for keys by a nonlinear increment
        boolean noVal = false; // tests to see if the method returns false if the key isn't present

        HashtableMap hashtable = new HashtableMap();

        hashtable.put(1, "fish");

        oneVal = hashtable.containsKey(1);

        hashtable.put(3, "tree");
        hashtable.put(7, "sock");

        multipleVal = hashtable.containsKey(3) && hashtable.containsKey(7);

        noVal = !hashtable.containsKey(2) && !hashtable.containsKey(4);

        return oneVal && multipleVal && noVal;
    }

    /**
     * tester for remove method, tests to see if the method removes values from the
     * hashtable properly and returns the value of the removed key-value pair
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test4() {
        boolean oneVal = false; // tests to see if the method removes a singular key properly
        boolean multipleVal = false; // makes sure that it removes key-value pairs at random keys
        boolean noVal = false; // tests to see if it returns null if the key doesn't exist

        HashtableMap hashtable = new HashtableMap();

        hashtable.put(1, "fish");
        hashtable.put(3, "tree");
        hashtable.put(7, "sock");

        oneVal = hashtable.remove(1) == "fish";

        multipleVal = hashtable.remove(3) == "tree" && hashtable.remove(7) == "sock";

        noVal = hashtable.remove(2) == null && hashtable.remove(4) == null;

        return oneVal && multipleVal && noVal;
    }

    /**
     * tester for the clear method, tests to see if the method properly clears the
     * hashtable of all key-value pairs
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test5() {
        boolean clearsAll = false; // tests if all the key-value pairs are properly removed from the hashtable

        HashtableMap hashtable = new HashtableMap();

        hashtable.put(1, "fish");
        hashtable.put(3, "tree");
        hashtable.put(7, "sock");

        hashtable.clear();

        clearsAll = hashtable.size() == 0 && hashtable.remove(1) == null && hashtable.remove(3) == null
                && hashtable.remove(7) == null;

        return clearsAll;
    }

    public static boolean runAllTests() {
        if (test1() && test2() && test3() && test4() && test5()) {
            return true;
        }
        return false;
    }
}
