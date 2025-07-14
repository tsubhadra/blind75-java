#https://neetcode.io/problems/kth-smallest-integer-in-bst?list=blind75
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
    
    /**
     * Approach 2: Iterative In-order Traversal with Stack
     * Time Complexity: O(h + k)
     * Space Complexity: O(h) for stack
     */
    public int kthSmallest2(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        
        while (true) {
            // Go to leftmost node
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            
            // Process current node
            root = stack.pop();
            
            // Check if this is the kth node
            if (--k == 0) {
                return root.val;
            }
            
            // Move to right subtree
            root = root.right;
        }
    }
    
    /**
     * Approach 3: In-order Traversal with List (Simple but less efficient)
     * Time Complexity: O(n)
     * Space Complexity: O(n) for storing all values
     */
    public int kthSmallest3(TreeNode root, int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        return inorder.get(k - 1);
    }
    
    private void inorderTraversal(TreeNode node, List<Integer> inorder) {
        if (node == null) return;
        
        inorderTraversal(node.left, inorder);
        inorder.add(node.val);
        inorderTraversal(node.right, inorder);
    }
    
    /**
     * Approach 4: Optimized with Node Count (for frequent queries)
     * This approach is useful when the tree is modified frequently
     * and we need to answer multiple kth smallest queries
     */
    public int kthSmallest4(TreeNode root, int k) {
        int leftCount = countNodes(root.left);
        
        if (k <= leftCount) {
            // kth smallest is in left subtree
            return kthSmallest4(root.left, k);
        } else if (k > leftCount + 1) {
            // kth smallest is in right subtree
            return kthSmallest4(root.right, k - leftCount - 1);
        } else {
            // root is the kth smallest
            return root.val;
        }
    }
    
    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    /**
     * Approach 5: Morris Traversal (O(1) space, advanced)
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int kthSmallest5(TreeNode root, int k) {
        int count = 0;
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // Visit current node
                count++;
                if (count == k) {
                    return current.val;
                }
                current = current.right;
            } else {
                // Find inorder predecessor
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Make current the right child of its inorder predecessor
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Revert the changes made
                    predecessor.right = null;
                    count++;
                    if (count == k) {
                        return current.val;
                    }
                    current = current.right;
                }
            }
        }
        
        return -1; // Should never reach here for valid input
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: [3,1,4,null,2], k = 1
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(4);
        root1.left.right = new TreeNode(2);
        
        System.out.println("Test 1 - [3,1,4,null,2], k=1: " + solution.kthSmallest(root1, 1));
        // Expected: 1
        
        // Test case 2: [5,3,6,2,4,null,null,1], k = 3
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(3);
        root2.right = new TreeNode(6);
        root2.left.left = new TreeNode(2);
        root2.left.right = new TreeNode(4);
        root2.left.left.left = new TreeNode(1);
        
        System.out.println("Test 2 - [5,3,6,2,4,null,null,1], k=3: " + solution.kthSmallest(root2, 3));
        // Expected: 3
        
        // Test case 3: Single node
        TreeNode root3 = new TreeNode(1);
        System.out.println("Test 3 - [1], k=1: " + solution.kthSmallest(root3, 1));
        // Expected: 1
        
        // Test different approaches
        System.out.println("\nTesting different approaches:");
        System.out.println("Approach 2 (iterative): " + solution.kthSmallest2(root1, 1));
        System.out.println("Approach 3 (list): " + solution.kthSmallest3(root1, 1));
        System.out.println("Approach 4 (node count): " + solution.kthSmallest4(root1, 1));
        System.out.println("Approach 5 (Morris): " + solution.kthSmallest5(root1, 1));
        
        // Test with larger k
        System.out.println("\nTesting with k=4 on tree 2:");
        System.out.println("Approach 1: " + solution.kthSmallest(root2, 4));
        System.out.println("Approach 2: " + solution.kthSmallest2(root2, 4));
        // Expected: 4
    }
}
