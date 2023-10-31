import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * This class can be used to test text based user interactions by 1) specifying
 * a String of text input (that will be fed to System.in as if entered by the user),
 * and then 2) capturing the output printed to System.out and System.err in String
 * form so that it can be compared to the expect output.
 * @author dahl
 * @date 2021.10
 */
public class FrontendDeveloperTests {

    /**
     * This main method demonstrates the use of a TextUITester object to check
     * the behavior of the following run method.
     * @param args from the command line are not used in this example
     */
    public static void main(String[] args) {
      System.out.println("Test 1: "+test1()+"\nTest 2: "+test2()+"\nTest 3: "+test3()+"\nTest 4: "+test4()+"\nTest 5: "+test5());
    }   
    
    /**
     * test1: Checks whether welcome message is printed as expected
     * @return  true if output functional, false otherwise
     */
    public static boolean test1() { 
      // 1. Create a new TextUITester object for each test, and
      // pass the text that you'd like to simulate a user typing as only argument.
      FrontendDeveloperTests tester = new FrontendDeveloperTests("1\nsound\n2\n2010\n3\n1\n5\nq");

      // 2. Run the code that you want to test here:
      run(); // (this code should read from System.in and write to System.out)

      // 3. Check whether the output printed to System.out matches your expectations.
      String output = tester.checkOutput();
      if(!output.startsWith("Welcome to the Show Searcher App!")){
        return false;
      }

      return true;
    }
    
    
    /**
     * test2: Reads 7 strings that represent options, titles, years, and providers from System.in, 
     *    and checks whether the command menu transitions into commands as expected
     * @return  true if command menu functional, false otherwise
     */
    public static boolean test2() { 
   // 1. Create a new TextUITester object for each test, and
      // pass the text that you'd like to simulate a user typing as only argument.
      FrontendDeveloperTests tester = new FrontendDeveloperTests("1\nsound\n2\n2010\n3\n1\n5\nq");

      // 2. Run the code that you want to test here:
      run(); // (this code should read from System.in and write to System.out)

      // 3. Check whether the options and providers are listed, and whether titles and years are searched for.
      String output = tester.checkOutput();
      if(!output.contains("Choose a command from")||!output.contains("Choose a word that")||
          !output.contains("Choose a year")||!output.contains("Providers that shows are being searched for"))
          return false;
 
      return true;
    }
    
    /**
     * test3: Checks whether the shows displayed from the options selected print properly
     * @return  true if display functional, false otherwise
     */
    public static boolean test3() {

      // 1. Create a new TextUITester object for each test, and
      // pass the text that you'd like to simulate a user typing as only argument.
      FrontendDeveloperTests tester = new FrontendDeveloperTests("1\nsound\n2\n2010\n3\n1\n5\nq");

      // 2. Run the code that you want to test here:
      run(); // (this code should read from System.in and write to System.out)

      // 3. Check whether the options and providers are listed, and whether titles and years are searched for.
      String output = tester.checkOutput();
      if(!output.contains("Sound of Music")||!output.contains("Sound of War")||
          !output.contains("Damage")||!output.contains("Land of War")||!output.contains("(2010)"))
          return false;
 
      return true;
    
    }
    
    /**
     * test4: Checks whether the front end terminates correctly with the 8th string "q" passed in
     * @return  true if quit functional, false otherwise
     */
    public static boolean test4() {
   // 1. Create a new TextUITester object for each test, and
      // pass the text that you'd like to simulate a user typing as only argument.
      FrontendDeveloperTests tester = new FrontendDeveloperTests("1\nsound\n3\n1\n5\nq\n2\n2010");

      // 2. Run the code that you want to test here:
      run(); // (this code should read from System.in and write to System.out)

      // 3. Check whether year is searched for after the program has been told to quit
      String output = tester.checkOutput();
      if(output.contains("Choose a year")||!output.contains("Providers that shows are being searched for"))
          return false;
 
      return true;
      }
    
      /**
       * test5: Checks whether the toggle option toggles the correct provider
       * 
       * @return true if toggle functional, false otherwise
       */
      public static boolean test5() {

        // 1. Create a new TextUITester object for each test, and
        // pass the text that you'd like to simulate a user typing as only argument.
        FrontendDeveloperTests tester = new FrontendDeveloperTests("1\nsound\n2\n2010\n3\n1\n5\nq");

        // 2. Run the code that you want to test here:
        run(); // (this code should read from System.in and write to System.out)

        // 3. Check whether the options and providers are listed, and whether titles and years are
        // searched for.
        String output = tester.checkOutput();
        if (!output.contains("__ [N]etflix") || !output.contains("_x_ [N]etflix")
            || !output.contains("2) _x_ [H]ulu") || output.contains("2) __ [H]ulu")) {
          return false;
        }
        return true;

      }
    
