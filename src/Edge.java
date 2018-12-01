/*
 * Class for edge for graph
 */
public class Edge {
    private int node;
    private int weight;

    public Edge(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }

    public int getNode() {
        return node;
    }

    public int getWeight() {
        return weight;
    }
}
