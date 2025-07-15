//https://neetcode.io/problems/non-overlapping-intervals?list=blind75
public class Solution {
    
    // Approach 1: Greedy - Sort by end time (Most efficient and elegant)
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        // Sort by end time (greedy choice: intervals that end earlier)
        java.util.Arrays.sort(intervals, 
                             (a, b) -> Integer.compare(a[1], b[1]));
        
        int count = 0;          // Number of intervals to remove
        int lastEnd = intervals[0][1];  // End time of last kept interval
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < lastEnd) {
                // Current interval overlaps with last kept interval
                count++;  // Remove current interval
            } else {
                // No overlap, keep current interval
                lastEnd = intervals[i][1];
            }
        }
        
        return count;
    }
    
    // Approach 2: Greedy - Sort by start time (Alternative approach)
    public int eraseOverlapIntervalsStartSort(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        // Sort by start time
        java.util.Arrays.sort(intervals, 
                             (a, b) -> Integer.compare(a[0], b[0]));
        
        int count = 0;
        int lastEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < lastEnd) {
                // Overlapping - remove the one with later end time
                count++;
                lastEnd = Math.min(lastEnd, intervals[i][1]);
            } else {
                // No overlap
                lastEnd = intervals[i][1];
            }
        }
        
        return count;
    }
    
    // Approach 3: Dynamic Programming - Find maximum non-overlapping intervals
    public int eraseOverlapIntervalsDP(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        // Sort by start time for DP approach
        java.util.Arrays.sort(intervals, 
                             (a, b) -> Integer.compare(a[0], b[0]));
        
        int n = intervals.length;
        // dp[i] = maximum number of non-overlapping intervals ending at or before i
        int[] dp = new int[n];
        dp[0] = 1;
        
        for (int i = 1; i < n; i++) {
            dp[i] = 1; // At least current interval
            
            for (int j = 0; j < i; j++) {
                // If intervals don't overlap
                if (intervals[j][1] <= intervals[i][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        
        // Find maximum non-overlapping intervals
        int maxNonOverlapping = 0;
        for (int i = 0; i < n; i++) {
            maxNonOverlapping = Math.max(maxNonOverlapping, dp[i]);
        }
        
        return n - maxNonOverlapping;
    }
    
    // Approach 4: Activity Selection with explicit tracking
    public int eraseOverlapIntervalsActivitySelection(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        // Sort by end time
        java.util.Arrays.sort(intervals, 
                             (a, b) -> Integer.compare(a[1], b[1]));
        
        java.util.List<int[]> selected = new java.util.ArrayList<>();
        selected.add(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] lastSelected = selected.get(selected.size() - 1);
            
            // If current interval doesn't overlap with last selected
            if (intervals[i][0] >= lastSelected[1]) {
                selected.add(intervals[i]);
            }
            // If overlaps, we implicitly remove current interval
        }
        
        return intervals.length - selected.size();
    }
    
    // Approach 5: Recursive with memoization (less efficient but educational)
    public int eraseOverlapIntervalsRecursive(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        // Sort by start time
        java.util.Arrays.sort(intervals, 
                             (a, b) -> Integer.compare(a[0], b[0]));
        
        int n = intervals.length;
        int maxNonOverlapping = findMaxNonOverlapping(intervals, 0, -1, 
                                                     new java.util.HashMap<>());
        
        return n - maxNonOverlapping;
    }
    
    private int findMaxNonOverlapping(int[][] intervals, int index, 
                                    int lastEnd, 
                                    java.util.Map<String, Integer> memo) {
        if (index >= intervals.length) {
            return 0;
        }
        
        String key = index + "," + lastEnd;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        // Option 1: Skip current interval
        int skip = findMaxNonOverlapping(intervals, index + 1, lastEnd, memo);
        
        // Option 2: Take current interval (if no overlap)
        int take = 0;
        if (lastEnd <= intervals[index][0]) {
            take = 1 + findMaxNonOverlapping(intervals, index + 1, 
                                           intervals[index][1], memo);
        }
        
        int result = Math.max(skip, take);
        memo.put(key, result);
        return result;
    }
    
    // Helper method to check if two intervals overlap
    private boolean isOverlapping(int[] interval1, int[] interval2) {
        return interval1[0] < interval2[1] && interval2[0] < interval1[1];
    }
    
    // Method to demonstrate the greedy algorithm step by step
    public void demonstrateGreedy(int[][] intervals) {
        System.out.println("\nDemonstrating Greedy Algorithm:");
        System.out.println("Original intervals: " + 
                          java.util.Arrays.deepToString(intervals));
        
        if (intervals == null || intervals.length <= 1) {
            System.out.println("No removal needed");
            return;
        }
        
        // Sort by end time
        int[][] sorted = intervals.clone();
        java.util.Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));
        System.out.println("Sorted by end time: " + 
                          java.util.Arrays.deepToString(sorted));
        
        int count = 0;
        int lastEnd = sorted[0][1];
        java.util.List<int[]> kept = new java.util.ArrayList<>();
        java.util.List<int[]> removed = new java.util.ArrayList<>();
        
        kept.add(sorted[0]);
        System.out.println("\nStep 1: Keep " + 
                          java.util.Arrays.toString(sorted[0]) + 
                          " (first interval)");
        
        for (int i = 1; i < sorted.length; i++) {
            System.out.print("Step " + (i + 1) + ": Check " + 
                           java.util.Arrays.toString(sorted[i]));
            
            if (sorted[i][0] < lastEnd) {
                // Overlapping
                count++;
                removed.add(sorted[i]);
                System.out.println(" -> REMOVE (overlaps: " + 
                                 sorted[i][0] + " < " + lastEnd + ")");
            } else {
                // Non-overlapping
                lastEnd = sorted[i][1];
                kept.add(sorted[i]);
                System.out.println(" -> KEEP (no overlap: " + 
                                 sorted[i][0] + " >= " + lastEnd + ")");
                System.out.println("    Update lastEnd to " + lastEnd);
            }
        }
        
        System.out.println("\nFinal result:");
        System.out.println("Intervals to remove: " + count);
        System.out.println("Kept intervals: " + 
                          formatIntervalList(kept));
        System.out.println("Removed intervals: " + 
                          formatIntervalList(removed));
    }
    
    // Helper method to format interval list
    private String formatIntervalList(java.util.List<int[]> intervals) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < intervals.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(java.util.Arrays.toString(intervals.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }
    
    // Method to demonstrate why sorting by end time works better
    public void demonstrateSortingStrategies(int[][] intervals) {
        System.out.println("\nComparing Sorting Strategies:");
        System.out.println("Original: " + 
                          java.util.Arrays.deepToString(intervals));
        
        // Sort by end time
        int[][] sortedByEnd = intervals.clone();
        java.util.Arrays.sort(sortedByEnd, 
                             (a, b) -> Integer.compare(a[1], b[1]));
        int resultEndSort = eraseOverlapIntervals(sortedByEnd);
        System.out.println("Sort by end time -> Remove: " + resultEndSort);
        
        // Sort by start time
        int[][] sortedByStart = intervals.clone();
        java.util.Arrays.sort(sortedByStart, 
                             (a, b) -> Integer.compare(a[0], b[0]));
        int resultStartSort = eraseOverlapIntervalsStartSort(sortedByStart);
        System.out.println("Sort by start time -> Remove: " + resultStartSort);
        
        System.out.println("Both should give the same optimal result!");
    }
    
    // Method to test various overlap scenarios
    public void testOverlapScenarios() {
        System.out.println("\nTesting Overlap Scenarios:");
        
        // No overlaps
        int[][] noOverlap = {{1, 2}, {3, 4}, {5, 6}};
        System.out.println("No overlaps " + 
                          java.util.Arrays.deepToString(noOverlap) + 
                          " -> Remove: " + eraseOverlapIntervals(noOverlap));
        
        // All overlapping
        int[][] allOverlap = {{1, 3}, {1, 3}, {1, 3}};
        System.out.println("All same " + 
                          java.util.Arrays.deepToString(allOverlap) + 
                          " -> Remove: " + eraseOverlapIntervals(allOverlap));
        
        // Chain of overlaps
        int[][] chain = {{1, 2}, {2, 3}, {3, 4}};
        System.out.println("Touching chain " + 
                          java.util.Arrays.deepToString(chain) + 
                          " -> Remove: " + eraseOverlapIntervals(chain));
        
        // Nested intervals
        int[][] nested = {{1, 100}, {11, 22}, {1, 11}, {2, 12}};
        System.out.println("Nested intervals " + 
                          java.util.Arrays.deepToString(nested) + 
                          " -> Remove: " + eraseOverlapIntervals(nested));
    }
    
    // Method to compare performance of different approaches
    public void compareApproaches(int[][] intervals) {
        System.out.println("\nComparing approaches for " + 
                          intervals.length + " intervals:");
        
        long start, end;
        
        // Greedy (end time sort)
        start = System.nanoTime();
        int result1 = eraseOverlapIntervals(intervals);
        end = System.nanoTime();
        System.out.println("Greedy (end sort): " + result1 + 
                          " (Time: " + (end - start) + " ns)");
        
        // Greedy (start time sort)
        start = System.nanoTime();
        int result2 = eraseOverlapIntervalsStartSort(intervals);
        end = System.nanoTime();
        System.out.println("Greedy (start sort): " + result2 + 
                          " (Time: " + (end - start) + " ns)");
        
        // Activity selection
        start = System.nanoTime();
        int result3 = eraseOverlapIntervalsActivitySelection(intervals);
        end = System.nanoTime();
        System.out.println("Activity Selection: " + result3 + 
                          " (Time: " + (end - start) + " ns)");
        
        // Dynamic Programming (for smaller arrays)
        if (intervals.length <= 100) {
            start = System.nanoTime();
            int result4 = eraseOverlapIntervalsDP(intervals);
            end = System.nanoTime();
            System.out.println("Dynamic Programming: " + result4 + 
                              " (Time: " + (end - start) + " ns)");
        }
        
        // Recursive (for very small arrays)
        if (intervals.length <= 20) {
            start = System.nanoTime();
            int result5 = eraseOverlapIntervalsRecursive(intervals);
            end = System.nanoTime();
            System.out.println("Recursive: " + result5 + 
                              " (Time: " + (end - start) + " ns)");
        }
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        int[][][] testCases = {
            {{1, 2}, {2, 4}, {1, 4}},
            {{1, 2}, {2, 4}},
            {{1, 2}, {1, 2}, {1, 2}},
            {{1, 2}, {2, 3}, {3, 4}, {1, 3}},
            {{1, 100}, {11, 22}, {1, 11}, {2, 12}},
            {{0, 2}, {1, 3}, {2, 4}, {3, 5}, {4, 6}},
            {{1, 2}},
            {{1, 2}, {3, 4}, {5, 6}},
            {{1, 3}, {2, 4}, {3, 5}, {4, 6}},
            {{-1, 0}, {0, 1}, {1, 2}}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[][] intervals = testCases[i];
            
            System.out.println("\nTest Case " + (i + 1) + ":");
            System.out.println("Input: " + 
                              java.util.Arrays.deepToString(intervals));
            
            int result1 = solution.eraseOverlapIntervals(intervals);
            int result2 = solution.eraseOverlapIntervalsStartSort(intervals);
            int result3 = solution.eraseOverlapIntervalsDP(intervals);
            int result4 = solution.eraseOverlapIntervalsActivitySelection(intervals);
            
            System.out.println("Greedy (end sort): " + result1);
            System.out.println("Greedy (start sort): " + result2);
            System.out.println("Dynamic Programming: " + result3);
            System.out.println("Activity Selection: " + result4);
            
            // Demonstrate algorithm for smaller test cases
            if (intervals.length <= 6) {
                solution.demonstrateGreedy(intervals);
                solution.demonstrateSortingStrategies(intervals);
            }
            
            // Performance comparison for larger test cases
            if (intervals.length >= 4) {
                solution.compareApproaches(intervals);
            }
            
            System.out.println("---");
        }
        
        // Test overlap scenarios
        solution.testOverlapScenarios();
    }
}
