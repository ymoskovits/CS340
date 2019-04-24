public class Speaker extends Thread {
	public static long time;
	
	public Speaker(String name) {
		super();
		setName(name);
	}

	public void run() {
		Lobby lobby = Lobby.getLobby();
		Theater theater = Theater.getTheater();
		
		while(!Main.closingSoon) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(Main.movieJustFinished) {
				
				msg("I see that the movie Just Ended");
				theater.wakeUpViewers();
				//give them tickets
				//let them leave in alpha order
				//anounce the next movie showtime or whatever
				Main.movieJustFinished = false;
			}
			else {

			}
		}
	}
	
	public void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
}
