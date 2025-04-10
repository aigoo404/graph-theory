package AdjacencyMatrix;

import java.util.*;

public abstract class Graph {
    protected int[][] adjMatrix; // Adjacency matrix to represent edges
    protected int vertexCount; // Current number of vertices

    public Graph(int size) {
        adjMatrix = new int[size][size];
        vertexCount = size;
    }

    public int getNumVertices() {
        return vertexCount;
    }

    // Abstract methods to be defined in child classes
    public abstract void addEdge(int start, int end);

    public abstract void removeEdge(int start, int end);

    // Add a new vertex by expanding the matrix
    public void addVertex() {
        vertexCount++;
        int[][] newMatrix = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount - 1; i++) {
            System.arraycopy(adjMatrix[i], 0, newMatrix[i], 0, vertexCount - 1);
        }
        adjMatrix = newMatrix;
    }

    // Delete a vertex and shrink the matrix
    public void deleteVertex(int v) {
        if (v >= vertexCount)
            return;
        int[][] newMatrix = new int[vertexCount - 1][vertexCount - 1];
        for (int i = 0, ni = 0; i < vertexCount; i++) {
            if (i == v)
                continue; // Skip the vertex to delete
            for (int j = 0, nj = 0; j < vertexCount; j++) {
                if (j == v)
                    continue;
                newMatrix[ni][nj++] = adjMatrix[i][j];
            }
            ni++;
        }
        vertexCount--;
        adjMatrix = newMatrix;
    }

    // Print the full adjacency matrix
    public void printMatrix() {
        System.out.println("Adjacency Matrix:");
        for (int[] row : adjMatrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    // Print all edges from the adjacency matrix
    public void printEdgeList() {
        System.out.println("Edge List:");
        for (int i = 0; i < vertexCount; i++) {
            for (int j = (this instanceof UndirectedGraph ? i : 0); j < vertexCount; j++) {
                for (int k = 0; k < adjMatrix[i][j]; k++) {
                    System.out.println(i + " - " + j);
                }
            }
        }
    }

    // Deep copy of the matrix (useful for future algorithms like Euler cycle)
    public int[][] copy() {
        int[][] copy = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            System.arraycopy(adjMatrix[i], 0, copy[i], 0, vertexCount);
        }
        return copy;
    }

    // Abstract methods for degree and edge counting
    public abstract int degree(int v);

    public abstract void degree();

    public abstract int countEdges();

    // Check if the graph contains loops
    public boolean hasLoop() {
        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[i][i] > 0)
                return true;
        }
        return false;
    }

    // Check if it's a simple graph (no loops, no multiple edges)
    public boolean isSimpleGraph() {
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (i == j && adjMatrix[i][j] > 0)
                    return false;
                if (adjMatrix[i][j] > 1)
                    return false;
            }
        }
        return true;
    }

    // Check if the graph is complete (every pair of vertices is connected)
    public boolean isCompleteGraph() {
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (i != j && adjMatrix[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    // Check if the graph is bipartite using BFS coloring
    public boolean isBipartite() {
        int[] colors = new int[vertexCount];
        Arrays.fill(colors, -1);
        Queue<Integer> q = new LinkedList<>();

        for (int start = 0; start < vertexCount; start++) {
            if (colors[start] == -1) {
                q.add(start);
                colors[start] = 0;

                while (!q.isEmpty()) {
                    int u = q.poll();
                    for (int v = 0; v < vertexCount; v++) {
                        if (adjMatrix[u][v] > 0) {
                            if (colors[v] == -1) {
                                colors[v] = 1 - colors[u];
                                q.add(v);
                            } else if (colors[v] == colors[u]) {
                                return false; // Same color found, not bipartite
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}