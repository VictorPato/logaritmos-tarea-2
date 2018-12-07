public interface IDijkstra {
    /*
     *  Applies Dijkstra algorith
     *  Returns an array with size 2
     *  First element is an array of distances from origin
     *  Second element is an array of the previous node, represented as ints
     */
    Ans applyAlgorithm(Graph g, int origin);
}

class Ans {

    private double[] dist;
    private int[] prev;

    Ans(double[] dist, int[] prev) {
        this.dist = dist;
        this.prev = prev;
    }

    public double[] getDist() {
        return dist;
    }

    public int[] getPrev() {
        return prev;
    }
}
