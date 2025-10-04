// --== CS400 Project One File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: <any optional extra notes to your grader>

/**
 * placeholder class for Show
 * 
 * @author Alan Luo
 */
public class ShowPlaceholder implements IShow {
    String title; // represents the title of the show
    int year; // represents the year the show was released
    int rating; // represents the RottenTomatoes rating of the show (out of 100)

    /**
     * constructor for the Show class
     * 
     * @param title  represents the title of the show
     * @param year   represents the year the show was released
     * @param rating represents the RottenTomatoes rating of the show (out of 100)
     */
    public ShowPlaceholder(String title, int year, int rating) {
        this.title = title;
        this.year = year;
        this.rating = rating;
    }

    /**
     * getter method for the title
     * 
     * @return the title of the show
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * getter method for the year
     * 
     * @return the year the show was released
     */
    @Override
    public int getYear() {
        return year;
    }

    /**
     * getter method for the rating
     * 
     * @return the RottenTomatoes rating of the show (out of 100)
     */
    @Override
    public int getRating() {
        return rating;
    }

    @Override
    /**
     * checks if the show is available on the given streaming service (defaulted to
     * true for the sake of testing)
     * 
     * @return whether the show is available or not
     */
    public boolean isAvailableOn(String provider) {
        return true;
    }
    
    // not used
    @Override
    public int compareTo(IShow o) {
        if (o.getRating() < this.rating) {
            return -1;
        }

        if (o.getRating() > this.rating) {
            return 1;
        }

        return 0;
    }

}
