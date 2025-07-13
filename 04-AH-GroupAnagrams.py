#https://neetcode.io/problems/anagram-groups?list=blind75
def groupAnagrams(strs):
    """
    Given an array of strings strs, group all anagrams together into sublists.
    
    Time Complexity: O(m * n) - where m is number of strings, n is max string length
    Space Complexity: O(m) - for storing the result groups
    """
    from collections import defaultdict
    
    # Use defaultdict to automatically create empty lists for new keys
    anagram_groups = defaultdict(list)
    
    for s in strs:
        # Create a frequency count as the key
        # Using a tuple of character counts (a-z)
        char_count = [0] * 26
        for char in s:
            char_count[ord(char) - ord('a')] += 1
        
        # Convert to tuple so it can be used as a dictionary key
        key = tuple(char_count)
        anagram_groups[key].append(s)
    
    # Return all groups as a list of lists
    return list(anagram_groups.values())

# Alternative solution using sorted strings as keys
def groupAnagramsSorted(strs):
    """
    Alternative solution using sorted strings as keys.
    Time Complexity: O(m * n * log n) - due to sorting each string
    Space Complexity: O(m * n) - for storing sorted strings as keys
    """
    from collections import defaultdict
    
    anagram_groups = defaultdict(list)
    
    for s in strs:
        # Sort the string to get a canonical form
        sorted_str = ''.join(sorted(s))
        anagram_groups[sorted_str].append(s)
    
    return list(anagram_groups.values())

# Helper function to create character frequency key
def get_char_frequency_key(s):
    """
    Create a frequency-based key for a string.
    Returns a tuple representing character frequencies.
    """
    char_count = [0] * 26
    for char in s:
        char_count[ord(char) - ord('a')] += 1
    return tuple(char_count)

# Test cases
def test_group_anagrams():
    # Test case 1
    strs1 = ["act", "pots", "tops", "cat", "stop", "hat"]
    result1 = groupAnagrams(strs1)
    print(f"Input: {strs1}")
    print(f"Output: {result1}")
    print(f"Expected: [['hat'], ['act', 'cat'], ['stop', 'pots', 'tops']] (order may vary)")
    print()
    
    # Test case 2
    strs2 = ["x"]
    result2 = groupAnagrams(strs2)
    print(f"Input: {strs2}")
    print(f"Output: {result2}")
    print(f"Expected: [['x']]")
    print()
    
    # Test case 3
    strs3 = [""]
    result3 = groupAnagrams(strs3)
    print(f"Input: {strs3}")
    print(f"Output: {result3}")
    print(f"Expected: [['']]")
    print()
    
    # Additional test cases
    # All same anagrams
    strs4 = ["abc", "bca", "cab", "acb"]
    result4 = groupAnagrams(strs4)
    print(f"Input: {strs4}")
    print(f"Output: {result4}")
    print(f"Expected: [['abc', 'bca', 'cab', 'acb']]")
    print()
    
    # No anagrams
    strs5 = ["a", "b", "c"]
    result5 = groupAnagrams(strs5)
    print(f"Input: {strs5}")
    print(f"Output: {result5}")
    print(f"Expected: [['a'], ['b'], ['c']]")
    print()
    
    # Mixed lengths
    strs6 = ["eat", "tea", "tan", "ate", "nat", "bat"]
    result6 = groupAnagrams(strs6)
    print(f"Input: {strs6}")
    print(f"Output: {result6}")
    print(f"Expected: [['eat', 'tea', 'ate'], ['tan', 'nat'], ['bat']] (order may vary)")
    print()
    
    # Compare with sorted solution
    print("Comparing with sorted solution:")
    print(f"Frequency method: {groupAnagrams(strs1)}")
    print(f"Sorted method: {groupAnagramsSorted(strs1)}")
    print()
    
    # Show how keys work
    print("Example of frequency keys:")
    for s in ["act", "cat", "hat"]:
        key = get_char_frequency_key(s)
        print(f"'{s}' -> key: {key}")

# Run tests
if __name__ == "__main__":
    test_group_anagrams()
