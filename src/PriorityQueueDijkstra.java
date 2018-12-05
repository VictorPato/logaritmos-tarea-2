import java.util.ArrayList;

public class PriorityQueueDijkstra implements IDijkstra {
    
    private IPriorityQueue Queue;

    PriorityQueueDijkstra(IPriorityQueue queue) {
        Queue = queue;
    }

    @Override
    public int[][] applyAlgorithm(Graph g, int origin) {
        IPriorityQueue Q = Queue
        int V = g.getSize();
        int[] prev = new int[V];
        int[] dist = new int[V];
        for (int v = 0; v < V; v++) {
            if (v == origin)
                dist[v] = 0 // distance from origin to origin is 0
            else
                dist[v] = Integer.MAX_VALUE; // distance from origin to v is unknown
            prev[v] = null // predecessor of v
            Q.add(v, dist[v]) // add v to queue with its distance as priority
        }
        int m;
        ArrayList<Edge> neighbours;
        int v, w;
        while (!Q.isEmpty()) {
            m = Q.extractMin() // extract vertex with best priority
            neighbours = g.getNeighbours(m);
            // every neighbour that is still in Q
            for (Edge e : neighbours) {
                v = e.getNode();
                w = e.getWeight();
                newDist = dist[m] + w // distance from origin to v using edge (m,v)
                if (newDist < dist[v]) {
                    dist[v] = newDist // update distance of v
                    prev[v] = m // set m as v's predecessor
                    Q.decreaseKey(v, newDist) // increase v's priority
                }
            }
        }
        return dist, prev
    }

}