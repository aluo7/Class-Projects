// --== CS400 Project One File Header ==--
// Name: Ryan Hanson
// CSL Username: rhanson
// Email: rrhanson@wisc.edu
// Lecture #: 001
// Notes to Grader: 



public class Ticket implements ITicket {
	public String date;
	private String name;
	private String ip;
	private String problem;

	public Ticket() {
		date = "";
		name = "";
		ip = "";
		problem = "";
	}

	public Ticket(String date, String name, String ip, String problem) {
		this.date = date;
		this.name = name;
		this.ip = ip;
		this.problem = problem;
	}

	@Override
	public int compareTo(ITicket ticket2) {
		String[] dates1 = this.date.split(":");
		String[] dates2 = ((Ticket) ticket2).date.split(":");

		for (int i = 0; i < dates1.length; i++) {
			if (Integer.parseInt(dates2[i]) - Integer.parseInt(dates1[i]) != 0) {
				return Integer.parseInt(dates2[i]) - Integer.parseInt(dates1[i]);
			}
		}

		// If they are the same return 0;
		if (ticket2.getIP() == this.getIP()) {
			if (ticket2.getName() == this.getName()) {
				if (ticket2.getProblem() == this.getProblem()) {
					return 0;
				}
			}
		}

		// If both are filed on the same date. They have equal priority and
		// one is picked over other.
		return 1;
	}

	public String getDate() {
		return date;
	} // retrieve the date the help request was submitted

	public String getName() {
		return name;
	} // retrieve the name of the person that submitted the help request

	public String getIP() {
		return ip;
	} // retrieve the IP address of the ticket's computer

	public String getProblem() {
		return problem;
	} // retrieves the issue type
}
