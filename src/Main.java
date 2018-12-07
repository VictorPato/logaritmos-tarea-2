import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Tests.testGraph();
        Tests.testDijkstraCorrectness();
        Tests.testBinomialTree();
        Tests.testFibonacciHeap();
        Tests.testFibonacciHeapDecreaseKey();
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
        BinomialTree result = t1.uniteListWith(t2);
        assert (result == t1);
        assert (t1.next == t2);
        assert (t2.next == t1);
        assert (t3.next == t3);
        result = t1.uniteListWith(t3);
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
        t1.uniteListWith(t2);
        t2.uniteListWith(t3);
        ta.uniteListWith(tb);
        tb.uniteListWith(tc);
        t1.uniteListWith(ta);
        assert (t1.next == ta);
        assert (ta.prev == t1);
        assert (tc.next == t2);
        assert (t2.prev == tc);
        System.out.println("Binomial Tree works fine");
    }

    static void testFibonacciHeap() {
        FibonacciHeap FH = new FibonacciHeap();
        FH.add(0, 1);
        FH.add(1, 5);
        FH.add(2, 0);
        int ans = FH.extractMin();
        assert (ans == 2);
        ans = FH.extractMin();
        assert (ans == 0);
        ans = FH.extractMin();
        assert (ans == 1);
        FH.add(3, 10);
        FH.add(4, 9);
        FH.add(5, 11);
        FH.add(6, 8);
        FH.add(7, 7);
        ans = FH.extractMin();
        assert (ans == 7);
        ans = FH.extractMin();
        assert (ans == 6);
        ans = FH.extractMin();
        assert (ans == 4);
        FH.add(8, 0);
        FH.add(9, 100);
        ans = FH.extractMin();
        assert (ans == 8);
        ans = FH.extractMin();
        assert (ans == 3);
        ans = FH.extractMin();
        assert (ans == 5);
        ans = FH.extractMin();
        assert (ans == 9);
        System.out.println("Fibonacci Heap works fine");
    }

    static void testFibonacciHeapDecreaseKey() {
        FibonacciHeap FH = new FibonacciHeap();
        FH.add(0, 1);
        FH.add(1, 5);
        FH.add(2, 0);
        FH.add(3, 7);
        FH.decreaseKey(3, 3);
        int ans = FH.extractMin();
        assert (ans == 2);
        ans = FH.extractMin();
        assert (ans == 0);
        ans = FH.extractMin();
        assert (ans == 3);
        ans = FH.extractMin();
        assert (ans == 1);
        for (int i = 1; i <= 10; i++) {
            FH.add(i, 2 * i);
        }
        ans = FH.extractMin();
        assert (ans == 1);
        FH.decreaseKey(9,0);
        FH.decreaseKey(8,1);
        FH.decreaseKey(7,2);
        FH.decreaseKey(6,3);
        ans = FH.extractMin();
        assert (ans == 9);
        ans = FH.extractMin();
        assert (ans == 8);
        ans = FH.extractMin();
        assert (ans == 7);
        ans = FH.extractMin();
        assert (ans == 6);
        ans = FH.extractMin();
        assert (ans == 2);
        System.out.println("Decrease Key works fine");
    }
}

