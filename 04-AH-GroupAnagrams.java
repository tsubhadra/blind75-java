#https://neetcode.io/problems/anagram-groups?list=blind75
import java.util.*;

public class GroupAnagrams {
    
    // Approach 1: Using Sorted String as Key - O(n * k log k) time, O(n * k) space
    // Where n = number of strings, k = average length of strings
    // Most intuitive and commonly used approach
    public List<List<String>> groupAnagrams1(String[] strs) {
        HashMap<String, List<String>> groups = new HashMap<>();
        
        for (String str : strs) {
            // Sort the string to create a key
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            
            // Add to the group
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(groups.values());
    }
    
    // Approach 2: Using Character Frequency as Key - O(n * k) time, O(n * k) space
    // More efficient as it avoids sorting
    public List<List<String>> groupAnagrams2(String[] strs) {
        HashMap<String, List<String>> groups = new HashMap<>();
        
        for (String str : strs) {
            // Create frequency key
            String key = getFrequencyKey(str);
            
            // Add to the group
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(groups.values());
    }
    
    // Helper method to create frequency-based key
    private String getFrequencyKey(String str) {
        int[] count = new int[26];
        
        // Count character frequencies
        for (char c : str.toCharArray()) {
            count[c - 'a']++;
        }
        
        // Build key string
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                key.append((char) ('a' + i)).append(count[i]);
            }
        }
        
        return key.toString();
    }
    
    // Approach 3: Using Arrays.toString() for frequency array as key
    // Alternative way to create frequency-based key
    public List<List<String>> groupAnagrams3(String[] strs) {
        HashMap<String, List<String>> groups = new HashMap<>();
        
        for (String str : strs) {
            // Create frequency array
            int[] count = new int[26];
            for (char c : str.toCharArray()) {
                count[c - 'a']++;
            }
            
            // Use array string representation as key
            String key = Arrays.toString(count);
            
            // Add to the group
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(groups.values());
    }
    
    // Approach 4: Using Prime Numbers (Mathematical approach)
    // Each character mapped to a prime number, multiply them together
    // Note: This can cause integer overflow for very long strings
    public List<List<String>> groupAnagrams4(String[] strs) {
        HashMap<Long, List<String>> groups = new HashMap<>();
        
        // Prime numbers for each character a-z
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
        
        for (String str : strs) {
            long key = 1;
            
            // Calculate product of primes
            for (char c : str.toCharArray()) {
                key *= primes[c - 'a'];
            }
            
            // Add to the group
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(groups.values());
    }
    
    // Utility method to print results
    public void printGroups(List<List<String>> groups) {
        System.out.println("Groups:");
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("Group " + (i + 1) + ": " + groups.get(i));
        }
        System.out.println();
    }
    
    // Test cases
    public static void main(String[] args) {
        GroupAnagrams solution = new GroupAnagrams();
        
        // Test case 1: Standard example
        String[] strs1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Test 1 - Input: " + Arrays.toString(strs1));
        solution.printGroups(solution.groupAnagrams1(strs1));
        // Expected: [["bat"], ["nat","tan"], ["ate","eat","tea"]]
        
        // Test case 2: Empty string
        String[] strs2 = {""};
        System.out.println("Test 2 - Input: " + Arrays.toString(strs2));
        solution.printGroups(solution.groupAnagrams1(strs2));
        // Expected: [[""]]
        
        // Test case 3: Single character
        String[] strs3 = {"a"};
        System.out.println("Test 3 - Input: " + Arrays.toString(strs3));
        solution.printGroups(solution.groupAnagrams1(strs3));
        // Expected: [["a"]]
        
        // Test case 4: All same anagrams
        String[] strs4 = {"abc", "bca", "cab", "acb"};
        System.out.println("Test 4 - Input: " + Arrays.toString(strs4));
        solution.printGroups(solution.groupAnagrams1(strs4));
        // Expected: [["abc","bca","cab","acb"]]
        
        // Test case 5: No anagrams
        String[] strs5 = {"abc", "def", "ghi"};
        System.out.println("Test 5 - Input: " + Arrays.toString(strs5));
        solution.printGroups(solution.groupAnagrams1(strs5));
        // Expected: [["abc"], ["def"], ["ghi"]]
        
        // Test case 6: Mixed lengths
        String[] strs6 = {"a", "aa", "aaa", "b", "bb", "bbb"};
        System.out.println("Test 6 - Input: " + Arrays.toString(strs6));
        solution.printGroups(solution.groupAnagrams1(strs6));
        // Expected: [["a"], ["aa"], ["aaa"], ["b"], ["bb"], ["bbb"]]
        
        // Test case 7: Duplicate strings
        String[] strs7 = {"listen", "silent", "hello", "olleh", "listen"};
        System.out.println("Test 7 - Input: " + Arrays.toString(strs7));
        solution.printGroups(solution.groupAnagrams1(strs7));
        // Expected: [["listen","silent","listen"], ["hello","olleh"]]
        
        // Performance comparison
        System.out.println("--- Performance Comparison ---");
        String[] largeTest = {"eat", "tea", "tan", "ate", "nat", "bat", "tab", "god", "dog", "good", "dogo"};
        
        long start = System.currentTimeMillis();
        List<List<String>> result1 = solution.groupAnagrams1(largeTest);
        long end = System.currentTimeMillis();
        System.out.println("Approach 1 (Sorting) time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        List<List<String>> result2 = solution.groupAnagrams2(largeTest);
        end = System.currentTimeMillis();
        System.out.println("Approach 2 (Frequency) time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        List<List<String>> result3 = solution.groupAnagrams3(largeTest);
        end = System.currentTimeMillis();
        System.out.println("Approach 3 (Array toString) time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        List<List<String>> result4 = solution.groupAnagrams4(largeTest);
        end = System.currentTimeMillis();
        System.out.println("Approach 4 (Prime numbers) time: " + (end - start) + "ms");
        
        // Verify all approaches give same result (though order might differ)
        System.out.println("All approaches return same group count: " + 
            (result1.size() == result2.size() && result2.size() == result3.size() && result3.size() == result4.size()));
    }
}
