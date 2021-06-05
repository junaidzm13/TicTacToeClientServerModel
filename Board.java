import java.util.ArrayList;

/**
 * This class represents a board represented by an ArrayList&lt;String&gt;, and is used
 * to immitate a 3x3 TicTacToe Board.
 * 
 * @author Junaid Zubair
 * @version 1.0
 */
public class Board {
	private ArrayList<String> board;
	
	/**
	 * No argument constructor that creates an empty Board.
	 */
	public Board() {
		board = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			board.add("");
		}
	}
	
	
	/**
	 * A getter method, that returns this Board's instance variable board of type
	 * ArrayList&lt;String&gt;.
	 * 
	 * @return board instance variable that represents a board as an ArrayList&lt;String&gt;.
	 */
	public ArrayList<String> getBoard(){
		return board;
	}
	
	
	/**
	 * Takes an integer index and a String marker, and replaces the String object at the given index 
	 * of this Board by marker.
	 * 
	 * @param index represents the position where the marker needs to be placed.
	 * @param marker represents the marker that needs to be set at the given index.
	 */
	public void set(int index, String marker) {
		board.set(index, marker);
	}
	
	/**
	 * A method that takes a String marker, and adds it to the end of this Board.
	 * 
	 * @param marker needs to be added at the end of this Board.
	 */
	public void add(String marker) {
		board.add(marker);
	}
	
	/**
	 * A method that takes an integer index, and returns the String object at this index of this Board.
	 * 
	 * @param index representing the index of the String object that needs to be returned.
	 * @return String that represents the marker at the given index of this Board.
	 */
	public String get(int index) {
		return board.get(index);
	}
	
	
	/**
	 * A method that returns true if at least one of the two players have won, otherwise returns false.
	 * 
	 * @return true if at least one of the two players have won in the tic tac toe game, otherwise false.
	 */
	public boolean playerWon() {
		// columns
		for(int i = 0; i < 3; i++)
			if(areEqual(i, i+3, i+6) && notEmpty(i)) return true;
		
		// diagonals
		if(areEqual(0, 4, 8) && notEmpty(0)) return true;
		if(areEqual(2, 4, 6) && notEmpty(2)) return true;
		
		//rows
		for(int j = 0; j < 7; j += 3)
			if(areEqual(j, j+1, j+2) && notEmpty(j)) return true;
		
		return false;
	}
	
	private boolean areEqual(int i1, int i2, int i3) {
		return board.get(i1).equals(board.get(i2))
				&& board.get(i1).equals(board.get(i3));
	}
	
	private boolean notEmpty(int i) {
		return !board.get(i).equals("");
	}
	
	
	/**
	 * A method that returns true if the board is completely filled with players' markers, 
	 * and no empty space is available, otherwise it returns false.
	 * 
	 * @return true if the board has no empty spaces, otherwise false.
	 */
	public boolean boardFull() {
		for(int i = 0; i < 9; i++) {
			if(board.get(i).equals("")) return false;
		}
		return true;
	}
	
	
}