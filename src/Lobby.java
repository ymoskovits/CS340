public class Lobby {
	private int visitorCount = Main.numberOfVisitors;
	private static boolean lobbyCreated = false;
	private static Lobby obj;
	
	private Lobby() {
		lobbyCreated = true;
		obj = this;
	}
	
	public static synchronized Lobby getLobby() {
		if(lobbyCreated) {
			return obj;
		}
		else {
			return new Lobby();
		}
	}
	
	public boolean isEmpty() {
		if(visitorCount == 0) {
			return true;
		}
		return false;
	}
	
	public synchronized void visitorExited() {
		visitorCount--;
		((Visitor) Thread.currentThread()) .msg("Leaving Lobby");
	}
	
	public synchronized void visitorEntered() {
		visitorCount++;
		((Visitor) Thread.currentThread()) .msg("Entering Lobby");
	}
}