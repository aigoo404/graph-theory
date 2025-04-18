package AdjacencyMatrix;

import java.util.*;

public class FreeTree extends UndirectedGraph {

    public FreeTree(int size) {
        super(size);
    }

    // Override addEdge to ensure no cycles are formed
    @Override
    public void addEdge(int start, int end) {
        if (start >= numVertices || end >= numVertices || start < 0 || end < 0 || adjMatrix[start][end] == 1) {
            return;
        }

        // Temporarily add the edge to check for cycles
        adjMatrix[start][end] = 1;
        adjMatrix[end][start] = 1;

        // Check if it creates a cycle
        if (isConnected()) {
            // No cycle, edge is valid
            return;
        } else {
            // Revert edge to avoid cycle
            adjMatrix[start][end] = 0;
            adjMatrix[end][start] = 0;
        }
    }

    // Check if the tree is complete (n vertices, n-1 edges)
    public boolean isCompletedTree() {
        int edgeCount = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (adjMatrix[i][j] != 0) {
                    edgeCount++;
                }
            }
        }
        return edgeCount == numVertices - 1 && isConnected();
    }

    // Calculate the eccentricity of a vertex
    public int eccentricity(int vertex) {
        boolean[] visited = new boolean[numVertices];
        int[] distance = new int[numVertices];
        Arrays.fill(distance, -1); // Initialize distance to -1
        bfsDistanceUtil(vertex, visited, distance);
        int maxDistance = 0;
        for (int dist : distance) {
            if (dist > maxDistance) {
                maxDistance = dist;
            }
        }
        return maxDistance;
    }

    private void bfsDistanceUtil(int start, boolean[] visited, int[] distance) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        distance[start] = 0;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[vertex][i] != 0 && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                    distance[i] = distance[vertex] + 1;
                }
            }
        }
    }

    // Calculate the radius of the tree
    public int radius() {
        int minEccentricity = Integer.MAX_VALUE;
        for (int i = 0; i < numVertices; i++) {
            int ecc = eccentricity(i);
            if (ecc < minEccentricity) {
                minEccentricity = ecc;
            }
        }
        return minEccentricity;
    }

    // Find the center of the tree (all vertices with eccentricity equal to radius)
    public List<Integer> center() {
        List<Integer> centers = new ArrayList<>();
        int radius = radius();
        for (int i = 0; i < numVertices; i++) {
            if (eccentricity(i) == radius) {
                centers.add(i);
            }
        }
        return centers;
    }

    // Convert the free tree to a rooted tree by choosing a root node
    public void convertToRootedTree(int root) {
        // Create a new directed graph with the same size
        RootedTree rootedTree = new RootedTree(numVertices);

        // Set the root
        for (int i = 0; i < numVertices; i++) {
            if (i != root) {
                for (int j = 0; j < numVertices; j++) {
                    if (adjMatrix[i][j] == 1) {
                        // Direct the edge from i -> j (i is the parent, j is the child)
                        rootedTree.addEdge(i, j);
                    }
                }
            }
        }

        // Convert the adjacency matrix to that of a rooted tree
        this.adjMatrix = rootedTree.adjMatrix;
    }
}