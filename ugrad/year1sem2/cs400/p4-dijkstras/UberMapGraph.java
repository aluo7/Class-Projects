import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * This class extends the CS400Graph class.
 *
 * @param <T> node type
 */
public class UberMapGraph<T> extends CS400Graph<T> implements IUberMapGraph<T> {

    /**
     * This method should return a sorted list of the lowest weight to the 
     * highest weight locations based off where
     * is the closest locations to the furthest locations 
     * from the driver’s location the driver currently is.
     *
     * @param driverLocation the node where the driver currently is
     * @return a sorted list from the closest locations to the driver’s location to the furthest locations
     * @throws NoSuchElementException if driverLocation is null
     */
    public List<T> getSortedRoutes(T driverLocation){
	   
	if (vertices.get(driverLocation)==null){
		throw new NoSuchElementException("This location doesn't exist");
	}
	//create a priority queue of paths that are sorted by the distance
        PriorityQueue<Path> paths= new PriorityQueue<Path>();

        //check for destination nodes from edges
        for (Vertex v: vertices.values()){
		if (v.data!=null){
                    paths.add(dijkstrasShortestPath(driverLocation, v.data));
		}
	}
	List<T> sortedRoute=new LinkedList<T>();
	for (int i=0; i<paths.size();i++){
		sortedRoute.add(paths.poll().end.data);
	}
	if (sortedRoute.contains(driverLocation)){
		sortedRoute.remove(driverLocation);
	}

	return sortedRoute;
    }
}

