package AdjacencyMatrix;

import java.util.*;

public class BFS {
    public static void search(Graph graph, int start) {
        boolean[] visited = new boolean[graph.numVertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print("[" + vertex + "]");

            for (int i = 0; i < graph.numVertices; i++) {
                if (graph.adjMatrix[vertex][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }

            if (!queue.isEmpty()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}