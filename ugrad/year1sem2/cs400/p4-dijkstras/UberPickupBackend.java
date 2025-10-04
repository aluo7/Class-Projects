// --== CS400 Project 3 File Header ==--
// Name: Hong Kai Ng
// Email: hng7@wisc.edu
// Team: AK: blue
// TA: Yelun Bao
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * UberPickupBackend class that will be used by the frontend deveoper to run the program
 */
public class UberPickupBackend implements IUberPickUpBackend{
    protected IIntersection driverLocation;
    protected List<IRoute> passengerList;
    protected UberMapGraphBD_Placeholder map; // needs to be replaced with actual class during integration

    // Intersection objects that are represented as nodes in the graph
    private IIntersection regentPark;
    private IIntersection charterPark;
    private IIntersection universityPark;
    private IIntersection randallRegent;
    private IIntersection randallOrchard;
    private IIntersection regentCampus;
    private IIntersection campusBabcock;
    private IIntersection regentState;
    private IIntersection babcockState;
    private IIntersection orchardState;
    private IIntersection universityState;

    /**
     * Default constructor that creates an UberPickupBackend object. It creates the map, and loads in
     * a list of passengers in a JSON file created by the Algorithm Engineer. 
     */
    public UberPickupBackend(){
        createMap(); // Initializes the intersections and specifies the driver location
        try{
            passengerList = this.getPassengerList(); // Load the data in JSON file into an arraylist

        }catch (Exception e){
            System.out.println("Path to JSON file not found");
        }
    }

	private ArrayList<Path> routes = new ArrayList<Path>();

	@Override
	protected Path dijkstrasShortestPath(String start, String end) {	
	return routes.get(1);
		}

public ArrayList<Path> initialize(){
	//In this Scenario driver is at 1st Ave and Miflin or first. 
	Vertex manuela = new Vertex("Manuela St. and Miflin St.");
	Vertex chestnut = new Vertex("Chestnut Ave. and Miflin St.");
	Vertex miflin = new Vertex("1st Ave. and Miflin St.");
	Vertex first = new Vertex("1st Ave. and Manuela St.");
	vertices.put("Manuela St. and Miflin St.", manuela);
	vertices.put("Chestnut Ave. and Miflin St.", chestnut);
	vertices.put("1st Ave. and Miflin St.", miflin);
	vertices.put("1st Ave. and Manuela St.", first);
Edge street1 = new Edge(chestnut, 1);
Edge street2 = new Edge(miflin, 20);
Edge street3 = new Edge(manuela, 2);
Edge street4 = new Edge(manuela, 2);
Edge street5 = new Edge(miflin, 1);
Edge street6 = new Edge(chestnut, 1);

	Path route1 = new Path(first);
	Path route2 = new Path(first);
	Path route3 = new Path(first);
	Path route4 = new Path(first);
	Path route5 = new Path(first);
	Path route6 = new Path(first);	
	Path route7 = new Path(first);
	Path route8 = new Path(first);
	
	route1 = new Path(route1, street1);
	route2 = new Path(route2, street2);
	route3 = new Path(route1, street4);
	route4 = new Path(route2, street3);
	route5 = new Path(route1, street5);
	route6 = new Path(route2, street6);
	route7 = new Path(route6, street4);
	route8 = new Path(route5, street3);
	//sorted list
	routes.add(route1);
	routes.add(route5);
	routes.add(route3);
	routes.add(route8);
	routes.add(route2);
	routes.add(route6);
	routes.add(route4);
	routes.add(route7);
/*	
	for(int i = 0; i<routes.size(); i++){
		List<String> list= routes.get(i).dataSequence;
		for(int j = 0; j<list.size(); j++){
			System.out.println("Stop " + j + list.get(j));
			System.out.println("                                   ");
		}
		System.out.println("\n\n\n\n");
	}
*/

	return routes;
}

	public ArrayList<Path> getPossiblePaths(){
		return initialize();
	}
	
