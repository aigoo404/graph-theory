package AdjacencyMatrix;

import java.util.Arrays;

public abstract class Graph {
    protected int[][] adjMatrix;
    protected int numVertices;

    public Graph(int size) {
        this.numVertices = size;
        this.adjMatrix = new int[size][size]; // Initialize matrix with correct size
    }

    // Add a new vertex to the graph
    public void addVertex() {
        numVertices++; // Increase vertex count
        int[][] newMatrix = new int[numVertices][numVertices]; // New adjacency matrix

        // Copy old matrix values into new matrix
        for (int i = 0; i < numVertices - 1; i++) {
            System.arraycopy(adjMatrix[i], 0, newMatrix[i], 0, numVertices - 1);
        }

        adjMatrix = newMatrix; // Replace old matrix
    }

    // Delete a vertex and shift matrix accordingly
    public void deleteVertex(int vertex) {
        if (vertex < 0 || vertex >= numVertices)
            return;
        int[][] newMatrix = new int[numVertices - 1][numVertices - 1];

        for (int i = 0, ni = 0; i < numVertices; i++) {
            if (i == vertex)
                continue;
            for (int j = 0, nj = 0; j < numVertices; j++) {
                if (j == vertex)
                    continue;
                newMatrix[ni][nj++] = adjMatrix[i][j];
            }
            ni++;
        }
        numVertices--;
        adjMatrix = newMatrix;
    }

    // Add an edge between two vertices
    public abstract void addEdge(int start, int end);

    // Delete an edge between two vertices
    public abstract void deleteEdge(int start, int end);

    // Print edge list of the graph
    public void printEdges() {
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] != 0) {
                    System.out.println("Edge: (" + i + ", " + j + ")");
                }
            }
        }
    }

    // Compute and print degree of each vertex
    public void computeDegree() {
        for (int i = 0; i < numVertices; i++) {
            int degree = 0;
            for (int j = 0; j < numVertices; j++) {
                degree += adjMatrix[i][j];
            }
            System.out.println("Vertex [" + i + "], Degree = " + degree);
        }
    }

    // Compute the number of edges or arcs in the graph
    public int countEdges() {
        int count = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                count += adjMatrix[i][j];
            }
        }
        return count / 2; // Divided by 2 for undirected graph
    }

    public void printGraph() {
        for (int[] row : adjMatrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}