#https://neetcode.io/problems/two-integer-sum?list=blind75
def twoSum(nums, target):
    """
    Given an array of integers nums and an integer target, return the indices 
    i and j such that nums[i] + nums[j] == target and i != j.
    
    Time Complexity: O(n) - we iterate through the array once
    Space Complexity: O(n) - we store at most n elements in the hash map
    """
    # Hash map to store value -> index mapping
    num_to_index = {}
    
    for i, num in enumerate(nums):
        complement = target - num
        
        # Check if complement exists in our hash map
        if complement in num_to_index:
            # Found the pair! Return indices with smaller index first
            return [num_to_index[complement], i]
        
        # Store current number and its index
        num_to_index[num] = i
    
    # This should never happen given the problem constraints
    return []

# Brute force solution for comparison (O(n^2) time)
def twoSumBruteForce(nums, target):
    """
    Brute force solution - check all pairs.
    Time Complexity: O(n^2)
    Space Complexity: O(1)
    """
    n = len(nums)
    for i in range(n):
        for j in range(i + 1, n):
            if nums[i] + nums[j] == target:
                return [i, j]
    return []

# Test cases
def test_two_sum():
    # Test case 1
    nums1 = [3, 4, 5, 6]
    target1 = 7
    result1 = twoSum(nums1, target1)
    print(f"Input: nums = {nums1}, target = {target1}")
    print(f"Output: {result1}")
    print(f"Expected: [0, 1]")
    print(f"Verification: nums[{result1[0]}] + nums[{result1[1]}] = {nums1[result1[0]]} + {nums1[result1[1]]} = {nums1[result1[0]] + nums1[result1[1]]}")
    print()
    
    # Test case 2
    nums2 = [4, 5, 6]
    target2 = 10
    result2 = twoSum(nums2, target2)
    print(f"Input: nums = {nums2}, target = {target2}")
    print(f"Output: {result2}")
    print(f"Expected: [0, 2]")
    print(f"Verification: nums[{result2[0]}] + nums[{result2[1]}] = {nums2[result2[0]]} + {nums2[result2[1]]} = {nums2[result2[0]] + nums2[result2[1]]}")
    print()
    
    # Test case 3
    nums3 = [5, 5]
    target3 = 10
    result3 = twoSum(nums3, target3)
    print(f"Input: nums = {nums3}, target = {target3}")
    print(f"Output: {result3}")
    print(f"Expected: [0, 1]")
    print(f"Verification: nums[{result3[0]}] + nums[{result3[1]}] = {nums3[result3[0]]} + {nums3[result3[1]]} = {nums3[result3[0]] + nums3[result3[1]]}")
    print()
    
    # Additional test cases
    # Negative numbers
    nums4 = [-1, -2, -3, -4, -5]
    target4 = -8
    result4 = twoSum(nums4, target4)
    print(f"Input: nums = {nums4}, target = {target4}")
    print(f"Output: {result4}")
    print(f"Verification: nums[{result4[0]}] + nums[{result4[1]}] = {nums4[result4[0]]} + {nums4[result4[1]]} = {nums4[result4[0]] + nums4[result4[1]]}")
    print()
    
    # Mixed positive and negative
    nums5 = [2, 7, 11, 15]
    target5 = 9
    result5 = twoSum(nums5, target5)
    print(f"Input: nums = {nums5}, target = {target5}")
    print(f"Output: {result5}")
    print(f"Verification: nums[{result5[0]}] + nums[{result5[1]}] = {nums5[result5[0]]} + {nums5[result5[1]]} = {nums5[result5[0]] + nums5[result5[1]]}")
    print()
    
    # Compare with brute force
    print("Comparing with brute force solution:")
    print(f"Hash map result: {twoSum(nums1, target1)}")
    print(f"Brute force result: {twoSumBruteForce(nums1, target1)}")

# Run tests
if __name__ == "__main__":
    test_two_sum()
