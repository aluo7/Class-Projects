// --== CS400 P3 File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: 001

import java.util.List;

import java.io.IOException;

/**
 * Instances of classes that implement this interface can be used to load a list
 * of tickets from a specified json source file
 * The following JSON names are used to load these route attributes:
 * - NAME: The name of the passenger requesting to be picked up
 * - TIME: The time that the passenger requested to be picked up
 * - DLOCATION: The location of the driver
 * - PLOCATION: The location of the passenger
 */
public interface IRouteLoader {
  /**
   * This method loads the list of tickets described within an JSON file.
   * 
   * @param filepath is relative to executable's working directory
   * @return a list of route objects that were read from specified file
   * @throws IOException
   */
  public List<IRoute> loadRoutes(String filepath)
      throws IOException;
}
