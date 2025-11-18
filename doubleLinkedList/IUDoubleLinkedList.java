import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * Double-linked node-based implementation of IndexedUnsortedList.
 * Supports a fully-functional ListIterator in addition to basic 
 * Iterator and ListIterator operations. Also supports all IndexedUnsortedList
 * operations.
 * @author Jacob Smith
 */

public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int modCount;

    /** Creates an empty list */
    public IUDoubleLinkedList() {
        head = tail = null;
        size = 0;
        modCount = 0;
    }

    /**
     * Returns the Node at a given index in quicker time.
     */
    private Node<T> getNodeAt(int index) {
        if (index < (size / 2)) { // If index is in the first half
            Node<T> current = head;
            for (int i = 0; i < index; i++) { // Traverse from head
                current = current.getNextNode();
            }
            return current;
        } else { // If index is in the second half
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) { // Traverse from tail
                current = current.getPrevNode();
            }
            return current;
        }
    }
    
    @Override
    public void addToFront(T element) {
        Node<T> newNode = new Node<T>(element);
        newNode.setNextNode(head);
        if (tail == null) { // If list is empty
            tail = newNode;
        } else { // If list is not empty
            head.setPrevNode(newNode);
        }
        head = newNode;
        size++;
        modCount++;
    }

    @Override
    public void addToRear(T element) { // Add element to the end of the list
        Node<T> newNode = new Node<T>(element);
        if (tail == null) { // If list is empty
            head = tail = newNode;
        } else { // If list is not empty
            tail.setNextNode(newNode);
            newNode.setPrevNode(tail);
            tail = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public void add(T element) { // Default to add to rear
        addToRear(element);
    }

    @Override
    public void addAfter(T element, T target) {
        Node<T> currentNode = head;
        while (currentNode != null && !currentNode.getElement().equals(target)) { // Traverse to find target
            currentNode = currentNode.getNextNode();
        }
        if (currentNode == null) { // Target not found
            throw new NoSuchElementException();
        }
        Node<T> newNode = new Node<T>(element);
        newNode.setNextNode(currentNode.getNextNode());
        newNode.setPrevNode(currentNode);
        if (currentNode.getNextNode() != null) { // If not adding at the end
            currentNode.getNextNode().setPrevNode(newNode);
        } else { // If adding at the end
            tail = newNode;
        }
        currentNode.setNextNode(newNode);
        size++;
        modCount++;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        Node<T> newNode = new Node<>(element);

        if (size == 0) { // Empty list
            head = tail = newNode;
        } else if (index == 0) { // Add to front
            newNode.setNextNode(head);
            head.setPrevNode(newNode);
            head = newNode;
        } else if (index == size) { // Add to rear
            newNode.setPrevNode(tail);
            tail.setNextNode(newNode);
            tail = newNode;
        } else { // Add in middle
            Node<T> current = getNodeAt(index);
            Node<T> prev = current.getPrevNode();
            prev.setNextNode(newNode);
            newNode.setPrevNode(prev);
            newNode.setNextNode(current);
            current.setPrevNode(newNode);
        }
        size++;
        modCount++;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T retVal = head.getElement();
        head = head.getNextNode();
        if (size == 1) { // If there was only one element
            tail = null;
        } else { // More than one element
            head.setPrevNode(null);
        }
        size--;
        modCount++;
        return retVal; // Return removed element
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T result = tail.getElement();

        // If there's only one element
        if (head == tail) {
            head = tail = null;
        } else {
            Node<T> prev = tail.getPrevNode();
            tail.setPrevNode(null);
            prev.setNextNode(null);
            tail = prev;
        }

        size--;
        modCount++;
        return result; // Return removed element
    }

    @Override
    public T remove(T element) {
        int index = indexOf(element);
        if (index == -1) {
            throw new NoSuchElementException();
        }
        return remove(index); // Remove by index
    }

    @Override
    public T remove(int index) {
        // Validate index
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<T> current = getNodeAt(index);
        T result = current.getElement();

        Node<T> prev = current.getPrevNode();
        Node<T> next = current.getNextNode();

        // Unlink current node
        if (prev == null)
            head = next;
        else
            prev.setNextNode(next);

        if (next == null)
            tail = prev;
        else
            next.setPrevNode(prev);

        size--;
        modCount++;
        return result; // Return removed element
    }


    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size) // Validate index
            throw new IndexOutOfBoundsException();
        getNodeAt(index).setElement(element); // Update element at index
        modCount++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return getNodeAt(index).getElement(); // Return element at index
    }

    @Override
    public int indexOf(T element) {
        int index = 0;
        Node<T> currentNode = head;
        while (currentNode != null) { // Traverse the list
            T curr = currentNode.getElement();
            if ((element == null && curr == null) || (element != null && element.equals(curr))) {
                return index;
            }
            currentNode = currentNode.getNextNode(); // Move to next node
            index++;
        }
        return -1; // Element not found
    }


	@Override
	public T first() {
		if (isEmpty()) { // Check if list is empty
			throw new NoSuchElementException();
		}
		return head.getElement(); // Return the first element
	}

	@Override
	public T last() { // Check if list is empty
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return tail.getElement(); // Return the last element
	}

	@Override
	public boolean contains(T target) { // Check if the list contains the target element
		return indexOf(target) > -1;
	}

	@Override
	public boolean isEmpty() { // Check if the list is empty
		return size == 0;
	}

	@Override
	public int size() { // Return the size of the list
		return size;
	}

	@Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (T element : this) {
            str.append(element.toString());
            str.append(", ");
        }
        if (!isEmpty()) {
            str.delete(str.length() -2, str.length());
        }
        str.append("]");
        return str.toString();
    }


    @Override
    public Iterator<T> iterator() { // Return a new DLLIterator
        return new DLLIterator();
    }

    @Override
    public ListIterator<T> listIterator() { // Return a new DLLIterator
        return new DLLIterator();
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) { // Return a new DLLIterator at startingIndex
        return new DLLIterator(startingIndex);
    }
    
    /** 
     * Inner class that implements a ListIterator for the DoubleLinkedList
     * @author Jacob Smith
     */
    private class DLLIterator implements ListIterator<T> {
        private Node<T> nextNode;
        private Node<T> lastReturnedNode;
        private int iterModCount;
        private int nextIndex;
        private boolean lastMoveWasNext;

        /** Intitalizes the iterator before first element */
        public DLLIterator() {
            nextNode = head;
            lastReturnedNode = null;
            lastMoveWasNext = false;
            iterModCount = modCount;
            nextIndex = 0;
        }

        /** Intiialize iterator before the specificed index 
        * @param startingIndex index that would be next after constructor
        */
        public DLLIterator(int startingIndex) {
            if (startingIndex < 0 || startingIndex > size) {
                throw new IndexOutOfBoundsException();
            }
            lastReturnedNode = null;
            nextIndex = startingIndex;
            iterModCount = modCount;

            if (startingIndex == size) {
                nextNode = null;
                return;
            }
            if (startingIndex < size / 2) {
                nextNode = head;
                for (int i = 0; i < startingIndex; i++) {
                    nextNode = nextNode.getNextNode();
                }
            } else {
                nextNode = tail;
                for (int i = size - 1; i > startingIndex; i--) {
                    nextNode = nextNode.getPrevNode();
                }
            }
        }

        /**
        * Checks if the iterator is still valid
        * @throws ConcurrentModificationException if the list has been modified
        */
        private void checkForComod() {
            if (iterModCount != modCount)
                throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext() {
            checkForComod();
            return nextNode != null;
        }

        @Override
        public T next() {
            checkForComod();
            if(nextNode == null) {
                throw new NoSuchElementException();
            }
            T retVal = nextNode.getElement();
            lastReturnedNode = nextNode;
            nextNode = nextNode.getNextNode();
            lastMoveWasNext = true;
            nextIndex = nextIndex + 1;
            return retVal;
        }
        
        @Override
        public boolean hasPrevious() {
            checkForComod();
            return nextIndex > 0;
        }

        @Override
        public T previous() {
            checkForComod();
            T retVal = null;
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }

            if (nextNode == null) {
                nextNode = tail;
            } else {
                nextNode = nextNode.getPrevNode();
            }

            lastReturnedNode = nextNode;
            lastMoveWasNext = false;
            nextIndex--;

            return lastReturnedNode.getElement();
            
        }

        @Override
        public int nextIndex() {
            checkForComod(); 
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            checkForComod();
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            checkForComod();
            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }

            Node<T> prev = lastReturnedNode.getPrevNode();
            Node<T> next = lastReturnedNode.getNextNode();

            if (prev == null) {
                head = next;
            } else {
                prev.setNextNode(next);
            }
            if (next == null) {
                tail = prev;
            } else {
                next.setPrevNode(prev);
            }
            
            if (lastMoveWasNext) {
                nextIndex--;
            } else {
            }

            size--;
            modCount++;
            iterModCount++;
            lastReturnedNode = null;
            lastMoveWasNext = false;
        }

        @Override
        public void set(T e) {
            checkForComod();
            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }
            lastReturnedNode.setElement(e);
            modCount++;
            iterModCount++;
            lastReturnedNode = null;
        }

        @Override
        public void add(T e) {
            checkForComod();

            Node<T> newNode = new Node<>(e);
            Node<T> prev = (nextNode == null) ? tail : nextNode.getPrevNode();
            Node<T> next = nextNode;

            newNode.setPrevNode(prev);
            newNode.setNextNode(next);

            if (prev == null) {
                head = newNode;
            } else {
                prev.setNextNode(newNode);
            }

            if (next == null) {
                tail = newNode;

            } else {
                next.setPrevNode(newNode);
            }

            size++;
            modCount++;
            iterModCount++;

            nextIndex++;
            lastReturnedNode = null; 
            lastMoveWasNext = false;
            nextNode = newNode.getNextNode();
        }
    }
}