	public List<IRoute> getSortedRoutes(IRoute driverLocation){
		routes = initialize();
		return (List<IRoute>) routes;
	}
    /**
     * Method to create the actual map where drivers and passengers are located.
     */
    public void createMap(){
        // Intersection Initialization
        regentPark = new Intersection("Regent & Park");
        charterPark = new Intersection("Charter & Park");
        universityPark = new Intersection("University & Park");
        randallRegent = new Intersection("Randall & Regent");
        randallOrchard = new Intersection("Randall & Orchard");
        regentCampus = new Intersection("Regent & Campus");
        campusBabcock = new Intersection("Campus & Babcock");
        regentState = new Intersection("Regent & State");
        babcockState = new Intersection("Babcock & State");
        orchardState = new Intersection("Orchard & State");
        universityState = new Intersection("University & State");

        // Setting up the map
        map = new UberMapGraphBD_Placeholder();
        map.insertVertex(regentPark);
        map.insertVertex(charterPark);
        map.insertVertex(universityPark);
        map.insertVertex(randallOrchard);
        map.insertVertex(regentCampus);
        map.insertVertex(campusBabcock);
        map.insertVertex(regentState);
        map.insertVertex(babcockState);
        map.insertVertex(orchardState);
        map.insertVertex(randallRegent);
        map.insertVertex(universityState);
        map.insertEdge(universityState, universityPark, 7);
        map.insertEdge(universityPark, charterPark, 2);
        map.insertEdge(charterPark, universityPark, 2);
        map.insertEdge(charterPark, regentPark, 1);
        map.insertEdge(regentPark, charterPark, 1);
        map.insertEdge(regentPark, randallRegent, 5);
        map.insertEdge(randallRegent, regentPark, 5);
        map.insertEdge(randallRegent, randallOrchard, 3);
        map.insertEdge(randallOrchard, randallRegent, 3);
        map.insertEdge(randallOrchard, orchardState, 2);
        map.insertEdge(orchardState, randallOrchard, 2);
        map.insertEdge(randallRegent, regentCampus, 4);
        map.insertEdge(regentCampus, randallRegent, 4);
        map.insertEdge(regentCampus, campusBabcock, 1);
        map.insertEdge(campusBabcock, regentCampus, 1);
        map.insertEdge(babcockState, campusBabcock, 2);
        map.insertEdge(regentCampus, regentState, 2);
        map.insertEdge(regentState, regentCampus, 2);
        map.insertEdge(regentState, babcockState, 3);
        map.insertEdge(babcockState, regentState, 3);
        map.insertEdge(babcockState, orchardState, 5);
        map.insertEdge(orchardState, babcockState, 5);
        map.insertEdge(orchardState, universityState, 3);
        map.insertEdge(universityState, orchardState, 3);

        // Initializing the driver's starting location
        driverLocation = charterPark;
    }

    /**
     * Method to get the driver's location
     * 
     * @return String representation of the driver's location
     */
    @Override
    public String getDriverLocation() {
        return driverLocation.getName();
    }

    /**
     * Returns the location of a specific passenger
     * 
     * @return the location of this passenger
     * @throws NoSuchElementException if the list is empty or if the passenger is not on the list
     */
    @Override
    public String getPassengerLocation(String passengerName) throws NoSuchElementException {
        if (passengerList == null){ // Throws exception if passengerList is not initialized 
            throw new NoSuchElementException("No passengers on the list");
        }
        for (int i = 0; i < passengerList.size(); i ++){
            if (passengerList.get(i).getName().equals(passengerName)){
                // Returns the passenger location
                return passengerList.get(i).getPassengerLocation();
            }
        }
        // Throw an exception if the passenger is not found in the list
        throw new NoSuchElementException(passengerName + "is not in the list");
    }

