//https://neetcode.io/problems/linked-list-cycle-detection?list=blind75
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

public class LinkedListCycleDetection {

    // SOLUTION 1: Floyd's Cycle Detection Algorithm (Tortoise and Hare) - OPTIMAL
    // Time: O(n), Space: O(1)
    // Uses two pointers moving at different speeds
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;      // Tortoise - moves 1 step
        ListNode fast = head;      // Hare - moves 2 steps

        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move 1 step
            fast = fast.next.next;      // Move 2 steps

            if (slow == fast) {         // Pointers meet = cycle detected
                return true;
            }
        }

        return false;  // fast reached end = no cycle
    }

    // SOLUTION 2: HashSet Approach
    // Time: O(n), Space: O(n)
    // Store visited nodes in a set
    public boolean hasCycleHashSet(ListNode head) {
        if (head == null) {
            return false;
        }

        Set<ListNode> visited = new HashSet<>();
        ListNode current = head;

        while (current != null) {
            if (visited.contains(current)) {
                return true;  // Found a node we've seen before
            }
            visited.add(current);
            current = current.next;
        }

        return false;  // Reached end without revisiting any node
    }

    // SOLUTION 3: Node Modification Approach (Destructive)
    // Time: O(n), Space: O(1)
    // Modifies the original list - not recommended in practice
    public boolean hasCycleModify(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode current = head;
        ListNode marker = new ListNode(Integer.MAX_VALUE);  // Special marker

        while (current != null) {
            if (current.next == marker) {
                return true;  // Found our marker = cycle detected
            }

            ListNode next = current.next;
            current.next = marker;  // Mark this node as visited
            current = next;
        }

        return false;
    }

    // SOLUTION 4: Detailed Floyd's Algorithm with Step Tracking
    // Time: O(n), Space: O(1)
    // Educational version that shows the process
    public boolean hasCycleDetailed(ListNode head) {
        if (head == null || head.next == null) {
            System.out.println("List too short for cycle");
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;
        int step = 0;

        System.out.println("Starting Floyd's Cycle Detection:");
        System.out.printf("Initial: slow at node %d, fast at node %d%n",
                getNodeIndex(head, slow), getNodeIndex(head, fast));

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            step++;

            System.out.printf("Step %d: slow at node %d (val=%d), fast at node %d (val=%d)%n",
                    step,
                    getNodeIndex(head, slow), slow.val,
                    getNodeIndex(head, fast), fast.val);

            if (slow == fast) {
                System.out.println("Pointers met! Cycle detected.");
                return true;
            }
        }

        System.out.println("Fast pointer reached end. No cycle.");
        return false;
    }

    // Helper method to get node index for visualization
    private int getNodeIndex(ListNode head, ListNode target) {
        int index = 0;
        ListNode current = head;
        Set<ListNode> visited = new HashSet<>();

        while (current != null) {
            if (current == target) {
                return index;
            }
            if (visited.contains(current)) {
                return -1; // Cycle without finding target
            }
            visited.add(current);
            current = current.next;
            index++;
        }
        return -1; // Not found
    }

    // SOLUTION 5: Cycle Detection with Cycle Length
    // Time: O(n), Space: O(1)
    // Not only detects cycle but also finds its length
    public int detectCycleLength(ListNode head) {
        if (head == null || head.next == null) {
            return 0; // No cycle
        }

        ListNode slow = head;
        ListNode fast = head;

        // Phase 1: Detect if cycle exists
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                // Phase 2: Find cycle length
                int length = 1;
                ListNode temp = slow.next;

                while (temp != slow) {
                    temp = temp.next;
                    length++;
                }

                return length;
            }
        }

        return 0; // No cycle
    }

    // SOLUTION 6: Cycle Detection with Starting Point
    // Time: O(n), Space: O(1)
    // Finds the node where the cycle begins
    public ListNode detectCycleStart(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        // Phase 1: Detect if cycle exists
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                // Phase 2: Find cycle start
                ListNode start = head;
                while (start != slow) {
                    start = start.next;
                    slow = slow.next;
                }
                return start;
            }
        }

        return null; // No cycle
    }

    // SOLUTION 7: Recursive Approach (Not practical due to stack overflow risk)
    // Time: O(n), Space: O(n)
    public boolean hasCycleRecursive(ListNode head) {
        return hasCycleHelper(head, new HashSet<>());
    }

    private boolean hasCycleHelper(ListNode node, Set<ListNode> visited) {
        if (node == null) {
            return false;
        }

        if (visited.contains(node)) {
            return true;
        }

        visited.add(node);
        return hasCycleHelper(node.next, visited);
    }

    // SOLUTION 8: Boundary-based Detection (Using null check optimization)
    // Time: O(n), Space: O(1)
    // Slight optimization of Floyd's algorithm
    public boolean hasCycleOptimized(ListNode head) {
        if (head == null) return false;

        ListNode slow = head;
        ListNode fast = head.next; // Start fast one step ahead

        while (fast != null && fast.next != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        return false;
    }

    // Helper methods for testing

    // Create a linked list with a cycle
    public ListNode createListWithCycle(int[] values, int cycleIndex) {
        if (values.length == 0) return null;

        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        ListNode cycleStart = null;

        if (cycleIndex == 0) {
            cycleStart = head;
        }

        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;

            if (i == cycleIndex) {
                cycleStart = current;
            }
        }

        // Create cycle if cycleIndex is valid
        if (cycleIndex >= 0 && cycleIndex < values.length) {
            current.next = cycleStart;
        }

        return head;
    }

    // Create a linear linked list (no cycle)
    public ListNode createLinearList(int[] values) {
        return createListWithCycle(values, -1);
    }

    // Print list up to a certain limit (to avoid infinite loop)
    public void printList(ListNode head, int maxNodes) {
        System.out.print("[");
        ListNode current = head;
        int count = 0;

        while (current != null && count < maxNodes) {
            System.out.print(current.val);
            if (current.next != null && count < maxNodes - 1) {
                System.out.print(",");
            }
            current = current.next;
            count++;
        }

        if (current != null) {
            System.out.print("...");
        }
        System.out.println("]");
    }

    // Test method to verify solutions
    public static void main(String[] args) {
        LinkedListCycleDetection solution = new LinkedListCycleDetection();

        // Test case 1: List with cycle
        System.out.println("Test Case 1: [1,2,3,4] with cycle at index 1");
        ListNode head1 = solution.createListWithCycle(new int[]{1, 2, 3, 4}, 1);

        System.out.print("List structure: ");
        solution.printList(head1, 10);
        System.out.println("Has cycle (Floyd's): " + solution.hasCycle(head1));
        System.out.println("Has cycle (HashSet): " + solution.hasCycleHashSet(head1));
        System.out.println("Expected: true");

        // Create fresh list for detailed analysis (previous one might be modified)\n        ListNode head1Fresh = solution.createListWithCycle(new int[]{1, 2, 3, 4}, 1);
        System.out.println("\\nDetailed analysis:");
        solution.hasCycleDetailed(head1Fresh);

        // Test cycle length and start
        ListNode head1Fresh2 = solution.createListWithCycle(new int[]{1, 2, 3, 4}, 1);
        int cycleLength = solution.detectCycleLength(head1Fresh2);
        System.out.println("Cycle length: " + cycleLength);

        ListNode head1Fresh3 = solution.createListWithCycle(new int[]{1, 2, 3, 4}, 1);
        ListNode cycleStart = solution.detectCycleStart(head1Fresh3);
        System.out.println("Cycle starts at node with value: " + (cycleStart != null ? cycleStart.val : "none"));

        // Test case 2: Linear list (no cycle)
        System.out.println("\\n" + "=".repeat(60));
        System.out.println("Test Case 2: [1,2] with no cycle");
        ListNode head2 = solution.createLinearList(new int[]{1, 2});

        System.out.print("List structure: ");
        solution.printList(head2, 10);
        System.out.println("Has cycle (Floyd's): " + solution.hasCycle(head2));
        System.out.println("Has cycle (HashSet): " + solution.hasCycleHashSet(head2));
        System.out.println("Expected: false");

        // Test case 3: Single node with self-cycle
        System.out.println("\\n" + "=".repeat(60));
        System.out.println("Test Case 3: [1] with self-cycle");
        ListNode head3 = solution.createListWithCycle(new int[]{1}, 0);

        System.out.println("Has cycle (Floyd's): " + solution.hasCycle(head3));
        System.out.println("Expected: true");

        // Test case 4: Empty list
        System.out.println("\\n" + "=".repeat(60));
        System.out.println("Test Case 4: Empty list");
        ListNode head4 = null;

        System.out.println("Has cycle (Floyd's): " + solution.hasCycle(head4));
        System.out.println("Expected: false");

        // Test case 5: Two nodes with cycle
        System.out.println("\\n" + "=".repeat(60));
        System.out.println("Test Case 5: [1,2] with cycle at index 0");
        ListNode head5 = solution.createListWithCycle(new int[]{1, 2}, 0);

        System.out.println("Has cycle (Floyd's): " + solution.hasCycle(head5));
        System.out.println("Expected: true");

        // Test case 6: Large list with cycle at end
        System.out.println("\\n" + "=".repeat(60));
        System.out.println("Test Case 6: [1,2,3,4,5,6] with cycle at index 3");
        ListNode head6 = solution.createListWithCycle(new int[]{1, 2, 3, 4, 5, 6}, 3);

        System.out.print("List structure: ");
        solution.printList(head6, 15);
        System.out.println("Has cycle (Floyd's): " + solution.hasCycle(head6));
        System.out.println("Expected: true");

        // Performance comparison
        System.out.println("\\n" + "=".repeat(60));
        System.out.println("Performance Analysis:");
        System.out.println("Floyd's Algorithm: O(n) time, O(1) space - OPTIMAL");
        System.out.println("HashSet Approach: O(n) time, O(n) space - Simple but uses memory");
        System.out.println("Node Modification: O(n) time, O(1) space - Destructive");
        System.out.println("\\nFloyd's Algorithm Advantages:");
        System.out.println("- Constant space usage");
        System.out.println("- No modification of original list");
        System.out.println("- Works even with read-only data structures");
        System.out.println("- Can be extended to find cycle start and length");
    }
}