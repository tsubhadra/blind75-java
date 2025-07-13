#https://neetcode.io/problems/longest-substring-without-duplicates?list=blind75
import java.util.HashMap;
import java.util.Map;

public class LongestSubstring {
    
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        int left = 0; // left pointer of sliding window
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If character is already seen and is within current window
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                // Move left pointer to position after the duplicate character
                left = charIndexMap.get(currentChar) + 1;
            }
            
            // Update character's latest position
            charIndexMap.put(currentChar, right);
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    public static void main(String[] args) {
        // Test cases
        String test1 = "zxyzxyz";
        String test2 = "xxxx";
        String test3 = "abcabcbb";
        String test4 = "bbbbb";
        String test5 = "pwwkew";
        String test6 = "";
        String test7 = "a";
        String test8 = "abcdef";
        
        System.out.println("Test 1: \"" + test1 + "\" -> " + lengthOfLongestSubstring(test1));
        System.out.println("Test 2: \"" + test2 + "\" -> " + lengthOfLongestSubstring(test2));
        System.out.println("Test 3: \"" + test3 + "\" -> " + lengthOfLongestSubstring(test3));
        System.out.println("Test 4: \"" + test4 + "\" -> " + lengthOfLongestSubstring(test4));
        System.out.println("Test 5: \"" + test5 + "\" -> " + lengthOfLongestSubstring(test5));
        System.out.println("Test 6: \"" + test6 + "\" -> " + lengthOfLongestSubstring(test6));
        System.out.println("Test 7: \"" + test7 + "\" -> " + lengthOfLongestSubstring(test7));
        System.out.println("Test 8: \"" + test8 + "\" -> " + lengthOfLongestSubstring(test8));
    }
}

/*
Algorithm Explanation:
1. Use sliding window technique with two pointers (left and right)
2. HashMap stores each character and its most recent index
3. When duplicate found within current window:
   - Move left pointer to position after the previous occurrence
4. Update maximum length at each step

Time Complexity: O(n) - single pass through string
Space Complexity: O(m) - where m is number of unique characters

Example walkthrough for "zxyzxyz":
- z(0): window="z", length=1
- x(1): window="zx", length=2  
- y(2): window="zxy", length=3
- z(3): duplicate found, left moves to 1, window="xyz", length=3
- x(4): duplicate found, left moves to 2, window="yzx", length=3
- y(5): duplicate found, left moves to 3, window="zxy", length=3
- z(6): duplicate found, left moves to 4, window="xyz", length=3
Result: 3
*/
