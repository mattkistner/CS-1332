import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author MATTHEW KISTNER
 * @version 1.0
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null.");
        } else {
            for (T item : data) {
                if (item == null) {
                    throw new IllegalArgumentException("data cannot be null.");
                } else {
                    add(item);
                }
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * 
     * Hint: Should you use value equality or reference equality?
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
     * add helper method.
     *
     * @param data data to find.
     * @param curr node to traverse through AVL.
     * @return node added.
     */
    private AVLNode<T> addh(T data, AVLNode<T> curr) {
        if (curr == null) {
            ++size;
            return new AVLNode<T>(data);
        } else {
            if (data.compareTo(curr.getData()) < 0) {
                curr.setLeft(addh(data, curr.getLeft()));
            } else if (data.compareTo(curr.getData()) > 0) {
                curr.setRight(addh(data, curr.getRight()));
            } else {
                return curr;
            }
            return adjuster(curr);
        }
    }

    /**
     * performs an AVL left rotation.
     *
     * @param curr node to perform rotation.
     * @return new node in location.
     */
    private AVLNode<T> rotateLeft(AVLNode curr) {
        AVLNode<T> rotater = curr.getRight();
        curr.setRight(rotater.getLeft());
        rotater.setLeft(curr);
        updater(curr);
        updater(rotater);
        return rotater;
    }

    /**
     * performs an AVL right rotation.
     *
     * @param curr node to rotate.
     * @return new node in location.
     */
    private AVLNode<T> rotateRight(AVLNode curr) {
        AVLNode<T> rotater = curr.getLeft();
        curr.setLeft(rotater.getRight());
        rotater.setRight(curr);
        updater(curr);
        updater(rotater);
        return rotater;
    }

    /**
     * updates height and balance factor.
     *
     * @param node node to update.
     */
    private void updater(AVLNode<T> node) {
        node.setHeight(1 + Math.max(heightF(node.getLeft()), heightF(node.getRight())));
        node.setBalanceFactor(heightF(node.getLeft()) - heightF(node.getRight()));
    }

    /**
     * performs rotations based on balance factor.
     *
     * @param curr node to evaluate balance factor of.
     * @return node from adjustment.
     */
    private AVLNode<T> adjuster(AVLNode<T> curr) {
        updater(curr);
        if (curr.getBalanceFactor() > 1 || curr.getBalanceFactor() < -1) {
            if (curr.getBalanceFactor() < -1) {
                if (curr.getRight().getBalanceFactor() > 0) {
                    curr.setRight(rotateRight(curr.getRight()));
                }
                return rotateLeft(curr);
            } else if (curr.getBalanceFactor() > 1) {
                if (curr.getLeft().getBalanceFactor() < 0) {
                    curr.setLeft(rotateLeft(curr.getLeft()));
                }
                return rotateRight(curr);
            }
        }
        return curr;
    }

    /**
     * returns height of node.
     *
     * @param node node to return height of.
     * @return height of node.
     */
    private int heightF(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else if (size == 0) {
            throw new NoSuchElementException("AVL is empty, nothing to remove");
        } else {
            AVLNode<T> parent = new AVLNode<T>(null);
            root = removeh(data, root, parent);
            --size;
            return parent.getData();
        }
    }

    /**
     * remove helper method.
     *
     * @param data data to find.
     * @param curr node to traverse through AVL.
     * @param parent dummy node to use.
     * @return adjusted node to remove.
     */
    private AVLNode<T> removeh(T data, AVLNode<T> curr, AVLNode<T> parent) {
        if (curr == null) {
            throw new NoSuchElementException("No element found to match this data.");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeh(data, curr.getLeft(), parent));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeh(data, curr.getRight(), parent));
        } else {
            parent.setData(curr.getData());
            AVLNode<T> assist;
            if (curr.getLeft() == null || curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                assist = curr.getLeft();
                curr.setLeft(null);
                updater(assist);
                return adjuster(assist);
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                assist = curr.getRight();
                curr.setRight(null);
                updater(assist);
                return adjuster(assist);
            } else {
                AVLNode<T> parent2 = new AVLNode<T>(null);
                curr.setLeft(predecessor(curr.getLeft(), parent2));
                curr.setData(parent2.getData());
            }
        }
        updater(curr);
        return adjuster(curr);
    }

    /**
     * Finds predecessor node.
     *
     * @param curr node to traverse to predecessor.
     * @param parent node to find predecessor of.
     * @return predecessor node.
     */
    private AVLNode<T> predecessor(AVLNode<T> curr, AVLNode<T> parent) {
        if (curr.getRight() == null) {
            parent.setData(curr.getData());
            updater(curr);
            return adjuster(curr).getLeft();
        } else {
            curr.setRight(predecessor(curr.getRight(), parent));
        }
        updater(curr);
        return adjuster(curr);
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        return geth(data, root).getData();
    }

    /**
     * Get helper method
     *
     * @param data data to find.
     * @param curr node to traverse through AVL.
     * @return AVL Node retrieved.
     */
    private AVLNode<T> geth(T data, AVLNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("No Such Element exists in the AVL");
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
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        return containsh(data, root);
    }

    /**
     * Contains helper method.
     *
     * @param data data of type T to find.
     * @param curr node to traverse through AVL.
     * @return boolean indicating if AVL contains data.
     */
    private boolean containsh(T data, AVLNode<T> curr) {
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
     * Returns the height of the root of the tree.
     * 
     * Should be O(1). 
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightF(root);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with 
     * the deepest depth. 
     * 
     * Should be recursive. 
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        return mDNh(root);
    }

    /**
     * Max Deepest Node Helper method.
     *
     * @param curr node to traverse through AVL.
     * @return data of max deepest node.
     */
    private T mDNh(AVLNode<T> curr) {
        if (curr == null) {
            return null;
        } else if (curr.getRight() == null && curr.getLeft() == null) {
            return curr.getData();
        } else if (curr.getRight() != null && curr.getLeft() != null) {
            if (heightF(curr.getLeft()) > heightF(curr.getRight())) {
                return mDNh(curr.getLeft());
            } else {
                return mDNh(curr.getRight());
            }
        } else if (curr.getRight() != null) {
            return mDNh(curr.getRight());
        } else if (curr.getLeft() != null) {
            return mDNh(curr.getLeft());
        }
        return curr.getData();
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data. 
     * 
     * The second case means the successor node will be one of the node(s) we 
     * traversed left from to find data. Since the successor is the SMALLEST element 
     * greater than data, the successor node is the lowest/last node 
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     * 
     * Should be recursive. 
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else if (!contains(data) || root == null) {
            throw new java.util.NoSuchElementException("The data is not in the AVL");
        }
        return successorh(data, root);
    }

    /**
     * Successor helper method.
     *
     * @param data data of type T to search for.
     * @param curr node to traverse.
     * @return data to return.
     */
    private T successorh(T data, AVLNode<T> curr) {
        AVLNode<T> node = null;
        while (curr.getData() != null && curr.getData().compareTo(data) != 0) {
            if (curr.getData().compareTo(data) < 0) {
                curr = curr.getRight();
            } else {
                node = curr;
                curr = curr.getLeft();
            }
        }
        if (curr != null && curr.getRight() != null) {
            curr = curr.getRight();
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
            node = curr;
        }
        if (node == null) {
            return null;
        } else {
            return node.getData();
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
