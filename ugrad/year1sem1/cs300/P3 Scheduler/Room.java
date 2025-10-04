//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Course.Java
// Course: CS 300 Spring 2022
//
// Author: Yash Sancheti
// Email: ysancheti@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Benjamin Wirch
// Partner Email: bwirch@wisc.edu
// Partner Lecturer's Name: Mouna Kacem
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// X Write-up states that pair programming is allowed for this assignment.
// X We have both read and understand the course Pair Programming Policy.
// X We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * @author yashsancheti
 * @author benjaminwirch
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Room {
  private String location;
  private int capacity;

  public Room(String location, int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("Invalid number of students.");
    }
    this.location = location;
    this.capacity = capacity;
  }

  public String getLocation() {
    return this.location;
  }

  public int getCapacity() {
    return this.capacity;
  }

  public Room reduceCapacity(int capacity) {
    if (capacity > this.capacity) {
      throw new IllegalArgumentException("Capacity to decrease is greater than actual capacity");
    }
    return new Room(this.location, this.capacity - capacity);
  }
}
