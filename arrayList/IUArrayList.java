
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An array-based implementation of IndexedUnsortedList
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
    private T[] array;
    private int rear;
    public static final int DEFAULT_CAPACITY = 10;


    /**
     * initialize list with default capacity
     */
    public IUArrayList() {
        // array = (T[])(new Object[DEFAULT_CAPACITY]);
        // rear = 0;
        this(DEFAULT_CAPACITY);
    }

    /**
     * initialize list with given initial capacity
     * @param initialCapacity size of initial array
     */
    @SuppressWarnings(value = "unchecked")  
    public IUArrayList(int initialCapacity) {
       array=(T[])(new Object[initialCapacity] );
        rear=0;
    }

    /**
     * Double the array capacity if there is no more room to add
     */
    private void expandIfNecessary() {
        if (rear == array.length) {
           array = Arrays.copyOf(array, array.length*2);
        }
    }

    @Override
    public void addToFront(T element) {
        expandIfNecessary();
        for (int i = rear; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = element;
        rear++;
    }

    @Override
    public void addToRear(T element) {
        expandIfNecessary();
        array[rear] = element;
        rear++;
    }   

    @Override
    public void add(T element) {
        addToRear(element);
    }   

    @Override
    public void addAfter(T element, T target) {
        int index = indexOf(target);
        if (index == -1) throw new NoSuchElementException();
        add(index + 1, element);
    }   

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > rear) {
            throw new IndexOutOfBoundsException();
        }
        expandIfNecessary();
        for (int i = rear; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        rear++;
    }   

    @Override
    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        T result = array[0];
        for (int i = 0; i < rear - 1; i++) {
            array[i] = array[i + 1];
        }
        rear--;
        array[rear] = null;
        return result;
    }

    @Override
    public T removeLast() {
       if (isEmpty()) throw new NoSuchElementException();
        T result = array[--rear];
        array[rear] = null;
        return result;
    }

    @Override
    public T remove(T element) {        
        int index = indexOf(element);
        if (index == -1) throw new NoSuchElementException();
        return remove(index);
    }

    @Override
    public T remove(int index) {        
        if (index < 0 || index >= rear) {
            throw new IndexOutOfBoundsException();
        }
        T returnValue = array[index];
        for(int i = index; i < rear - 1; i++) {
            array[i] = array[i+1];
        }
        rear --;
        array[rear] = null;
        return returnValue;
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } 
        return array[0];
    }  

    @Override
    public T last() {   
         if (isEmpty()) {
            throw new NoSuchElementException();
        } 
        return array[rear - 1];
    }

    @Override
    public T get(int index) {     
        if (index < 0 || index >= rear) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }
    // is the lazy or faster way to follow this practice

    @Override
    public int indexOf(T element) {    
        // int index = -1;
        for (int i=0;i<rear;i++){
            // if (int i = 0; index < 0 && i < rear; i++) {
            if (array[i].equals(element)){
                return i;
                // index=i;
            }
        }
        // return index;
        return -1;
    }

    @Override
    public boolean contains(T target) {     
        return indexOf (target) > -1;
    }

    @Override
    public int size() {    
        return rear;
    }

    @Override
    public boolean isEmpty() {     
        return rear == 0;
    }

    @Override
    public void set(int index, T element) {
         if (index < 0 || index >= rear) {
        throw new IndexOutOfBoundsException();
        }
        array[index] = element;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i = 0; i < rear; i++) {
            str.append(array[i].toString());
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

