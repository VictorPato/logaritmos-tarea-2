import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Tests.runTests();

        Graph[][] graphs = Tests.createGraphs();

        long[] naiveStats = Tests.dijkstraTime("Naive", graphs);
        long[] heapStats = Tests.dijkstraTime("Heap", graphs);
        long[] fibonacciStats = Tests.dijkstraTime("Fibonacci", graphs);

        System.out.println("\nNaive Stats:");
        System.out.println("10n Edges: " + naiveStats[0]);
        System.out.println("100n Edges: " + naiveStats[1]);
        System.out.println("1000n Edges: " + naiveStats[2]);

        System.out.println("\nHeap Stats:");
        System.out.println("10n Edges: " + heapStats[0]);
        System.out.println("100n Edges: " + heapStats[1]);
        System.out.println("1000n Edges: " + heapStats[2]);

        System.out.println("\nFibonacci Stats:");
        System.out.println("10n Edges: " + fibonacciStats[0]);
        System.out.println("100n Edges: " +fibonacciStats[1]);
        System.out.println("1000n Edges: " + fibonacciStats[2]);

    }
}

class Tests {
    static void runTests() {
        testGraph();
        testBinomialTree();
        testQueue("Heap");
        testDecreaseKey("Heap");
        testQueue("Fibonacci");
        testDecreaseKey("Fibonacci");
        testDijkstraCorrectness("Naive");
        testDijkstraCorrectness("Heap");
        testDijkstraCorrectness("Fibonacci");
    }

    static void testGraph() {
        Graph g = new Graph(5);
        g.addDoubleEdge(0, 1, 5);
        g.addDoubleEdge(0, 2, 3);
        g.addDoubleEdge(1, 3, 4);
        assert (g.getNeighbours(2).get(0).getNode() == 0);
        assert (g.getNeighbours(0).size() == 2);
        System.out.println("Graph works fine");
    }

