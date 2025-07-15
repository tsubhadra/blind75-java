//https://neetcode.io/problems/maximum-subarray?list=blind75


public class Solution {
    
    // Approach 1: Kadane's Algorithm - Most efficient and elegant
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int maxSum = nums[0];        // Maximum sum found so far
        int currentSum = nums[0];    // Maximum sum ending at current position
        
        for (int i = 1; i < nums.length; i++) {
            // Either extend current subarray or start new one
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            
            // Update global maximum
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    // Approach 2: Dynamic Programming - More explicit state tracking
    public int maxSubArrayDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        // dp[i] represents maximum sum of subarray ending at index i
        int[] dp = new int[n];
        
        dp[0] = nums[0];
        int maxSum = dp[0];
        
        for (int i = 1; i < n; i++) {
            // Either include current element in previous subarray or start new
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            maxSum = Math.max(maxSum, dp[i]);
        }
        
        return maxSum;
    }
    
    // Approach 3: Divide and Conquer - O(n log n) time
    public int maxSubArrayDivideConquer(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        return maxSubArrayHelper(nums, 0, nums.length - 1);
    }
    
    private int maxSubArrayHelper(int[] nums, int left, int right) {
        // Base case: single element
        if (left == right) {
            return nums[left];
        }
        
        int mid = left + (right - left) / 2;
        
        // Maximum subarray is either:
        // 1. Entirely in left half
        int leftMax = maxSubArrayHelper(nums, left, mid);
        
        // 2. Entirely in right half
        int rightMax = maxSubArrayHelper(nums, mid + 1, right);
        
        // 3. Crosses the middle point
        int crossMax = maxCrossingSubarray(nums, left, mid, right);
        
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }
    
    private int maxCrossingSubarray(int[] nums, int left, int mid, int right) {
        // Find maximum sum for left half (must include mid)
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }
        
