#https://neetcode.io/problems/is-anagram?list=blind75
import java.util.*;

public class ValidAnagram {
    
    // Approach 1: Using Character Count Array - O(n) time, O(1) space
    // Most efficient for lowercase English letters
    public boolean isAnagram1(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        // Array to count occurrences of each character (a-z)
        int[] charCount = new int[26];
        
        // Count characters in string s and t
        for (int i = 0; i < s.length(); i++) {
            charCount[s.charAt(i) - 'a']++;
            charCount[t.charAt(i) - 'a']--;
        }
        
        // Check if all counts are zero
        for (int count : charCount) {
            if (count != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    // Approach 2: Using HashMap - O(n) time, O(k) space where k is unique characters
    // More flexible, works with any characters including Unicode
    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        HashMap<Character, Integer> charCount = new HashMap<>();
        
        // Count characters in string s
        for (char c : s.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        
        // Subtract character counts for string t
        for (char c : t.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) - 1);
        }
        
        // Check if all counts are zero
        for (int count : charCount.values()) {
            if (count != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    // Approach 3: Using Sorting - O(n log n) time, O(n) space
    // Simple and intuitive approach
    public boolean isAnagram3(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        
        return Arrays.equals(sChars, tChars);
    }
    
    // Approach 4: Using Two HashMaps - O(n) time, O(k) space
    // Alternative HashMap approach
    public boolean isAnagram4(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        HashMap<Character, Integer> sCount = new HashMap<>();
        HashMap<Character, Integer> tCount = new HashMap<>();
        
        // Count characters in both strings
        for (int i = 0; i < s.length(); i++) {
            sCount.put(s.charAt(i), sCount.getOrDefault(s.charAt(i), 0) + 1);
            tCount.put(t.charAt(i), tCount.getOrDefault(t.charAt(i), 0) + 1);
        }
        
        return sCount.equals(tCount);
    }
    
    // Approach 5: Using Frequency Array (Alternative implementation)
    public boolean isAnagram5(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        int[] sCount = new int[26];
        int[] tCount = new int[26];
        
        for (int i = 0; i < s.length(); i++) {
            sCount[s.charAt(i) - 'a']++;
            tCount[t.charAt(i) - 'a']++;
        }
        
        return Arrays.equals(sCount, tCount);
    }
    
    // Test cases
    public static void main(String[] args) {
        ValidAnagram solution = new ValidAnagram();
        
        // Test case 1: Valid anagram
        String s1 = "anagram";
        String t1 = "nagaram";
        System.out.println("Test 1 - '" + s1 + "' & '" + t1 + "': " + solution.isAnagram1(s1, t1)); // true
        
        // Test case 2: Not an anagram
        String s2 = "rat";
        String t2 = "car";
        System.out.println("Test 2 - '" + s2 + "' & '" + t2 + "': " + solution.isAnagram1(s2, t2)); // false
        
        // Test case 3: Different lengths
        String s3 = "a";
        String t3 = "ab";
        System.out.println("Test 3 - '" + s3 + "' & '" + t3 + "': " + solution.isAnagram1(s3, t3)); // false
        
        // Test case 4: Empty strings
        String s4 = "";
        String t4 = "";
        System.out.println("Test 4 - '" + s4 + "' & '" + t4 + "': " + solution.isAnagram1(s4, t4)); // true
        
        // Test case 5: Single character
        String s5 = "a";
        String t5 = "a";
        System.out.println("Test 5 - '" + s5 + "' & '" + t5 + "': " + solution.isAnagram1(s5, t5)); // true
        
        // Test case 6: Same characters, different frequency
        String s6 = "aab";
        String t6 = "aaa";
        System.out.println("Test 6 - '" + s6 + "' & '" + t6 + "': " + solution.isAnagram1(s6, t6)); // false
        
        // Test case 7: Valid anagram with repeated characters
        String s7 = "listen";
        String t7 = "silent";
        System.out.println("Test 7 - '" + s7 + "' & '" + t7 + "': " + solution.isAnagram1(s7, t7)); // true
        
        // Performance comparison (uncomment to test)
        /*
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Random rand = new Random();
        
        // Create large strings for performance testing
        for (int i = 0; i < 100000; i++) {
            char c = (char) ('a' + rand.nextInt(26));
            sb1.append(c);
            sb2.append(c);
        }
        
        String large1 = sb1.toString();
        String large2 = sb2.toString();
        
        long start = System.currentTimeMillis();
        solution.isAnagram1(large1, large2);
        long end = System.currentTimeMillis();
        System.out.println("Array approach time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        solution.isAnagram2(large1, large2);
        end = System.currentTimeMillis();
        System.out.println("HashMap approach time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        solution.isAnagram3(large1, large2);
        end = System.currentTimeMillis();
        System.out.println("Sorting approach time: " + (end - start) + "ms");
        */
    }
}
