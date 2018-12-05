import java.util.ArrayList;

public class FibonacciHeap implements IPriorityQueue {

    BinomialTree min;

    FibonacciHeap(int numberOfNodes) {
    }

    /**
     * Adds the node to the Heap. If priority is equal or lower, update min.
     *
     * @param node
     * @param priority
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

    @Override
    public int extractMin() {
        BinomialTree minNext = min.next;
        return 0;
    }

    @Override
    public void decreaseKey(int node, int newPriority) {

    }

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
        otherTree.prev.next = next;
        next.prev = otherTree.prev;
        next = otherTree;
        otherTree.prev = this;
        return priority <= otherTree.priority ? this : otherTree;
    }
}