        // Find maximum sum for right half (must include mid+1)
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }
        
        return leftSum + rightSum;
    }
    
    // Approach 4: Brute Force - O(n^2) - For comparison
    public int maxSubArrayBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int maxSum = Integer.MIN_VALUE;
        
        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        
        return maxSum;
    }
    
    // Method to find the actual subarray (indices) with maximum sum
    public int[] findMaxSubarray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }
        
        int maxSum = nums[0];
        int currentSum = nums[0];
        int start = 0, end = 0, tempStart = 0;
        
        for (int i = 1; i < nums.length; i++) {
            if (currentSum < 0) {
                // Start new subarray
                currentSum = nums[i];
                tempStart = i;
            } else {
                // Extend current subarray
                currentSum += nums[i];
            }
            
            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }
        
        // Return the actual subarray
        int[] result = new int[end - start + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = nums[start + i];
        }
        
        return result;
    }
    
    // Method to find subarray indices with maximum sum
    public int[] findMaxSubarrayIndices(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        
        int maxSum = nums[0];
        int currentSum = nums[0];
        int start = 0, end = 0, tempStart = 0;
        
        for (int i = 1; i < nums.length; i++) {
            if (currentSum < 0) {
                currentSum = nums[i];
                tempStart = i;
            } else {
                currentSum += nums[i];
            }
            
            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }
        
        return new int[]{start, end};
    }
    
    // Helper method to demonstrate Kadane's algorithm step by step
    public void demonstrateKadane(int[] nums) {
        System.out.println("\nDemonstrating Kadane's Algorithm for: " + 
                          java.util.Arrays.toString(nums));
        
        if (nums == null || nums.length == 0) {
            System.out.println("Empty array");
            return;
        }
        
        int maxSum = nums[0];
        int currentSum = nums[0];
        
        System.out.println("Initial: maxSum = " + maxSum + 
                          ", currentSum = " + currentSum);
        
        for (int i = 1; i < nums.length; i++) {
            int prevCurrentSum = currentSum;
            
            // Decision: extend current subarray or start new one
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            
            String decision = (nums[i] > currentSum - nums[i]) ? 
                             "start new" : "extend current";
            
            maxSum = Math.max(maxSum, currentSum);
            
            System.out.printf("Step %d: nums[%d] = %d, decision = %s, " +
                            "currentSum = %d, maxSum = %d%n", 
                            i, i, nums[i], decision, currentSum, maxSum);
        }
        
        System.out.println("Final maximum subarray sum: " + maxSum);
        
        // Show the actual subarray
        int[] subarray = findMaxSubarray(nums);
        int[] indices = findMaxSubarrayIndices(nums);
        System.out.println("Maximum subarray: " + 
                          java.util.Arrays.toString(subarray));
        System.out.println("Indices: [" + indices[0] + ", " + indices[1] + "]");
    }
    
    // Method to demonstrate divide and conquer approach
    public void demonstrateDivideConquer(int[] nums) {
        System.out.println("\nDemonstrating Divide and Conquer for: " + 
                          java.util.Arrays.toString(nums));
        
        if (nums == null || nums.length == 0) {
            System.out.println("Empty array");
            return;
        }
        
        int result = maxSubArrayDivideConquer(nums);
        System.out.println("Maximum subarray sum: " + result);
    }
    
    // Method to compare performance of different approaches
    public void compareApproaches(int[] nums) {
        System.out.println("\nComparing approaches for array of length " + 
                          nums.length + ":");
        
        long start, end;
        
        // Kadane's Algorithm
        start = System.nanoTime();
        int result1 = maxSubArray(nums);
        end = System.nanoTime();
        System.out.println("Kadane's Algorithm: " + result1 + 
                          " (Time: " + (end - start) + " ns)");
        
        // Dynamic Programming
        start = System.nanoTime();
        int result2 = maxSubArrayDP(nums);
        end = System.nanoTime();
        System.out.println("Dynamic Programming: " + result2 + 
                          " (Time: " + (end - start) + " ns)");
        
        // Divide and Conquer
        start = System.nanoTime();
        int result3 = maxSubArrayDivideConquer(nums);
        end = System.nanoTime();
        System.out.println("Divide and Conquer: " + result3 + 
                          " (Time: " + (end - start) + " ns)");
        
        // Brute Force (only for smaller arrays)
        if (nums.length <= 1000) {
            start = System.nanoTime();
            int result4 = maxSubArrayBruteForce(nums);
            end = System.nanoTime();
            System.out.println("Brute Force: " + result4 + 
                              " (Time: " + (end - start) + " ns)");
        }
    }
    
    // Method to handle edge cases
    public void testEdgeCases() {
        System.out.println("\nTesting Edge Cases:");
        
        // All negative numbers
        int[] allNegative = {-5, -2, -8, -1};
        System.out.println("All negative " + 
                          java.util.Arrays.toString(allNegative) + 
                          ": " + maxSubArray(allNegative));
        
        // All positive numbers
        int[] allPositive = {1, 2, 3, 4, 5};
        System.out.println("All positive " + 
                          java.util.Arrays.toString(allPositive) + 
                          ": " + maxSubArray(allPositive));
        
        // Single element
        int[] singleElement = {-3};
        System.out.println("Single element " + 
                          java.util.Arrays.toString(singleElement) + 
                          ": " + maxSubArray(singleElement));
        
        // Mixed with zeros
        int[] withZeros = {-1, 0, -2, 0, 3};
        System.out.println("With zeros " + 
                          java.util.Arrays.toString(withZeros) + 
                          ": " + maxSubArray(withZeros));
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        int[][] testCases = {
            {2, -3, 4, -2, 2, 1, -1, 4},
            {-1},
            {-2, -3, -1, -5},
            {1, 2, 3, 4, 5},
            {-2, 1, -3, 4, -1, 2, 1, -5, 4},
            {5, 4, -1, 7, 8},
            {-1, -2, -3, -4},
            {1},
            {0, -1, 2, -1, 3},
            {-2, -1}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            
            System.out.println("\nTest Case " + (i + 1) + ":");
            System.out.println("Input: " + java.util.Arrays.toString(nums));
            
            int result1 = solution.maxSubArray(nums);
            int result2 = solution.maxSubArrayDP(nums);
            int result3 = solution.maxSubArrayDivideConquer(nums);
            int result4 = solution.maxSubArrayBruteForce(nums);
            
            System.out.println("Kadane's Algorithm: " + result1);
            System.out.println("Dynamic Programming: " + result2);
            System.out.println("Divide and Conquer: " + result3);
            System.out.println("Brute Force: " + result4);
            
            // Find actual subarray
            int[] maxSubarray = solution.findMaxSubarray(nums);
            int[] indices = solution.findMaxSubarrayIndices(nums);
            System.out.println("Maximum subarray: " + 
                              java.util.Arrays.toString(maxSubarray));
            System.out.println("Indices: [" + indices[0] + ", " + 
                              indices[1] + "]");
            
            // Demonstrate algorithm for smaller arrays
            if (nums.length <= 8) {
                solution.demonstrateKadane(nums);
            }
            
            // Performance comparison for larger arrays
            if (nums.length >= 5) {
                solution.compareApproaches(nums);
            }
            
            System.out.println("---");
        }
        
        // Test edge cases
        solution.testEdgeCases();
    }
}
