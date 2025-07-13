#https://neetcode.io/problems/max-water-container?list=blind75
def max_area(height):
    """
    Given an integer array heights, return the maximum amount of water 
    a container can store by choosing any two bars.
    
    Area = min(height[left], height[right]) * (right - left)
    
    Time Complexity: O(n)
    Space Complexity: O(1)
    """
    if len(height) < 2:
        return 0
    
    left = 0
    right = len(height) - 1
    max_water = 0
    
    while left < right:
        # Calculate area with current left and right pointers
        width = right - left
        current_height = min(height[left], height[right])
        current_area = width * current_height
        
        # Update maximum area if current is larger
        max_water = max(max_water, current_area)
        
        # Move the pointer with smaller height
        # This is the key insight: moving the taller line won't increase area
        if height[left] < height[right]:
            left += 1
        else:
            right -= 1
    
    return max_water


def max_area_verbose(height):
    """
    Same algorithm but with detailed step-by-step output for demonstration
    """
    print(f"Input heights: {height}")
    print("Finding maximum water container area...\n")
    
    if len(height) < 2:
        return 0
    
    left = 0
    right = len(height) - 1
    max_water = 0
    step = 1
    
    while left < right:
        width = right - left
        left_height = height[left]
        right_height = height[right]
        current_height = min(left_height, right_height)
        current_area = width * current_height
        
        print(f"Step {step}:")
        print(f"  Left: index {left}, height {left_height}")
        print(f"  Right: index {right}, height {right_height}")
        print(f"  Width: {width}")
        print(f"  Container height: min({left_height}, {right_height}) = {current_height}")
        print(f"  Area: {width} × {current_height} = {current_area}")
        
        if current_area > max_water:
            max_water = current_area
            print(f"  ✓ New maximum area: {max_water}")
        else:
            print(f"  Current max remains: {max_water}")
        
        # Move pointer logic
        if left_height < right_height:
            print(f"  Moving left pointer (smaller height: {left_height})")
            left += 1
        else:
            print(f"  Moving right pointer (smaller/equal height: {right_height})")
            right -= 1
        
        print()
        step += 1
    
    print(f"Final maximum area: {max_water}")
    return max_water


def max_area_brute_force(height):
    """
    Brute force solution for comparison - O(n²) time
    NOT recommended for large inputs, but useful for verification
    """
    if len(height) < 2:
        return 0
    
    max_water = 0
    n = len(height)
    
    for i in range(n):
        for j in range(i + 1, n):
            width = j - i
            container_height = min(height[i], height[j])
            area = width * container_height
            max_water = max(max_water, area)
    
    return max_water


def visualize_container(height, left_idx, right_idx):
    """
    Create a simple ASCII visualization of the container
    """
    if not height or left_idx >= right_idx:
        return
    
    max_height = max(height)
    width = right_idx - left_idx + 1
    
    print(f"Container visualization (indices {left_idx} to {right_idx}):")
    
    # Draw from top to bottom
    for level in range(max_height, 0, -1):
        line = ""
        for i in range(left_idx, right_idx + 1):
            if i == left_idx or i == right_idx:
                # Container walls
                if height[i] >= level:
                    line += "|"
                else:
                    line += " "
            else:
                # Water area
                if level <= min(height[left_idx], height[right_idx]):
                    line += "~"
                else:
                    line += " "
        print(f"{level:2d} {line}")
    
    # Draw indices
    index_line = "   "
    for i in range(left_idx, right_idx + 1):
        index_line += str(i % 10)
    print(index_line)


def why_two_pointers_work():
    """
    Explanation of why the two-pointer approach is optimal
    """
    print("Why does the two-pointer approach work?")
    print("=" * 50)
    print("Key insight: When we have two pointers at positions i and j where height[i] < height[j],")
    print("moving j inward will never give us a larger area because:")
    print("1. The width decreases")
    print("2. The height is still limited by height[i] (the smaller one)")
    print("3. Therefore, area = min(height[i], height[j]) × width can only decrease")
    print()
    print("So we should move the pointer with the smaller height, as it's the limiting factor.")
    print("This ensures we don't miss any potentially better solutions.")
    print()


def test_solution():
    test_cases = [
        [1, 7, 2, 5, 4, 7, 3, 6],
        [2, 2, 2],
        [1, 8, 6, 2, 5, 4, 8, 3, 7],
        [1, 1],
        [4, 3, 2, 1, 4],
        [1, 2, 1],
        [2, 1],
        [1, 2, 4, 3]
    ]
    
    print("=== Testing Container With Most Water ===\n")
    
    for i, heights in enumerate(test_cases, 1):
        print(f"Test Case {i}:")
        result = max_area(heights)
        brute_force_result = max_area_brute_force(heights)
        
        print(f"Input: {heights}")
        print(f"Optimized result: {result}")
        print(f"Brute force result: {brute_force_result}")
        print(f"Results match: {result == brute_force_result}")
        
        # Show detailed process for first few examples
        if i <= 2:
            print("\nDetailed process:")
            max_area_verbose(heights.copy())
        
        print("-" * 60)


def performance_comparison():
    """
    Compare optimized vs brute force performance
    """
    import time
    
    # Generate test data
    test_heights = list(range(1, 101)) + list(range(100, 0, -1))  # 200 elements
    
    # Optimized solution
    start = time.time()
    for _ in range(1000):
        max_area(test_heights)
    optimized_time = time.time() - start
    
    # Brute force solution (smaller iterations due to O(n²))
    start = time.time()
    for _ in range(10):
        max_area_brute_force(test_heights)
    brute_force_time = (time.time() - start) * 100  # Scale to match iterations
    
    print(f"Optimized O(n): {optimized_time:.4f} seconds")
    print(f"Brute force O(n²): {brute_force_time:.4f} seconds")
    print(f"Optimized is {brute_force_time/optimized_time:.2f}x faster")


if __name__ == "__main__":
    why_two_pointers_work()
    test_solution()
    print("\n=== Performance Comparison ===")
    performance_comparison()
