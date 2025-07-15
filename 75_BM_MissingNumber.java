//https://neetcode.io/problems/missing-number?list=blind75
import java.util.*;

public class MissingNumber {
    
    /**
     * Finds missing number - Approach 1: Mathematical Sum Formula
     * Time Complexity: O(n) - single pass through array
     * Space Complexity: O(1) - constant extra space
     * 
     * Key insight: Sum of [0,n] = n*(n+1)/2, subtract actual sum to find missing
     * 
     * @param nums array containing n integers in range [0,n] with one missing
     * @return the missing number
     */
    public int missingNumber(int[] nums) {
        int n = nums.length;
        
        // Calculate expected sum: 0 + 1 + 2 + ... + n = n*(n+1)/2
        int expectedSum = n * (n + 1) / 2;
        
        // Calculate actual sum of array elements
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        
        // Missing number = expected sum - actual sum
        return expectedSum - actualSum;
    }
    
    /**
     * Finds missing number - Approach 2: XOR Bit Manipulation
     * Time Complexity: O(n) - single pass through array
     * Space Complexity: O(1) - constant extra space
     * 
     * Key insight: XOR of identical numbers is 0, XOR with 0 returns the number
     * XOR all numbers [0,n] with all array elements, duplicate pairs cancel out
     * 
     * @param nums array containing n integers in range [0,n] with one missing
     * @return the missing number
     */
    public int missingNumberXOR(int[] nums) {
        int n = nums.length;
        int result = n; // Start with n (the largest number in complete range)
        
        // XOR all indices with corresponding array values
        for (int i = 0; i < n; i++) {
            result ^= i ^ nums[i];
        }
        
        return result;
    }
    
    /**
     * Finds missing number - Approach 3: HashSet Lookup
     * Time Complexity: O(n) - single pass to build set + single pass to find missing
     * Space Complexity: O(n) - space for hashset
     * 
     * @param nums array containing n integers in range [0,n] with one missing
     * @return the missing number
     */
    public int missingNumberHashSet(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        
        // Add all numbers to set
        for (int num : nums) {
            numSet.add(num);
        }
        
        // Check which number from [0, n] is missing
        for (int i = 0; i <= nums.length; i++) {
            if (!numSet.contains(i)) {
                return i;
            }
        }
        
        return -1; // Should never reach here with valid input
    }
    