    /**
     * This is the code being tested by the main method above.
     * It 1) prints out a welcome message, 
     *    2) reads 7 strings that represent options, titles, years, and providers from System.in, and then
     *    3) the shows displayed from those options selected
     *       Quits with the 8th string "q" passed in
     */
    public static void run() {
        // Note to avoid instantiating more than one Scanner reading from System.in in your code!
      Scanner input = new Scanner(System.in); // Create a Scanner object
      PHShowSearcherBackend back=new PHShowSearcherBackend();
      System.out.println("Welcome to the Show Searcher App!\n"
          + "============================================================");

      System.out.println("Command Menu:\n\t" + "1) Search by [T]itle Word\n\t"+
          "2) Search by [Y]ear First Produced\n\t"+ "3) [F]ilter by Streaming Provider\n\t" + 
          "4) [Q]uit\n"        + "Choose a command from the menu above: ");
      String command="";
      while (!command.equals('q') && !command.equals('Q')) {
        command = input.nextLine(); // Read user input
        if (command.equals("q") || command.equals("Q") || command.equals("4")) {
          break;
        }
        else if (command.equals("t") || command.equals("T") || command.equals("1")) {
          System.out.println("Choose a word that you would like to search for:");
          String keyword = input.nextLine(); // Read user input
          List<IShow> s = back.searchByTitleWord(keyword);
          for (int i = 0; i < s.size(); i++) {
            String provider = "";
            String[] providers = {"Hulu", "Netflix", "Disney+", "Prime Video"};
            for (int x = 0; x < 4; x++) {
              if (s.get(i).isAvailableOn(providers[x])) {
                provider = providers[x];
              }
            }
            System.out.println((i + 1) + ". " + s.get(i).getTitle() + "\n\t"
                + s.get(i).getRating() + "/100 (" + s.get(i).getYear() + ") on: " + provider);
          }
        }
        else if (command.equals("y") || command.equals("Y") || command.equals("2")) {
          System.out.println("Choose a year that you would like to search for:");
          String years = input.nextLine();
          int year=Integer.parseInt(years);
          List<IShow> s = back.searchByYear(year);
          for (int i = 0; i < s.size(); i++) {
            String provider = "";
            String[] providers = {"Hulu", "Netflix", "Disney+", "Prime Video"};
            for (int x = 0; x < 4; x++) {
              if (s.get(i).isAvailableOn(providers[x])) {
                provider = providers[x];
              }
            }
            System.out.println((i + 1) + ". " + s.get(i).getTitle() + "\n\t"
                + s.get(i).getRating() + "/100 (" + s.get(i).getYear() + ") on: " + provider);
          }
        }
        else if (command.equals("f") || command.equals("F") || command.equals("3")) {
          String provider="";
          while (!provider.equals("5")) {
            boolean n = back.getProviderFilter("Netflix");
            String ns = "";
            if (n) {
              ns += "x";
            }
            boolean h = back.getProviderFilter("Hulu");
            String hs = "";
            if (h) {
              hs += "x";
            }
            boolean p = back.getProviderFilter("Prime Video");
            String ps = "";
            if (p) {
              ps += "x";
            }
            boolean d = back.getProviderFilter("Disney+");
            String ds = "";
            if (d) {
              ds += "x";
            }
            System.out.println("Providers that shows are being searched for:\r\n" + "    1) _" + ns
                + "_ [N]etflix\r\n" + "    2) _" + hs + "_ [H]ulu\r\n" + "    3) _" + ps
                + "_ [P]rime Video\r\n" + "    4) _" + ds + "_ [D]isney+\r\n"
                + "    5) [Q]uit toggling provider filters\r\n"
                + "Choose the number provider (Typing Q quits the entire program!) that you'd like "
                + "to toggle the filter for:");
            provider = input.nextLine();
            if (provider.equals("5")) {
              break;
            } else {
              if (provider.equals("1") || provider.equals("n") || provider.equals("N")) {
                back.toggleProviderFilter("Netflix");
              }
              if (provider.equals("2") || provider.equals("h") || provider.equals("H")) {
                back.toggleProviderFilter("Hulu");
              }
              if (provider.equals("3") || provider.equals("p") || provider.equals("P")) {
                back.toggleProviderFilter("Prime Video");
              }
              if (provider.equals("4") || provider.equals("d") || provider.equals("D")) {
                back.toggleProviderFilter("Disney+");
              }
            }
          }

        }
        System.out.print("Command Menu:\n\t" + "1) Search by [T]itle Word\n\t"+
            "2) Search by [Y]ear First Produced\n\t"+ "3) [F]ilter by Streaming Provider\n\t" + 
            "4) [Q]uit\n"        + "Choose a command from the menu above: ");
        
      }
      input.close();
    }

    // Below is the code that actually implements the redirection of System.in and System.out,
    // and you are welcome to ignore this code: focusing instead on how the constructor and
    // checkOutput() method is used int he example above.

    private PrintStream saveSystemOut; // store standard io references to restore after test
    private PrintStream saveSystemErr;
    private InputStream saveSystemIn;
    private ByteArrayOutputStream redirectedOut; // where output is written to durring the test
    private ByteArrayOutputStream redirectedErr;

    /**
     * Creates a new test object with the specified string of simulated user input text.
     * @param programInput the String of text that you want to simulate being typed in by the user.
     */
    public FrontendDeveloperTests(String programInput) {
        // backup standard io before redirecting for tests
        saveSystemOut = System.out;
        saveSystemErr = System.err;
        saveSystemIn = System.in;    
        // create alternative location to write output, and to read input from
        System.setOut(new PrintStream(redirectedOut = new ByteArrayOutputStream()));
        System.setErr(new PrintStream(redirectedErr = new ByteArrayOutputStream()));
        System.setIn(new ByteArrayInputStream(programInput.getBytes()));
    }

    /**
     * Call this method after running your test code, to check whether the expected
     * text was printed out to System.out and System.err.  Calling this method will 
     * also un-redirect standard io, so that the console can be used as normal again.
     * 
     * @return captured text that was printed to System.out and System.err durring test.
     */
    public String checkOutput() {
        try {
            String programOutput = redirectedOut.toString() + redirectedErr.toString();
            return programOutput;    
        } finally {
            // restore standard io to their pre-test states
            System.out.close();
            System.setOut(saveSystemOut);
            System.err.close();
            System.setErr(saveSystemErr);
            System.setIn(saveSystemIn);    
        }
    }
}
