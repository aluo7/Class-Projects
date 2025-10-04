// --== CS400 P3 File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: 001

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test; // junit imports
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * tester class for the Data Wrangler's implementation of classes
 */
public class DataWranglerTests {

    private UberMapGraph<String> graph;

    /**
     * instantiates the graph that makes use of the Dijkstra's shortest path
     * methods,
     * changed up the weights
     */
    @BeforeEach
    public void createGraph() {
        graph = new UberMapGraph<>();

        // insert vertices A-F
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");

        // insert edges, changing them from AlgorithmEngineer's tests
        graph.insertEdge("A", "B", 5);
        graph.insertEdge("A", "C", 6);
        graph.insertEdge("A", "D", 3);
        graph.insertEdge("B", "E", 5);
        graph.insertEdge("B", "C", 4);
        graph.insertEdge("C", "B", 1);
        graph.insertEdge("C", "F", 2);
        graph.insertEdge("D", "E", 4);
        graph.insertEdge("E", "A", 7);
        graph.insertEdge("F", "A", 2);
        graph.insertEdge("F", "D", 4);
    }

    /**
     * tests to see that Route objects are created properly, using the constructors
     * 
     * @return true if all tests are passed, false otherwise
     */
    @Test
    public void test1() {
        Route r1 = new Route(); // empty constructor
        Route r2 = new Route("Maddie", "09:06", "Kensington Dr", "Lake Zurich"); // constructor args

        assertEquals("", r1.getName());
        assertEquals("", r1.getTime());
        assertEquals("", r1.getPassengerLocation());
        assertEquals("", r1.getDriverLocation());

        assertEquals("Maddie", r2.getName());
        assertEquals("09:06", r2.getTime());
        assertEquals("Kensington Dr", r2.getPassengerLocation());
        assertEquals("Lake Zurich", r2.getDriverLocation());
    }

    /**
     * tests to see that the compareTo method works properly in all three cases
     * 
     * @return true if all tests are passed, false otherwise
     */
    @Test
    public void test2() {
        Route r1 = new Route("Maddie", "12:04", "Kensington Dr", "Lake Zurich");
        Route r2 = new Route("Maddie", "12:36", "Kensington Dr", "Lake Zurich");
        Route r3 = new Route("Sabrina", "04:32", "Regent St", "Observatory Dr");

        assertEquals(0, r1.compareTo(r1)); // comparing the same route

        assertTrue(r1.compareTo(r2) > 0); // different routes

        assertTrue(r1.compareTo(r3) < 0);
    }

    /**
     * tests to see that the .json file is parsed properly, and that the returned
     * array is the correct size
     * 
     * @return true if all tests are passed, false otherwise
     */
    @Test
    public void test3() {
        List<IRoute> list = new ArrayList<IRoute>(); // instantiating List of IRoutes to be read in

        try {
            list = RouteLoader
                    .loadRoutes("C://Users//luoal//OneDrive//Documents//year1sem2//cs400//p3w14//routes.json"); // loading
                                                                                                                // in
                                                                                                                // the
                                                                                                                // Routes
                                                                                                                // from
                                                                                                                // the
                                                                                                                // .json
                                                                                                                // file
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(25, list.size()); // checks that the list is the correct size
    }

    /**
     * tests to see that the .json file is parsed properly, and that the Route
     * objects within the file contain the correct data values
     * 
     * @return true if all tests are passed, false otherwise
     */
    @Test
    public void test4() {
        List<IRoute> list = new ArrayList<IRoute>(); // instantiating List of IRoutes to be read in

        try {
            list = RouteLoader.loadRoutes("C://Users//luoal//OneDrive//Documents//year1sem2//cs400//p3w14//routes.json"); // loading in the Routes from the .json file
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("Madeline Mitchell", list.get(0).getName()); // checking different Routes, ensuring they match
        assertEquals("09:06", list.get(0).getTime());

        assertEquals("Sabrina Oh", list.get(1).getName());
        assertEquals("Chestnut Ave. and Miflin St.", list.get(1).getPassengerLocation());

        assertEquals("Jusleen Bindra", list.get(2).getName());
        assertEquals("1st Ave. and Manuela St.", list.get(2).getDriverLocation());
    }

    /**
     * tests to see that the correct exception is thrown when the wrong directory is
     * inputted into the loadRoutes method
     * 
     * @return true if all tests are passed, false otherwise
     */
    @Test
    public void test5() {
        List<IRoute> list = new ArrayList<IRoute>();
        boolean testException = false; // changed to true if IOException is caught

        try {
            list = RouteLoader.loadRoutes("./wrongDirectory.json"); // reading in a .json file that doesn't exist
        } catch (IOException e) {
            testException = true;
        }

        assertTrue(testException);
    }

    /**
     * code review test for the Algorithm Engineer, ensures that the graph was
     * instantiated
     * properly and has the proper weights and paths
     */
    @Test
    public void test6() {
        assertEquals(8, graph.getPathCost("A", "F")); // testing that the path cost is correct

        assertEquals(graph.shortestPath("A", "F").toString(), "[A, C, F]"); // testing that the path taken is
        // correct

        List<String> shortPath = graph.shortestPath("A", "F"); // testing that the predecessor is correct
        assertEquals("C", shortPath.get(shortPath.size() - 2));
    }

    /**
     * code review test for the Algorithm Engineer, ensures that the graph was
     * instantiated properly and has the proper weights and paths
     */
    @Test
    public void test7() {
        boolean shortestPathTest = false;
        List<String> paths = graph.getSortedRoutes("F");

        for (int i = 1; i < paths.size(); i++) {
            if (graph.dijkstrasShortestPath("F", paths.get(i)).distance < graph.dijkstrasShortestPath("F",
                    paths.get(i - 1)).distance) {
                shortestPathTest = true;
            }
        }
        assertEquals(shortestPathTest, false);
    }

    /**
     * integration test with Backend, ensure
     */
    @Test
    public void test8() {
        
    }
}
