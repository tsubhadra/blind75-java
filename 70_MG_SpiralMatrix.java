//https://neetcode.io/problems/spiral-matrix?list=blind75
import java.util.*;

public class SpiralMatrix {
    
    /**
     * Returns all elements of the matrix in spiral order
     * Time Complexity: O(m * n) where m is rows and n is columns
     * Space Complexity: O(1) excluding the result list
     * 
     * @param matrix the input m x n matrix
     * @return list of elements in spiral order
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Define boundaries
        int top = 0;
        int bottom = rows - 1;
        int left = 0;
        int right = cols - 1;
        
        while (top <= bottom && left <= right) {
            // Traverse right along the top row
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++;
            
            // Traverse down along the right column
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            
            // Traverse left along the bottom row (if we still have rows)
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--;
            }
            
            // Traverse up along the left column (if we still have columns)
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }
        
        return result;
    }
    
    /**
     * Test method to demonstrate the solution
     */
    public static void main(String[] args) {
        SpiralMatrix solution = new SpiralMatrix();
        
        // Test case 1
        int[][] matrix1 = {{1, 2}, {3, 4}};
        System.out.println("Input: " + Arrays.deepToString(matrix1));
        System.out.println("Output: " + solution.spiralOrder(matrix1));
        // Expected: [1, 2, 4, 3]
        
        // Test case 2
        int[][] matrix2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println("\nInput: " + Arrays.deepToString(matrix2));
        System.out.println("Output: " + solution.spiralOrder(matrix2));
        // Expected: [1, 2, 3, 6, 9, 8, 7, 4, 5]
        
        // Test case 3
        int[][] matrix3 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println("\nInput: " + Arrays.deepToString(matrix3));
        System.out.println("Output: " + solution.spiralOrder(matrix3));
        // Expected: [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]
        
        // Edge case: single row
        int[][] matrix4 = {{1, 2, 3, 4, 5}};
        System.out.println("\nInput: " + Arrays.deepToString(matrix4));
        System.out.println("Output: " + solution.spiralOrder(matrix4));
        // Expected: [1, 2, 3, 4, 5]
        
        // Edge case: single column
        int[][] matrix5 = {{1}, {2}, {3}, {4}};
        System.out.println("\nInput: " + Arrays.deepToString(matrix5));
        System.out.println("Output: " + solution.spiralOrder(matrix5));
        // Expected: [1, 2, 3, 4]
    }
}
