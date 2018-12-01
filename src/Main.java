import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Tests.testGraph();
    }
}

class Tests{
    static void testGraph(){
        Graph g = new Graph(5);
        g.addDoubleEdge(0,1,5);
        g.addDoubleEdge(0,2,3);
        g.addDoubleEdge(1,3,4);
        assert(g.getNeighbours(2).get(0).getNode() == 0);
        assert(g.getNeighbours(0).size() == 2);
        System.out.println("Graph works fine");
    }
}

