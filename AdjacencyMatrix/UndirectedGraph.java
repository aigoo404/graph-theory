package AdjacencyMatrix;

import java.util.LinkedList;
import java.util.Queue;

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
}
