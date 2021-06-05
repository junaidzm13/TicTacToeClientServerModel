
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * This class contains all the GUI logic for the client side of the Tic Tac Toe game.
 * 
 * @author Junaid Zubair
 * @version 1.0
 */
public class View {

	private ArrayList<JButton> board;
	private JButton currentButton;
	private JFrame frame;
	private JLabel infoLabel;
	private JButton submitButton;
	private JTextField nameField;
	private String playerName;
	
	
	/**
	 * The no argument constructor, that initializes most instance variables and contains
	 * all the main logic for the GUI design.
	 */
	public View() {
		JPanel centerPanel = new JPanel(new GridLayout(3, 3));
		
		board = new ArrayList<JButton>(9);
		for(int i = 0; i < 9; i++) {		
			board.add(new JButton(""));
			board.get(i).setBackground(Color.white);
			board.get(i).setBorder(BorderFactory.createLineBorder(Color.black, 2));
			board.get(i).setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
			board.get(i).setFocusPainted(false);
			board.get(i).setEnabled(false);
			
			centerPanel.add(board.get(i));
		}
		
		
		// north panel containing the label
		JPanel northPanel = new JPanel();
		infoLabel = new JLabel("Enter your player name...");
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(infoLabel);
		
		
		// south panel containing the submit button and name field
		JPanel southPanel = new JPanel();
		nameField = new JTextField(20);
		submitButton = new JButton("Submit");
//		submitButton.addActionListener(new SubmitButton());
		southPanel.add(nameField);
		southPanel.add(submitButton);
		
		
		// MenuBar
		JMenuBar menuBar = new JMenuBar();
		JMenu controlMenu = new JMenu("Control");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		JMenuItem instMenuItem = new JMenuItem("Instructions");
		exitMenuItem.addActionListener(new ExitMenuItem());
		instMenuItem.addActionListener(new HelpMenu());		
		controlMenu.add(exitMenuItem); helpMenu.add(instMenuItem);
		menuBar.add(controlMenu); menuBar.add(helpMenu);
		
		
		// frame
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Tic Tac Toe");
		
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.add(northPanel, BorderLayout.NORTH);
		frame.setJMenuBar(menuBar);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	
//	private class SubmitButton implements ActionListener{	
//		public void actionPerformed(ActionEvent e) {
//			playerName = nameField.getText();
//			infoLabel.setText(String.format("WELCOME %s", playerName));
//			frame.setTitle(String.format("Tic Tac Toe-Player: %s", playerName));
//			submitButton.setEnabled(false); nameField.setEnabled(false);
//		}
//	}	
	
	/**
	 * This class implements an ActionListener interface that is registered with
	 * the JMenuItem instMenuItem within the JMenu helpMenu in the GUI.
	 * 
	 * @author Junaid Zubair
	 * @version 1.0
	 */
	public class HelpMenu implements ActionListener{
		
		/**
		 * This method inherited from the ActionListener interface, takes an event,
		 * and displays a message dialogue that shows the description of the game
		 * when the event occurs.
		 * 
		 * @param event represents the event that occurred.
		 */
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(frame, 
					"Some information about the game:\n"
					+ "  Criteria for a valid move:\n"
					+ "    - The move is not occupied by any mark\n"
					+ "    - The move is made in the player's turn\n"
					+ "    - The move is made within the 3 x 3 board\n"
					+ "  The game would continue and switch among the opposite player until it reaches either one of the following conditions:\n"
					+ "    - Player 1 wins\n"
					+ "    - Player 2 wins\n"
					+ "    - Draw\n",
		               "Help", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	/**
	 * This class implements an ActionListener interface that is registered with
	 * the JMenuItem exitMenuItem within the JMenu controlMenu in the GUI.
	 * 
	 * @author Junaid Zubair
	 * @version 1.0
	 */
	public class ExitMenuItem implements ActionListener{
		
		/**
		 * This method inherited from the ActionListener interface, takes an event,
		 * and closes the window containing the GUI when this event occurs.
		 * 
		 * @param event represents the event that occurred.
		 */
		public void actionPerformed(ActionEvent event) {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	
	/**
	 * A getter method that returns the JLabel instance variable infoLabel.
	 * 
	 * @return JLabel infoLabel representing the information label placed at the top of the GUI just below the menu bar.
	 */
	public JLabel getInfoLabel() {
		return infoLabel;
	}
	
	
	/**
	 * A setter method that sets the text of the JLabel instance variable infoLabel to String s.
	 * 
	 * @param s representing the new text that needs to be set for the infoLabel.
	 */
	public void setInfoLabel(String s) {
		infoLabel.setText(s);
	}

	
	/**
	 * A getter method that returns the frame instance variable of type JFrame.
	 * 
	 * @return JFrame frame representing the frame of the GUI.
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	
	/**
	 * A getter method that returns the board instance variable of type ArrayList&lt;JButton&gt;.
	 * 
	 * @return ArrayList&lt;JButton&gt; board representing the board of the tictactoe game.
	 */
	public ArrayList<JButton> getBoard(){
		return board;
	}
	
	
	/**
	 * A getter method that returns the submitButton instance variable representing the Submit Button on the GUI.
	 * 
	 * @return JButton representing the Submit Button on the GUI.
	 */
	public JButton getSubmitButton(){
		return submitButton;
	}
	
	
	/**
	 * A getter method that returns the nameField instance variable representing the name text field on the GUI.
	 * 
	 * @return JTextField representing the name text field on the GUI.
	 */
	public JTextField getNameField(){
		return nameField;
	}
	
	
	/**
	 * A getter method that returns the playerName instance variable representing the name of the player.
	 * 
	 * @return String playerName representing the name of the player.
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	
	/**
	 * A setter method that sets the value of the playerName instance variable representing the name of the player.
	 * 
	 * @param name representing the new name of the player.
	 */
	public void setPlayerName(String name) {
		playerName = name;
		}
	
	
	/**
	 * A getter method that returns the currentButton instance variable representing the button on the board
	 * most recently pressed by the player.
	 * 
	 * @return JButton currentButton representing button on the board pressed latest.
	 */
	public JButton getCurrentButton() {
		return currentButton;
	}
	
	
	/**
	 * A setter method that sets the value of the currentButton instance variable by assigning the reference 
	 * of the JButton btn to it
	 * 
	 * @param btn whose reference needs to be assigned to the currentButton.
	 */
	public void setCurrentButton(JButton btn) {
		currentButton = btn;
	}
}