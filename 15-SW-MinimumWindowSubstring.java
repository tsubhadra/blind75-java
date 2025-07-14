#https://neetcode.io/problems/minimum-window-with-characters?list=blind75
import java.util.*;

class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        // Count frequency of characters in t
        Map<Character, Integer> targetCount = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetCount.put(c, targetCount.getOrDefault(c, 0) + 1);
        }
        
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;
        int formed = 0; // Number of unique characters in current window with desired frequency
        int required = targetCount.size(); // Number of unique characters in t
        
        // Current window character counts
        Map<Character, Integer> windowCount = new HashMap<>();
        
        for (int right = 0; right < s.length(); right++) {
            // Add character from right to window
            char rightChar = s.charAt(right);
            windowCount.put(rightChar, windowCount.getOrDefault(rightChar, 0) + 1);
            
            // If current character's frequency matches target frequency, increment formed
            if (targetCount.containsKey(rightChar) && 
                windowCount.get(rightChar).intValue() == targetCount.get(rightChar).intValue()) {
                formed++;
            }
            
            // Try to shrink window from left
            while (left <= right && formed == required) {
                // Update minimum window if current is smaller
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }
                
                // Remove character from left
                char leftChar = s.charAt(left);
                windowCount.put(leftChar, windowCount.get(leftChar) - 1);
                
                // If removed character was required and its count drops below target
                if (targetCount.containsKey(leftChar) && 
                    windowCount.get(leftChar).intValue() < targetCount.get(leftChar).intValue()) {
                    formed--;
                }
                
                left++;
            }
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
    
    // Alternative solution using array for ASCII characters (more efficient)
    public String minWindowOptimized(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        // Use arrays for ASCII characters (assuming extended ASCII)
        int[] targetCount = new int[128];
        int[] windowCount = new int[128];
        
        // Count characters in t
        int required = 0;
        for (char c : t.toCharArray()) {
            if (targetCount[c] == 0) {
                required++;
            }
            targetCount[c]++;
        }
        
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;
        int formed = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            windowCount[rightChar]++;
            
            if (targetCount[rightChar] > 0 && windowCount[rightChar] == targetCount[rightChar]) {
                formed++;
            }
            
            while (formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }
                
                char leftChar = s.charAt(left);
                windowCount[leftChar]--;
                
                if (targetCount[leftChar] > 0 && windowCount[leftChar] < targetCount[leftChar]) {
                    formed--;
                }
                
                left++;
            }
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: s = "ADOBECODEBANC", t = "ABC"
        System.out.println("Test 1: \"" + solution.minWindow("ADOBECODEBANC", "ABC") + "\""); // Expected: "BANC"
        
        // Test case 2: s = "a", t = "a"
        System.out.println("Test 2: \"" + solution.minWindow("a", "a") + "\""); // Expected: "a"
        
        // Test case 3: s = "a", t = "aa"
        System.out.println("Test 3: \"" + solution.minWindow("a", "aa") + "\""); // Expected: ""
        
        // Test case 4: s = "ab", t = "b"
        System.out.println("Test 4: \"" + solution.minWindow("ab", "b") + "\""); // Expected: "b"
        
        // Test case 5: s = "bba", t = "ab"
        System.out.println("Test 5: \"" + solution.minWindow("bba", "ab") + "\""); // Expected: "ba"
        
        // Test optimized version
        System.out.println("\nOptimized version:");
        System.out.println("Test 1: \"" + solution.minWindowOptimized("ADOBECODEBANC", "ABC") + "\""); // Expected: "BANC"
    }
}
