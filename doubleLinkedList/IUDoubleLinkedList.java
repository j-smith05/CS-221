import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * Double-linked node-based implementation of IndexedUnsortedList.
 * Supports a fully-functional ListIterator in addition to basic Iterator
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
        if (index < (size / 2)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNextNode();
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrevNode();
            }
            return current;
        }
    }
    
    @Override
    public void addToFront(T element) {
        Node<T> newNode = new Node<T>(element);
        newNode.setNextNode(head);
        if (tail == null) {
            tail = newNode;
        } else {
            head.setPrevNode(newNode);
        }
        head = newNode;
        size++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        Node<T> newNode = new Node<T>(element);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.setNextNode(newNode);
            newNode.setPrevNode(tail);
            tail = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public void add(T element) {
        addToRear(element);
    }

    @Override
    public void addAfter(T element, T target) {
        Node<T> currentNode = head;
        while (currentNode != null && !currentNode.getElement().equals(target)) {
            currentNode = currentNode.getNextNode();
        }
        if (currentNode == null) {
            throw new NoSuchElementException();
        }
        Node<T> newNode = new Node<T>(element);
        newNode.setNextNode(currentNode.getNextNode());
        newNode.setPrevNode(currentNode);
        if (currentNode.getNextNode() != null) {
            currentNode.getNextNode().setPrevNode(newNode);
        } else {
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

        if (size == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.setNextNode(head);
            head.setPrevNode(newNode);
            head = newNode;
        } else if (index == size) {
            newNode.setPrevNode(tail);
            tail.setNextNode(newNode);
            tail = newNode;
        } else {
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
        if (size == 1) {
            tail = null;
        } else {
            head.setPrevNode(null);
        }
        size--;
        modCount++;
        return retVal;
        // ListIterator<T> iter = listIterator();
        // if (!iter.hasNext()) {
        //     throw new NoSuchElementException();
        // }
        // T retVal = iter.next();
        // iter.remove();
        // return retVal;
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
        return result;
        // ListIterator<T> iter = listIterator(size);
        // if (!iter.hasPrevious()) {
        //     throw new NoSuchElementException();
        // }
        // T retVal = iter.previous();
        // iter.remove();
        // return retVal;
    }

    @Override
    public T remove(T element) {
        int index = indexOf(element);
        if (index == -1) {
            throw new NoSuchElementException();
        }
        return remove(index);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<T> current = getNodeAt(index);
        T result = current.getElement();

        Node<T> prev = current.getPrevNode();
        Node<T> next = current.getNextNode();

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
        return result;
    }


    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        getNodeAt(index).setElement(element);
        modCount++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return getNodeAt(index).getElement();
    }

    @Override
    public int indexOf(T element) {
        int index = 0;
        Node<T> currentNode = head;
        while (currentNode != null) {
            T curr = currentNode.getElement();
            if ((element == null && curr == null) || (element != null && element.equals(curr))) {
                return index;
            }
            currentNode = currentNode.getNextNode();
            index++;
        }
        return -1;
    }


	@Override
	public T first() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		return indexOf(target) > -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
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
    public Iterator<T> iterator() {
        return new DLLIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DLLIterator();
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        return new DLLIterator(startingIndex);
    }
    

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
