//https://neetcode.io/problems/remove-node-from-end-of-linked-list?list=blind75
import java.util.*;

// Definition for singly-linked list
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

public class RemoveNthNodeFromEnd {

    // SOLUTION 1: Two-Pointer Approach (One Pass) - OPTIMAL
    // Time: O(n), Space: O(1)
    // Uses two pointers with n gap between them
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Use dummy node to handle edge case of removing head
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode first = dummy;  // First pointer
        ListNode second = dummy; // Second pointer

        // Move first pointer n+1 steps ahead to create gap of n
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }

        // Move both pointers until first reaches end
        while (first != null) {
            first = first.next;
            second = second.next;
        }

        // second now points to node before the one to remove
        second.next = second.next.next;

        return dummy.next;
    }

    // SOLUTION 2: Two-Pass Approach (Calculate Length First)
    // Time: O(n), Space: O(1)
    // First pass to get length, second pass to remove
    public ListNode removeNthFromEndTwoPass(ListNode head, int n) {
        // Edge case: single node
        if (head.next == null && n == 1) {
            return null;
        }

        // First pass: calculate length
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }

        // Calculate position from start (0-indexed)
        int positionFromStart = length - n;

        // Edge case: removing head
        if (positionFromStart == 0) {
            return head.next;
        }

        // Second pass: find node before target and remove
        current = head;
        for (int i = 0; i < positionFromStart - 1; i++) {
            current = current.next;
        }

        current.next = current.next.next;
        return head;
    }

    // SOLUTION 3: Using ArrayList/Stack
    // Time: O(n), Space: O(n)
    // Store all nodes then remove the nth from end
    public ListNode removeNthFromEndArrayList(ListNode head, int n) {
        List<ListNode> nodes = new ArrayList<>();\n        \n        // Store all nodes in list\n        ListNode current = head;\n        while (current != null) {\n            nodes.add(current);\n            current = current.next;\n        }\n        \n        int size = nodes.size();\n        \n        // Edge case: removing the only node\n        if (size == 1 && n == 1) {\n            return null;\n        }\n        \n        // Edge case: removing head\n        if (n == size) {\n            return head.next;\n        }\n        \n        // Remove nth node from end\n        int indexToRemove = size - n;\n        nodes.get(indexToRemove - 1).next = nodes.get(indexToRemove).next;\n        \n        return head;\n    }\n    \n    // SOLUTION 4: Recursive Approach\n    // Time: O(n), Space: O(n) due to recursion stack\n    public ListNode removeNthFromEndRecursive(ListNode head, int n) {\n        int[] count = {0};  // Use array to pass by reference\n        return removeHelper(head, n, count);\n    }\n    \n    private ListNode removeHelper(ListNode node, int n, int[] count) {\n        if (node == null) {\n            return null;\n        }\n        \n        node.next = removeHelper(node.next, n, count);\n        count[0]++;\n        \n        // If this is the nth node from end, remove it\n        if (count[0] == n) {\n            return node.next;\n        }\n        \n        return node;\n    }\n    \n    // SOLUTION 5: Detailed Two-Pointer with Step Tracking (Educational)\n    // Time: O(n), Space: O(1)\n    public ListNode removeNthFromEndDetailed(ListNode head, int n) {\n        System.out.printf(\"Removing %dth node from end\\n\", n);\n        System.out.print(\"Original list: \");\n        printList(head);\n        \n        if (head == null) {\n            System.out.println(\"Empty list - nothing to remove\");\n            return null;\n        }\n        \n        ListNode dummy = new ListNode(0);\n        dummy.next = head;\n        \n        ListNode first = dummy;\n        ListNode second = dummy;\n        \n        // Phase 1: Move first pointer n+1 steps ahead\n        System.out.printf(\"\\nPhase 1: Moving first pointer %d steps ahead\\n\", n + 1);\n        for (int i = 0; i <= n; i++) {\n            System.out.printf(\"Step %d: first at %s\\n\", i + 1, \n                            first.next != null ? \"node \" + first.next.val : \"end\");\n            first = first.next;\n        }\n        \n        // Phase 2: Move both pointers until first reaches end\n        System.out.println(\"\\nPhase 2: Moving both pointers until first reaches end\");\n        int step = 0;\n        while (first != null) {\n            step++;\n            System.out.printf(\"Step %d: first at %s, second at node %d\\n\", \n                            step,\n                            first.next != null ? \"node \" + first.next.val : \"end\",\n                            second.next.val);\n            first = first.next;\n            second = second.next;\n        }\n        \n        // Remove the node\n        ListNode nodeToRemove = second.next;\n        System.out.printf(\"\\nRemoving node with value: %d\\n\", nodeToRemove.val);\n        second.next = second.next.next;\n        \n        System.out.print(\"Result: \");\n        printList(dummy.next);\n        \n        return dummy.next;\n    }\n    \n    // SOLUTION 6: Using Stack (Alternative Approach)\n    // Time: O(n), Space: O(n)\n    public ListNode removeNthFromEndStack(ListNode head, int n) {\n        Stack<ListNode> stack = new Stack<>();\n        ListNode dummy = new ListNode(0);\n        dummy.next = head;\n        \n        // Push all nodes including dummy onto stack\n        ListNode current = dummy;\n        while (current != null) {\n            stack.push(current);\n            current = current.next;\n        }\n        \n        // Pop n nodes to get to the node before target\n        for (int i = 0; i < n; i++) {\n            stack.pop();\n        }\n        \n        // Remove the target node\n        ListNode prev = stack.peek();\n        prev.next = prev.next.next;\n        \n        return dummy.next;\n    }\n    \n    // SOLUTION 7: Iterative with Position Calculation\n    // Time: O(n), Space: O(1)\n    public ListNode removeNthFromEndIterative(ListNode head, int n) {\n        if (head == null) return null;\n        \n        // Handle single node case\n        if (head.next == null && n == 1) {\n            return null;\n        }\n        \n        ListNode dummy = new ListNode(0);\n        dummy.next = head;\n        ListNode current = dummy;\n        \n        // Count total nodes\n        int totalNodes = 0;\n        ListNode temp = head;\n        while (temp != null) {\n            totalNodes++;\n            temp = temp.next;\n        }\n        \n        // Move to position before the node to remove\n        int stepsToMove = totalNodes - n;\n        for (int i = 0; i < stepsToMove; i++) {\n            current = current.next;\n        }\n        \n        // Remove the node\n        current.next = current.next.next;\n        \n        return dummy.next;\n    }\n    \n    // Helper methods for testing\n    \n    // Create linked list from array\n    public ListNode createList(int[] values) {\n        if (values.length == 0) return null;\n        \n        ListNode head = new ListNode(values[0]);\n        ListNode current = head;\n        \n        for (int i = 1; i < values.length; i++) {\n            current.next = new ListNode(values[i]);\n            current = current.next;\n        }\n        \n        return head;\n    }\n    \n    // Print linked list\n    public void printList(ListNode head) {\n        if (head == null) {\n            System.out.println(\"[]\");\n            return;\n        }\n        \n        System.out.print(\"[\");\n        while (head != null) {\n            System.out.print(head.val);\n            if (head.next != null) {\n                System.out.print(\",\");\n            }\n            head = head.next;\n        }\n        System.out.println(\"]\");\n    }\n    \n    // Convert linked list to array for comparison\n    public int[] listToArray(ListNode head) {\n        List<Integer> result = new ArrayList<>();\n        while (head != null) {\n            result.add(head.val);\n            head = head.next;\n        }\n        return result.stream().mapToInt(i -> i).toArray();\n    }\n    \n    // Create a copy of the list for testing multiple algorithms\n    public ListNode copyList(ListNode head) {\n        if (head == null) return null;\n        \n        ListNode newHead = new ListNode(head.val);\n        ListNode current = newHead;\n        ListNode original = head.next;\n        \n        while (original != null) {\n            current.next = new ListNode(original.val);\n            current = current.next;\n            original = original.next;\n        }\n        \n        return newHead;\n    }\n    \n    // Test method to verify solutions\n    public static void main(String[] args) {\n        RemoveNthNodeFromEnd solution = new RemoveNthNodeFromEnd();\n        \n        // Test case 1: Remove 2nd from end\n        System.out.println(\"Test Case 1: [1,2,3,4], n = 2\");\n        ListNode head1 = solution.createList(new int[]{1, 2, 3, 4});\n        System.out.print(\"Original: \");\n        solution.printList(head1);\n        \n        ListNode result1 = solution.removeNthFromEnd(head1, 2);\n        System.out.print(\"Result: \");\n        solution.printList(result1);\n        System.out.println(\"Expected: [1,2,4]\\n\");\n        \n        // Test case 2: Remove only node\n        System.out.println(\"Test Case 2: [5], n = 1\");\n        ListNode head2 = solution.createList(new int[]{5});\n        System.out.print(\"Original: \");\n        solution.printList(head2);\n        \n        ListNode result2 = solution.removeNthFromEnd(head2, 1);\n        System.out.print(\"Result: \");\n        solution.printList(result2);\n        System.out.println(\"Expected: []\\n\");\n        \n        // Test case 3: Remove head\n        System.out.println(\"Test Case 3: [1,2], n = 2\");\n        ListNode head3 = solution.createList(new int[]{1, 2});\n        System.out.print(\"Original: \");\n        solution.printList(head3);\n        \n        ListNode result3 = solution.removeNthFromEnd(head3, 2);\n        System.out.print(\"Result: \");\n        solution.printList(result3);\n        System.out.println(\"Expected: [2]\\n\");\n        \n        // Test case 4: Remove last node\n        System.out.println(\"Test Case 4: [1,2,3,4,5], n = 1\");\n        ListNode head4 = solution.createList(new int[]{1, 2, 3, 4, 5});\n        System.out.print(\"Original: \");\n        solution.printList(head4);\n        \n        ListNode result4 = solution.removeNthFromEnd(head4, 1);\n        System.out.print(\"Result: \");\n        solution.printList(result4);\n        System.out.println(\"Expected: [1,2,3,4]\\n\");\n        \n        // Test case 5: Remove from middle\n        System.out.println(\"Test Case 5: [1,2,3,4,5], n = 3\");\n        ListNode head5 = solution.createList(new int[]{1, 2, 3, 4, 5});\n        System.out.print(\"Original: \");\n        solution.printList(head5);\n        \n        ListNode result5 = solution.removeNthFromEnd(head5, 3);\n        System.out.print(\"Result: \");\n        solution.printList(result5);\n        System.out.println(\"Expected: [1,2,4,5]\\n\");\n        \n        // Detailed step-by-step example\n        System.out.println(\"=\" .repeat(60));\n        System.out.println(\"Detailed Step-by-Step Example: [1,2,3,4,5], n = 2\");\n        ListNode head6 = solution.createList(new int[]{1, 2, 3, 4, 5});\n        solution.removeNthFromEndDetailed(head6, 2);\n        \n        // Test different algorithms for correctness\n        System.out.println(\"\\n\" + \"=\".repeat(60));\n        System.out.println(\"Algorithm Verification: [1,2,3,4,5,6], n = 3\");\n        \n        ListNode original = solution.createList(new int[]{1, 2, 3, 4, 5, 6});\n        \n        ListNode copy1 = solution.copyList(original);\n        ListNode result_onePass = solution.removeNthFromEnd(copy1, 3);\n        System.out.print(\"One-pass (optimal): \");\n        solution.printList(result_onePass);\n        \n        ListNode copy2 = solution.copyList(original);\n        ListNode result_twoPass = solution.removeNthFromEndTwoPass(copy2, 3);\n        System.out.print(\"Two-pass: \");\n        solution.printList(result_twoPass);\n        \n        ListNode copy3 = solution.copyList(original);\n        ListNode result_arrayList = solution.removeNthFromEndArrayList(copy3, 3);\n        System.out.print(\"ArrayList: \");\n        solution.printList(result_arrayList);\n        \n        ListNode copy4 = solution.copyList(original);\n        ListNode result_recursive = solution.removeNthFromEndRecursive(copy4, 3);\n        System.out.print(\"Recursive: \");\n        solution.printList(result_recursive);\n        \n        System.out.println(\"All should be: [1,2,3,5,6]\");\n        \n        // Edge cases\n        System.out.println(\"\\n\" + \"=\".repeat(60));\n        System.out.println(\"Edge Cases:\");\n        \n        // Two nodes, remove first\n        ListNode edge1 = solution.createList(new int[]{1, 2});\n        System.out.print(\"[1,2], n=2: \");\n        solution.printList(solution.removeNthFromEnd(edge1, 2));\n        \n        // Two nodes, remove second\n        ListNode edge2 = solution.createList(new int[]{1, 2});\n        System.out.print(\"[1,2], n=1: \");\n        solution.printList(solution.removeNthFromEnd(edge2, 1));\n        \n        // Single node\n        ListNode edge3 = solution.createList(new int[]{42});\n        System.out.print(\"[42], n=1: \");\n        solution.printList(solution.removeNthFromEnd(edge3, 1));\n        \n        // Performance analysis\n        System.out.println(\"\\n\" + \"=\".repeat(60));\n        System.out.println(\"Performance Analysis:\");\n        System.out.println(\"Two-pointer (one pass): O(n) time, O(1) space - OPTIMAL\");\n        System.out.println(\"Two-pass: O(n) time, O(1) space - Simple but two traversals\");\n        System.out.println(\"ArrayList: O(n) time, O(n) space - Uses extra memory\");\n        System.out.println(\"Recursive: O(n) time, O(n) space - Elegant but stack overhead\");\n        System.out.println(\"Stack: O(n) time, O(n) space - Alternative implementation\");\n        System.out.println(\"\\nThe two-pointer approach is preferred in interviews!\");\n    }\n}"