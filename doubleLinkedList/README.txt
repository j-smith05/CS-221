****************
* DoubleLinkedList / ListTester
* CS 221 - Computer Science II
* 11/15/25
* Jacob Smith
**************** 

OVERVIEW:

This program implements a fully functional double-linked list that supports adding, removing, 
and accessing elements, along with a complete bidirectional ListIterator. A separate automated 
tester runs thousands of scenarios to verify the correctness, behavior, and edge-case handling 
of the list implementation.

INCLUDED FILES:

IUDoubleLinkedList.java – Implements the double-linked list and all required list operations.
Node.java – Defines the node structure used to store elements and next/previous links.
IndexedUnsortedList.java – Interface specifying all methods the list must support.
ListTester.java – Automated test suite that validates the list across hundreds of scenarios.
GoodList.java, BadList.java, ArrayList.java, SingleLinkedList.java – Reference and comparison implementations used by the tester.
README.txt – This documentation file explaining how to compile, run, and understand the program.

COMPILING AND RUNNING:

From the directory containing all .java files, compile the entire project with the command:

$ javac *.java

This compiles the list implementation, all helper classes, and the tester.

Run the automated test suite with:

$ java ListTester

The program will execute all test scenarios and print the results to the console once it finishes.

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

This program is built around a double-linked list that stores elements in nodes that point both 
forward and backward. The main idea is to keep track of the first and last nodes (the head and tail) 
so the list can add or remove elements efficiently at either end. Each element is stored in a small 
Node object that holds the value plus links to the next and previous nodes.

The main class, IUDoubleLinkedList, handles all list operations such as adding, removing, getting, 
and setting elements. It also keeps track of the list’s size and updates a modCount value so the 
iterator can detect when the list has been changed illegally during iteration.

The program also includes an inner class called DLLIterator, which implements a full ListIterator. 
This iterator can move forward and backward, add new elements at its current position, remove the 
last element it returned, and update elements. To do this correctly, it keeps track of its current 
position using pointers like nextNode and lastReturnedNode, and it checks modCount to make sure the 
list hasn’t been modified outside the iterator.

The IndexedUnsortedList interface defines what methods the list is required to support, and the ListTester 
class uses that interface to run a large number of tests on the implementation. The tester checks basic list 
operations, iterator behavior, exception handling, and many edge cases.

This design works well for a double-linked list because it makes adding and removing nodes straightforward 
once the iterator is in the right place. If I were to improve anything, I might add “dummy” head and tail 
nodes to reduce special-case code, or look into speeding up index-based operations so they don’t require 
scanning through the whole list.

TESTING:

 How did you test your program to be sure it works and meets all of the
 requirements? What was the testing strategy? What kinds of tests were run?
 Can your program handle bad input? Is your program  idiot-proof? How do you 
 know? What are the known issues / bugs remaining in your program?


DISCUSSION:

I tested my program mainly using the ListTester class that came with the 
project, but I was also updating and checking things as I went. Every time 
I finished a new part of the list or the iterator, I would re-run the 
tester to see what passed and what broke. This helped me catch problems 
early instead of waiting until the whole thing was written.

The ListTester runs tons of different scenarios, including empty lists, 
one-element lists, adding and removing in different positions, and a lot of 
complicated iterator actions like calling next(), previous(), add(), remove(), 
and set() in different orders. Many of the iterator bugs only showed up in 
very specific sequences, so the tester was extremely helpful.

Besides the automated testing, I also did some smaller manual checks—like 
printing out the list after certain operations—just to make sure the pointers 
were updating the way I expected.

The program handles bad input by throwing the correct exceptions, 
like IndexOutOfBoundsException, IllegalStateException, and ConcurrentModificationException. 
The fail-fast behavior works too because of modCount, so the iterator 
can tell when the list has been changed illegally.

At this point, all of the ListTester scenarios pass, and I’m not 
aware of any remaining bugs. If anything was still wrong, it would 
probably be in a really specific iterator edge case, but based on 
the final test results, the program meets the requirements.
 
 
EXTRA CREDIT:

Not applicable for this assignment.


----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.
