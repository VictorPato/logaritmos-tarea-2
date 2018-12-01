public interface IDijkstra {
    /*
     *  Applies Dijkstra algorith
     *  Returns an array with size 2
     *  First element is an array of distances from origin
     *  Second element is an array of the previous node, represented as ints
     */
    int[][] applyAlgorithm(Graph g, int origin);
}
