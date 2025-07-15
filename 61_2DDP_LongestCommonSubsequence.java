//https://neetcode.io/problems/longest-common-subsequence?list=blind75
 public class Solution {
    
    // Approach 1: 2D Dynamic Programming - Most intuitive
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        // dp[i][j] represents LCS length for text1[0...i-1] and text2[0...j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        // Base case: empty string with any string has LCS = 0
        // This is already handled by default array initialization
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    // Characters match: include this character in LCS
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // Characters don't match: take maximum of excluding either character
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }\n    \n    // Approach 2: Space-optimized DP - O(min(m,n)) space
    public int longestCommonSubsequenceOptimized(String text1, String text2) {
        // Ensure text1 is the shorter string for space optimization
        if (text1.length() > text2.length()) {
            return longestCommonSubsequenceOptimized(text2, text1);
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // Only need current and previous row
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];
        
        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[i] = prev[i - 1] + 1;
                } else {
                    curr[i] = Math.max(prev[i], curr[i - 1]);
                }
            }
            // Swap arrays for next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        
        return prev[m];
    }\n    \n    // Approach 3: Further space optimization - O(min(m,n)) with single array
    public int longestCommonSubsequenceSingleArray(String text1, String text2) {
        if (text1.length() > text2.length()) {
            return longestCommonSubsequenceSingleArray(text2, text1);
        }
        
        int m = text1.length();
        int n = text2.length();
        
        int[] dp = new int[m + 1];
        
        for (int j = 1; j <= n; j++) {
            int prev = 0; // dp[i-1][j-1] for current iteration
            
            for (int i = 1; i <= m; i++) {
                int temp = dp[i]; // Store dp[i][j-1] before updating
                
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i] = prev + 1;
                } else {
                    dp[i] = Math.max(dp[i], dp[i - 1]);
                }
                
                prev = temp; // Update prev for next iteration
            }
        }
        
        return dp[m];
    }\n    \n    // Approach 4: Recursive with Memoization\n    public int longestCommonSubsequenceRecursive(String text1, String text2) {\n        int[][] memo = new int[text1.length()][text2.length()];\n        \n        // Initialize memo with -1 (uncomputed)\n        for (int[] row : memo) {\n            java.util.Arrays.fill(row, -1);\n        }\n        \n        return lcsRecursive(text1, text2, 0, 0, memo);\n    }\n    \n    private int lcsRecursive(String text1, String text2, int i, int j, 
                            int[][] memo) {
        // Base case: reached end of either string
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }
        
        // Check memoization
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        
        int result;
        if (text1.charAt(i) == text2.charAt(j)) {
            // Characters match: include and move both pointers
            result = 1 + lcsRecursive(text1, text2, i + 1, j + 1, memo);
        } else {
            // Characters don't match: try both options
            int option1 = lcsRecursive(text1, text2, i + 1, j, memo);     // Skip char in text1
            int option2 = lcsRecursive(text1, text2, i, j + 1, memo);     // Skip char in text2
            result = Math.max(option1, option2);
        }
        
        memo[i][j] = result;
        return result;
    }\n    \n    // Method to find the actual LCS string (not just length)
    public String findLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        
        // Build the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Reconstruct the LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                // Characters match: this character is part of LCS
                lcs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Move up (exclude character from text1)
                i--;
            } else {
                // Move left (exclude character from text2)
                j--;
            }
        }
        
        // Reverse the string since we built it backwards
        return lcs.reverse().toString();
    }\n    \n    // Method to find all possible LCS\n    public java.util.Set<String> findAllLCS(String text1, String text2) {\n        int m = text1.length();\n        int n = text2.length();\n        \n        int[][] dp = new int[m + 1][n + 1];\n        \n        // Build the DP table\n        for (int i = 1; i <= m; i++) {\n            for (int j = 1; j <= n; j++) {\n                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {\n                    dp[i][j] = dp[i - 1][j - 1] + 1;\n                } else {\n                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);\n                }\n            }\n        }\n        \n        java.util.Set<String> result = new java.util.HashSet<>();\n        findAllLCSHelper(text1, text2, m, n, dp, \"\", result);\n        return result;\n    }\n    \n    private void findAllLCSHelper(String text1, String text2, int i, int j, 
                                 int[][] dp, String current, 
                                 java.util.Set<String> result) {
        if (i == 0 || j == 0) {
            result.add(new StringBuilder(current).reverse().toString());
            return;
        }
        
        if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
            findAllLCSHelper(text1, text2, i - 1, j - 1, dp, 
                           current + text1.charAt(i - 1), result);
        } else {
            if (dp[i - 1][j] == dp[i][j]) {
                findAllLCSHelper(text1, text2, i - 1, j, dp, current, result);
            }
            if (dp[i][j - 1] == dp[i][j]) {
                findAllLCSHelper(text1, text2, i, j - 1, dp, current, result);
            }
        }
    }\n    \n    // Helper method to demonstrate the DP process
    public void demonstrateLCS(String text1, String text2) {
        System.out.println("\nDemonstrating LCS for \"" + text1 + 
                          "\" and \"" + text2 + "\":");
        
        int m = text1.length();
        int n = text2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        
        System.out.println("\nDP Table construction:");
        System.out.print("    ");
        for (int j = 0; j <= n; j++) {
            if (j == 0) System.out.print("  ε ");
            else System.out.print("  " + text2.charAt(j - 1) + " ");
        }
        System.out.println();
        
        for (int i = 0; i <= m; i++) {
            if (i == 0) System.out.print(" ε ");
            else System.out.print(" " + text1.charAt(i - 1) + " ");
            
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
                System.out.printf("%3d ", dp[i][j]);
            }
            System.out.println();
        }
        
        System.out.println("\nLCS Length: " + dp[m][n]);
        System.out.println("One LCS: \"" + findLCS(text1, text2) + "\"");
        
        if (text1.length() <= 5 && text2.length() <= 5) {
            java.util.Set<String> allLCS = findAllLCS(text1, text2);
            System.out.println("All possible LCS: " + allLCS);
        }
    }\n    \n    // Method to compare different approaches
    public void compareApproaches(String text1, String text2) {
        System.out.println("\nComparing approaches for \"" + text1 + 
                          "\" and \"" + text2 + "\":");
        
        long start, end;
        
        start = System.nanoTime();
        int result1 = longestCommonSubsequence(text1, text2);
        end = System.nanoTime();
        System.out.println("2D DP: " + result1 + " (Time: " + (end - start) + " ns)");
        
        start = System.nanoTime();
        int result2 = longestCommonSubsequenceOptimized(text1, text2);
        end = System.nanoTime();
        System.out.println("Space Optimized: " + result2 + " (Time: " + 
                          (end - start) + " ns)");
        
        start = System.nanoTime();
        int result3 = longestCommonSubsequenceSingleArray(text1, text2);
        end = System.nanoTime();
        System.out.println("Single Array: " + result3 + " (Time: " + 
                          (end - start) + " ns)");
        
        start = System.nanoTime();
        int result4 = longestCommonSubsequenceRecursive(text1, text2);
        end = System.nanoTime();
        System.out.println("Recursive: " + result4 + " (Time: " + 
                          (end - start) + " ns)");
    }\n    \n    // Test method\n    public static void main(String[] args) {\n        Solution solution = new Solution();\n        \n        // Test cases\n        String[][] testCases = {\n            {\"cat\", \"crabt\"},\n            {\"abcd\", \"abcd\"},\n            {\"abcd\", \"efgh\"},\n            {\"abc\", \"abc\"},\n            {\"abc\", \"def\"},\n            {\"ABCDGH\", \"AEDFHR\"},\n            {\"AGGTAB\", \"GXTXAYB\"},\n            {\"programming\", \"contest\"},\n            {\"\", \"abc\"},\n            {\"a\", \"a\"}\n        };\n        \n        for (int i = 0; i < testCases.length; i++) {\n            String text1 = testCases[i][0];\n            String text2 = testCases[i][1];\n            \n            System.out.println(\"\\nTest Case \" + (i + 1) + \":\");\n            System.out.println(\"Text1: \\\"\" + text1 + \"\\\"\");\n            System.out.println(\"Text2: \\\"\" + text2 + \"\\\"\");\n            \n            int result1 = solution.longestCommonSubsequence(text1, text2);\n            int result2 = solution.longestCommonSubsequenceOptimized(text1, text2);\n            int result3 = solution.longestCommonSubsequenceSingleArray(text1, text2);\n            int result4 = solution.longestCommonSubsequenceRecursive(text1, text2);\n            \n            System.out.println(\"2D DP: \" + result1);\n            System.out.println(\"Space Optimized: \" + result2);\n            System.out.println(\"Single Array: \" + result3);\n            System.out.println(\"Recursive: \" + result4);\n            \n            if (!text1.isEmpty() && !text2.isEmpty()) {\n                String lcs = solution.findLCS(text1, text2);\n                System.out.println(\"Actual LCS: \\\"\" + lcs + \"\\\"\");\n            }\n            \n            // Demonstrate process for smaller strings\n            if (text1.length() <= 6 && text2.length() <= 6) {\n                solution.demonstrateLCS(text1, text2);\n            }\n            \n            // Performance comparison for longer strings\n            if (text1.length() >= 8 || text2.length() >= 8) {\n                solution.compareApproaches(text1, text2);\n            }\n            \n            System.out.println(\"---\");\n        }\n    }\n}
