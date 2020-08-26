import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * @param <T>
 * @author : Arkesh Rath
 */

/**
 * Implementation of a BST, including custom iterators.
 * Java collections frameworks are being imported only for iterator implementation.
 *
 * @param <T> : Type of data stored in each BST Node.
 */

public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {
    /**
     * Root of the BST.
     */
    private Node<T> root;
    /**
     * Comparator which defines the natural order of the node's data.
     */
    private Comparator<T> comparator;

    public BST() {
        this(null);
    }

    public BST(Comparator<T> comp) {
        comparator = comp;
        root = null;
    }

    /**
     * Returns comparator being used by the tree.
     *
     * @return : Returns comparator being used by the BST.
     */
    public Comparator<T> comparator() {
        return comparator;
    }

    /**
     * Private helper method to compare 2 node's data.
     *
     * @param obj1 : Data of Node 1.
     * @param obj2 : Data of Node 2.
     * @return : An integer value, which is the difference between the object's values.
     */
    private int compare(T obj1, T obj2) {
        if (comparator != null) {
            return comparator.compare(obj1, obj2);
        } else {
            return obj1.compareTo(obj2);
        }
    }

    /**
     * Returns the root of the current BST.
     *
     * @return : Node object which represents the root of the current tree.
     */
    public T getRoot() {
        if (root == null) {
            return null;
        }
        return root.data;
    }


    /**
     * Recursive helper method to find height of the tree.
     *
     * @param current : Current node in the recursive call.
     * @return : Height of the tree.
     */
    private int getHeight(Node<T> current) {
        /*
        Base Case, when you hit a null or a leaf you have traversed till the end of that branch.
        Hence we return 0.
         */
        if (current == null || (current.right == null && current.left == null)) {
            return 0;
        }

        int lHeight = getHeight(current.left); // Recursive case, traverse left subtree.
        int rHeight = getHeight(current.right); // Recursive case, traverse right subtree.

        return Math.max(lHeight, rHeight) + 1;
    }

    /**
     * Method to find height of current BST.
     *
     * @return : An integer which is height of the BST.
     */
    public int getHeight() {
        if (root == null) {
            return 0;
        }
        return getHeight(root);
    }

    /**
     * Recursive helper method to find number of nodes in the tree.
     *
     * @param current : Current node in the recursive call.
     * @return : Number of nodes in the tree.
     */
    private int getNumberOfNodes(Node<T> current) {
        /*
        Base Case : When we hit a null we have finished traversing a branch.
        Hence return 0 and continue processing of the other branches.
         */
        if (current == null) {
            return 0;
        }
        int numOfNodes = 0;
        numOfNodes += 1;

        numOfNodes += getNumberOfNodes(current.left); // Recursive Case : Count number of nodes in left subtree.
        numOfNodes += getNumberOfNodes(current.right); // Recursive Case : Count number of nodes in right subtree.

        return numOfNodes;
    }

    /**
     * Method to find number of nodes in current BST.
     *
     * @return : An integer which is the number of nodes in the BST.
     */
    public int getNumberOfNodes() {
        if (root == null) {
            return 0;
        }

        return getNumberOfNodes(root);
    }

    /**
     * Private recursive helper method to search for an object in the BST.
     *
     * @param current  : Current node in the recursive call.
     * @param toSearch : Key to be searched for.
     * @return : Data within node being searched for.
     */
    private T search(Node<T> current, T toSearch) {
        /*
        Base case, we have traversed till the end of a branch and not found the word, hence return null.
         */
        if (current == null) {
            return null;
        }

        /*
        Base case, we have found the object we were looking for, hence return the object.
         */
        if (compare(current.data, toSearch) == 0) {
            return current.data;
        }

        /*
        Recursive case , if value of search object is lesser than current node, search in the left subtree.
         */
        if (compare(current.data, toSearch) > 0) {
            return search(current.left, toSearch);
        }

        /*
        Recursive case, if value of search object is greater than current node, search in the right subtree.
         */
        return search(current.right, toSearch);
    }

    /**
     * Searches for a node in the BST. Returns node's data if found.
     *
     * @param toSearch Object value to search.
     * @return : Node's data if found, else null.
     */
    @Override
    public T search(T toSearch) {
        if (root == null) {
            return null;
        }

        return search(root, toSearch);
    }

    /**
     * Private recursive helper method to insert a new node in the BST.
     * After insertion, we return the root of the tree.
     *
     * @param current  : Current node in the recursive call.
     * @param toInsert : Node to be inserted.
     * @return : Root reference of the current tree..
     */
    private Node<T> insert(Node<T> current, T toInsert) {
        /*
        Base case, we found a position currently not occupied in the tree, hence we instantiate a new node and return.
         */
        if (current == null) {
            return new Node<>(toInsert);
        }

        /*
        Base case, we find insertion node is already present in the tree, we return the node itself.
         */
        if (compare(current.data, toInsert) == 0) {
            return current;
        }

        if (compare(current.data, toInsert) > 0) {
            /*
            Recursive case, value of insert data is lesser than current node, find insertion position in the
            left subtree and update current's left subtree.
             */
            current.left = insert(current.left, toInsert);
        } else {
            /*
            Recursive case, value of insert data is greater than current node, find insertion position in the
            right subtree and update current's right subtree.
             */
            current.right = insert(current.right, toInsert);
        }

        return current;
    }


    /**
     * Inserts a node in the BST.
     *
     * @param toInsert Object value to be inserted.
     */
    @Override
    public void insert(T toInsert) {
        if (root == null) {
            root = new Node<>(toInsert);
        }

        root = insert(root, toInsert);
    }

    /**
     * Custom Iterator for the BST, implemented using a stack.
     */
    private class BSTIterator implements Iterator<T> {
        /**
         * Stack to hold the smallest elements of the tree.
         */
        private Stack<Node<T>> bststack;

        /**
         * Constructor to initialize stack and push the left most values of tree into stack.
         */
        BSTIterator() {
            bststack = new Stack<>();
            pushSmallest(root);
        }

        /**
         * Checks if the stack is not empty.
         *
         * @returns : boolean variable indicating status of the stack.
         */
        @Override
        public boolean hasNext() {
            return !bststack.empty();
        }

        /**
         * Returns the next smallest element of the BST. Pushes the right subtree
         * of the extracted node into the stack.
         *
         * @return : Node object with smallest value.
         */
        @Override
        public T next() {
            Node<T> nextN = bststack.pop();
            pushSmallest(nextN.right);
            return nextN.data;
        }

        /**
         * Pushes the leftmost branch of current node into stack.
         *
         * @param current : Current node being pushed.
         */
        private void pushSmallest(Node<T> current) {
            while (current != null) {
                bststack.push(current);
                current = current.left;
            }
        }
    }

    /**
     * Returns custom iterator of BST.
     *
     * @return : Iterator object.
     */
    @Override
    public Iterator<T> iterator() {
        return new BSTIterator();
    }

    /**
     * Node class of each node in BST.
     *
     * @param <T>
     */
    private static class Node<T> {
        /**
         * Data held in node.
         */
        private T data;
        /**
         * Left Pointer of node.
         */
        private Node<T> left;
        /**
         * Right pointer of node.
         */
        private Node<T> right;

        Node(T d) {
            this(d, null, null);
        }

        Node(T d, Node<T> l, Node<T> r) {
            data = d;
            left = l;
            right = r;
        }
    }

}
