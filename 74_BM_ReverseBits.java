//https://neetcode.io/problems/reverse-bits?list=blind75
public class ReverseBits {
    
    /**
     * Reverses bits of 32-bit unsigned integer - Approach 1: Bit by bit reversal
     * Time Complexity: O(32) = O(1) - processes exactly 32 bits
     * Space Complexity: O(1) - constant extra space
     * 
     * @param n the 32-bit unsigned integer
     * @return the integer with reversed bits
     */
    public int reverseBits(int n) {
        int result = 0;
        
        // Process all 32 bits
        for (int i = 0; i < 32; i++) {
            // Left shift result to make room for next bit
            result <<= 1;
            
            // Add the least significant bit of n to result
            result |= (n & 1);
            
            // Right shift n to process next bit
            n >>>= 1;  // Unsigned right shift
        }
        
        return result;
    }
    
    /**
     * Reverses bits - Approach 2: Optimized with bit manipulation
     * Time Complexity: O(1) - constant time operations
     * Space Complexity: O(1) - constant extra space
     * 
     * Uses divide and conquer approach to reverse bits in parallel
     * 
     * @param n the 32-bit unsigned integer
     * @return the integer with reversed bits
     */
    public int reverseBitsOptimal(int n) {
        // Swap adjacent bits
        n = ((n & 0xAAAAAAAA) >>> 1) | ((n & 0x55555555) << 1);
        
        // Swap adjacent pairs
        n = ((n & 0xCCCCCCCC) >>> 2) | ((n & 0x33333333) << 2);
        
        // Swap nibbles (4-bit groups)
        n = ((n & 0xF0F0F0F0) >>> 4) | ((n & 0x0F0F0F0F) << 4);
        
        // Swap bytes
        n = ((n & 0xFF00FF00) >>> 8) | ((n & 0x00FF00FF) << 8);
        
        // Swap 16-bit halves
        n = (n >>> 16) | (n << 16);
        
        return n;
    }
    
    /**
     * Reverses bits - Approach 3: Using StringBuilder
     * Time Complexity: O(32) = O(1) - processes 32 bits
     * Space Complexity: O(32) = O(1) - string for 32 bits
     * 
     * Converts to binary string, reverses, and converts back
     * 
     * @param n the 32-bit unsigned integer
     * @return the integer with reversed bits
     */
    public int reverseBitsString(int n) {
        // Convert to 32-bit binary string
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            binary.append((n >>> (31 - i)) & 1);
        }
        
        // Reverse the string
        binary.reverse();
        
        // Convert back to integer
        int result = 0;
        for (int i = 0; i < 32; i++) {
            if (binary.charAt(i) == '1') {
                result |= (1 << (31 - i));
            }
        }
        
