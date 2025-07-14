#https://neetcode.io/problems/is-palindrome?list=blind75
def is_palindrome(s):
    """
    Given a string s, return true if it is a palindrome, otherwise return false.
    A palindrome is case-insensitive and ignores non-alphanumeric characters.
    
    Time Complexity: O(n)
    Space Complexity: O(1)
    """
    left = 0
    right = len(s) - 1
    
    while left < right:
        # Skip non-alphanumeric characters from left
        while left < right and not s[left].isalnum():
            left += 1
        
        # Skip non-alphanumeric characters from right
        while left < right and not s[right].isalnum():
            right -= 1
        
        # Compare characters (case-insensitive)
        if s[left].lower() != s[right].lower():
            return False
        
        left += 1
        right -= 1
    
    return True


def is_palindrome_cleaned(s):
    """
    Alternative approach: clean string first, then check
    Time Complexity: O(n)
    Space Complexity: O(n) - due to cleaned string
    """
    # Clean the string: keep only alphanumeric and convert to lowercase
    cleaned = ''.join(char.lower() for char in s if char.isalnum())
    
    # Check if cleaned string equals its reverse
    return cleaned == cleaned[::-1]


def is_palindrome_with_steps(s):
    """
    Same as main solution but with step-by-step output for demonstration
    """
    print(f"Checking palindrome for: '{s}'")
    
    left = 0
    right = len(s) - 1
    valid_chars = []
    
    while left < right:
        # Skip non-alphanumeric from left
        while left < right and not s[left].isalnum():
            left += 1
        
        # Skip non-alphanumeric from right
        while left < right and not s[right].isalnum():
            right -= 1
        
        left_char = s[left].lower()
        right_char = s[right].lower()
        
        print(f"Comparing: '{left_char}' (pos {left}) vs '{right_char}' (pos {right})")
        
        if left_char != right_char:
            print(f"Mismatch found: '{left_char}' != '{right_char}'")
            return False
        
        valid_chars.append(left_char)
        left += 1
        right -= 1
    
    # Handle middle character for odd length
    if left == right and s[left].isalnum():
        valid_chars.append(s[left].lower())
    
    print(f"Valid characters processed: {''.join(valid_chars)}")
    return True


# Helper function to demonstrate the cleaning process
def show_cleaning_process(s):
    """
    Shows step-by-step how the string gets cleaned
    """
    print(f"Original: '{s}'")
    
    # Step 1: Keep only alphanumeric
    alphanumeric_only = ''.join(char for char in s if char.isalnum())
    print(f"Alphanumeric only: '{alphanumeric_only}'")
    
    # Step 2: Convert to lowercase
    cleaned = alphanumeric_only.lower()
    print(f"Lowercase: '{cleaned}'")
    
    # Step 3: Check if palindrome
    reversed_str = cleaned[::-1]
    print(f"Reversed: '{reversed_str}'")
    print(f"Is palindrome: {cleaned == reversed_str}")
    
    return cleaned == reversed_str


def test_solution():
    test_cases = [
        "Was it a car or a cat I saw?",
        "tab a cat",
        "A man, a plan, a canal: Panama",
        "race a car",
        "Madam",
        "No 'x' in Nixon",
        "Mr. Owl ate my metal worm",
        "",
        "a",
        "Aa",
        "ab",
        "12321",
        "A Santa at NASA"
    ]
    
    print("=== Testing Palindrome Checker ===\n")
    
    for i, test_case in enumerate(test_cases, 1):
        print(f"Test Case {i}:")
        result = is_palindrome(test_case)
        print(f"Input: '{test_case}'")
        print(f"Output: {result}")
        
        # Show cleaning process for first few examples
        if i <= 3:
            print("Cleaning process:")
            show_cleaning_process(test_case)
        
        print("-" * 50)


def performance_comparison():
    """
    Compare performance of different approaches
    """
    import time
    
    # Test string
    test_str = "A man, a plan, a canal: Panama" * 100
    
    # Test two-pointer approach
    start = time.time()
    for _ in range(1000):
        is_palindrome(test_str)
    two_pointer_time = time.time() - start
    
    # Test cleaning approach
    start = time.time()
    for _ in range(1000):
        is_palindrome_cleaned(test_str)
    cleaning_time = time.time() - start
    
    print(f"Two-pointer approach: {two_pointer_time:.4f} seconds")
    print(f"Cleaning approach: {cleaning_time:.4f} seconds")
    print(f"Two-pointer is {cleaning_time/two_pointer_time:.2f}x faster")


if __name__ == "__main__":
    test_solution()
    print("\n=== Performance Comparison ===")
    performance_comparison()
