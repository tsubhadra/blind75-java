//https://neetcode.io/problems/max-water-container?list=blind75
public class ContainerWithMostWater {

    // SOLUTION 1: Optimal Two-Pointer Approach (Recommended)
    // Time: O(n), Space: O(1)
    public int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int maxWater = 0;

        while (left < right) {
            // Calculate current area
            // Area = min(height[left], height[right]) * (right - left)
            int width = right - left;
            int currentHeight = Math.min(height[left], height[right]);
            int currentArea = width * currentHeight;

            // Update maximum area
            maxWater = Math.max(maxWater, currentArea);

            // Move the pointer pointing to the shorter bar
            // This is the key insight: moving the taller bar won't increase area
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxWater;
    }

    // SOLUTION 2: Optimized Two-Pointer with Skip Logic
    // Time: O(n), Space: O(1)
    // Optimization: Skip bars that are shorter than current minimum
    public int maxAreaOptimized(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int maxWater = 0;

        while (left < right) {
            int width = right - left;
            int leftHeight = height[left];
            int rightHeight = height[right];
            int currentHeight = Math.min(leftHeight, rightHeight);
            int currentArea = width * currentHeight;

            maxWater = Math.max(maxWater, currentArea);

            // Move the pointer with shorter height
            if (leftHeight < rightHeight) {
                // Skip all bars shorter than or equal to current left height
                while (left < right && height[left] <= leftHeight) {
                    left++;
                }
            } else {
                // Skip all bars shorter than or equal to current right height
                while (left < right && height[right] <= rightHeight) {
                    right--;
                }
            }
        }

        return maxWater;
    }

    // SOLUTION 3: Brute Force Approach (Not recommended for large inputs)
    // Time: O(n²), Space: O(1)
    public int maxAreaBruteForce(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int maxWater = 0;

        // Try all possible pairs
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int width = j - i;
                int currentHeight = Math.min(height[i], height[j]);
                int currentArea = width * currentHeight;
                maxWater = Math.max(maxWater, currentArea);
            }
        }

        return maxWater;
    }

    // SOLUTION 4: Recursive Approach with Memoization
    // Time: O(n²), Space: O(n²) - Not efficient but demonstrates concept
    public int maxAreaRecursive(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        Integer[][] memo = new Integer[height.length][height.length];
        return maxAreaHelper(height, 0, height.length - 1, memo);
    }

    private int maxAreaHelper(int[] height, int left, int right, Integer[][] memo) {
        if (left >= right) {
            return 0;
        }

        if (memo[left][right] != null) {
            return memo[left][right];
        }

        // Calculate current area
        int width = right - left;
        int currentHeight = Math.min(height[left], height[right]);
        int currentArea = width * currentHeight;

        // Try moving left or right pointer
        int maxFromLeft = maxAreaHelper(height, left + 1, right, memo);
        int maxFromRight = maxAreaHelper(height, left, right - 1, memo);

        int result = Math.max(currentArea, Math.max(maxFromLeft, maxFromRight));
        memo[left][right] = result;

        return result;
    }

    // SOLUTION 5: Dynamic Programming Approach (Overkill for this problem)
    // Time: O(n²), Space: O(n²)
    public int maxAreaDP(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int n = height.length;
        int[][] dp = new int[n][n];
        int maxWater = 0;

        // Fill DP table
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int width = j - i;
                int currentHeight = Math.min(height[i], height[j]);
                dp[i][j] = width * currentHeight;
                maxWater = Math.max(maxWater, dp[i][j]);
            }
        }

        return maxWater;
    }

    // SOLUTION 6: Alternative Two-Pointer with Detailed Tracking
    // Time: O(n), Space: O(1)
    // Shows step-by-step calculation for educational purposes
    public int maxAreaDetailed(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int maxWater = 0;
        int maxLeft = left;
        int maxRight = right;

        System.out.println("Step-by-step calculation:");

        while (left < right) {
            int width = right - left;
            int leftHeight = height[left];
            int rightHeight = height[right];
            int currentHeight = Math.min(leftHeight, rightHeight);
            int currentArea = width * currentHeight;

            System.out.printf("Left=%d (h=%d), Right=%d (h=%d), Width=%d, Height=%d, Area=%d%n",
                    left, leftHeight, right, rightHeight, width, currentHeight, currentArea);

            if (currentArea > maxWater) {
                maxWater = currentArea;
                maxLeft = left;
                maxRight = right;
                System.out.println("  ^ New maximum found!");
            }

            // Move pointer with shorter height
            if (leftHeight < rightHeight) {
                left++;
            } else {
                right--;
            }
        }

        System.out.printf("Maximum area: %d (between positions %d and %d)%n",
                maxWater, maxLeft, maxRight);

        return maxWater;
    }

    // Test method to verify solutions
    public static void main(String[] args) {
        ContainerWithMostWater solution = new ContainerWithMostWater();

        // Test case 1
        int[] height1 = {1, 7, 2, 5, 4, 7, 3, 6};
        System.out.println("Input: [1,7,2,5,4,7,3,6]");
        System.out.println("Output: " + solution.maxArea(height1));
        System.out.println("Expected: 36");
        System.out.println("Explanation: Container between positions 1 and 5 (heights 7 and 7)");
        System.out.println("Area = min(7,7) × (5-1) = 7 × 4 = 28... wait, let me recalculate");
        System.out.println("Actually: Container between positions 1 and 7 (heights 7 and 6)");
        System.out.println("Area = min(7,6) × (7-1) = 6 × 6 = 36");

        System.out.println("\nDetailed calculation for first example:");
        solution.maxAreaDetailed(height1);

        // Test case 2
        int[] height2 = {2, 2, 2};
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Input: [2,2,2]");
        System.out.println("Output: " + solution.maxArea(height2));
        System.out.println("Expected: 4");
        System.out.println("Explanation: Container between positions 0 and 2 (heights 2 and 2)");
        System.out.println("Area = min(2,2) × (2-0) = 2 × 2 = 4");

        // Test case 3: Edge cases
        int[] height3 = {1, 1};
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Input: [1,1]");
        System.out.println("Output: " + solution.maxArea(height3));
        System.out.println("Expected: 1");

        int[] height4 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Input: [1,8,6,2,5,4,8,3,7]");
        System.out.println("Output: " + solution.maxArea(height4));
        System.out.println("Expected: 49");
        System.out.println("Explanation: Container between positions 1 and 8 (heights 8 and 7)");
        System.out.println("Area = min(8,7) × (8-1) = 7 × 7 = 49");

        // Performance comparison
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Performance Comparison:");

        int[] largeArray = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeArray[i] = (int) (Math.random() * 1000);
        }

        long startTime = System.nanoTime();
        int result1 = solution.maxArea(largeArray);
        long endTime = System.nanoTime();
        System.out.println("Two-pointer approach: " + (endTime - startTime) / 1000000.0 + " ms, Result: " + result1);

        startTime = System.nanoTime();
        int result2 = solution.maxAreaOptimized(largeArray);
        endTime = System.nanoTime();
        System.out.println("Optimized two-pointer: " + (endTime - startTime) / 1000000.0 + " ms, Result: " + result2);

        // Note: Brute force would be too slow for 1000 elements
        System.out.println("Brute force: Too slow for 1000 elements (would take ~500,000 operations)");
    }
}