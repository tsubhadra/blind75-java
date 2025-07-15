//https://neetcode.io/problems/search-for-word-ii?list=blind75
import java.util.*;

public class WordSearchII {
    
    // TrieNode class for building the trie
    class TrieNode {
        TrieNode[] children;
        String word; // Store the complete word at the end node
        
        public TrieNode() {
            children = new TrieNode[26];
            word = null;
        }
    }
    
    private TrieNode root;
    private List<String> result;
    private int rows, cols;
    
    public List<String> findWords(char[][] board, String[] words) {
        result = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.length == 0) {
            return result;
        }
        
        rows = board.length;
        cols = board[0].length;
        
        // Build trie from all words
        buildTrie(words);
        
        // Search for words starting from each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfs(board, i, j, root);
            }
        }
        
        return result;
    }
    
    private void buildTrie(String[] words) {
        root = new TrieNode();
        
        for (String word : words) {
            TrieNode current = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }
                current = current.children[index];
            }
            current.word = word; // Store the complete word
        }
    }
    
    private void dfs(char[][] board, int row, int col, TrieNode node) {
        // Check bounds
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return;
        }
        
        char c = board[row][col];
        
        // Check if character was already visited or doesn't exist in trie
        if (c == '#' || node.children[c - 'a'] == null) {
            return;
        }
        
        // Move to the next node in trie
        node = node.children[c - 'a'];
        
        // If we found a complete word, add it to result
        if (node.word != null) {
            result.add(node.word);
            node.word = null; // Avoid duplicate results
        }
        
        // Mark current cell as visited
        board[row][col] = '#';
        
        // Explore all 4 directions
        dfs(board, row - 1, col, node); // up
        dfs(board, row + 1, col, node); // down
        dfs(board, row, col - 1, node); // left
        dfs(board, row, col + 1, node); // right
        
        // Backtrack: restore the original character
        board[row][col] = c;
    }
    
    // Optimized version with trie pruning
    public List<String> findWordsOptimized(char[][] board, String[] words) {
        result = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.length == 0) {
            return result;
        }
        
        rows = board.length;
        cols = board[0].length;
        
        // Build trie from all words
        buildTrie(words);
        
        // Search for words starting from each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfsOptimized(board, i, j, root);
            }
        }
        
        return result;
    }
    
    private void dfsOptimized(char[][] board, int row, int col, TrieNode node) {
        // Check bounds
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return;
        }
        
        char c = board[row][col];
        
        // Check if character was already visited or doesn't exist in trie
        if (c == '#' || node.children[c - 'a'] == null) {
            return;
        }
        
        // Move to the next node in trie
        node = node.children[c - 'a'];
        
        // If we found a complete word, add it to result
        if (node.word != null) {
            result.add(node.word);
            node.word = null; // Avoid duplicate results
        }
        
        // Mark current cell as visited
        board[row][col] = '#';
        
        // Explore all 4 directions
        dfsOptimized(board, row - 1, col, node); // up
        dfsOptimized(board, row + 1, col, node); // down
        dfsOptimized(board, row, col - 1, node); // left
        dfsOptimized(board, row, col + 1, node); // right
        
        // Backtrack: restore the original character
        board[row][col] = c;
        
        // Optimization: Remove leaf nodes to prune the trie
        if (node.word == null && hasNoChildren(node)) {
            node = null; // This helps with garbage collection
        }
    }
    
    private boolean hasNoChildren(TrieNode node) {
        for (TrieNode child : node.children) {
            if (child != null) {
                return false;
            }
        }
        return true;
    }
    
    // Helper method to print trie structure (for debugging)
    public void printTrie() {
        System.out.println("Trie structure:");
        printTrie(root, "");
    }
    
    private void printTrie(TrieNode node, String prefix) {
        if (node.word != null) {
            System.out.println(prefix + " -> " + node.word);
        }
        
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                printTrie(node.children[i], prefix + (char)(i + 'a'));
            }
        }
    }
    
    // Test method
    public static void main(String[] args) {
        WordSearchII ws = new WordSearchII();
        
        // Example 1
        System.out.println("=== Example 1 ===");
        char[][] board1 = {
            {'a', 'b', 'c', 'd'},
            {'s', 'a', 'a', 't'},
            {'a', 'c', 'k', 'e'},
            {'a', 'c', 'd', 'n'}
        };
        String[] words1 = {"bat", "cat", "back", "backend", "stack"};
        
        List<String> result1 = ws.findWords(board1, words1);
        System.out.println("Found words: " + result1);
        // Expected: ["cat", "back", "backend"]
        
        // Example 2
        System.out.println("\n=== Example 2 ===");
        char[][] board2 = {
            {'x', 'o'},
            {'x', 'o'}
        };
        String[] words2 = {"xoxo"};
        
        List<String> result2 = ws.findWords(board2, words2);
        System.out.println("Found words: " + result2);
        // Expected: []
        
        // Additional test case
        System.out.println("\n=== Additional Test ===");
        char[][] board3 = {
            {'o', 'a', 'a', 'n'},
            {'e', 't', 'a', 'e'},
            {'i', 'h', 'k', 'r'},
            {'i', 'f', 'l', 'v'}
        };
        String[] words3 = {"oath", "pea", "eat", "rain", "hike", "fake"};
        
        List<String> result3 = ws.findWords(board3, words3);
        System.out.println("Found words: " + result3);
        // Expected: ["oath", "eat", "hike"]
        
        // Performance test with optimized version
        System.out.println("\n=== Performance Test (Optimized) ===");
        long startTime = System.currentTimeMillis();
        List<String> result4 = ws.findWordsOptimized(board3, words3);
        long endTime = System.currentTimeMillis();
        System.out.println("Optimized result: " + result4);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
        // Test with large word list
        System.out.println("\n=== Stress Test ===");
        String[] largeWordList = {
            "a", "aa", "aaa", "oath", "pea", "eat", "rain", "hike", "fake",
            "oat", "tea", "ate", "hat", "the", "kit", "kite", "take", "fake",
            "hi", "he", "hit", "her", "here", "tire", "fire", "life", "live"
        };
        
        startTime = System.currentTimeMillis();
        List<String> stressResult = ws.findWordsOptimized(board3, largeWordList);
        endTime = System.currentTimeMillis();
        System.out.println("Stress test result: " + stressResult);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}
