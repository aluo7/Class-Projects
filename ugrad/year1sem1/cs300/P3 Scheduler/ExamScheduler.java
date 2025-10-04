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

public class ExamScheduler {
  public static Schedule findSchedule(Room[] rooms, Course[] courses) {
    return findScheduleHelper(new Schedule(rooms, courses), 0);
  }

  private static Schedule findScheduleHelper(Schedule schedule, int index) {
    if (index == schedule.getNumCourses()) {
      if (schedule.isComplete()) {
        return schedule;
      }
      throw new IllegalStateException("Schedule not complete");
    }
    if (schedule.isAssigned(index))
      return findScheduleHelper(schedule, index + 1);
    else {
      for (int i = 0; i < schedule.getNumRooms(); i++) {
        try {
          Schedule newSchedule = schedule.assignCourse(index, i);
          return findScheduleHelper(newSchedule, index + 1);
        } catch (Exception e) {
          System.out.println(e);
        }
      }
      throw new IllegalStateException("Schedule not valid");
    }
  }

  public static ArrayList<Schedule> findAllSchedules(Room[] rooms, Course[] courses) {
    return findAllSchedulesHelper(new Schedule(rooms, courses), 0);
  }

  private static ArrayList<Schedule> findAllSchedulesHelper(Schedule schedule, int index) {
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    if (index == schedule.getNumCourses()) {
      if (schedule.isComplete()) {
        schedules.add(schedule);
        return schedules;
      }
      throw new IllegalStateException("Schedule not complete");
    }
    if (schedule.isAssigned(index)) {
      schedules.addAll(findAllSchedulesHelper(schedule, index + 1));
      return schedules;
    } else {
      for (int i = 0; i < schedule.getNumRooms(); i++) {
        try {
          Schedule newSchedule = schedule.assignCourse(index, i);
          schedules.addAll(findAllSchedulesHelper(newSchedule, index + 1));
        } catch (Exception e) {
          System.out.println(e);
        }
      }
      return schedules;
    }
  }
}
