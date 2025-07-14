#https://neetcode.io/problems/find-minimum-in-rotated-sorted-array?list=blind75
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // If mid element is greater than rightmost element,
            // minimum is in the right half
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } 
            // If mid element is less than rightmost element,
            // minimum is in the left half (including mid)
            else {
                right = mid;
            }
        }
        
        return nums[left];
    }
    
    // Alternative approach: compare with leftmost element
    public int findMinAlternative(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        // If array is not rotated
        if (nums[left] < nums[right]) {
            return nums[left];
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // If mid is greater than left, minimum is in right half
            if (nums[mid] > nums[left]) {
                left = mid + 1;
            }
            // If mid is less than left, minimum is in left half (including mid)
            else if (nums[mid] < nums[left]) {
                right = mid;
            }
            // If mid equals left, move left pointer
            else {
                left++;
            }
        }
        
        return nums[left];
    }
    
    // Iterative approach to find the rotation point
    public int findMinIterative(int[] nums) {
        int n = nums.length;
        
        // Handle single element
        if (n == 1) return nums[0];
        
        // Array is not rotated
        if (nums[0] < nums[n - 1]) {
            return nums[0];
        }
        
        int left = 0;
        int right = n - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Found the rotation point
            if (nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }
            
            // Check if previous element is the rotation point
            if (nums[mid - 1] > nums[mid]) {
                return nums[mid];
            }
            
            // Decide which half to search
            if (nums[mid] > nums[0]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return nums[0];
    }
    
    // Recursive approach
    public int findMinRecursive(int[] nums) {
        return findMinHelper(nums, 0, nums.length - 1);
    }
    
    private int findMinHelper(int[] nums, int left, int right) {
        // Base case: single element or two elements
        if (left == right) {
            return nums[left];
        }
        
        if (left + 1 == right) {
            return Math.min(nums[left], nums[right]);
        }
        
        // Array is not rotated
        if (nums[left] < nums[right]) {
            return nums[left];
        }
        
        int mid = left + (right - left) / 2;
        
        // Minimum is in right half
        if (nums[mid] > nums[right]) {
            return findMinHelper(nums, mid + 1, right);
        }
        // Minimum is in left half (including mid)
        else {
            return findMinHelper(nums, left, mid);
        }
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        int[][] testCases = {
            {3, 4, 5, 1, 2},           // Expected: 1
            {4, 5, 6, 7, 0, 1, 2},     // Expected: 0
            {11, 13, 15, 17},          // Expected: 11 (not rotated)
            {2, 1},                    // Expected: 1
            {1},                       // Expected: 1
            {2, 3, 4, 5, 1},           // Expected: 1
            {5, 1, 2, 3, 4},           // Expected: 1
            {1, 2, 3, 4, 5}            // Expected: 1 (not rotated)
        };
        
        System.out.println("Testing main solution:");
        for (int i = 0; i < testCases.length; i++) {
            int result = solution.findMin(testCases[i]);
            System.out.printf("Test %d: %s -> %d%n", i + 1, 
                java.util.Arrays.toString(testCases[i]), result);
        }
        
        System.out.println("\nTesting alternative solution:");
        for (int i = 0; i < testCases.length; i++) {
            int result = solution.findMinAlternative(testCases[i]);
            System.out.printf("Test %d: %s -> %d%n", i + 1, 
                java.util.Arrays.toString(testCases[i]), result);
        }
        
        System.out.println("\nTesting recursive solution:");
        for (int i = 0; i < testCases.length; i++) {
            int result = solution.findMinRecursive(testCases[i]);
            System.out.printf("Test %d: %s -> %d%n", i + 1, 
                java.util.Arrays.toString(testCases[i]), result);
        }
    }
}
