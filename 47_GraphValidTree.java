//https://neetcode.io/problems/valid-tree?list=blind75
import java.util.*;

public class GraphValidTree {
    
    // Solution 1: DFS with Cycle Detection (Most Intuitive)
    public boolean validTree(int n, int[][] edges) {
        // Tree property: exactly n-1 edges for n nodes
        if (edges.length != n - 1) {
            return false;
        }
        
        // Build adjacency list
        List<List<Integer>> graph = buildGraph(n, edges);
        
        // Check if graph is connected and has no cycles
        boolean[] visited = new boolean[n];
        
        // Start DFS from node 0
        if (hasCycle(graph, visited, 0, -1)) {
            return false; // Cycle detected
        }
        
        // Check if all nodes are visited (connected)
        for (boolean v : visited) {
            if (!v) {
                return false; // Not connected
            }
        }
        
        return true;
    }
    
    private boolean hasCycle(List<List<Integer>> graph, boolean[] visited, int node, int parent) {
        visited[node] = true;
        
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                // Recursively check unvisited neighbor
                if (hasCycle(graph, visited, neighbor, node)) {
                    return true;
                }
            } else if (neighbor != parent) {
                // Visited neighbor that's not parent = cycle
                return true;
            }
        }
        
        return false;
    }
    
    // Solution 2: Union-Find (Disjoint Set) - Most Efficient
    public boolean validTreeUnionFind(int n, int[][] edges) {
        // Tree property: exactly n-1 edges for n nodes
        if (edges.length != n - 1) {
            return false;
        }
        
        UnionFind uf = new UnionFind(n);
        
        // Try to union each edge
        for (int[] edge : edges) {
            int node1 = edge[0];
            int node2 = edge[1];
            
            // If nodes are already connected, adding edge creates cycle
            if (uf.connected(node1, node2)) {
                return false;
            }
            
            uf.union(node1, node2);
        }
        
        // Check if all nodes are in the same component
        return uf.getComponentCount() == 1;
    }
    
    // Solution 3: BFS with Cycle Detection
    public boolean validTreeBFS(int n, int[][] edges) {
        // Tree property: exactly n-1 edges for n nodes
        if (edges.length != n - 1) {
            return false;
        }
        
        // Build adjacency list
        List<List<Integer>> graph = buildGraph(n, edges);
        
        // BFS to check connectivity and cycles
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parent = new HashMap<>();
        
        queue.offer(0);
        visited[0] = true;
        parent.put(0, -1);
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent.put(neighbor, node);
                    queue.offer(neighbor);
                } else if (parent.get(node) != neighbor) {
                    // Visited neighbor that's not parent = cycle
                    return false;
                }
            }
        }
        
        // Check if all nodes are visited (connected)
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        
        return true;
    }
    
    // Solution 4: Simple Edge Count + Connectivity Check
    public boolean validTreeSimple(int n, int[][] edges) {
        // Quick check: tree must have exactly n-1 edges
        if (edges.length != n - 1) {
            return false;
        }
        
        // Build adjacency list
        List<List<Integer>> graph = buildGraph(n, edges);
        
        // Simple DFS to check connectivity
        boolean[] visited = new boolean[n];
        dfs(graph, visited, 0);
        
        // Check if all nodes are reachable
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        
        return true; // n-1 edges + connected = valid tree
    }
    
    private void dfs(List<List<Integer>> graph, boolean[] visited, int node) {
        visited[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(graph, visited, neighbor);
            }
        }
    }
    
    // Helper method to build adjacency list
    private List<List<Integer>> buildGraph(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u); // Undirected graph
        }
        
        return graph;
    }
    
    // Union-Find data structure
    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int componentCount;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            componentCount = n;
            
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                // Union by rank
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                componentCount--;
            }
        }
        
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
        
        public int getComponentCount() {
            return componentCount;
        }
    }
    
    // Utility methods for testing and visualization
    
    public void printGraph(int n, int[][] edges) {
        System.out.println("Graph structure:");
        List<List<Integer>> graph = buildGraph(n, edges);
        
        for (int i = 0; i < n; i++) {
            System.out.println("Node " + i + " connected to: " + graph.get(i));
        }
        System.out.println();
    }
    
    public void analyzeGraph(int n, int[][] edges) {
        System.out.println("Graph analysis:");
        System.out.println("Nodes: " + n);
        System.out.println("Edges: " + edges.length);
        System.out.println("Expected edges for tree: " + (n - 1));
        
        if (edges.length == n - 1) {
            System.out.println("✓ Correct number of edges for tree");
        } else if (edges.length < n - 1) {
            System.out.println("✗ Too few edges - cannot be connected");
        } else {
            System.out.println("✗ Too many edges - must contain cycle");
        }
        
        // Calculate degree of each node
        int[] degree = new int[n];
        for (int[] edge : edges) {
            degree[edge[0]]++;
            degree[edge[1]]++;
        }
        
        System.out.print("Node degrees: ");
        for (int i = 0; i < n; i++) {
            System.out.print(i + ":" + degree[i] + " ");
        }
        System.out.println("\\n");
    }
    
    public List<List<Integer>> findConnectedComponents(int n, int[][] edges) {
        List<List<Integer>> graph = buildGraph(n, edges);
        boolean[] visited = new boolean[n];
        List<List<Integer>> components = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfsComponent(graph, visited, i, component);
                components.add(component);
            }
        }
        
        return components;
    }
    
    private void dfsComponent(List<List<Integer>> graph, boolean[] visited, int node, List<Integer> component) {
        visited[node] = true;
        component.add(node);
        
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfsComponent(graph, visited, neighbor, component);
            }
        }
    }
    
    // Test method
    public static void main(String[] args) {
        GraphValidTree solution = new GraphValidTree();
        
        // Example 1: Valid tree
        System.out.println("=== Example 1: Valid Tree ===");
        int n1 = 5;
        int[][] edges1 = {{0, 1}, {0, 2}, {0, 3}, {1, 4}};
        
        solution.printGraph(n1, edges1);
        solution.analyzeGraph(n1, edges1);
        
        System.out.println("DFS Result: " + solution.validTree(n1, edges1));
        System.out.println("Union-Find Result: " + solution.validTreeUnionFind(n1, edges1));
        System.out.println("BFS Result: " + solution.validTreeBFS(n1, edges1));
        System.out.println("Simple Result: " + solution.validTreeSimple(n1, edges1));
        
        List<List<Integer>> components1 = solution.findConnectedComponents(n1, edges1);
        System.out.println("Connected components: " + components1);
        
        // Example 2: Invalid tree (has cycle)
        System.out.println("\\n=== Example 2: Invalid Tree (Cycle) ===");
        int n2 = 5;
        int[][] edges2 = {{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}};
        
        solution.printGraph(n2, edges2);
        solution.analyzeGraph(n2, edges2);
        
        System.out.println("DFS Result: " + solution.validTree(n2, edges2));
        System.out.println("Union-Find Result: " + solution.validTreeUnionFind(n2, edges2));
        System.out.println("BFS Result: " + solution.validTreeBFS(n2, edges2));
        System.out.println("Simple Result: " + solution.validTreeSimple(n2, edges2));
        
        List<List<Integer>> components2 = solution.findConnectedComponents(n2, edges2);
        System.out.println("Connected components: " + components2);
        
        // Example 3: Disconnected graph
        System.out.println("\\n=== Example 3: Disconnected Graph ===");
        int n3 = 4;
        int[][] edges3 = {{0, 1}, {2, 3}};
        
        solution.printGraph(n3, edges3);
        solution.analyzeGraph(n3, edges3);
        
        System.out.println("DFS Result: " + solution.validTree(n3, edges3));
        System.out.println("Union-Find Result: " + solution.validTreeUnionFind(n3, edges3));
        
        List<List<Integer>> components3 = solution.findConnectedComponents(n3, edges
