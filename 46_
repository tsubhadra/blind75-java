//https://neetcode.io/problems/course-schedule?list=blind75
import java.util.*;

public class CourseSchedule {
    
    // Solution 1: DFS with Cycle Detection (Most Intuitive)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // Build adjacency list representation of the graph
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        
        // Track node states: 0 = unvisited, 1 = visiting, 2 = visited
        int[] state = new int[numCourses];
        
        // Check each course for cycles
        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0 && hasCycle(graph, state, i)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean hasCycle(List<List<Integer>> graph, int[] state, int course) {
        if (state[course] == 1) {
            return true; // Back edge found - cycle detected
        }
        if (state[course] == 2) {
            return false; // Already processed
        }
        
        // Mark as visiting
        state[course] = 1;
        
        // Check all neighbors
        for (int neighbor : graph.get(course)) {
            if (hasCycle(graph, state, neighbor)) {
                return true;
            }
        }
        
        // Mark as visited
        state[course] = 2;
        return false;
    }
    
    // Solution 2: Kahn's Algorithm (Topological Sort using BFS)
    public boolean canFinishKahn(int numCourses, int[][] prerequisites) {
        // Build graph and calculate in-degrees
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            graph.get(prerequisite).add(course);
            inDegree[course]++;
        }
        
        // Initialize queue with courses that have no prerequisites
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int processedCourses = 0;
        
