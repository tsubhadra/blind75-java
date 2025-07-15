//https://neetcode.io/problems/kth-smallest-integer-in-bst?list=blind75
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

public class a32_TR_kth_smallest_integer_in_bst {
    
    /**
     * Approach 1: Recursive In-order Traversal with Counter
     * Time Complexity: O(h + k) where h is height, k is the target
     * Space Complexity: O(h) for recursion stack
     */
    private int count = 0;
    private int result = 0;
    
    public int kthSmallest(TreeNode root, int k) {
        count = 0;
        result = 0;
        inorder(root, k);
        return result;
    }
    
    private void inorder(TreeNode node, int k) {
        if (node == null) return;
        
        inorder(node.left, k);
        
        count++;
        if (count == k) {
            result = node.val;
            return;
        }
        
        inorder(node.right, k);
    }
    
    
    // Test method
    public static void main(String[] args) {
        a32_TR_kth_smallest_integer_in_bst a32_TR_kth_smallest_integer_in_bst = new a32_TR_kth_smallest_integer_in_bst();
        
        // Test case 1: [3,1,4,null,2], k = 1
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(4);
        root1.left.right = new TreeNode(2);
        
        System.out.println("Test 1 - [3,1,4,null,2], k=1: " + a32_TR_kth_smallest_integer_in_bst.kthSmallest(root1, 1));
        // Expected: 1
        
        // Test case 2: [5,3,6,2,4,null,null,1], k = 3
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(3);
        root2.right = new TreeNode(6);
        root2.left.left = new TreeNode(2);
        root2.left.right = new TreeNode(4);
        root2.left.left.left = new TreeNode(1);
        
        System.out.println("Test 2 - [5,3,6,2,4,null,null,1], k=3: " + a32_TR_kth_smallest_integer_in_bst.kthSmallest(root2, 3));
        // Expected: 3
        
        // Test case 3: Single node
        TreeNode root3 = new TreeNode(1);
        System.out.println("Test 3 - [1], k=1: " + a32_TR_kth_smallest_integer_in_bst.kthSmallest(root3, 1));
        // Expected: 1
        
        // Test case 4: Empty tree
        TreeNode root4 = null;
        try {
            System.out.println("Test 4 - Empty tree, k=1: " + a32_TR_kth_smallest_integer_in_bst.kthSmallest(root4, 1));
        } catch (Exception e) {
            System.out.println("Test 4 - Empty tree, k=1: Exception caught - " + e.getMessage());
        }
        
        // Test with larger k
        System.out.println("\nTesting with k=4 on tree 2:");
        System.out.println("Approach 1: " + a32_TR_kth_smallest_integer_in_bst.kthSmallest(root2, 4));

    }
}
