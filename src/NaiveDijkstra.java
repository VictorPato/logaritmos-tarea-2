public class NaiveDijkstra implements IDijkstra {
    @Override
    public Ans applyAlgorithm(Graph g, int origin) {
        int V = g.getSize();
        // all distances are not final, and initialized false (by default)
        boolean[] finalDistance = new boolean[V];
        int[] prev = new int[V];
        double[] dist = new double[V];
        for (int i = 0; i < V; i++) {
            dist[i] = Double.MAX_VALUE;
            prev[i] = -1;
        }
        dist[origin] = 0;
        for (int i = 0; i < V; i++) {
            double minDist = Double.MAX_VALUE;
            int minNodo = -1;
            for (int j = 0; j < V; j++) {
                if (!(finalDistance[j]) && (dist[j] < minDist)) {
                    minDist = dist[j];
                    minNodo = j;
                }
            }
            int u = minNodo;
            finalDistance[u] = true;
            for (Edge e : g.getNeighbours(u)) {
                int v = e.getNode();
                double w = e.getWeight();
                if (dist[v] > dist[u] + w) {
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                }
            }
        }
        return new Ans(dist, prev);
    }
}