        // Process courses in topological order
        while (!queue.isEmpty()) {
            int course = queue.poll();
            processedCourses++;
            
            // Reduce in-degree of neighbors
            for (int neighbor : graph.get(course)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // If we processed all courses, no cycle exists
        return processedCourses == numCourses;
    }
    
    // Solution 3: DFS with Visited Set (Alternative implementation)
    public boolean canFinishDFSSet(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recursionStack = new HashSet<>();
        
        for (int i = 0; i < numCourses; i++) {
            if (!visited.contains(i)) {
                if (hasCycleDFS(graph, visited, recursionStack, i)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean hasCycleDFS(List<List<Integer>> graph, Set<Integer> visited, 
                               Set<Integer> recursionStack, int course) {
        visited.add(course);
        recursionStack.add(course);
        
        for (int neighbor : graph.get(course)) {
            if (!visited.contains(neighbor)) {
                if (hasCycleDFS(graph, visited, recursionStack, neighbor)) {
                    return true;
                }
            } else if (recursionStack.contains(neighbor)) {
                return true; // Back edge - cycle found
            }
        }
        
        recursionStack.remove(course);
        return false;
    }
    
    // Solution 4: Union-Find approach (Less common but valid)
    public boolean canFinishUnionFind(int numCourses, int[][] prerequisites) {
        // Union-Find with cycle detection
        UnionFind uf = new UnionFind(numCourses);
        
        // Build graph for topological validation
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        
        // Check if adding each edge creates a cycle
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            
            // If they're already connected and we're adding reverse edge, it's a cycle
            if (uf.connected(course, prerequisite)) {
                return false;
            }
        }
        
        // Use DFS as Union-Find alone isn't sufficient for directed graphs
        return canFinish(numCourses, prerequisites);
    }
    
    // Helper method to build adjacency list
    private List<List<Integer>> buildGraph(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            graph.get(prerequisite).add(course); // prerequisite -> course
        }
        
        return graph;
    }
    
    // Union-Find data structure (for completeness)
    class UnionFind {
        private int[] parent;
        private int[] rank;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
        
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }
    
    // Utility method to get actual course ordering (bonus)
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            graph.get(prerequisite).add(course);
            inDegree[course]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int[] result = new int[numCourses];
        int index = 0;
        
        while (!queue.isEmpty()) {
            int course = queue.poll();
            result[index++] = course;
            
            for (int neighbor : graph.get(course)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        return index == numCourses ? result : new int[0];
    }
    
    // Utility methods for testing and visualization
    public void printGraph(int numCourses, int[][] prerequisites) {
        System.out.println("Course prerequisite graph:");
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        
        for (int i = 0; i < numCourses; i++) {
            System.out.println("Course " + i + " enables: " + graph.get(i));
        }
        System.out.println();
    }
    
    public void analyzeDependencies(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        int[] inDegree = new int[numCourses];
        
        for (int[] prereq : prerequisites) {
            inDegree[prereq[0]]++;
        }
        
        System.out.println("Course dependency analysis:");
        for (int i = 0; i < numCourses; i++) {
            System.out.println("Course " + i + ": " + inDegree[i] + " prerequisites, enables " + graph.get(i).size() + " courses");
        }
        System.out.println();
    }
    
    // Test method
    public static void main(String[] args) {
        CourseSchedule solution = new CourseSchedule();
        
        // Example 1: Can finish
        System.out.println("=== Example 1: Simple Linear Dependency ===");
        int numCourses1 = 2;
        int[][] prerequisites1 = {{0, 1}};
        
        solution.printGraph(numCourses1, prerequisites1);
        solution.analyzeDependencies(numCourses1, prerequisites1);
        
        System.out.println("DFS Result: " + solution.canFinish(numCourses1, prerequisites1));
        System.out.println("Kahn's Algorithm Result: " + solution.canFinishKahn(numCourses1, prerequisites1));
        System.out.println("DFS with Set Result: " + solution.canFinishDFSSet(numCourses1, prerequisites1));
        
        int[] order1 = solution.findOrder(numCourses1, prerequisites1);
        System.out.println("Course order: " + Arrays.toString(order1));
        
        // Example 2: Cycle detected
        System.out.println("\\n=== Example 2: Circular Dependency ===");
        int numCourses2 = 2;
        int[][] prerequisites2 = {{0, 1}, {1, 0}};
        
        solution.printGraph(numCourses2, prerequisites2);
        solution.analyzeDependencies(numCourses2, prerequisites2);
        
        System.out.println("DFS Result: " + solution.canFinish(numCourses2, prerequisites2));
        System.out.println("Kahn's Algorithm Result: " + solution.canFinishKahn(numCourses2, prerequisites2));
        System.out.println("DFS with Set Result: " + solution.canFinishDFSSet(numCourses2, prerequisites2));
        
        int[] order2 = solution.findOrder(numCourses2, prerequisites2);
        System.out.println("Course order: " + Arrays.toString(order2));
        
        // Example 3: Complex scenario
        System.out.println("\\n=== Example 3: Complex Valid Dependencies ===");
        int numCourses3 = 4;
        int[][] prerequisites3 = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        
        solution.printGraph(numCourses3, prerequisites3);
        solution.analyzeDependencies(numCourses3, prerequisites3);
        
        System.out.println("DFS Result: " + solution.canFinish(numCourses3, prerequisites3));
        System.out.println("Kahn's Algorithm Result: " + solution.canFinishKahn(numCourses3, prerequisites3));
        
        int[] order3 = solution.findOrder(numCourses3, prerequisites3);
        System.out.println("Course order: " + Arrays.toString(order3));
        
        // Example 4: Complex cycle
        System.out.println("\\n=== Example 4: Complex Cycle ===");
        int numCourses4 = 4;
        int[][] prerequisites4 = {{1, 0}, {2, 1}, {3, 2}, {0, 3}};
        
        solution.printGraph(numCourses4, prerequisites4);
        solution.analyzeDependencies(numCourses4, prerequisites4);
        
        System.out.println("DFS Result: " + solution.canFinish(numCourses4, prerequisites4));
        System.out.println("Kahn's Algorithm Result: " + solution.canFinishKahn(numCourses4, prerequisites4));
        
        int[] order4 = solution.findOrder(numCourses4, prerequisites4);
        System.out.println("Course order: " + Arrays.toString(order4));
        
        // Example 5: No prerequisites
        System.out.println("\\n=== Example 5: No Prerequisites ===");
        int numCourses5 = 3;
        int[][] prerequisites5 = {};
        
        System.out.println("DFS Result: " + solution.canFinish(numCourses5, prerequisites5));
        System.out.println("Kahn's Algorithm Result: " + solution.canFinishKahn(numCourses5, prerequisites5));
        
        int[] order5 = solution.findOrder(numCourses5, prerequisites5);
        System.out.println("Course order: " + Arrays.toString(order5));
        
        // Performance test
        System.out.println("\\n=== Performance Test ===");
        int numCourses6 = 100;
        int[][] prerequisites6 = new int[150][2];
        Random rand = new Random(42);
        
        // Generate random prerequisites without obvious cycles
        for (int i = 0; i < 150; i++) {
            int course = rand.nextInt(numCourses6);
            int prereq = rand.nextInt(course + 1); // Only earlier courses as prerequisites
            prerequisites6[i] = new int[]{course, prereq};
        }
        
        long startTime = System.currentTimeMillis();
        boolean result6DFS = solution.canFinish(numCourses6, prerequisites6);
        long dfsTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        boolean result6Kahn = solution.canFinishKahn(numCourses6, prerequisites6);
        long kahnTime = System.currentTimeMillis() - startTime;
        
        System.out.println("100 courses, 150 prerequisites:");
        System.out.println("DFS Result: " + result6DFS + " (Time: " + dfsTime + " ms)");
        System.out.println("Kahn Result: " + result6Kahn + " (Time: " + kahnTime + " ms)");
    }
}
