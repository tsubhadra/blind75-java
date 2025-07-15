//https://neetcode.io/problems/house-robber?list=blind75
public class Solution {
    
    // Approach 1: Dynamic Programming (Bottom-up) - Clear and intuitive
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        if (nums.length == 1) {
            return nums[0];
        }
        
        int n = nums.length;
        int[] dp = new int[n];
        
        // Base cases
        dp[0] = nums[0];                    // Rob first house
        dp[1] = Math.max(nums[0], nums[1]); // Rob either first or second house
        
        // Fill dp array
        for (int i = 2; i < n; i++) {
            // Either rob current house (nums[i] + dp[i-2]) or skip it (dp[i-1])
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        
        return dp[n - 1];
    }
    
    // Approach 2: Space-optimized DP - O(1) space
    public int robOptimized(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        if (nums.length == 1) {
            return nums[0];
        }
        
        int prev2 = nums[0];                    // dp[i-2]
        int prev1 = Math.max(nums[0], nums[1]); // dp[i-1]
        
        for (int i = 2; i < nums.length; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    // Approach 3: Even more space-optimized - Two variables only
    public int robMostOptimized(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int robPrev = 0;    // Maximum money robbed up to previous house
        int notRobPrev = 0; // Maximum money robbed up to previous house without robbing it
        
        for (int num : nums) {
            int temp = robPrev;
            robPrev = notRobPrev + num;     // Rob current house
            notRobPrev = Math.max(temp, notRobPrev); // Don't rob current house
        }
        
        return Math.max(robPrev, notRobPrev);
    }
    
    // Approach 4: Recursive with Memoization - For educational purposes
    public int robRecursive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        Integer[] memo = new Integer[nums.length];
        return robHelper(nums, 0, memo);
    }
    
    private int robHelper(int[] nums, int index, Integer[] memo) {
        if (index >= nums.length) {
            return 0;
        }
        
        if (memo[index] != null) {
            return memo[index];
        }
        
        // Two choices: rob current house or skip it
        int robCurrent = nums[index] + robHelper(nums, index + 2, memo);
        int skipCurrent = robHelper(nums, index + 1, memo);
        
        memo[index] = Math.max(robCurrent, skipCurrent);
        return memo[index];
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        int[] nums1 = {1, 1, 3, 3};
        int[] nums2 = {2, 9, 8, 3, 6};
        int[] nums3 = {5, 1, 3, 9};
        int[] nums4 = {1, 2, 3, 1};
        
        System.out.println("Test Case 1: " + java.util.Arrays.toString(nums1));
        System.out.println("Result: " + solution.rob(nums1)); // Expected: 4
        
        System.out.println("\nTest Case 2: " + java.util.Arrays.toString(nums2));
        System.out.println("Result: " + solution.rob(nums2)); // Expected: 16
        
        System.out.println("\nTest Case 3: " + java.util.Arrays.toString(nums3));
        System.out.println("Result: " + solution.rob(nums3)); // Expected: 14
        
        System.out.println("\nTest Case 4: " + java.util.Arrays.toString(nums4));
        System.out.println("Result: " + solution.rob(nums4)); // Expected: 4
        
        // Test optimized versions
        System.out.println("\n--- Testing Optimized Versions ---");
        System.out.println("Optimized result for nums2: " + solution.robOptimized(nums2));
        System.out.println("Most optimized result for nums2: " + solution.robMostOptimized(nums2));
        System.out.println("Recursive result for nums2: " + solution.robRecursive(nums2));
    }
}
