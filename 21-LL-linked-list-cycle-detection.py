#https://neetcode.io/problems/linked-list-cycle-detection?list=blind75
# Definition for singly-linked list.
class ListNode(object):
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution(object):
    def hasCycle(self, head):
        """
        Floyd's Cycle Detection Algorithm (Tortoise and Hare) - OPTIMAL
        Time: O(n), Space: O(1)
        :type head: ListNode
        :rtype: bool
        """
        if not head or not head.next:
            return False
        
        # Two pointers: slow moves 1 step, fast moves 2 steps
        slow = head
        fast = head
        
        while fast and fast.next:
            slow = slow.next        # Move 1 step
            fast = fast.next.next   # Move 2 steps
            
            # If they meet, there's a cycle
            if slow == fast:
                return True
        
        return False

    def hasCycleHashSet(self, head):
        """
        Hash Set approach - Simple but uses extra space
        Time: O(n), Space: O(n)
        :type head: ListNode
        :rtype: bool
        """
        seen = set()
        current = head
        
        while current:
            if current in seen:
                return True
            seen.add(current)
            current = current.next
        
        return False

    def hasCycleModification(self, head):
        """
        Node modification approach - Modifies the original list
        Time: O(n), Space: O(1)
        Note: This modifies the original list structure
        :type head: ListNode
        :rtype: bool
        """
        if not head:
            return False
        
        # Use a sentinel value to mark visited nodes
        VISITED = float('inf')
        current = head
        
        while current:
            if current.val == VISITED:
                return True
            
            # Mark as visited
            current.val = VISITED
            current = current.next
        
        return False

    def findCycleStart(self, head):
        """
        Extended version: Find where the cycle starts
        Time: O(n), Space: O(1)
        :type head: ListNode
        :rtype: ListNode or None
        """
        if not head or not head.next:
            return None
        
        # Phase 1: Detect if cycle exists
        slow = fast = head
        
        while fast and fast.next:
            slow = slow.next
            fast = fast.next.next
            
            if slow == fast:
                break
        else:
            return None  # No cycle found
        
        # Phase 2: Find the start of the cycle
        # Move one pointer to head, keep other at meeting point
        # Move both one step at a time until they meet
        slow = head
        while slow != fast:
            slow = slow.next
            fast = fast.next
        
        return slow  # This is the start of the cycle

    def getCycleLength(self, head):
        """
        Extended version: Get the length of the cycle
        Time: O(n), Space: O(1)
        :type head: ListNode
        :rtype: int (0 if no cycle)
        """
        if not head or not head.next:
            return 0
        
        # Phase 1: Detect cycle
        slow = fast = head
        
        while fast and fast.next:
            slow = slow.next
            fast = fast.next.next
            
            if slow == fast:
                break
        else:
            return 0  # No cycle
        
        # Phase 2: Count cycle length
        cycle_length = 1
        current = slow.next
        
        while current != slow:
            current = current.next
            cycle_length += 1
        
        return cycle_length

# Helper functions for testing
def create_cycle_list(values, cycle_pos):
    """
    Create a linked list with a cycle at the given position
    cycle_pos: -1 means no cycle, otherwise index where cycle starts
    """
    if not values:
        return None
    
    nodes = [ListNode(val) for val in values]
    
    # Connect nodes
    for i in range(len(nodes) - 1):
        nodes[i].next = nodes[i + 1]
    
    # Create cycle if specified
    if cycle_pos != -1 and cycle_pos < len(nodes):
        nodes[-1].next = nodes[cycle_pos]
    
    return nodes[0]

def print_list_with_cycle_info(head, max_print=10):
    """Print list values (limited to avoid infinite loop if cycle exists)"""
    if not head:
        print("Empty list")
        return
    
    current = head
    values = []
    count = 0
    
    while current and count < max_print:
        values.append(current.val)
        current = current.next
        count += 1
    
    if current:
        values.append("...")
    
    print(f"List: {values}")

# Test all approaches
if __name__ == "__main__":
    solution = Solution()
    
    # Test case 1: List with cycle [3,2,0,-4] with cycle at index 1
    print("Test Case 1: List with cycle")
    head1 = create_cycle_list([3, 2, 0, -4], 1)
    print_list_with_cycle_info(head1)
    print(f"Has cycle (Floyd's): {solution.hasCycle(head1)}")
    print(f"Has cycle (HashSet): {solution.hasCycleHashSet(head1)}")
    
    # Find cycle start and length
    cycle_start = solution.findCycleStart(head1)
    cycle_length = solution.getCycleLength(head1)
    print(f"Cycle starts at node with value: {cycle_start.val if cycle_start else 'None'}")
    print(f"Cycle length: {cycle_length}")
    
    # Test case 2: List without cycle [1,2]
    print("\nTest Case 2: List without cycle")
    head2 = create_cycle_list([1, 2], -1)
    print_list_with_cycle_info(head2)
    print(f"Has cycle (Floyd's): {solution.hasCycle(head2)}")
    print(f"Has cycle (HashSet): {solution.hasCycleHashSet(head2)}")
    
    # Test case 3: Single node with self-cycle
    print("\nTest Case 3: Single node with self-cycle")
    head3 = create_cycle_list([1], 0)
    print_list_with_cycle_info(head3)
    print(f"Has cycle (Floyd's): {solution.hasCycle(head3)}")
    
    # Test case 4: Empty list
    print("\nTest Case 4: Empty list")
    head4 = None
    print_list_with_cycle_info(head4)
    print(f"Has cycle (Floyd's): {solution.hasCycle(head4)}")
    
    # Test case 5: Single node without cycle
    print("\nTest Case 5: Single node without cycle")
    head5 = create_cycle_list([1], -1)
    print_list_with_cycle_info(head5)
    print(f"Has cycle (Floyd's): {solution.hasCycle(head5)}")
    
    print("\n" + "="*50)
    print("Algorithm Comparison:")
    print("1. Floyd's Algorithm (Tortoise & Hare): O(n) time, O(1) space - OPTIMAL")
    print("2. Hash Set: O(n) time, O(n) space - Simple but uses extra memory")
    print("3. Node Modification: O(n) time, O(1) space - Modifies original list")
    print("="*50)
