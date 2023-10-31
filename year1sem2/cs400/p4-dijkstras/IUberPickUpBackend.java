import java.util.List;

/**
 * An instance of a class that implements the following interface can be 
 * used to make a UberPickUpBackend object that allows user to find location
 * of passengers.
 * 
 */
public interface IUberPickUpBackend {

    public String getDriverLocation(); // Returns the location of the driver
    // Output: The location of the driver

    public String getPassengerLocation(String passengerName); // Returns the location of the passenger 
    // Output : The location of the passenger

    public String getPickUpTime(String passengerName); // Returns the pickup time scheduled by the passenger
    // Input: Name of the passenger
    // Output: Time that the passenger requested to be picked up

    public void removePassenger (String passengerName); // Removes the passenger once they are dropped off
    // Input: Name of the passenger

    public List<IIntersection> getShortestPath(String passengerName); // Get the shortest path from the driver to the passenger
    // Input: Name of the passenger
    // Output: The object containing the highest priority route
    
}
