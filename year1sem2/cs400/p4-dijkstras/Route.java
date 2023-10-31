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
public class Route implements IRoute {

    private String name; // name of passenger
    private String time; // time that the pickup request was submitted
    private String passengerLocation; // location of the passenger
    private String driverLocation; // location of the driver

    /**
     * no-parameter constructor that creates an empty route
     */
    public Route() {
        name = "";
        time = "";
        passengerLocation = "";
        driverLocation = "";
    }

    /**
     * constructor that takes in parameters to instantiate the Route object
     * 
     * @param name
     * @param time
     * @param passengerLocation
     * @param driverLocation
     */
    public Route(String name, String time, String passengerLocation, String driverLocation) {
        this.name = name;
        this.time = time;
        this.passengerLocation = passengerLocation;
        this.driverLocation = driverLocation;
    }

    /**
     * compareTo() methods supports sorting routes in order by priority (time
     * submitted)
     * 
     * oldest (high priority) to newest (low priority) requests
     * 
     * @param o represents other route to be compared to 
     * @return 0 if same route, 1 if submitted at same time, difference in time otherwise
     */
    @Override
    public int compareTo(IRoute o) {
        String[] t1 = this.time.split(":"); // splits the times of the Routes
        String[] t2 = ((Route) o).time.split(":");

        for (int i = 0; i < t1.length; i++) {
            if (Integer.parseInt(t2[i]) - Integer.parseInt(t1[i]) != 0) {
                return Integer.parseInt(t2[i]) - Integer.parseInt(t1[i]);
            }
        }

        // If they are the same return 0;
        if (o.getName() == this.getName()) {
            if (o.getTime() == this.getTime()) {
                if (o.getPassengerLocation() == this.getPassengerLocation()) {
                    if (o.getDriverLocation() == this.getDriverLocation()) {
                        return 0;
                    }
                }
            }
        }

        // If both are filed at the same time. They have equal priority and
        // one is picked over other.
        return 1;
    }

    /**
     * getter method for the name instance variable
     * 
     * @return name of passenger
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter method for the time instance variable
     * 
     * @return time pickup request was submitted
     */
    public String getTime() {
        return this.time;
    }

    /**
     * getter method for the passengerLocation instance variable
     * 
     * @return location of passenger
     */
    public String getPassengerLocation() {
        return this.passengerLocation;
    }

    /**
     * getter method for the driverLocation instance variable
     * 
     * @return location of driver
     */
    public String getDriverLocation() {
        return this.driverLocation;
    }

}
