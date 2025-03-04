
import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author MATTHEW KISTNER
 * @version 1.0
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be less than 0.");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than List size.");
        } else if (data == null) {
            throw new IllegalArgumentException("Entered data cannot be null");
        } else {
            DoublyLinkedListNode<T> addition = new DoublyLinkedListNode<T>(data, null, null);
            if (index == 0) {
                addition.setNext(head);
                if (head != null) {
                    head.setPrevious(addition);
                } else {
                    tail = addition;
                }
                head = addition;
            } else if (index == size) {
                addition.setPrevious(tail);
                if (tail != null) {
                    tail.setNext(addition);
                }
                tail = addition;
            } else if (index <= (size / 2)) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                addition.setPrevious(curr.getPrevious());
                addition.setNext(curr);
                curr.getPrevious().setNext(addition);
                curr.setPrevious(addition);
            } else if (index >= (size / 2)) {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
                addition.setPrevious(curr.getPrevious());
                addition.setNext(curr);
                curr.getPrevious().setNext(addition);
                curr.setPrevious(addition);
            }
            ++size;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be greater than 0.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than or equal to List size");
        } else {
            if (index == 0) {
                if (head.equals(tail)) {
                    T dataToReturn = head.getData();
                    clear();
                    return dataToReturn;
                }
                DoublyLinkedListNode<T> removed = head;
                head = head.getNext();
                head.getPrevious().setNext(null);
                head.setPrevious(null);
                --size;
                return removed.getData();
            } else if (index == (size - 1)) {
                DoublyLinkedListNode<T> removed = tail;
                tail = tail.getPrevious();
                tail.getNext().setPrevious(null);
                tail.setNext(null);
                --size;
                return removed.getData();
            } else if (index <= (size - 1 / 2)) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                curr.getNext().setPrevious(curr.getPrevious());
                curr.getPrevious().setNext(curr.getNext());
                curr.setPrevious(null);
                curr.setNext(null);
                --size;
                return curr.getData();
            } else if (index >= (size - 1 / 2)) {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getNext();
                }
                curr.getNext().setPrevious(curr.getPrevious());
                curr.getPrevious().setNext(curr.getNext());
                curr.setPrevious(null);
                curr.setNext(null);
                --size;
                return curr.getData();
            }
        }
        return null;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty, nothing can be removed.");
        } else {
            return removeAtIndex(0);
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty, nothing can be removed.");
        } else {
            return removeAtIndex(size - 1);
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be greater than 0.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than or equal to List size");
        } else if (index < (size / 2)) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        } else {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.getPrevious();
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null && tail == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove anything, List is empty.");
        } else {
            if (tail.getData().equals(data)) {
                return removeFromBack();
            } else {
                DoublyLinkedListNode<T> occurence = tail.getPrevious();
                while (!(occurence.getData().equals(data))) {
                    if (occurence.equals(head)) {
                        throw new NoSuchElementException("Data does not exist within list.");
                    } else {
                        occurence = occurence.getPrevious();
                    }
                }
                if (occurence.equals(head)) {
                    return removeFromFront();
                } else {
                    T toReturn = occurence.getData();
                    occurence.getPrevious().setNext(occurence.getNext());
                    occurence.getNext().setPrevious(occurence.getPrevious());
                    occurence.setNext(null);
                    occurence.setPrevious(null);
                    --size;
                    return toReturn;
                }
            }
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        T[] arr = (T[]) new Object[size];
        DoublyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            arr[i] = curr.getData();
            curr = curr.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
