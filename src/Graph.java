import java.util.ArrayList;

/*
 * Representation of a graph, where node names are ints
 */
public class Graph {
    private int size;
    private ArrayList<ArrayList<Edge>> E;

    public int getSize() {
        return size;
    }

    public Graph(int size) {
        this.size = size;
        E = new ArrayList<ArrayList<Edge>>(size);
        for (int i = 0; i < size; i++) {
            E.add(i, new ArrayList<Edge>());
        }
    }

    public void addEdge(int node, Edge e) {
        E.get(node).add(e);
    }

    public void addDoubleEdge(int node1, int node2, double weight){
        E.get(node1).add(new Edge(node2,weight));
        E.get(node2).add(new Edge(node1,weight));
    }

    public ArrayList<Edge> getNeighbours(int node) {
        return E.get(node);
    }
}
