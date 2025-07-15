//https://neetcode.io/problems/pacific-atlantic-water-flow?list=blind75
import java.util.*;

public class PacificAtlanticWaterFlow {
    
    private int rows, cols;
    private int[][] heights;
    private boolean[][] pacificReachable;
    private boolean[][] atlanticReachable;
    
    // Directions: up, down, left, right
    private final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    // Solution 1: DFS Approach (Most Intuitive)
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return new ArrayList<>();
        }
        
        this.heights = heights;
        rows = heights.length;
        cols = heights[0].length;
        
        // Track which cells can reach each ocean
        pacificReachable = new boolean[rows][cols];
        atlanticReachable = new boolean[rows][cols];
        
        // Start DFS from Pacific borders (top and left edges)
        for (int i = 0; i < rows; i++) {
            dfs(i, 0, pacificReachable); // Left edge
        }
        for (int j = 0; j < cols; j++) {
            dfs(0, j, pacificReachable); // Top edge
        }
        
        // Start DFS from Atlantic borders (bottom and right edges)
        for (int i = 0; i < rows; i++) {
            dfs(i, cols - 1, atlanticReachable); // Right edge
        }
        for (int j = 0; j < cols; j++) {
            dfs(rows - 1, j, atlanticReachable); // Bottom edge
        }
        
        // Find cells that can reach both oceans
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacificReachable[i][j] && atlanticReachable[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        
        return result;
    }
    
    private void dfs(int row, int col, boolean[][] reachable) {
        // Mark current cell as reachable
        reachable[row][col] = true;
        
        // Explore all 4 directions
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            // Check bounds and conditions
            if (isValidCell(newRow, newCol) && 
                !reachable[newRow][newCol] && 
                heights[newRow][newCol] >= heights[row][col]) {
                dfs(newRow, newCol, reachable);
            }
        }
    }
    
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
    
    // Solution 2: BFS Approach
    public List<List<Integer>> pacificAtlanticBFS(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return new ArrayList<>();
        }
        
        this.heights = heights;
        rows = heights.length;
        cols = heights[0].length;
        
        boolean[][] pacificReachable = new boolean[rows][cols];
        boolean[][] atlanticReachable = new boolean[rows][cols];
        
        Queue<int[]> pacificQueue = new LinkedList<>();
        Queue<int[]> atlanticQueue = new LinkedList<>();
        
        // Add Pacific border cells to queue
        for (int i = 0; i < rows; i++) {
            pacificQueue.offer(new int[]{i, 0});
            pacificReachable[i][0] = true;
        }
        for (int j = 1; j < cols; j++) {
            pacificQueue.offer(new int[]{0, j});
            pacificReachable[0][j] = true;
        }
        
        // Add Atlantic border cells to queue
        for (int i = 0; i < rows; i++) {
            atlanticQueue.offer(new int[]{i, cols - 1});
            atlanticReachable[i][cols - 1] = true;
        }
        for (int j = 0; j < cols - 1; j++) {
            atlanticQueue.offer(new int[]{rows - 1, j});
            atlanticReachable[rows - 1][j] = true;
        }
        
        // BFS from Pacific borders
        bfs(pacificQueue, pacificReachable);
        
        // BFS from Atlantic borders
        bfs(atlanticQueue, atlanticReachable);
        
        // Find cells that can reach both oceans
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacificReachable[i][j] && atlanticReachable[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        
        return result;
    }
    
    private void bfs(Queue<int[]> queue, boolean[][] reachable) {
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            
            // Explore all 4 directions
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // Check bounds and conditions
                if (isValidCell(newRow, newCol) && 
                    !reachable[newRow][newCol] && 
                    heights[newRow][newCol] >= heights[row][col]) {
                    reachable[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
    }
    
    // Solution 3: Optimized single-pass DFS
    public List<List<Integer>> pacificAtlanticOptimized(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return new ArrayList<>();
        }
        
        this.heights = heights;
        rows = heights.length;
        cols = heights[0].length;
        
        // Use bit manipulation to track reachability
        // 0: not visited, 1: Pacific reachable, 2: Atlantic reachable, 3: both reachable
        int[][] reachability = new int[rows][cols];
        
        // DFS from Pacific borders
        for (int i = 0; i < rows; i++) {
            dfsOptimized(i, 0, reachability, 1); // Left edge
        }
        for (int j = 0; j < cols; j++) {
            dfsOptimized(0, j, reachability, 1); // Top edge
        }
        
        // DFS from Atlantic borders
        for (int i = 0; i < rows; i++) {
            dfsOptimized(i, cols - 1, reachability, 2); // Right edge
        }
        for (int j = 0; j < cols; j++) {
            dfsOptimized(rows - 1, j, reachability, 2); // Bottom edge
        }
        
        // Collect results
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (reachability[i][j] == 3) { // Both oceans reachable
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        
        return result;
    }
    
    private void dfsOptimized(int row, int col, int[][] reachability, int oceanFlag) {
        // If already marked for this ocean, skip
        if ((reachability[row][col] & oceanFlag) != 0) {
            return;
        }
        
        // Mark as reachable for this ocean
        reachability[row][col] |= oceanFlag;
        
        // Explore all 4 directions
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            // Check bounds and conditions
            if (isValidCell(newRow, newCol) && 
                heights[newRow][newCol] >= heights[row][col]) {
                dfsOptimized(newRow, newCol, reachability, oceanFlag);
            }
        }
    }
    
    // Utility method to visualize the grid and reachability
    public void visualizeResult(int[][] heights, List<List<Integer>> result) {
        System.out.println("Heights grid:");
        for (int[] row : heights) {
            System.out.println(Arrays.toString(row));
        }
        
        System.out.println("\nCells reaching both oceans (marked with *):");
        Set<String> resultSet = new HashSet<>();
        for (List<Integer> cell : result) {
            resultSet.add(cell.get(0) + "," + cell.get(1));
        }
        
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                if (resultSet.contains(i + "," + j)) {
                    System.out.printf("%3s ", "*");
                } else {
                    System.out.printf("%3d ", heights[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Test method
    public static void main(String[] args) {
        PacificAtlanticWaterFlow solution = new PacificAtlanticWaterFlow();
        
        // Example 1
        System.out.println("=== Example 1 ===");
        int[][] heights1 = {
            {4, 2, 7, 3, 4},
            {7, 4, 6, 4, 7},
            {6, 3, 5, 3, 6}
        };
        
        List<List<Integer>> result1 = solution.pacificAtlantic(heights1);
        System.out.println("DFS Result: " + result1);
        solution.visualizeResult(heights1, result1);
        
        List<List<Integer>> result1BFS = solution.pacificAtlanticBFS(heights1);
        System.out.println("BFS Result: " + result1BFS);
        
        List<List<Integer>> result1Opt = solution.pacificAtlanticOptimized(heights1);
        System.out.println("Optimized Result: " + result1Opt);
        
        // Example 2
        System.out.println("=== Example 2 ===");
        int[][] heights2 = {{1}};
        
        List<List<Integer>> result2 = solution.pacificAtlantic(heights2);
        System.out.println("Single cell result: " + result2);
        solution.visualizeResult(heights2, result2);
        
        // Example 3 - More complex
        System.out.println("=== Example 3 (Complex) ===");
        int[][] heights3 = {
            {1, 2, 2, 3, 5},
            {3, 2, 3, 4, 4},
            {2, 4, 5, 3, 1},
            {6, 7, 1, 4, 5},
            {5, 1, 1, 2, 4}
        };
        
        List<List<Integer>> result3 = solution.pacificAtlantic(heights3);
        System.out.println("Complex grid result: " + result3);
        solution.visualizeResult(heights3, result3);
        
        // Performance test
        System.out.println("=== Performance Test ===");
        int[][] largeGrid = new int[50][50];
        Random rand = new Random(42); // Fixed seed for reproducibility
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                largeGrid[i][j] = rand.nextInt(1000);
            }
        }
        
        long startTime = System.currentTimeMillis();
        List<List<Integer>> largeResult = solution.pacificAtlantic(largeGrid);
        long endTime = System.currentTimeMillis();
        
        System.out.println("50x50 grid result count: " + largeResult.size());
        System.out.println("DFS Time taken: " + (endTime - startTime) + " ms");
        
        startTime = System.currentTimeMillis();
        List<List<Integer>> largeResultBFS = solution.pacificAtlanticBFS(largeGrid);
        endTime = System.currentTimeMillis();
        
        System.out.println("BFS Time taken: " + (endTime - startTime) + " ms");
        
        startTime = System.currentTimeMillis();
        List<List<Integer>> largeResultOpt = solution.pacificAtlanticOptimized(largeGrid);
        endTime = System.currentTimeMillis();
        
        System.out.println("Optimized Time taken: " + (endTime - startTime) + " ms");
    }
}
