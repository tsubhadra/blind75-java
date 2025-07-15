import java.util.*;
//https://neetcode.io/problems/same-binary-tree?list=blind75
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

public class a27_TR_same_binary_tree {
    
    /**
     * Approach 1: Recursive DFS a27_TR_same_binary_tree (Most Popular)
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
    
    // Test the a27_TR_same_binary_tree
    public static void main(String[] args) {
        a27_TR_same_binary_tree a27_TR_same_binary_tree = new a27_TR_same_binary_tree();
        
        // Test case 1: Same trees [1,2,3] and [1,2,3] -> Expected: true
        TreeNode p1 = buildTree1();
        TreeNode q1 = buildTree2();
        System.out.println("Test 1 - Recursive: " + a27_TR_same_binary_tree.isSameTree(p1, q1));

        
        // Test case 2: Different trees [1,2] and [1,null,2] -> Expected: false
        TreeNode p2 = buildTree3();
        TreeNode q2 = buildTree4();
        System.out.println("Test 2 - Recursive: " + a27_TR_same_binary_tree.isSameTree(p2, q2));
        
        // Test case 3: Both null trees -> Expected: true
        System.out.println("Test 3 - Both null: " + a27_TR_same_binary_tree.isSameTree(null, null));
        
        // Test case 4: One null, one not -> Expected: false
        TreeNode p4 = new TreeNode(1);
        System.out.println("Test 4 - One null: " + a27_TR_same_binary_tree.isSameTree(p4, null));
        
        // Test case 5: Same single node -> Expected: true
        TreeNode p5 = new TreeNode(1);
        TreeNode q5 = new TreeNode(1);
        System.out.println("Test 5 - Same single node: " + a27_TR_same_binary_tree.isSameTree(p5, q5));
    }
}


