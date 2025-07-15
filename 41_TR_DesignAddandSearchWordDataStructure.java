//https://neetcode.io/problems/design-word-search-data-structure?list=blind75

class WordDictionary {
    
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
    public WordDictionary() {
        root = new TrieNode();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
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
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return searchHelper(word, 0, root);
    }
    
    /** Helper method for recursive search with wildcard support */
    private boolean searchHelper(String word, int index, TrieNode node) {
        // Base case: if we've processed all characters
        if (index == word.length()) {
            return node.isEndOfWord;
        }
        
        char c = word.charAt(index);
        
        if (c == '.') {
            // Wildcard: try all possible children
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    if (searchHelper(word, index + 1, node.children[i])) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            // Regular character: follow the specific path
            int charIndex = c - 'a';
            if (node.children[charIndex] == null) {
                return false;
            }
            return searchHelper(word, index + 1, node.children[charIndex]);
        }
    }
    
    // Additional utility methods for debugging and testing
    
    /** Helper method to print all words in the dictionary (for debugging) */
    public void printAllWords() {
        System.out.println("All words in dictionary:");
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
    
    /** Returns the number of words in the dictionary */
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
    
    /** Returns all words that match a given pattern (for debugging) */
    public java.util.List<String> findMatches(String pattern) {
        java.util.List<String> matches = new java.util.ArrayList<>();
        findMatches(pattern, 0, root, new StringBuilder(), matches);
        return matches;
    }
    
    private void findMatches(String pattern, int index, TrieNode node, StringBuilder current, java.util.List<String> matches) {
        if (index == pattern.length()) {
            if (node.isEndOfWord) {
                matches.add(current.toString());
            }
            return;
        }
        
        char c = pattern.charAt(index);
        
        if (c == '.') {
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    current.append((char)(i + 'a'));
                    findMatches(pattern, index + 1, node.children[i], current, matches);
                    current.deleteCharAt(current.length() - 1);
                }
            }
        } else {
            int charIndex = c - 'a';
            if (node.children[charIndex] != null) {
                current.append(c);
                findMatches(pattern, index + 1, node.children[charIndex], current, matches);
                current.deleteCharAt(current.length() - 1);
            }
        }
    }
    
    // Test method
    public static void main(String[] args) {
        WordDictionary wordDictionary = new WordDictionary();
        
        // Example from the problem
        System.out.println("=== Problem Example ===");
        wordDictionary.addWord("day");
        wordDictionary.addWord("bay");
        wordDictionary.addWord("may");
        
        System.out.println("search('say'): " + wordDictionary.search("say")); // false
        System.out.println("search('day'): " + wordDictionary.search("day")); // true
        System.out.println("search('.ay'): " + wordDictionary.search(".ay")); // true
        System.out.println("search('b..'): " + wordDictionary.search("b..")); // true
        
        // Additional comprehensive tests
        System.out.println("\n=== Additional Tests ===");
        WordDictionary dict = new WordDictionary();
        
        // Add more words
        dict.addWord("bad");
        dict.addWord("dad");
        dict.addWord("mad");
        dict.addWord("pad");
        dict.addWord("cat");
        dict.addWord("bat");
        dict.addWord("rat");
        dict.addWord("apple");
        dict.addWord("application");
        
        // Test various patterns
        System.out.println("search('bad'): " + dict.search("bad"));       // true
        System.out.println("search('.ad'): " + dict.search(".ad"));       // true
        System.out.println("search('b.d'): " + dict.search("b.d"));       // true
        System.out.println("search('..d'): " + dict.search("..d"));       // true
        System.out.println("search('b..'): " + dict.search("b.."));       // true
        System.out.println("search('....'): " + dict.search("...."));     // false (no 4-letter words ending with .)\n        System.out.println("search('.at'): " + dict.search(".at"));       // true
        System.out.println("search('c.t'): " + dict.search("c.t"));       // true
        System.out.println("search('..t'): " + dict.search("..t"));       // true
        System.out.println("search('app..'): " + dict.search("app.."));   // false (apple is 5 letters, not matching app..)\n        System.out.println("search('appl.'): " + dict.search("appl."));   // true
        System.out.println("search('xyz'): " + dict.search("xyz"));       // false
        System.out.println("search('...'): " + dict.search("..."));       // true
        
        // Test utility methods
        System.out.println("\nTotal words in dictionary: " + dict.countWords());
        
        System.out.println("\nAll words matching '.ad':");
        java.util.List<String> matches = dict.findMatches(".ad");
        for (String match : matches) {
            System.out.println("  " + match);
        }
        
        System.out.println("\nAll words matching '.at':");
        matches = dict.findMatches(".at");
        for (String match : matches) {
            System.out.println("  " + match);
        }
        
        // Edge cases
        System.out.println("\n=== Edge Cases ===");
        WordDictionary edgeDict = new WordDictionary();
        edgeDict.addWord("a");
        edgeDict.addWord("aa");
        edgeDict.addWord("aaa");
        
        System.out.println("search('.'): " + edgeDict.search("."));       // true
        System.out.println("search('..'): " + edgeDict.search(".."));     // true
        System.out.println("search('...'): " + edgeDict.search("..."));   // true
        System.out.println("search('....'): " + edgeDict.search("...."));// false
    }
}
