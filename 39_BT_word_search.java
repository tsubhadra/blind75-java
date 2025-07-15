//https://neetcode.io/problems/search-for-word?list=blind75
public class WordSearch {
    
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0 || word == null || word.length() == 0) {
            return false;
        }
        
        int rows = board.length;
        int cols = board[0].length;
        
        // Try starting from each cell in the board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int row, int col, int index) {
        // Base case: if we've matched the entire word
        if (index == word.length()) {
            return true;
        }
        
        // Check bounds and if current character matches
        if (row < 0 || row >= board.length || 
            col < 0 || col >= board[0].length || 
            board[row][col] != word.charAt(index)) {
            return false;
        }
        
        // Mark current cell as visited by temporarily changing it
        char temp = board[row][col];
        board[row][col] = '#'; // Mark as visited
        
        // Explore all 4 directions: up, down, left, right
        boolean found = dfs(board, word, row - 1, col, index + 1) || // up
                       dfs(board, word, row + 1, col, index + 1) || // down
                       dfs(board, word, row, col - 1, index + 1) || // left
                       dfs(board, word, row, col + 1, index + 1);   // right
        
        // Backtrack: restore the original character
        board[row][col] = temp;
        
        return found;
    }
    
    // Test method
    public static void main(String[] args) {
        WordSearch ws = new WordSearch();
        
        // Example 1
        char[][] board1 = {
            {'A', 'B', 'C', 'D'},
            {'S', 'A', 'A', 'T'},
            {'A', 'C', 'A', 'E'}
        };
        String word1 = "CAT";
        System.out.println("Example 1 - CAT: " + ws.exist(board1, word1)); // true
        
        // Example 2
        char[][] board2 = {
            {'A', 'B', 'C', 'D'},
            {'S', 'A', 'A', 'T'},
            {'A', 'C', 'A', 'E'}
        };
        String word2 = "BAT";
        System.out.println("Example 2 - BAT: " + ws.exist(board2, word2)); // false
        
        // Additional test cases
        String word3 = "ABCATED";
        System.out.println("Additional test - ABCATED: " + ws.exist(board1, word3)); // true
        
        String word4 = "ABCB";
        System.out.println("Additional test - ABCB: " + ws.exist(board1, word4)); // false
    }
}