    /**
     * Finds missing number - Approach 4: Sorting
     * Time Complexity: O(n log n) - due to sorting
     * Space Complexity: O(1) - if sorting in-place, O(n) if using extra space
     * 
     * @param nums array containing n integers in range [0,n] with one missing
     * @return the missing number
     */
    public int missingNumberSorting(int[] nums) {
        Arrays.sort(nums);
        
        // Check if 0 is missing
        if (nums[0] != 0) {
            return 0;
        }
        
        // Check if n is missing
        if (nums[nums.length - 1] != nums.length) {
            return nums.length;
        }
        
        // Check for gap in sorted array
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1] + 1) {
                return nums[i - 1] + 1;
            }
        }
        
        return -1; // Should never reach here with valid input
    }
    
    /**
     * Finds missing number - Approach 5: Cyclic Sort (In-place)
     * Time Complexity: O(n) - each element is moved at most once
     * Space Complexity: O(1) - modifies input array in-place
     * 
     * Places each number at its correct index, then finds the missing position
     * 
     * @param nums array containing n integers in range [0,n] with one missing
     * @return the missing number
     */
    public int missingNumberCyclicSort(int[] nums) {
        int n = nums.length;
        
        // Place each number at its correct position
        for (int i = 0; i < n; i++) {
            // Keep swapping until current position has correct number
            while (nums[i] < n && nums[i] != i) {
                // Swap nums[i] with nums[nums[i]]
                int temp = nums[nums[i]];
                nums[nums[i]] = nums[i];
                nums[i] = temp;
            }
        }
        
        // Find the first position where number doesn't match index
        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        
        // If all positions [0, n-1] are correct, then n is missing
        return n;
    }
    
    /**
     * Helper method to demonstrate XOR approach step by step
     */
    private void demonstrateXOR(int[] nums) {
        System.out.println("Demonstrating XOR approach for: " + Arrays.toString(nums));
        int n = nums.length;
        int result = n;
        
        System.out.println("Initial result = " + n + " (binary: " + Integer.toBinaryString(n) + ")");
        
        for (int i = 0; i < n; i++) {
            System.out.println("Step " + (i + 1) + ":");
            System.out.println("  XOR with index " + i + " (binary: " + Integer.toBinaryString(i) + ")");
            result ^= i;
            System.out.println("  Result after XOR with " + i + ": " + result + " (binary: " + Integer.toBinaryString(result) + ")");
            
            System.out.println("  XOR with nums[" + i + "] = " + nums[i] + " (binary: " + Integer.toBinaryString(nums[i]) + ")");
            result ^= nums[i];
            System.out.println("  Result after XOR with " + nums[i] + ": " + result + " (binary: " + Integer.toBinaryString(result) + ")");
            System.out.println();
        }
        
        System.out.println("Final missing number: " + result);
    }
    
    /**
     * Helper method to validate all approaches give same result
     */
    private boolean validateApproaches(int[] nums) {
        // Create copies for methods that modify the array
        int[] copy1 = nums.clone();
        int[] copy2 = nums.clone();
        
        int result1 = missingNumber(nums);
        int result2 = missingNumberXOR(nums);
        int result3 = missingNumberHashSet(nums);
        int result4 = missingNumberSorting(copy1);
        int result5 = missingNumberCyclicSort(copy2);
        
        return result1 == result2 && result2 == result3 && 
               result3 == result4 && result4 == result5;
    }
    
    /**
     * Test method to demonstrate all approaches
     */
    public static void main(String[] args) {
        MissingNumber solution = new MissingNumber();
        
        // Test case 1: nums = [1,2,3], missing = 0
        int[] nums1 = {1, 2, 3};
        System.out.println("Test Case 1: " + Arrays.toString(nums1));
        System.out.println("Expected: 0");
        System.out.println("Sum Formula: " + solution.missingNumber(nums1));
        System.out.println("XOR Method: " + solution.missingNumberXOR(nums1));
        System.out.println("HashSet: " + solution.missingNumberHashSet(nums1));
        System.out.println("Sorting: " + solution.missingNumberSorting(nums1.clone()));
        System.out.println("Cyclic Sort: " + solution.missingNumberCyclicSort(nums1.clone()));
        System.out.println("All approaches match: " + solution.validateApproaches(nums1));
        System.out.println();
        
        // Test case 2: nums = [0,2], missing = 1
        int[] nums2 = {0, 2};
        System.out.println("Test Case 2: " + Arrays.toString(nums2));
        System.out.println("Expected: 1");
        System.out.println("Sum Formula: " + solution.missingNumber(nums2));
        System.out.println("XOR Method: " + solution.missingNumberXOR(nums2));
        System.out.println("All approaches match: " + solution.validateApproaches(nums2));
        System.out.println();
        
        // Test case 3: nums = [0,1,2,4,5], missing = 3
        int[] nums3 = {0, 1, 2, 4, 5};
        System.out.println("Test Case 3: " + Arrays.toString(nums3));
        System.out.println("Expected: 3");
        System.out.println("Sum Formula: " + solution.missingNumber(nums3));
        System.out.println("XOR Method: " + solution.missingNumberXOR(nums3));
        System.out.println("All approaches match: " + solution.validateApproaches(nums3));
        System.out.println();
        
        // Test case 4: Edge case - single element array
        int[] nums4 = {1};
        System.out.println("Test Case 4 (Edge): " + Arrays.toString(nums4));
        System.out.println("Expected: 0");
        System.out.println("Sum Formula: " + solution.missingNumber(nums4));
        System.out.println("XOR Method: " + solution.missingNumberXOR(nums4));
        System.out.println();
        
        // Test case 5: Missing largest number
        int[] nums5 = {0, 1, 2, 3};
        System.out.println("Test Case 5: " + Arrays.toString(nums5));
        System.out.println("Expected: 4");
        System.out.println("Sum Formula: " + solution.missingNumber(nums5));
        System.out.println("XOR Method: " + solution.missingNumberXOR(nums5));
        System.out.println();
        
        // Demonstrate XOR approach
        solution.demonstrateXOR(new int[]{0, 2});
        
        // Performance comparison
        System.out.println("\nPerformance test for large array (n = 1000):");
        int[] largeArray = new int[999];
        for (int i = 0; i < 999; i++) {
            largeArray[i] = i + 1; // Missing 0
        }
        
        long start, end;
        
        start = System.nanoTime();
        solution.missingNumber(largeArray);
        end = System.nanoTime();
        System.out.println("Sum Formula: " + (end - start) / 1000.0 + " μs");
        
        start = System.nanoTime();
        solution.missingNumberXOR(largeArray);
        end = System.nanoTime();
        System.out.println("XOR Method: " + (end - start) / 1000.0 + " μs");
        
        start = System.nanoTime();
        solution.missingNumberHashSet(largeArray);
        end = System.nanoTime();
        System.out.println("HashSet: " + (end - start) / 1000.0 + " μs");
    }
}
