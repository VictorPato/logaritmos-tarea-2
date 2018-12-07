import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class FibonacciHeap implements IPriorityQueue {

    BinomialTree min;
    HashMap<Integer, BinomialTree> pointerToNode;

    public FibonacciHeap() {
        pointerToNode = new HashMap<Integer, BinomialTree>();
    }

    /**
     * Adds the node to the Heap. If priority is equal or lower, update min.
     *
     * @param node     Node to add
     * @param priority Priority of node
     */
    @Override
    public void add(int node, double priority) {
        BinomialTree newNode = new BinomialTree(node, priority);
        pointerToNode.put(node, newNode);
        if (min == null) {
            min = newNode;
        } else {
            min = min.uniteListWith(newNode);
        }
    }

    /**
     * Extracts the node with lowest priority. Afterwards it turns the Fibonacci Heap into a Binomial Heap to extract
     * the new minimum.
     *
     * @return The node with lowest priority.
     */
    @Override
    public int extractMin() {
        if (min == null) {
            throw new NoSuchElementException();
        }
        int ans = min.node;
        if (min.child != null) {
            min.child.parent = null;
            BinomialTree minChild = min.child.next;
            while (minChild != min.child) {
                minChild.parent = null;
                minChild = minChild.next;
            }
        }
        BinomialTree nextRootTree = min.remove();
        if (nextRootTree == null) {
            min = min.child;
        } else {
            min = uniteLists(nextRootTree, min.child);
        }
        makeBinomialHeap();
        pointerToNode.remove(ans);
        return ans;
    }

    /**
     * Decreases the priority of node received to the new priority. Causes a recursive cut if necessary.
     *
     * @param node        Node to update.
     * @param newPriority New priority.
     */
    @Override
    public void decreaseKey(int node, double newPriority) {
        BinomialTree treeToDecrease = pointerToNode.get(node);
        treeToDecrease.priority = newPriority;
        BinomialTree parentTree = treeToDecrease.parent;
        if (treeToDecrease.parent != null && parentTree.priority > newPriority) {
            cut(treeToDecrease);
            recursiveCut(parentTree);
        }
        if (newPriority < min.priority) {
            min = treeToDecrease;
        }

    }

    /**
     * If the node is marked, cut it and recursive cut the parent. If not, mark it.
     *
     * @param node The node to mark/cut.
     */
    public void recursiveCut(BinomialTree node) {
        if (node.parent == null) {
            return;
        }
        if (node.isMarked) {
            BinomialTree parent = node.parent;
            cut(node);
            recursiveCut(parent);
            node.isMarked = false;
        } else {
            node.isMarked = true;
        }
    }

    /**
     * Cuts a BT, adding it as sibling to the root. Decreases parent's degree.
     *
     * @param node The node to cut.
     */
    public void cut(BinomialTree node) {
        node.isMarked = false;
        BinomialTree nextNode = node.remove();
        if (node.parent.child == node) {
            node.parent.child = nextNode;
        }
        node.parent.k--;
        node.parent = null;
        uniteLists(node, min);
    }

    /**
     * Check if heap has elements left.
     *
     * @return True if heap has no elements left. False otherwise.
     */
    @Override
    public boolean isEmpty() {
        return min == null;
    }


    /**
     * Turns the Fibonacci Heap into a Binomial Heap temporally to extract the minimum priority node
     */
    private void makeBinomialHeap() {
        if (min == null) {
            return;
        }
        ArrayList<BinomialTree> currentTrees = new ArrayList<>(Collections.nCopies(min.k + 2, null));
        currentTrees.set(min.k, min);
        BinomialTree rootTree = min.next;
        min.next = min;
        min.prev = min;
        // for each root tree, we combine the ones with same degree until all of them have different degrees
        while (min != rootTree) {
            BinomialTree nextRootTree = rootTree.next;
            rootTree.next = rootTree;
            rootTree.prev = rootTree;
            while (currentTrees.size() < rootTree.k + 2) {
                currentTrees.add(null);
            }
            while (currentTrees.get(rootTree.k) != null) {
                BinomialTree otherRootTree = currentTrees.get(rootTree.k);
                currentTrees.set(rootTree.k, null);
                rootTree = otherRootTree.combine(rootTree);
            }
            while (currentTrees.size() < rootTree.k + 2) {
                currentTrees.add(null);
            }
            currentTrees.set(rootTree.k, rootTree);
            rootTree = nextRootTree;
        }
        // we take all of the new root trees and link them together
        min = null;
        for (BinomialTree tree : currentTrees) {
            if (tree == null) {
                continue;
            }
            min = uniteLists(min, tree);
        }
    }

    /**
     * Same as UniteListsWith from BinomialTree, but takes care of null values
     *
     * @param t1 First BT
     * @param t2 Second BT
     * @return The BT with lower priority
     */
    static BinomialTree uniteLists(BinomialTree t1, BinomialTree t2) {
        if (t1 == null && t2 == null) {
            return null;
        }
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        return t1.uniteListWith(t2);
    }
}

/**
 * Implements a node which has values node and priority
 * It also uses a double-linked circular list for siblings
 */
class BinomialTree {
    int node;
    double priority;
    int k;
    boolean isMarked;
    BinomialTree parent;
    BinomialTree prev;
    BinomialTree next;
    BinomialTree child;

    public BinomialTree(int node, double priority) {
        this.node = node;
        this.priority = priority;
        this.k = 0;
        this.prev = this;
        this.next = this;
    }

    /**
     * Unites the linked-lists of two nodes
     *
     * @param otherTree The other node
     * @return The smaller node
     */
    public BinomialTree uniteListWith(BinomialTree otherTree) {
        if (otherTree == null) {
            return this;
        }
        otherTree.prev.next = next;
        next.prev = otherTree.prev;
        next = otherTree;
        otherTree.prev = this;
        return priority <= otherTree.priority ? this : otherTree;
    }

    /**
     * Removes the node from its lists
     *
     * @return The next element of the list, or null if it was the last element
     */
    public BinomialTree remove() {
        if (next == this) {
            return null;
        }
        prev.next = next;
        next.prev = prev;
        BinomialTree nextNode = next;
        next = this;
        prev = this;
        return nextNode;
    }

    /**
     * Takes 2 trees and makes the one with the highest priority the child of the other one
     * It's up to the user to make sure that both trees have same k and correct siblings (none) before using this function
     *
     * @param otherTree The other tree
     * @return The tree with lower priority
     */
    public BinomialTree combine(BinomialTree otherTree) {
        BinomialTree lowerPriority;
        BinomialTree higherPriority;
        if (priority <= otherTree.priority) {
            lowerPriority = this;
            higherPriority = otherTree;
        } else {
            lowerPriority = otherTree;
            higherPriority = this;
        }
        lowerPriority.child = higherPriority.uniteListWith(lowerPriority.child);
        higherPriority.parent = lowerPriority;
        lowerPriority.k++;
        return lowerPriority;
    }
}
