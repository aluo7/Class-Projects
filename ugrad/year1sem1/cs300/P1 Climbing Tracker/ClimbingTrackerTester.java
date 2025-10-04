public class ClimbingTrackerTester {

  public static void main(String[] args) {
    runAllTests();
    String[] send = {"V1", "V2", "V3", "V4", "V5", "V6", "V7", null, null};
    String[] fail = {"V1", "V2", "V3", "V4", "V5", "V6", "V7"};
    int numSend = 7;
    int numFail = 7;
    int historyLength = 4;

    
    
     
    String[] sendSOR = {"V1", "V2", "V5", "V2", "V7", "V3"};
    String[] failSOR = {"V1", "V3", "V3", "V4", "V7", null};
    numSend = 6;
    numFail = 5;
     historyLength = 8;
     String[] sendE = new String[100];
     numSend = 0;
     numFail = 7;
     historyLength = 2;
     System.out.println(ClimbingTracker.getStats(sendE, numSend, fail, numFail, historyLength));
     if (ClimbingTracker.getStats(sendE, numSend, fail, numFail, historyLength)
         .contains("send: --\n" + "fail: 6.5")) {

       System.out.println("helo");

    
     }
    }
  

  /*
   * tests sendClimb to see if it properly adds to the array of successful climbs, and doesn't add
   * to it in specific cases
   * 
   * @returns false if it doesn't work, true if it passes all tests
   */
  public static boolean testSendClimb() {
    String[] sSend = {"V0", "V1", "V2", "V3", null, null}; // oversized array
    String[] send = {"V0", "V2", "V1", "V3"};
    String[] s = new String[100]; // empty array
    int numSend = 4;
    String validGrade = "V6";
    String invalidGrade = "G6"; // wrong letter
    String invalidGrade2 = "V06"; // invalid number
    String invalidGrade3 = "V8"; // number too high
    if (ClimbingTracker.sendClimb(sSend, numSend, validGrade) != 5) {
      return false;
    }
    if (ClimbingTracker.sendClimb(send, numSend, validGrade) != 4) {
      return false;
    }
    numSend = 0;
    if (ClimbingTracker.sendClimb(s, numSend, validGrade) != 1) {
      return false;
    }
    numSend = 4;
    if (ClimbingTracker.sendClimb(sSend, numSend, invalidGrade) != 4) {
      return false;
    }
    if (ClimbingTracker.sendClimb(sSend, numSend, invalidGrade2) != 4) {
      return false;
    }
    if (ClimbingTracker.sendClimb(sSend, numSend, invalidGrade3) != 4) {
      return false;
    }
    return true;
  }

  /*
   * tests failClimb to see if it properly adds to the array of failed climbs, and doesn't add to it
   * in specific cases
   * 
   * @returns false if it doesn't work, true if it passes all tests
   */
  public static boolean testFailClimb() {
    String[] fFail = {"V0", "V1", "V2", "V3", null, null}; // oversized array
    String[] fail = {"V0", "V2", "V1", "V3"};
    String[] f = new String[100]; // empty array
    int numFail = 4;
    String validGrade = "V6";
    String invalidGrade = "G6"; // wrong letter
    String invalidGrade2 = "V06"; // invalid number
    String invalidGrade3 = "V8"; // number too high
    if (ClimbingTracker.sendClimb(fFail, numFail, validGrade) != 5) {
      return false;
    }
    if (ClimbingTracker.sendClimb(fail, numFail, validGrade) != 4) {
      return false;
    }
    numFail = 0;
    if (ClimbingTracker.sendClimb(f, numFail, validGrade) != 1) {
      return false;
    }
    numFail = 4;
    if (ClimbingTracker.sendClimb(fFail, numFail, invalidGrade) != 4) {
      return false;
    }
    if (ClimbingTracker.sendClimb(fFail, numFail, invalidGrade2) != 4) {
      return false;
    }
    if (ClimbingTracker.sendClimb(fFail, numFail, invalidGrade3) != 4) {
      return false;
    }
    return true;
  }

  /*
   * tests to make sure that the averages of the scores are correct, as well as the string that is
   * outputted
   * 
   * @returns false if it fails any test, true if it passes all tests
   */
  public static boolean testGetStats() {

    // typical cases
    String[] send = {"V1", "V2", "V3", "V4", "V5", "V6", "V7", null, null};
    String[] fail = {"V1", "V2", "V3", "V4", "V5", "V6", "V7"};
    int numSend = 7;
    int numFail = 7;
    int historyLength = 4;

    if (!ClimbingTracker.getStats(send, numSend, fail, numFail, historyLength)
        .contains("send: 5.5\n" + "fail: 5.5")) {
      return false;
    }
    // historyLength > numSend and numFail
    String[] sendSOR = {"V1", "V2", "V5", "V2", "V7", "V3"};
    String[] failSOR = {"V1", "V3", "V3", "V4", "V7", null};
    numSend = 6;
    numFail = 5;
    historyLength = 8;

    if (!ClimbingTracker.getStats(sendSOR, numSend, failSOR, numFail, historyLength)
        .contains("send: 3.3333333333333335\n" + "fail: 3.6")) {

      return false;
    }

    // edge case
    String[] sendE = new String[100];
    numSend = 0;
    numFail = 7;
    historyLength = 2;

    if (!ClimbingTracker.getStats(sendE, numSend, fail, numFail, historyLength)
        .contains("send: --\n" + "fail: 6.5")) {
      return false;
    }

    // empty send and fail array
    String[] failE = new String[100];
    numSend = 0;
    numFail = 0;
    historyLength = 3;

    if (!ClimbingTracker.getStats(sendE, numSend, failE, numFail, historyLength)
        .contains("send: --\n" + "fail: --")) {
      return false;
    }

    return true;
  }

  /*
   * tests to see if the histogram properly displays and collects data from the arrays, and that the
   * outputted string is correct
   * 
   * @returns true if the outputted string for the histogram is true, returns false otherwise
   */
  public static boolean testGetHistogram() {

    // testing edge case
    String[] send = {"V0", "V2", "V1", "V3"};
    String[] fail = {"V5", "V0", "V1", "V2", "V3"};
    int numSend = 4;
    int numFail = 5;
    if (!ClimbingTracker.getHistogram(send, numSend, fail, numFail)
        .contains("V0: " + " - +" + "\n" + "V1: " + " - +" + "\n" + "V2: " + " - +" + "\n" + "V3: "
            + " - +" + "\n" + "V4: " + "\n" + "V5: " + " -")) {
      return false;
    }



    // empty arrays
    String[] s = new String[100];
    numSend = 0;
    String[] f = new String[100];
    numFail = 0;


    if (!ClimbingTracker.getHistogram(s, numSend, f, numFail)
        .contains("Error: " + "no data to display")) {
      return false;
    }

    // testing typical values
    String[] sSend = {"V0", "V1", "V2", "V3", null, null};
    String[] fFail = {"V0", "V2", "V1", "V2", "V0", "V7", "V5"};
    numSend = 4;
    numFail = 7;
    if (!ClimbingTracker.getHistogram(sSend, numSend, fFail, numFail)
        .contains("V0:  - - +\n" + "V1:  - +\n" + "V2:  - - +\n" + "V3:  +\n" + "V4: \n"
            + "V5:  -\n" + "V6: \n" + "V7:  -")) {
      return false;
    }



    return true;
  }

  /*
   * checks if each method returns true
   * 
   * @returns true if each method is true
   */
  public static boolean runAllTests() {
    if (testSendClimb() == true && testGetHistogram() == true && testFailClimb() == true
        && testGetStats() == true) {
      return true;
    }
    return false;
  }
}
