import java.util.Set;

public class Clock extends Thread {

	public static long time;
	public static long mToS = 1000;
	
	public Clock(String name) {
		super();
		setName(name);
	}
	
	public void run() {
		Lobby lobby = Lobby.getLobby();
		Theater theater = Theater.getTheater();
		
		while(theater.getNumberOfShowingsLeftForToday() > 0) {
			//give sufficient time for people to enter.
			msg("Will now sleep for 2 seconds to allow people to find seats.");
			try {
				Thread.sleep(2*mToS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println("Line 20");
			//kick losers out before movie starts
			while(theater.peopleAreInTheaterLookingForSeats() ) {
				msg("Line 23");
				//busy wait for everyone to exit.
				
			}

			//close the doors and start the movie
			theater.setMovieInProgress(true);
			//sleep for time of movie
			msg("Movie is about to start!!!");			
			
			try { 
				Thread.sleep(10 * mToS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//wake up and alert the speaker
			Main.movieJustFinished = true;
			
			while(Main.movieJustFinished) {
				//let the people leave the theater
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				msg("Movie is still in just finished state");
			}
			

			//change numberofShowingsLeft
			theater.setNumberOfShowingsLeftForToday(theater.getNumberOfShowingsLeftForToday() - 1);
			msg("There are only " + theater.getNumberOfShowingsLeftForToday() + " showings left for today.");
		}
		msg("The final presentation has completed. The Museum will be closing shortly.");
		Main.closingSoon = true;
		
	}

	public void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
}
