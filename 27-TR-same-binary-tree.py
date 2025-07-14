#https://neetcode.io/problems/same-binary-tree?list=blind75
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
     * Approach 1: Recursive DFS Solution (Most Popular)
     * Time Complexity: O(min(m,n)) where m and n are the number of nodes in each tree
     * Space Complexity: O(min(m,n)) due to recursion stack in worst case
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Base case: both trees are empty
        if (p == null && q == null) {
            return true;
        }
        
        // One tree is empty, the other is not
        if (p == null || q == null) {
            return false;
        }
        
        // Check if current nodes have same value and recursively check subtrees
        return (p.val == q.val) && 
               isSameTree(p.left, q.left) && 
               isSameTree(p.right, q.right);
    }
    
    /**
     * Approach 2: Iterative BFS Solution using Queue
     * Time Complexity: O(min(m,n)) where m and n are the number of nodes in each tree
     * Space Complexity: O(min(m,n)) for the queue
     */
    public boolean isSameTreeIterativeBFS(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(p);
        queue.offer(q);
        
        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();
            
            // Both nodes are null - continue
            if (node1 == null && node2 == null) {
                continue;
            }
            
            // One is null, other is not - trees are different
            if (node1 == null || node2 == null) {
                return false;
            }
            
            // Values are different - trees are different
            if (node1.val != node2.val) {
                return false;
            }
            
            // Add children to queue for comparison
            queue.offer(node1.left);
            queue.offer(node2.left);
            queue.offer(node1.right);
            queue.offer(node2.right);
        }
        
        return true;
    }
    
    /**
     * Approach 3: Iterative DFS Solution using Stack
     * Time Complexity: O(min(m,n)) where m and n are the number of nodes in each tree
     * Space Complexity: O(min(m,n)) for the stack
     */
    public boolean isSameTreeIterativeDFS(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(p);
        stack.push(q);
        
        while (!stack.isEmpty()) {
            TreeNode node2 = stack.pop();
            TreeNode node1 = stack.pop();
            
            // Both nodes are null - continue
            if (node1 == null && node2 == null) {
                continue;
            }
            
            // One is null, other is not - trees are different
            if (node1 == null || node2 == null) {
                return false;
            }
            
            // Values are different - trees are different
            if (node1.val != node2.val) {
                return false;
            }
            
            // Add children to stack for comparison
            stack.push(node1.left);
            stack.push(node2.left);
            stack.push(node1.right);
            stack.push(node2.right);
        }
        
        return true;
    }
    
    /**
     * Approach 4: Preorder Traversal Comparison
     * Convert both trees to strings and compare
     * Time Complexity: O(m + n) where m and n are the number of nodes in each tree
     * Space Complexity: O(m + n) for the string representations
     */
    public boolean isSameTreePreorder(TreeNode p, TreeNode q) {
        return preorderTraversal(p).equals(preorderTraversal(q));
    }
    
    private String preorderTraversal(TreeNode root) {
        if (root == null) {
            return "#"; // Use # to represent null nodes
        }
        
        return root.val + "," + 
               preorderTraversal(root.left) + "," + 
               preorderTraversal(root.right);
    }
    
    // Helper method to build test trees
    public static TreeNode buildTree1() {
        // Build tree: [1,2,3]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        return root;
    }
    
    public static TreeNode buildTree2() {
        // Build tree: [1,2,3]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        return root;
    }
    
    public static TreeNode buildTree3() {
        // Build tree: [1,2]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        return root;
    }
    
    public static TreeNode buildTree4() {
        // Build tree: [1,null,2]
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        return root;
    }
    
    // Test the solution
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: Same trees [1,2,3] and [1,2,3] -> Expected: true
        TreeNode p1 = buildTree1();
        TreeNode q1 = buildTree2();
        System.out.println("Test 1 - Recursive: " + solution.isSameTree(p1, q1));
        System.out.println("Test 1 - BFS: " + solution.isSameTreeIterativeBFS(p1, q1));
        System.out.println("Test 1 - DFS: " + solution.isSameTreeIterativeDFS(p1, q1));
        System.out.println("Test 1 - Preorder: " + solution.isSameTreePreorder(p1, q1));
        
        // Test case 2: Different trees [1,2] and [1,null,2] -> Expected: false
        TreeNode p2 = buildTree3();
        TreeNode q2 = buildTree4();
        System.out.println("Test 2 - Recursive: " + solution.isSameTree(p2, q2));
        
        // Test case 3: Both null trees -> Expected: true
        System.out.println("Test 3 - Both null: " + solution.isSameTree(null, null));
        
        // Test case 4: One null, one not -> Expected: false
        TreeNode p4 = new TreeNode(1);
        System.out.println("Test 4 - One null: " + solution.isSameTree(p4, null));
        
        // Test case 5: Same single node -> Expected: true
        TreeNode p5 = new TreeNode(1);
        TreeNode q5 = new TreeNode(1);
        System.out.println("Test 5 - Same single node: " + solution.isSameTree(p5, q5));
    }
}

import java.util.*;
