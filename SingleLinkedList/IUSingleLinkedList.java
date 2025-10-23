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
		Node<T> newNode = new Node<T>(element);
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
		Node<T> newNode = new Node<T>(element);
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
		// TODO 
		
	}

	@Override
	public void add(int index, T element) {
		 // TODO
		
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
		// TODO 
		return null;
	}

	@Override
	public T remove(T element) {
		// TODO 
		return null;
	}

	@Override
	public T remove(int index) {
		// TODO 
		return null;
	}

	@Override
	public void set(int index, T element) {
		// TODO 
		
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
			// TODO 
			return false;
		}

		@Override
		public T next() {
			// TODO 
			return null;
		}
		
		@Override
		public void remove() {
			// TODO
		}
	}

}