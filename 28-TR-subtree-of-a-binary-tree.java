#https://neetcode.io/problems/subtree-of-a-binary-tree?list=blind75
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
     * Time Complexity: O(m * n) where m is nodes in root, n is nodes in subRoot
     * Space Complexity: O(max(m, n)) due to recursion stack
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // Base case: if root is null but subRoot is not, no subtree exists
        if (root == null) {
            return false;
        }
        
        // Check if current root matches subRoot, or check left/right subtrees
        return isSameTree(root, subRoot) || 
               isSubtree(root.left, subRoot) || 
               isSubtree(root.right, subRoot);
    }
    
    /**
     * Helper method to check if two trees are identical
     * This is the same as the "Same Tree" problem we solved earlier
     */
    private boolean isSameTree(TreeNode p, TreeNode q) {
        // Both trees are empty
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
     * Approach 2: String Serialization Solution
     * Convert both trees to strings and check if subRoot string is substring of root string
     * Time Complexity: O(m + n) where m is nodes in root, n is nodes in subRoot
     * Space Complexity: O(m + n) for the string representations
     */
    public boolean isSubtreeStringSerialization(TreeNode root, TreeNode subRoot) {
        String rootSerialization = serialize(root);
        String subRootSerialization = serialize(subRoot);
        
        return rootSerialization.contains(subRootSerialization);
    }
    
    private String serialize(TreeNode node) {
        if (node == null) {
            return "#"; // Use # to represent null nodes
        }
        
        // Use commas and parentheses to avoid false matches
        return "(" + node.val + "," + serialize(node.left) + "," + serialize(node.right) + ")";
    }
    
    /**
     * Approach 3: Iterative BFS Solution
     * Use BFS to traverse root tree and check each node as potential subtree root
     * Time Complexity: O(m * n) where m is nodes in root, n is nodes in subRoot
     * Space Complexity: O(m) for the queue
     */
    public boolean isSubtreeIterativeBFS(TreeNode root, TreeNode subRoot) {
        if (root == null) return false;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            
            // Check if current node can be the root of a subtree matching subRoot
            if (isSameTree(current, subRoot)) {
                return true;
            }
            
            // Add children to queue for further exploration
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        
        return false;
    }
    
    /**
     * Approach 4: Preorder Traversal with ArrayList
     * Store preorder traversal of both trees and check if subRoot's traversal 
     * is a subsequence of root's traversal
     * Time Complexity: O(m + n) where m is nodes in root, n is nodes in subRoot
     * Space Complexity: O(m + n) for the lists
     */
    public boolean isSubtreePreorder(TreeNode root, TreeNode subRoot) {
        List<Integer> rootTraversal = new ArrayList<>();
        List<Integer> subRootTraversal = new ArrayList<>();
        
        preorderTraversal(root, rootTraversal);
        preorderTraversal(subRoot, subRootTraversal);
        
        return isSubsequence(rootTraversal, subRootTraversal);
    }
    
    private void preorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) {
            result.add(null); // Add null to maintain structure
            return;
        }
        
        result.add(node.val);
        preorderTraversal(node.left, result);
        preorderTraversal(node.right, result);
    }
    
    private boolean isSubsequence(List<Integer> main, List<Integer> sub) {
        if (sub.isEmpty()) return true;
        if (main.size() < sub.size()) return false;
        
        for (int i = 0; i <= main.size() - sub.size(); i++) {
            boolean match = true;
            for (int j = 0; j < sub.size(); j++) {
                if (!Objects.equals(main.get(i + j), sub.get(j))) {
                    match = false;
                    break;
                }
            }
            if (match) return true;
        }
        
        return false;
    }
    
    // Helper methods to build test trees
    public static TreeNode buildRootTree() {
        // Build tree: [3,4,5,1,2]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(2);
        return root;
    }
    
    public static TreeNode buildSubRootTree() {
        // Build tree: [4,1,2]
        TreeNode subRoot = new TreeNode(4);
        subRoot.left = new TreeNode(1);
        subRoot.right = new TreeNode(2);
        return subRoot;
    }
    
    public static TreeNode buildRootTree2() {
        // Build tree: [3,4,5,1,2,null,null,null,null,0]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(0);
        return root;
    }
    
    // Test the solution
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: root = [3,4,5,1,2], subRoot = [4,1,2] -> Expected: true
        TreeNode root1 = buildRootTree();
        TreeNode subRoot1 = buildSubRootTree();
        System.out.println("Test 1 - Recursive: " + solution.isSubtree(root1, subRoot1));
        System.out.println("Test 1 - String Serialization: " + solution.isSubtreeStringSerialization(root1, subRoot1));
        System.out.println("Test 1 - BFS: " + solution.isSubtreeIterativeBFS(root1, subRoot1));
        System.out.println("Test 1 - Preorder: " + solution.isSubtreePreorder(root1, subRoot1));
        
        // Test case 2: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2] -> Expected: false
        TreeNode root2 = buildRootTree2();
        TreeNode subRoot2 = buildSubRootTree();
        System.out.println("Test 2 - Recursive: " + solution.isSubtree(root2, subRoot2));
        
        // Test case 3: subRoot is the entire root tree -> Expected: true
        TreeNode root3 = buildRootTree();
        TreeNode subRoot3 = buildRootTree();
        System.out.println("Test 3 - Same trees: " + solution.isSubtree(root3, subRoot3));
        
        // Test case 4: subRoot is a single node that exists in root -> Expected: true
        TreeNode root4 = buildRootTree();
        TreeNode subRoot4 = new TreeNode(2);
        System.out.println("Test 4 - Single node: " + solution.isSubtree(root4, subRoot4));
        
        // Test case 5: Empty subRoot -> Expected: true (empty tree is subtree of any tree)
        TreeNode root5 = buildRootTree();
        System.out.println("Test 5 - Empty subRoot: " + solution.isSubtree(root5, null));
    }
}

import java.util.*;
