
/**
 * Instances of classes that implement this interface represent a single ticket object that can be stored, sorted, and searched for based on these accessors below
 */
public interface ITicket extends Comparable<ITicket> {

  // constructor args (String date, String name, String address, String problem)

  String getDate(); // retrieve the date the help request was submitted
  String getName(); // retrieve the name of the person that submitted the help request
  String getIP(); // retrieve the IP address of the ticket's computer
  String getProblem(); // retrieves the issue type

  // compareTo() methods supports sorting tickets in order by priority (date submitted)
  // oldest (high priority) to newest (low priority) requests
}
