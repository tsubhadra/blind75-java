//https://neetcode.io/problems/clone-graph?list=blind75
import java.util.*;

// Definition for a Node
class Node {
    public int val;
    public List<Node> neighbors;
    
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
    
    @Override
    public String toString() {
        return "Node{val=" + val + ", neighbors=" + 
               neighbors.stream().map(n -> String.valueOf(n.val)).reduce("[", (a, b) -> a + b + ",") + "]}";
    }
}

public class CloneGraph {
    
    // Solution 1: DFS with HashMap (Recursive)
    private Map<Node, Node> visited = new HashMap<>();
    
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        
        visited.clear(); // Reset for fresh calls
        return dfsClone(node);
    }
    
    private Node dfsClone(Node node) {
        // If we've already cloned this node, return the clone
        if (visited.containsKey(node)) {
            return visited.get(node);
        }
        
        // Create a new node with the same value
        Node cloneNode = new Node(node.val);
        
        // Mark this node as visited BEFORE processing neighbors
        // This prevents infinite loops in cyclic graphs
        visited.put(node, cloneNode);
        
        // Clone all neighbors recursively
        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(dfsClone(neighbor));
        }
        
        return cloneNode;
    }
    
    // Solution 2: BFS with HashMap (Iterative)
    public Node cloneGraphBFS(Node node) {
        if (node == null) {
            return null;
        }
        
        Map<Node, Node> visited = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        
        // Clone the starting node
        Node cloneNode = new Node(node.val);
        visited.put(node, cloneNode);
        queue.offer(node);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            
            // Process all neighbors of current node
            for (Node neighbor : current.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    // Clone the neighbor if not already cloned
                    visited.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }
                
                // Add the cloned neighbor to current node's clone
                visited.get(current).neighbors.add(visited.get(neighbor));
            }
        }
        
        return cloneNode;
    }
    
    // Solution 3: DFS without instance variable (Pure functional approach)
    public Node cloneGraphPure(Node node) {
        if (node == null) {
            return null;
        }
        
        return dfsClonePure(node, new HashMap<>());
    }
    
    private Node dfsClonePure(Node node, Map<Node, Node> visited) {
        // If we've already cloned this node, return the clone
        if (visited.containsKey(node)) {
            return visited.get(node);
        }
        
        // Create a new node with the same value
        Node cloneNode = new Node(node.val);
        visited.put(node, cloneNode);
        
        // Clone all neighbors recursively
        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(dfsClonePure(neighbor, visited));
        }
        
        return cloneNode;
    }
    
    // Solution 4: Iterative DFS using Stack
    public Node cloneGraphIterativeDFS(Node node) {
        if (node == null) {
            return null;
        }
        
        Map<Node, Node> visited = new HashMap<>();
        Stack<Node> stack = new Stack<>();
        
        // Clone the starting node
        visited.put(node, new Node(node.val));
        stack.push(node);
        
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            
            // Process all neighbors
            for (Node neighbor : current.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    // Clone the neighbor if not already cloned
                    visited.put(neighbor, new Node(neighbor.val));
                    stack.push(neighbor);
                }
                
                // Add the cloned neighbor to current node's clone
                visited.get(current).neighbors.add(visited.get(neighbor));
            }
        }
        
        return visited.get(node);
    }
    
    // Utility methods for testing
    
    // Create a graph from adjacency list representation
    public static Node createGraphFromAdjList(int[][] adjList) {
        if (adjList == null || adjList.length == 0) {
            return null;
        }
        
        Map<Integer, Node> nodes = new HashMap<>();
        
        // Create all nodes first
        for (int i = 0; i < adjList.length; i++) {
            nodes.put(i + 1, new Node(i + 1));
        }
        
        // Add neighbors
        for (int i = 0; i < adjList.length; i++) {
            Node currentNode = nodes.get(i + 1);
            for (int neighborVal : adjList[i]) {
                currentNode.neighbors.add(nodes.get(neighborVal));
            }
        }
        
        return nodes.get(1); // Return the first node
    }
    
    // Convert graph to adjacency list for comparison
    public static List<List<Integer>> graphToAdjList(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }
        
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        
        queue.offer(node);
        visited.add(node);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            List<Integer> neighbors = new ArrayList<>();
            
            for (Node neighbor : current.neighbors) {
                neighbors.add(neighbor.val);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
            
            Collections.sort(neighbors); // Sort for consistent output
            adjMap.put(current.val, neighbors);
        }
        
        // Convert to list format
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 1; i <= adjMap.size(); i++) {
            result.add(adjMap.getOrDefault(i, new ArrayList<>()));
        }
        
        return result;
    }
    
    // Verify that two graphs are identical but separate objects
    public static boolean verifyClone(Node original, Node clone) {
        if (original == null && clone == null) {
            return true;
        }
        if (original == null || clone == null) {
            return false;
        }
        
        Map<Node, Node> mapping = new HashMap<>();
        return verifyCloneHelper(original, clone, mapping);
    }
    
    private static boolean verifyCloneHelper(Node original, Node clone, Map<Node, Node> mapping) {
        if (original == clone) {
            return false; // Same object reference - not a clone!
        }
        
        if (original.val != clone.val) {
            return false;
        }
        
        if (mapping.containsKey(original)) {
            return mapping.get(original) == clone;
        }
        
        mapping.put(original, clone);
        
        if (original.neighbors.size() != clone.neighbors.size()) {
            return false;
        }
        
        for (int i = 0; i < original.neighbors.size(); i++) {
            if (!verifyCloneHelper(original.neighbors.get(i), clone.neighbors.get(i), mapping)) {
                return false;
            }
        }
        
        return true;
    }
    
    // Print graph structure for debugging
    public static void printGraph(Node node, String label) {
        if (node == null) {
            System.out.println(label + ": null");
            return;
        }
        
        System.out.println(label + ":");
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        
        queue.offer(node);
        visited.add(node);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            List<Integer> neighborVals = new ArrayList<>();
            
            for (Node neighbor : current.neighbors) {
                neighborVals.add(neighbor.val);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
            
            System.out.println("  Node " + current.val + " -> " + neighborVals);
        }
        System.out.println();
    }
    
    // Test method
    public static void main(String[] args) {
        CloneGraph solution = new CloneGraph();
        
        // Example 1: [[2],[1,3],[2]]
        System.out.println("=== Example 1: Connected Triangle ===");
        int[][] adjList1 = {{2}, {1, 3}, {2}};
        Node graph1 = createGraphFromAdjList(adjList1);
        
        printGraph(graph1, "Original Graph 1");
        
        Node clone1DFS = solution.cloneGraph(graph1);
        Node clone1BFS = solution.cloneGraphBFS(graph1);
        Node clone1Pure = solution.cloneGraphPure(graph1);
        Node clone1Iterative = solution.cloneGraphIterativeDFS(graph1);
        
        printGraph(clone1DFS, "DFS Clone");
        
        System.out.println("DFS Clone verification: " + verifyClone(graph1, clone1DFS));
        System.out.println("BFS Clone verification: " + verifyClone(graph1, clone1BFS));
        System.out.println("Pure DFS Clone verification: " + verifyClone(graph1, clone1Pure));
        System.out.println("Iterative DFS Clone verification: " + verifyClone(graph1, clone1Iterative));
        
        System.out.println("Original adj list: " + graphToAdjList(graph1));
        System.out.println("Cloned adj list: " + graphToAdjList(clone1DFS));
        
        // Example 2: [[]]
        System.out.println("\n=== Example 2: Single Node ===");
        int[][] adjList2 = {{}};
        Node graph2 = createGraphFromAdjList(adjList2);
        
        printGraph(graph2, "Original Graph 2");
        
        Node clone2 = solution.cloneGraph(graph2);
        printGraph(clone2, "Cloned Graph 2");
        
        System.out.println("Clone verification: " + verifyClone(graph2, clone2));
        
        // Example 3: null (empty graph)
        System.out.println("\n=== Example 3: Empty Graph ===");
        Node graph3 = null;
        Node clone3 = solution.cloneGraph(graph3);
        
        System.out.println("Original: " + graph3);
        System.out.println("Clone: " + clone3);
        System.out.println("Clone verification: " + verifyClone(graph3, clone3));
        
        // Example 4: Complex graph (cycle)
        System.out.println("\n=== Example 4: Four-Node Cycle ===");
        int[][] adjList4 = {{2, 4}, {1, 3}, {2, 4}, {1, 3}};
        Node graph4 = createGraphFromAdjList(adjList4);
        
        printGraph(graph4, "Original Graph 4");
        
        Node clone4 = solution.cloneGraph(graph4);
        printGraph(clone4, "Cloned Graph 4");
        
        System.out.println("Clone verification: " + verifyClone(graph4, clone4));
        System.out.println("Original adj list: " + graphToAdjList(graph4));
        System.out.println("Cloned adj list: " + graphToAdjList(clone4));
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        // Create a larger graph (complete graph with 10 nodes)
        int[][] largeAdjList = new int[10][];
        for (int i = 0; i < 10; i++) {
            List<Integer> neighbors = new ArrayList<>();
            for (int j = 1; j <= 10; j++) {
                if (j != i + 1) { // Don't include self
                    neighbors.add(j);
                }
            }
            largeAdjList[i] = neighbors.stream().mapToInt(Integer::intValue).toArray();
        }
        
        Node largeGraph = createGraphFromAdjList(largeAdjList);
        
        long startTime = System.currentTimeMillis();
        Node largeClone = solution.cloneGraph(largeGraph);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Large graph (10 nodes, complete) clone time: " + (endTime - startTime) + " ms");
        System.out.println("Large graph clone verification: " + verifyClone(largeGraph, largeClone));
    }
}
