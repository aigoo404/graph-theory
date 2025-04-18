package AdjacencyMatrix;

import java.util.*;

public class Search {
    private Graph graph;

    public Search(Graph graph) {
        this.graph = graph;
    }

    // BFS (Breadth-First Search)
    public void bfs(int start) {
        boolean[] visited = new boolean[graph.numVertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        System.out.print("BFS Path: ");

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");
            for (int i = 0; i < graph.numVertices; i++) {
                if (graph.adjMatrix[vertex][i] != 0 && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
        }
        System.out.println();
    }

    // DFS (Depth-First Search)
    public void dfs(int start) {
        boolean[] visited = new boolean[graph.numVertices];
        System.out.print("DFS Path: ");
        dfsUtil(start, visited);
        System.out.println();
    }

    private void dfsUtil(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        for (int i = 0; i < graph.numVertices; i++) {
            if (graph.adjMatrix[vertex][i] != 0 && !visited[i]) {
                dfsUtil(i, visited);
            }
        }
    }

    // BFS Distance - Returns the distance from start to the target vertex
    public int bfsDistance(int start, int target) {
        boolean[] visited = new boolean[graph.numVertices];
        int[] distance = new int[graph.numVertices];
        Arrays.fill(distance, -1); // Initialize distance to -1
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        distance[start] = 0;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            if (vertex == target) {
                return distance[vertex];
            }
            for (int i = 0; i < graph.numVertices; i++) {
                if (graph.adjMatrix[vertex][i] != 0 && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                    distance[i] = distance[vertex] + 1;
                }
            }
        }
        return -1; // If target is not reachable
    }

    // DFS Distance - Returns the distance from start to the target vertex
    public int dfsDistance(int start, int target) {
        boolean[] visited = new boolean[graph.numVertices];
        int[] distance = new int[graph.numVertices];
        Arrays.fill(distance, -1); // Initialize distance to -1
        dfsDistanceUtil(start, visited, distance, 0, target);
        return distance[target];
    }

    private void dfsDistanceUtil(int vertex, boolean[] visited, int[] distance, int dist, int target) {
        if (visited[vertex])
            return;
        visited[vertex] = true;
        distance[vertex] = dist;

        if (vertex == target)
            return;

        for (int i = 0; i < graph.numVertices; i++) {
            if (graph.adjMatrix[vertex][i] != 0 && !visited[i]) {
                dfsDistanceUtil(i, visited, distance, dist + 1, target);
            }
        }
    }

    // Print the search path with format [i]->[i+1]
    public void printSearchPath(List<Integer> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            System.out.print("[" + path.get(i) + "]->[" + path.get(i + 1) + "] ");
        }
        System.out.println();
    }
}