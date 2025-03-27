package AdjacencyMatrix;

import java.util.*;

public class DFS {
    public static void search(Graph graph, int start) {
        boolean[] visited = new boolean[graph.numVertices];
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int vertex = stack.pop();

            if (!visited[vertex]) {
                visited[vertex] = true;
                System.out.print("[" + vertex + "]");

                // Push adjacent vertices in reverse order to maintain correct traversal order
                List<Integer> neighbors = new ArrayList<>();
                for (int i = 0; i < graph.numVertices; i++) {
                    if (graph.adjMatrix[vertex][i] == 1 && !visited[i]) {
                        neighbors.add(i);
                    }
                }
                Collections.reverse(neighbors); // Ensure correct order
                for (int neighbor : neighbors) {
                    stack.push(neighbor);
                }

                if (!stack.isEmpty()) {
                    System.out.print(" -> ");
                }
            }
        }
        System.out.println();
    }
}
