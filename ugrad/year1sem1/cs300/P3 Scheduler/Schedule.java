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

/**
 * This is the Schedule class which holds the Schedle constructor and accessors
 */
public class Schedule {
  // initiates variables that make up Schedule data
  private Room[] rooms;
  private Course[] courses;
  private int[] assignments;

  /**
   * Constructor for Schedule class. Instantiates rooms, courses, and assignments.
   * 
   * @param rooms   Array of rooms
   * @param courses Array of courses
   */
  public Schedule(Room[] rooms, Course[] courses) {
    this.rooms = rooms;
    this.courses = courses;
    this.assignments = new int[courses.length];
    for (int i = 0; i < assignments.length; i++) {
      this.assignments[i] = -1;
    }
  }

  /**
   * Constructior for Schedule class with assignements field. Instantiates rooms,
   * courses, and
   * assignments.
   * 
   * @param rooms       Array of rooms
   * @param courses     Array of courses
   * @param assignments Array of assignements
   */
  private Schedule(Room[] rooms, Course[] courses, int[] assignments) {
    this.rooms = rooms;
    this.courses = courses;
    this.assignments = assignments;
  }

  /**
   * Gets the number of Rooms from rooms array
   * 
   * @return Length of rooms array
   */
  public int getNumRooms() {
    return this.rooms.length;
  }

  /**
   * Gets Room in question
   * 
   * @param index position at what room the user is looking for
   * @return Returns specific Room object
   */
  public Room getRoom(int index) {
    try {
      return this.rooms[index];
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("Index is Invalid");
    }
  }

  /**
   * Gets number of courses
   * 
   * @return number of courses
   */
  public int getNumCourses() {
    return this.courses.length;
  }

  /**
   * Gets specific course at the index
   * 
   * @param index position at which specific course is located
   * @return return
   */
  public Course getCourse(int index) {
    try {
      return this.courses[index];
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("Index is Invalid");
    }
  }

  public boolean isAssigned(int index) {
    return this.assignments[index] != -1;
  }

  public Room getAssignment(int index) {
    try {
      if (this.assignments[index] == -1) {
        throw new IllegalArgumentException("Course has not been assigned a room");
      }
      return this.rooms[this.assignments[index]];
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("Index is Invalid");
    }
  }

  public boolean isComplete() {
    for (int i = 0; i < assignments.length; i++) {
      if (this.assignments[i] == -1) {
        return false;
      }
    }
    return true;
  }

  public Schedule assignCourse(int index1, int index2) {
    try {
      if (isAssigned(index1)
          || this.rooms[index2].getCapacity() < this.courses[index1].getNumStudents()) {
        throw new IllegalArgumentException(
            "Course has been assigned a room or Doesnt have Sufficient capacity");
      }
      Room[] newRooms = Arrays.copyOf(this.rooms, rooms.length);
      Course[] newCourses = Arrays.copyOf(this.courses, courses.length);
      int[] newAssignments = Arrays.copyOf(this.assignments, assignments.length);
      newAssignments[index1] = index2;
      newRooms[index2] = newRooms[index2].reduceCapacity(courses[index1].getNumStudents());
      return new Schedule(newRooms, newCourses, newAssignments);
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("Index is Invalid");
    }
  }

  @Override
  public String toString() {
    String stringRepresentation = "{";
    for (int i = 0; i < courses.length; i++) {
      if (assignments[i] == -1) {
        stringRepresentation += this.courses[i].getName() + ": Unassigned, ";
      } else {
        stringRepresentation += this.courses[i].getName() + ": " + this.rooms[assignments[i]].getLocation() + ", ";
      }
    }
    stringRepresentation = stringRepresentation.substring(0, stringRepresentation.lastIndexOf(", "));
    return stringRepresentation + "}";
  }

}
