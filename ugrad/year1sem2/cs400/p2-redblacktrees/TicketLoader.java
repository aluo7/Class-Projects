import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketLoader {
    public static List<ITicket> loadTickets(String filepath) throws IOException {
        try {
            File input = new File(filepath);
            List<ITicket> list = new ArrayList<ITicket>();
            FileInputStream fis = new FileInputStream(input);
            Scanner sc = new Scanner(fis); // file to be scanned
            // returns true if there is another line to read
            sc.nextLine();
            while (sc.hasNextLine()) {
                if (sc.nextLine().trim().equals("<dataitem>")) {
                    sc.nextLine();
                    String date = sc.nextLine().trim();
                    sc.nextLine();
                    sc.nextLine();
                    String name = sc.nextLine().trim();
                    sc.nextLine();
                    sc.nextLine();
                    String ip = sc.nextLine().trim();
                    sc.nextLine();
                    sc.nextLine();
                    String problem = sc.nextLine().trim();
                    sc.nextLine();
                    sc.nextLine();
                    Ticket add = new Ticket(date, name, ip, problem);
                    list.add(add);
                }
            }
            sc.close();
            return list;
        } catch (Exception E) {
            throw new IOException("XML File has problem associated with its data or load.");
        }
    }
}