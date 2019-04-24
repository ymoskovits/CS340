public class Visitor extends Thread {
	public static long time;
	public boolean seenMovie = false;
	public boolean inMovie = false;
	
	public Visitor(String name) {
		super();
		setName(name);
	}
	
	public boolean findSeat() { 
		Theater theater = Theater.getTheater();
		if(theater.takeSeat()) {
			//set the in movie variable.
			//sleep here?? and watch the movie??
			inMovie = true;
			try {
				Thread.sleep(1000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				msg("Someone interrupted me. Is the movie over? I better go.");
				//this is where we can add the ticket stuff maybe
//				theater.findGroup();
			}
			seenMovie = true;
			inMovie = false;
			return true;
		}
		return false;
	}
	
	public synchronized void fromLobbyToTheater() {
		Lobby lobby = Lobby.getLobby();
		Theater theater = Theater.getTheater();
		lobby.visitorExited();
		theater.visitorEntered();
	}
	
	public synchronized void fromTheaterToLobby() {
		Lobby lobby = Lobby.getLobby();
		Theater theater = Theater.getTheater();
		theater.visitorExited();
		lobby.visitorEntered();
	}

	public void run() {	
		//enter the lobby
		Lobby lobby = Lobby.getLobby();
		Theater theater = Theater.getTheater();
		
		lobby.visitorEntered();

		
		while(!seenMovie && !Main.closingSoon) { // there can be a synch func to check and enter the doors
			if(!theater.seatsAreFull()) {
				//try to find a seat
				fromLobbyToTheater();
				if(findSeat()) {
					fromTheaterToLobby();
					//now the movies over and we do ticket thing		
					//get into a group
					//grab a ticket
					//exit theater
				}
				else {
					Thread.yield();
					fromTheaterToLobby();
				}			
			}
		}
	}
	
	public void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}

}
