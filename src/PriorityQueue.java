public interface PriorityQueue {
    void add(int node, int priority);

    int extractMin();

    void decreaseKey(int node, int newPriority);
}
