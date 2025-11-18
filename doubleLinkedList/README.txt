****************
* DoubleLinkedList/List Tester
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
GoodList.java - Reference and comparison implementations used by the tester.
BadList.java - Reference and comparison implementations used by the tester.
ArrayList.java - Reference and comparison implementations used by the tester.
SingleLinkedList.java – Reference and comparison implementations used by the tester.
README.txt (Current File) – This file explaining how to compile, run, and understand the program.

COMPILING AND RUNNING:

From the directory containing all .java files, compile the entire project with the command:

$ javac *.java

This compiles the list implementation, all helper classes, and the tester.

Run the automated test suite with:

$ java ListTester

The program will execute all test scenarios and print the results to the console once it finishes.

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

This program is built around a double-linked list that stores elements 
in nodes that point both forward and backward. The main idea is to keep 
track of the first and last nodes (the head and tail) so the list can add 
or remove elements efficiently at either end. Each element is stored in a small 
Node object that holds the value plus links to the next and previous nodes.

The main class, IUDoubleLinkedList, handles all list operations such as adding, 
removing, getting, and setting elements. It also keeps track of the list’s size 
and updates a modCount value so the iterator can detect when the list has been 
changed illegally during iteration.

The program also includes a private inner class called DLLIterator, which 
implements a full ListIterator. This iterator can move forward and backward, 
add new elements at its current position, remove the last element it returned, 
and update elements. To do this correctly, it keeps track of its current 
position using pointers like nextNode and lastReturnedNode, and it checks modCount
to make sure the list hasn’t been modified outside the iterator.

The IndexedUnsortedList interface defines what methods the list is required to 
support, and the ListTester class uses that interface to run a large number of 
tests on the implementation. The tester checks basic list  operations, iterator 
behavior, exception handling, and many edge cases.

This design works well for a double-linked list because it makes adding and 
removing nodes straightforward once the iterator is in the right place. If 
I were to improve anything, I might add “dummy” head and tail  nodes to reduce 
special-case code, or look into speeding up index-based operations so they 
don’t require scanning through the whole list.

TESTING:

I tested my program mainly using the ListTester file that came with the 
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

DISCUSSION:
 
One of the biggest issues I ran into while working on this project 
was figuring out how the iterator was supposed to behave. At first, 
I kept running into problems where the iterator’s nextNode or 
lastReturnedNode wasn’t what I expected, which caused all kinds of 
weird errors—everything from NullPointerExceptions to 
IllegalStateExceptions popping up in the tester. I realized pretty 
quickly that I didn’t fully understand the rules behind how Java’s 
ListIterator is supposed to work, so I had to spend some time looking 
things up and reading through examples to get a better idea of what 
was going on.

Another thing that gave me trouble was handling all the edge cases 
with the list pointers. Removing the head, removing the tail, removing 
the only element in the entire list—pretty much all of those situations 
broke at least once while I was developing. Usually it came down to a 
small pointer mistake, like forgetting to update a prev link or missing 
a case where the list becomes empty.

I also ran into issues where I forgot to update modCount, which caused 
the fail-fast behavior to break and made iterator tests fail even though 
the list itself looked correct. That’s when I learned how important it 
is to make sure every structural change updates modCount.

A lot of the errors I saw were things like nodes getting skipped, the 
iterator being off by one, or remove() or set() throwing errors because 
I wasn’t tracking the iterator’s state correctly. Most of the time, the 
fixes came from slowing down and walking through what the iterator was 
actually pointing at before and after each method call.

The hardest part for me was definitely getting the iterator to behave 
exactly right. But eventually, after breaking it enough times, the 
logic finally started to click. I had that moment where I suddenly 
understood why next() and previous() change different pointers, and why 
remove() depends so much on lastReturnedNode. Once that clicked, the 
rest of the debugging went a lot smoother.

Overall, this project really helped me understand double-linked lists 
and iterators in a more hands-on way than just reading about them.
 
EXTRA CREDIT:

Not applicable for this assignment.

----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.
