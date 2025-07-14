#https://neetcode.io/problems/valid-binary-search-tree?list=blind75
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

public class Solution {
    
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
    
    /**
     * Approach 2: In-order traversal (should be sorted for valid BST)
     * Time Complexity: O(n)
     * Space Complexity: O(h) for recursion stack
     */
    private TreeNode prev = null;
    
    public boolean isValidBST2(TreeNode root) {
        prev = null; // Reset for each test
        return inorderCheck(root);
    }
    
    private boolean inorderCheck(TreeNode node) {
        if (node == null) {
            return true;
        }
        
        // Check left subtree
        if (!inorderCheck(node.left)) {
            return false;
        }
        
        // Check current node against previous node in inorder traversal
        if (prev != null && node.val <= prev.val) {
            return false;
        }
        prev = node;
        
        // Check right subtree
        return inorderCheck(node.right);
    }
    
    /**
     * Approach 3: In-order traversal with list (easier to understand)
     * Time Complexity: O(n)
     * Space Complexity: O(n) for storing all values
     */
    public boolean isValidBST3(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        
        // Check if inorder traversal is strictly increasing
        for (int i = 1; i < inorder.size(); i++) {
            if (inorder.get(i) <= inorder.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
    
    private void inorderTraversal(TreeNode node, List<Integer> inorder) {
        if (node == null) {
            return;
        }
        
        inorderTraversal(node.left, inorder);
        inorder.add(node.val);
        inorderTraversal(node.right, inorder);
    }
    
    /**
     * Approach 4: Iterative in-order traversal with stack
     * Time Complexity: O(n)
     * Space Complexity: O(h) for stack
     */
    public boolean isValidBST4(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        Integer prev = null;
        
        while (!stack.isEmpty() || root != null) {
            // Go to leftmost node
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            
            // Process current node
            root = stack.pop();
            
            // Check BST property
            if (prev != null && root.val <= prev) {
                return false;
            }
            prev = root.val;
            
            // Move to right subtree
            root = root.right;
        }
        
        return true;
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: [2,1,3] - Valid BST
        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(3);
        
        System.out.println("Test 1 - [2,1,3]: " + solution.isValidBST(root1));
        // Expected: true
        
        // Test case 2: [5,1,4,null,null,3,6] - Invalid BST
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(3);
        root2.right.right = new TreeNode(6);
        
        System.out.println("Test 2 - [5,1,4,null,null,3,6]: " + solution.isValidBST(root2));
        // Expected: false (3 is in right subtree of 5 but 3 < 5)
        
        // Test case 3: [10,5,15,null,null,6,20] - Invalid BST
        TreeNode root3 = new TreeNode(10);
        root3.left = new TreeNode(5);
        root3.right = new TreeNode(15);
        root3.right.left = new TreeNode(6);
        root3.right.right = new TreeNode(20);
        
        System.out.println("Test 3 - [10,5,15,null,null,6,20]: " + solution.isValidBST(root3));
        // Expected: false (6 is in right subtree of 10 but 6 < 10)
        
        // Test case 4: [2,2,2] - Invalid BST (duplicates)
        TreeNode root4 = new TreeNode(2);
        root4.left = new TreeNode(2);
        root4.right = new TreeNode(2);
        
        System.out.println("Test 4 - [2,2,2]: " + solution.isValidBST(root4));
        // Expected: false (BST requires strict ordering)
        
        // Test case 5: Single node
        TreeNode root5 = new TreeNode(1);
        System.out.println("Test 5 - [1]: " + solution.isValidBST(root5));
        // Expected: true
        
        // Test case 6: Empty tree
        System.out.println("Test 6 - []: " + solution.isValidBST(null));
        // Expected: true
        
        // Test different approaches
        System.out.println("\nTesting different approaches:");
        System.out.println("Approach 2 (inorder): " + solution.isValidBST2(root1));
        System.out.println("Approach 3 (inorder list): " + solution.isValidBST3(root1));
        System.out.println("Approach 4 (iterative): " + solution.isValidBST4(root1));
    }
}
