//https://neetcode.io/problems/merge-two-sorted-linked-lists?list=blind75
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

public class MergeTwoSortedLists {

    // SOLUTION 1: Iterative Approach with Dummy Node (Recommended)
    // Time: O(m + n), Space: O(1)
    // Uses a dummy node to simplify edge case handling
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Create a dummy node to simplify the logic
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // Merge nodes while both lists have elements
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // Append remaining nodes (if any)
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }

        return dummy.next;  // Return the actual head (skip dummy)
    }

    // SOLUTION 2: Recursive Approach
    // Time: O(m + n), Space: O(m + n) due to recursion stack
    public ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        // Base cases
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        // Choose the smaller node as the current head
        if (list1.val <= list2.val) {
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsRecursive(list1, list2.next);
            return list2;
        }
    }

    // SOLUTION 3: Iterative without Dummy Node
    // Time: O(m + n), Space: O(1)
    // More complex but shows how to handle without dummy node
    public ListNode mergeTwoListsNoDummy(ListNode list1, ListNode list2) {
        // Handle edge cases
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        // Determine the head of the merged list
        ListNode head;
        if (list1.val <= list2.val) {
            head = list1;
            list1 = list1.next;
        } else {
            head = list2;
            list2 = list2.next;
        }

        ListNode current = head;

        // Merge the remaining nodes
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // Append remaining nodes
        current.next = (list1 != null) ? list1 : list2;

        return head;
    }

    // SOLUTION 4: Detailed Step-by-Step Approach (Educational)
    // Time: O(m + n), Space: O(1)
    public ListNode mergeTwoListsDetailed(ListNode list1, ListNode list2) {
        System.out.println("Starting merge process...");
        printList("List1", list1);
        printList("List2", list2);

        if (list1 == null && list2 == null) {
            System.out.println("Both lists are empty");
            return null;
        }

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int step = 0;

        while (list1 != null && list2 != null) {
            step++;
            System.out.printf("Step %d: Comparing %d (list1) vs %d (list2)%n",
                    step, list1.val, list2.val);

            if (list1.val <= list2.val) {
                System.out.printf("  Choosing %d from list1%n", list1.val);
                current.next = list1;
                list1 = list1.next;
            } else {
                System.out.printf("  Choosing %d from list2%n", list2.val);
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;

            System.out.print("  Current merged list: ");
            printPartialList(dummy.next);
        }

        // Handle remaining nodes
        if (list1 != null) {
            System.out.println("Appending remaining nodes from list1");
            current.next = list1;
        } else if (list2 != null) {
            System.out.println("Appending remaining nodes from list2");
            current.next = list2;
        }

        System.out.print("Final merged list: ");
        printList("", dummy.next);

        return dummy.next;
    }

    // SOLUTION 5: In-place Merge (Modifies original lists)
    // Time: O(m + n), Space: O(1)
    // Reuses existing nodes without creating new ones
    public ListNode mergeTwoListsInPlace(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        // Ensure list1 starts with the smaller value
        if (list1.val > list2.val) {
            ListNode temp = list1;
            list1 = list2;
            list2 = temp;
        }

        ListNode result = list1;

        while (list1 != null && list2 != null) {
            ListNode temp = null;

            // Find the correct position in list1 to insert from list2
            while (list1 != null && list1.val <= list2.val) {
                temp = list1;
                list1 = list1.next;
            }

            // Insert the node from list2
            temp.next = list2;
            list2 = list2.next;
            temp.next.next = list1;
        }

        return result;
    }

    // SOLUTION 6: Using Priority Queue (Overkill for 2 lists)
    // Time: O((m + n) log(m + n)), Space: O(m + n)
    // Demonstrates how to handle multiple sorted lists
    public ListNode mergeTwoListsPriorityQueue(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) return null;

        java.util.PriorityQueue<ListNode> pq = new java.util.PriorityQueue<>(
                (a, b) -> Integer.compare(a.val, b.val)
        );

        // Add all nodes to priority queue
        while (list1 != null) {
            pq.offer(list1);
            list1 = list1.next;
        }

        while (list2 != null) {
            pq.offer(list2);
            list2 = list2.next;
        }

        // Build result from priority queue
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (!pq.isEmpty()) {
            current.next = pq.poll();
            current = current.next;
        }

        current.next = null;  // Important: terminate the list
        return dummy.next;
    }

    // Helper methods for testing and visualization

    // Create linked list from array
    public ListNode createList(int[] values) {
        if (values.length == 0) return null;

        ListNode head = new ListNode(values[0]);
        ListNode current = head;

        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }

        return head;
    }

    // Print linked list
    public void printList(String label, ListNode head) {
        System.out.print(label + (label.isEmpty() ? "" : ": "));
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

    // Print partial list for step-by-step visualization
    private void printPartialList(ListNode head) {
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
        MergeTwoSortedLists solution = new MergeTwoSortedLists();

        // Test case 1: Normal merge
        System.out.println("Test Case 1: [1,2,4] + [1,3,5]");
        ListNode list1_1 = solution.createList(new int[]{1, 2, 4});
        ListNode list2_1 = solution.createList(new int[]{1, 3, 5});

        solution.printList("List1", list1_1);
        solution.printList("List2", list2_1);

        ListNode merged1 = solution.mergeTwoLists(list1_1, list2_1);
        solution.printList("Merged", merged1);
        System.out.println("Expected: [1,1,2,3,4,5]");

        // Test case 2: One empty list
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Test Case 2: [] + [1,2]");
        ListNode list1_2 = solution.createList(new int[]{});
        ListNode list2_2 = solution.createList(new int[]{1, 2});

        solution.printList("List1", list1_2);
        solution.printList("List2", list2_2);

        ListNode merged2 = solution.mergeTwoLists(list1_2, list2_2);
        solution.printList("Merged", merged2);
        System.out.println("Expected: [1,2]");

        // Test case 3: Both empty
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Test Case 3: [] + []");
        ListNode list1_3 = solution.createList(new int[]{});
        ListNode list2_3 = solution.createList(new int[]{});

        solution.printList("List1", list1_3);
        solution.printList("List2", list2_3);

        ListNode merged3 = solution.mergeTwoLists(list1_3, list2_3);
        solution.printList("Merged", merged3);
        System.out.println("Expected: []");

        // Test case 4: Different lengths
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Test Case 4: [1,3,5,7,9] + [2,4,6]");
        ListNode list1_4 = solution.createList(new int[]{1, 3, 5, 7, 9});
        ListNode list2_4 = solution.createList(new int[]{2, 4, 6});

        solution.printList("List1", list1_4);
        solution.printList("List2", list2_4);

        ListNode merged4 = solution.mergeTwoLists(list1_4, list2_4);
        solution.printList("Merged", merged4);
        System.out.println("Expected: [1,2,3,4,5,6,7,9]");

        // Detailed step-by-step example
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Detailed Step-by-Step Example: [1,3] + [2,4]");
        ListNode list1_5 = solution.createList(new int[]{1, 3});
        ListNode list2_5 = solution.createList(new int[]{2, 4});
        solution.mergeTwoListsDetailed(list1_5, list2_5);

        // Test different algorithms for correctness
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Algorithm Verification: [1,2,3] + [4,5,6]");

        ListNode iterativeResult = solution.mergeTwoLists(
                solution.createList(new int[]{1, 2, 3}),
                solution.createList(new int[]{4, 5, 6})
        );

        ListNode recursiveResult = solution.mergeTwoListsRecursive(
                solution.createList(new int[]{1, 2, 3}),
                solution.createList(new int[]{4, 5, 6})
        );

        ListNode noDummyResult = solution.mergeTwoListsNoDummy(
                solution.createList(new int[]{1, 2, 3}),
                solution.createList(new int[]{4, 5, 6})
        );

        solution.printList("Iterative", iterativeResult);
        solution.printList("Recursive", recursiveResult);
        solution.printList("No Dummy", noDummyResult);

        // Edge case: Single nodes
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Edge Case: [5] + [1,2,4]");
        ListNode singleNode = solution.createList(new int[]{5});
        ListNode multiNode = solution.createList(new int[]{1, 2, 4});

        ListNode edgeResult = solution.mergeTwoLists(singleNode, multiNode);
        solution.printList("Result", edgeResult);
        System.out.println("Expected: [1,2,4,5]");

        // Performance analysis
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Performance Analysis:");
        System.out.println("Iterative with dummy: O(m+n) time, O(1) space - BEST");
        System.out.println("Recursive: O(m+n) time, O(m+n) space - Elegant but uses stack");
        System.out.println("Iterative no dummy: O(m+n) time, O(1) space - More complex code");
        System.out.println("Priority Queue: O((m+n)log(m+n)) time - Overkill for 2 lists");
    }
}