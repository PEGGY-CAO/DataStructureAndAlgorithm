import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Your implementation of a binary search tree.
 *
 * @author Yuqi Cao
 * @userid ycao344
 * @GTID 903352025
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        for (T item: data) {
            if (item == null) {
                throw new IllegalArgumentException(
                        "There is a null in data.");
            }
            add(item);
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The added data cannot be null");
        }

        root = helperAdd(root, data);

    }

    /**
     * recursive helper method for add
     * @param node a BSTNode to add from
     * @param data the data needs to be added
     * @return BSTNode return a node for pointer reinforcement
     */
    private BSTNode<T> helperAdd(BSTNode<T> node, T data) {
        if (node == null) {
            node = new BSTNode<>(data);
            size++;
            return node;
        }
        int a = node.getData().compareTo(data);
        if (a == 0) {
            return node;
        } else if (a > 0) {
            //node>data
            node.setLeft(helperAdd(node.getLeft(), data));
        } else {
            node.setRight(helperAdd(node.getRight(), data));
        }
        return node;
    }




    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "The data you want to remove cannot be null.");
        }
        BSTNode<T> dummyNode = new BSTNode<>(null);
        root = removeHelper(root, data, dummyNode);
        return dummyNode.getData();
    }

    /**
     * a private helper to remove the data recursively
     * @param curr the current node to remove from
     * @param data passed in to find and remove
     * @param dummyNode used to store the node we found
     * @return new node after remove
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr,
                                    T data, BSTNode<T> dummyNode) {
        if (curr == null) {
            throw new NoSuchElementException("The data is not found");
        }
        int i = curr.getData().compareTo(data);
        if (i < 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummyNode));
        } else if (i > 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummyNode));
        } else {
            dummyNode.setData(data);
            size--;
            if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(succ(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * private helper to find successor recursively
     * @param cur current node passed in to find the successor
     * @param dummy2 used to store the successor we found
     * @return the new node after delete successor
     */
    private BSTNode<T> succ(BSTNode<T> cur, BSTNode<T> dummy2) {
        if (cur.getLeft() == null) {
            dummy2.setData(cur.getData());
            return cur.getRight();
        } else {
            cur.setLeft(succ(cur.getLeft(), dummy2));
            return cur;
        }
    }
    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        return getHelper(root, data);
    }

    /**
     * private method used to find matched data recursively
     * @param node the BSTNode need to find data from
     * @param data we want to find
     * @return node's data matching the data
     */
    private T getHelper(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("data is not found in this tree");
        } else {
            int i = node.getData().compareTo(data);
            if (i == 0) {
                return node.getData();
            } else if (i < 0) {
                return getHelper(node.getRight(), data);
            } else {
                return getHelper(node.getLeft(), data);
            }
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        return containHelper(root, data);
    }

    /**
     * private method used to recursive find contain data or not
     * @param node the node we want to find from
     * @param data we want to find
     * @return if we find or not
     */
    private boolean containHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        } else {
            int i = node.getData().compareTo(data);
            if (i == 0) {
                return true;
            } else if (i < 0) {
                return containHelper(node.getRight(), data);
            } else {
                return containHelper(node.getLeft(), data);
            }
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorderHelper(root, list);
        return list;
    }

    /**
     * private helper method to preorderly traversal
     * @param node start from
     * @param list we will return in public method
     */
    private void preorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorderHelper(node.getLeft(), list);
            preorderHelper(node.getRight(), list);
        }
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    /**
     * recursively complete inorder traversal
     * @param node start from
     * @param list we want to complete
     */
    private void inorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorderHelper(node.getLeft(), list);
            list.add(node.getData());
            inorderHelper(node.getRight(), list);
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;
    }

    /**
     * recursively do postorder
     * @param node do postorder from
     * @param list return from public method
     */
    private void postorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorderHelper(node.getLeft(), list);
            postorderHelper(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        Queue<BSTNode<T>> queueForNode = new LinkedList<>();
        queueForNode.add(root);
        while (queueForNode.peek() != null) {
            BSTNode<T> temp = queueForNode.poll();
            list.add(temp.getData());
            boolean addL = temp.getLeft() != null
                    ? queueForNode.add(temp.getLeft()) : false;
            boolean addR = temp.getRight() != null
                    ? queueForNode.add(temp.getRight()) : false;
        }
        return list;
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return heightHelper(root);
    }

    /**
     * the helper method to get node's height recursively
     * @param node the node passed in to find height
     * @return the height of the node
     */
    private int heightHelper(BSTNode<T> node) {
        int left = node.getLeft() == null ? -1
                : heightHelper(node.getLeft());
        int right = node.getRight() == null ? -1
                : heightHelper(node.getRight());
        return 1 + Math.max(left, right);
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
