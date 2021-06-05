import java.io.*;
import java.net.*;

/**
 * This class represents the only server of the TicTacToe Game and contains the required logic
 * to connect to the Clients using Sockets and send and receive messages simultaneously as the
 * game progresses.
 * 
 * @author Junaid Zubair
 * @version 1.0
 */
public class TicTacToeServer {

	private ServerSocket serverSock1;
	private Board board;
	private PlayerR currentPlayer;
	
	
	/**
	 * This is the main method where the execution of the server program start. It creates
	 * an instance of this class and calls its method go().
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TicTacToeServer server = new TicTacToeServer();
		server.go();
	}
	
	
	private void go() {
		
		board = new Board();
		
		try {
			serverSock1 = new ServerSocket(5000);		
			
			Socket s1 = serverSock1.accept();
			Socket s2 = serverSock1.accept();
			
			PlayerR playerXRunnable = new PlayerR(s1, "x");
			PlayerR playerORunnable = new PlayerR(s2, "o");
			
			playerXRunnable.setOtherPlayer(playerORunnable);
			playerORunnable.setOtherPlayer(playerXRunnable);
			
			currentPlayer = playerXRunnable;
			
			Thread t1 = new Thread(playerXRunnable);
			Thread t2 = new Thread(playerORunnable);
			t1.start(); t2.start();
			
		}
		catch (Exception ex) {
				ex.printStackTrace();
		}
	}
	
	private class PlayerR implements Runnable {
		private Socket socket;
		private PrintWriter writer;
		private InputStreamReader streamReader;
		private BufferedReader reader;
		private String playerMarker;
		private PlayerR otherPlayer;
		
		public PlayerR(Socket socket, String playerMarker) {
			this.socket = socket;
			this.playerMarker = playerMarker;
			
			try {
				streamReader = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(streamReader);
				writer = new PrintWriter(socket.getOutputStream(), true);
				writer.println("PLAYER " + playerMarker);
			}
			catch (Exception e) {e.printStackTrace();}	
		}
		
		public void run() {
			
			try {
                writer.println("ALL");

                // Continuously get commands from the player and process them accordingly
                while (true) {
                    String command = reader.readLine();
                    
                	if (command.startsWith("MOVE")) {
                        int index = Integer.parseInt(command.substring(5));
                        if (moveIsLegal(this, index)) {
                            writer.println("LEGAL_MOVE");
                            if (board.playerWon()) writer.println("WIN");
                            else if (board.boardFull()) writer.println("DRAW");
                            else writer.println("");
                        }       
                        else writer.println("CANT_MOVE");
                    } 
                	
                	else if (command.startsWith("QUIT")) {
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Player Disconnected: " + e);
            } finally {
            	otherPlayer.playerLeft();
            }
		}
		
		public void setOtherPlayer(PlayerR o) {
			otherPlayer = o;
		}
		
		public PlayerR getOtherPlayer() {
			return otherPlayer;
		}
		
		public void playerLeft() {
			writer.println("LEFT");
		}
		
		public void otherPlayerMoved(int index) {
			writer.println("OTHER_PL_MOVED " + index);
			if (board.playerWon()) writer.println("LOSE");
            else if (board.boardFull()) writer.println("DRAW");
            else writer.println("");
		}
		
		public boolean moveIsLegal(PlayerR p, int index) {
	        if (p.equals(currentPlayer) && board.get(index).equals("")) {
	            board.set(index, playerMarker);
	            currentPlayer = currentPlayer.getOtherPlayer();
	            currentPlayer.otherPlayerMoved(index);
	            return true;
	        } else return false;
	    }
		
	}
	
}