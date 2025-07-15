//https://neetcode.io/problems/counting-bits?list=blind75
import java.util.*;

public class CountingBits {
    
    /**
     * Counts 1 bits for range [0, n] - Approach 1: Brute Force
     * Time Complexity: O(n * log n) - for each number, count bits
     * Space Complexity: O(1) excluding output array
     * 
     * @param n the upper bound of the range
     * @return array where result[i] = number of 1 bits in i
     */
    public int[] countBits(int n) {
        int[] result = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            result[i] = hammingWeight(i);
        }
        
        return result;
    }
    
    /**
     * Helper method to count 1 bits in a number using Brian Kernighan's algorithm
     */
    private int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n = n & (n - 1);  // Remove rightmost 1 bit
            count++;
        }
        return count;
    }
    
    /**
     * Counts 1 bits for range [0, n] - Approach 2: Dynamic Programming (Optimal)
     * Time Complexity: O(n) - single pass through all numbers
     * Space Complexity: O(1) excluding output array
     * 
     * Key insight: For any number i, countBits[i] = countBits[i >> 1] + (i & 1)
     * This means: bits in i = bits in (i/2) + 1 if i is odd
     * 
     * @param n the upper bound of the range
     * @return array where result[i] = number of 1 bits in i
     */
    public int[] countBitsOptimal(int n) {
        int[] result = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            // Right shift removes the least significant bit
            // (i & 1) adds 1 if the current number is odd (LSB = 1)
            result[i] = result[i >> 1] + (i & 1);
        }
        
        return result;
    }
    
    /**
     * Counts 1 bits for range [0, n] - Approach 3: DP with (i & (i-1)) pattern
     * Time Complexity: O(n) - single pass
     * Space Complexity: O(1) excluding output array
     * 
     * Key insight: For any number i, countBits[i] = countBits[i & (i-1)] + 1
     * This uses the fact that i & (i-1) removes the rightmost set bit
     * 
     * @param n the upper bound of the range
     * @return array where result[i] = number of 1 bits in i
     */
    public int[] countBitsAlternative(int n) {
        int[] result = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            // i & (i-1) removes the rightmost set bit
            // So we add 1 to the count of (i & (i-1))
            result[i] = result[i & (i - 1)] + 1;
        }
        
        return result;
    }
    
    /**
     * Counts 1 bits for range [0, n] - Approach 4: Using Built-in method
     * Time Complexity: O(n) - built-in bitCount is optimized
     * Space Complexity: O(1) excluding output array
     * 
     * @param n the upper bound of the range
     * @return array where result[i] = number of 1 bits in i
     */
    public int[] countBitsBuiltIn(int n) {
        int[] result = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            result[i] = Integer.bitCount(i);
        }
        
        return result;
    }
    
    /**
     * Helper method to convert integer to binary string
     */
    private String toBinary(int n) {
        return String.format("%8s", Integer.toBinaryString(n)).replace(' ', '0');
    }
    
    /**
     * Helper method to demonstrate the DP pattern
     */
    private void demonstratePattern(int n) {
        System.out.println("Demonstrating DP pattern for n = " + n);
        System.out.println("Number | Binary   | Bits | Calculation");
        System.out.println("-------|----------|------|------------");
        
        int[] result = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            if (i == 0) {
                result[i] = 0;
                System.out.printf("%-6d | %-8s | %-4d | Base case%n", 
                    i, toBinary(i), result[i]);
            } else {
                result[i] = result[i >> 1] + (i & 1);
                System.out.printf("%-6d | %-8s | %-4d | result[%d] + %d = %d + %d%n", 
                    i, toBinary(i), result[i], i >> 1, i & 1, result[i >> 1], i & 1);
            }
        }
        
        System.out.println("Final result: " + Arrays.toString(result));
    }
    
    /**
     * Helper method to validate all approaches give same result
     */
    private boolean validateApproaches(int n) {
        int[] result1 = countBits(n);
        int[] result2 = countBitsOptimal(n);
        int[] result3 = countBitsAlternative(n);
        int[] result4 = countBitsBuiltIn(n);
        
        return Arrays.equals(result1, result2) && 
               Arrays.equals(result2, result3) && 
               Arrays.equals(result3, result4);
    }
    
    /**
     * Test method to demonstrate all approaches
     */
    public static void main(String[] args) {
        CountingBits solution = new CountingBits();
        
        // Test case 1: n = 4
        int n1 = 4;
        System.out.println("Test Case 1: n = " + n1);
        System.out.println("Expected: [0,1,1,2,1]");
        System.out.println("Brute Force: " + Arrays.toString(solution.countBits(n1)));
        System.out.println("DP Optimal: " + Arrays.toString(solution.countBitsOptimal(n1)));
        System.out.println("DP Alternative: " + Arrays.toString(solution.countBitsAlternative(n1)));
        System.out.println("Built-in: " + Arrays.toString(solution.countBitsBuiltIn(n1)));
        System.out.println("All approaches match: " + solution.validateApproaches(n1));
        System.out.println();
        
        // Test case 2: n = 8
        int n2 = 8;
        System.out.println("Test Case 2: n = " + n2);
        System.out.println("DP Optimal: " + Arrays.toString(solution.countBitsOptimal(n2)));
        System.out.println("All approaches match: " + solution.validateApproaches(n2));
        System.out.println();
        
        // Test case 3: Edge cases
        System.out.println("Edge Cases:");
        System.out.println("n = 0: " + Arrays.toString(solution.countBitsOptimal(0)));
        System.out.println("n = 1: " + Arrays.toString(solution.countBitsOptimal(1)));
        System.out.println("n = 2: " + Arrays.toString(solution.countBitsOptimal(2)));
        System.out.println();
        
        // Demonstrate the DP pattern
        solution.demonstratePattern(8);
        
        // Performance comparison for larger input
        System.out.println("\nPerformance test for n = 1000:");
        long start, end;
        
        start = System.nanoTime();
        solution.countBits(1000);
        end = System.nanoTime();
        System.out.println("Brute Force: " + (end - start) / 1000000.0 + " ms");
        
        start = System.nanoTime();
        solution.countBitsOptimal(1000);
        end = System.nanoTime();
        System.out.println("DP Optimal: " + (end - start) / 1000000.0 + " ms");
        
        start = System.nanoTime();
        solution.countBitsAlternative(1000);
        end = System.nanoTime();
        System.out.println("DP Alternative: " + (end - start) / 1000000.0 + " ms");
        
        start = System.nanoTime();
        solution.countBitsBuiltIn(1000);
        end = System.nanoTime();
        System.out.println("Built-in: " + (end - start) / 1000000.0 + " ms");
    }
}
