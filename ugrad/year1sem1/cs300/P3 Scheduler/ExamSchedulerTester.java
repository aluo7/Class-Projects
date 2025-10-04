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

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Tester methods for Exam Scheduler Methods
 * 
 * @author Benjamin Wirch
 * @author Yash Sanchetti
 */
public class ExamSchedulerTester {

  /**
   * Checks Course() for any possible errors
   * 
   * @return true if working properly, false otherwise
   */
  public static boolean testCourse() {
    boolean exceptionTest = false;
    boolean constructorTest = false;
    // Constructor testing
    {
      String testName = "ENGL100";
      int testNumStudents = 20;

      try {
        Course testCourse = new Course(testName, testNumStudents);
        if (!testCourse.getName().equals(testName)) {
          System.out.println("getName() method has an error.");
          constructorTest = false;
        } else if (testCourse.getNumStudents() != testNumStudents) {
          System.out.println("getNumStudents() method has an error.");
          constructorTest = false;
        } else {
          constructorTest = true;
        }
      } catch (Exception e) {
        System.out.println("Invalid exception thrown!");
        constructorTest = false;
      }

    }
    // Method throwing testing
    {
      String testName = "ENGL100";
      int testNumStudents = -100;

      try {
        Course testCourse = new Course(testName, testNumStudents);
      } catch (IllegalArgumentException e) {
        exceptionTest = true;
      }

    }
    return constructorTest && exceptionTest;
  }

  /**
   * Checks Room() for any possible errors
   * 
   * @return true if working properly, false otherwise
   */
  public static boolean testRoom() {
    boolean exceptionTest = false;
    boolean constructorTest = false;

    // Tests constructors
    {
      String testLocation = "AG 210";
      int testCapacity = 150;
      try {
        Room testRoom = new Room(testLocation, testCapacity);
        if (!testRoom.getLocation().equals(testLocation)) {
          System.out.println("getName() method has an error.");
          constructorTest = false;
        } else if (testRoom.getCapacity() != testCapacity) {
          System.out.println("getNumStudents() method has an error.");
          constructorTest = false;
        } else {
          constructorTest = true;
        }
      } catch (Exception e) {
        System.out.println("Invalid exception thrown!");
        constructorTest = false;
      }
    }
    {
      String testLocation = "ENGL100";
      int testCapacity = -100;

      // Method throwing testing
      {
        try {
          Room testRoom = new Room(testLocation, testCapacity);
        } catch (IllegalArgumentException e) {
          exceptionTest = true;
        }
      }
      return constructorTest && exceptionTest;
    }
  }

  /**
   * Checks the scheduleAccessors() method for any possible errors
   * 
   * @return true if working properly, false otherwise
   */
  public static boolean testScheduleAccessors() {
    // Test the getNumRooms() and the getNumCourses() methods
    boolean roomSizeCheck = false;
    boolean courseSizeCheck = false;

    int testRoomSize = 100;
    int testCourseSize = 50;
    Room[] testRooms = new Room[testRoomSize];
    Course[] testCourses = new Course[testCourseSize];

    try {
      Schedule testSchedule = new Schedule(testRooms, testCourses);

      if (testSchedule.getNumRooms() == testRoomSize) {
        roomSizeCheck = true;
      }
      if (testSchedule.getNumCourses() == testCourseSize) {
        courseSizeCheck = true;
      }
    } catch (Exception e) {
      return false;
    }
    // Tests the getRoom() and the getCourse() methods
    int testRoomSize2 = 100;
    int testCourseSize2 = 50;
    int testRoomIndex = 10;
    int testCourseIndex = 5;
    Room[] testRooms2 = new Room[testRoomSize2];
    Course[] testCourses2 = new Course[testCourseSize2];

    testRooms2[testRoomIndex] = new Room("AG 210", 500);
    testCourses2[testCourseIndex] = new Course("AG 211", 500);

    // TODO YASH START HERE

    return roomSizeCheck && courseSizeCheck;
  }

  /**
   * Checks the assignCourse() method for any possible errors
   * 
   * @return true if working properly, false otherwise
   */
  public static boolean testAssignCourse() {
    return true;
  }

  /**
   * Checks the findSchedule() method for any possible errors
   * 
   * @return true if working properly, false otherwise
   */
  public static boolean testFindSchedule() {
    return true;
  }

  /**
   * Checks the findAllSchedules() method for any possible errors
   * 
   * @return true if working properly, false otherwise
   */
  public static boolean testFindAllSchedules() {
    return true;
  }

  public static void main(String[] args) {
    System.out.println(testCourse());
    System.out.println(testRoom());
    // System.out.println(testScheduleAccessors());
    // System.out.println(testAssignCourse());
  }

}
