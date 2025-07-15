https://neetcode.io/problems/reorder-linked-list?list=blind75
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

public class ReorderLinkedList {

    // SOLUTION 1: Optimal Three-Step Approach (Recommended)
    // Time: O(n), Space: O(1)
    // Step 1: Find middle, Step 2: Reverse second half, Step 3: Merge alternately
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        // Step 1: Find the middle of the linked list using slow/fast pointers
        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse the second half
        ListNode secondHalf = reverseList(slow.next);
        slow.next = null;  // Cut the connection

        // Step 3: Merge the two halves alternately
        mergeTwoLists(head, secondHalf);
    }

    // Helper method to reverse a linked list
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }

        return prev;
    }

    // Helper method to merge two lists alternately
    private void mergeTwoLists(ListNode first, ListNode second) {
        while (second != null) {
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;

            first.next = second;
            second.next = firstNext;

            first = firstNext;
            second = secondNext;
        }\n    }\n    \n    // SOLUTION 2: Using ArrayList (Simpler but uses extra space)\n    // Time: O(n), Space: O(n)\n    public void reorderListArrayList(ListNode head) {\n        if (head == null || head.next == null) {\n            return;\n        }\n        \n        // Step 1: Store all nodes in ArrayList\n        List<ListNode> nodes = new ArrayList<>();\n        ListNode current = head;\n        \n        while (current != null) {\n            nodes.add(current);\n            current = current.next;\n        }\n        \n        // Step 2: Reorder using two pointers\n        int left = 0;\n        int right = nodes.size() - 1;\n        \n        while (left < right) {\n            nodes.get(left).next = nodes.get(right);\n            left++;\n            \n            if (left < right) {\n                nodes.get(right).next = nodes.get(left);\n                right--;\n            }\n        }\n        \n        // Terminate the list\n        nodes.get(left).next = null;\n    }\n    \n    // SOLUTION 3: Using Stack (Alternative approach)\n    // Time: O(n), Space: O(n)\n    public void reorderListStack(ListNode head) {\n        if (head == null || head.next == null) {\n            return;\n        }\n        \n        // Step 1: Push all nodes to stack\n        Stack<ListNode> stack = new Stack<>();\n        ListNode current = head;\n        int length = 0;\n        \n        while (current != null) {\n            stack.push(current);\n            current = current.next;\n            length++;\n        }\n        \n        // Step 2: Rebuild list by alternating between start and stack\n        current = head;\n        for (int i = 0; i < length / 2; i++) {\n            ListNode fromEnd = stack.pop();\n            ListNode nextNode = current.next;\n            \n            current.next = fromEnd;\n            fromEnd.next = nextNode;\n            current = nextNode;\n        }\n        \n        // Terminate the list\n        current.next = null;\n    }\n    \n    // SOLUTION 4: Detailed Step-by-Step Approach (Educational)\n    // Time: O(n), Space: O(1)\n    public void reorderListDetailed(ListNode head) {\n        if (head == null || head.next == null) {\n            System.out.println(\"List too short to reorder\");\n            return;\n        }\n        \n        System.out.println(\"Original list:\");\n        printList(head);\n        \n        // Step 1: Find middle\n        System.out.println(\"\\nStep 1: Finding middle...\");\n        ListNode slow = head;\n        ListNode fast = head;\n        \n        while (fast.next != null && fast.next.next != null) {\n            slow = slow.next;\n            fast = fast.next.next;\n        }\n        \n        System.out.println(\"Middle found at value: \" + slow.val);\n        \n        // Step 2: Split and reverse second half\n        System.out.println(\"\\nStep 2: Splitting and reversing second half...\");\n        ListNode secondHalf = slow.next;\n        slow.next = null;\n        \n        System.out.print(\"First half: \");\n        printList(head);\n        System.out.print(\"Second half before reverse: \");\n        printList(secondHalf);\n        \n        secondHalf = reverseList(secondHalf);\n        System.out.print(\"Second half after reverse: \");\n        printList(secondHalf);\n        \n        // Step 3: Merge alternately\n        System.out.println(\"\\nStep 3: Merging alternately...\");\n        mergeTwoListsDetailed(head, secondHalf);\n        \n        System.out.print(\"Final reordered list: \");\n        printList(head);\n    }\n    \n    // Detailed merge for educational purposes\n    private void mergeTwoListsDetailed(ListNode first, ListNode second) {\n        int step = 1;\n        while (second != null) {\n            ListNode firstNext = first.next;\n            ListNode secondNext = second.next;\n            \n            System.out.printf(\"  Step %d: Connecting %d -> %d -> %s\\n\", \n                            step, first.val, second.val, \n                            firstNext != null ? String.valueOf(firstNext.val) : \"null\");\n            \n            first.next = second;\n            second.next = firstNext;\n            \n            first = firstNext;\n            second = secondNext;\n            step++;\n        }\n    }\n    \n    // SOLUTION 5: Recursive Approach (Not recommended due to stack overflow risk)\n    // Time: O(n), Space: O(n)\n    public void reorderListRecursive(ListNode head) {\n        if (head == null || head.next == null) {\n            return;\n        }\n        \n        int length = getLength(head);\n        reorderHelper(head, length);\n    }\n    \n    private ListNode reorderHelper(ListNode head, int length) {\n        if (length == 1) {\n            ListNode tail = head.next;\n            head.next = null;\n            return tail;\n        }\n        \n        if (length == 2) {\n            ListNode tail = head.next;\n            head.next.next = null;\n            return tail;\n        }\n        \n        ListNode tail = reorderHelper(head.next, length - 2);\n        ListNode nextTail = tail.next;\n        tail.next = head.next;\n        head.next = tail;\n        \n        return nextTail;\n    }\n    \n    private int getLength(ListNode head) {\n        int length = 0;\n        while (head != null) {\n            length++;\n            head = head.next;\n        }\n        return length;\n    }\n    \n    // SOLUTION 6: Using Deque (Double-ended Queue)\n    // Time: O(n), Space: O(n)\n    public void reorderListDeque(ListNode head) {\n        if (head == null || head.next == null) {\n            return;\n        }\n        \n        // Store all nodes in deque\n        Deque<ListNode> deque = new ArrayDeque<>();\n        ListNode current = head;\n        \n        while (current != null) {\n            deque.addLast(current);\n            current = current.next;\n        }\n        \n        // Rebuild by alternating between front and back\n        ListNode dummy = new ListNode(0);\n        current = dummy;\n        boolean fromFront = true;\n        \n        while (!deque.isEmpty()) {\n            if (fromFront) {\n                current.next = deque.removeFirst();\n            } else {\n                current.next = deque.removeLast();\n            }\n            current = current.next;\n            fromFront = !fromFront;\n        }\n        \n        current.next = null;\n        \n        // Copy result back to original head\n        current = dummy.next;\n        ListNode original = head;\n        while (current != null) {\n            original.val = current.val;\n            original = original.next;\n            current = current.next;\n        }\n    }\n    \n    // Helper methods for testing\n    \n    // Create linked list from array\n    public ListNode createList(int[] values) {\n        if (values.length == 0) return null;\n        \n        ListNode head = new ListNode(values[0]);\n        ListNode current = head;\n        \n        for (int i = 1; i < values.length; i++) {\n            current.next = new ListNode(values[i]);\n            current = current.next;\n        }\n        \n        return head;\n    }\n    \n    // Print linked list\n    public void printList(ListNode head) {\n        if (head == null) {\n            System.out.println(\"[]\");\n            return;\n        }\n        \n        System.out.print(\"[\");\n        while (head != null) {\n            System.out.print(head.val);\n            if (head.next != null) {\n                System.out.print(\",\");\n            }\n            head = head.next;\n        }\n        System.out.println(\"]\");\n    }\n    \n    // Convert linked list to array for comparison\n    public int[] listToArray(ListNode head) {\n        List<Integer> result = new ArrayList<>();\n        while (head != null) {\n            result.add(head.val);\n            head = head.next;\n        }\n        return result.stream().mapToIn