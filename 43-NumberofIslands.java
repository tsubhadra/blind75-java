//https://neetcode.io/problems/count-number-of-islands?list=blind75
import java.util.*;

public class NumberOfIslands {
    
    // Solution 1: DFS Approach (Recursive)
    public int numIslandsDFS(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        int numIslands = 0;
        
        // Iterate through each cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // If we find an unvisited land cell, it's a new island
                if (grid[i][j] == '1') {
                    numIslands++;
                    dfs(grid, i, j); // Mark all connected land cells
                }
            }
        }
        
        return numIslands;
    }
    
    private void dfs(char[][] grid, int row, int col) {
        // Check bounds and if current cell is water or already visited
        if (row < 0 || row >= grid.length || 
            col < 0 || col >= grid[0].length || 
            grid[row][col] == '0') {
            return;
        }
        
        // Mark current cell as visited (change '1' to '0')
        grid[row][col] = '0';
        
        // Explore all 4 directions
        dfs(grid, row - 1, col); // up
        dfs(grid, row + 1, col); // down
        dfs(grid, row, col - 1); // left
        dfs(grid, row, col + 1); // right
    }
    
    // Solution 2: BFS Approach (Iterative)
    public int numIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        int numIslands = 0;
        
        // Iterate through each cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // If we find an unvisited land cell, it's a new island
                if (grid[i][j] == '1') {
                    numIslands++;
                    bfs(grid, i, j); // Mark all connected land cells
                }
            }
        }
        
        return numIslands;
    }
    
    private void bfs(char[][] grid, int startRow, int startCol) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        grid[startRow][startCol] = '0'; // Mark as visited
        
        // Directions: up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            
            // Explore all 4 directions
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // Check bounds and if it's an unvisited land cell
                if (newRow >= 0 && newRow < grid.length && 
                    newCol >= 0 && newCol < grid[0].length && 
                    grid[newRow][newCol] == '1') {
                    
                    grid[newRow][newCol] = '0'; // Mark as visited
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
    }
    
    // Solution 3: DFS with visited array (preserves original grid)
    public int numIslandsPreserveGrid(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int numIslands = 0;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    numIslands++;
                    dfsWithVisited(grid, visited, i, j);
                }
            }
        }
        
        return numIslands;
    }
    
    private void dfsWithVisited(char[][] grid, boolean[][] visited, int row, int col) {
        if (row < 0 || row >= grid.length || 
            col < 0 || col >= grid[0].length || 
            visited[row][col] || grid[row][col] == '0') {
            return;
        }
        
        visited[row][col] = true;
        
        // Explore all 4 directions
        dfsWithVisited(grid, visited, row - 1, col);
        dfsWithVisited(grid, visited, row + 1, col);
        dfsWithVisited(grid, visited, row, col - 1);
        dfsWithVisited(grid, visited, row, col + 1);
    }
    
    // Solution 4: Union-Find approach
    public int numIslandsUnionFind(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        UnionFind uf = new UnionFind(grid);
        
        // Directions for adjacent cells
        int[][] directions = {{0, 1}, {1, 0}};
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    // Check right and down neighbors
                    for (int[] dir : directions) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];
                        
                        if (ni < rows && nj < cols && grid[ni][nj] == '1') {
                            int id1 = i * cols + j;
                            int id2 = ni * cols + nj;
                            uf.union(id1, id2);
                        }
                    }
                }
            }
        }
        
        return uf.getCount();
    }
    
    // Union-Find data structure
    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int count;
        
        public UnionFind(char[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            parent = new int[rows * cols];
            rank = new int[rows * cols];
            count = 0;
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == '1') {
                        int id = i * cols + j;
                        parent[id] = id;
                        count++;
                    }
                    rank[i * cols + j] = 0;
                }
            }
        }
        
        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]); // Path compression
            }
            return parent[i];
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
                count--;
            }
        }
        
        public int getCount() {
            return count;
        }
    }
    
    // Utility method to print grid
    private void printGrid(char[][] grid) {
        for (char[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
    
    // Utility method to copy grid
    private char[][] copyGrid(char[][] original) {
        char[][] copy = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
    
    // Test method
    public static void main(String[] args) {
        NumberOfIslands solution = new NumberOfIslands();
        
        // Example 1
        System.out.println("=== Example 1 ===");
        char[][] grid1 = {
            {'0','1','1','1','0'},
            {'0','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        
        System.out.println("Original grid:");
        solution.printGrid(grid1);
        
        char[][] grid1Copy1 = solution.copyGrid(grid1);
        char[][] grid1Copy2 = solution.copyGrid(grid1);
        char[][] grid1Copy3 = solution.copyGrid(grid1);
        char[][] grid1Copy4 = solution.copyGrid(grid1);
        
        System.out.println("DFS result: " + solution.numIslandsDFS(grid1Copy1));
        System.out.println("BFS result: " + solution.numIslandsBFS(grid1Copy2));
        System.out.println("DFS with visited array: " + solution.numIslandsPreserveGrid(grid1Copy3));
        System.out.println("Union-Find result: " + solution.numIslandsUnionFind(grid1Copy4));
        
        // Example 2
        System.out.println("\n=== Example 2 ===");
        char[][] grid2 = {
            {'1','1','0','0','1'},
            {'1','1','0','0','1'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        
        System.out.println("Original grid:");
        solution.printGrid(grid2);
        
        char[][] grid2Copy1 = solution.copyGrid(grid2);
        char[][] grid2Copy2 = solution.copyGrid(grid2);
        char[][] grid2Copy3 = solution.copyGrid(grid2);
        char[][] grid2Copy4 = solution.copyGrid(grid2);
        
        System.out.println("DFS result: " + solution.numIslandsDFS(grid2Copy1));
        System.out.println("BFS result: " + solution.numIslandsBFS(grid2Copy2));
        System.out.println("DFS with visited array: " + solution.numIslandsPreserveGrid(grid2Copy3));
        System.out.println("Union-Find result: " + solution.numIslandsUnionFind(grid2Copy4));
        
        // Additional test cases
        System.out.println("\n=== Additional Tests ===");
        
        // Single island
        char[][] grid3 = {
            {'1','1','1'},
            {'1','1','1'},
            {'1','1','1'}
        };
        System.out.println("Single large island: " + solution.numIslandsDFS(solution.copyGrid(grid3)));
        
        // All water
        char[][] grid4 = {
            {'0','0','0'},
            {'0','0','0'},
            {'0','0','0'}
        };
        System.out.println("All water: " + solution.numIslandsDFS(solution.copyGrid(grid4)));
        
        // All land
        char[][] grid5 = {
            {'1','0','1'},
            {'0','1','0'},
            {'1','0','1'}
        };
        System.out.println("Checkerboard pattern: " + solution.numIslandsDFS(solution.copyGrid(grid5)));
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        char[][] largeGrid = new char[50][50];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                largeGrid[i][j] = (i + j) % 3 == 0 ? '1' : '0';
            }
        }
        
        long startTime = System.currentTimeMillis();
        int result = solution.numIslandsDFS(solution.copyGrid(largeGrid));
        long endTime = System.currentTimeMillis();
        
        System.out.println("50x50 grid islands: " + result);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}