    /**
     * 
     * Getter method to get the pickup time of a specific passenger
     * 
     * @return the pickup time of this passenger
     * @throws NoSuchElementException if the list is empty or if the passenger is not on the list
     */
    @Override
    public String getPickUpTime(String passengerName) throws NoSuchElementException{
        if (passengerList == null){// Throws exception if passengerList is not initialized 
            throw new NoSuchElementException("No passengers on the list");
        }
        for (int i = 0; i < passengerList.size(); i ++){
            if (passengerList.get(i).getName().equals(passengerName)){
                // Return the passenger's pickup Time
                return passengerList.get(i).getTime();
            }
        }

        // Throw an exception if the passenger is not found in the list
        throw new NoSuchElementException(passengerName + "is not in the list");   
     }

     /**
      * Method to remove a speicfic passenger from the list
      *
      * @throws NoSuchElementException if the list is empty or if the passenger is not on the list
      */
    @Override
    public void removePassenger(String passengerName) throws NoSuchElementException{
        if (passengerList == null){ // Throws exception if passengerList is not initialized 
            throw new NoSuchElementException("No passengers on the list");
        }
        for (int i = 0; i < passengerList.size(); i ++){
            if (passengerList.get(i).getName().equals(passengerName)){
                // Remove the passenger from the list
                passengerList.remove(i);
                return;
            }
        }

        // Throw an exception if the passenger is not found in the list
        throw new NoSuchElementException(passengerName + "is not in the list");

        
    }


    /**
     * Method to get the shortestPath from the driver to the passenger
     * 
     * @param passengerName the name of the passenger
     * @return a list of Intersections representing the direction to get from the driver to the passenger
     * @throws NoSuchElement if the passenger list is null or if a route cannot be computed
     */
    @Override
    public List<IIntersection> getShortestPath(String passengerName) throws NoSuchElementException{
        if (passengerList == null){ // Throws exception if passengerList is not initialized 
            throw new NoSuchElementException("No passengers on the list");
        }
        // Declaring an Intersection object 
        IIntersection passengerIntersection = null;
        for (int i=0; i < passengerList.size(); i++){ // For each passenger in the list :
            if (passengerList.get(i).getName().equals(passengerName)){ // Find the passenger that is passed in as argument

                // Swtich statement to initialize passengerIntersection to the correct intersection
                switch (passengerList.get(i).getPassengerLocation()){
                    case ("Regent & State"):
                    passengerIntersection = regentState;
                    break;

                    case ("Regent & Park"):
                    passengerIntersection = regentPark;

                    case ("Charter & Park"):
                    passengerIntersection = charterPark;

                    case ("University & State"):
                    passengerIntersection = universityPark;
                    break;

                    case ("Randall & Regent"):
                    passengerIntersection = randallRegent;

                    case ("Randall & Orchard"):
                    passengerIntersection = randallOrchard;
                    break;

                    case ("Regent & Campus"):
                    passengerIntersection = regentCampus;

                    case ("Campus & Babcock"):
                    passengerIntersection = campusBabcock;
                    break;

                    case ("Babcock & State"):
                    passengerIntersection = babcockState;

                    case ("Orchard & State"):
                    passengerIntersection = orchardState;
                    break;

                    case ("University & Park"):
                    passengerIntersection = universityPark;
                    break;
                } // End of Switch Statement
      
            } // End of if statement
        } // End of for loop

        // If passengerIntersection was successfully intialized, compute and return shortest path
        if (passengerIntersection != null){
            return map.shortestPath(driverLocation, passengerIntersection);
        }
        else { // Else throw exception
            throw new NoSuchElementException("Either passenger is not on the list of no route was found");
        }
    } // End of method


    /**
     * Method to get the list of passengers 
     * @return an Arraylist containing passenger location, name and pickup time
     */
    public List<IRoute>getPassengerList() throws FileNotFoundException{
        RouteLoader_BDPlaceholder loader = new RouteLoader_BDPlaceholder();
        List<IRoute> temp = loader.loadUberPickUp("Placeholder"); 
        // Replace "Placeholder" with the file path to get to soft's code next week
        return temp;
    }
    
}
