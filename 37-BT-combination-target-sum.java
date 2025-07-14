#https://neetcode.io/problems/combination-target-sum?list=blind75
import java.util.*;

/**
 * Combination Sum - Find all unique combinations that sum to target
 * Problem: Given an array of distinct integers and a target, find all unique combinations
 * where the numbers sum to target. Numbers can be used multiple times.
 */
public class CombinationSum {
    
    /**
     * Main solution using backtracking
     * Time Complexity: O(N^(T/M)) where N=array length, T=target, M=minimal value
     * Space Complexity: O(T/M) for recursion depth
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // Sort for optimization
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] candidates, int target, int start, 
                          List<Integer> current, List<List<Integer>> result) {
        // Base case: found a valid combination
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try each candidate starting from 'start' index
        for (int i = start; i < candidates.length; i++) {
            // Pruning: if current candidate > target, no need to continue
            if (candidates[i] > target) {
                break;
            }
            
            // Choose: add current candidate
            current.add(candidates[i]);
            
            // Explore: recursively find combinations with reduced target
            // Use 'i' (not i+1) because we can reuse the same number
            backtrack(candidates, target - candidates[i], i, current, result);
            
            // Backtrack: remove the last added candidate
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Alternative implementation with more explicit pruning
     */
    public List<List<Integer>> combinationSumOptimized(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void dfs(int[] candidates, int target, int index, 
                    List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > target) break; // Early termination
            
            path.add(candidates[i]);
            dfs(candidates, target - candidates[i], i, path, result);
            path.remove(path.size() - 1);
        }
    }
    
    /**
     * Test method with comprehensive examples
     */
    public static void main(String[] args) {
        CombinationSum solution = new CombinationSum();
        
        // Test Case 1: Basic example
        System.out.println("=== Test Case 1 ===");
        int[] candidates1 = {2, 3, 6, 7};
        int target1 = 7;
        List<List<Integer>> result1 = solution.combinationSum(candidates1, target1);
        System.out.println("Input: " + Arrays.toString(candidates1) + ", target: " + target1);
        System.out.println("Output: " + result1);
        // Expected: [[2,2,3], [7]]
        
        // Test Case 2: Multiple solutions
        System.out.println("\n=== Test Case 2 ===");
        int[] candidates2 = {2, 3, 5};
        int target2 = 8;
        List<List<Integer>> result2 = solution.combinationSum(candidates2, target2);
        System.out.println("Input: " + Arrays.toString(candidates2) + ", target: " + target2);
        System.out.println("Output: " + result2);
        // Expected: [[2,2,2,2], [2,3,3], [3,5]]
        
        // Test Case 3: No solution
        System.out.println("\n=== Test Case 3 ===");
        int[] candidates3 = {2};
        int target3 = 1;
        List<List<Integer>> result3 = solution.combinationSum(candidates3, target3);
        System.out.println("Input: " + Arrays.toString(candidates3) + ", target: " + target3);
        System.out.println("Output: " + result3);
        // Expected: []
        
        // Test Case 4: Single element solution
        System.out.println("\n=== Test Case 4 ===");
        int[] candidates4 = {1};
        int target4 = 1;
        List<List<Integer>> result4 = solution.combinationSum(candidates4, target4);
        System.out.println("Input: " + Arrays.toString(candidates4) + ", target: " + target4);
        System.out.println("Output: " + result4);
        // Expected: [[1]]
        
        // Test Case 5: Larger example
        System.out.println("\n=== Test Case 5 ===");
        int[] candidates5 = {2, 4, 6, 8};
        int target5 = 8;
        List<List<Integer>> result5 = solution.combinationSum(candidates5, target5);
        System.out.println("Input: " + Arrays.toString(candidates5) + ", target: " + target5);
        System.out.println("Output: " + result5);
        // Expected: [[2,2,2,2], [2,2,4], [2,6], [4,4], [8]]
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] perfCandidates = {2, 3, 5, 7, 11, 13};
        int perfTarget = 15;
        long startTime = System.nanoTime();
        List<List<Integer>> perfResult = solution.combinationSum(perfCandidates, perfTarget);
        long endTime = System.nanoTime();
        
        System.out.println("Input: " + Arrays.toString(perfCandidates) + ", target: " + perfTarget);
        System.out.println("Found " + perfResult.size() + " combinations");
        System.out.println("Time: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("Sample results: " + perfResult.subList(0, Math.min(5, perfResult.size())));
    }
}

/**
 * Related problems and variations
 */
class CombinationSumVariations {
    
    /**
     * Combination Sum II - Each number can be used only once
     * Input array may contain duplicates
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack2(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack2(int[] candidates, int target, int start,
                           List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target) break;
            
            // Skip duplicates at the same level
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            
            current.add(candidates[i]);
            // Use i+1 because each number can only be used once
            backtrack2(candidates, target - candidates[i], i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Combination Sum III - Find k numbers that add up to n
     * Use numbers 1-9 only, each number used at most once
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack3(k, n, 1, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack3(int k, int target, int start,
                           List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k && target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        if (current.size() >= k || target <= 0) {
            return; // Pruning
        }
        
        for (int i = start; i <= 9; i++) {
            if (i > target) break;
            
            current.add(i);
            backtrack3(k, target - i, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Generate all possible combinations of k numbers from 1 to n
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombine(n, k, 1, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrackCombine(int n, int k, int start,
                                 List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Pruning: if remaining slots > remaining numbers, impossible
        int remaining = k - current.size();
        for (int i = start; i <= n - remaining + 1; i++) {
            current.add(i);
            backtrackCombine(n, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    public static void main(String[] args) {
        CombinationSumVariations variations = new CombinationSumVariations();
        
        // Test Combination Sum II
        System.out.println("=== Combination Sum II ===");
        int[] candidates2 = {10, 1, 2, 7, 6, 1, 5};
        int target2 = 8;
        List<List<Integer>> result2 = variations.combinationSum2(candidates2, target2);
        System.out.println("Input: " + Arrays.toString(candidates2) + ", target: " + target2);
        System.out.println("Output: " + result2);
        
        // Test Combination Sum III
        System.out.println("\n=== Combination Sum III ===");
        int k3 = 3, n3 = 7;
        List<List<Integer>> result3 = variations.combinationSum3(k3, n3);
        System.out.println("k=" + k3 + ", n=" + n3);
        System.out.println("Output: " + result3);
        
        // Test Combinations
        System.out.println("\n=== Combinations ===");
        int n4 = 4, k4 = 2;
        List<List<Integer>> result4 = variations.combine(n4, k4);
        System.out.println("n=" + n4 + ", k=" + k4);
        System.out.println("Output: " + result4);
    }
}
