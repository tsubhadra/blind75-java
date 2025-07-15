//https://neetcode.io/problems/maximum-product-subarray?list=blind75
public class Solution {
    
    // Approach 1: Dynamic Programming - Track both max and min products
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int maxProduct = nums[0];
        int minProduct = nums[0];
        int result = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            
            // When we multiply by a negative number, max and min swap
            if (current < 0) {
                int temp = maxProduct;
                maxProduct = minProduct;
                minProduct = temp;
            }
            
            // Update max and min products ending at current position
            maxProduct = Math.max(current, maxProduct * current);
            minProduct = Math.min(current, minProduct * current);
            
            // Update global maximum
            result = Math.max(result, maxProduct);
        }
        
        return result;
    }
    
    // Approach 2: More explicit DP without swapping\n    public int maxProductExplicit(int[] nums) {\n        if (nums == null || nums.length == 0) {\n            return 0;\n        }\n        \n        int maxProduct = nums[0];\n        int minProduct = nums[0];\n        int result = nums[0];\n        \n        for (int i = 1; i < nums.length; i++) {\n            int current = nums[i];\n            \n            // Calculate all possible products\n            int option1 = current;                    // Start new subarray\n            int option2 = maxProduct * current;       // Extend max subarray\n            int option3 = minProduct * current;       // Extend min subarray\n            \n            // Update max and min\n            maxProduct = Math.max(option1, Math.max(option2, option3));\n            minProduct = Math.min(option1, Math.min(option2, option3));\n            \n            // Update global result\n            result = Math.max(result, maxProduct);\n        }\n        \n        return result;\n    }\n    \n    // Approach 3: Brute Force - Check all subarrays\n    public int maxProductBruteForce(int[] nums) {\n        if (nums == null || nums.length == 0) {\n            return 0;\n        }\n        \n        int maxProduct = nums[0];\n        \n        for (int i = 0; i < nums.length; i++) {\n            int currentProduct = 1;\n            \n            for (int j = i; j < nums.length; j++) {\n                currentProduct *= nums[j];\n                maxProduct = Math.max(maxProduct, currentProduct);\n            }\n        }\n        \n        return maxProduct;\n    }\n    \n    // Approach 4: Two-pass approach (left to right, right to left)\n    public int maxProductTwoPass(int[] nums) {\n        if (nums == null || nums.length == 0) {\n            return 0;\n        }\n        \n        int n = nums.length;\n        int maxProduct = nums[0];\n        \n        // Left to right pass\n        int product = 1;\n        for (int i = 0; i < n; i++) {\n            product *= nums[i];\n            maxProduct = Math.max(maxProduct, product);\n            \n            // Reset product if it becomes 0\n            if (product == 0) {\n                product = 1;\n            }\n        }\n        \n        // Right to left pass\n        product = 1;\n        for (int i = n - 1; i >= 0; i--) {\n            product *= nums[i];\n            maxProduct = Math.max(maxProduct, product);\n            \n            // Reset product if it becomes 0\n            if (product == 0) {\n                product = 1;\n            }\n        }\n        \n        return maxProduct;\n    }\n    \n    // Approach 5: Handle zeros explicitly\n    public int maxProductHandleZeros(int[] nums) {\n        if (nums == null || nums.length == 0) {\n            return 0;\n        }\n        \n        int maxProduct = Integer.MIN_VALUE;\n        int product = 1;\n        \n        // Left to right\n        for (int num : nums) {\n            product *= num;\n            maxProduct = Math.max(maxProduct, product);\n            if (product == 0) {\n                product = 1;\n            }\n        }\n        \n        // Right to left\n        product = 1;\n        for (int i = nums.length - 1; i >= 0; i--) {\n            product *= nums[i];\n            maxProduct = Math.max(maxProduct, product);\n            if (product == 0) {\n                product = 1;\n            }\n        }\n        \n        return maxProduct;\n    }\n    \n    // Helper method to demonstrate the DP process\n    public void demonstrateMaxProduct(int[] nums) {\n        System.out.println(\"\\nDemonstrating Max Product for: \" + \n                          java.util.Arrays.toString(nums));\n        \n        if (nums == null || nums.length == 0) {\n            System.out.println(\"Empty array\");\n            return;\n        }\n        \n        int maxProduct = nums[0];\n        int minProduct = nums[0];\n        int result = nums[0];\n        \n        System.out.println(\"Initial: max=\" + maxProduct + \", min=\" + minProduct + \n                          \", result=\" + result);\n        \n        for (int i = 1; i < nums.length; i++) {\n            int current = nums[i];\n            System.out.print(\"Step \" + i + \" (num=\" + current + \"): \");\n            \n            // Store previous values for explanation\n            int prevMax = maxProduct;\n            int prevMin = minProduct;\n            \n            if (current < 0) {\n                System.out.print(\"negative, swap max/min -> \");\n                int temp = maxProduct;\n                maxProduct = minProduct;\n                minProduct = temp;\n            }\n            \n            maxProduct = Math.max(current, maxProduct * current);\n            minProduct = Math.min(current, minProduct * current);\n            result = Math.max(result, maxProduct);\n            \n            System.out.println(\"max=\" + maxProduct + \", min=\" + minProduct + \n                             \", result=\" + result);\n        }\n        \n        System.out.println(\"Final result: \" + result);\n    }\n    \n    // Method to find the actual subarray that gives maximum product\n    public int[] findMax
