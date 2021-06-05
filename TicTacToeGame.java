import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * This class represents the client side of the TicTacToe Game and contains the required logic
 * to connect to the Server using Sockets and send and receive messages simultaneously as the
 * game progresses.
 * 
 * @author Junaid Zubair
 * @version 1.0
 */
public class TicTacToeGame {

	private final static int portNumber = 5000;
	private final static String IPADDRESS = "127.0.0.1";
	
	private String playerMarker, otherPlayerMarker;
	private Color playerMarkerColor, otherPlayerMarkerColor;
	private Socket socket;
	private PrintWriter writer;
	private InputStreamReader streamReader;
	private BufferedReader reader;
	private View v;
	
	
	/**
	 * This is the main method where the execution of the client program start. It creates
	 * an instance of this class and calls its method start().
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		TicTacToeGame game = new TicTacToeGame();
		game.start();
	}
	
	
	private void start() {
		v = new View();
		for(int i = 0; i < 9; i++)
			v.getBoard().get(i).addActionListener(new BoardButton());
		v.getSubmitButton().addActionListener(new SubmitButton());
	}
	
	
    private void startGame() {
        String response;
        
        try {
            response = reader.readLine();
            if (response.startsWith("PLAYER")) {
                playerMarker = response.substring(7, 8);
                playerMarkerColor = (playerMarker.equals("x")? Color.GREEN : Color.RED); 
                otherPlayerMarkerColor = (playerMarker.equals("x")? Color.RED : Color.GREEN);
                otherPlayerMarker = (playerMarker.equals("x")? "o" : "x");
//                System.out.println(playerMarker);
            }
            
            
            while (true) {
                response = reader.readLine();
//                System.out.println(response);
                
                if (response.startsWith("ALL")) {
                	for(int i = 0; i < 9; i++) {
    					v.getBoard().get(i).setEnabled(true);
    				}
                    if (playerMarker.equals("x")) v.setInfoLabel(String.format(
                    		"WELCOME %s. Your move.", v.getPlayerName()));
                    else v.setInfoLabel(String.format(
                    		"WELCOME %s. Wait for your opponent to move.", v.getPlayerName()));
                }
                
                else if(response.startsWith("LEGAL_MOVE")) {
                	v.getCurrentButton().setForeground(playerMarkerColor);
                	v.getCurrentButton().setText(playerMarker);
  					v.setInfoLabel("Valid move, wait for your opponent.");
                }
                
                else if (response.startsWith("OTHER_PL_MOVED")) {
                	int index= Integer.parseInt(response.substring(15));
                    v.getBoard().get(index).setText(otherPlayerMarker);
                    v.getBoard().get(index).setForeground(otherPlayerMarkerColor);
        			v.setInfoLabel("Your opponent has moved, now is your turn");
                } 
                
                else if (response.startsWith("WIN")) {
                	JOptionPane.showMessageDialog(v.getFrame(), "Congratulations! You WIN!", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
					break;
                }
                
                else if (response.startsWith("LOSE")) {
                	JOptionPane.showMessageDialog(v.getFrame(), "You LOSE! :(", 
    						"Message", JOptionPane.INFORMATION_MESSAGE);
    				break;
                } 
                
                else if (response.startsWith("DRAW")) {
                	JOptionPane.showMessageDialog(v.getFrame(), "DRAW!", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                
                else if (response.startsWith("CANT")) {}
                
                else if (response.startsWith("LEFT")) {
                	JOptionPane.showMessageDialog(v.getFrame(), "Game Ends. One of the players left.", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
                	break;
                }
                
                else continue;
            }
            writer.println("QUIT");
	    v.getFrame().dispatchEvent(new WindowEvent(v.getFrame(), WindowEvent.WINDOW_CLOSING));
        }
        catch (Exception e) {e.printStackTrace();}
    }
	
	
	private class BoardButton implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			v.setCurrentButton((JButton) e.getSource());
			int j = v.getBoard().indexOf(v.getCurrentButton());
//			System.out.println("Move " + j);
			writer.println("MOVE " + j);		
		}
	}
	
	private class SubmitButton implements ActionListener{	
		public void actionPerformed(ActionEvent e) {
			v.setPlayerName(v.getNameField().getText());
			v.setInfoLabel(String.format("WELCOME %s", v.getPlayerName()));
			v.getFrame().setTitle(String.format("Tic Tac Toe-Player: %s", v.getPlayerName()));
			v.getSubmitButton().setEnabled(false); v.getNameField().setEnabled(false);
			
			
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						socket = new Socket(IPADDRESS, portNumber);
						streamReader = new InputStreamReader(socket.getInputStream());
						reader = new BufferedReader(streamReader);
						writer = new PrintWriter(socket.getOutputStream(), true);}
					catch(Exception e) {}
					startGame();
				}
			});
			t.start();
		}
	}

}
