#https://neetcode.io/problems/serialize-and-deserialize-binary-tree?list=blind75
import java.util.*;

// Challenge 1: Binary Tree Level Order Traversal
class BinaryTreeLevelOrder {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }
}

// Challenge 2: Maximum Depth of Binary Tree
class MaxDepth {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        return Math.max(leftDepth, rightDepth) + 1;
    }
    
    // Iterative approach
    public int maxDepthIterative(TreeNode root) {
        if (root == null) return 0;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            depth++;
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return depth;
    }
}

// Challenge 3: Invert Binary Tree
class InvertBinaryTree {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        
        // Swap left and right children
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        
        // Recursively invert subtrees
        invertTree(root.left);
        invertTree(root.right);
        
        return root;
    }
    
    // Iterative approach
    public TreeNode invertTreeIterative(TreeNode root) {
        if (root == null) return null;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            // Swap children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        return root;
    }
}

// Challenge 4: Validate Binary Search Tree
class ValidateBST {
    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }
    
    private boolean validate(TreeNode node, Integer min, Integer max) {
        if (node == null) return true;
        
        if ((min != null && node.val <= min) || (max != null && node.val >= max)) {
            return false;
        }
        
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }
    
    // Inorder traversal approach
    public boolean isValidBSTInorder(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        
        for (int i = 1; i < inorder.size(); i++) {
            if (inorder.get(i) <= inorder.get(i - 1)) {
                return false;
            }
        }
        
        return true;
    }
    
    private void inorderTraversal(TreeNode node, List<Integer> inorder) {
        if (node == null) return;
        
        inorderTraversal(node.left, inorder);
        inorder.add(node.val);
        inorderTraversal(node.right, inorder);
    }
}

// Challenge 5: Lowest Common Ancestor of a Binary Tree
class LowestCommonAncestor {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        if (left != null && right != null) {
            return root; // Current node is LCA
        }
        
        return left != null ? left : right;
    }
}

// Challenge 6: Binary Tree Right Side View
class RightSideView {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add the rightmost node of each level
                if (i == levelSize - 1) {
                    result.add(node.val);
                }
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return result;
    }
    
    // DFS approach
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }
    
    private void dfs(TreeNode node, int level, List<Integer> result) {
        if (node == null) return;
        
        // If this is the first node we're visiting at this level
        if (level == result.size()) {
            result.add(node.val);
        }
        
        // Visit right first, then left
        dfs(node.right, level + 1, result);
        dfs(node.left, level + 1, result);
    }
}

// Test class with main method
public class TreeChallenges {
    public static void main(String[] args) {
        // Create test tree:      3
        //                       / \
        //                      9   20
        //                         /  \
        //                        15   7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        
        // Test Level Order Traversal
        BinaryTreeLevelOrder levelOrder = new BinaryTreeLevelOrder();
        System.out.println("Level Order: " + levelOrder.levelOrder(root));
        
        // Test Max Depth
        MaxDepth maxDepth = new MaxDepth();
        System.out.println("Max Depth: " + maxDepth.maxDepth(root));
        
        // Test Right Side View
        RightSideView rightSideView = new RightSideView();
        System.out.println("Right Side View: " + rightSideView.rightSideView(root));
        
        // Test BST Validation
        ValidateBST validateBST = new ValidateBST();
        System.out.println("Is Valid BST: " + validateBST.isValidBST(root));
    }
}
