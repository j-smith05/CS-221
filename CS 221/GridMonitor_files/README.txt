****************
* Warmup
* CS 221
* 09/04/2025
* Jacob Smith
**************** 

OVERVIEW:

GridMonitor reads .txt files containing number grids of various sizes and stores the data in a two-dimensional array. 
It provides methods to calculate surrounding sums, averages, allowable deltas, and to determine whether cells are 
“in danger.” Borders are handled using a mirroring rule, which ensures sums and averages are calculated correctly even 
at the edges of the grid.

INCLUDED FILES:

 * GridMonitor.java - main source file containing the program logic
 * GridMonitorInterface.java - interface that defines required methods
 * GridMonitorTest.java - test file for verifying correctness
 * negatives.txt - sample grid with negative values
 * oneByOne.txt - sample grid with a single value
 * sample.txt - basic test file
 * sample4x5.txt - larger sample grid
 * sampleDoubles.txt - grid containing floating-point values
 * WideRange.txt - grid with values spanning a wide range
 * README.txt - this file

COMPILING AND RUNNING:

From the directory containing all source files, compile the program with:

$ javac GridMonitorTest.java

Run the program with:

$ java GridMonitorTest

Console output will display whether the input grids pass or fail the checks.

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

GridMonitor implements the GridMonitorInterface, which lays out the methods needed to analyze a grid of numbers. 
As the name suggests, GridMonitor keeps track of the grid internally using a two-dimensional array. The program 
reads the numbers from a .txt file, loads them into the array, and then provides different ways to analyze each cell.

The main tasks include calculating surrounding sums, averages, and allowable deltas, as well as checking whether a 
cell is “in danger” (meaning its value strays too far from its neighbors). These calculations happen when the program 
calls the corresponding methods from the interface.

The grid itself can be any size, so the array is created to match whatever rows and columns the input file provides. 
Storing the data in a straightforward 2D array makes it easy to grab a cell’s neighbors and quickly perform the math.

The interface acts like a contract, making sure that any class implementing it has the same core methods. This means 
the program could be extended later — for example, using a different way of storing or reading grid data without 
breaking the rest of the system.

Finally, GridMonitorTest is used to double-check everything works as expected. It runs through different grid setups, 
like small one-cell grids, larger grids, grids with negatives, and so on. That way, we can be confident the program 
handles both normal cases and edge cases correctly.

TESTING:

GridMonitorTest was the primary mechanism for testing GridMonitor. The tests were written alongside development, 
which helped ensure each method worked correctly as it was implemented. This approach made it easier to catch mistakes 
early and confirm that new changes didn’t break existing functionality.

Scenarios being tested by GridMonitorTest include:
    reading a small 1x1 grid
    handling a standard 3x3 grid
    processing a non-square 4x5 grid
    working with grids that contain negative numbers
    working with grids that contain floating-point values
    checking the behavior of “in danger” cells
    verifying calculations for surrounding sums, averages, and deltas

Additional scenarios, such as handling malformed input files or extremely large grids, would be useful to test in 
the future, but were not included due to time.

All tests for the included scenarios are currently passing, and the program behaves as expected for valid input files.

DISCUSSION:
 
One of the main hiccups was with negative numbers. At first, deltas were calculated without taking 
the absolute value of the average, which caused tests with negatives to fail. Once I realized delta 
is supposed to be a magnitude (always positive), fixing that cleared up the issue. Another key part 
was handling the borders correctly with the mirroring rule — once that was in place, sums and averages 
came out as expected. Overall, the program now passes all the required tests and handles tricky edge cases properly.

Another minor error I faced was the feeling of being overwelmed, as my CS 121 was at a difference pace 
then this class currently is, so I am behind, so ontop of all the assignments i got to learn the new 
concepts that werent taught in my CS 121 so i can be prepared for CS 221.
 
EXTRA CREDIT:

N/A For this assignment
