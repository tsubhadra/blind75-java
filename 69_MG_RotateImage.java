//https://neetcode.io/problems/rotate-matrix?list=blind75

public class Solution {
    
    // Approach 1: Transpose + Reverse - Most intuitive and elegant
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        
        // Step 1: Transpose the matrix (swap elements across main diagonal)
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
        }
    }
    
    // Approach 2: Layer-by-layer rotation (4-way swap)
    public void rotateLayerByLayer(int[][] matrix) {
        int n = matrix.length;
        
        // Process each layer from outside to inside
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            
            // Rotate elements in current layer
            for (int i = first; i < last; i++) {
                int offset = i - first;
                
                // Save top element
                int top = matrix[first][i];
                
                // Top = Left
                matrix[first][i] = matrix[last - offset][first];
                
                // Left = Bottom
                matrix[last - offset][first] = matrix[last][last - offset];
                
                // Bottom = Right
                matrix[last][last - offset] = matrix[i][last];
                
                // Right = Top (saved)
                matrix[i][last] = top;
            }
        }
    }
    
    // Approach 3: Direct coordinate transformation
    public void rotateDirectTransform(int[][] matrix) {
        int n = matrix.length;
        
        // Use a temporary matrix to store rotated values
        int[][] temp = new int[n][n];
        
        // Apply rotation transformation: (i, j) -> (j, n-1-i)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[j][n - 1 - i] = matrix[i][j];
            }
        }
        
        // Copy back to original matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = temp[i][j];
            }
        }
    }
    
    // Approach 4: Cycle detection and rotation
    public void rotateCycles(int[][] matrix) {
        int n = matrix.length;
        boolean[][] visited = new boolean[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j]) {
                    rotateCycle(matrix, i, j, visited);
                }
            }
        }
    }
    
    private void rotateCycle(int[][] matrix, int startI, int startJ, 
                           boolean[][] visited) {
        int n = matrix.length;
        int i = startI, j = startJ;
        int prev = matrix[i][j];
        
        do {
            // Calculate next position after 90-degree rotation
            int nextI = j;
            int nextJ = n - 1 - i;
            
            // Swap with next position
            int temp = matrix[nextI][nextJ];
            matrix[nextI][nextJ] = prev;
            prev = temp;
            
            visited[i][j] = true;
            
            // Move to next position
            i = nextI;
            j = nextJ;
            
        } while (i != startI || j != startJ);
    }
    
    // Approach 5: Recursive rotation (divide and conquer)
    public void rotateRecursive(int[][] matrix) {
        rotateRecursiveHelper(matrix, 0, matrix.length - 1);
    }
    
    private void rotateRecursiveHelper(int[][] matrix, int start, int end) {
        if (start >= end) {
            return; // Base case: single element or invalid range
        }
        
        // Rotate current layer
        for (int i = start; i < end; i++) {
            int offset = i - start;
            
            // Save top
            int temp = matrix[start][i];
            
            // Move left to top
            matrix[start][i] = matrix[end - offset][start];
            
            // Move bottom to left
            matrix[end - offset][start] = matrix[end][end - offset];
            
            // Move right to bottom
            matrix[end][end - offset] = matrix[i][end];
            
            // Move top to right
            matrix[i][end] = temp;
        }
        
        // Recursively rotate inner layer
        rotateRecursiveHelper(matrix, start + 1, end - 1);
    }
    
    // Helper method to print matrix
    private void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(java.util.Arrays.toString(row));
        }
    }
    
    // Helper method to copy matrix
    private int[][] copyMatrix(int[][] matrix) {
        int n = matrix.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = matrix[i][j];
            }
        }
        return copy;
    }
    
    // Method to demonstrate the transpose + reverse approach step by step
    public void demonstrateTransposeReverse(int[][] matrix) {
        System.out.println("\nDemonstrating Transpose + Reverse Approach:");
        System.out.println("Original matrix:");
        printMatrix(matrix);
        
        int n = matrix.length;
        
        // Step 1: Transpose
        System.out.println("\nStep 1: Transpose (swap across main diagonal):");
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i != j) {
                    System.out.printf("Swap matrix[%d][%d]=%d with matrix[%d][%d]=%d%n",
                                    i, j, matrix[i][j], j, i, matrix[j][i]);
                }
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        System.out.println("After transpose:");
        printMatrix(matrix);
        
        // Step 2: Reverse each row
        System.out.println("\nStep 2: Reverse each row:");
        for (int i = 0; i < n; i++) {
            System.out.printf("Row %d before: %s", 
                            i, java.util.Arrays.toString(matrix[i]));
            
            int left = 0, right = n - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
            
            System.out.printf(" -> after: %s%n", 
                            java.util.Arrays.toString(matrix[i]));
        }
        
        System.out.println("\nFinal rotated matrix:");
        printMatrix(matrix);
    }
    
    // Method to demonstrate layer-by-layer rotation
    public void demonstrateLayerRotation(int[][] matrix) {
        System.out.println("\nDemonstrating Layer-by-Layer Rotation:");
        System.out.println("Original matrix:");
        printMatrix(matrix);
        
        int n = matrix.length;
        
        for (int layer = 0; layer < n / 2; layer++) {
            System.out.printf("\nProcessing layer %d:%n", layer);
            
            int first = layer;
            int last = n - 1 - layer;
            
            System.out.printf("Layer boundaries: first=%d, last=%d%n", 
                            first, last);
            
            for (int i = first; i < last; i++) {
                int offset = i - first;
                
                System.out.printf("  Rotating group at offset %d:%n", offset);
                
                // Show the 4 positions being rotated
                int topRow = first, topCol = i;
                int rightRow = i, rightCol = last;
                int bottomRow = last, bottomCol = last - offset;
                int leftRow = last - offset, leftCol = first;
                
                System.out.printf("    Top[%d][%d]=%d -> Right[%d][%d]%n",
                                topRow, topCol, matrix[topRow][topCol],
                                rightRow, rightCol);
                System.out.printf("    Right[%d][%d]=%d -> Bottom[%d][%d]%n",
                                rightRow, rightCol, matrix[rightRow][rightCol],
                                bottomRow, bottomCol);
                System.out.printf("    Bottom[%d][%d]=%d -> Left[%d][%d]%n",
                                bottomRow, bottomCol, matrix[bottomRow][bottomCol],
                                leftRow, leftCol);
                System.out.printf("    Left[%d][%d]=%d -> Top[%d][%d]%n",
                                leftRow, leftCol, matrix[leftRow][leftCol],
                                topRow, topCol);
                
                // Perform the 4-way rotation
                int temp = matrix[topRow][topCol];
                matrix[topRow][topCol] = matrix[leftRow][leftCol];
                matrix[leftRow][leftCol] = matrix[bottomRow][bottomCol];
                matrix[bottomRow][bottomCol] = matrix[rightRow][rightCol];
                matrix[rightRow][rightCol] = temp;
            }
            
            System.out.println("  Matrix after this layer:");
            printMatrix(matrix);
        }
        
        System.out.println("\nFinal rotated matrix:");
        printMatrix(matrix);
    }
    
    // Method to visualize coordinate transformation
    public void visualizeTransformation(int[][] matrix) {
        System.out.println("\nCoordinate Transformation Visualization:");
        System.out.println("Original matrix with coordinates:");
        
        int n = matrix.length;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("(%d,%d):%d ", i, j, matrix[i][j]);
            }
            System.out.println();
        }
        
        System.out.println("\nTransformation rule: (i,j) -> (j, n-1-i)");
        System.out.println("Mapping:");
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int newI = j;
                int newJ = n - 1 - i;
                System.out.printf("(%d,%d) -> (%d,%d): %d%n", 
                                i, j, newI, newJ, matrix[i][j]);
            }
        }
    }
    
    // Method to compare performance of different approaches
    public void compareApproaches(int[][] matrix) {
        System.out.println("\nComparing approaches for " + 
                          matrix.length + "x" + matrix.length + " matrix:");
        
        long start, end;
        
        // Transpose + Reverse
        int[][] copy1 = copyMatrix(matrix);
        start = System.nanoTime();
        rotate(copy1);
        end = System.nanoTime();
        System.out.println("Transpose + Reverse: " + (end - start) + " ns");
        
        // Layer-by-layer
        int[][] copy2 = copyMatrix(matrix);
        start = System.nanoTime();
        rotateLayerByLayer(copy2);
        end = System.nanoTime();
        System.out.println("Layer-by-layer: " + (end - start) + " ns");
        
        // Cycles
        int[][] copy3 = copyMatrix(matrix);
        start = System.nanoTime();
        rotateCycles(copy3);
        end = System.nanoTime();
        System.out.println("Cycles: " + (end - start) + " ns");
        
        // Recursive
        int[][] copy4 = copyMatrix(matrix);
        start = System.nanoTime();
        rotateRecursive(copy4);
        end = System.nanoTime();
        System.out.println("Recursive: " + (end - start) + " ns");
        
        // Verify all approaches give same result
        boolean allSame = true;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (copy1[i][j] != copy2[i][j] || 
                    copy1[i][j] != copy3[i][j] || 
                    copy1[i][j] != copy4[i][j]) {
                    allSame = false;
                    break;
                }
            }
        }
        System.out.println("All approaches match: " + allSame);
    }
    
    // Method to test edge cases
    public void testEdgeCases() {
        System.out.println("\nTesting Edge Cases:");
        
        // 1x1 matrix
        int[][] single = {{5}};
        System.out.println("1x1 matrix:");
        System.out.println("Before: " + java.util.Arrays.deepToString(single));
        rotate(copyMatrix(single));
        System.out.println("After: " + java.util.Arrays.deepToString(single));
        
        // 2x2 matrix
        int[][] two = {{1, 2}, {3, 4}};
        System.out.println("\n2x2 matrix:");
        System.out.println("Before: " + java.util.Arrays.deepToString(two));
        int[][] rotatedTwo = copyMatrix(two);
        rotate(rotatedTwo);
        System.out.println("After: " + java.util.Arrays.deepToString(rotatedTwo));
        
        // Matrix with negative numbers
        int[][] negative = {{-1, -2}, {-3, -4}};
        System.out.println("\nMatrix with negatives:");
        System.out.println("Before: " + java.util.Arrays.deepToString(negative));
        int[][] rotatedNeg = copyMatrix(negative);
        rotate(rotatedNeg);
        System.out.println("After: " + java.util.Arrays.deepToString(rotatedNeg));
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        int[][][] testCases = {
            {{1, 2}, {3, 4}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}},
            {{1}},
            {{1, 2, 3, 4, 5}, 
             {6, 7, 8, 9, 10}, 
             {11, 12, 13, 14, 15}, 
             {16, 17, 18, 19, 20}, 
             {21, 22, 23, 24, 25}}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[][] matrix = testCases[i];
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("Test Case " + (i + 1) + ": " + 
                              matrix.length + "x" + matrix.length + " matrix");
            System.out.println("=".repeat(50));
            
            System.out.println("Original matrix:");
            solution.printMatrix(matrix);
            
            // Test all approaches
            int[][] copy1 = solution.copyMatrix(matrix);
            int[][] copy2 = solution.copyMatrix(matrix);
            int[][] copy3 = solution.copyMatrix(matrix);
            int[][] copy4 = solution.copyMatrix(matrix);
            int[][] copy5 = solution.copyMatrix(matrix);
            
            solution.rotate(copy1);
            solution.rotateLayerByLayer(copy2);
            solution.rotateDirectTransform(copy3);
            solution.rotateCycles(copy4);
            solution.rotateRecursive(copy5);
            
            System.out.println("\nResults:");
            System.out.println("Transpose + Reverse:");
            solution.printMatrix(copy1);
            
            System.out.println("Layer-by-layer:");
            solution.printMatrix(copy2);
            
            // Demonstrate algorithms for smaller matrices
            if (matrix.length <= 3) {
                solution.demonstrateTransposeReverse(solution.copyMatrix(matrix));
                solution.demonstrateLayerRotation(solution.copyMatrix(matrix));
                solution.visualizeTransformation(matrix);
            }
            
            // Performance comparison for larger matrices
            if (matrix.length >= 3) {
                solution.compareApproaches(matrix);
            }
        }
        
        // Test edge cases
        solution.testEdgeCases();
    }
}
