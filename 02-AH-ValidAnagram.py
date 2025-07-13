#https://neetcode.io/problems/is-anagram?list=blind75

def isAnagram(s, t):
    """
    Given two strings s and t, return true if the two strings are anagrams 
    of each other, otherwise return false.
    
    Time Complexity: O(n + m) - we iterate through both strings once
    Space Complexity: O(1) - we use a fixed-size array of 26 elements
    """
    # If lengths are different, they can't be anagrams
    if len(s) != len(t):
        return False
    
    # Count frequency of each character (26 lowercase letters)
    char_count = [0] * 26
    
    # Count characters in both strings
    for i in range(len(s)):
        char_count[ord(s[i]) - ord('a')] += 1
        char_count[ord(t[i]) - ord('a')] -= 1
    
    # If all counts are zero, strings are anagrams
    return all(count == 0 for count in char_count)

# Alternative solution using sorting (O(n log n) time)
def isAnagramSorting(s, t):
    """
    Alternative solution using sorting.
    Time Complexity: O(n log n + m log m)
    Space Complexity: O(1) if we don't count the space used by sorting
    """
    return sorted(s) == sorted(t)

# Alternative solution using Counter (O(n + m) time, O(1) space)
from collections import Counter

def isAnagramCounter(s, t):
    """
    Alternative solution using Counter.
    Time Complexity: O(n + m)
    Space Complexity: O(1) - at most 26 different characters
    """
    return Counter(s) == Counter(t)

# Test cases
def test_is_anagram():
    # Test case 1: Valid anagrams
    s1, t1 = "racecar", "carrace"
    result1 = isAnagram(s1, t1)
    print(f"Input: s = \"{s1}\", t = \"{t1}\"")
    print(f"Output: {result1}")
    print(f"Expected: True")
    print()
    
    # Test case 2: Not anagrams
    s2, t2 = "jar", "jam"
    result2 = isAnagram(s2, t2)
    print(f"Input: s = \"{s2}\", t = \"{t2}\"")
    print(f"Output: {result2}")
    print(f"Expected: False")
    print()
    
    # Additional test cases
    # Empty strings
    s3, t3 = "", ""
    result3 = isAnagram(s3, t3)
    print(f"Input: s = \"{s3}\", t = \"{t3}\"")
    print(f"Output: {result3}")
    print(f"Expected: True")
    print()
    
    # Different lengths
    s4, t4 = "abc", "abcd"
    result4 = isAnagram(s4, t4)
    print(f"Input: s = \"{s4}\", t = \"{t4}\"")
    print(f"Output: {result4}")
    print(f"Expected: False")
    print()
    
    # Same string
    s5, t5 = "listen", "silent"
    result5 = isAnagram(s5, t5)
    print(f"Input: s = \"{s5}\", t = \"{t5}\"")
    print(f"Output: {result5}")
    print(f"Expected: True")
    print()
    
    # Test alternative solutions
    print("Testing alternative solutions:")
    print(f"Sorting method: {isAnagramSorting(s1, t1)}")
    print(f"Counter method: {isAnagramCounter(s1, t1)}")

# Run tests
if __name__ == "__main__":
    test_is_anagram()
