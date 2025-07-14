#https://neetcode.io/problems/duplicate-integer?list=blind75
import java.util.*;

public class ContainsDuplicate {
    
    // Approach 1: Using HashSet - O(n) time, O(n) space
    // Most efficient approach for general cases
    public boolean containsDuplicate1(int[] nums) {
        HashSet<Integer> seen = new HashSet<>();
        
        for (int num : nums) {
            if (seen.contains(num)) {
                return true;
            }
            seen.add(num);
        }
        
        return false;
    }
    
    // Approach 2: Using Arrays.sort() - O(n log n) time, O(1) space
    // Good when space is limited
    public boolean containsDuplicate2(int[] nums) {
        Arrays.sort(nums);
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        
        return false;
    }
    
    // Approach 3: Brute Force - O(n²) time, O(1) space
    // Simple but inefficient - only for educational purposes
    public boolean containsDuplicate3(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    // Approach 4: Using HashMap to count occurrences - O(n) time, O(n) space
    // Useful if you need to know the count of duplicates
    public boolean containsDuplicate4(int[] nums) {
        HashMap<Integer, Integer> count = new HashMap<>();
        
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
            if (count.get(num) > 1) {
                return true;
            }
        }
        
        return false;
    }
    
    // Test cases
    public static void main(String[] args) {
        ContainsDuplicate solution = new ContainsDuplicate();
        
        // Test case 1: Contains duplicate
        int[] nums1 = {1, 2, 3, 1};
        System.out.println("Test 1 - [1,2,3,1]: " + solution.containsDuplicate1(nums1)); // true
        
        // Test case 2: No duplicates
        int[] nums2 = {1, 2, 3, 4};
        System.out.println("Test 2 - [1,2,3,4]: " + solution.containsDuplicate1(nums2)); // false
        
        // Test case 3: All same elements
        int[] nums3 = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
        System.out.println("Test 3 - [1,1,1,3,3,4,3,2,4,2]: " + solution.containsDuplicate1(nums3)); // true
        
        // Test case 4: Single element
        int[] nums4 = {1};
        System.out.println("Test 4 - [1]: " + solution.containsDuplicate1(nums4)); // false
        
        // Test case 5: Empty array
        int[] nums5 = {};
        System.out.println("Test 5 - []: " + solution.containsDuplicate1(nums5)); // false
        
        // Performance comparison (you can uncomment to test)
        /*
        int[] largeArray = new int[100000];
        for (int i = 0; i < 100000; i++) {
            largeArray[i] = i % 50000; // This will create duplicates
        }
        
        long start = System.currentTimeMillis();
        solution.containsDuplicate1(largeArray.clone());
        long end = System.currentTimeMillis();
        System.out.println("HashSet approach time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        solution.containsDuplicate2(largeArray.clone());
        end = System.currentTimeMillis();
        System.out.println("Sorting approach time: " + (end - start) + "ms");
        */
    }
}
