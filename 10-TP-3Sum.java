//https://neetcode.io/problems/three-integer-sum?list=blind75
import java.util.*;

public class ThreeSum {

    // SOLUTION 1: Optimal Two-Pointer Approach (Recommended)
    // Time: O(n²), Space: O(1) excluding output
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        if (nums == null || nums.length < 3) {
            return result;
        }

        // Sort the array to enable two-pointer technique and handle duplicates
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for the first element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // Two-pointer approach for the remaining two elements
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum == target) {
                    // Found a triplet
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Skip duplicates for left pointer
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    // Skip duplicates for right pointer
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }

    // SOLUTION 2: HashSet Approach for Duplicate Handling
    // Time: O(n²), Space: O(n)
    public List<List<Integer>> threeSumHashSet(int[] nums) {
        Set<List<Integer>> resultSet = new HashSet<>();

        if (nums == null || nums.length < 3) {
            return new ArrayList<>(resultSet);
        }

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    resultSet.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return new ArrayList<>(resultSet);
    }

    // SOLUTION 3: HashMap Approach (Two Sum variation)
    // Time: O(n²), Space: O(n)
    public List<List<Integer>> threeSumHashMap(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        if (nums == null || nums.length < 3) {
            return result;
        }

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for the first element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // Use HashMap to find two sum
            Map<Integer, Integer> map = new HashMap<>();
            int target = -nums[i];

            for (int j = i + 1; j < nums.length; j++) {
                int complement = target - nums[j];

                if (map.containsKey(complement)) {
                    result.add(Arrays.asList(nums[i], complement, nums[j]));

                    // Skip duplicates for nums[j]
                    while (j + 1 < nums.length && nums[j] == nums[j + 1]) {
                        j++;
                    }
                }

                map.put(nums[j], j);
            }
        }

        return result;
    }

    // SOLUTION 4: Brute Force with Set for Duplicates (Not recommended)
    // Time: O(n³), Space: O(n)
    public List<List<Integer>> threeSumBruteForce(int[] nums) {
        Set<List<Integer>> resultSet = new HashSet<>();

        if (nums == null || nums.length < 3) {
            return new ArrayList<>(resultSet);
        }

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        resultSet.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    }
                }
            }
        }

        return new ArrayList<>(resultSet);
    }

    // SOLUTION 5: Optimized with Early Termination
    // Time: O(n²), Space: O(1) excluding output
    public List<List<Integer>> threeSumOptimized(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        if (nums == null || nums.length < 3) {
            return result;
        }

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            // Early termination: if current number is positive and it's the smallest,
            // no way to get sum = 0
            if (nums[i] > 0) {
                break;
            }

            // Skip duplicates for the first element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Skip duplicates
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }

    // Helper method to print results nicely
    private void printResult(List<List<Integer>> result) {
        System.out.print("[");
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));
            if (i < result.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    // Test method to verify solutions
    public static void main(String[] args) {
        ThreeSum solution = new ThreeSum();

        // Test case 1
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        System.out.println("Input: [-1,0,1,2,-1,-4]");
        System.out.print("Output: ");
        List<List<Integer>> result1 = solution.threeSum(nums1);
        solution.printResult(result1);
        System.out.println("Expected: [[-1,-1,2],[-1,0,1]]");

        // Test case 2
        int[] nums2 = {0, 1, 1};
        System.out.println("\nInput: [0,1,1]");
        System.out.print("Output: ");
        List<List<Integer>> result2 = solution.threeSum(nums2);
        solution.printResult(result2);
        System.out.println("Expected: []");

        // Test case 3
        int[] nums3 = {0, 0, 0};
        System.out.println("\nInput: [0,0,0]");
        System.out.print("Output: ");
        List<List<Integer>> result3 = solution.threeSum(nums3);
        solution.printResult(result3);
        System.out.println("Expected: [[0,0,0]]");

        // Test case 4: Edge case with duplicates
        int[] nums4 = {-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4};
        System.out.println("\nInput: [-1,0,1,2,-1,-4,-2,-3,3,0,4]");
        System.out.print("Output: ");
        List<List<Integer>> result4 = solution.threeSum(nums4);
        solution.printResult(result4);

        // Test case 5: All same elements
        int[] nums5 = {1, 1, 1};
        System.out.println("\nInput: [1,1,1]");
        System.out.print("Output: ");
        List<List<Integer>> result5 = solution.threeSum(nums5);
        solution.printResult(result5);
        System.out.println("Expected: []");

        // Test case 6: Two elements only
        int[] nums6 = {1, 2};
        System.out.println("\nInput: [1,2]");
        System.out.print("Output: ");
        List<List<Integer>> result6 = solution.threeSum(nums6);
        solution.printResult(result6);
        System.out.println("Expected: []");
    }
}