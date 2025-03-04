import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;


/**
 * Your implementation of a BST.
 *
 * @author MATTHEW KISTNER
 * @version 1.0
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else {
            for (T item : data) {
                if (item == null) {
                    throw new IllegalArgumentException("Data cannot be null.");
                } else {
                    add(item);
                }
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        root = addh(data, root);
    }

    /**
     * Helper method to the above add method.
     *
     * Recursively adds a new BSTNode to the BST.
     *
     * @param data data to add to the BST.
     * @param curr BSTNode used to traverse through the BST.
     * @return BSTNode that was added to BST.
     */
    private BSTNode<T> addh(T data, BSTNode<T> curr) {
        if (curr == null) {
            ++size;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addh(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addh(data, curr.getRight()));
        }
        return curr;
    }


    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else if (size == 0) {
            throw new NoSuchElementException("The BST is empty.");
        } else {
            BSTNode<T> parent = new BSTNode<T>(null);
            root = removeh(data, root, parent);
            --size;
            return parent.getData();
        }
    }

    /**
     * Helper method for the BST remove method.
     *
     * Recursively removes a BST Node from the BST.
     *
     * @param data data being searched for in the BST.
     * @param curr BSTNode traversing through tree.
     * @param parent BSTNode storing data to remove.
     * @throws java.util.NoSuchElementException if data is not in the tree.
     * @return BSTNode
     */
    private BSTNode<T> removeh(T data, BSTNode<T> curr, BSTNode<T> parent) {
        if (curr == null) {
            throw new NoSuchElementException("No element found with this data.");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeh(data, curr.getLeft(), parent));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeh(data, curr.getRight(), parent));
        } else {
            parent.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> parent2 = new BSTNode<T>(null);
                curr.setRight(successor(curr.getRight(), parent2));
                curr.setData(parent2.getData());
            }
        }
        return curr;
    }

    /**
     * Returns the successor.
     *
     * @param curr BSTNode traversing through BST.
     * @param parent BSTNode holding data to remove.
     * @return the successor node.
     */
    private BSTNode<T> successor(BSTNode<T> curr, BSTNode<T> parent) {
        if (curr.getLeft() == null) {
            parent.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(successor(curr.getLeft(), parent));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else {
            return geth(data, root).getData();
        }
    }

    /**
     * A helper method for the get method.
     *
     * Recursively finds a node containing entered data.
     *
     * @param data data of type T to be searched for.
     * @param curr BSTNode to traverse through the BST.
     * @return BSTNode matching entered data, null if not found.
     */
    private BSTNode<T> geth(T data, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("No such element exists in this BST.");
        } else if (data.compareTo(curr.getData()) > 0) {
            return geth(data, curr.getRight());
        } else if (data.compareTo(curr.getData()) < 0) {
            return geth(data, curr.getLeft());
        } else {
            return curr;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else {
            return containsh(data, root);
        }
    }

    /**
     * Helper method for contains method.
     *
     * Recursively traverses through BST to return a boolean if the item is in the BST.
     * @param data Data of type T to be found.
     * @param curr BSTNode used to traverse through BST.
     * @return boolean indicating if the data is present.
     */
    private boolean containsh(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsh(data, curr.getRight());
        } else if (data.compareTo(curr.getData()) < 0) {
            return containsh(data, curr.getLeft());
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        return preorderh(list, root);
    }

    /**
     * A helper method for the preorder method.
     *
     * Recursively traverses through data and adds it to a List of type T.
     *
     * @param list List of type T that is holding the data in preorder.
     * @param curr BSTNode used to traverse through data.
     * @return List of type T with data in preorder.
     */
    private List<T> preorderh(List<T> list, BSTNode<T> curr) {
        if (curr == null) {
            return list;
        } else {
            list.add(curr.getData());
            preorderh(list, curr.getLeft());
            preorderh(list, curr.getRight());
        }
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        return inorderh(list, root);
    }

    /**
     * A helper method for the inorder method.
     *
     * Recursively traverses through BST, returning a List of the data inorder.
     *
     * @param list List of type T to hold data inorder.
     * @param curr BSTNode used to traverse through BST.
     * @return List of type T inorder.
     */
    private List<T> inorderh(List<T> list, BSTNode<T> curr) {
        if (curr == null) {
            return list;
        } else {
            inorderh(list, curr.getLeft());
            list.add(curr.getData());
            inorderh(list, curr.getRight());
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        return postorderh(list, root);
    }

    /**
     * A helper method for the postorder method.
     *
     * Recursively traverses through BST, returning a List of the data postorder.
     *
     * @param list List of type T to hold data postorder.
     * @param curr BSTNode used to traverse through BST.
     * @return List of type T postorder.
     */
    private List<T> postorderh(List<T> list, BSTNode<T> curr) {
        if (curr == null) {
            return list;
        } else {
            postorderh(list, curr.getLeft());
            postorderh(list, curr.getRight());
            list.add(curr.getData());
        }
        return list;
    }



    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<T>();
        if (size == 0) {
            return list;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> ordered = queue.remove();
            if (ordered.getLeft() != null) {
                queue.add(ordered.getLeft());
            }
            if (ordered.getRight() != null) {
                queue.add(ordered.getRight());
            }
            list.add(ordered.getData());
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heighth(root);
    }

    /**
     * Helper method to the height method.
     *
     * Recursively finds the max height of the BST in an int.
     *
     * @param curr BSTNode used to traverse through the BST.
     * @return int representing the max height of the BST.
     */
    private int heighth(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int leftSide = heighth(curr.getLeft());
            int rightSide = heighth(curr.getRight());
            return leftSide > rightSide ? leftSide + 1 : rightSide + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> list = new ArrayList<>();
        getMaxDataPerLevelh(list, root, 0);
        return list;

    }

    /**
     * Helper method to the getMaxDataPerLevel method.
     *
     * Recursively traverses and analyzes greatest value in each row.
     *
     * @param list List of type T to hold the max values.
     * @param curr BSTNode meant to traverse through the BST.
     * @param level int evaluating the level of the BST.
     */
    private void getMaxDataPerLevelh(List<T> list, BSTNode<T> curr, int level) {
        if (curr == null) {
            return;
        }
        if (list.size() == level) {
            list.add(curr.getData());
        }
        getMaxDataPerLevelh(list, curr.getRight(), level + 1);
        getMaxDataPerLevelh(list, curr.getLeft(), level + 1);
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
