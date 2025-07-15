import java.util.*;
//https://neetcode.io/problems/depth-of-binary-tree?list=blind75
/**
 * Definition for a binary tree node.
 */
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

public class a26_TR_depth_of_binary_tree {
    
    /**
     * Approach 1: Recursive DFS a26_TR_depth_of_binary_tree (Most Common)
     * Time Complexity: O(n) where n is the number of nodes
     * Space Complexity: O(h) where h is the height of the tree (recursion stack)
     */
    public int maxDepth(TreeNode root) {
        // Base case: empty tree has depth 0
        if (root == null) {
            return 0;
        }
        
        // Recursively find depth of left and right subtrees
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        // Return 1 + maximum of left and right depths
        return 1 + Math.max(leftDepth, rightDepth);
    }

    
    // Helper method to build a test tree
    public static TreeNode buildTestTree() {
        // Build tree: [3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        return root;
    }
    
    // Test the a26_TR_depth_of_binary_tree
    public static void main(String[] args) {
        a26_TR_depth_of_binary_tree a26_TR_depth_of_binary_tree = new a26_TR_depth_of_binary_tree();
        
        // Test case 1: [3,9,20,null,null,15,7] -> Expected: 3
        TreeNode root1 = buildTestTree();
        System.out.println("Test 1 - Recursive: " + a26_TR_depth_of_binary_tree.maxDepth(root1));
        
        // Test case 2: [1,null,2] -> Expected: 2
        TreeNode root2 = new TreeNode(1);
        root2.right = new TreeNode(2);
        System.out.println("Test 2 - Recursive: " + a26_TR_depth_of_binary_tree.maxDepth(root2));
        
        // Test case 3: [] -> Expected: 0
        System.out.println("Test 3 - Empty tree: " + a26_TR_depth_of_binary_tree.maxDepth(null));
        
        // Test case 4: [0] -> Expected: 1
        TreeNode root4 = new TreeNode(0);
        System.out.println("Test 4 - Single node: " + a26_TR_depth_of_binary_tree.maxDepth(root4));
    }
}


