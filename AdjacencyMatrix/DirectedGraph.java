package AdjacencyMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DirectedGraph extends Graph {
    public DirectedGraph(int size) {
        super(size);
    }

    @Override
    public void addEdge(int start, int end) {
        if (start >= numVertices || end >= numVertices || start < 0 || end < 0 || adjMatrix[start][end] == 1)
            return;
        adjMatrix[start][end] = 1; // Only one-way direction
    }

    @Override
    public void deleteEdge(int start, int end) {
        if (start >= numVertices || end >= numVertices || start < 0 || end < 0)
            return;
        adjMatrix[start][end] = 0;
    }

    // Compute in-degree of a vertex
    public int halfIn(int vertex) {
        int inDegree = 0;
        for (int i = 0; i < numVertices; i++) {
            inDegree += adjMatrix[i][vertex];
        }
        return inDegree;
    }

    // Compute out-degree of a vertex
    public int halfOut(int vertex) {
        int outDegree = 0;
        for (int j = 0; j < numVertices; j++) {
            outDegree += adjMatrix[vertex][j];
        }
        return outDegree;
    }

    // Compute and print both half-in and half-out degrees
    public void computeHalfDegrees() {
        for (int i = 0; i < numVertices; i++) {
            int halfIn = halfIn(i);
            int halfOut = halfOut(i);
            System.out.println("Vertex: [" + i + "]\nHalf In: " + halfIn + "\nHalf Out: " + halfOut);
        }
    }

    public UndirectedGraph undirectConvert() {
        UndirectedGraph newGraph = new UndirectedGraph(numVertices);

        // Copy the diagonal
        for (int i = 0; i < numVertices; i++) {
            newGraph.adjMatrix[i][i] = adjMatrix[i][i];
        }

        // Convert to undirected graph
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] == 1) {
                    newGraph.adjMatrix[i][j] = 1;
                    newGraph.adjMatrix[j][i] = 1; // Mirror the connection
                }
            }
        }
        return newGraph;
    }

    public boolean isWeaklyConnected() {
        UndirectedGraph convertedGraph = undirectConvert();
        return convertedGraph.isConnected();
    }

    public boolean isEuler() {
        // Check weak connectivity
        if (!isWeaklyConnected()) {
            return false;
        }

        // For Eulerian directed graph: in-degree == out-degree for every vertex
        for (int i = 0; i < numVertices; i++) {
            if (halfIn(i) != halfOut(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isHalfEuler() {
        if (!isWeaklyConnected()) {
            return false;
        }

        int startCandidate = 0; // out = in + 1
        int endCandidate = 0; // in = out + 1

        for (int i = 0; i < numVertices; i++) {
            int in = halfIn(i);
            int out = halfOut(i);

            if (in == out) {
                continue;
            } else if (in == out + 1) {
                endCandidate++;
            } else if (out == in + 1) {
                startCandidate++;
            } else {
                return false; // More than 1 difference => not half-eulerian
            }
        }

        return startCandidate == 1 && endCandidate == 1;
    }

    public List<Integer> findEulerCycle() {
        if (!isEuler()) {
            System.out.println("Graph is not Eulerian.");
            return null;
        }

        int[][] copyMatrix = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            copyMatrix[i] = Arrays.copyOf(adjMatrix[i], numVertices);
        }

        Stack<Integer> stack = new Stack<>();
        List<Integer> result = new ArrayList<>();

        int startVertex = 0;
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            int v = stack.peek();
            boolean found = false;
            for (int u = 0; u < numVertices; u++) {
                if (copyMatrix[v][u] > 0) {
                    stack.push(u);
                    copyMatrix[v][u]--;
                    found = true;
                    break;
                }
            }

            if (!found) {
                result.add(stack.pop());
            }
        }

        return result;
    }

}