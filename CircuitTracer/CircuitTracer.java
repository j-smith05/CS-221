import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail and Jacob Smith
 */
public class CircuitTracer {

	/** Launch the program. 
	 * 
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args);
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		System.out.println("To Run The Program Please Follow The Following Input Structure:");
		System.out.println("<-StorageType> <-ProgramMode> <InputFileName>");
		System.out.println("Storage Type Options:");
		System.out.println("-s -- uses a stack, DFS");
		System.out.println("-q -- uses a queue, BFS");
		System.out.println("Program Mode Options:");
		System.out.println("-c -- runs program in the console");
		System.out.println("-g -- runs program in a GUI");
		System.out.println("Example:");
		System.out.println("java CircuitTracer -s -c inputFile.dat");
	}

	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		if (args.length != 3) {
			printUsage();
			return;
		}

		// ------- storage type selection --------
		Storage<TraceState> stateStore;
		if (args[0].equals("-s")) {
			stateStore = Storage.getStackInstance();
		} else if (args[0].equals("-q")) {
			stateStore = Storage.getQueueInstance();
		} else {
			printUsage();
			return;
		}

		// -------- output mode selection --------
		if (args[1].equals("-g")) {
			System.out.println("GUI not available.");
			return;
		} else if (!args[1].equals("-c")) {
			printUsage();
			return;
		}

		// Loads board from a file
		CircuitBoard board;
		try {
			board = new CircuitBoard(args[2]);
		} catch (InvalidFileFormatException e) {
			System.out.println("InvalidFileFormatException");
			return;
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			return;
		}

		// Initializes search 
		ArrayList<TraceState> bestPaths = new ArrayList<>();
		int bestLength = Integer.MAX_VALUE;

		Point start = board.getStartingPoint();
		int r = start.x;
		int c = start.y;

		if (board.isOpen(r - 1, c)) stateStore.store(new TraceState(board, r - 1, c));
		if (board.isOpen(r + 1, c)) stateStore.store(new TraceState(board, r + 1, c));
		if (board.isOpen(r, c - 1)) stateStore.store(new TraceState(board, r, c - 1));
		if (board.isOpen(r, c + 1)) stateStore.store(new TraceState(board, r, c + 1));

		// Search for best paths
		while (!stateStore.isEmpty()) {
			TraceState current = stateStore.retrieve();

			if (current.pathLength() > bestLength) {
				continue;
			}

			if (current.isSolution()) {
				int len = current.pathLength();

				if (len < bestLength) {
					bestPaths.clear();
					bestLength = len;
				}
				bestPaths.add(current);
			} 
			else {
				int cr = current.getRow();
				int cc = current.getCol();

				if (current.isOpen(cr - 1, cc)) {
					stateStore.store(new TraceState(current, cr - 1, cc));
				}
				if (current.isOpen(cr + 1, cc)) {
					stateStore.store(new TraceState(current, cr + 1, cc));
				}
				if (current.isOpen(cr, cc - 1)) {
					stateStore.store(new TraceState(current, cr, cc - 1));
				}
				if (current.isOpen(cr, cc + 1)) {
					stateStore.store(new TraceState(current, cr, cc + 1));
				}
			}
		}

		// Print best paths found
		System.out.println("Best paths found (length " + bestLength + "):");
		for (TraceState path : bestPaths) {
			System.out.print(path);
			System.out.println();
		}
	}
}