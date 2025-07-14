#https://neetcode.io/problems/duplicate-integer?list=blind75
def containsDuplicate(nums):
    """
    Given an integer array nums, return true if any value appears 
    more than once in the array, otherwise return false.
    
    Time Complexity: O(n) - we iterate through the array once
    Space Complexity: O(n) - in worst case, we store all elements in the set
    """
    seen = set()
    
    for num in nums:
        if num in seen:
            return True
        seen.add(num)
    
    return False

# Test cases
def test_contains_duplicate():
    # Test case 1: Array with duplicates
    nums1 = [1, 2, 3, 3]
    result1 = containsDuplicate(nums1)
    print(f"Input: {nums1}")
    print(f"Output: {result1}")
    print(f"Expected: True")
    print()
    
    # Test case 2: Array without duplicates
    nums2 = [1, 2, 3, 4]
    result2 = containsDuplicate(nums2)
    print(f"Input: {nums2}")
    print(f"Output: {result2}")
    print(f"Expected: False")
    print()
    
    # Additional test cases
    # Empty array
    nums3 = []
    result3 = containsDuplicate(nums3)
    print(f"Input: {nums3}")
    print(f"Output: {result3}")
    print(f"Expected: False")
    print()
    
    # Single element
    nums4 = [1]
    result4 = containsDuplicate(nums4)
    print(f"Input: {nums4}")
    print(f"Output: {result4}")
    print(f"Expected: False")
    print()
    
    # All same elements
    nums5 = [1, 1, 1, 1]
    result5 = containsDuplicate(nums5)
    print(f"Input: {nums5}")
    print(f"Output: {result5}")
    print(f"Expected: True")

# Run tests
if __name__ == "__main__":
    test_contains_duplicate()
