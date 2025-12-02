import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O';	//capital 'o', an open position
	private final char CLOSED = 'X';//a blocked position
	private final char TRACE = 'T';	//part of the trace connecting 1 to 2
	private final char START = '1';	//the starting component
	private final char END = '2';	//the ending component
	private final String ALLOWED_CHARS = "OXT12"; //useful for validating with indexOf

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot open or read the file
	 * @throws InvalidFileFormatException for any file formatting or content issue
	 */
public CircuitBoard(String filename) throws FileNotFoundException {
    Scanner fileScan = new Scanner(new File(filename));
    
    try {
        if (!fileScan.hasNextInt()) {
            throw new InvalidFileFormatException("First line must contain number of rows and columns.");
        }
        int rows = fileScan.nextInt();

        if (!fileScan.hasNextInt()) {
            throw new InvalidFileFormatException("First line must contain two integers: rows and columns.");
        }
        int cols = fileScan.nextInt();

        if (rows <= 0 || cols <= 0) {
            throw new InvalidFileFormatException("Rows and columns must be positive.");
        }

        ROWS = rows;
        COLS = cols;

        if (fileScan.hasNextLine()) {
            fileScan.nextLine();
        }

        board = new char[ROWS][COLS];
        int startCount = 0;
        int endCount = 0;

        for (int r = 0; r < ROWS; r++) {
            if (!fileScan.hasNextLine()) {
                throw new InvalidFileFormatException("Not enough rows of board data. Expected " + ROWS);
            }

            String line = fileScan.nextLine().trim().replaceAll("\\s+", "");

            if (line.length() != COLS) {
                throw new InvalidFileFormatException(
                    "Row " + r + " has length " + line.length() + " but expected " + COLS
                );
            }

            for (int c = 0; c < COLS; c++) {
                char ch = line.charAt(c);

                if (ALLOWED_CHARS.indexOf(ch) == -1) {
                    throw new InvalidFileFormatException(
                        "Invalid character '" + ch + "' at row " + r + ", col " + c
                    );
                }

                if (ch == START) {
                    startingPoint = new Point(r, c);
                    startCount++;
                } else if (ch == END) {
                    endingPoint = new Point(r, c);
                    endCount++;
                }

                board[r][c] = ch;
            }
        }

        while (fileScan.hasNextLine()) {
            if (!fileScan.nextLine().trim().isEmpty()) {
                throw new InvalidFileFormatException("Extra data found after board rows.");
            }
        }

        if (startCount != 1 || endCount != 1) {
            throw new InvalidFileFormatException(
                "Board must contain exactly one '" + START + "' and one '" + END + "'. " +
                "Found " + startCount + " start(s), " + endCount + " end(s)."
            );
        }

    } finally {
        fileScan.close();
    }
}

	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** Utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
