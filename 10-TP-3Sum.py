#https://neetcode.io/problems/three-integer-sum?list=blind75
def three_sum(nums):
    """
    Given an integer array nums, return all triplets [nums[i], nums[j], nums[k]] 
    where nums[i] + nums[j] + nums[k] == 0, and the indices i, j, k are all distinct.
    The output should not contain any duplicate triplets.
    
    Time Complexity: O(n²)
    Space Complexity: O(1) - not counting the output array
    """
    if len(nums) < 3:
        return []
    
    nums.sort()  # Sort to enable two-pointer technique and handle duplicates
    result = []
    
    for i in range(len(nums) - 2):
        # Skip duplicate values for the first element
        if i > 0 and nums[i] == nums[i - 1]:
            continue
        
        # Two-pointer approach for the remaining two elements
        left = i + 1
        right = len(nums) - 1
        target = -nums[i]  # We want nums[left] + nums[right] = -nums[i]
        
        while left < right:
            current_sum = nums[left] + nums[right]
            
            if current_sum == target:
                # Found a valid triplet
                result.append([nums[i], nums[left], nums[right]])
                
                # Skip duplicates for the second element
                while left < right and nums[left] == nums[left + 1]:
                    left += 1
                
                # Skip duplicates for the third element
                while left < right and nums[right] == nums[right - 1]:
                    right -= 1
                
                left += 1
                right -= 1
                
            elif current_sum < target:
                left += 1  # Need a larger sum
            else:
                right -= 1  # Need a smaller sum
    
    return result


def three_sum_verbose(nums):
    """
    Same algorithm but with detailed step-by-step output for demonstration
    """
    print(f"Input: {nums}")
    
    if len(nums) < 3:
        print("Array too short for triplets")
        return []
    
    nums.sort()
    print(f"Sorted: {nums}")
    result = []
    
    for i in range(len(nums) - 2):
        if i > 0 and nums[i] == nums[i - 1]:
            print(f"Skipping duplicate at index {i}: {nums[i]}")
            continue
        
        print(f"\nFixing first element: nums[{i}] = {nums[i]}")
        print(f"Looking for pairs that sum to {-nums[i]}")
        
        left = i + 1
        right = len(nums) - 1
        target = -nums[i]
        
        while left < right:
            current_sum = nums[left] + nums[right]
            print(f"  Checking: {nums[left]} + {nums[right]} = {current_sum}, target = {target}")
            
            if current_sum == target:
                triplet = [nums[i], nums[left], nums[right]]
                result.append(triplet)
                print(f"  ✓ Found triplet: {triplet}")
                
                # Skip duplicates
                while left < right and nums[left] == nums[left + 1]:
                    left += 1
                    print(f"    Skipping duplicate left: {nums[left]}")
                
                while left < right and nums[right] == nums[right - 1]:
                    right -= 1
                    print(f"    Skipping duplicate right: {nums[right]}")
                
                left += 1
                right -= 1
                
            elif current_sum < target:
                left += 1
                print(f"    Sum too small, moving left pointer")
            else:
                right -= 1
                print(f"    Sum too large, moving right pointer")
    
    print(f"\nFinal result: {result}")
    return result


def three_sum_brute_force(nums):
    """
    Brute force solution for comparison - O(n³) time
    NOT recommended for large inputs, but useful for understanding
    """
    if len(nums) < 3:
        return []
    
    result = []
    n = len(nums)
    
    for i in range(n - 2):
        for j in range(i + 1, n - 1):
            for k in range(j + 1, n):
                if nums[i] + nums[j] + nums[k] == 0:
                    triplet = sorted([nums[i], nums[j], nums[k]])
                    if triplet not in result:
                        result.append(triplet)
    
    return result


def three_sum_with_target(nums, target):
    """
    Generalized version that finds triplets summing to any target
    """
    if len(nums) < 3:
        return []
    
    nums.sort()
    result = []
    
    for i in range(len(nums) - 2):
        if i > 0 and nums[i] == nums[i - 1]:
            continue
        
        left = i + 1
        right = len(nums) - 1
        remaining_target = target - nums[i]
        
        while left < right:
            current_sum = nums[left] + nums[right]
            
            if current_sum == remaining_target:
                result.append([nums[i], nums[left], nums[right]])
                
                while left < right and nums[left] == nums[left + 1]:
                    left += 1
                while left < right and nums[right] == nums[right - 1]:
                    right -= 1
                
                left += 1
                right -= 1
                
            elif current_sum < remaining_target:
                left += 1
            else:
                right -= 1
    
    return result


def test_solution():
    test_cases = [
        [-1, 0, 1, 2, -1, -4],
        [0, 1, 1],
        [0, 0, 0],
        [-2, 0, 1, 1, 2],
        [-1, 0, 1],
        [1, 2, -2, -1],
        [],
        [0, 0],
        [1, 1, 1],
        [-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6]
    ]
    
    print("=== Testing 3Sum Solution ===\n")
    
    for i, nums in enumerate(test_cases, 1):
        print(f"Test Case {i}:")
        result = three_sum(nums)
        print(f"Input: {nums}")
        print(f"Output: {result}")
        
        # Show detailed process for first few examples
        if i <= 2:
            print("Detailed process:")
            three_sum_verbose(nums.copy())
        
        print("-" * 60)


def performance_comparison():
    """
    Compare optimized vs brute force (for small inputs only)
    """
    import time
    
    test_array = [-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4]
    
    # Optimized solution
    start = time.time()
    for _ in range(1000):
        three_sum(test_array.copy())
    optimized_time = time.time() - start
    
    # Brute force solution
    start = time.time()
    for _ in range(1000):
        three_sum_brute_force(test_array.copy())
    brute_force_time = time.time() - start
    
    print(f"Optimized O(n²): {optimized_time:.4f} seconds")
    print(f"Brute force O(n³): {brute_force_time:.4f} seconds")
    print(f"Optimized is {brute_force_time/optimized_time:.2f}x faster")


if __name__ == "__main__":
    test_solution()
    print("\n=== Performance Comparison ===")
    performance_comparison()
