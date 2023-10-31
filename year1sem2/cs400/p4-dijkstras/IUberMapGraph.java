import java.util.List;
import java.util.NoSuchElementException;

/**
 * This interface is used to create a class which extends the CS400Graph class.
 *
 * @param <T> node type
 */
public interface IUberMapGraph<T> extends GraphADT<T>{

    /**
     * This method should return a sorted list of the lowest weight to the highest weight locations based off where
     * ie the closest locations to the furthest locations from the driver’s location
     * the driver currently is.
     *
     * @param driverLocation the node where the driver currently is
     * @return a sorted list from the closest locations to the driver’s location to the furthest locations
     * @throws NoSuchElementException if driverLocation is null
     */
    public List<T> getSortedRoutes(T driverLocation);
}

