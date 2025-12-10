****************
* CircuitTracer
* CS 221
* 12/10/2025
* Jacob Smith
****************


OVERVIEW:

This program takes in a data file that represents the format of a CircuitBoard, 
which is then read by a scanner. Once read, the program performs a BruteForce 
search which searches through every possible paths and then prints or displays 
the best found solution(s). These paths are depicted by T's which are used 
to connect 1 (beginning of path) and 2 (end of path) on the board.

INCLUDED FILES:

 * CircuitBoard.java - Used to generate the CircuitBoard object from a data file entry
 * CircuitTracer.java - Driver file, assess provided args and depending on the args 
 executes the brute force search.
 * InvalidFileFormatException.java - Custom exception handling
 * OccupiedPositionException.java - Custom exception handling
 * Storage.java - Class that provides either a stack or queue depending on arg by driver file
 * TraceState.java - Program that is used to trace each path as the program searches for the best solution(s). 
 * README - this file

COMPILING AND RUNNING:

From the directory containing all source files, compile the program using:

$ javac CircuitTracer.java

Run the program using:

$ java CircuitTracer <-StorageType> <-ProgramMode> <InputFileName>"

Where:

Storage Type Is: 
-s uses stack-based storage
-q uses queue-based Storage

Program Mode Is: 
-c outputs results to the console
-q outputs results in a GUI

InputFileName is:
The file you wish to examine

After execution, the console will display the discovered best path

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

This program is built around a search algorithm that explores possible paths 
through a grid-based circuit board. The design separates the search logic from 
the storage behavior using a Storage interface, allowing the same search algorithm 
to run with either a stack or a queue.
The CircuitTracer class serves as the program driver. It processes command-line arguments, 
loads the circuit board, selects the appropriate storage structure, and program mode and executes the search.
The CircuitBoard class models the grid and handles obstacle detection and board bounds 
checking. Each search step is represented using the TraceState class, which stores the current board 
location and the full path taken so far.
The main algorithm repeatedly removes a search state from Storage, checks if it matches 
the goal, generates all valid neighboring moves, and adds new states back into Storage.
Using a stack produces depth-first search behavior, while using a queue produces 
breadth-first search behavior. This design allows both strategies to be tested without 
changing the overall program logic.
One possible improvement would be adding heuristic-based searching such as A* to improve performance on large boards.

TESTING:

During development the provided tester was used to see where the program was 
failing and incorrect. Once the program passed all the tests with expected, outputs
was looked at manually to ensure the proper output was occuring and not missed. 
The Tester and manual testing allowed for problems to be easily identifiable and by extension easy to fix. 
For example, all tests were passing except for 12 ,it turned out that a part of my 
text input which had an extra blank line in it was causing the fail. By removing that 
section of text output I was able to pass all the test correctly.

DISCUSSION:

One major challenge during development was correctly managing the Storage 
behavior so that the same algorithm could work with both stacks and queues. 
Another difficulty was preventing repeated paths and ensuring that backtracking worked correctly.

Debugging involved using debugger following the tracing search paths step-by-step 
and verifying that new states were being generated and stored correctly. 
The biggest concept that finally “clicked” was seeing the real-world difference 
between depth-first and breadth-first search in practice.

Analysis

How does the choice of Storage (stack vs. queue) affect how paths are explored?

When the program uses a stack, it follows one path as far as it 
can before backing up and trying a different one, which can 
be fast if it goes in the right direction but can also waste 
time in dead ends. When it uses a queue, it checks all the 
shortest paths first and then moves on to longer ones, spreading 
out evenly from the starting point in a more consistent and organized way.

Does using a stack or queue change the total number of search states?

If the program were to explore every possible path, 
both the stack and queue would end up generating 
the same total number of states. The difference is 
just the order in which those paths are explored.

Is one structure more likely to find a solution faster?

Using a queue usually finds a solution faster when 
a short path exists because it always checks the 
shortest paths first. A stack (DFS) can sometimes 
get lucky and find a quick solution too, but it 
can also spend a lot of time exploring long dead ends.

Does either structure guarantee the first solution is the shortest?

A queue does guarantee that the first solution found 
is the shortest possible path.A stack does not guarantee 
this. It might find a longer path first even if a shorter one exists.

How does memory usage compare between stack and queue?

A stack uses much less memory because it mainly stores one long path at a time.
A queue uses a lot more memory because it has to store all the active paths at 
each level of the search. As the board gets bigger, this memory difference 
becomes very noticeable.

What is the Big-Oh runtime of the search?

The worst-case runtime of the search is exponential, written as: O(x^n).
Here, x is the average number of choices at each step, and n is the 
size of the board (number of open cells).

What does this Big-Oh value actually represent?

This runtime shows how fast the number of possible paths grows, not 
just how many spaces are on the board. Every time the search branches, 
the number of paths increases rapidly, which is why the algorithm can 
get slow on larger boards.

What is n, the main factor that makes the problem harder?

n represents the number of open spaces on the board. 
The more open cells there are, the more possible paths 
exist, which makes the search take longer and use more memory.

EXTRA CREDIT:

N/A, I could've implemented a GUI interface, but i decided against it as 
I am not a fan of them, and ran out of time to struggle through it.

------------------------------------------------------------