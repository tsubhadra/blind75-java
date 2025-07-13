#https://neetcode.io/problems/longest-consecutive-sequence?list=blind75
def longest_consecutive(nums):
    """
    Given an array of integers nums, return the length of the longest 
    consecutive sequence of elements that can be formed.
    
    Time Complexity: O(n)
    Space Complexity: O(n)
    """
    if not nums:
        return 0
    
    # Convert to set for O(1) lookups
    num_set = set(nums)
    max_length = 0
    
    for num in num_set:
        # Only start counting if this is the beginning of a sequence
        # (i.e., num-1 is not in the set)
        if num - 1 not in num_set:
            current_num = num
            current_length = 1
            
            # Count consecutive numbers starting from this point
            while current_num + 1 in num_set:
                current_num += 1
                current_length += 1
            
            max_length = max(max_length, current_length)
    
    return max_length


def longest_consecutive_verbose(nums):
    """
    Same algorithm but with more detailed tracking for demonstration
    """
    if not nums:
        return 0
    
    num_set = set(nums)
    max_length = 0
    longest_sequence = []
    
    for num in num_set:
        # Only start if this is the beginning of a sequence
        if num - 1 not in num_set:
            current_sequence = [num]
            current_num = num
            
            # Build the sequence
            while current_num + 1 in num_set:
                current_num += 1
                current_sequence.append(current_num)
            
            # Update maximum if this sequence is longer
            if len(current_sequence) > max_length:
                max_length = len(current_sequence)
                longest_sequence = current_sequence[:]
    
    print(f"Longest consecutive sequence: {longest_sequence}")
    return max_length


# Alternative approach using Union-Find (more complex but educational)
def longest_consecutive_union_find(nums):
    """
    Alternative solution using Union-Find data structure
    Time Complexity: O(n)
    Space Complexity: O(n)
    """
    if not nums:
        return 0
    
    # Union-Find with path compression and union by rank
    parent = {}
    rank = {}
    size = {}
    
    def find(x):
        if parent[x] != x:
            parent[x] = find(parent[x])  # Path compression
        return parent[x]
    
    def union(x, y):
        px, py = find(x), find(y)
        if px == py:
            return
        
        # Union by rank
        if rank[px] < rank[py]:
            px, py = py, px
        
        parent[py] = px
        size[px] += size[py]
        
        if rank[px] == rank[py]:
            rank[px] += 1
    
    # Initialize Union-Find
    num_set = set(nums)
    for num in num_set:
        parent[num] = num
        rank[num] = 0
        size[num] = 1
    
    # Union consecutive numbers
    for num in num_set:
        if num + 1 in num_set:
            union(num, num + 1)
    
    # Find the largest component
    return max(size[find(num)] for num in num_set)


def test_solution():
    # Test case 1
    nums1 = [2, 20, 4, 10, 3, 4, 5]
    result1 = longest_consecutive(nums1)
    print(f"Input: {nums1}")
    print(f"Output: {result1}")
    print(f"Expected: 4")
    longest_consecutive_verbose(nums1)
    print()
    
    # Test case 2  
    nums2 = [0, 3, 2, 5, 4, 6, 1, 1]
    result2 = longest_consecutive(nums2)
    print(f"Input: {nums2}")
    print(f"Output: {result2}")
    print(f"Expected: 7")
    longest_consecutive_verbose(nums2)
    print()
    
    # Additional test cases
    nums3 = [100, 4, 200, 1, 3, 2]
    result3 = longest_consecutive(nums3)
    print(f"Input: {nums3}")
    print(f"Output: {result3}")
    print(f"Expected: 4 (sequence: [1,2,3,4])")
    longest_consecutive_verbose(nums3)
    print()
    
    # Edge cases
    nums4 = []
    result4 = longest_consecutive(nums4)
    print(f"Input: {nums4}")
    print(f"Output: {result4}")
    print(f"Expected: 0")
    print()
    
    nums5 = [1]
    result5 = longest_consecutive(nums5)
    print(f"Input: {nums5}")
    print(f"Output: {result5}")
    print(f"Expected: 1")
    print()
    
    nums6 = [1, 2, 0, 1]
    result6 = longest_consecutive(nums6)
    print(f"Input: {nums6}")
    print(f"Output: {result6}")
    print(f"Expected: 3 (sequence: [0,1,2])")
    longest_consecutive_verbose(nums6)


if __name__ == "__main__":
    test_solution()
