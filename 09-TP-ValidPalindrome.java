//https://neetcode.io/problems/is-palindrome?list=blind75

public class ValidPalindrome {

    // SOLUTION 1: Optimal Two-Pointer Approach (Recommended)
    // Time: O(n), Space: O(1)
    public boolean isPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return true;
        }

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric characters from left
            while (left < right && !isAlphaNumeric(s.charAt(left))) {
                left++;
            }

            // Skip non-alphanumeric characters from right
            while (left < right && !isAlphaNumeric(s.charAt(right))) {
                right--;
            }

            // Compare characters (case-insensitive)
            if (toLowerCase(s.charAt(left)) != toLowerCase(s.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    // Helper method to check if character is alphanumeric
    private boolean isAlphaNumeric(char c) {
        return (c >= 'A' && c <= 'Z') ||
                (c >= 'a' && c <= 'z') ||
                (c >= '0' && c <= '9');
    }

    // Helper method to convert to lowercase
    private char toLowerCase(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c + 32);
        }
        return c;
    }

    // SOLUTION 2: Using Built-in Java Methods
    // Time: O(n), Space: O(n) - creates new string
    public boolean isPalindromeBuiltIn(String s) {
        if (s == null || s.length() <= 1) {
            return true;
        }

        // Clean the string: keep only alphanumeric and convert to lowercase
        StringBuilder cleaned = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }

        String cleanedStr = cleaned.toString();
        String reversed = new StringBuilder(cleanedStr).reverse().toString();

        return cleanedStr.equals(reversed);
    }

    // SOLUTION 3: Clean String First, Then Check
    // Time: O(n), Space: O(n)
    public boolean isPalindromeCleanFirst(String s) {
        if (s == null || s.length() <= 1) {
            return true;
        }

        // Clean the string
        StringBuilder cleaned = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }

        // Check if cleaned string is palindrome
        String cleanedStr = cleaned.toString();
        int left = 0;
        int right = cleanedStr.length() - 1;

        while (left < right) {
            if (cleanedStr.charAt(left) != cleanedStr.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    // SOLUTION 4: Recursive Approach
    // Time: O(n), Space: O(n) due to recursion stack
    public boolean isPalindromeRecursive(String s) {
        if (s == null || s.length() <= 1) {
            return true;
        }

        return isPalindromeHelper(s, 0, s.length() - 1);
    }

    private boolean isPalindromeHelper(String s, int left, int right) {
        // Base case
        if (left >= right) {
            return true;
        }

        // Skip non-alphanumeric characters from left
        if (!isAlphaNumeric(s.charAt(left))) {
            return isPalindromeHelper(s, left + 1, right);
        }

        // Skip non-alphanumeric characters from right
        if (!isAlphaNumeric(s.charAt(right))) {
            return isPalindromeHelper(s, left, right - 1);
        }

        // Compare characters
        if (toLowerCase(s.charAt(left)) != toLowerCase(s.charAt(right))) {
            return false;
        }

        return isPalindromeHelper(s, left + 1, right - 1);
    }

    // SOLUTION 5: Using Regular Expressions (Not recommended for interviews)
    // Time: O(n), Space: O(n)
    public boolean isPalindromeRegex(String s) {
        if (s == null || s.length() <= 1) {
            return true;
        }

        // Remove non-alphanumeric characters and convert to lowercase
        String cleaned = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        // Check if palindrome
        int left = 0;
        int right = cleaned.length() - 1;

        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    // SOLUTION 6: Single Pass with StringBuilder (Alternative)
    // Time: O(n), Space: O(n)
    public boolean isPalindromeSinglePass(String s) {
        if (s == null || s.length() <= 1) {
            return true;
        }

        StringBuilder forward = new StringBuilder();
        StringBuilder backward = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isAlphaNumeric(c)) {
                char lower = toLowerCase(c);
                forward.append(lower);
                backward.insert(0, lower);
            }
        }

        return forward.toString().equals(backward.toString());
    }

    // Test method to verify solutions
    public static void main(String[] args) {
        ValidPalindrome solution = new ValidPalindrome();

        // Test case 1
        String s1 = "Was it a car or a cat I saw?";
        System.out.println("Input: \"" + s1 + "\"");
        System.out.println("Output: " + solution.isPalindrome(s1));
        System.out.println("Expected: true");
        System.out.println("Cleaned: \"wasitacaroracatisaw\"");

        // Test case 2
        String s2 = "tab a cat";
        System.out.println("\nInput: \"" + s2 + "\"");
        System.out.println("Output: " + solution.isPalindrome(s2));
        System.out.println("Expected: false");
        System.out.println("Cleaned: \"tabacat\"");

        // Test case 3: Edge cases
        String s3 = "";
        System.out.println("\nInput: \"" + s3 + "\"");
        System.out.println("Output: " + solution.isPalindrome(s3));
        System.out.println("Expected: true");

        String s4 = "a";
        System.out.println("\nInput: \"" + s4 + "\"");
        System.out.println("Output: " + solution.isPalindrome(s4));
        System.out.println("Expected: true");

        String s5 = "race a car";
        System.out.println("\nInput: \"" + s5 + "\"");
        System.out.println("Output: " + solution.isPalindrome(s5));
        System.out.println("Expected: false");
        System.out.println("Cleaned: \"raceacar\"");

        String s6 = "A man, a plan, a canal: Panama";
        System.out.println("\nInput: \"" + s6 + "\"");
        System.out.println("Output: " + solution.isPalindrome(s6));
        System.out.println("Expected: true");
        System.out.println("Cleaned: \"amanaplanacanalpanama\"");

        // Test with numbers
        String s7 = "race a e-car";
        System.out.println("\nInput: \"" + s7 + "\"");
        System.out.println("Output: " + solution.isPalindrome(s7));
        System.out.println("Expected: false");
        System.out.println("Cleaned: \"raceaecar\"");

        String s8 = "0P";
        System.out.println("\nInput: \"" + s8 + "\"");
        System.out.println("Output: " + solution.isPalindrome(s8));
        System.out.println("Expected: false");
        System.out.println("Cleaned: \"0p\"");
    }
}