//https://neetcode.io/problems/longest-consecutive-sequence?list=blind75

import java.util.*;

public class LongestConsecutiveSequence {

    // SOLUTION 1: Optimal O(n) time, O(n) space using HashSet
    // Key insight: Only start counting from the beginning of a sequence
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;

        // Add all numbers to HashSet for O(1) lookup
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int maxLength = 0;

        // For each number, check if it's the start of a sequence
        for (int num : numSet) {
            // Only process if this is the start of a sequence
            // (i.e., num-1 is not in the set)
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentLength = 1;

                // Count consecutive numbers
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentLength++;
                }

                maxLength = Math.max(maxLength, currentLength);
            }
        }

        return maxLength;
    }

    // SOLUTION 2: Alternative O(n) approach using HashMap to track sequence lengths
    public int longestConsecutiveWithMap(int[] nums) {
        if (nums.length == 0) return 0;

        Map<Integer, Integer> map = new HashMap<>();
        int maxLength = 0;

        for (int num : nums) {
            if (!map.containsKey(num)) {
                // Get lengths of adjacent sequences
                int leftLength = map.getOrDefault(num - 1, 0);
                int rightLength = map.getOrDefault(num + 1, 0);

                // Current sequence length
                int currentLength = leftLength + rightLength + 1;
                maxLength = Math.max(maxLength, currentLength);

                // Update the map
                map.put(num, currentLength);

                // Update the boundaries of the sequence
                map.put(num - leftLength, currentLength);
                map.put(num + rightLength, currentLength);
            }
        }

        return maxLength;
    }

    // SOLUTION 3: Sorting approach O(n log n) time, O(1) space
    // Not optimal but easier to understand
    public int longestConsecutiveSorting(int[] nums) {
        if (nums.length == 0) return 0;

        Arrays.sort(nums);

        int maxLength = 1;
        int currentLength = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                // Skip duplicates
                continue;
            } else if (nums[i] == nums[i - 1] + 1) {
                // Consecutive number found
                currentLength++;
            } else {
                // Sequence broken, reset
                maxLength = Math.max(maxLength, currentLength);
                currentLength = 1;
            }
        }

        return Math.max(maxLength, currentLength);
    }

    // SOLUTION 4: Union-Find approach O(n) average time
    public int longestConsecutiveUnionFind(int[] nums) {
        if (nums.length == 0) return 0;

        UnionFind uf = new UnionFind();

        // Add all numbers to union-find
        for (int num : nums) {
            uf.add(num);
        }

        // Union consecutive numbers
        for (int num : nums) {
            if (uf.contains(num + 1)) {
                uf.union(num, num + 1);
            }
        }

        return uf.getMaxSize();
    }

    // Helper class for Union-Find solution
    class UnionFind {
        private Map<Integer, Integer> parent;
        private Map<Integer, Integer> size;

        public UnionFind() {
            parent = new HashMap<>();
            size = new HashMap<>();
        }

        public void add(int x) {
            if (!parent.containsKey(x)) {
                parent.put(x, x);
                size.put(x, 1);
            }
        }

        public boolean contains(int x) {
            return parent.containsKey(x);
        }

        public int find(int x) {
            if (parent.get(x) != x) {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                if (size.get(rootX) < size.get(rootY)) {
                    parent.put(rootX, rootY);
                    size.put(rootY, size.get(rootX) + size.get(rootY));
                } else {
                    parent.put(rootY, rootX);
                    size.put(rootX, size.get(rootX) + size.get(rootY));
                }
            }
        }

        public int getMaxSize() {
            return size.values().stream().max(Integer::compareTo).orElse(0);
        }
    }

    // SOLUTION 5: Brute Force O(n³) approach (not recommended)
    public int longestConsecutiveBruteForce(int[] nums) {
        if (nums.length == 0) return 0;

        int maxLength = 1;

        for (int num : nums) {
            int currentLength = 1;
            int currentNum = num;

            // Check for consecutive numbers in positive direction
            while (contains(nums, currentNum + 1)) {
                currentNum++;
                currentLength++;
            }

            maxLength = Math.max(maxLength, currentLength);
        }

        return maxLength;
    }

    private boolean contains(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) return true;
        }
        return false;
    }

    // Test method to verify solutions
    public static void main(String[] args) {
        LongestConsecutiveSequence solution = new LongestConsecutiveSequence();

        // Test case 1
        int[] nums1 = {2, 20, 4, 10, 3, 4, 5};
        System.out.println("Input: [2,20,4,10,3,4,5]");
        System.out.println("Output: " + solution.longestConsecutive(nums1));
        System.out.println("Expected: 4 (sequence: [2,3,4,5])");

        // Test case 2
        int[] nums2 = {0, 3, 2, 5, 4, 6, 1, 1};
        System.out.println("\nInput: [0,3,2,5,4,6,1,1]");
        System.out.println("Output: " + solution.longestConsecutive(nums2));
        System.out.println("Expected: 7 (sequence: [0,1,2,3,4,5,6])");

        // Test case 3: Edge cases
        int[] nums3 = {};
        System.out.println("\nInput: []");
        System.out.println("Output: " + solution.longestConsecutive(nums3));
        System.out.println("Expected: 0");

        int[] nums4 = {1};
        System.out.println("\nInput: [1]");
        System.out.println("Output: " + solution.longestConsecutive(nums4));
        System.out.println("Expected: 1");

        int[] nums5 = {1, 2, 0, 1};
        System.out.println("\nInput: [1,2,0,1]");
        System.out.println("Output: " + solution.longestConsecutive(nums5));
        System.out.println("Expected: 3 (sequence: [0,1,2])");
    }
}