package AdjacencyMatrix;

import java.util.*;

public class UndirectedGraph extends Graph {
    public UndirectedGraph(int size) {
        super(size);
    }

    @Override
    public void addEdge(int start, int end) {
        if (start >= numVertices || end >= numVertices || start < 0 || end < 0)
            return;
        adjMatrix[start][end] = 1;
        adjMatrix[end][start] = 1; // Symmetric for undirected graph
    }

    @Override
    public void deleteEdge(int start, int end) {
        if (start >= numVertices || end >= numVertices || start < 0 || end < 0)
            return;
        adjMatrix[start][end] = 0;
        adjMatrix[end][start] = 0;
    }

    public boolean isConnected() {
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        int count = 1;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[vertex][i] == 1 && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                    count++;
                }
            }
        }
        return count == numVertices;
    }

    public boolean isEuler() {
        // If not connected, it's not Eulerian
        if (!isConnected()) {
            return false;
        }

        // Check if all degrees are even
        for (int i = 0; i < numVertices; i++) {
            int degree = 0;
            for (int j = 0; j < numVertices; j++) {
                degree += adjMatrix[i][j];
            }
            if (degree % 2 != 0) {
                return false; // Odd degree found
            }
        }
        return true; // All even degrees and connected
    }

    public boolean isHalfEuler() {
        if (!isConnected()) {
            return false;
        }

        int oddCount = 0;
        for (int i = 0; i < numVertices; i++) {
            int degree = 0;
            for (int j = 0; j < numVertices; j++) {
                degree += adjMatrix[i][j];
            }
            if (degree % 2 != 0) {
                oddCount++;
            }
        }

        return oddCount == 2;
    }

    public List<Integer> findEulerCycle() {
        // Check if the graph is Eulerian first
        if (!isEuler()) {
            throw new IllegalStateException("Graph is not Eulerian. No Euler cycle exists.");
        }

        // Step 1: Initialize
        // Create a new graph h with the same number of vertices
        UndirectedGraph h = new UndirectedGraph(numVertices);

        // Copy the adjacency matrix from the current graph into the new graph h
        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(adjMatrix[i], 0, h.adjMatrix[i], 0, numVertices);
        }

        // Initialize the Euler cycle list and start with vertex 0
        List<Integer> c = new ArrayList<>();
        c.add(0); // Start from vertex 0

        // Count all edges in the graph h
        int m = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i; j < numVertices; j++) {
                m += h.adjMatrix[i][j];
            }
        }

        // Step 2: Build Euler cycle
        while (m > 0) {
            // 2.1 Find vertex v in c that still has unused edges
            int v = -1;
            for (int i : c) {
                int degree = 0;
                for (int j = 0; j < numVertices; j++) {
                    degree += h.adjMatrix[i][j];
                }
                if (degree > 0) {
                    v = i;
                    break;
                }
            }

            if (v == -1)
                break; // No more unused edges

            // 2.2 Traverse from v to build subcircuit
            List<Integer> subCircuit = new ArrayList<>();
            subCircuit.add(v);
            int current = v;

            while (true) {
                boolean found = false;
                for (int j = 0; j < numVertices; j++) {
                    if (h.adjMatrix[current][j] > 0) {
                        subCircuit.add(j);
                        h.deleteEdge(current, j);
                        m--;
                        current = j;
                        found = true;
                        break;
                    }
                }
                if (!found)
                    break;
            }

            // 2.3 Insert subCircuit into c at index of v
            subCircuit.remove(subCircuit.size() - 1); // Avoid duplicate
            int insertIndex = c.indexOf(v);
            c.addAll(insertIndex, subCircuit);
        }

        return c;
    }
}