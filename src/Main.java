public class Main {	
	public static long time = System.currentTimeMillis();
	public static final int numberOfVisitors = 23;
	public static final int theaterCapacity = 5;
	public static final int partyTickets = 3;
	public static boolean closingSoon = false;
	public static int currentNumberOfVisitors = numberOfVisitors;
	public static boolean movieJustFinished = false;
	
	public static void main(String[] args) {
		Lobby lobby = Lobby.getLobby();
		Clock.time = time;
		Visitor.time = time;
		Speaker.time = time;
		
		if(args.length != 0) {
			currentNumberOfVisitors = Integer.parseInt(args[0]);
		}
		
		Clock clockThread = new Clock("Clock");
		Speaker speakerThread = new Speaker("Speaker");
		Visitor[] visitorThreads = new Visitor[numberOfVisitors];
		
		for(int i = 0; i < numberOfVisitors; ++i) {
			visitorThreads[i] = new Visitor("V"+(i+1));
		}
		
		//start the threads
		clockThread.start();
		speakerThread.start();
		for(int i = 0; i < numberOfVisitors; ++i) {
			visitorThreads[i].start();
		}
		
		while(!closingSoon) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(Visitor v : visitorThreads) {
			if(v == null) {
				continue;
			}
			if(v.isAlive()) {
				v.msg("Im still alive" );
				v.stop();
			}
		}
		
		try {
			speakerThread.join();
			clockThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
