//https://neetcode.io/problems/count-connected-components?list=blind75
import java.util.*;

public class ConnectedComponents {
    
    // Solution 1: DFS (Most Intuitive)
    public int countComponents(int n, int[][] edges) {
        // Build adjacency list
        List<List<Integer>> graph = buildGraph(n, edges);
        
        boolean[] visited = new boolean[n];
        int components = 0;
        
        // Visit each unvisited node and perform DFS
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(graph, visited, i);
                components++; // Found a new component
            }
        }
        
        return components;
    }
    
    private void dfs(List<List<Integer>> graph, boolean[] visited, int node) {
        visited[node] = true;
        
        // Visit all unvisited neighbors
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(graph, visited, neighbor);
            }
        }
    }
    
    // Solution 2: BFS (Iterative)
    public int countComponentsBFS(int n, int[][] edges) {
        // Build adjacency list
        List<List<Integer>> graph = buildGraph(n, edges);
        
        boolean[] visited = new boolean[n];
        int components = 0;
        
        // Visit each unvisited node and perform BFS
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                bfs(graph, visited, i);
                components++; // Found a new component
            }
        }
        
        return components;
    }
    
    private void bfs(List<List<Integer>> graph, boolean[] visited, int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            
            // Visit all unvisited neighbors
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }
    
    // Solution 3: Union-Find (Most Efficient)
    public int countComponentsUnionFind(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        
        // Union all connected nodes
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }
        
        return uf.getComponentCount();
    }
    
    // Solution 4: DFS with Component Collection
    public List<List<Integer>> findAllComponents(int n, int[][] edges) {
        List<List<Integer>> graph = buildGraph(n, edges);
        boolean[] visited = new boolean[n];
        List<List<Integer>> allComponents = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfsCollectNodes(graph, visited, i, component);
                allComponents.add(component);
            }
        }
        
        return allComponents;
    }
    
    private void dfsCollectNodes(List<List<Integer>> graph, boolean[] visited, int node, List<Integer> component) {
        visited[node] = true;
        component.add(node);
        
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfsCollectNodes(graph, visited, neighbor, component);
            }
        }
    }
    
    // Solution 5: Iterative DFS with Stack
    public int countComponentsIterativeDFS(int n, int[][] edges) {
        List<List<Integer>> graph = buildGraph(n, edges);
        boolean[] visited = new boolean[n];
        int components = 0;
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                // Use stack for iterative DFS
                Stack<Integer> stack = new Stack<>();
                stack.push(i);
                visited[i] = true;
                
                while (!stack.isEmpty()) {
                    int node = stack.pop();
                    
                    for (int neighbor : graph.get(node)) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            stack.push(neighbor);
                        }
                    }
                }
                
                components++;
            }
        }
        
        return components;
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
            componentCount = n; // Initially, each node is its own component
            
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
                componentCount--; // Two components merged into one
            }
        }
        
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
        
        public int getComponentCount() {
            return componentCount;
        }
        
        public List<List<Integer>> getAllComponents() {
            Map<Integer, List<Integer>> componentMap = new HashMap<>();
            
            for (int i = 0; i < parent.length; i++) {
                int root = find(i);
                componentMap.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
            }
            
            return new ArrayList<>(componentMap.values());
        }
    }
    
    // Utility methods for analysis and visualization
    
    public void printGraph(int n, int[][] edges) {
        System.out.println("Graph structure:");
        List<List<Integer>> graph = buildGraph(n, edges);
        
        for (int i = 0; i < n; i++) {
            if (graph.get(i).isEmpty()) {
                System.out.println("Node " + i + ": isolated");
            } else {
                System.out.println("Node " + i + " connected to: " + graph.get(i));
            }
        }
        System.out.println();
    }
    
    public void analyzeGraph(int n, int[][] edges) {
        System.out.println("Graph analysis:");
        System.out.println("Total nodes: " + n);
        System.out.println("Total edges: " + edges.length);
        
        // Calculate node degrees
        int[] degree = new int[n];
        for (int[] edge : edges) {
            degree[edge[0]]++;
            degree[edge[1]]++;
        }
        
        int isolatedNodes = 0;
        int maxDegree = 0;
        double avgDegree = 0;
        
        for (int i = 0; i < n; i++) {
            if (degree[i] == 0) isolatedNodes++;
            maxDegree = Math.max(maxDegree, degree[i]);
            avgDegree += degree[i];
        }
        avgDegree /= n;
        
        System.out.println("Isolated nodes: " + isolatedNodes);
        System.out.println("Maximum degree: " + maxDegree);
        System.out.printf("Average degree: %.2f%n", avgDegree);
        System.out.println();
    }
    
    public void comparePerformance(int n, int[][] edges) {
        System.out.println("Performance comparison:");
        
        long startTime, endTime;
        
        // DFS
        startTime = System.nanoTime();
        int resultDFS = countComponents(n, edges);
        endTime = System.nanoTime();
        long dfsTime = endTime - startTime;
        
        // BFS
        startTime = System.nanoTime();
        int resultBFS = countComponentsBFS(n, edges);
        endTime = System.nanoTime();
        long bfsTime = endTime - startTime;
        
        // Union-Find
        startTime = System.nanoTime();
        int resultUF = countComponentsUnionFind(n, edges);
        endTime = System.nanoTime();
        long ufTime = endTime - startTime;
        
        // Iterative DFS
        startTime = System.nanoTime();
        int resultIterDFS = countComponentsIterativeDFS(n, edges);
        endTime = System.nanoTime();
        long iterDfsTime = endTime - startTime;
        
        System.out.println("DFS: " + resultDFS + " components (" + dfsTime + " ns)");
        System.out.println("BFS: " + resultBFS + " components (" + bfsTime + " ns)");
        System.out.println("Union-Find: " + resultUF + " components (" + ufTime + " ns)");
        System.out.println("Iterative DFS: " + resultIterDFS + " components (" + iterDfsTime + " ns)");
        System.out.println();
    }
    
    // Test method
    public static void main(String[] args) {
        ConnectedComponents solution = new ConnectedComponents();
        
        // Example 1: Single component
        System.out.println("=== Example 1: Single Component ===");
        int n1 = 3;
        int[][] edges1 = {{0, 1}, {0, 2}};
        
        solution.printGraph(n1, edges1);
        solution.analyzeGraph(n1, edges1);
        
        System.out.println("Number of components:");
        System.out.println("DFS: " + solution.countComponents(n1, edges1));
        System.out.println("BFS: " + solution.countComponentsBFS(n1, edges1));
        System.out.println("Union-Find: " + solution.countComponentsUnionFind(n1, edges1));
        
        List<List<Integer>> components1 = solution.findAllComponents(n1, edges1);
        System.out.println("Components: " + components1);
        
        // Example 2: Multiple components
        System.out.println("\\n=== Example 2: Multiple Components ===");
        int n2 = 6;
        int[][] edges2 = {{0, 1}, {1, 2}, {2, 3}, {4, 5}};
        
        solution.printGraph(n2, edges2);
        solution.analyzeGraph(n2, edges2);
        
        System.out.println("Number of components:");
        System.out.println("DFS: " + solution.countComponents(n2, edges2));
        System.out.println("BFS: " + solution.countComponentsBFS(n2, edges2));
        System.out.println("Union-Find: " + solution.countComponentsUnionFind(n2, edges2));
        
        List<List<Integer>> components2 = solution.findAllComponents(n2, edges2);
        System.out.println("Components: " + components2);
        
        // Example 3: All isolated nodes
        System.out.println("\\n=== Example 3: All Isolated Nodes ===");
        int n3 = 4;
        int[][] edges3 = {};
        
        solution.printGraph(n3, edges3);
        solution.analyzeGraph(n3, edges3);
        
        System.out.println("Number of components:");
        System.out.println("DFS: " + solution.countComponents(n3, edges3));
        System.out.println("Union-Find: " + solution.countComponentsUnionFind(n3, edges3));
        
        List<List<Integer>> components3 = solution.findAllComponents(n3, edges3);
        System.out.println("Components: " + components3);
        
        // Example 4: Complete graph
        System.out.println("\\n=== Example 4: Complete Graph ===");
        int n4 = 4;
        int[][] edges4 = {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}};
        
        solution.printGraph(n4, edges4);
        solution.analyzeGraph(n4, edges4);
        
        System.out.println("Number of components:");
        System.out.println("DFS: " + solution.countComponents(n4, edges4));
        System.out.println("Union-Find: " + solution.countComponentsUnionFind(n4, edges4));
        
        List<List<Integer>> components4 = solution.findAllComponents(n4, edges4);
        System.out.println("Components: " + components4);
        
        // Example 5: Mixed scenario
        System.out.println("\\n=== Example 5: Mixed Scenario ===");
        int n5 = 8;
        int[][] edges5 = {{0, 1}, {1, 2}, {3, 4}, {6, 7}};
        // Components: {0,1,2}, {3,4}, {5}, {6,7}
        
        solution.printGraph(n5, edges5);
        solution.analyzeGraph(n5, edges5);
        
        System.out.println("Number of components:");
        System.out.println("DFS: " + solution.countComponents(n5, edges5));
        System.out.println("Union-Find: " + solution.countComponentsUnionFind(n5, edges5));
        
        List<List<Integer>> components5 = solution.findAllComponents(n5, edges5);
        System.out.println("Components: " + components5);
        
        // Example 6: Linear chain
        System.out.println("\\n=== Example 6: Linear Chain ===");
        int n6 = 5;
        int[][] edges6 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        
        solution.printGraph(n6, edges6);
        solution.analyzeGraph(n6, edges6);
        
        System.out.println("Number of components:");
        System.out.println("DFS: " + solution.countComponents(n6, edges6));
        System.out.println("Union-Find: " + solution.countComponentsUnionFind(n6, edges6));
        
        List<List<Integer>> components6 = solution.findAllComponents(n6, edges6);
        System.out.println("Components: " + components6);
        
        // Performance test
        System.out.println("\\n=== Performance Test ===");
        int n7 = 50;
        List<int[]> edgeList = new ArrayList<>();
        
        // Create multiple components: chains of length 5
        for (int i = 0; i < 50; i += 5) {
            for (int j = 0; j < 4 && i + j + 1 < 50; j++) {
                edgeList.add(new int[]{i + j, i + j + 1});
            }
        }
        
        int[][] edges7 = edgeList.toArray(new int[edgeList.size()][]);
        
        solution.analyzeGraph(n7, edges7);
        solution.comparePerformance(n7, edges7);
        
        // Test Union-Find component details
        System.out.println("=== Union-Find Component Details ===");
        UnionFind uf = solution.new UnionFind(n2);
        for (int[] edge : edges2) {
            uf.union(edge[0], edge[1]);
        }
        
        List<List<Integer>> ufComponents = uf.getAllComponents();
        System.out.println("Union-Find detailed components: " + ufComponents);
    }
}
