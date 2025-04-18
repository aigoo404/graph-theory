package AdjacencyMatrix;

public class RootedTree extends DirectedGraph {
    private int root;

    public RootedTree(int size) {
        super(size);
        this.root = -1; // Default root is -1 (invalid root)
    }

    // Override addEdge to avoid cycles
    @Override
    public void addEdge(int start, int end) {
        if (start == end || adjMatrix[start][end] == 1) {
            return;
        }

        // Check for cycle before adding edge
        adjMatrix[start][end] = 1;
        if (!isTree()) {
            adjMatrix[start][end] = 0; // Revert the edge if it causes a cycle
        }
    }

    // Check if the tree is complete (n vertices, n-1 edges)
    public boolean isCompletedTree() {
        int edgeCount = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] != 0) {
                    edgeCount++;
                }
            }
        }
        return edgeCount == numVertices - 1;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    // Convert the rooted tree back to a free tree (make the graph undirected)
    public void convertToFreeTree() {
        // Create a new undirected graph
        FreeTree freeTree = new FreeTree(numVertices);

        // Convert the directed edges back to undirected edges
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] == 1) {
                    freeTree.addEdge(i, j);
                    freeTree.addEdge(j, i); // Ensure the graph is undirected
                }
            }
        }

        // Update the adjacency matrix to reflect the undirected tree
        this.adjMatrix = freeTree.adjMatrix;
        this.root = -1; // Invalidate the root
    }
}