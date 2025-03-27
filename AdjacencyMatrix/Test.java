package AdjacencyMatrix;

public class Test {
    public static void main(String[] args) {
        System.out.println("Undirected Graph:");
        UndirectedGraph uGraph = new UndirectedGraph(5);
        uGraph.addEdge(0, 1);
        uGraph.addEdge(0, 3);
        uGraph.addEdge(0, 4);
        uGraph.addEdge(0, 4);
        uGraph.addEdge(1, 1);
        uGraph.addEdge(1, 2);
        uGraph.addEdge(1, 3);
        uGraph.addEdge(1, 4);
        uGraph.addEdge(3, 4);
        uGraph.addEdge(3, 4);
        uGraph.addEdge(3, 4);
        uGraph.printGraph();
        System.out.println(uGraph.isConnected());
        System.out.println("BFS path: ");
        BFS.search(uGraph, 0);
        System.out.println("____________________________");
        System.out.println("DFS path: ");
        DFS.search(uGraph, 0);
        // System.out.println("\nDirected Graph:");
        // DirectedGraph dGraph = new DirectedGraph(4);
        // dGraph.addEdge(0, 1);
        // dGraph.addEdge(1, 2);
        // dGraph.addEdge(2, 3);
        // dGraph.printEdges();
        // dGraph.computeHalfDegrees();
        // System.out.println("Number of edges: " + dGraph.countEdges());
    }
}
