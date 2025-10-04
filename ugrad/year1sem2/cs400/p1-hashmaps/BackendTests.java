// --== CS400 Project One File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: <any optional extra notes to your grader>

/**
 * tester for the ShowSearcherBackend class
 * 
 * @author Alan Luo
 */
public class BackendTests {

    /**
     * main method runs all tests
     * 
     * @param args
     */
    public static void main(String[] args) {
	if(runAllTests()) {
		System.out.println("all tests passed");
	}
        runAllTests();
    }

    /**
     * tester for the addShow method, testing to see if the values are added to the
     * hashtables properly and if the size is incrememnted properly
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test1() {
        boolean testSingle = false; // boolean testing whether it adds one show properly or not
        boolean testMultiple = false; // boolean testing whether it adds multiple shows properly or not

        ShowSearcherBackend be = new ShowSearcherBackend(true); // instantiates ShowSearcherBackend object

        // makes use of temporary implementation for Show class
        ShowPlaceholder s1 = new ShowPlaceholder("Enchanted", 2007, 93); // instantiates shows

        be.addShow(s1); // adds the show

        testSingle = be.getNumberOfShows() == 1;

        ShowPlaceholder s2 = new ShowPlaceholder("Cars", 2011, 39);
        ShowPlaceholder s3 = new ShowPlaceholder("Black Sheep", 1996, 72);

        be.addShow(s2);
        be.addShow(s3);

        testMultiple = be.getNumberOfShows() == 3;

        return testSingle && testMultiple;
    }

    /**
     * tester for the setProviderFilter method, testing to see if the filters are
     * set properly
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test2() {
        boolean testSet = false; // boolean testing whether you can set the toggles properly or not

        ShowSearcherBackend be = new ShowSearcherBackend(true); // instantiates ShowSearcherBackend object

        // sets filter for Netflix to false, Prime Video to true, etc.
        be.setProviderFilter("Netflix", false);
        be.setProviderFilter("Prime Video", true);
        be.setProviderFilter("Hulu", true);
        be.setProviderFilter("Disney+", false);

        // retrieves the values of the filters, checks to see if they are as expected
        testSet = !be.getProviderFilter("Netflix") && be.getProviderFilter("Prime Video")
                && be.getProviderFilter("Hulu") && !be.getProviderFilter("Disney+");

        return testSet;
    }

    /**
     * tester for the toggleProviderFilter method, testing to see if the filters are
     * toggled properly
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test3() {
        boolean testToggle = false; // boolean testing to see whether the toggle switches on/off properly or not

        ShowSearcherBackend be = new ShowSearcherBackend(true); // instantiates ShowSearcherBackend object

        // sets filter for Netflix to false, Prime Video to true, etc.
        be.setProviderFilter("Netflix", false);
        be.setProviderFilter("Prime Video", true);
        be.setProviderFilter("Hulu", true);
        be.setProviderFilter("Disney+", false);

        // toggles the Netflix filter (expects true) and the Hulu filter (expects false)
        be.toggleProviderFilter("Netflix");
        be.toggleProviderFilter("Hulu");

        // retrieves the values of the filters, checks to see if they are as expected
        testToggle = be.getProviderFilter("Netflix") && be.getProviderFilter("Prime Video")
                && !be.getProviderFilter("Hulu") && !be.getProviderFilter("Disney+");

        return testToggle;
    }

    /**
     * tester for the searchByTitleWord method, tests to see if they search for and
     * find the proper shows and return the correct list with the found shows in
     * them
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test4() {
        boolean testTitle = false; // boolean testing whether the correct shows can be found given a word in the
                                   // title

        ShowSearcherBackend be = new ShowSearcherBackend(true); // instantiates ShowSearcherBackend object

        // makes use of temporary implementation of HashTableSortedSets class
        testTitle = be.searchByTitleWord("The").size() == 3; // checks to see if the results of searching for the word
                                                             // "The" returns a list of the correct size

        return testTitle;
    }

    /**
     * tester for the searchByYear method, tests to see if they search for and
     * find the proper shows and return the correct list with the found shows in them
     *
     * @return true if all tests are passed, false otherwise
     */
    public static boolean test5() {
        boolean testYear = false; // boolean testing whether the correct shows can be found given the year the
                                  // show was released

        ShowSearcherBackend be = new ShowSearcherBackend(true); // instantiates ShowSearcherBackend object

        testYear = be.searchByYear(2004).size() == 2; // checks to see if the results of searching for the year 2004
                                                      // returns a list of the correct size

        return testYear;
    }

    /**
     * method that runs all the tests in the tester
     * 
     * @return true if all tests are passed, false otherwise
     */
    public static boolean runAllTests() {
        return test1() && test2() && test3() && test4() && test5();
    }
}
