//https://neetcode.io/problems/longest-increasing-subsequence?list=blind75
public class Solution {
    
    // Approach 1: Dynamic Programming - O(n^2) - Most intuitive
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        // dp[i] represents length of LIS ending at index i
        int[] dp = new int[n];
        
        // Each element forms a subsequence of length 1
        java.util.Arrays.fill(dp, 1);
        
        int maxLength = 1;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If current element is greater than previous element
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }
        
        return maxLength;
    }
    
    // Approach 2: Binary Search + Greedy - O(n log n) - Most efficient
    public int lengthOfLISOptimal(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // tails[i] stores the smallest tail element for subsequence of length i+1
        java.util.List<Integer> tails = new java.util.ArrayList<>();
        
        for (int num : nums) {
            // Binary search for the position to insert/replace
            int pos = binarySearch(tails, num);
            
            if (pos == tails.size()) {
                // num is larger than all elements, extend the sequence
                tails.add(num);
            } else {
                // Replace the element at pos with num (maintaining smallest tails)
                tails.set(pos, num);
            }
        }
        
        return tails.size();
    }
    
    // Binary search to find leftmost position where target can be inserted
    private int binarySearch(java.util.List<Integer> tails, int target) {
        int left = 0, right = tails.size();
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (tails.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    // Approach 3: Using Java's built-in binary search
    public int lengthOfLISBuiltIn(int[] nums) {
        java.util.List<Integer> tails = new java.util.ArrayList<>();
        
        for (int num : nums) {
            int pos = java.util.Collections.binarySearch(tails, num);
            
            if (pos < 0) {
                pos = -(pos + 1); // Convert to insertion point
            }
            
            if (pos == tails.size()) {
                tails.add(num);
            } else {
                tails.set(pos, num);
            }
        }
        
        return tails.size();
    }
    
    // Approach 4: Recursive with Memoization
    public int lengthOfLISRecursive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int[][] memo = new int[nums.length][nums.length + 1];
        // Initialize memo with -1 (uncomputed)
        for (int[] row : memo) {
            java.util.Arrays.fill(row, -1);
        }
        
        return lisRecursive(nums, 0, -1, memo);
    }
    
    private int lisRecursive(int[] nums, int index, int prevIndex, int[][] memo) {
        if (index == nums.length) {
            return 0;
        }
        
        int memoIndex = prevIndex + 1; // Shift by 1 since prevIndex can be -1
        if (memo[index][memoIndex] != -1) {
            return memo[index][memoIndex];
        }
        
        // Option 1: Don't take current element
        int exclude = lisRecursive(nums, index + 1, prevIndex, memo);
        
        // Option 2: Take current element (if it's valid)
        int include = 0;
        if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
            include = 1 + lisRecursive(nums, index + 1, index, memo);
        }
        
        memo[index][memoIndex] = Math.max(exclude, include);
        return memo[index][memoIndex];
    }
    
    // Method to find the actual LIS (not just length)\n    public java.util.List<Integer> findLIS(int[] nums) {\n        if (nums == null || nums.length == 0) {\n            return new java.util.ArrayList<>();\n        }\n        \n        int n = nums.length;\n        int[] dp = new int[n];\n        int[] parent = new int[n]; // To reconstruct the sequence\n        \n        java.util.Arrays.fill(dp, 1);\n        java.util.Arrays.fill(parent, -1);\n        \n        int maxLength = 1;\n        int maxIndex = 0;\n        \n        for (int i = 1; i < n; i++) {\n            for (int j = 0; j < i; j++) {\n                if (nums[i] > nums[j] && dp[j] + 1 > dp[i]) {\n                    dp[i] = dp[j] + 1;\n                    parent[i] = j;\n                }\n            }\n            \n            if (dp[i] > maxLength) {\n                maxLength = dp[i];\n                maxIndex = i;\n            }\n        }\n        \n        // Reconstruct the LIS\n        java.util.List<Integer> lis = new java.util.ArrayList<>();\n        int current = maxIndex;\n        \n        while (current != -1) {\n            lis.add(0, nums[current]); // Add to front\n            current = parent[current];\n        }\n        \n        return lis;\n    }\n    \n    // Method to count number of LIS\n    public int findNumberOfLIS(int[] nums) {\n        if (nums == null || nums.length == 0) {\n            return 0;\n        }\n        \n        int n = nums.length;\n        int[] lengths = new int[n]; // Length of LIS ending at i\n        int[] counts = new int[n];  // Number of LIS ending at i\n        \n        java.util.Arrays.fill(lengths, 1);\n        java.util.Arrays.fill(counts, 1);\n        \n        int maxLength = 1;\n        \n        for (int i = 1; i < n; i++) {\n            for (int j = 0; j < i; j++) {\n                if (nums[i] > nums[j]) {\n                    if (lengths[j] + 1 > lengths[i]) {\n                        lengths[i] = lengths[j] + 1;\n                        counts[i] = counts[j];\n                    } else if (lengths[j] + 1 == lengths[i]) {\n                        counts[i] += counts[j];\n                    }\n                }\n            }\n            maxLength = Math.max(maxLength, lengths[i]);\n        }\n
