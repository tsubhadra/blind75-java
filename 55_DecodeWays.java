//https://neetcode.io/problems/decode-ways?list=blind75
public class Solution {
    
    // Approach 1: Dynamic Programming (Bottom-up) - Most intuitive
    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }
        
        int n = s.length();
        int[] dp = new int[n + 1];
        
        // Base cases
        dp[0] = 1; // Empty string has 1 way to decode
        dp[1] = 1; // First character (already validated as non-zero)
        
        for (int i = 2; i <= n; i++) {
            // Check single digit (current character)
            int oneDigit = Integer.parseInt(s.substring(i - 1, i));
            if (oneDigit >= 1 && oneDigit <= 9) {
                dp[i] += dp[i - 1];
            }
            
            // Check two digits (previous and current characters)
            int twoDigits = Integer.parseInt(s.substring(i - 2, i));
            if (twoDigits >= 10 && twoDigits <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        
        return dp[n];
    }
    
    // Approach 2: Space-optimized DP - O(1) space
    public int numDecodingsOptimized(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }
        
        int prev2 = 1; // dp[i-2]
        int prev1 = 1; // dp[i-1]
        
        for (int i = 1; i < s.length(); i++) {
            int current = 0;
            
            // Check single digit
            int oneDigit = s.charAt(i) - '0';
            if (oneDigit >= 1 && oneDigit <= 9) {
                current += prev1;
            }
            
            // Check two digits
            int twoDigits = (s.charAt(i - 1) - '0') * 10 + (s.charAt(i) - '0');
            if (twoDigits >= 10 && twoDigits <= 26) {
                current += prev2;
            }
            
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    // Approach 3: Recursive with Memoization - Top-down
    public int numDecodingsRecursive(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        Integer[] memo = new Integer[s.length()];
        return decode(s, 0, memo);
    }
    
    private int decode(String s, int index, Integer[] memo) {
        // Base case: reached end of string
        if (index == s.length()) {
            return 1;
        }
        
        // Invalid: leading zero
        if (s.charAt(index) == '0') {
            return 0;
        }
        
        // Check memoization
        if (memo[index] != null) {
            return memo[index];
        }
        
        int ways = 0;
        
        // Take one digit
        ways += decode(s, index + 1, memo);
        
        // Take two digits (if valid)
        if (index + 1 < s.length()) {
            int twoDigits = Integer.parseInt(s.substring(index, index + 2));
            if (twoDigits <= 26) {
                ways += decode(s, index + 2, memo);
            }
        }
        
        memo[index] = ways;
        return ways;
    }
    
    // Approach 4: Clean DP with character operations
    public int numDecodingsClean(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }
        
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            char curr = s.charAt(i - 1);
            char prev = s.charAt(i - 2);
            
            // Single digit decode
            if (curr != '0') {
                dp[i] += dp[i - 1];
            }
            
            // Two digit decode
            if (prev == '1' || (prev == '2' && curr <= '6')) {
                dp[i] += dp[i - 2];
            }
        }
        
        return dp[n];
    }
    
    // Approach 5: Most space-optimized with clear logic
    public int numDecodingsMostOptimized(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }
        
        int twoBack = 1; // Ways to decode up to i-2
        int oneBack = 1; // Ways to decode up to i-1
        
        for (int i = 1; i < s.length(); i++) {
            int current = 0;
            
            // Can we decode current character alone?
            if (s.charAt(i) != '0') {
                current += oneBack;
            }
            
            // Can we decode current and previous character together?
            int twoDigitNum = (s.charAt(i - 1) - '0') * 10 + (s.charAt(i) - '0');
            if (twoDigitNum >= 10 && twoDigitNum <= 26) {
                current += twoBack;
            }
            
            twoBack = oneBack;
            oneBack = current;
        }
        
        return oneBack;
    }
    
    // Helper method to demonstrate the decoding process
    public void demonstrateDecoding(String s) {
        System.out.println("Demonstrating decoding for: \"" + s + "\"");
        
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            System.out.println("Invalid input - starts with 0 or empty");
            return;
        }
        
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        System.out.println("dp[0] = 1 (base case)");
        System.out.println("dp[1] = 1 (first character '" + s.charAt(0) + "')");
        
        for (int i = 2; i <= n; i++) {
            char curr = s.charAt(i - 1);
            char prev = s.charAt(i - 2);
            
            System.out.print("Position " + i + " (char '" + curr + "'): ");
            
            // Single digit
            if (curr != '0') {
                dp[i] += dp[i - 1];
                System.out.print("single='" + curr + "'(" + dp[i - 1] + ") ");
            }
            
            // Two digits
            if (prev == '1' || (prev == '2' && curr <= '6')) {
                dp[i] += dp[i - 2];
                System.out.print("double='" + prev + curr + "'(" + dp[i - 2] + ") ");
            }
            
            System.out.println("-> dp[" + i + "] = " + dp[i]);
        }
        
        System.out.println("Total ways: " + dp[n]);
        System.out.println();
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        String[] testCases = {
            "12",
            "226",
            "06",
            "01",
            "10",
            "27",
            "0",
            "1",
            "11",
            "111",
            "1234"
        };
        
        for (String test : testCases) {
            System.out.println("Input: \"" + test + "\"");
            System.out.println("Standard DP: " + solution.numDecodings(test));
            System.out.println("Optimized DP: " + solution.numDecodingsOptimized(test));
            System.out.println("Recursive: " + solution.numDecodingsRecursive(test));
            System.out.println("Clean DP: " + solution.numDecodingsClean(test));
            System.out.println("Most Optimized: " + solution.numDecodingsMostOptimized(test));
            
            // Show detailed breakdown for short strings
            if (test.length() <= 4 && !test.startsWith("0")) {
                solution.demonstrateDecoding(test);
            }
            
            System.out.println("---");
        }
    }
}
