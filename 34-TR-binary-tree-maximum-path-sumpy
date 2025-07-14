#https://neetcode.io/problems/binary-tree-maximum-path-sum?list=blind75
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
     * Approach 1: Post-order DFS with Global Maximum
     * Time Complexity: O(n) - visit each node once
     * Space Complexity: O(h) - recursion stack where h is height
     */
    private int maxPathSum = Integer.MIN_VALUE;
    
    public int maxPathSum(TreeNode root) {
        maxPathSum = Integer.MIN_VALUE;
        maxGain(root);
        return maxPathSum;
    }
    
    /**
     * Returns the maximum gain from the node as a path endpoint
     * This is the maximum sum from root to any leaf in the subtree
     */
    private int maxGain(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        // Get maximum gain from left and right subtrees
        // Use Math.max with 0 to ignore negative paths
        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);
        
        // Current path sum through this node (split at this node)
        int currentPathSum = node.val + leftGain + rightGain;
        
        // Update global maximum
        maxPathSum = Math.max(maxPathSum, currentPathSum);
        
        // Return maximum gain from this node as an endpoint
        // Can only choose one direction (left or right) + current node
        return node.val + Math.max(leftGain, rightGain);
    }
    
    /**
     * Approach 2: Using wrapper class to avoid global variable
     * Time Complexity: O(n)
     * Space Complexity: O(h)
     */
    class Result {
        int maxSum;
        Result(int maxSum) {
            this.maxSum = maxSum;
        }
    }
    
    public int maxPathSum2(TreeNode root) {
        Result result = new Result(Integer.MIN_VALUE);
        maxGainHelper(root, result);
        return result.maxSum;
    }
    
    private int maxGainHelper(TreeNode node, Result result) {
        if (node == null) {
            return 0;
        }
        
        int leftGain = Math.max(maxGainHelper(node.left, result), 0);
        int rightGain = Math.max(maxGainHelper(node.right, result), 0);
        
        // Path sum through current node
        int currentPathSum = node.val + leftGain + rightGain;
        result.maxSum = Math.max(result.maxSum, currentPathSum);
        
        // Return max gain from this node
        return node.val + Math.max(leftGain, rightGain);
    }
    
    /**
     * Approach 3: Iterative using post-order traversal (more complex)
     * Time Complexity: O(n)
     * Space Complexity: O(h)
     */
    public int maxPathSum3(TreeNode root) {
        if (root == null) return 0;
        
        Stack<TreeNode> stack = new Stack<>();
        Map<TreeNode, Integer> gains = new HashMap<>();
        Set<TreeNode> visited = new HashSet<>();
        
        stack.push(root);
        int maxSum = Integer.MIN_VALUE;
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            
            // If children haven't been processed, process them first
            if ((node.left != null && !visited.contains(node.left)) ||
                (node.right != null && !visited.contains(node.right))) {
                
                if (node.left != null && !visited.contains(node.left)) {
                    stack.push(node.left);
                }
                if (node.right != null && !visited.contains(node.right)) {
                    stack.push(node.right);
                }
            } else {
                // Process current node
                stack.pop();
                visited.add(node);
                
                int leftGain = node.left != null ? Math.max(gains.get(node.left), 0) : 0;
                int rightGain = node.right != null ? Math.max(gains.get(node.right), 0) : 0;
                
                int currentPathSum = node.val + leftGain + rightGain;
                maxSum = Math.max(maxSum, currentPathSum);
                
                gains.put(node, node.val + Math.max(leftGain, rightGain));
            }
        }
        
        return maxSum;
    }
    
    /**
     * Helper method to print tree structure for debugging
     */
    public void printTree(TreeNode root, int level) {
        if (root == null) return;
        
        printTree(root.right, level + 1);
        
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
        System.out.println(root.val);
        
        printTree(root.left, level + 1);
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: [1,2,3] - Expected: 6 (2->1->3)
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        
        System.out.println("Test 1 - [1,2,3]:");
        solution.printTree(root1, 0);
        System.out.println("Max Path Sum: " + solution.maxPathSum(root1));
        System.out.println("Expected: 6\n");
        
        // Test case 2: [-10,9,20,null,null,15,7] - Expected: 42 (15->20->7)
        TreeNode root2 = new TreeNode(-10);
        root2.left = new TreeNode(9);
        root2.right = new TreeNode(20);
        root2.right.left = new TreeNode(15);
        root2.right.right = new TreeNode(7);
        
        System.out.println("Test 2 - [-10,9,20,null,null,15,7]:");
        solution.printTree(root2, 0);
        System.out.println("Max Path Sum: " + solution.maxPathSum(root2));
        System.out.println("Expected: 42\n");
        
        // Test case 3: [-3] - Expected: -3 (single negative node)
        TreeNode root3 = new TreeNode(-3);
        
        System.out.println("Test 3 - [-3]:");
        solution.printTree(root3, 0);
        System.out.println("Max Path Sum: " + solution.maxPathSum(root3));
        System.out.println("Expected: -3\n");
        
        // Test case 4: [2,-1] - Expected: 2 (ignore negative child)
        TreeNode root4 = new TreeNode(2);
        root4.left = new TreeNode(-1);
        
        System.out.println("Test 4 - [2,-1]:");
        solution.printTree(root4, 0);
        System.out.println("Max Path Sum: " + solution.maxPathSum(root4));
        System.out.println("Expected: 2\n");
        
        // Test case 5: [5,4,8,11,null,13,4,7,2,null,null,null,1] - Complex tree
        TreeNode root5 = new TreeNode(5);
        root5.left = new TreeNode(4);
        root5.right = new TreeNode(8);
        root5.left.left = new TreeNode(11);
        root5.left.left.left = new TreeNode(7);
        root5.left.left.right = new TreeNode(2);
        root5.right.left = new TreeNode(13);
        root5.right.right = new TreeNode(4);
        root5.right.right.right = new TreeNode(1);
        
        System.out.println("Test 5 - Complex tree:");
        solution.printTree(root5, 0);
        System.out.println("Max Path Sum: " + solution.maxPathSum(root5));
        System.out.println("Expected: 48 (7->11->4->5->8->13)\n");
        
        // Test different approaches
        System.out.println("Testing different approaches on test 1:");
        System.out.println("Approach 1: " + solution.maxPathSum(root1));
        System.out.println("Approach 2: " + solution.maxPathSum2(root1));
        System.out.println("Approach 3: " + solution.maxPathSum3(root1));
    }
}
