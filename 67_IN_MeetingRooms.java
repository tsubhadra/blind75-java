//https://neetcode.io/problems/meeting-schedule?list=blind75
public class Solution {
    
    // Approach 1: Sort by start time - Most intuitive and efficient
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        // Sort meetings by start time
        java.util.Arrays.sort(intervals, 
                             (a, b) -> Integer.compare(a[0], b[0]));
        
        // Check for conflicts between consecutive meetings
        for (int i = 1; i < intervals.length; i++) {
            // If current meeting starts before previous meeting ends
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false; // Conflict detected
            }
        }
        
        return true; // No conflicts found
    }
    
    // Approach 2: Sort by start time with explicit conflict check
    public boolean canAttendMeetingsExplicit(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        // Sort by start time
        java.util.Arrays.sort(intervals, 
                             (a, b) -> Integer.compare(a[0], b[0]));
        
        // Check each pair of consecutive meetings
        for (int i = 0; i < intervals.length - 1; i++) {
            int[] currentMeeting = intervals[i];
            int[] nextMeeting = intervals[i + 1];
            
            // Check if meetings overlap
            if (isOverlapping(currentMeeting, nextMeeting)) {
                return false;
            }
        }
        
        return true;
    }
    
    // Approach 3: Event-based approach using sweep line algorithm
    public boolean canAttendMeetingsSweepLine(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        // Create events: start = +1, end = -1
        java.util.List<int[]> events = new java.util.ArrayList<>();
        
        for (int[] interval : intervals) {
            events.add(new int[]{interval[0], 1});  // Meeting starts
            events.add(new int[]{interval[1], -1}); // Meeting ends
        }
        
        // Sort events by time, with end events before start events at same time
        events.sort((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]); // End (-1) before start (+1)
        });
        
        int activeeMeetings = 0;
        
        for (int[] event : events) {
            activeeMeetings += event[1];
            
            // If more than 1 meeting active at any time
            if (activeeMeetings > 1) {
                return false;
            }
        }
        
        return true;
    }
    
    // Approach 4: Using TreeMap for automatic sorting
    public boolean canAttendMeetingsTreeMap(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        java.util.TreeMap<Integer, Integer> timeline = new java.util.TreeMap<>();
        
        // Add all start and end times
        for (int[] interval : intervals) {
            timeline.put(interval[0], 
                        timeline.getOrDefault(interval[0], 0) + 1);  // Start
            timeline.put(interval[1], 
                        timeline.getOrDefault(interval[1], 0) - 1);  // End
        }
        
        int activeMeetings = 0;
        
        for (int change : timeline.values()) {
            activeMeetings += change;
            
            if (activeMeetings > 1) {
                return false;
            }
        }
        
        return true;
    }
    
    // Approach 5: Brute force - Check all pairs (for comparison)
    public boolean canAttendMeetingsBruteForce(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        // Check every pair of meetings for overlap
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1; j < intervals.length; j++) {
                if (isOverlapping(intervals[i], intervals[j])) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    // Helper method to check if two intervals overlap
    private boolean isOverlapping(int[] interval1, int[] interval2) {
        // Two intervals overlap if one starts before the other ends
        // Note: Touching at boundary (end1 == start2) is NOT overlapping
        return interval1[0] < interval2[1] && interval2[0] < interval1[1];
    }
    
    // Method to demonstrate the algorithm step by step
    public void demonstrateAlgorithm(int[][] intervals) {
        System.out.println("\nDemonstrating Meeting Rooms Algorithm:");
        System.out.println("Original meetings: " + 
                          java.util.Arrays.deepToString(intervals));
        
        if (intervals == null || intervals.length <= 1) {
            System.out.println("Result: true (≤1 meeting)");
            return;
        }
        
        // Show sorting step
        int[][] sorted = intervals.clone();
        java.util.Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println("Sorted by start time: " + 
                          java.util.Arrays.deepToString(sorted));
        
        System.out.println("\nChecking for conflicts:");
        
        for (int i = 1; i < sorted.length; i++) {
            int[] prevMeeting = sorted[i - 1];
            int[] currMeeting = sorted[i];
            
            System.out.printf("Step %d: Compare %s and %s%n", 
                            i, 
                            java.util.Arrays.toString(prevMeeting),
                            java.util.Arrays.toString(currMeeting));
            
            if (currMeeting[0] < prevMeeting[1]) {
                System.out.printf("  CONFLICT: Meeting starts at %d " +
                                "but previous ends at %d%n", 
                                currMeeting[0], prevMeeting[1]);
                System.out.println("  Result: false");
                return;
            } else {
                System.out.printf("  OK: %d >= %d (no conflict)%n", 
                                currMeeting[0], prevMeeting[1]);
            }
        }
        
        System.out.println("\nResult: true (no conflicts found)");
    }
    
    // Method to demonstrate sweep line algorithm
    public void demonstrateSweepLine(int[][] intervals) {
        System.out.println("\nDemonstrating Sweep Line Algorithm:");
        System.out.println("Original meetings: " + 
                          java.util.Arrays.deepToString(intervals));
        
        if (intervals == null || intervals.length <= 1) {
            System.out.println("Result: true");
            return;
        }
        
        // Create events
        java.util.List<int[]> events = new java.util.ArrayList<>();
        
        for (int[] interval : intervals) {
            events.add(new int[]{interval[0], 1});  // Start
            events.add(new int[]{interval[1], -1}); // End
        }
        
        // Sort events
        events.sort((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });
        
        System.out.println("Events (time, +1=start/-1=end):");
        for (int[] event : events) {
            System.out.printf("  Time %d: %s%n", 
                            event[0], 
                            event[1] == 1 ? "START" : "END");
        }
        
        int activeMeetings = 0;
        System.out.println("\nProcessing events:");
        
        for (int[] event : events) {
            activeMeetings += event[1];
            String action = event[1] == 1 ? "START" : "END";
            
            System.out.printf("Time %d: %s -> Active meetings: %d%n", 
                            event[0], action, activeMeetings);
            
            if (activeMeetings > 1) {
                System.out.println("CONFLICT: More than 1 active meeting!");
                System.out.println("Result: false");
                return;
            }
        }
        
        System.out.println("Result: true (never more than 1 active meeting)");
    }
    
    // Method to test boundary cases
    public void testBoundaryCases() {
        System.out.println("\nTesting Boundary Cases:");
        
        // Touching meetings (should be valid)
        int[][] touching = {{0, 8}, {8, 10}};
        System.out.println("Touching meetings " + 
                          java.util.Arrays.deepToString(touching) + 
                          ": " + canAttendMeetings(touching));
        
        // Exactly overlapping (should be invalid)
        int[][] exactOverlap = {{1, 5}, {1, 5}};
        System.out.println("Exact overlap " + 
                          java.util.Arrays.deepToString(exactOverlap) + 
                          ": " + canAttendMeetings(exactOverlap));
        
        // One meeting inside another
        int[][] nested = {{1, 10}, {3, 7}};
        System.out.println("Nested meetings " + 
                          java.util.Arrays.deepToString(nested) + 
                          ": " + canAttendMeetings(nested));
        
        // Adjacent with gap
        int[][] gap = {{1, 3}, {4, 6}};
        System.out.println("Gap between meetings " + 
                          java.util.Arrays.deepToString(gap) + 
                          ": " + canAttendMeetings(gap));
        
        // Single meeting
        int[][] single = {{1, 5}};
        System.out.println("Single meeting " + 
                          java.util.Arrays.deepToString(single) + 
                          ": " + canAttendMeetings(single));
        
        // Empty array
        int[][] empty = {};
        System.out.println("Empty array: " + canAttendMeetings(empty));
    }
    
    // Method to compare performance of different approaches
    public void compareApproaches(int[][] intervals) {
        System.out.println("\nComparing approaches for " + 
                          intervals.length + " meetings:");
        
        long start, end;
        
        // Sort approach
        start = System.nanoTime();
        boolean result1 = canAttendMeetings(intervals);
        end = System.nanoTime();
        System.out.println("Sort approach: " + result1 + 
                          " (Time: " + (end - start) + " ns)");
        
        // Explicit check
        start = System.nanoTime();
        boolean result2 = canAttendMeetingsExplicit(intervals);
        end = System.nanoTime();
        System.out.println("Explicit check: " + result2 + 
                          " (Time: " + (end - start) + " ns)");
        
        // Sweep line
        start = System.nanoTime();
        boolean result3 = canAttendMeetingsSweepLine(intervals);
        end = System.nanoTime();
        System.out.println("Sweep line: " + result3 + 
                          " (Time: " + (end - start) + " ns)");
        
        // TreeMap (for smaller datasets)
        if (intervals.length <= 100) {
            start = System.nanoTime();
            boolean result4 = canAttendMeetingsTreeMap(intervals);
            end = System.nanoTime();
            System.out.println("TreeMap: " + result4 + 
                              " (Time: " + (end - start) + " ns)");
        }
        
        // Brute force (for very small datasets)
        if (intervals.length <= 20) {
            start = System.nanoTime();
            boolean result5 = canAttendMeetingsBruteForce(intervals);
            end = System.nanoTime();
            System.out.println("Brute force: " + result5 + 
                              " (Time: " + (end - start) + " ns)");
        }
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        int[][][] testCases = {
            {{0, 30}, {5, 10}, {15, 20}},
            {{5, 8}, {9, 15}},
            {{0, 8}, {8, 10}},
            {{1, 5}, {2, 6}},
            {{1, 3}, {4, 6}, {7, 9}},
            {{1, 5}},
            {},
            {{1, 2}, {3, 4}, {5, 6}, {7, 8}},
            {{1, 10}, {2, 3}, {4, 5}},
            {{0, 1}, {1, 2}, {2, 3}}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[][] intervals = testCases[i];
            
            System.out.println("\nTest Case " + (i + 1) + ":");
            System.out.println("Input: " + 
                              java.util.Arrays.deepToString(intervals));
            
            boolean result1 = solution.canAttendMeetings(intervals);
            boolean result2 = solution.canAttendMeetingsExplicit(intervals);
            boolean result3 = solution.canAttendMeetingsSweepLine(intervals);
            boolean result4 = solution.canAttendMeetingsTreeMap(intervals);
            boolean result5 = solution.canAttendMeetingsBruteForce(intervals);
            
            System.out.println("Sort approach: " + result1);
            System.out.println("Explicit check: " + result2);
            System.out.println("Sweep line: " + result3);
            System.out.println("TreeMap: " + result4);
            System.out.println("Brute force: " + result5);
            
            // Demonstrate algorithms for smaller test cases
            if (intervals.length <= 5 && intervals.length > 0) {
                solution.demonstrateAlgorithm(intervals);
                solution.demonstrateSweepLine(intervals);
            }
            
            // Performance comparison for larger test cases
            if (intervals.length >= 3) {
                solution.compareApproaches(intervals);
            }
            
            System.out.println("---");
        }
        
        // Test boundary cases
        solution.testBoundaryCases();
    }
}
