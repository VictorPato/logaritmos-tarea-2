public interface IPriorityQueue {
    void add(int node, double priority);

    double extractMin();

    void decreaseKey(int node, double newPriority);

    boolean isEmpty();
}
