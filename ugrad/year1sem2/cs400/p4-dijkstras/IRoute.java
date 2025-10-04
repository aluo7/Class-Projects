// --== CS400 P3 File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: 001

/**
 * Instances of classes that implement this interface represent a single uber
 * pickup object that can be stored, sorted, and searched for based on these
 * accessors below
 */
public interface IRoute extends Comparable<IRoute> {

    // no-arg constructor
    // constructor args (String time, String name, String driverLocation, String
    // passengerLocation)
    public String getTime(); // retrieve the time the pick up request was submitted

    public String getName(); // retrieve the name of the person that submitted the request

    public String getPassengerLocation(); // retrieve the location of the passenger

    public String getDriverLocation(); // retrieve the location of the driver
    
    // compareTo() methods supports sorting routes in order by priority (time
    // submitted)
    // oldest (high priority) to newest (low priority) requests

}