        return result;
    }
    
    /**
     * Reverses bits - Approach 4: Lookup table (for frequent calls)
     * Time Complexity: O(1) - constant lookup time
     * Space Complexity: O(256) = O(1) - fixed size lookup table
     * 
     * Pre-computes reversal for 8-bit values, then combines for 32-bit
     * 
     * @param n the 32-bit unsigned integer
     * @return the integer with reversed bits
     */
    public int reverseBitsLookup(int n) {
        // Initialize lookup table for 8-bit reversal (done once)
        if (lookupTable == null) {
            initializeLookupTable();
        }
        
        // Extract each byte, reverse using lookup, and combine
        int result = 0;
        result |= lookupTable[(n >>> 24) & 0xFF];        // Most significant byte
        result |= lookupTable[(n >>> 16) & 0xFF] << 8;   // Second byte
        result |= lookupTable[(n >>> 8) & 0xFF] << 16;   // Third byte
        result |= lookupTable[n & 0xFF] << 24;           // Least significant byte
        
        return result;
    }
    
    // Lookup table for 8-bit reversal
    private static int[] lookupTable = null;
    
    /**
     * Initialize lookup table for 8-bit reversal
     */
    private void initializeLookupTable() {
        lookupTable = new int[256];
        for (int i = 0; i < 256; i++) {
            lookupTable[i] = reverseByte(i);
        }
    }
    
    /**
     * Reverse bits in a single byte
     */
    private int reverseByte(int b) {
        int result = 0;
        for (int i = 0; i < 8; i++) {
            result = (result << 1) | (b & 1);
            b >>>= 1;
        }
        return result;
    }
    
    /**
     * Helper method to convert integer to 32-bit binary string
     */
    private String toBinary32(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 31; i >= 0; i--) {
            sb.append((n >>> i) & 1);
        }
        return sb.toString();
    }
    
    /**
     * Helper method to demonstrate bit reversal step by step
     */
    private void demonstrateReversal(int n) {
        System.out.println("Demonstrating bit reversal for n = " + n);
        System.out.println("Original:  " + toBinary32(n));
        
        int result = 0;
        int temp = n;
        
        System.out.println("\nStep-by-step reversal:");
        for (int i = 0; i < 32; i++) {
            // Show current state
            int bit = temp & 1;
            result = (result << 1) | bit;
            temp >>>= 1;
            
            if (i < 8 || i >= 24) {  // Show first and last few steps
                System.out.printf("Step %2d: bit=%d, result=%s%n", 
                    i + 1, bit, toBinary32(result));
            } else if (i == 8) {
                System.out.println("   ...  (continuing for remaining bits)");
            }
        }
        
        System.out.println("Reversed:  " + toBinary32(result));
        System.out.println("Result:    " + result + " (unsigned: " + Integer.toUnsignedString(result) + ")");
    }
    
    /**
     * Helper method to demonstrate optimal approach masks
     */
    private void demonstrateOptimalApproach(int n) {
        System.out.println("Demonstrating optimal approach for n = " + n);
        System.out.println("Original: " + toBinary32(n));
        
        // Step 1: Swap adjacent bits
        int step1 = ((n & 0xAAAAAAAA) >>> 1) | ((n & 0x55555555) << 1);
        System.out.println("Step 1 (swap adjacent bits): " + toBinary32(step1));
        
        // Step 2: Swap adjacent pairs
        int step2 = ((step1 & 0xCCCCCCCC) >>> 2) | ((step1 & 0x33333333) << 2);
        System.out.println("Step 2 (swap 2-bit pairs):   " + toBinary32(step2));
        
        // Step 3: Swap nibbles
        int step3 = ((step2 & 0xF0F0F0F0) >>> 4) | ((step2 & 0x0F0F0F0F) << 4);
        System.out.println("Step 3 (swap nibbles):       " + toBinary32(step3));
        
        // Step 4: Swap bytes
        int step4 = ((step3 & 0xFF00FF00) >>> 8) | ((step3 & 0x00FF00FF) << 8);
        System.out.println("Step 4 (swap bytes):         " + toBinary32(step4));
        
        // Step 5: Swap 16-bit halves
        int result = (step4 >>> 16) | (step4 << 16);
        System.out.println("Step 5 (swap halves):        " + toBinary32(result));
        
        System.out.println("Final result: " + result);
    }
    
    /**
     * Helper method to validate all approaches give same result
     */
    private boolean validateApproaches(int n) {
        int result1 = reverseBits(n);
        int result2 = reverseBitsOptimal(n);
        int result3 = reverseBitsString(n);
        int result4 = reverseBitsLookup(n);
        
        return result1 == result2 && result2 == result3 && result3 == result4;
    }
    
    /**
     * Test method to demonstrate all approaches
     */
    public static void main(String[] args) {
        ReverseBits solution = new ReverseBits();
        
        // Test case 1: n = 21 (00000000000000000000000000010101)
        int n1 = 21;
        System.out.println("Test Case 1: n = " + n1);
        System.out.println("Binary: " + solution.toBinary32(n1));
        System.out.println("Bit-by-bit: " + solution.reverseBits(n1));
        System.out.println("Optimal: " + solution.reverseBitsOptimal(n1));
        System.out.println("String method: " + solution.reverseBitsString(n1));
        System.out.println("Lookup table: " + solution.reverseBitsLookup(n1));
        System.out.println("Expected: 2818572288");
        System.out.println("All approaches match: " + solution.validateApproaches(n1));
        System.out.println();
        
        // Test case 2: n = -1 (all 1s)
        int n2 = -1;
        System.out.println("Test Case 2: n = " + n2 + " (all 1s)");
        System.out.println("Binary: " + solution.toBinary32(n2));
        System.out.println("Reversed: " + solution.reverseBits(n2));
        System.out.println("All approaches match: " + solution.validateApproaches(n2));
        System.out.println();
        
        // Test case 3: n = 0
        int n3 = 0;
        System.out.println("Test Case 3: n = " + n3);
        System.out.println("Reversed: " + solution.reverseBits(n3));
        System.out.println();
        
        // Test case 4: Power of 2
        int n4 = 1024; // 2^10
        System.out.println("Test Case 4: n = " + n4 + " (2^10)");
        System.out.println("Binary: " + solution.toBinary32(n4));
        System.out.println("Reversed: " + solution.reverseBits(n4));
        System.out.println("Reversed binary: " + solution.toBinary32(solution.reverseBits(n4)));
        System.out.println();
        
        // Demonstrate step-by-step for small number
        solution.demonstrateReversal(5);
        System.out.println();
        
        // Demonstrate optimal approach
        solution.demonstrateOptimalApproach(21);
        System.out.println();
        
        // Performance comparison
        System.out.println("Performance test (1 million operations):");
        int testValue = 12345678;
        int iterations = 1000000;
        
        long start, end;
        
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            solution.reverseBits(testValue);
        }
        end = System.nanoTime();
        System.out.println("Bit-by-bit: " + (end - start) / 1000000.0 + " ms");
        
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            solution.reverseBitsOptimal(testValue);
        }
        end = System.nanoTime();
        System.out.println("Optimal: " + (end - start) / 1000000.0 + " ms");
        
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            solution.reverseBitsLookup(testValue);
        }
        end = System.nanoTime();
        System.out.println("Lookup table: " + (end - start) / 1000000.0 + " ms");
    }
}
