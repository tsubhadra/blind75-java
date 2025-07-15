//https://neetcode.io/problems/level-order-traversal-of-binary-tree?list=blind75
import java.util.*;

// Definition for a binary tree node
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class a30_TR_level_order_traversal_of_binary_tree {
    

    /**
     * Alternative DFS approach using recursion
     * Time Complexity: O(n)
     * Space Complexity: O(h) where h is the height of the tree
     */
    public List<List<Integer>> levelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        dfs(root, 0, result);
        return result;
    }
    
    private void dfs(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) {
            return;
        }
        
        // If this is the first node at this level, create new list
        if (level == result.size()) {
            result.add(new ArrayList<>());
        }
        
        // Add current node to its level
        result.get(level).add(node.val);
        
        // Recursively process left and right subtrees
        dfs(node.left, level + 1, result);
        dfs(node.right, level + 1, result);
    }
    
    // Test method
    public static void main(String[] args) {
        a30_TR_level_order_traversal_of_binary_tree a30_TR_level_order_traversal_of_binary_tree = new a30_TR_level_order_traversal_of_binary_tree();
        
        // Test case 1: [3,9,20,null,null,15,7]
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        
        System.out.println("Test 1 - DFS: " + a30_TR_level_order_traversal_of_binary_tree.levelOrderDFS(root1));
        // Expected: [[3], [9, 20], [15, 7]]
        
        // Test case 2: [1]
        TreeNode root2 = new TreeNode(1);
        System.out.println("Test 2 - DFS: " + a30_TR_level_order_traversal_of_binary_tree.levelOrderDFS(root2));
        // Expected: [[1]]
        
        // Test case 3: []
        System.out.println("Test 3 - DFS: " + a30_TR_level_order_traversal_of_binary_tree.levelOrderDFS(null));
        // Expected: []
        
        // Test DFS approach
        TreeNode root4 = new TreeNode(3);
        root4.left = new TreeNode(9);
        root4.right = new TreeNode(20);
        root4.right.left = new TreeNode(15);
        root4.right.right = new TreeNode(7);
        
        System.out.println("Test 1 - DFS: " + a30_TR_level_order_traversal_of_binary_tree.levelOrderDFS(root4));
        // Expected: [[3], [9, 20], [15, 7]]
    }
}
