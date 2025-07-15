//https://neetcode.io/problems/implement-prefix-tree?list=blind75
class PrefixTree {
    
    // TrieNode class to represent each node in the trie
    class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;
        
        public TrieNode() {
            children = new TrieNode[26]; // For lowercase letters a-z
            isEndOfWord = false;
        }
    }
    
    private TrieNode root;
    
    /** Initialize your data structure here. */
    public PrefixTree() {
        root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Convert character to index (0-25)
            
            // If the path doesn't exist, create a new node
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            
            // Move to the next node
            current = current.children[index];
        }
        
        // Mark the end of the word
        current.isEndOfWord = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            
            // If the path doesn't exist, word is not in trie
            if (current.children[index] == null) {
                return false;
            }
            
            current = current.children[index];
        }
        
        // Word exists only if we reach a node that marks end of word
        return current.isEndOfWord;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            
            // If the path doesn't exist, no word starts with this prefix
            if (current.children[index] == null) {
                return false;
            }
            
            current = current.children[index];
        }
        
        // If we can traverse the entire prefix, it exists
        return true;
    }
    
    // Additional utility methods for debugging and testing
    
    /** Helper method to print all words in the trie (for debugging) */
    public void printAllWords() {
        printAllWords(root, new StringBuilder());
    }
    
    private void printAllWords(TrieNode node, StringBuilder prefix) {
        if (node.isEndOfWord) {
            System.out.println(prefix.toString());
        }
        
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                prefix.append((char)(i + 'a'));
                printAllWords(node.children[i], prefix);
                prefix.deleteCharAt(prefix.length() - 1); // backtrack
            }
        }
    }
    
    /** Returns the number of words in the trie */
    public int countWords() {
        return countWords(root);
    }
    
    private int countWords(TrieNode node) {
        int count = 0;
        if (node.isEndOfWord) {
            count = 1;
        }
        
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                count += countWords(node.children[i]);
            }
        }
        
        return count;
    }
    
    // Test method
    public static void main(String[] args) {
        PrefixTree prefixTree = new PrefixTree();
        
        // Example from the problem
        System.out.println("=== Problem Example ===");
        prefixTree.insert("dog");
        System.out.println("search('dog'): " + prefixTree.search("dog"));     // true
        System.out.println("search('do'): " + prefixTree.search("do"));       // false
        System.out.println("startsWith('do'): " + prefixTree.startsWith("do")); // true
        prefixTree.insert("do");
        System.out.println("search('do'): " + prefixTree.search("do"));       // true
        
        // Additional comprehensive tests
        System.out.println("\n=== Additional Tests ===");
        PrefixTree trie = new PrefixTree();
        
        // Insert some words
        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");
        trie.insert("apply");
        trie.insert("banana");
        trie.insert("band");
        trie.insert("bandana");
        
        // Test search
        System.out.println("search('app'): " + trie.search("app"));           // true
        System.out.println("search('apple'): " + trie.search("apple"));       // true
        System.out.println("search('appl'): " + trie.search("appl"));         // false
        System.out.println("search('banana'): " + trie.search("banana"));     // true
        System.out.println("search('ban'): " + trie.search("ban"));           // false
        
        // Test startsWith
        System.out.println("startsWith('app'): " + trie.startsWith("app"));   // true
        System.out.println("startsWith('ban'): " + trie.startsWith("ban"));   // true
        System.out.println("startsWith('cat'): " + trie.startsWith("cat"));   // false
        System.out.println("startsWith('application'): " + trie.startsWith("application")); // true
        
        // Test utility methods
        System.out.println("\nTotal words in trie: " + trie.countWords());
        System.out.println("\nAll words in trie:");
        trie.printAllWords();
    }
}
