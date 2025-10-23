/**
 * Linkable node for linear data structures
 * @author Jacob Smith
 */

public class Node<T> {

    private Node<T> nextNode;
    private T element;

    /**
     * Initialize a new node with the given element
     * @param element
     */
    public Node(T element) {
        this.element = element;
        nextNode = null;
    }

    /**
     * Initialize a Node with the given element and next Node
     * 
     * @param element
     * @param nextNode
     */
    public Node(T element, Node<T> nextNode) {
        this.element = element;
        this.nextNode = nextNode;
    }

    public Node<T> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }
}
