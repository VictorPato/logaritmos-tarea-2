public interface IPriorityQueue {
    void add(int node, double priority);

    int extractMin();

    void decreaseKey(int node, double newPriority);

    boolean isEmpty();
}
