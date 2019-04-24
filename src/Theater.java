import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Theater {
	private int visitorCount = 0;
	private static boolean theaterCreated = false;
	private static Theater obj;
	private int availableSeats;
	private boolean movieInProgress;
	private int numberOfShowingsLeftForToday = 4;
	
	private Visitor[] seats;
	private ArrayList<Set<Visitor>> ticketGroups;

	
	private Theater() {
		availableSeats = Main.theaterCapacity;
		movieInProgress = false;
		seats = new Visitor[Main.theaterCapacity];
		theaterCreated = true;
		obj = this;
	}
	
	public static synchronized Theater getTheater() {
		if(theaterCreated) {
			return obj;
		}
		else {
			return new Theater();
		}
	}

	public synchronized boolean isEmpty() {
		if(visitorCount == 0) {
			return true;
		}
		return false;
	}
	
	public synchronized boolean seatsAreFull() {
		if(availableSeats == 0) {
			return true;
		}
		return false;
	}
	
	public synchronized boolean peopleAreInTheaterLookingForSeats() {
//		System.out.println(visitorCount);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(visitorCount > Main.theaterCapacity - availableSeats) {
			return true;
		}
		return false;
	}
	
	public synchronized void visitorExited() {
		visitorCount--;
		((Visitor) Thread.currentThread()) .msg("Leaving Theater");
	}
	
	public synchronized void visitorEntered() {
		visitorCount++;
		((Visitor) Thread.currentThread()) .msg("Entering Theater");
	}
	
	public synchronized boolean takeSeat() {
		if(availableSeats > 0) {
			availableSeats--;
			seats[availableSeats] = (Visitor) Thread.currentThread();
			((Visitor) Thread.currentThread()) .msg("Took seat " + availableSeats + ". Ready to watch movie.");
			return true;
		}
		return false;
	}
	
	public synchronized boolean leaveMovieChair() {
		Visitor v = seats[availableSeats];
		if(v == null) {
			return false;
		}
		v.interrupt();
		v.msg("Sending another interrupt notif from the leaveMovieChair method in Theater class.");
		availableSeats++;
		return true;
	}
	
	public synchronized void setMovieInProgress(boolean p) {
		movieInProgress = p;
	}
	
	public synchronized boolean getMovieInProgress() {
		return movieInProgress;
	}

	public synchronized int getNumberOfShowingsLeftForToday() {
		return numberOfShowingsLeftForToday;
	}

	public synchronized void setNumberOfShowingsLeftForToday(int numberOfShowingsLeftForToday) {
		this.numberOfShowingsLeftForToday = numberOfShowingsLeftForToday;
	}
	
	public synchronized void wakeUpViewers() {
		for(Visitor v : seats) {
			if(v != null) {
				availableSeats++;
				v.interrupt();
			}
		}
	}
	
	public synchronized boolean stillShowsLeft() {
		if(numberOfShowingsLeftForToday > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public synchronized void findGroup() {
		if(ticketGroups == null) {
			ticketGroups = new ArrayList<Set<Visitor> >();
			Set<Visitor> vSet = new HashSet<Visitor>();
			vSet.add((Visitor) Thread.currentThread());
			ticketGroups.add(vSet);
		}
		else if( ticketGroups.get( ticketGroups.size() - 1) .size() == Main.partyTickets)  {
			Set<Visitor> vSet = new HashSet<Visitor>();
			vSet.add((Visitor) Thread.currentThread());
			ticketGroups.add(vSet);
		}
		else {
			ticketGroups.get( ticketGroups.size() - 1).add((Visitor) Thread.currentThread());
		}
		((Visitor) Thread.currentThread()) .msg("Found a spot in group " + ticketGroups.size() + ".");
	}
	
}
