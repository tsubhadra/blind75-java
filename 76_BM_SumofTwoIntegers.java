//https://neetcode.io/problems/sum-of-two-integers?list=blind75
public class SumOfTwoIntegers {
    
    /**
     * Adds two integers without using + or - operators - Iterative approach
     * Time Complexity: O(log max(a,b)) - number of carry operations
     * Space Complexity: O(1) - constant extra space
     * 
     * Key insights:
     * - XOR gives sum without carry: a ^ b
     * - AND gives carry positions: (a & b) << 1
     * - Repeat until no carry remains
     * 
     * @param a first integer
     * @param b second integer
     * @return sum of a and b without using + or -
     */
    public int getSum(int a, int b) {
        while (b != 0) {
            // Calculate carry: positions where both bits are 1
            int carry = (a & b) << 1;
            
            // Calculate sum without carry: XOR of the bits
            a = a ^ b;
            
            // Update b to be the carry for next iteration
            b = carry;
        }
        
        return a;
    }
    
    /**
     * Adds two integers - Recursive approach
     * Time Complexity: O(log max(a,b)) - recursion depth
     * Space Complexity: O(log max(a,b)) - recursion stack
     * 
     * @param a first integer
     * @param b second integer
     * @return sum of a and b without using + or -
     */
    public int getSumRecursive(int a, int b) {
        // Base case: no carry
        if (b == 0) {
            return a;
        }
        
        // Recursive case: sum without carry, and carry
        return getSumRecursive(a ^ b, (a & b) << 1);
    }
    
    /**
     * Subtracts two integers without using + or - operators
     * Time Complexity: O(log max(a,b))
     * Space Complexity: O(1)
     * 
     * Subtraction: a - b = a + (-b)
     * Two's complement: -b = ~b + 1
     * 
     * @param a first integer (minuend)
     * @param b second integer (subtrahend)
     * @return difference a - b without using + or -
     */
    public int getSubtraction(int a, int b) {
        // Convert subtraction to addition: a - b = a + (-b)
        // Two's complement of b: ~b + 1
        return getSum(a, getSum(~b, 1));
    }
    
    /**
     * Multiplies two integers without using *, +, or - operators
     * Time Complexity: O(log b) - number of bits in b
     * Space Complexity: O(1)
     * 
     * Uses bit shifting and addition (Russian peasant multiplication)
     * 
     * @param a first integer
     * @param b second integer (must be positive for this implementation)
     * @return product of a and b
     */
    public int getMultiplication(int a, int b) {
        if (b == 0) return 0;
        
        boolean negative = false;
        
        // Handle negative numbers
        if (b < 0) {
            b = getSubtraction(0, b);  // Make b positive
            negative = !negative;
        }
        if (a < 0) {
            a = getSubtraction(0, a);  // Make a positive
            negative = !negative;
        }
        
        int result = 0;
        
        while (b > 0) {
            // If current bit of b is 1, add current a to result
            if ((b & 1) == 1) {
                result = getSum(result, a);
            }
            
            // Double a and halve b
            a <<= 1;  // a = a * 2
            b >>>= 1; // b = b / 2 (unsigned right shift)
        }
        
        // Apply sign if needed
        return negative ? getSubtraction(0, result) : result;
    }
    
    /**
     * Helper method to convert integer to binary string with sign
     */
    private String toBinaryWithSign(int n) {
        if (n >= 0) {
            return "+" + String.format("%8s", Integer.toBinaryString(n)).replace(' ', '0');
        } else {
            return "-" + String.format("%8s", Integer.toBinaryString(-n)).replace(' ', '0');
        }
    }
    
    /**
     * Helper method to demonstrate addition step by step
     */
    private void demonstrateAddition(int a, int b) {
        System.out.println("Demonstrating addition: " + a + " + " + b);
        System.out.println("a = " + a + " (binary: " + toBinaryWithSign(a) + ")");
        System.out.println("b = " + b + " (binary: " + toBinaryWithSign(b) + ")");
        System.out.println();
        
        int step = 1;
        while (b != 0) {
            int carry = (a & b) << 1;
            int sum = a ^ b;
            
            System.out.println("Step " + step + ":");
            System.out.println("  a & b = " + (a & b) + " (binary: " + String.format("%8s", Integer.toBinaryString(a & b)).replace(' ', '0') + ")");
            System.out.println("  carry = (a & b) << 1 = " + carry + " (binary: " + String.format("%8s", Integer.toBinaryString(carry)).replace(' ', '0') + ")");
            System.out.println("  sum = a ^ b = " + sum + " (binary: " + String.format("%8s", Integer.toBinaryString(sum)).replace(' ', '0') + ")");
            
            a = sum;
            b = carry;
            
            System.out.println("  Next: a = " + a + ", b = " + b);
            System.out.println();
            step++;
        }
        
        System.out.println("Final result: " + a);
    }
    
