#https://neetcode.io/problems/binary-tree-from-preorder-and-inorder-traversal?list=blind75
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
     * Approach 1: Recursive with HashMap for O(1) lookup
     * Time Complexity: O(n)
     * Space Complexity: O(n) for HashMap and recursion stack
     */
    private int preorderIndex = 0;
    private Map<Integer, Integer> inorderMap;
    
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preorderIndex = 0;
        inorderMap = new HashMap<>();
        
        // Build HashMap for O(1) lookup of inorder indices
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        
        return buildHelper(preorder, 0, inorder.length - 1);
    }
    
    private TreeNode buildHelper(int[] preorder, int inorderStart, int inorderEnd) {
        if (inorderStart > inorderEnd) {
            return null;
        }
        
        // Root is always the next element in preorder
        int rootVal = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootVal);
        
        // Find root position in inorder array
        int rootIndex = inorderMap.get(rootVal);
        
        // Build left subtree first (important: left before right)
        root.left = buildHelper(preorder, inorderStart, rootIndex - 1);
        // Build right subtree
        root.right = buildHelper(preorder, rootIndex + 1, inorderEnd);
        
        return root;
    }
    
    /**
     * Approach 2: Recursive without HashMap (simpler but less efficient)
     * Time Complexity: O(n²) in worst case (skewed tree)
     * Space Complexity: O(n) for recursion stack
     */
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        return buildHelper2(preorder, inorder, 0, 0, inorder.length - 1);
    }
    
    private TreeNode buildHelper2(int[] preorder, int[] inorder, int preStart, int inStart, int inEnd) {
        if (preStart > preorder.length - 1 || inStart > inEnd) {
            return null;
        }
        
        TreeNode root = new TreeNode(preorder[preStart]);
        
        // Find root position in inorder array
        int inIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == root.val) {
                inIndex = i;
                break;
            }
        }
        
        // Build left and right subtrees
        root.left = buildHelper2(preorder, inorder, preStart + 1, inStart, inIndex - 1);
        root.right = buildHelper2(preorder, inorder, preStart + inIndex - inStart + 1, inIndex + 1, inEnd);
        
        return root;
    }
    
    /**
     * Approach 3: Iterative using Stack
     * Time Complexity: O(n)
     * Space Complexity: O(n) for stack
     */
    public TreeNode buildTree3(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode root = new TreeNode(preorder[0]);
        stack.push(root);
        
        int inorderIndex = 0;
        
        for (int i = 1; i < preorder.length; i++) {
            TreeNode node = new TreeNode(preorder[i]);
            TreeNode parent = null;
            
            // Find the parent node
            while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                parent = stack.pop();
                inorderIndex++;
            }
            
            if (parent != null) {
                // Right child
                parent.right = node;
            } else {
                // Left child
                stack.peek().left = node;
            }
            
            stack.push(node);
        }
        
        return root;
    }
    
    /**
     * Approach 4: Using array slicing (less efficient but intuitive)
     * Time Complexity: O(n²) due to array copying
     * Space Complexity: O(n²) for array copies
     */
    public TreeNode buildTree4(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;
        
        TreeNode root = new TreeNode(preorder[0]);
        
        // Find root position in inorder
        int rootIndex = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == preorder[0]) {
                rootIndex = i;
                break;
            }
        }
        
        // Create subarrays
        int[] leftInorder = Arrays.copyOfRange(inorder, 0, rootIndex);
        int[] rightInorder = Arrays.copyOfRange(inorder, rootIndex + 1, inorder.length);
        
        int[] leftPreorder = Arrays.copyOfRange(preorder, 1, rootIndex + 1);
        int[] rightPreorder = Arrays.copyOfRange(preorder, rootIndex + 1, preorder.length);
        
        root.left = buildTree4(leftPreorder, leftInorder);
        root.right = buildTree4(rightPreorder, rightInorder);
        
        return root;
    }
    
    // Helper method to print tree (for testing)
    public void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
    
    public void printPreorder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printPreorder(root.left);
        printPreorder(root.right);
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: [3,9,20,15,7] preorder, [9,3,15,20,7] inorder
        int[] preorder1 = {3, 9, 20, 15, 7};
        int[] inorder1 = {9, 3, 15, 20, 7};
        
        TreeNode root1 = solution.buildTree(preorder1, inorder1);
        System.out.println("Test 1 - Preorder: [3,9,20,15,7], Inorder: [9,3,15,20,7]");
        System.out.print("Result Preorder: ");
        solution.printPreorder(root1);
        System.out.println();
        System.out.print("Result Inorder: ");
        solution.printInorder(root1);
        System.out.println();
        
        // Test case 2: [-1] preorder, [-1] inorder
        int[] preorder2 = {-1};
        int[] inorder2 = {-1};
        
        TreeNode root2 = solution.buildTree(preorder2, inorder2);
        System.out.println("\nTest 2 - Single node:");
        System.out.print("Result Preorder: ");
        solution.printPreorder(root2);
        System.out.println();
        
        // Test case 3: [1,2,3] preorder, [3,2,1] inorder (left skewed)
        int[] preorder3 = {1, 2, 3};
        int[] inorder3 = {3, 2, 1};
        
        TreeNode root3 = solution.buildTree(preorder3, inorder3);
        System.out.println("\nTest 3 - Left skewed tree:");
        System.out.print("Result Preorder: ");
        solution.printPreorder(root3);
        System.out.println();
        System.out.print("Result Inorder: ");
        solution.printInorder(root3);
        System.out.println();
        
        // Test different approaches
        System.out.println("\nTesting different approaches on test 1:");
        TreeNode root1_2 = solution.buildTree2(preorder1, inorder1);
        System.out.print("Approach 2 Preorder: ");
        solution.printPreorder(root1_2);
        System.out.println();
        
        TreeNode root1_3 = solution.buildTree3(preorder1, inorder1);
        System.out.print("Approach 3 Preorder: ");
        solution.printPreorder(root1_3);
        System.out.println();
        
        TreeNode root1_4 = solution.buildTree4(preorder1, inorder1);
        System.out.print("Approach 4 Preorder: ");
        solution.printPreorder(root1_4);
        System.out.println();
    }
}
