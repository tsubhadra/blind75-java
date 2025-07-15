//https://neetcode.io/problems/set-zeroes-in-matrix?list=blind75
import java.util.*;

public class SetMatrixZeroes {
    
    /**
     * Sets entire row and column to zero if element is 0 - O(m+n) space approach
     * Time Complexity: O(m * n)
     * Space Complexity: O(m + n) for storing row and column flags
     * 
     * @param matrix the input m x n matrix to modify in-place
     */
    public void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Track which rows and columns should be set to zero
        boolean[] zeroRows = new boolean[rows];
        boolean[] zeroCols = new boolean[cols];
        
        // First pass: identify zeros and mark rows/columns
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    zeroRows[i] = true;
                    zeroCols[j] = true;
                }
            }
        }
        
        // Second pass: set zeros based on marked rows/columns
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (zeroRows[i] || zeroCols[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    
    /**
     * Sets entire row and column to zero if element is 0 - O(1) space approach
     * Uses first row and column as markers to achieve constant space
     * Time Complexity: O(m * n)
     * Space Complexity: O(1)
     * 
     * @param matrix the input m x n matrix to modify in-place
     */
    public void setZeroesOptimal(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Check if first row and first column originally contain zeros
        boolean firstRowZero = false;
        boolean firstColZero = false;
        
        // Check first row
        for (int j = 0; j < cols; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }
        
        // Check first column
        for (int i = 0; i < rows; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }
        
        // Use first row and column as markers for zeros in the rest of matrix
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;  // Mark row
                    matrix[0][j] = 0;  // Mark column
                }
            }
        }
        
        // Set zeros based on markers (excluding first row and column)
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // Handle first row if it should be zero
        if (firstRowZero) {
            for (int j = 0; j < cols; j++) {
                matrix[0][j] = 0;
            }
        }
        
        // Handle first column if it should be zero
        if (firstColZero) {
            for (int i = 0; i < rows; i++) {
                matrix[i][0] = 0;
            }
        }
    }
    
    /**
     * Helper method to print matrix
     */
    private void printMatrix(int[][] matrix) {
        System.out.println("[");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("  " + Arrays.toString(matrix[i]));
            if (i < matrix.length - 1) System.out.print(",");
            System.out.println();
        }
        System.out.println("]");
    }
    
    /**
     * Helper method to create a deep copy of matrix for testing
     */
    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
    
    /**
     * Test method to demonstrate both solutions
     */
    public static void main(String[] args) {
        SetMatrixZeroes solution = new SetMatrixZeroes();
        
        // Test case 1
        int[][] matrix1 = {{0, 1}, {1, 0}};
        System.out.println("Test Case 1:");
        System.out.println("Original matrix:");
        solution.printMatrix(matrix1);
        
        int[][] copy1 = solution.deepCopy(matrix1);
        solution.setZeroes(matrix1);
        System.out.println("After setZeroes (O(m+n) space):");
        solution.printMatrix(matrix1);
        
        solution.setZeroesOptimal(copy1);
        System.out.println("After setZeroesOptimal (O(1) space):");
        solution.printMatrix(copy1);
        
        // Test case 2
        int[][] matrix2 = {{1, 2, 3}, {4, 0, 5}, {6, 7, 8}};
        System.out.println("\nTest Case 2:");
        System.out.println("Original matrix:");
        solution.printMatrix(matrix2);
        
        int[][] copy2 = solution.deepCopy(matrix2);
        solution.setZeroes(matrix2);
        System.out.println("After setZeroes (O(m+n) space):");
        solution.printMatrix(matrix2);
        
        solution.setZeroesOptimal(copy2);
        System.out.println("After setZeroesOptimal (O(1) space):");
        solution.printMatrix(copy2);
        
        // Test case 3: Edge case with first row/column zeros
        int[][] matrix3 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 0}};
        System.out.println("\nTest Case 3 (Edge case):");
        System.out.println("Original matrix:");
        solution.printMatrix(matrix3);
        
        int[][] copy3 = solution.deepCopy(matrix3);
        solution.setZeroesOptimal(copy3);
        System.out.println("After setZeroesOptimal (O(1) space):");
        solution.printMatrix(copy3);
    }
}