    /**
     * Helper method to demonstrate bit manipulation concepts
     */
    private void demonstrateBitConcepts() {
        System.out.println("Bit Manipulation Concepts:");
        System.out.println("1. XOR (^) - Addition without carry:");
        System.out.println("   0 ^ 0 = 0, 0 ^ 1 = 1, 1 ^ 0 = 1, 1 ^ 1 = 0");
        System.out.println("   Example: 5 ^ 3 = " + (5 ^ 3) + " (binary: " + Integer.toBinaryString(5 ^ 3) + ")");
        System.out.println();
        
        System.out.println("2. AND (&) - Carry detection:");
        System.out.println("   0 & 0 = 0, 0 & 1 = 0, 1 & 0 = 0, 1 & 1 = 1");
        System.out.println("   Example: 5 & 3 = " + (5 & 3) + " (binary: " + Integer.toBinaryString(5 & 3) + ")");
        System.out.println("   Carry: (5 & 3) << 1 = " + ((5 & 3) << 1) + " (binary: " + Integer.toBinaryString((5 & 3) << 1) + ")");
        System.out.println();
        
        System.out.println("3. Two's complement for negation:");
        System.out.println("   -x = ~x + 1");
        System.out.println("   Example: -5 = ~5 + 1 = " + (~5) + " + 1 = " + (~5 + 1));
        System.out.println();
    }
    
    /**
     * Helper method to validate approaches
     */
    private boolean validateAddition(int a, int b) {
        int expected = a + b;  // Using built-in addition for validation
        int result1 = getSum(a, b);
        int result2 = getSumRecursive(a, b);
        
        return result1 == expected && result2 == expected;
    }
    
    /**
     * Test method to demonstrate all approaches
     */
    public static void main(String[] args) {
        SumOfTwoIntegers solution = new SumOfTwoIntegers();
        
        // Test case 1: a = 1, b = 1
        int a1 = 1, b1 = 1;
        System.out.println("Test Case 1: a = " + a1 + ", b = " + b1);
        System.out.println("Expected: 2");
        System.out.println("Iterative: " + solution.getSum(a1, b1));
        System.out.println("Recursive: " + solution.getSumRecursive(a1, b1));
        System.out.println("Validation: " + solution.validateAddition(a1, b1));
        System.out.println();
        
        // Test case 2: a = 4, b = 7
        int a2 = 4, b2 = 7;
        System.out.println("Test Case 2: a = " + a2 + ", b = " + b2);
        System.out.println("Expected: 11");
        System.out.println("Iterative: " + solution.getSum(a2, b2));
        System.out.println("Recursive: " + solution.getSumRecursive(a2, b2));
        System.out.println("Validation: " + solution.validateAddition(a2, b2));
        System.out.println();
        
        // Test case 3: Negative numbers
        int a3 = -1, b3 = 1;
        System.out.println("Test Case 3: a = " + a3 + ", b = " + b3);
        System.out.println("Expected: 0");
        System.out.println("Iterative: " + solution.getSum(a3, b3));
        System.out.println("Validation: " + solution.validateAddition(a3, b3));
        System.out.println();
        
        // Test case 4: Both negative
        int a4 = -3, b4 = -5;
        System.out.println("Test Case 4: a = " + a4 + ", b = " + b4);
        System.out.println("Expected: -8");
        System.out.println("Iterative: " + solution.getSum(a4, b4));
        System.out.println("Validation: " + solution.validateAddition(a4, b4));
        System.out.println();
        
        // Test case 5: Zero
        int a5 = 0, b5 = 5;
        System.out.println("Test Case 5: a = " + a5 + ", b = " + b5);
        System.out.println("Expected: 5");
        System.out.println("Iterative: " + solution.getSum(a5, b5));
        System.out.println();
        
        // Demonstrate step-by-step addition
        solution.demonstrateAddition(5, 3);
        
        // Test subtraction
        System.out.println("Subtraction Examples:");
        System.out.println("7 - 3 = " + solution.getSubtraction(7, 3) + " (expected: 4)");
        System.out.println("3 - 7 = " + solution.getSubtraction(3, 7) + " (expected: -4)");
        System.out.println("10 - (-3) = " + solution.getSubtraction(10, -3) + " (expected: 13)");
        System.out.println();
        
        // Test multiplication
        System.out.println("Multiplication Examples:");
        System.out.println("3 * 4 = " + solution.getMultiplication(3, 4) + " (expected: 12)");
        System.out.println("7 * 8 = " + solution.getMultiplication(7, 8) + " (expected: 56)");
        System.out.println("-3 * 4 = " + solution.getMultiplication(-3, 4) + " (expected: -12)");
        System.out.println();
        
        // Demonstrate bit manipulation concepts
        solution.demonstrateBitConcepts();
        
        // Performance comparison
        System.out.println("Performance test (1 million operations):");
        int testA = 123, testB = 456;
        int iterations = 1000000;
        
        long start, end;
        
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            solution.getSum(testA, testB);
        }
        end = System.nanoTime();
        System.out.println("Iterative: " + (end - start) / 1000000.0 + " ms");
        
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            solution.getSumRecursive(testA, testB);
        }
        end = System.nanoTime();
        System.out.println("Recursive: " + (end - start) / 1000000.0 + " ms");
    }
}
