import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FibonacciHeap implements IPriorityQueue {

    BinomialTree min;

    public FibonacciHeap() {
    }

    /**
     * Adds the node to the Heap. If priority is equal or lower, update min.
     *
     * @param node     Node to add
     * @param priority Priority of node
     */
    @Override
    public void add(int node, int priority) {
        BinomialTree newNode = new BinomialTree(node, priority);
        if (min == null) {
            min = newNode;
        } else {
            min = min.uniteWith(newNode);
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
            min = nextRootTree.uniteWith(min.child);
        }
        makeBinomialHeap();
        return ans;
    }

    // TODO
    @Override
    public void decreaseKey(int node, int newPriority) {

    }

    @Override
    public boolean isEmpty() {
        return min == null;
    }

    // TODO
    private BinomialTree makeBinomialHeap() {
        return null;
    }
}

/**
 * Implements a node which has values node and priority
 * It also uses a double-linked circular list for siblings
 */
class BinomialTree {
    int node;
    int priority;
    int k;
    boolean isMarked;
    BinomialTree parent;
    BinomialTree prev;
    BinomialTree next;
    BinomialTree child;

    public BinomialTree(int node, int priority) {
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
    public BinomialTree uniteWith(BinomialTree otherTree) {
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
}
