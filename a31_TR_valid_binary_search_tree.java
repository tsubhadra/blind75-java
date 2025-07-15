//https://neetcode.io/problems/valid-binary-search-tree?list=blind75
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

public class a31_TR_valid_binary_search_tree {
    
    /**
     * Approach 1: Top-down recursion with min/max bounds
     * Time Complexity: O(n) where n is number of nodes
     * Space Complexity: O(h) where h is height of tree (recursion stack)
     */
    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }
    
    private boolean validate(TreeNode node, Integer min, Integer max) {
        // Empty tree is valid BST
        if (node == null) {
            return true;
        }
        
        // Check if current node violates BST property
        if ((min != null && node.val <= min) || (max != null && node.val >= max)) {
            return false;
        }
        
        // Recursively validate left and right subtrees with updated bounds
        return validate(node.left, min, node.val) && 
               validate(node.right, node.val, max);
    }


    // Test method
    public static void main(String[] args) {
        a31_TR_valid_binary_search_tree a31_TR_valid_binary_search_tree = new a31_TR_valid_binary_search_tree();
        
        // Test case 1: [2,1,3] - Valid BST
        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(3);
        
        System.out.println("Test 1 - [2,1,3]: " + a31_TR_valid_binary_search_tree.isValidBST(root1));
        // Expected: true
        
        // Test case 2: [5,1,4,null,null,3,6] - Invalid BST
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(3);
        root2.right.right = new TreeNode(6);
        
        System.out.println("Test 2 - [5,1,4,null,null,3,6]: " + a31_TR_valid_binary_search_tree.isValidBST(root2));
        // Expected: false (3 is in right subtree of 5 but 3 < 5)
        
        // Test case 3: [10,5,15,null,null,6,20] - Invalid BST
        TreeNode root3 = new TreeNode(10);
        root3.left = new TreeNode(5);
        root3.right = new TreeNode(15);
        root3.right.left = new TreeNode(6);
        root3.right.right = new TreeNode(20);
        
        System.out.println("Test 3 - [10,5,15,null,null,6,20]: " + a31_TR_valid_binary_search_tree.isValidBST(root3));
        // Expected: false (6 is in right subtree of 10 but 6 < 10)
        
        // Test case 4: [2,2,2] - Invalid BST (duplicates)
        TreeNode root4 = new TreeNode(2);
        root4.left = new TreeNode(2);
        root4.right = new TreeNode(2);
        
        System.out.println("Test 4 - [2,2,2]: " + a31_TR_valid_binary_search_tree.isValidBST(root4));
        // Expected: false (BST requires strict ordering)
        
        // Test case 5: Single node
        TreeNode root5 = new TreeNode(1);
        System.out.println("Test 5 - [1]: " + a31_TR_valid_binary_search_tree.isValidBST(root5));
        // Expected: true
        
        // Test case 6: Empty tree
        System.out.println("Test 6 - []: " + a31_TR_valid_binary_search_tree.isValidBST(null));
        // Expected: true
    }
}
