#https://neetcode.io/problems/depth-of-binary-tree?list=blind75
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
     * Approach 1: Recursive DFS Solution (Most Common)
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
    
    /**
     * Approach 2: Iterative BFS Solution using Queue
     * Time Complexity: O(n) where n is the number of nodes
     * Space Complexity: O(w) where w is the maximum width of the tree
     */
    public int maxDepthIterativeBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            depth++; // Increment depth for each level
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                
                // Add children to queue for next level
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
        }
        
        return depth;
    }
    
    /**
     * Approach 3: Iterative DFS Solution using Stack
     * Time Complexity: O(n) where n is the number of nodes
     * Space Complexity: O(h) where h is the height of the tree
     */
    public int maxDepthIterativeDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        
        nodeStack.push(root);
        depthStack.push(1);
        
        int maxDepth = 0;
        
        while (!nodeStack.isEmpty()) {
            TreeNode current = nodeStack.pop();
            int currentDepth = depthStack.pop();
            
            // Update maximum depth
            maxDepth = Math.max(maxDepth, currentDepth);
            
            // Add children with incremented depth
            if (current.left != null) {
                nodeStack.push(current.left);
                depthStack.push(currentDepth + 1);
            }
            if (current.right != null) {
                nodeStack.push(current.right);
                depthStack.push(currentDepth + 1);
            }
        }
        
        return maxDepth;
    }
    
    /**
     * Approach 4: One-liner recursive solution
     * Most concise but same complexity as approach 1
     */
    public int maxDepthOneLiner(TreeNode root) {
        return root == null ? 0 : 1 + Math.max(maxDepthOneLiner(root.left), maxDepthOneLiner(root.right));
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
    
    // Test the solution
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: [3,9,20,null,null,15,7] -> Expected: 3
        TreeNode root1 = buildTestTree();
        System.out.println("Test 1 - Recursive: " + solution.maxDepth(root1));
        System.out.println("Test 1 - BFS: " + solution.maxDepthIterativeBFS(root1));
        System.out.println("Test 1 - DFS: " + solution.maxDepthIterativeDFS(root1));
        System.out.println("Test 1 - One-liner: " + solution.maxDepthOneLiner(root1));
        
        // Test case 2: [1,null,2] -> Expected: 2
        TreeNode root2 = new TreeNode(1);
        root2.right = new TreeNode(2);
        System.out.println("Test 2 - Recursive: " + solution.maxDepth(root2));
        
        // Test case 3: [] -> Expected: 0
        System.out.println("Test 3 - Empty tree: " + solution.maxDepth(null));
        
        // Test case 4: [0] -> Expected: 1
        TreeNode root4 = new TreeNode(0);
        System.out.println("Test 4 - Single node: " + solution.maxDepth(root4));
    }
}

import java.util.*;
