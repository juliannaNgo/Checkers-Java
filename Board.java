//Board.java by Julianna Ngo
//Last Updated: Dec 2019
import java.util.ArrayList;
import java.util.Map;

public class Board {
	static final int len = 8;
	static final int width = 8;
	private Character[][] board;

	public Board () {
		board = new Character[len][width];
		//Initializing board values
		for (int i = len-1; i >= 0; i--) { // rows/numbers
			for (int j = 0; j < width ; j++) { // columns/letters
				if (i == 3 || i == 4) board [i][j] = ' '; //empty rows 4 and 5
				else if (i > 4 && i % 2 == 0 && j % 2 == 0) board[i][j] = 'x';
				else if (i > 4 && i % 2 != 0 && j % 2 != 0) board[i][j] = 'x';
				else if (i < 3 && i % 2 == 0 && j % 2 == 0) board[i][j] = 'o';
				else if (i < 3 && i % 2 != 0 && j % 2 != 0) board[i][j] = 'o';
				else board[i][j] = ' ';
			}
		}
	}
	
	/**
	 * Initializes a deep copy of a Board object
	 * @param b
	 */
	public Board(Board b) {
		for (int i = len-1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {
				this.board[i][j] = b.getSpace(new Entry(i, j));
			}
		}
	}
	
	/**
	 * Helper method to check whether a moved piece needs to be updated to a King piece, then updated
	 * @param pos position where piece was just moved to (new position)
	 * @param curTurn piece type (x or o) of the current player
	 */
	public void updateToKing(Entry<Integer, Integer> pos, char curTurn) {
		//checking that the 
		if (this.getSpace(pos) == 'x' && pos.getKey() == 0) {
			setSpace(pos, 'X');
		}
		else if (getSpace(pos) == 'o' && pos.getKey() == 7) {
			setSpace(pos, 'O');
		}
	}
	
	/**
	 * Returns the character representing 
	 * @param e
	 * @return
	 */
	public char getSpace(Entry<Integer, Integer> e){
		return this.board[e.getKey()][e.getValue()];
	}

	/**
	 * Method that moves a piece by updating the board
	 * @param oldPos position of piece that will be moved
	 * @param newPos position of space where piece is to be moved to
	 */
	public void movePiece(Entry<Integer, Integer> oldPos, Entry<Integer, Integer> newPos, char curTurn) {
		this.board[newPos.getKey()][newPos.getValue()] = this.board[oldPos.getKey()][oldPos.getValue()];
		this.board[oldPos.getKey()][oldPos.getValue()] = ' ';
		this.updateToKing(newPos, curTurn);
	}
	
	public void setSpace(Entry<Integer, Integer> pos, char c) {
		this.board[pos.getKey()][pos.getValue()] = c;
	}
	
	public String toString() {
		String temp = "    A   B   C   D   E   F   G   H \n";
		temp += "  ---------------------------------\n";
		for (int i = len-1; i >= 0; i--) {
			temp += i+1;
			for (int j = 0; j < width; j++) {
				temp += " | " + board[i][j];
			}
			temp += " |\n  ---------------------------------\n";
		}
		return temp;
	}
}
