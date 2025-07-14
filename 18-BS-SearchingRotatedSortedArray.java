#https://neetcode.io/problems/find-target-in-rotated-sorted-array?list=blind75
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Found the target
            if (nums[mid] == target) {
                return mid;
            }
            
            // Determine which half is sorted
            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                if (nums[left] <= target && target < nums[mid]) {
                    // Target is in the left half
                    right = mid - 1;
                } else {
                    // Target is in the right half
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                if (nums[mid] < target && target <= nums[right]) {
                    // Target is in the right half
                    left = mid + 1;
                } else {
                    // Target is in the left half
                    right = mid - 1;
                }
            }
        }
        
        return -1; // Target not found
    }
    
    // Alternative approach: Find rotation point first, then search
    public int searchAlternative(int[] nums, int target) {
        // Find the rotation point (minimum element index)
        int rotationIndex = findRotationPoint(nums);
        
        // If array is not rotated
        if (rotationIndex == 0) {
            return binarySearch(nums, 0, nums.length - 1, target);
        }
        
        // Determine which half to search
        if (target >= nums[0]) {
            // Search in left half
            return binarySearch(nums, 0, rotationIndex - 1, target);
        } else {
            // Search in right half
            return binarySearch(nums, rotationIndex, nums.length - 1, target);
        }
    }
    
    private int findRotationPoint(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    private int binarySearch(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
    
    // Recursive approach
    public int searchRecursive(int[] nums, int target) {
        return searchHelper(nums, 0, nums.length - 1, target);
    }
    
    private int searchHelper(int[] nums, int left, int right, int target) {
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        }
        
        // Left half is sorted
        if (nums[left] <= nums[mid]) {
            if (nums[left] <= target && target < nums[mid]) {
                return searchHelper(nums, left, mid - 1, target);
            } else {
                return searchHelper(nums, mid + 1, right, target);
            }
        } 
        // Right half is sorted
        else {
            if (nums[mid] < target && target <= nums[right]) {
                return searchHelper(nums, mid + 1, right, target);
            } else {
                return searchHelper(nums, left, mid - 1, target);
            }
        }
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        int[][] arrays = {
            {4, 5, 6, 7, 0, 1, 2},
            {4, 5, 6, 7, 0, 1, 2},
            {1},
            {1, 2, 3, 4, 5, 6, 7},
            {7, 1, 2, 3, 4, 5, 6},
            {3, 1}
        };
        
        int[] targets = {0, 3, 1, 4, 1, 1};
        int[] expected = {4, -1, 0, 3, 1, 1};
        
        System.out.println("Testing main solution:");
        for (int i = 0; i < arrays.length; i++) {
            int result = solution.search(arrays[i], targets[i]);
            System.out.printf("Array: %s, Target: %d -> Index: %d (Expected: %d) %s%n", 
                java.util.Arrays.toString(arrays[i]), targets[i], result, expected[i],
                result == expected[i] ? "✓" : "✗");
        }
        
        System.out.println("\nTesting alternative solution:");
        for (int i = 0; i < arrays.length; i++) {
            int result = solution.searchAlternative(arrays[i], targets[i]);
            System.out.printf("Array: %s, Target: %d -> Index: %d (Expected: %d) %s%n", 
                java.util.Arrays.toString(arrays[i]), targets[i], result, expected[i],
                result == expected[i] ? "✓" : "✗");
        }
        
        System.out.println("\nTesting recursive solution:");
        for (int i = 0; i < arrays.length; i++) {
            int result = solution.searchRecursive(arrays[i], targets[i]);
            System.out.printf("Array: %s, Target: %d -> Index: %d (Expected: %d) %s%n", 
                java.util.Arrays.toString(arrays[i]), targets[i], result, expected[i],
                result == expected[i] ? "✓" : "✗");
        }
    }
}
