//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Climbing Tracker
// Course: CS 300 Fall 2021
//
// Author: Alan Luo
// Email: aluo7@wisc.edu
// Lecturer: (Mouna Kacem or Hobbes LeGault)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Isha Rustagi
// Partner Email: irustagi@wisc.edu
// Partner Lecturer's Name: (Hobbes LeGault)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// X Write-up states that pair programming is allowed for this assignment.
// X We have both read and understand the course Pair Programming Policy.
// X We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: (identify each by name and describe how they helped)
// Online Sources: (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////


public class ClimbingTracker {
  /*
   * This class modifies the send array by adding successful climbs to the end of the array
   * 
   * @return the resulting size of the array of successful climbs
   */
  public static int sendClimb(String[] send, int numSend, String grade) {
    if (send.length > numSend) {
      if (grade.charAt(0) == 'V' && grade.length() == 2
          && Integer.parseInt(grade.substring(1)) < 8) {
        send[numSend] = grade;
        numSend++;
        return numSend;
      }
    }
    return numSend;
  }

  /*
   * This class modifies the fail array by adding failed climbs to the end of the array
   * 
   * @return the resulting size of the array of failed climbs
   */
  public static int failClimb(String[] fail, int numFail, String grade) {
    if (fail.length > numFail) {
      if (grade.charAt(0) == 'V' && grade.length() == 2
          && Integer.parseInt(grade.substring(1)) < 8) {

        fail[numFail] = grade;
        numFail++;
        return numFail;
      }
    }
    return numFail;
  }

  /*
   * @returns a string that displays the average climb grade over a given (historyLength) number of
   * climbs for both the send and fail arrays
   */
  public static String getStats(String[] send, int numSend, String[] fail, int numFail,
      int historyLength) {
    double sumSend = 0.0; // sum of scores in array send
    double sumFail = 0.0; // sum of scores in array fail
    double averageSend = 0.0; // average of send array
    double averageFail = 0.0; // average of fail array
    String strS = ""; // temporary send string being added to in for loops
    String strF = ""; // temporary fail string being added to in for loops
    String S = ""; // final send string being returned after formatted
    String F = ""; // final fail string being returned after formatted

    if (historyLength < 1) {
      strS = "--";
      strF = "--";
      S += strS;
      F += strF;

    } else {
      if (numSend == 0) {
        strS = "--";
        S += strS;

      } else if (historyLength > numSend) {
        for (int i = 0; i < numSend; i++) {

          sumSend += Integer.parseInt(send[i].substring(1));
        }
        averageSend = sumSend / numSend;
        S += averageSend;

      } else {
        for (int i = numSend - historyLength; i < numSend; i++) {

          sumSend += Integer.parseInt(send[i].substring(1));
        }
        averageSend = sumSend / historyLength;
        S += averageSend;
      }

      if (numFail == 0) {
        strF = "--";
        F += strF;
      } else if (historyLength > numFail) {
        for (int i = 0; i < numFail; i++) {

          sumFail += Integer.parseInt(fail[i].substring(1));
        }
        averageFail = sumFail / numFail;
        F += averageFail;

      } else {
        for (int i = numFail - historyLength; i < numFail; i++) {

          sumFail += Integer.parseInt(fail[i].substring(1));
        }
        averageFail = sumFail / historyLength;
        F += averageFail;
      }
    }

    return ("send: " + S + "\n" + "fail: " + F);
  }

  /*
   * @returns a formatted string that contains a list of failures and successes for each grade with
   * "-" representing a failure and a "+" representing a send
   */
  public static String getHistogram(String[] send, int numSend, String[] fail, int numFail) {
    String histo = ""; // final histogram of grades
    int max = 0; // highest grade between the send and fail arrays
    if (numSend == 0 && numFail == 0) {
      return "Error: no data to display";
    } else {

      for (int counter = 0; counter < numFail; counter++) {
        if (Integer.parseInt(fail[counter].substring(1)) > max) {
          max = Integer.parseInt(fail[counter].substring(1));
        }
      }
      for (int counter = 0; counter < numSend; counter++) {
        if (Integer.parseInt(send[counter].substring(1)) > max) {
          max = Integer.parseInt(send[counter].substring(1));
        }
      }

      for (int v = 0; v <= max; v++) {
        String gradeLvl = "V" + v + ": "; // string containing the successes and failures for each
                                          // grade
        for (int i = 0; i < numFail; i++) {
          if (Integer.parseInt(fail[i].substring(1)) == v) {
            gradeLvl += " -";
          }
        }
        for (int i = 0; i < numSend; i++) {
          if (Integer.parseInt(send[i].substring(1)) == v) {
            gradeLvl += " +";
          }
        }
        if (v != 0) {
          histo += "\n";
        }
        histo += gradeLvl;
      }

      return histo;
    }
  }
}
