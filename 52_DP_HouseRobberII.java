//https://neetcode.io/problems/house-robber-ii?list=blind75
public class Solution {
    
    // Main method: Handle circular constraint by considering two scenarios
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        if (nums.length == 1) {
            return nums[0];
        }
        
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        
        // Two scenarios due to circular constraint:
        // 1. Rob houses from 0 to n-2 (exclude last house)
        // 2. Rob houses from 1 to n-1 (exclude first house)
        int scenario1 = robLinear(nums, 0, nums.length - 2);
        int scenario2 = robLinear(nums, 1, nums.length - 1);
        
        return Math.max(scenario1, scenario2);
    }
    
    // Helper method: Rob houses in linear arrangement (from start to end index)
    private int robLinear(int[] nums, int start, int end) {
        if (start > end) {
            return 0;
        }
        
        if (start == end) {
            return nums[start];
        }
        
        int prev2 = nums[start];                           // dp[i-2]
        int prev1 = Math.max(nums[start], nums[start + 1]); // dp[i-1]
        
        for (int i = start + 2; i <= end; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    // Alternative implementation: More explicit with separate arrays
    public int robAlternative(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        if (nums.length == 1) {
            return nums[0];
        }
        
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        
        // Create two arrays for the two scenarios
        int[] scenario1 = new int[nums.length - 1]; // Exclude last house
        int[] scenario2 = new int[nums.length - 1]; // Exclude first house
        
        // Copy elements for scenario 1 (0 to n-2)
        for (int i = 0; i < nums.length - 1; i++) {
            scenario1[i] = nums[i];
        }
        
        // Copy elements for scenario 2 (1 to n-1)
        for (int i = 1; i < nums.length; i++) {
            scenario2[i - 1] = nums[i];
        }
        
        return Math.max(robSimple(scenario1), robSimple(scenario2));
    }
    
    // Simple linear house robber (from House Robber I)
    private int robSimple(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        
        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);
        
        for (int i = 2; i < nums.length; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    // Most optimized version: Single pass with two variables
    public int robMostOptimized(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return Math.max(nums[0], nums[1]);
        
        return Math.max(
            robRange(nums, 0, nums.length - 2),
            robRange(nums, 1, nums.length - 1)
        );
    }
    
    private int robRange(int[] nums, int start, int end) {
        int prev2 = 0, prev1 = 0;
        
        for (int i = start; i <= end; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        int[] nums1 = {3, 4, 3};
        int[] nums2 = {2, 9, 8, 3, 6};
        int[] nums3 = {1, 2, 3, 1};
        int[] nums4 = {1, 2, 3};
        int[] nums5 = {5};
        
        System.out.println("Test Case 1: " + java.util.Arrays.toString(nums1));
        System.out.println("Result: " + solution.rob(nums1)); // Expected: 4
        System.out.println("Explanation: Can only rob house 1 (value 4)");
        
        System.out.println("\nTest Case 2: " + java.util.Arrays.toString(nums2));
        System.out.println("Result: " + solution.rob(nums2)); // Expected: 15
        System.out.println("Explanation: Rob houses 1 and 4 (9 + 6 = 15)");
        
        System.out.println("\nTest Case 3: " + java.util.Arrays.toString(nums3));
        System.out.println("Result: " + solution.rob(nums3)); // Expected: 4
        System.out.println("Explanation: Rob houses 0 and 2 OR houses 1 and 3, both give 4");
        
        System.out.println("\nTest Case 4: " + java.util.Arrays.toString(nums4));
        System.out.println("Result: " + solution.rob(nums4)); // Expected: 3
        
        System.out.println("\nTest Case 5: " + java.util.Arrays.toString(nums5));
        System.out.println("Result: " + solution.rob(nums5)); // Expected: 5
        
        // Test alternative implementations
        System.out.println("\n--- Testing Alternative Implementations ---");
        System.out.println("Alternative result for nums2: " + solution.robAlternative(nums2));
        System.out.println("Most optimized result for nums2: " + solution.robMostOptimized(nums2));
    }
}
