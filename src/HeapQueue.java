public class HeapQueue implements IPriorityQueue {

    private Heap Heap;

    /**
     * Constructor
     *
     * @param maxsize Maximum size of the Queue
     */
    HeapQueue(int maxsize) {
        Heap = new Heap(maxsize);
    }

    @Override
    public void add(int node, double priority) {
        Node n = new Node(node, priority);
        Heap.insert(n);
    }

    @Override
    public int extractMin() {
        return Heap.extractMin().getKey();
    }

    @Override
    public void decreaseKey(int node, double newPriority) {
        Heap.decreaseKey(node, newPriority);
    }

    @Override
    public boolean isEmpty() {
        return Heap.isEmpty();
    }
}

/**
 * Class Heap, implements a Heap
 */
class Heap {

    private Node[] Heap;
    private int size;
    private int[] position;

    private static final int FRONT = 1;

    /**
     * Constructor
     *
     * @param maxsize Maximum size of the heap
     */
    Heap(int maxsize) {
        this.size = 0;
        Heap = new Node[maxsize + 1];
        Heap[0] = new Node(-1, Integer.MIN_VALUE);
        position = new int[maxsize + 1];
    }

    /**
     * Returns the position of the parent of the given pos
     *
     * @param pos Position whose parent we seek
     * @return Position of the parent
     */
    private int parent(int pos) {
        return pos / 2;
    }

    /**
     * Returns the position of the left child of the given pos
     *
     * @param pos Position whose left child we seek
     * @return Position of the left child
     */
    private int leftChild(int pos) {
        return (2 * pos);
    }

    /**
     * Returns the position of the right child of the given pos
     *
     * @param pos Position whose right child we seek
     * @return Position of the right child
     */
    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    /**
     * Returns true if Heap is empty
     */
    boolean isEmpty() {
        return size == 0;
    }

    /**
     * Swaps two elements of the Heap
     *
     * @param fpos First position
     * @param spos Second position
     */
    private void swap(int fpos, int spos) {
        Node element1 = Heap[fpos];
        Node element2 = Heap[spos];
        Heap[fpos] = element2;
        Heap[spos] = element1;
        position[element1.getKey()] = spos;
        position[element2.getKey()] = fpos;
    }

    /**
     * Preserves Heap conditions
     */
    private void heapify(int i) {
        int left = leftChild(i);
        int right = rightChild(i);
        int smallest = i;

        if (left <= size && Heap[left].getValue() < Heap[smallest].getValue())
            smallest = left;
        if (right <= size && Heap[right].getValue() < Heap[smallest].getValue())
            smallest = right;

        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    /**
     * Inserts a new element
     *
     * @param element Node to be inserted
     */
    void insert(Node element) {
        Heap[++size] = element;
        position[element.getKey()] = size;
        for (int i = size; (i > 1) && Heap[i].getValue() < Heap[parent(i)].getValue(); i /= 2) {
            swap(i, parent(i));
        }
    }

    /**
     * Extracts the min element and repairs the Heap using heapify
     *
     * @return Min element
     */
    Node extractMin() {
        Node min = Heap[FRONT];
        Heap[FRONT] = Heap[size--];
        heapify(FRONT);
        position[min.getKey()] = 0;
        return min;
    }

    /**
     * Decrease value of key
     *
     * @param key   Key to decrease
     * @param value New value
     */
    void decreaseKey(int key, double value) {
        // get node
        int pos = position[key];

        if (pos == 0) return;

        Node n = Heap[pos];

        // change value
        n.setValue(value);

        // destroy
        swap(pos, size);
        size--;

        // insert again
        insert(n);
    }
}

/**
 * Class Node, it stores a key, a value and a position in the Heap
 */
class Node {

    private int key;
    private double value;

    /**
     * Constructor
     *
     * @param key   Key
     * @param value Value
     */
    Node(int key, double value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns key
     */
    int getKey() {
        return key;
    }

    /**
     * Returns value
     */
    double getValue() {
        return value;
    }

    /**
     * Sets value
     *
     * @param value New value to be set
     */
    void setValue(double value) {
        this.value = value;
    }
}
