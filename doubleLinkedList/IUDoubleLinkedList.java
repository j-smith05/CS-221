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
    }

    @Override
    public void add(int index, T element) {
        Node<T> newNode = new Node<T>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.setNextNode(head);
            head.setPrevNode(newNode);
            head = newNode;
        } else {
            Node<T> currentNode = head;
            for (int i = 0; i < index-1; i++) {
                currentNode = currentNode.getNextNode();
            }
            newNode.setPrevNode(currentNode);
            newNode.setNextNode(currentNode.getNextNode());
            currentNode.setNextNode(newNode);
            if (newNode.getNextNode() != null) {
                newNode.getNextNode().setPrevNode(newNode);
            } else {
                tail = newNode;
            }
        }
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
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T retVal = tail.getElement();
        tail = tail.getPrevNode();
        if (size == 1) {
            head = null;
        } else {
            tail.setNextNode(null);
        }
        size--;
        modCount++;
        return retVal;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        T element = currentNode.getElement();
        if (currentNode == head) {
            head = currentNode.getNextNode();
        } else {
            currentNode.getPrevNode().setNextNode(currentNode.getNextNode());
        }
        if (currentNode == tail) {
            tail = currentNode.getPrevNode();
        } else {
            currentNode.getNextNode().setPrevNode(currentNode.getPrevNode());
        }
        size--;
        modCount++;
        return element;
    }

    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        currentNode.setElement(element);
    }

	@Override
	public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        return currentNode.getElement();
	}

	@Override
	public int indexOf(T element) {
		int index = 0;
		Node<T> currentNode = head;
		while (currentNode != null && !element.equals(currentNode.getElement())) {
			currentNode = currentNode.getNextNode();
			index++;
		}
		if (currentNode == null) {
			index = -1;
		}
		return index;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }
    
    private class DLLIterator implements Iterator<T> {
		private Node<T> nextNode;
		private int iterModCount;
		private boolean canRemove = false;

        public DLLIterator() {
			nextNode = head;
			iterModCount = modCount;
			canRemove = false;
        }
        private void checkForComod() {
        if (iterModCount != modCount) throw new ConcurrentModificationException();
    	}

        @Override
        public boolean hasNext() {
            checkForComod();
            canRemove = true;
            return nextNode != null;
        }

        @Override
        public T next() {
            checkForComod();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T retVal = nextNode.getElement();
            nextNode = nextNode.getNextNode();
            return retVal;
        }
        
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException();
            }
            checkForComod();
            Node<T> nodeToRemove = nextNode.getPrevNode();
            nodeToRemove.getPrevNode().setNextNode(nodeToRemove.getNextNode());
            nodeToRemove.getNextNode().setPrevNode(nodeToRemove.getPrevNode());
            modCount++;
            canRemove = false;
        }
    }
}
