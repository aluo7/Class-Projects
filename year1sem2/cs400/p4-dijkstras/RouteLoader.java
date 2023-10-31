// --== CS400 P3 File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: 001

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray; // import statements for json.simple
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Instances of classes that implement this interface can be used to load a list
 * of Routes from a specified json source file
 * The following JSON names are used to load these route attributes:
 * - name: The name of the passenger requesting to be picked up
 * - time: The time that the passenger requested to be picked up
 * - passengerLocation: The location of the driver
 * - driverLocation: The location of the passenger
 */
public class RouteLoader {

    /**
     * Makes use of json.simple to loads the list of Routes described within an JSON file.
     * 
     * @param filepath is relative to executable's working directory
     * @return a list of route objects that were read from specified file
     * @throws IOException
     */
    public static List<IRoute> loadRoutes(String filepath) throws IOException {
        JSONParser parser = new JSONParser();
        List<IRoute> routes = new ArrayList<IRoute>(); // list of Route objects to be returned

        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(filepath)); // loads in the file as an Array

            for (Object o : a) { // reading through the Route objects of the array
                JSONObject route = (JSONObject) o; 

                String name = (String) route.get("name"); // pulling the key information from .json file

                String time = (String) route.get("time");

                String passengerLocation = (String) route.get("passengerLocation");

                String driverLocation = (String) route.get("driverLocation");

                routes.add(new Route(name, time, passengerLocation, driverLocation)); // instantiating matching Route
										      // object, adding it to list
										      // of Routes
            }									      

            return routes;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
