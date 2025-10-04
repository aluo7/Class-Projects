import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the implementation of CS400Graph for the individual component of
 * Project Three: the implementation of Dijsktra's Shortest Path algorithm
 */
public class GraphTest {

    private CS400Graph<String> graph;

    /**
     * Instantiate graph from last week's shortest path activity
     */
    @BeforeEach
    public void createGraph() {
        graph = new CS400Graph<>();
        // insert vertices A-F
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");
        // insert edges from Week 11. Shortest Path Activity
        graph.insertEdge("A", "B", 6);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "D", 5);
        graph.insertEdge("B", "E", 1);
        graph.insertEdge("B", "C", 2);
        graph.insertEdge("C", "B", 3);
        graph.insertEdge("C", "F", 1);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("E", "A", 4);
        graph.insertEdge("F", "A", 1);
        graph.insertEdge("F", "D", 1);
    }

    /**
     * Checks the distance/total weight cost from the vertex A to F
     */
    @Test
    public void testPathCostAtoF() {
        assertTrue(graph.getPathCost("A", "F") == 3);
    }

    /**
     * Checks the distance/total weight cost from the vertex A to D
     */
    @Test
    public void testPathCostAtoD() {
        assertTrue(graph.getPathCost("A", "D") == 4);
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex
     * A to D
     */
    @Test
    public void testPathAtoD() {
        assertTrue(graph.shortestPath("A", "D").toString().equals("[A, C, F, D]"));
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex
     * A to F
     */
    @Test
    public void testPathAtoF() {
        assertTrue(graph.shortestPath("A", "F").toString().equals("[A, C, F]"));
    }

    /**
     * Confirm that the distance you computed for this node is correct
     */
    @Test
    public void testPathCostAtoE() {
        assertTrue(graph.getPathCost("A", "E") == 6);
    }

    /**
     * Confirm the sequence of nodes along the path from your source node to this
     * same end node is correct
     */
    @Test
    public void testPathAtoE() {
        assertTrue(graph.shortestPath("A", "E").toString().equals("[A, C, B, E]"));
    }

    @Test
    /**
     * Confirm the path cost you reported for the question about path cost
     */
    public void testPathCostBtoF() {
        assertTrue(graph.getPathCost("B", "F") == 3);
    }

    /**
     * Confirm the predecessor you reported for question about a final node's
     * predecessor
     */
    @Test
    public void testPredecessorCtoE() {
        assertTrue(graph.shortestPath("C", "E").get(1) == "B");
    }

    /**
     * Confirm the sequence of nodes and path cost for a starting node that isn't
     * "A"
     */
    @Test
    public void testBtoD() {
        assertTrue(graph.shortestPath("B", "D").toString().equals("[B, C, F, D]"));

        assertTrue(graph.getPathCost("B", "D") == 4);
    }
}