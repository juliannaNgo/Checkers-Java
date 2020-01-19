import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 
 * @author sakur
 *
 */
public class Game {
	private Board board;
	private Player p1;
	private Player p2;
	private char curTurn; //explain this
	private boolean complete;
	private ArrayList<Entry<Integer, Integer>> blackSpaces; //explain this
	
	/**
	 * Constructs a new game object
	 */
	public Game() {
		this.board = new Board();
		p1 = new Player('x');
		p2 = new Player('o');
		this.curTurn = 'x';
		this.complete = false;
		
		blackSpaces = new ArrayList<Entry<Integer, Integer>>();
		//initializing a list of black spaces
		for (int i = 0; i <= 7; i++) { //for each row
			if (i % 2 == 0) { //for all odd-labeled rows
				for (int j = 0; j < board.width; j += 2) blackSpaces.add(new Entry(i, j));
			}
			else {
				for (int j = 1; j < board.width; j+=2) {
					blackSpaces.add(new Entry(i, j));
				}
			}
		}
	}
	
	
	public Player getOPlayer() {
		if (curTurn == 'x') return p2;
		return p1;
	}
	
	/**
	 * Returns the character representing the pieces of current turn's player
	 * @return x if p1's turn, o if p2's turn
	 */
	public char getCurTurn() {
		return this.curTurn;
	}
	
	/**
	 * Returns the boolean value dictating the end of the game
	 * @return true if the game is complete, false otherwise
	 */
	public boolean getComplete() {
		return this.complete;
	}
	
	/**
	 * Sets the boolean value dictating the end of the game to true
	 */
	public void setComplete() {
		this.complete = true;
	}
	
	/**
	 * Sets the character value representing the pieces of the current turn's player
	 * @param c character 
	 */
	public void setCurTurn(char c) {
		this.curTurn = c;
	}

	/**
	 * 
	 * @return
	 */
	public Board getBoard() {
		return this.board;
	}
	
	public Entry<Integer, Integer> setCapPiece (Entry<Integer, Integer> oldSpace, Entry<Integer, Integer> newSpace){
		//checks if the move is moving up or down a row
		Entry<Integer, Integer >capPiece = null;
		if (oldSpace.getKey() == newSpace.getKey()) {
			System.out.println("ENTERS HERE");
			return null;
		}
		if (newSpace.getKey() == oldSpace.getKey()+2) {
			//checks if the move is moving up 2 columns and the space between is an opposing piece
			if (newSpace.getValue() == oldSpace.getValue()+2 && board.getSpace(new Entry(oldSpace.getKey()+1, oldSpace.getValue()+1)) != curTurn) {
				capPiece = new Entry(oldSpace.getKey()+1, oldSpace.getValue()+1);
			}
			//checks if the move is moving down 2 columns
			else if (newSpace.getValue() == oldSpace.getValue()-2 && board.getSpace(new Entry(oldSpace.getKey()+1, oldSpace.getValue()-1)) != curTurn) {
				capPiece = new Entry(oldSpace.getKey()+1, oldSpace.getValue()-1);
			}
		}
		//checks is the move is moving down 2 rows
		else if (newSpace.getKey() == oldSpace.getKey()-2) {
			//checks if the move is moving up 2 columns and the space between is an opposing piece
			if (newSpace.getValue() == oldSpace.getValue()+2 && board.getSpace(new Entry(oldSpace.getKey()-1, oldSpace.getValue()+1)) != curTurn) {
				capPiece = new Entry(oldSpace.getKey()-1, oldSpace.getValue()+1);
			}
			//checks if the move is moving down 2 columns and the space between is an opposing piece
			else if (newSpace.getValue() == oldSpace.getValue()-2 && board.getSpace(new Entry(oldSpace.getKey()-1, oldSpace.getValue()-1)) != curTurn) {
				capPiece = new Entry(oldSpace.getKey()-1, oldSpace.getValue()-1);
			}
		}
		return capPiece;
	}
	
	public boolean isDiag1(Entry<Integer, Integer> oldSpace, Entry<Integer, Integer> newSpace) {
		//checks if the move is moving up or down a row
		if (newSpace.getKey() == oldSpace.getKey()+1 || newSpace.getKey() == oldSpace.getKey()-1) {
			//checks if the move is moving up or down a column
			if (newSpace.getValue() == oldSpace.getValue()+1 || newSpace.getValue() == oldSpace.getValue() || newSpace.getValue() == oldSpace.getValue()-1) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Determines whether a player's inputed move was valid and updates entry with board indices if move is valid.
	 * Outputs an error message and returns false if input is invalid
	 * @param input the user's input
	 * @param entry entry to be updated with the input's corresponding integer indices
	 * @param noo new or old position being tested for validation (new = true, old = false)
	 * @return true if the move is valid, false otherwise
	 */
	public boolean isValidMove(boolean noo, Entry<Integer, Integer> entry) {
		//checks if the piece being moved (old) belongs to current player
		if (!blackSpaces.contains(entry)) {
			System.out.println("Invalid black space selected\nPlease try again");
			return false;
		}
		if (noo == false && board.getSpace(entry) != curTurn) {
			System.out.println("Selected piece does not belong to current player\nPlease try again");
			return false;
		}
		// checks if the space the piece is being moved to is empty
		if (noo == true && board.getSpace(entry) != ' '){
			System.out.println("Selected space is currently occupied\nPlease try again");
			return false;
		}
		return true;
	}

	public Entry<Integer, Integer> getIntIntEntry(String input){
		//checks for proper input length
		Entry<Integer, Integer> temp = null;
		if (input.length() != 2) {
			System.out.println("Player input must have length of 2\nPlease try again");
			return temp;
		}
		//checks if move is in letterNumber format (e.x: A8) and updates e if so
		if (Character.isDigit(input.charAt(1)) && Character.isLetter(input.charAt(0))) {
			temp = new Entry(Integer.parseInt(Character.toString(input.charAt(1)))-1, getIntIndex(input.charAt(0)));
		}
		//checks if move is in numberLetter format (e.x: 8A) and updates e if so
		else if (Character.isDigit(input.charAt(0)) && Character.isLetter(input.charAt(1))) {
			temp = new Entry(Integer.parseInt(Character.toString(input.charAt(0)))-1, getIntIndex(input.charAt(1)));
		}
		else {
			System.out.println("Player input must be in either letter-number or number-letter format\nPlease try again");
		}
		return temp;
	}

	/**
	 * Helper method that converts character coordinates to their respective integer indices
	 * @param c character coordinate (top labels)
	 * @return integer index of the character, -1 if the character is not a label on the board
	 */
	public int getIntIndex(Character c) {
		int i = 0;
		boolean comp = false;
		while (!comp && i < board.width) {
			if (Character.toLowerCase(c) == "abcdefg".charAt(i)) return i;
			i++;
		}
		return -1;
	}

}
