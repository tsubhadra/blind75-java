#https://neetcode.io/problems/longest-repeating-substring-with-replacement?list=blind75
class Solution {
    public int characterReplacement(String s, int k) {
        // HashMap to count frequency of characters in current window
        int[] count = new int[26]; // For uppercase English letters A-Z
        int left = 0; // Left pointer of sliding window
        int maxCount = 0; // Count of most frequent character in current window
        int maxLength = 0; // Maximum length found so far
        
        // Right pointer expands the window
        for (int right = 0; right < s.length(); right++) {
            // Add current character to window
            char rightChar = s.charAt(right);
            count[rightChar - 'A']++;
            
            // Update max count of any character in current window
            maxCount = Math.max(maxCount, count[rightChar - 'A']);
            
            // If current window size minus max character count > k,
            // we need to shrink the window from left
            while (right - left + 1 - maxCount > k) {
                char leftChar = s.charAt(left);
                count[leftChar - 'A']--;
                left++;
                
                // Note: We don't need to recalculate maxCount when shrinking
                // because we only care about finding a longer valid window
                // The maxCount from previous iterations is still valid for
                // our purpose of finding the maximum length
            }
            
            // Update maximum length found
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    // Alternative solution with more explicit maxCount recalculation
    public int characterReplacementAlternative(String s, int k) {
        int[] count = new int[26];
        int left = 0;
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            count[s.charAt(right) - 'A']++;
            
            // Calculate current max count in window
            int currentMaxCount = 0;
            for (int i = 0; i < 26; i++) {
                currentMaxCount = Math.max(currentMaxCount, count[i]);
            }
            
            // Shrink window if needed
            while (right - left + 1 - currentMaxCount > k) {
                count[s.charAt(left) - 'A']--;
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: s = "ABAB", k = 2
        System.out.println("Test 1: " + solution.characterReplacement("ABAB", 2)); // Expected: 4
        
        // Test case 2: s = "AABABBA", k = 1  
        System.out.println("Test 2: " + solution.characterReplacement("AABABBA", 1)); // Expected: 4
        
        // Test case 3: s = "ABCDE", k = 1
        System.out.println("Test 3: " + solution.characterReplacement("ABCDE", 1)); // Expected: 2
        
        // Test case 4: s = "AAAA", k = 0
        System.out.println("Test 4: " + solution.characterReplacement("AAAA", 0)); // Expected: 4
    }
}
