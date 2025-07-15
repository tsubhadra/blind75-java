import java.util.*;

//https://neetcode.io/problems/invert-a-binary-tree?list=blind75
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

public class a25_TR_invert_a_binary_tree {
    
    /**
     * Approach 1: Recursive DFS a25_TR_invert_a_binary_tree
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
    
    // Helper method to print tree (for testing)
    public void printInorder(TreeNode root) {
        if (root != null) {
            printInorder(root.left);
            System.out.print(root.val + " ");
            printInorder(root.right);
        }
    }
    
    // Test the a25_TR_invert_a_binary_tree
    public static void main(String[] args) {
        a25_TR_invert_a_binary_tree a25_TR_invert_a_binary_tree = new a25_TR_invert_a_binary_tree();
        
        // Create test tree: [4,2,7,1,3,6,9]
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);
        
        System.out.println("Original tree (inorder):");
        a25_TR_invert_a_binary_tree.printInorder(root);
        System.out.println();
        
        TreeNode inverted = a25_TR_invert_a_binary_tree.invertTree(root);
        
        System.out.println("Inverted tree (inorder):");
        a25_TR_invert_a_binary_tree.printInorder(inverted);
        System.out.println();
    }
}


