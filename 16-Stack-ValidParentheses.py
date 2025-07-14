#https://neetcode.io/problems/validate-parentheses?list=blind75
import java.util.*;

class Solution {
    public boolean isValid(String s) {
        // Use a stack to keep track of opening brackets
        Stack<Character> stack = new Stack<>();
        
        // Iterate through each character in the string
        for (char c : s.toCharArray()) {
            // If it's an opening bracket, push it to stack
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            // If it's a closing bracket
            else if (c == ')' || c == ']' || c == '}') {
                // If stack is empty, no matching opening bracket
                if (stack.isEmpty()) {
                    return false;
                }
                
                // Pop the last opening bracket and check if it matches
                char top = stack.pop();
                if (!isMatchingPair(top, c)) {
                    return false;
                }
            }
        }
        
        // Valid if stack is empty (all brackets are matched)
        return stack.isEmpty();
    }
    
    // Helper method to check if opening and closing brackets match
    private boolean isMatchingPair(char opening, char closing) {
        return (opening == '(' && closing == ')') ||
               (opening == '[' && closing == ']') ||
               (opening == '{' && closing == '}');
    }
    
    // Alternative solution using HashMap for cleaner matching
    public boolean isValidAlternative(String s) {
        Stack<Character> stack = new Stack<>();
        
        // Map closing brackets to their corresponding opening brackets
        Map<Character, Character> mappings = new HashMap<>();
        mappings.put(')', '(');
        mappings.put(']', '[');
        mappings.put('}', '{');
        
        for (char c : s.toCharArray()) {
            if (mappings.containsKey(c)) {
                // It's a closing bracket
                char topElement = stack.isEmpty() ? '#' : stack.pop();
                if (topElement != mappings.get(c)) {
                    return false;
                }
            } else {
                // It's an opening bracket
                stack.push(c);
            }
        }
        
        return stack.isEmpty();
    }
    
    // Space-optimized solution using character matching
    public boolean isValidOptimized(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            // Push corresponding closing bracket for opening brackets
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else {
                // For closing brackets, check if it matches the expected one
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }
    
    // Test method
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        String[] testCases = {
            "()",           // true
            "()[]{}",       // true
            "(]",           // false
            "([)]",         // false
            "{[]}",         // true
            "",             // true (empty string)
            "(((",          // false
            ")))",          // false
            "({[]})",       // true
            "([{}])"        // true
        };
        
        System.out.println("Testing main solution:");
        for (String test : testCases) {
            boolean result = solution.isValid(test);
            System.out.printf("Input: \"%s\" -> Output: %s%n", test, result);
        }
        
        System.out.println("\nTesting alternative solution:");
        for (String test : testCases) {
            boolean result = solution.isValidAlternative(test);
            System.out.printf("Input: \"%s\" -> Output: %s%n", test, result);
        }
        
        System.out.println("\nTesting optimized solution:");
        for (String test : testCases) {
            boolean result = solution.isValidOptimized(test);
            System.out.printf("Input: \"%s\" -> Output: %s%n", test, result);
        }
    }
}
