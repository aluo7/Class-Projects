import java.util.List;
import java.util.Scanner; // Import the Scanner class

/**
 * Used to drive a console-based text user interface for the ShowSearcher App.
 */
public class ShowSearcherFrontend {
  
  ShowSearcherBackend back;

  Scanner input;
  public ShowSearcherFrontend(ShowSearcherBackend s) {
    this.back = s;
    input = new Scanner(System.in); // Create a Scanner object
    // input = new Scanner(System.in); // Create a Scanner object
  }
  public ShowSearcherFrontend(String st,ShowSearcherBackend s) {
    this.back = s;
    // input = new Scanner(System.in); // Create a Scanner object
  }

  /**
   * This method drives the entire read, eval, print loop (repl) for the Song Search App. This loop
   * will continue to run until the user explicitly enters the quit command.
   */
  public void runCommandLoop() {

    // String main = input.nextLine(); // Read user input

    System.out.println("Welcome to the Show Searcher App!\n"
        + "============================================================");

    displayCommandMenu();
    String command="";
    while (!command.equals('q') && !command.equals('Q')) {
      command = input.nextLine(); // Read user input
      if (command.equals("q") || command.equals("Q") || command.equals("4")) {
        break;
      }
      else if (command.equals("t") || command.equals("T") || command.equals("1")) {
        titleSearch();
      }
      else if (command.equals("y") || command.equals("Y") || command.equals("2")) {
       yearSearch();
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
      displayCommandMenu();
      
    }

    // System.out.println("Username is: " + userName); // Output user input

    input.close();
  }

  // prints command options to System.out
  public void displayCommandMenu() {
    System.out.print("Command Menu:\n\t" + "1) Search by [T]itle Word\n\t"+
        "2) Search by [Y]ear First Produced\n\t"+ "3) [F]ilter by Streaming Provider\n\t" + 
        "4) [Q]uit\n"        + "Choose a command from the menu above: ");
  }

  // displays a list of shows
  public void displayShows(List<IShow> shows) {
    for (int i = 0; i < shows.size(); i++) {
      String provider = "";
      String[] providers = {"Hulu", "Netflix", "Disney+", "Prime Video"};
      for (int x = 0; x < 4; x++) {
        if (shows.get(i).isAvailableOn(providers[x])) {
          provider = providers[x];
        }
      }
      System.out.println((i + 1) + ". " + shows.get(i).getTitle() + "\n\t"
          + shows.get(i).getRating() + "/100 (" + shows.get(i).getYear() + ") on: " + provider);
    }
  }

  // reads word from System.in, displays results
  public void titleSearch() {
    System.out.println("Choose a word that you would like to search for:");
    String keyword = input.nextLine(); // Read user input
    List<IShow> s = back.searchByTitleWord(keyword);
    displayShows(s);
  }

  // reads year from System.in, displays results
  public void yearSearch() {
    System.out.println("Choose a year that you would like to search for:");
    String years = input.nextLine();
    int year=Integer.parseInt(years);
    List<IShow> s = back.searchByYear(year);
    displayShows(s);
  }
}
