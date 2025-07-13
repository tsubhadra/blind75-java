#https://neetcode.io/problems/string-encode-and-decode?list=blind75
def encode(strs):
    """
    Encode a list of strings to a single string.
    
    Format: length + delimiter + string + length + delimiter + string + ...
    We use '#' as delimiter between length and string content.
    
    Time Complexity: O(m) - where m is sum of lengths of all strings
    Space Complexity: O(m) - for the encoded string
    """
    encoded = ""
    
    for s in strs:
        # Format: length + '#' + string
        encoded += str(len(s)) + '#' + s
    
    return encoded

def decode(encoded_str):
    """
    Decode a single string back to a list of strings.
    
    Time Complexity: O(m) - where m is length of encoded string
    Space Complexity: O(m + n) - for result list and strings
    """
    decoded = []
    i = 0
    
    while i < len(encoded_str):
        # Find the delimiter '#'
        delimiter_pos = encoded_str.find('#', i)
        
        # Extract the length
        length = int(encoded_str[i:delimiter_pos])
        
        # Extract the string of specified length
        start = delimiter_pos + 1
        end = start + length
        string = encoded_str[start:end]
        
        decoded.append(string)
        
        # Move to next string
        i = end
    
    return decoded

# Alternative implementation with more explicit parsing
def encode_v2(strs):
    """
    Alternative encoding implementation.
    Same format but with different string building approach.
    """
    result = []
    
    for s in strs:
        result.append(str(len(s)))
        result.append('#')
        result.append(s)
    
    return ''.join(result)

def decode_v2(encoded_str):
    """
    Alternative decoding implementation.
    More explicit about parsing the length.
    """
    decoded = []
    i = 0
    
    while i < len(encoded_str):
        # Parse length until we hit '#'
        length_str = ""
        while i < len(encoded_str) and encoded_str[i] != '#':
            length_str += encoded_str[i]
            i += 1
        
        # Skip the '#' delimiter
        i += 1
        
        # Convert length and extract string
        length = int(length_str)
        string = encoded_str[i:i + length]
        decoded.append(string)
        
        # Move past this string
        i += length
    
    return decoded

# Test cases
def test_encode_decode():
    # Test case 1
    strs1 = ["neet", "code", "love", "you"]
    encoded1 = encode(strs1)
    decoded1 = decode(encoded1)
    print(f"Input: {strs1}")
    print(f"Encoded: '{encoded1}'")
    print(f"Decoded: {decoded1}")
    print(f"Match: {strs1 == decoded1}")
    print()
    
    # Test case 2
    strs2 = ["we", "say", ":", "yes"]
    encoded2 = encode(strs2)
    decoded2 = decode(encoded2)
    print(f"Input: {strs2}")
    print(f"Encoded: '{encoded2}'")
    print(f"Decoded: {decoded2}")
    print(f"Match: {strs2 == decoded2}")
    print()
    
    # Additional test cases
    # Empty list
    strs3 = []
    encoded3 = encode(strs3)
    decoded3 = decode(encoded3)
    print(f"Input: {strs3}")
    print(f"Encoded: '{encoded3}'")
    print(f"Decoded: {decoded3}")
    print(f"Match: {strs3 == decoded3}")
    print()
    
    # Empty strings
    strs4 = ["", "a", ""]
    encoded4 = encode(strs4)
    decoded4 = decode(encoded4)
    print(f"Input: {strs4}")
    print(f"Encoded: '{encoded4}'")
    print(f"Decoded: {decoded4}")
    print(f"Match: {strs4 == decoded4}")
    print()
    
    # Strings with special characters
    strs5 = ["hello#world", "test123", "!@#$%^&*()"]
    encoded5 = encode(strs5)
    decoded5 = decode(encoded5)
    print(f"Input: {strs5}")
    print(f"Encoded: '{encoded5}'")
    print(f"Decoded: {decoded5}")
    print(f"Match: {strs5 == decoded5}")
    print()
    
    # Long strings
    strs6 = ["a" * 50, "b" * 100, "c" * 25]
    encoded6 = encode(strs6)
    decoded6 = decode(encoded6)
    print(f"Input lengths: {[len(s) for s in strs6]}")
    print(f"Encoded length: {len(encoded6)}")
    print(f"Decoded lengths: {[len(s) for s in decoded6]}")
    print(f"Match: {strs6 == decoded6}")
    print()
    
    # Strings containing numbers and delimiters
    strs7 = ["123", "45#67", "89#10#11"]
    encoded7 = encode(strs7)
    decoded7 = decode(encoded7)
    print(f"Input: {strs7}")
    print(f"Encoded: '{encoded7}'")
    print(f"Decoded: {decoded7}")
    print(f"Match: {strs7 == decoded7}")
    print()
    
    # Test alternative implementations
    print("Testing alternative implementations:")
    print(f"Original: {encode(strs1)} -> {decode(encode(strs1))}")
    print(f"Alternative: {encode_v2(strs1)} -> {decode_v2(encode_v2(strs1))}")
    print(f"Cross-compatible: {decode(encode_v2(strs1)) == decode_v2(encode(strs1))}")

# Run tests
if __name__ == "__main__":
    test_encode_decode()
