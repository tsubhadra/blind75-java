//https://neetcode.io/problems/products-of-array-discluding-self?list=blind75
public class ProductOfArrayExceptSelf {

    // SOLUTION 1: Optimal O(n) time, O(1) space (excluding output array)
    // Two-pass approach: left products, then right products
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // First pass: calculate left products
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        // Second pass: multiply by right products
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] = result[i] * rightProduct;
            rightProduct *= nums[i];
        }

        return result;
    }

    // SOLUTION 2: Alternative O(n) time, O(n) space approach
    // Uses separate arrays for left and right products
    public int[] productExceptSelfWithExtraSpace(int[] nums) {
        int n = nums.length;
        int[] leftProducts = new int[n];
        int[] rightProducts = new int[n];
        int[] result = new int[n];

        // Calculate left products
        leftProducts[0] = 1;
        for (int i = 1; i < n; i++) {
            leftProducts[i] = leftProducts[i - 1] * nums[i - 1];
        }

        // Calculate right products
        rightProducts[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            rightProducts[i] = rightProducts[i + 1] * nums[i + 1];
        }

        // Multiply left and right products
        for (int i = 0; i < n; i++) {
            result[i] = leftProducts[i] * rightProducts[i];
        }

        return result;
    }

    // SOLUTION 3: Brute Force O(n²) approach (not recommended for large inputs)
    public int[] productExceptSelfBruteForce(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    product *= nums[j];
                }
            }
            result[i] = product;
        }

        return result;
    }

    // SOLUTION 4: Using division (handles edge cases with zeros)
    // Note: This approach uses division but handles zero cases properly
    public int[] productExceptSelfWithDivision(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // Count zeros and calculate total product of non-zero elements
        int zeroCount = 0;
        int totalProduct = 1;

        for (int num : nums) {
            if (num == 0) {
                zeroCount++;
            } else {
                totalProduct *= num;
            }
        }

        for (int i = 0; i < n; i++) {
            if (zeroCount > 1) {
                // More than one zero means all products are zero
                result[i] = 0;
            } else if (zeroCount == 1) {
                // Exactly one zero: only that position gets the total product
                result[i] = (nums[i] == 0) ? totalProduct : 0;
            } else {
                // No zeros: divide total product by current element
                result[i] = totalProduct / nums[i];
            }
        }

        return result;
    }

    // Test method to verify solutions
    public static void main(String[] args) {
        ProductOfArrayExceptSelf solution = new ProductOfArrayExceptSelf();

        // Test case 1
        int[] nums1 = {1, 2, 4, 6};
        int[] result1 = solution.productExceptSelf(nums1);
        System.out.println("Input: [1,2,4,6]");
        System.out.print("Output: [");
        for (int i = 0; i < result1.length; i++) {
            System.out.print(result1[i]);
            if (i < result1.length - 1) System.out.print(",");
        }
        System.out.println("]");

        // Test case 2
        int[] nums2 = {-1, 0, 1, 2, 3};
        int[] result2 = solution.productExceptSelf(nums2);
        System.out.println("\nInput: [-1,0,1,2,3]");
        System.out.print("Output: [");
        for (int i = 0; i < result2.length; i++) {
            System.out.print(result2[i]);
            if (i < result2.length - 1) System.out.print(",");
        }
        System.out.println("]");
    }
}