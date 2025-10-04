// DO NOT submit this file to gradescope 

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;

/**
 * This class models a school assignment with a name and due date
 */
public class Assignment implements Comparable<Assignment> {
  private static int idGenerator = 1; // generator of assignments' identifiers
  private final int ID; // unique identifier of this assignment
  private final String name; // name of this assignment
  private final Date dueDate; // due date of this assignment

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd ha"); // date format
  private static final String TO_STRING_FORMAT = "%s (Due %s)"; // string formatting of the due date

  /**
   * Creates a new Assignment with the given name and due date
   *
   * @param name       name of this Assignment
   * @param month      month of the year this Assignment is due (month must be in the range of 1 .. 12)
   * @param dayOfMonth day of the month this Assignment is due (day must be in the range 1 .. 31)
   * @param hour       hour of the day this Assignment is due (hour must be in the range 0 .. 23)
   * @throws IllegalArgumentException if the provided month, dayOfMonth, or hour is invalid
   */
  public Assignment(String name, int month, int dayOfMonth, int hour) {
    this.ID = idGenerator;
    idGenerator++;
    this.name = name;
    try {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.MONTH, month - 1);
      calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      calendar.set(Calendar.HOUR_OF_DAY, hour);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      dueDate = calendar.getTime();
    } catch (DateTimeException e) {
      throw new IllegalArgumentException("Invalid due date.");
    }
  }

  /**
   * Returns the name of this Assignment
   * 
   * @return the name of this Assignment
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the due date of this Assignment
   * 
   * @return the due date of this Assignment
   */
  public Date getDueDate() {
    return dueDate;
  }

  /**
   * Checks whether this assignment equals another object passed as input
   * 
   * @param o Object to compare to
   * @return {@code true} if the given Object is an Assignment and has the same name and due date as
   *         this Assignment
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Assignment)) {
      return false;
    }
    Assignment other = (Assignment) o;
    return other.name.equals(this.name) && other.dueDate.equals(this.dueDate);
  }

  /**
   * Compares this Assignment to another assignment based on their due dates
   * 
   * @return a negative integer if this Assignment has an earlier due date, {@code 0} if the two
   *         Assignments have the same due date and same ID, and a positive integer if this
   *         Assignment has a later due date. If two assignments have the same due date, the smaller
   *         assignment is the one having the smaller ID.
   * @throws NullPointerException if the other assignment o is null
   */
  @Override
  public int compareTo(Assignment o) {
    if(this.dueDate.equals(o.dueDate))
      return this.ID - o.ID;
    return this.dueDate.compareTo(o.dueDate);
  }

  /**
   * Returns a String representing this Assignment containing its name and due date.
   * 
   * @return a String representing this Assignment
   */
  @Override
  public String toString() {
    return String.format(TO_STRING_FORMAT, name, DATE_FORMAT.format(dueDate));
  }

  /**
   * Resets the ID generator for testing purposes.
   */
  public static void resetGenerator() {
    idGenerator = 1;
  }
}