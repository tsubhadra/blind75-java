#https://neetcode.io/problems/two-integer-sum?list=blind75
import java.util.*;

public class TwoSum {
    
    // Approach 1: HashMap (One Pass) - O(n) time, O(n) space
    // Most efficient approach - RECOMMENDED
    public int[] twoSum1(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            
            map.put(nums[i], i);
        }
        
        // Should never reach here based on problem constraints
        return new int[]{};
    }
    
    // Approach 2: HashMap (Two Pass) - O(n) time, O(n) space
    // Alternative HashMap approach
    public int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        
        // First pass: build the hash map
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        
        // Second pass: find the complement
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[]{i, map.get(complement)};
            }
        }
        
        return new int[]{};
    }
    
    // Approach 3: Brute Force - O(n²) time, O(1) space
    // Simple but inefficient - only for educational purposes
    public int[] twoSum3(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        
        return new int[]{};
    }
    
    // Approach 4: Two Pointers (Only works if array is sorted or we can sort)
    // O(n log n) time due to sorting, O(1) space
    // Note: This changes the original indices, so we need to track original positions
    public int[] twoSum4(int[] nums, int target) {
        // Create array of pairs (value, original_index)
        int[][] pairs = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            pairs[i][0] = nums[i];
            pairs[i][1] = i;
        }
        
        // Sort by value
        Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int sum = pairs[left][0] + pairs[right][0];
            if (sum == target) {
                return new int[]{pairs[left][1], pairs[right][1]};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[]{};
    }
    
    // Utility method to print array
    public void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
    
    // Test cases
    public static void main(String[] args) {
        TwoSum solution = new TwoSum();
        
        // Test case 1: Basic case
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        System.out.print("Test 1 - nums: " + Arrays.toString(nums1) + ", target: " + target1 + " => ");
        solution.printArray(solution.twoSum1(nums1, target1)); // [0, 1]
        
        // Test case 2: Multiple solutions exist (return any valid one)
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        System.out.print("Test 2 - nums: " + Arrays.toString(nums2) + ", target: " + target2 + " => ");
        solution.printArray(solution.twoSum1(nums2, target2)); // [1, 2]
        
        // Test case 3: Same number used twice
        int[] nums3 = {3, 3};
        int target3 = 6;
        System.out.print("Test 3 - nums: " + Arrays.toString(nums3) + ", target: " + target3 + " => ");
        solution.printArray(solution.twoSum1(nums3, target3)); // [0, 1]
        
        // Test case 4: Negative numbers
        int[] nums4 = {-1, -2, -3, -4, -5};
        int target4 = -8;
        System.out.print("Test 4 - nums: " + Arrays.toString(nums4) + ", target: " + target4 + " => ");
        solution.printArray(solution.twoSum1(nums4, target4)); // [2, 4]
        
        // Test case 5: Mix of positive and negative
        int[] nums5 = {-3, 4, 3, 90};
        int target5 = 0;
        System.out.print("Test 5 - nums: " + Arrays.toString(nums5) + ", target: " + target5 + " => ");
        solution.printArray(solution.twoSum1(nums5, target5)); // [0, 2]
        
        // Test case 6: Large numbers
        int[] nums6 = {1000000000, 999999999, 1, 2};
        int target6 = 1999999999;
        System.out.print("Test 6 - nums: " + Arrays.toString(nums6) + ", target: " + target6 + " => ");
        solution.printArray(solution.twoSum1(nums6, target6)); // [0, 1]
        
        // Performance comparison with larger array
        System.out.println("\n--- Performance Comparison ---");
        int[] largeNums = new int[10000];
        for (int i = 0; i < 10000; i++) {
            largeNums[i] = i;
        }
        int largeTarget = 19999; // Should find indices 9999 and 10000
        
        // Test HashMap approach
        long start = System.currentTimeMillis();
        int[] result1 = solution.twoSum1(largeNums.clone(), largeTarget);
        long end = System.currentTimeMillis();
        System.out.println("HashMap approach time: " + (end - start) + "ms, result: " + Arrays.toString(result1));
        
        // Test Brute Force approach
        start = System.currentTimeMillis();
        int[] result2 = solution.twoSum3(largeNums.clone(), largeTarget);
        end = System.currentTimeMillis();
        System.out.println("Brute Force approach time: " + (end - start) + "ms, result: " + Arrays.toString(result2));
        
        // Verify all approaches give same result for a test case
        System.out.println("\n--- Verification ---");
        int[] testNums = {2, 7, 11, 15};
        int testTarget = 9;
        System.out.println("Test array: " + Arrays.toString(testNums) + ", target: " + testTarget);
        System.out.println("Approach 1 (HashMap One-Pass): " + Arrays.toString(solution.twoSum1(testNums, testTarget)));
        System.out.println("Approach 2 (HashMap Two-Pass): " + Arrays.toString(solution.twoSum2(testNums, testTarget)));
        System.out.println("Approach 3 (Brute Force): " + Arrays.toString(solution.twoSum3(testNums, testTarget)));
        System.out.println("Approach 4 (Two Pointers): " + Arrays.toString(solution.twoSum4(testNums, testTarget)));
    }
}
