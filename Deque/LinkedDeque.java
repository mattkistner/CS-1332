import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
 *
 * @author MATTHEW KISTNER
 * @version 1.0
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else {
            if (head == null) {
                head = new LinkedNode<T>(data, null, null);
                tail = head;
            } else {
                LinkedNode<T> addition = new LinkedNode<T>(data, null, head);
                head.setPrevious(addition);
                head = addition;
            }
            ++size;
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else {
            if (tail == null) {
                tail = new LinkedNode<T>(data, null, null);
                head = tail;
            } else {
                LinkedNode<T> addition = new LinkedNode<T>(data, tail, null);
                tail.setNext(addition);
                tail = addition;
            }
            ++size;
        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (head == null && tail == null) {
            throw new NoSuchElementException("Deque is empty. No elements to remove.");
        } else {
            T dataToReturn = head.getData();
            if (head.equals(tail)) {
                head = null;
                tail = null;
            } else {
                head = head.getNext();
                head.getPrevious().setNext(null);
                head.setPrevious(null);
            }
            --size;
            return dataToReturn;
        }
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (head == null && tail == null) {
            throw new NoSuchElementException("Deque is empty. No elements to remove.");
        } else {
            T dataToReturn = tail.getData();
            if (tail.equals(head)) {
                tail = null;
                head = null;
            } else {
                tail = tail.getPrevious();
                tail.getNext().setPrevious(null);
                tail.setNext(null);
            }
            --size;
            return dataToReturn;
        }
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (head == null && tail == null) {
            throw new NoSuchElementException("The Deque is empty. No element to return.");
        } else {
            return head.getData();
        }
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (head == null && tail == null) {
            throw new NoSuchElementException("The Deque is empty. No element to return.");
        } else {
            return tail.getData();
        }
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
