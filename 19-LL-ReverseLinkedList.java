///
// https://neetcode.io/problems/reverse-a-linked-list?list=blind75Definition for singly-linked list
class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class ReverseLinkedList {

    // SOLUTION 1: Iterative Approach (Recommended)
    // Time: O(n), Space: O(1)
    // Uses three pointers to reverse the links
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode nextTemp = current.next;  // Store next node
            current.next = prev;               // Reverse the link
            prev = current;                    // Move prev forward
            current = nextTemp;                // Move current forward
        }

        return prev;  // prev is now the new head
    }

    // SOLUTION 2: Recursive Approach
    // Time: O(n), Space: O(n) due to recursion stack
    public ListNode reverseListRecursive(ListNode head) {
        // Base case: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }

        // Recursively reverse the rest of the list
        ListNode newHead = reverseListRecursive(head.next);

        // Reverse the current connection
        head.next.next = head;
        head.next = null;

        return newHead;
    }

    // SOLUTION 3: Iterative with Detailed Step Tracking (Educational)
    // Time: O(n), Space: O(1)
    public ListNode reverseListDetailed(ListNode head) {
        if (head == null) {
            System.out.println("Empty list - nothing to reverse");
            return null;
        }

        if (head.next == null) {
            System.out.println("Single node - already reversed");
            return head;
        }

        ListNode prev = null;
        ListNode current = head;
        int step = 0;

        System.out.println("Initial state:");
        printState(prev, current, step);

        while (current != null) {
            step++;
            ListNode nextTemp = current.next;

            System.out.printf("Step %d: Reversing link from %d%n", step, current.val);
            current.next = prev;

            prev = current;
            current = nextTemp;

            printState(prev, current, step);
        }

        System.out.println("Reversal complete! New head: " + prev.val);
        return prev;
    }

    // Helper method to print current state
    private void printState(ListNode prev, ListNode current, int step) {
        System.out.printf("  After step %d - prev: %s, current: %s%n",
                step,
                prev == null ? "null" : String.valueOf(prev.val),
                current == null ? "null" : String.valueOf(current.val));
    }

    // SOLUTION 4: Using Stack (Not optimal but demonstrates concept)
    // Time: O(n), Space: O(n)
    public ListNode reverseListStack(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        java.util.Stack<ListNode> stack = new java.util.Stack<>();

        // Push all nodes to stack
        ListNode current = head;
        while (current != null) {
            stack.push(current);
            current = current.next;
        }

        // Pop nodes and rebuild links
        ListNode newHead = stack.pop();
        current = newHead;

        while (!stack.isEmpty()) {
            current.next = stack.pop();
            current = current.next;
        }

        current.next = null;  // Important: terminate the list

        return newHead;
    }

    // SOLUTION 5: Two-Pass Approach (Array-based)
    // Time: O(n), Space: O(n) - Not recommended but shows alternative thinking
    public ListNode reverseListTwoPass(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // First pass: collect all values
        java.util.List<Integer> values = new java.util.ArrayList<>();
        ListNode current = head;
        while (current != null) {
            values.add(current.val);
            current = current.next;
        }

        // Second pass: rebuild list in reverse order
        ListNode dummy = new ListNode(0);
        current = dummy;

        for (int i = values.size() - 1; i >= 0; i--) {
            current.next = new ListNode(values.get(i));
            current = current.next;
        }

        return dummy.next;
    }

    // SOLUTION 6: Recursive with Helper Function
    // Time: O(n), Space: O(n)
    public ListNode reverseListRecursiveHelper(ListNode head) {
        return reverseHelper(head, null);
    }

    private ListNode reverseHelper(ListNode current, ListNode prev) {
        if (current == null) {
            return prev;
        }

        ListNode nextTemp = current.next;
        current.next = prev;

        return reverseHelper(nextTemp, current);
    }

    // SOLUTION 7: In-place with Single Pointer Manipulation
    // Time: O(n), Space: O(1)
    // Alternative implementation of iterative approach
    public ListNode reverseListSinglePointer(ListNode head) {
        ListNode prev = null;

        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;
    }

    // Helper methods for testing

    // Create linked list from array
    public ListNode createList(int[] values) {
        if (values.length == 0) {
            return null;
        }

        ListNode head = new ListNode(values[0]);
        ListNode current = head;

        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }

        return head;
    }

    // Print linked list
    public void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }

        System.out.print("[");
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) {
                System.out.print(",");
            }
            head = head.next;
        }
        System.out.println("]");
    }

    // Convert linked list to array for comparison
    public int[] listToArray(ListNode head) {
        java.util.List<Integer> result = new java.util.ArrayList<>();
        while (head != null) {
            result.add(head.val);
            head = head.next;
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    // Test method to verify solutions
    public static void main(String[] args) {
        ReverseLinkedList solution = new ReverseLinkedList();

        // Test case 1: Normal list
        System.out.println("Test Case 1: [0,1,2,3]");
        int[] values1 = {0, 1, 2, 3};
        ListNode head1 = solution.createList(values1);
        System.out.print("Original: ");
        solution.printList(head1);

        ListNode reversed1 = solution.reverseList(head1);
        System.out.print("Reversed: ");
        solution.printList(reversed1);
        System.out.println("Expected: [3,2,1,0]");

        // Test case 2: Empty list
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Test Case 2: []");
        ListNode head2 = solution.createList(new int[]{});
        System.out.print("Original: ");
        solution.printList(head2);

        ListNode reversed2 = solution.reverseList(head2);
        System.out.print("Reversed: ");
        solution.printList(reversed2);
        System.out.println("Expected: []");

        // Test case 3: Single node
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Test Case 3: [42]");
        int[] values3 = {42};
        ListNode head3 = solution.createList(values3);
        System.out.print("Original: ");
        solution.printList(head3);

        ListNode reversed3 = solution.reverseList(head3);
        System.out.print("Reversed: ");
        solution.printList(reversed3);
        System.out.println("Expected: [42]");

        // Test case 4: Two nodes
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Test Case 4: [1,2]");
        int[] values4 = {1, 2};
        ListNode head4 = solution.createList(values4);
        System.out.print("Original: ");
        solution.printList(head4);

        ListNode reversed4 = solution.reverseList(head4);
        System.out.print("Reversed: ");
        solution.printList(reversed4);
        System.out.println("Expected: [2,1]");

        // Detailed step-by-step example
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Detailed Step-by-Step Example: [1,2,3]");
        int[] values5 = {1, 2, 3};
        ListNode head5 = solution.createList(values5);
        solution.reverseListDetailed(head5);

        // Test different algorithms for correctness
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Algorithm Verification: [1,2,3,4,5]");
        int[] testValues = {1, 2, 3, 4, 5};

        ListNode iterativeResult = solution.reverseList(solution.createList(testValues));
        ListNode recursiveResult = solution.reverseListRecursive(solution.createList(testValues));
        ListNode stackResult = solution.reverseListStack(solution.createList(testValues));

        System.out.print("Iterative result: ");
        solution.printList(iterativeResult);
        System.out.print("Recursive result: ");
        solution.printList(recursiveResult);
        System.out.print("Stack result: ");
        solution.printList(stackResult);

        // Performance comparison (theoretical)
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Performance Analysis:");
        System.out.println("Iterative approach: O(n) time, O(1) space - BEST");
        System.out.println("Recursive approach: O(n) time, O(n) space - Good for small lists");
        System.out.println("Stack approach: O(n) time, O(n) space - Educational only");
        System.out.println("Array approach: O(n) time, O(n) space - Not recommended");
    }
}