import java.util.*;
//https://neetcode.io/problems/subtree-of-a-binary-tree?list=blind75
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

public class a28_TR_subtree_of_a_binary_tree {
    
    /**
     * Approach 1: Recursive DFS a28_TR_subtree_of_a_binary_tree (Most Common)
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
    
    // Test the a28_TR_subtree_of_a_binary_tree
    public static void main(String[] args) {
        a28_TR_subtree_of_a_binary_tree a28_TR_subtree_of_a_binary_tree = new a28_TR_subtree_of_a_binary_tree();
        
        // Test case 1: root = [3,4,5,1,2], subRoot = [4,1,2] -> Expected: true
        TreeNode root1 = buildRootTree();
        TreeNode subRoot1 = buildSubRootTree();
        System.out.println("Test 1 - Recursive: " + a28_TR_subtree_of_a_binary_tree.isSubtree(root1, subRoot1));

        // Test case 2: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2] -> Expected: false
        TreeNode root2 = buildRootTree2();
        TreeNode subRoot2 = buildSubRootTree();
        System.out.println("Test 2 - Recursive: " + a28_TR_subtree_of_a_binary_tree.isSubtree(root2, subRoot2));
        
        // Test case 3: subRoot is the entire root tree -> Expected: true
        TreeNode root3 = buildRootTree();
        TreeNode subRoot3 = buildRootTree();
        System.out.println("Test 3 - Same trees: " + a28_TR_subtree_of_a_binary_tree.isSubtree(root3, subRoot3));
        
        // Test case 4: subRoot is a single node that exists in root -> Expected: true
        TreeNode root4 = buildRootTree();
        TreeNode subRoot4 = new TreeNode(2);
        System.out.println("Test 4 - Single node: " + a28_TR_subtree_of_a_binary_tree.isSubtree(root4, subRoot4));
        
        // Test case 5: Empty subRoot -> Expected: true (empty tree is subtree of any tree)
        TreeNode root5 = buildRootTree();
        System.out.println("Test 5 - Empty subRoot: " + a28_TR_subtree_of_a_binary_tree.isSubtree(root5, null));
    }
}


