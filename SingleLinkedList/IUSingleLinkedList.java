import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author Jacob Smith
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private Node<T> head, tail;
	private int size;
	private int modCount;
	
	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		Node<T> newNode = new Node<>(element);
		newNode.setNextNode(head);
		head = newNode;
		if (tail == null) {
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		Node<T> newNode = new Node<>(element);
		if (!isEmpty()) {
			tail.setNextNode(newNode);
		}
		else {
			head = newNode;
		}
		tail = newNode;
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
		while (currentNode != null && !target.equals(currentNode.getElement())) {
			currentNode = currentNode.getNextNode();
		}
		if (currentNode == null) {
			throw new NoSuchElementException();
		}
		Node<T> newNode = new Node<>(element);
		newNode.setNextNode(currentNode.getNextNode());
		currentNode.setNextNode(newNode);
		if (currentNode == tail) {
			tail = newNode;
		}
		size++;
		modCount++;
		
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			addToFront(element);
		} else if (index == size) {
			addToRear(element);
		} else {
			Node<T> newNode = new Node<>(element);
			Node<T> currentNode = head;
			for (int i = 0; i < index - 1; i++) {
				currentNode = currentNode.getNextNode();
			}
			newNode.setNextNode(currentNode.getNextNode());
			currentNode.setNextNode(newNode);
			size++;
			modCount++;
		}
		
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Node<T> firstNode = head;
		head = head.getNextNode();
		if (head == null) {
			tail = null;
		}
		size--;
		modCount++;
		return firstNode.getElement();
	}

	@Override
	public T removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		if (size == 1) {
			head = tail = null;
		} else {
			Node<T> currentNode = head;
			for (int i = 0; i < size-2; i++) {
				currentNode = currentNode.getNextNode();
			}
			tail = currentNode;
			currentNode.setNextNode(null);
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal;
		if (head.getElement().equals(element)) {
			retVal = head.getElement();
			head = head.getNextNode();
			if (head == null) {
				tail = null;
			}
		} else {
			Node<T> currentNode = head;
			while (currentNode != tail && !currentNode.getNextNode().getElement().equals(element)) {
				currentNode = currentNode.getNextNode();
			}
			if (currentNode == tail) {
				throw new NoSuchElementException();
			}
			retVal = currentNode.getNextNode().getElement();
			currentNode.setNextNode(currentNode.getNextNode().getNextNode());
			if (currentNode.getNextNode() == null) {
				tail = currentNode;
			}
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			return removeFirst();
		}
		Node<T> currentNode = head;
		for (int i = 0; i < index - 1; i++) {
			currentNode = currentNode.getNextNode();
		}
		T removedElement = currentNode.getNextNode().getElement();
		currentNode.setNextNode(currentNode.getNextNode().getNextNode());
		if (currentNode.getNextNode() == null) {
			tail = currentNode;
		}
		size--;
		modCount++;
		return removedElement;
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
			throw new IllegalStateException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if (isEmpty()) {
			throw new IllegalStateException();
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
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private Node<T> nextNode;
		private int iterModCount;
		
		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			return nextNode != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T element = nextNode.getElement();
			nextNode = nextNode.getNextNode();

			return element;
		}
		
		@Override
		public void remove() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (nextNode == null) {
				throw new IllegalStateException();
			}
			Node<T> currentNode = head;
			Node<T> previousNode = null;
			while (currentNode != null && currentNode != nextNode) {
				previousNode = currentNode;
				currentNode = currentNode.getNextNode();
			}
			if (currentNode == null) {
				throw new IllegalStateException();
			}
			if (previousNode == null) {
				head = nextNode.getNextNode();
			} else {
				previousNode.setNextNode(nextNode.getNextNode());
			}
			if (nextNode == tail) {
				tail = previousNode;
			}
			size--;
			modCount++;
			iterModCount++;
		}
	}

}