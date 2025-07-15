//https://neetcode.io/problems/palindromic-substrings?list=blind75

public class Solution {
    
    // Approach 1: Expand Around Centers - Most efficient and intuitive
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int count = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // Count odd length palindromes (center at i)
            count += expandAroundCenter(s, i, i);
            
            // Count even length palindromes (center between i and i+1)
            count += expandAroundCenter(s, i, i + 1);
        }
        
        return count;
    }
    
    // Helper method to expand around center and count palindromes
    private int expandAroundCenter(String s, int left, int right) {
        int count = 0;
        
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;  // Found a palindrome
            left--;   // Expand to the left
            right++;  // Expand to the right
        }
        
        return count;
    }
    
    // Approach 2: Dynamic Programming - O(n^2) time and space
    public int countSubstringsDP(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int count = 0;
        
        // Every single character is a palindrome
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            count++;
        }
        
        // Check for palindromes of length 2
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                count++;
            }
        }
        
        // Check for palindromes of length 3 and more
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    count++;
                }
            }
        }
        
        return count;
    }
    
    // Approach 3: Brute Force - O(n^3) time - For educational purposes
    public int countSubstringsBruteForce(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int count = 0;
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (isPalindrome(s, i, j)) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    // Helper method to check if substring from i to j is palindrome
    private boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
    
    // Approach 4: Manacher's Algorithm - O(n) time (Advanced)
    public int countSubstringsManacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        // Transform string: "abc" -> "^#a#b#c#$"
        StringBuilder sb = new StringBuilder();
        sb.append("^");
        for (char c : s.toCharArray()) {
            sb.append("#").append(c);
        }
        sb.append("#$");
        
        String transformed = sb.toString();
        int n = transformed.length();
        int[] P = new int[n]; // P[i] = radius of palindrome centered at i
        int center = 0, right = 0;
        int count = 0;
        
        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;
            
            if (i < right) {
                P[i] = Math.min(right - i, P[mirror]);
            }
            
            // Try to expand palindrome centered at i
            while (transformed.charAt(i + P[i] + 1) == transformed.charAt(i - P[i] - 1)) {
                P[i]++;
            }
            
            // If palindrome centered at i extends past right, adjust center and right
            if (i + P[i] > right) {
                center = i;
                right = i + P[i];
            }
            
            // Count palindromes: each radius contributes (radius + 1) / 2 palindromes
            count += (P[i] + 1) / 2;
        }
        
        return count;
    }
    
    // Approach 5: Alternative expand around centers with detailed tracking
    public int countSubstringsDetailed(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int count = 0;
        java.util.List<String> palindromes = new java.util.ArrayList<>();
        
        for (int i = 0; i < s.length(); i++) {
            // Count odd length palindromes
            count += expandAndCollect(s, i, i, palindromes);
            
            // Count even length palindromes
            count += expandAndCollect(s, i, i + 1, palindromes);
        }
        
        // Print all palindromes found (for debugging/understanding)
        System.out.println("Palindromes found: " + palindromes);
        
        return count;
    }
    
    // Helper method that expands and collects palindromes
    private int expandAndCollect(String s, int left, int right, java.util.List<String> palindromes) {
        int count = 0;
        
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            palindromes.add(s.substring(left, right + 1));
            count++;
            left--;
            right++;
        }
        
        return count;
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        String[] testCases = {
            "abc",
            "aaa",
            "aba",
            "abccba",
            "racecar",
            "a",
            "ab"
        };
        
        for (String test : testCases) {
            System.out.println("Input: \"" + test + "\"");
            System.out.println("Expand Around Centers: " + solution.countSubstrings(test));
            System.out.println("Dynamic Programming: " + solution.countSubstringsDP(test));
            System.out.println("Brute Force: " + solution.countSubstringsBruteForce(test));
            System.out.println("Manacher's Algorithm: " + solution.countSubstringsManacher(test));
            
            // Show detailed breakdown for small strings
            if (test.length() <= 5) {
                System.out.print("Detailed (with palindromes): ");
                solution.countSubstringsDetailed(test);
            }
            
            System.out.println("---");
        }
    }
}
