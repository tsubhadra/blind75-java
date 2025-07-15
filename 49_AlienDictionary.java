//https://neetcode.io/problems/foreign-dictionary?list=blind75
import java.util.*;

public class AlienDictionary {
    
    // Solution 1: Kahn's Algorithm (BFS Topological Sort)
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        
        // Step 1: Find all unique characters and initialize in-degree
        Map<Character, Integer> inDegree = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                inDegree.put(c, 0);
            }
        }
        
        // Step 2: Build graph by comparing adjacent words
        Map<Character, Set<Character>> graph = new HashMap<>();
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            
            // Check for invalid case: longer word is prefix of shorter word
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return ""; // Invalid order
            }
            
            // Find first differing character
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                char c1 = word1.charAt(j);
                char c2 = word2.charAt(j);
                
                if (c1 != c2) {
                    // c1 should come before c2
                    if (!graph.containsKey(c1)) {
                        graph.put(c1, new HashSet<>());
                    }
                    
                    // Only add edge if it doesn't already exist
                    if (!graph.get(c1).contains(c2)) {
                        graph.get(c1).add(c2);
                        inDegree.put(c2, inDegree.get(c2) + 1);
                    }
                    
                    break; // Only consider first difference
                }
            }
        }
        
        // Step 3: Topological sort using Kahn's algorithm
        Queue<Character> queue = new LinkedList<>();
        
        // Add all characters with in-degree 0
        for (char c : inDegree.keySet()) {
            if (inDegree.get(c) == 0) {
                queue.offer(c);
            }
        }
        
        StringBuilder result = new StringBuilder();
        
        while (!queue.isEmpty()) {
            char current = queue.poll();
            result.append(current);
            
            // Reduce in-degree of neighbors
            if (graph.containsKey(current)) {
                for (char neighbor : graph.get(current)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }
        
        // Check if all characters are included (no cycle)
        return result.length() == inDegree.size() ? result.toString() : "";
    }
    
    // Solution 2: DFS with Cycle Detection
    public String alienOrderDFS(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        
        // Build graph
        Map<Character, Set<Character>> graph = new HashMap<>();
        Set<Character> allChars = new HashSet<>();
        
        // Initialize all characters
        for (String word : words) {
            for (char c : word.toCharArray()) {
                allChars.add(c);
                graph.put(c, new HashSet<>());
            }
        }
        
        // Build edges
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            
            // Check for invalid case
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            
            // Find first differing character
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                char c1 = word1.charAt(j);
                char c2 = word2.charAt(j);
                
                if (c1 != c2) {
                    graph.get(c1).add(c2);
                    break;
                }
            }
        }
        
        // DFS with cycle detection
        Map<Character, Integer> state = new HashMap<>(); // 0: unvisited, 1: visiting, 2: visited
        Stack<Character> result = new Stack<>();
        
        for (char c : allChars) {
            if (state.getOrDefault(c, 0) == 0) {
                if (hasCycleDFS(graph, state, c, result)) {
                    return ""; // Cycle detected
                }
            }
        }
        
        // Build result string in reverse order
        StringBuilder sb = new StringBuilder();
        while (!result.isEmpty()) {
            sb.append(result.pop());
        }
        
        return sb.toString();
    }
    
    private boolean hasCycleDFS(Map<Character, Set<Character>> graph, Map<Character, Integer> state, 
                               char node, Stack<Character> result) {
        state.put(node, 1); // Mark as visiting
        
        for (char neighbor : graph.get(node)) {
            if (state.getOrDefault(neighbor, 0) == 1) {
                return true; // Back edge found - cycle
            }
            if (state.getOrDefault(neighbor, 0) == 0 && 
                hasCycleDFS(graph, state, neighbor, result)) {
                return true;
            }
        }
        
        state.put(node, 2); // Mark as visited
        result.push(node); // Add to result in post-order
        return false;
    }
    
    // Solution 3: Enhanced with detailed debugging
    public String alienOrderDetailed(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        
        System.out.println("Input words: " + Arrays.toString(words));
        
        // Step 1: Collect all characters
        Set<Character> allChars = new HashSet<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                allChars.add(c);
            }
        }
        System.out.println("All characters: " + allChars);
        
        // Step 2: Build graph
        Map<Character, Set<Character>> graph = new HashMap<>();
        Map<Character, Integer> inDegree = new HashMap<>();
        
        for (char c : allChars) {
            graph.put(c, new HashSet<>());
            inDegree.put(c, 0);
        }
        
        // Compare adjacent words
        List<String> constraints = new ArrayList<>();
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            
            // Check for invalid case
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                System.out.println("Invalid: \"" + word1 + "\" cannot come before \"" + word2 + "\"");
                return "";
            }
            
            // Find first difference
            boolean foundDiff = false;
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                char c1 = word1.charAt(j);
                char c2 = word2.charAt(j);
                
                if (c1 != c2) {
                    if (!graph.get(c1).contains(c2)) {
                        graph.get(c1).add(c2);
                        inDegree.put(c2, inDegree.get(c2) + 1);
                        constraints.add("'" + c1 + "' < '" + c2 + "' (from \"" + word1 + "\" vs \"" + word2 + "\")");
                    }
                    foundDiff = true;
                    break;
                }
            }
        }
        
        System.out.println("Constraints found:");
        for (String constraint : constraints) {
            System.out.println("  " + constraint);
        }
        
        // Step 3: Topological sort
        Queue<Character> queue = new LinkedList<>();
        for (char c : inDegree.keySet()) {
            if (inDegree.get(c) == 0) {
                queue.offer(c);
            }
        }
        
        System.out.println("Starting characters (in-degree 0): " + queue);
        
        StringBuilder result = new StringBuilder();
        List<String> processOrder = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            char current = queue.poll();
            result.append(current);
            processOrder.add("Process '" + current + "'");
            
            if (graph.containsKey(current)) {
                for (char neighbor : graph.get(current)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                        processOrder.add("  Added '" + neighbor + "' to queue");
                    }
                }
            }
        }
        
        System.out.println("Processing order:");
        for (String step : processOrder) {
            System.out.println("  " + step);
        }
        
        String finalResult = result.length() == allChars.size() ? result.toString() : "";
        System.out.println("Final result: " + (finalResult.isEmpty() ? "INVALID (cycle detected)" : finalResult));
        
        return finalResult;
    }
    
    // Utility method to validate the solution
    public boolean isValidOrder(String[] words, String order) {
        if (order.isEmpty()) return false;
        
        // Create character to index mapping
        Map<Character, Integer> charIndex = new HashMap<>();
        for (int i = 0; i < order.length(); i++) {
            charIndex.put(order.charAt(i), i);
        }
        
        // Check if all characters in words are in the order
        for (String word : words) {
            for (char c : word.toCharArray()) {
                if (!charIndex.containsKey(c)) {
                    return false;
                }
            }
        }
        
        // Check if words are sorted according to this order
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            
            if (!isLexicographicallySmaller(word1, word2, charIndex)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isLexicographicallySmaller(String word1, String word2, Map<Character, Integer> charIndex) {
        int minLen = Math.min(word1.length(), word2.length());
        
        for (int i = 0; i < minLen; i++) {
            char c1 = word1.charAt(i);
            char c2 = word2.charAt(i);
            
            int index1 = charIndex.get(c1);
            int index2 = charIndex.get(c2);
            
            if (index1 < index2) {
                return true;
            } else if (index1 > index2) {
                return false;
            }
        }
        
        // If one is prefix of another, shorter should come first
        return word1.length() <= word2.length();
    }
    
    // Test method
    public static void main(String[] args) {
        AlienDictionary solution = new AlienDictionary();
        
        // Example 1: Simple case
        System.out.println("=== Example 1: Simple Case ===");
        String[] words1 = {"z", "o"};
        String result1 = solution.alienOrderDetailed(words1);
        System.out.println("Validation: " + solution.isValidOrder(words1, result1));
        System.out.println();
        
        // Example 2: Complex case
        System.out.println("=== Example 2: Complex Case ===");
        String[] words2 = {"hrn", "hrf", "er", "enn", "rfnn"};
        String result2 = solution.alienOrderDetailed(words2);
        System.out.println("Validation: " + solution.isValidOrder(words2, result2));
        System.out.println();
        
        // Example 3: Invalid case (cycle)
        System.out.println("=== Example 3: Invalid Case (Cycle) ===");
        String[] words3 = {"a", "b", "a"};
        String result3 = solution.alienOrderDetailed(words3);
        System.out.println();
        
        // Example 4: Invalid case (longer prefix)
        System.out.println("=== Example 4: Invalid Case (Prefix) ===");
        String[] words4 = {"abc", "ab"};
        String result4 = solution.alienOrderDetailed(words4);
        System.out.println();
        
        // Example 5: Single character words
        System.out.println("=== Example 5: Single Characters ===");
        String[] words5 = {"a", "b", "c", "d"};
        String result5 = solution.alienOrderDetailed(words5);
        System.out.println("Validation: " + solution.isValidOrder(words5, result5));
        System.out.println();
        
        // Example 6: Same characters, different lengths
        System.out.println("=== Example 6: Length Differences ===");
        String[] words6 = {"a", "aa", "aaa"};
        String result6 = solution.alienOrderDetailed(words6);
        System.out.println("Validation: " + solution.isValidOrder(words6, result6));
        System.out.println();
        
        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        String[] words7 = {"hrn", "hrf", "er", "enn", "rfnn"};
        
        long startTime = System.nanoTime();
        String resultKahn = solution.alienOrder(words7);
        long kahnTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String resultDFS = solution.alienOrderDFS(words7);
        long dfsTime = System.nanoTime() - startTime;
        
        System.out.println("Kahn's Algorithm: " + resultKahn + " (" + kahnTime + " ns)");
        System.out.println("DFS Algorithm: " + resultDFS + " (" + dfsTime + " ns)");
        System.out.println("Both valid: " + solution.isValidOrder(words7, resultKahn) + ", " + solution.isValidOrder(words7, resultDFS));
        
        // Edge cases
        System.out.println("\\n=== Edge Cases ===");
        
        // Empty input
        String[] empty = {};
        System.out.println("Empty input: \"" + solution.alienOrder(empty) + "\"");
        
        // Single word
        String[] single = {"abc"};
        System.out.println("Single word: \"" + solution.alienOrder(single) + "\"");
        
        // All same characters
        String[] same = {"aaa", "aa", "a"};
        System.out.println("Same characters: \"" + solution.alienOrder(same) + "\"");
    }
}
