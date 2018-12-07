/*
 * Class for edge for graph
 */
public class Edge {
    private int node;
    private double weight;

    public Edge(int node, double weight) {
        this.node = node;
        this.weight = weight;
    }

    public int getNode() {
        return node;
    }

    public double getWeight() {
        return weight;
    }
}
