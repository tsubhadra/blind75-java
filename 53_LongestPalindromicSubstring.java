//https://neetcode.io/problems/longest-palindromic-substring?list=blind75

public class Solution {
    
    // Approach 1: Expand Around Centers - Most intuitive and efficient
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        int start = 0;
        int maxLen = 1;
        
        for (int i = 0; i < s.length(); i++) {
            // Check for odd length palindromes (center is at i)
            int len1 = expandAroundCenter(s, i, i);
            
            // Check for even length palindromes (center is between i and i+1)
            int len2 = expandAroundCenter(s, i, i + 1);
            
            int currentMaxLen = Math.max(len1, len2);
            
            if (currentMaxLen > maxLen) {
                maxLen = currentMaxLen;
                // Calculate the start position of the palindrome
                start = i - (currentMaxLen - 1) / 2;
            }
        }
        
        return s.substring(start, start + maxLen);
    }
    
    // Helper method to expand around center and return palindrome length
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1; // Length of palindrome
    }
    
    // Approach 2: Dynamic Programming - O(n^2) time and space
    public String longestPalindromeDP(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        String result = "";
        
        // Every single character is a palindrome
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            result = s.substring(i, i + 1);
        }
        
        // Check for palindromes of length 2
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                result = s.substring(i, i + 2);
            }
        }
        
        // Check for palindromes of length 3 and more
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    result = s.substring(i, j + 1);
                }
            }
        }
        
        return result;
    }
    
    // Approach 3: Brute Force - O(n^3) time - For educational purposes
    public String longestPalindromeBruteForce(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        String longest = "";
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                String substring = s.substring(i, j + 1);
                if (isPalindrome(substring) && substring.length() > longest.length()) {
                    longest = substring;
                }
            }
        }
        
        return longest;
    }
    
    // Helper method to check if a string is palindrome
    private boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    // Approach 4: Manacher's Algorithm - O(n) time (Advanced)
    public String longestPalindromeManacher(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        // Transform string to handle even length palindromes
        // "abc" -> "^#a#b#c#$"
        StringBuilder sb = new StringBuilder();
        sb.append("^");
        for (char c : s.toCharArray()) {
            sb.append("#").append(c);
        }
        sb.append("#$");
        
        String transformed = sb.toString();
        int n = transformed.length();
        int[] P = new int[n]; // P[i] = length of palindrome centered at i
        int center = 0, right = 0;
        
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
        }
        
        // Find the longest palindrome
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (P[i] > maxLen) {
                maxLen = P[i];
                centerIndex = i;
            }
        }
        
        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        String[] testCases = {
            "ababd",
            "abbc",
            "racecar",
            "abcdef",
            "aabbaa",
            "a",
            "ac"
        };
        
        for (String test : testCases) {
            System.out.println("Input: \"" + test + "\"");
            System.out.println("Expand Around Centers: \"" + solution.longestPalindrome(test) + "\"");
            System.out.println("Dynamic Programming: \"" + solution.longestPalindromeDP(test) + "\"");
            System.out.println("Brute Force: \"" + solution.longestPalindromeBruteForce(test) + "\"");
            System.out.println("Manacher's Algorithm: \"" + solution.longestPalindromeManacher(test) + "\"");
            System.out.println("---");
        }
    }
}
