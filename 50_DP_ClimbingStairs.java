//https://neetcode.io/problems/climbing-stairs?list=blind75
public class Solution {
    
    // Approach 1: Dynamic Programming (Bottom-up) - Most efficient
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        
        // dp[i] represents number of ways to reach step i
        int[] dp = new int[n + 1];
        dp[1] = 1;  // 1 way to reach step 1
        dp[2] = 2;  // 2 ways to reach step 2
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
    
    // Approach 2: Space-optimized DP - O(1) space
    public int climbStairsOptimized(int n) {
        if (n <= 2) {
            return n;
        }
        
        int prev2 = 1;  // ways to reach step 1
        int prev1 = 2;  // ways to reach step 2
        
        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    // Approach 3: Recursive with Memoization - For educational purposes
    public int climbStairsRecursive(int n) {
        return climbStairsHelper(n, new int[n + 1]);
    }
    
    private int climbStairsHelper(int n, int[] memo) {
        if (n <= 2) {
            return n;
        }
        
        if (memo[n] != 0) {
            return memo[n];
        }
        
        memo[n] = climbStairsHelper(n - 1, memo) + climbStairsHelper(n - 2, memo);
        return memo[n];
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        System.out.println("n = 2: " + solution.climbStairs(2)); // Expected: 2
        System.out.println("n = 3: " + solution.climbStairs(3)); // Expected: 3
        System.out.println("n = 4: " + solution.climbStairs(4)); // Expected: 5
        System.out.println("n = 5: " + solution.climbStairs(5)); // Expected: 8
        
        // Test optimized version
        System.out.println("\nOptimized version:");
        System.out.println("n = 2: " + solution.climbStairsOptimized(2)); // Expected: 2
        System.out.println("n = 3: " + solution.climbStairsOptimized(3)); // Expected: 3
        System.out.println("n = 4: " + solution.climbStairsOptimized(4)); // Expected: 5
        System.out.println("n = 5: " + solution.climbStairsOptimized(5)); // Expected: 8
    }
}
