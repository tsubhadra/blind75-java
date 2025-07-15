import java.util.*;
//https://neetcode.io/problems/lowest-common-ancestor-in-binary-search-tree?list=blind75
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

public class a29_TR_lowest_common_ancestor_in_binary_search_tree {
    
    /**
     * Approach 1: Recursive a29_TR_lowest_common_ancestor_in_binary_search_tree leveraging BST property (Most Elegant)
     * Time Complexity: O(h) where h is the height of the tree
     * Space Complexity: O(h) due to recursion stack
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case: if root is null
        if (root == null) {
            return null;
        }
        
        // If both p and q are smaller than root, LCA is in left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        
        // If both p and q are greater than root, LCA is in right subtree
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        
        // If p and q are on different sides of root (or one equals root), 
        // then root is the LCA
        return root;
    }
    

    
    // Helper method to build test BST
    public static TreeNode buildTestBST() {
        // Build BST: [6,2,8,0,4,7,9,null,null,3,5]
        TreeNode root = new TreeNode(6);
        root.left = new TreeNode(2);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);
        root.left.right.left = new TreeNode(3);
        root.left.right.right = new TreeNode(5);
        return root;
    }
    
    // Helper method to find a node by value
    public static TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        
        TreeNode left = findNode(root.left, val);
        if (left != null) return left;
        
        return findNode(root.right, val);
    }
    
    // Test the a29_TR_lowest_common_ancestor_in_binary_search_tree
    public static void main(String[] args) {
        a29_TR_lowest_common_ancestor_in_binary_search_tree a29_TR_lowest_common_ancestor_in_binary_search_tree = new a29_TR_lowest_common_ancestor_in_binary_search_tree();
        TreeNode root = buildTestBST();
        
        // Test case 1: p = 2, q = 8 -> Expected: 6
        TreeNode p1 = findNode(root, 2);
        TreeNode q1 = findNode(root, 8);
        TreeNode result1 = a29_TR_lowest_common_ancestor_in_binary_search_tree.lowestCommonAncestor(root, p1, q1);
        System.out.println("Test 1 - Recursive (p=2, q=8): " + result1.val);
        
        TreeNode result1_iter = a29_TR_lowest_common_ancestor_in_binary_search_tree.lowestCommonAncestorIterative(root, p1, q1);
        System.out.println("Test 1 - Iterative (p=2, q=8): " + result1_iter.val);
        
        // Test case 2: p = 2, q = 4 -> Expected: 2
        TreeNode p2 = findNode(root, 2);
        TreeNode q2 = findNode(root, 4);
        TreeNode result2 = a29_TR_lowest_common_ancestor_in_binary_search_tree.lowestCommonAncestor(root, p2, q2);
        System.out.println("Test 2 - Recursive (p=2, q=4): " + result2.val);
        
        // Test case 3: p = 3, q = 5 -> Expected: 4
        TreeNode p3 = findNode(root, 3);
        TreeNode q3 = findNode(root, 5);
        TreeNode result3 = a29_TR_lowest_common_ancestor_in_binary_search_tree.lowestCommonAncestor(root, p3, q3);
        System.out.println("Test 3 - Recursive (p=3, q=5): " + result3.val);
        
        // Test case 4: p = 0, q = 9 -> Expected: 6
        TreeNode p4 = findNode(root, 0);
        TreeNode q4 = findNode(root, 9);
        TreeNode result4 = a29_TR_lowest_common_ancestor_in_binary_search_tree.lowestCommonAncestorOptimized(root, p4, q4);
        System.out.println("Test 4 - Optimized (p=0, q=9): " + result4.val);
        
        // Test case 5: p = 7, q = 9 -> Expected: 8
        TreeNode p5 = findNode(root, 7);
        TreeNode q5 = findNode(root, 9);
        TreeNode result5 = a29_TR_lowest_common_ancestor_in_binary_search_tree.lowestCommonAncestorPathBased(root, p5, q5);
        System.out.println("Test 5 - Path-based (p=7, q=9): " + result5.val);
    }
}