    static void testDijkstraCorrectness(String type) {
        IDijkstra dijkstra;
        switch (type) {
            case "Naive":
                dijkstra = new NaiveDijkstra();
                break;
            case "Heap":
                dijkstra = new PriorityQueueDijkstra(new HeapQueue(100000));
                break;
            case "Fibonacci":
                dijkstra = new PriorityQueueDijkstra(new FibonacciHeap());
                break;
            default:
                System.out.println("Please provide a valid type");
                return;
        }
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
        double[] ans = {0, 7, 9, 20, 20, 11};
        int[] last = {-1, 0, 0, 2, 5, 2};

        Ans answer = dijkstra.applyAlgorithm(g, 0);
        for (int i = 0; i < 6; i++) {
            assert (ans[i] == answer.getDist()[i]);
            assert (last[i] == answer.getPrev()[i]);
        }
        System.out.println(type + "Dijkstra works fine");
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

    static void testQueue(String type) {
        IPriorityQueue Q;
        if (type.equals("Heap"))
            Q = new HeapQueue(100);
        else if (type.equals("Fibonacci"))
            Q = new FibonacciHeap();
        else {
            System.out.println("Please provide a valid Queue type");
            return;
        }
        Q.add(0, 1);
        Q.add(1, 5);
        Q.add(2, 0);
        int ans = Q.extractMin();
        assert (ans == 2);
        ans = Q.extractMin();
        assert (ans == 0);
        ans = Q.extractMin();
        assert (ans == 1);
        Q.add(3, 10);
        Q.add(4, 9);
        Q.add(5, 11);
        Q.add(6, 8);
        Q.add(7, 7);
        ans = Q.extractMin();
        assert (ans == 7);
        ans = Q.extractMin();
        assert (ans == 6);
        ans = Q.extractMin();
        assert (ans == 4);
        Q.add(8, 0);
        Q.add(9, 100);
        ans = Q.extractMin();
        assert (ans == 8);
        ans = Q.extractMin();
        assert (ans == 3);
        ans = Q.extractMin();
        assert (ans == 5);
        ans = Q.extractMin();
        assert (ans == 9);
        System.out.println(type + " Queue works fine");
    }

    static void testDecreaseKey(String type) {
        IPriorityQueue Q;
        if (type.equals("Heap"))
            Q = new HeapQueue(100);
        else if (type.equals("Fibonacci"))
            Q = new FibonacciHeap();
        else {
            System.out.println("Please provide a valid Queue type");
            return;
        }
        Q.add(0, 1);
        Q.add(1, 5);
        Q.add(2, 0);
        Q.add(3, 7);
        Q.decreaseKey(3, 3);
        int ans = Q.extractMin();
        assert (ans == 2);
        ans = Q.extractMin();
        assert (ans == 0);
        ans = Q.extractMin();
        assert (ans == 3);
        ans = Q.extractMin();
        assert (ans == 1);
        for (int i = 1; i <= 10; i++) {
            Q.add(i, 2 * i);
        }
        ans = Q.extractMin();
        assert (ans == 1);
        Q.decreaseKey(9, 0);
        Q.decreaseKey(8, 1);
        Q.decreaseKey(7, 2);
        Q.decreaseKey(6, 3);
        ans = Q.extractMin();
        assert (ans == 9);
        ans = Q.extractMin();
        assert (ans == 8);
        ans = Q.extractMin();
        assert (ans == 7);
        ans = Q.extractMin();
        assert (ans == 6);
        ans = Q.extractMin();
        assert (ans == 2);
        Q.add(1, 2);
        Q.add(2, 3);
        Q.add(3, 4);
        Q.decreaseKey(2, 0);
        ans = Q.extractMin();
        assert (ans == 2);
        Q.decreaseKey(3, 1);
        Q.decreaseKey(1, 0);
        ans = Q.extractMin();
        assert (ans == 1);
        ans = Q.extractMin();
        assert (ans == 3);
        System.out.println("Decrease Key works fine");
    }

    static Graph createGraph(int e_factor, long randomSeed) {
        int n = 100000;
        int e = e_factor * n;

        Random rnd = new Random(randomSeed);

        Graph g = new Graph(n);
        double weight;

        // ensure connectivity
        for (int i = 0; i < n - 1; i++) {
            weight = rnd.nextDouble(); // weight is in the interval [0, 1)
            weight = weight == 0 ? 1 : weight; // now weight is in the interval (0, 1]
            g.addEdge(i, new Edge(i + 1, weight));
        }

        int node1, node2;

        // add e - n + 1 random edges
        for (int i = 0; i <= e - n; i++) {
            node1 = rnd.nextInt(n);
            node2 = rnd.nextInt(n);
            weight = rnd.nextDouble(); // weight is in the interval [0, 1)
            weight = weight == 0 ? 1 : weight; // now weight is in the interval (0, 1]
            g.addEdge(node1, new Edge(node2, weight));
        }

        return g;
    }

    static Graph[][] createGraphs() {
        long startTime = System.nanoTime();
        Graph g10_1 = createGraph(10, 123456789);
        Graph g10_2 = createGraph(10, 987654321);
        Graph g10_3 = createGraph(10, 321654987);
        Graph g10_4 = createGraph(10, 123789456);
        Graph g10_5 = createGraph(10, 789456123);
        long finishTime = System.nanoTime();
        long elapsedTime = (finishTime - startTime) / 1000000;
        System.out.println("Graphs with 10n edges creation time: " + elapsedTime + "ms");
        Graph[] graphs10 = {g10_1, g10_2, g10_3, g10_4, g10_5};

        startTime = System.nanoTime();
        Graph g100_1 = createGraph(100, 123456789);
        Graph g100_2 = createGraph(100, 987654321);
        Graph g100_3 = createGraph(100, 321654987);
        Graph g100_4 = createGraph(100, 123789456);
        Graph g100_5 = createGraph(100, 789456123);
        finishTime = System.nanoTime();
        elapsedTime = (finishTime - startTime) / 1000000;
        System.out.println("Graphs with 100n edges creation time: " + elapsedTime + "ms");
        Graph[] graphs100 = {g100_1, g100_2, g100_3, g100_4, g100_5};

        startTime = System.nanoTime();
        Graph g1000_1 = createGraph(1000, 123456789);
        finishTime = System.nanoTime();
        elapsedTime = (finishTime - startTime) / 1000000;
        System.out.println("Graphs with 1000n edges creation time: " + elapsedTime + "ms");
        Graph[] graphs1000 = {g1000_1};

        return new Graph[][]{graphs10, graphs100, graphs1000};
    }

    static long[] dijkstraTime(String type, Graph[][] graphs) {
        Graph[] graphs10, graphs100, graphs1000;
        graphs10 = graphs[0];
        graphs100 = graphs[1];
        graphs1000 = graphs[2];

        IDijkstra dijkstra;
        switch (type) {
            case "Naive":
                dijkstra = new NaiveDijkstra();
                break;
            case "Heap":
                dijkstra = new PriorityQueueDijkstra(new HeapQueue(100000));
                break;
            case "Fibonacci":
                dijkstra = new PriorityQueueDijkstra(new FibonacciHeap());
                break;
            default:
                System.out.println("Please provide a valid type");
                return new long[3];
        }


        long meanG10 = dijkstraApply(dijkstra, graphs10);
        long meanG100 = dijkstraApply(dijkstra, graphs100);
        long meanG1000 = dijkstraApply(dijkstra, graphs1000);

        return new long[]{meanG10, meanG100, meanG1000};
    }

    static long dijkstraApply(IDijkstra dijkstra, Graph[] graphs) {
        long startTime, finishTime, elapsedTime;
        long[] times = new long[graphs.length];
        for (int i = 0; i < graphs.length; i++) {
            startTime = System.nanoTime();
            dijkstra.applyAlgorithm(graphs[i], 0);
            finishTime = System.nanoTime();
            elapsedTime = (finishTime - startTime) / 1000000;
            times[i] = elapsedTime;
        }
        long sum = 0;
        for (long time : times) {
            sum += time;
        }
        return sum / times.length;
    }
}

