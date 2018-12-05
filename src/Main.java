import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Tests.testBinomialTree();
    }
}

class Tests {
    static void testGraph() {
        Graph g = new Graph(5);
        g.addDoubleEdge(0, 1, 5);
        g.addDoubleEdge(0, 2, 3);
        g.addDoubleEdge(1, 3, 4);
        assert (g.getNeighbours(2).get(0).getNode() == 0);
        assert (g.getNeighbours(0).size() == 2);
        System.out.println("Graph works fine");
    }

    static void testDijkstraCorrectness() {
        Graph g = new Graph(6);
        // graph from the wikipedia page for dijkstras algorithm
        // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
        g.addDoubleEdge(0, 1, 7);
        g.addDoubleEdge(0, 2, 9);
        g.addDoubleEdge(0, 5, 14);
        g.addDoubleEdge(1, 2, 10);
        g.addDoubleEdge(1, 3, 15);
        g.addDoubleEdge(2, 3, 11);
        g.addDoubleEdge(2, 5, 2);
        g.addDoubleEdge(3, 4, 6);
        g.addDoubleEdge(4, 5, 9);
        int[] ans = {0, 7, 9, 20, 20, 11};
        int[] last = {-1, 0, 0, 2, 5, 2};

        IDijkstra naive = new NaiveDijkstra();
        int[][] naiveAns = naive.applyAlgorithm(g, 0);
        for (int i = 0; i < 6; i++) {
            assert (ans[i] == naiveAns[0][i]);
            assert (last[i] == naiveAns[1][i]);
        }
        System.out.println("Dijkstra works fine");
    }

    static void testBinomialTree() {
        BinomialTree t1 = new BinomialTree(1, 1);
        BinomialTree t2 = new BinomialTree(2, 2);
        BinomialTree t3 = new BinomialTree(3, 3);
        BinomialTree result = t1.uniteWith(t2);
        assert (result == t1);
        assert (t1.next == t2);
        assert (t2.next == t1);
        assert (t3.next == t3);
        result = t1.uniteWith(t3);
        assert (result == t1);
        assert (t1.next == t3);
        assert (t1.prev == t2);
        assert (t3.next == t2);
        assert (t2.prev == t3);
        t1 = new BinomialTree(1, 1);
        t2 = new BinomialTree(2, 2);
        t3 = new BinomialTree(3, 3);
        BinomialTree ta = new BinomialTree(1, 1);
        BinomialTree tb = new BinomialTree(1, 1);
        BinomialTree tc = new BinomialTree(1, 1);
        t1.uniteWith(t2);
        t2.uniteWith(t3);
        ta.uniteWith(tb);
        tb.uniteWith(tc);
        t1.uniteWith(ta);
        assert (t1.next == ta);
        assert (ta.prev == t1);
        assert (tc.next == t2);
        assert (t2.prev == tc);
        System.out.println("Binomial Tree works fine");
    }
}

