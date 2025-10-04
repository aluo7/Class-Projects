import java.util.List;

/**
 * An instance of a class that implements the following interface can be used
 * to search and retrieve the database of shows within the ShowSearcher app.
 */
public interface IShowSearcherBackend {
    
    public void addShow(IShow show); // adds show to backend database
    public int getNumberOfShows(); // retrieve number of shows in database

    // set the desired provider filters before calling either search method:
    // (all providers are included in search results by default)
    public void setProviderFilter(String provider, boolean filter);
    public boolean getProviderFilter(String provider);
    public void toggleProviderFilter(String provider);

    // these methods can be used to look-up shows by title word or year
    // the results are filtered according to the provider filters set above
    public List<IShow> searchByTitleWord(String word);
    public List<IShow> searchByYear(int year);
    
}
