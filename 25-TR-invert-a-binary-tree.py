#https://neetcode.io/problems/invert-a-binary-tree?list=blind75
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

public class Solution {
    
    /**
     * Approach 1: Recursive DFS Solution
     * Time Complexity: O(n) where n is the number of nodes
     * Space Complexity: O(h) where h is the height of the tree (due to recursion stack)
     */
    public TreeNode invertTree(TreeNode root) {
        // Base case: if root is null, return null
        if (root == null) {
            return null;
        }
        
        // Swap the left and right children
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        
        // Recursively invert the left and right subtrees
        invertTree(root.left);
        invertTree(root.right);
        
        return root;
    }
    
    /**
     * Approach 2: Iterative BFS Solution using Queue
     * Time Complexity: O(n) where n is the number of nodes
     * Space Complexity: O(w) where w is the maximum width of the tree
     */
    public TreeNode invertTreeIterative(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            
            // Swap left and right children
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            
            // Add children to queue for processing
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        
        return root;
    }
    
    /**
     * Approach 3: Iterative DFS Solution using Stack
     * Time Complexity: O(n) where n is the number of nodes
     * Space Complexity: O(h) where h is the height of the tree
     */
    public TreeNode invertTreeIterativeDFS(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            
            // Swap left and right children
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            
            // Add children to stack for processing
            if (current.left != null) {
                stack.push(current.left);
            }
            if (current.right != null) {
                stack.push(current.right);
            }
        }
        
        return root;
    }
    
    // Helper method to print tree (for testing)
    public void printInorder(TreeNode root) {
        if (root != null) {
            printInorder(root.left);
            System.out.print(root.val + " ");
            printInorder(root.right);
        }
    }
    
    // Test the solution
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Create test tree: [4,2,7,1,3,6,9]
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);
        
        System.out.println("Original tree (inorder):");
        solution.printInorder(root);
        System.out.println();
        
        TreeNode inverted = solution.invertTree(root);
        
        System.out.println("Inverted tree (inorder):");
        solution.printInorder(inverted);
        System.out.println();
    }
}

import java.util.*;
