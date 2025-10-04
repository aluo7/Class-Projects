// --== CS400 Project One File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: <any optional extra notes to your grader>

import java.util.List;
import java.util.ArrayList;

/**
 * implementation of backend for a show searcher app
 * 
 * @author Alan Luo
 */
public class ShowSearcherBackend implements IShowSearcherBackend {

    private IHashTableSortedSets<String, IShow> sortedByTitle; // hashtable linking shows to a word in the title
    private IHashTableSortedSets<Integer, IShow> sortedByYear; // hashtable linking shows to the year it came out
    private int size; // number of shows being stored
    private ArrayList<String> providers; // ArrayList of providers (Netflix, Hulu, etc.)
    private boolean[] filters; // boolean list corresponding to providers, represents the provider filter

    /**
     * constructor for the ShowSearcherBackend class
     */
    public ShowSearcherBackend(boolean placeholder) {
        sortedByTitle = new HashTableSortedSetsPlaceholder<String, IShow>();
        sortedByYear = new HashTableSortedSetsPlaceholder<Integer, IShow>();

        providers = new ArrayList<String>();
        providers.add("Netflix");
        providers.add("Hulu");
        providers.add("Disney+");
        providers.add("Prime Video");

        filters = new boolean[4];
    }

    /**
     * constructor for the ShowSearcherBackend class
     */
    public ShowSearcherBackend() {
        sortedByTitle = new HashTableSortedSets<String, IShow>();
        sortedByYear = new HashTableSortedSets<Integer, IShow>();

        providers = new ArrayList<String>();
        providers.add("Netflix");
        providers.add("Hulu");
        providers.add("Disney+");
        providers.add("Prime Video");

        filters = new boolean[4];
    }

    /**
     * adds show to the database
     * 
     * @param show that represents the show to be added to the database
     */
    public void addShow(IShow show) {
        String title = show.getTitle();

        title = title.replaceAll(":|!||;|.|\\?|,|\\/", ""); // removes extraneous characters
        String[] words = title.split(" "); // splits the title into separate words

        for (String w : words) {
            sortedByTitle.add(w, show); // adds words into the title hashtable
        }

        sortedByYear.add(show.getYear(), show); // add show into the year hashtable

        size++; // increments the variable tracking the number of shows
    }

    /**
     * getter for number of shows stored in the databse
     * 
     * @return the number of shows stored in the database
     */
    public int getNumberOfShows() {
        return size;
    }

    /**
     * setter for the filter of the given provider, setting the toggle to true or
     * false
     * 
     * @param provider provider to have its filter set (Netflix, Hulu, etc.)
     * @param filter   boolean representing whether the filter is checked or not
     */
    public void setProviderFilter(String provider, boolean filter) {
        filters[providers.indexOf(provider)] = filter; // sets the filter boolean corresponding to the provider to the
                                                       // intended boolean value
    }

    /**
     * getter for the filter of the given provider, returning the current status of
     * the toggle
     * 
     * @param provider provider to have its filter returned
     * 
     * @return the filter status of the inputted provider
     */
    public boolean getProviderFilter(String provider) {
        return filters[providers.indexOf(provider)]; // gets the filter boolean corresponding to the provider and
                                                     // returns it
    }

    /**
     * Toggles the filter on or off for the given provider
     * 
     * @param provider provider to have its filter toggled
     */
    public void toggleProviderFilter(String provider) {
        filters[providers.indexOf(provider)] = !filters[providers.indexOf(provider)]; // sets the filter boolean
                                                                                      // corresponding to the provider
                                                                                      // to the opposite of what it was
                                                                                      // before
    }

    /**
     * searches for movies that contain the given word
     * 
     * @param word the word to be searched for
     * 
     * @return a list of shows that contain the inputted word
     */
    public List<IShow> searchByTitleWord(String word) {
        List<IShow> shows = new ArrayList<IShow>(); // list of shows to be filtered through and returned
        shows.addAll(sortedByTitle.get(word)); // adds all shows that contain the inputted word

        filter(shows); // filters through the shows and removes invalid ones (check helper method
                       // below)

        return shows;
    }

    /**
     * searches for movies that were released in the given year
     * 
     * @param year the year to be searched for
     * 
     * @return a list of shows that were released in the inputted year
     */
    public List<IShow> searchByYear(int year) {
        List<IShow> shows = new ArrayList<IShow>(); // list of shows to be filtered through and returned
        shows.addAll(sortedByYear.get(year)); // adds all shows that were released in the inputted year

        filter(shows); // filters through the shows and removes the invalid ones (check helper method
                       // below)

        return shows;
    }

    /**
     * helper method for the search classes, filters through the list that is given
     * and removes any shows that are not to be searched for
     * 
     * @param shows the list of shows to be searched through
     */
    private void filter(List<IShow> shows) {
        List<IShow> toRemove = new ArrayList<IShow>(); // list of shows to be removed (added to later)

        // checks the filter
        if (getProviderFilter(providers.get(0)) || getProviderFilter(providers.get(1))
                || getProviderFilter(providers.get(2))
                || getProviderFilter(providers.get(3))) {
            for (int i = 0; i < shows.size(); i++) {
                for (String p : providers) {
                    // checks if the show is available
                    if (!shows.get(i).isAvailableOn(p) || !filters[providers.indexOf(p)]) {
                        toRemove.add(shows.get(i)); // adds to the list of shows to be removed if they aren't available
                    }
                }
            }
        }

        // if there are values that need to be removed, remove them
        if (toRemove.size() > 0) {
            for (IShow s : toRemove) {
                shows.remove(s);
            }
        }
    }
}
