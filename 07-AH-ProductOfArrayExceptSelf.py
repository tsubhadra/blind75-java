#https://neetcode.io/problems/products-of-array-discluding-self?list=blind75

def product_except_self(nums):
    """
    Given an integer array nums, return an array output where output[i] 
    is the product of all the elements of nums except nums[i].
    
    Time Complexity: O(n)
    Space Complexity: O(1) - not counting the output array
    """
    n = len(nums)
    result = [1] * n
    
    # First pass: calculate left products
    # result[i] will contain the product of all elements to the left of i
    left_product = 1
    for i in range(n):
        result[i] = left_product
        left_product *= nums[i]
    
    # Second pass: calculate right products and multiply with left products
    # We traverse from right to left, maintaining running product of right elements
    right_product = 1
    for i in range(n - 1, -1, -1):
        result[i] *= right_product
        right_product *= nums[i]
    
    return result


# Alternative solution using division (simpler but has edge cases with zeros)
def product_except_self_division(nums):
    """
    Alternative approach using division - handles zero cases
    Time Complexity: O(n)
    Space Complexity: O(1)
    """
    n = len(nums)
    result = [0] * n
    
    # Count zeros and calculate product of non-zero elements
    zero_count = 0
    total_product = 1
    
    for num in nums:
        if num == 0:
            zero_count += 1
        else:
            total_product *= num
    
    # Handle different cases based on zero count
    for i in range(n):
        if zero_count > 1:
            # More than one zero means all products are 0
            result[i] = 0
        elif zero_count == 1:
            # Exactly one zero
            if nums[i] == 0:
                result[i] = total_product
            else:
                result[i] = 0
        else:
            # No zeros
            result[i] = total_product // nums[i]
    
    return result


# Test cases
def test_solution():
    # Test case 1
    nums1 = [1, 2, 4, 6]
    result1 = product_except_self(nums1)
    print(f"Input: {nums1}")
    print(f"Output: {result1}")
    print(f"Expected: [48, 24, 12, 8]")
    print()
    
    # Test case 2
    nums2 = [-1, 0, 1, 2, 3]
    result2 = product_except_self(nums2)
    print(f"Input: {nums2}")
    print(f"Output: {result2}")
    print(f"Expected: [0, -6, 0, 0, 0]")
    print()
    
    # Additional test cases
    nums3 = [2, 3, 4, 5]
    result3 = product_except_self(nums3)
    print(f"Input: {nums3}")
    print(f"Output: {result3}")
    print(f"Expected: [60, 40, 30, 24]")
    print()
    
    nums4 = [0, 0]
    result4 = product_except_self(nums4)
    print(f"Input: {nums4}")
    print(f"Output: {result4}")
    print(f"Expected: [0, 0]")

if __name__ == "__main__":
    test_solution()
