import java.util.NoSuchElementException;

/**
 * Your implementation of a linked deque.
 *
 * @author Yuqi Cao
 * @userid ycao344
 * @GTID YOUR GT ID HERE (e.g. 900000000)
 * @version 1.0
 */
public class LinkedDeque<T> {
    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        if (head == null) {
            head = new LinkedNode<>(data);
            tail = head;
        } else {
            LinkedNode<T> linkNode = new LinkedNode<>(data);
            linkNode.setNext(head);
            head.setPrevious(linkNode);
            head = linkNode;
        }
        size++;
    }

    /**
     * Adds the data to the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        if (tail == null) {
            tail = new LinkedNode<>(data);
            head = tail;
        } else {
            LinkedNode<T> newNode = new LinkedNode<>(data);
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes the data at the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("it's an empty deque");
        } else {
            T data = head.getData();
            head.getNext().setPrevious(null);
            head = head.getNext();
            size--;
            return data;
        }
    }

    /**
     * Removes the data at the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("the deque is empty");
        } else {
            T data = tail.getData();
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
            size--;
            return data;
        }
    }

    /**
     * Returns the number of elements in the list.
     *
     * Runs in O(1) for all cases.
     * 
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}