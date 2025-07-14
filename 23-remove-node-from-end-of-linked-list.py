#https://neetcode.io/problems/remove-node-from-end-of-linked-list?list=blind75
# Definition for singly-linked list.
class ListNode(object):
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution(object):
    def removeNthFromEnd(self, head, n):
        """
        Optimal one-pass solution using two pointers with dummy node
        Time: O(L) where L is length of list, Space: O(1)
        :type head: ListNode
        :type n: int
        :rtype: ListNode
        """
        # Create dummy node to handle edge case of removing head
        dummy = ListNode(0)
        dummy.next = head
        
        # Two pointers starting from dummy
        first = dummy
        second = dummy
        
        # Move first pointer n+1 steps ahead
        # This creates a gap of n nodes between first and second
        for i in range(n + 1):
            first = first.next
        
        # Move both pointers until first reaches the end
        # When first is at None, second will be at the node before target
        while first is not None:
            first = first.next
            second = second.next
        
        # Remove the nth node from end
        second.next = second.next.next
        
        return dummy.next

    def removeNthFromEndTwoPass(self, head, n):
        """
        Two-pass solution: first pass to get length, second pass to remove
        Time: O(L), Space: O(1)
        :type head: ListNode
        :type n: int
        :rtype: ListNode
        """
        # First pass: calculate length
        length = 0
        current = head
        while current:
            length += 1
            current = current.next
        
        # Handle edge case: removing the head node
        if length == n:
            return head.next
        
        # Second pass: find the node before the target
        current = head
        for i in range(length - n - 1):
            current = current.next
        
        # Remove the target node
        current.next = current.next.next
        
        return head

    def removeNthFromEndRecursive(self, head, n):
        """
        Recursive solution that counts from the end
        Time: O(L), Space: O(L) due to recursion stack
        :type head: ListNode
        :type n: int
        :rtype: ListNode
        """
        def helper(node):
            if not node:
                return 0
            
            # Get position from end (1-indexed)
            pos = helper(node.next) + 1
            
            # If this is the target node, skip it
            if pos == n + 1:
                node.next = node.next.next
            
            return pos
        
        # Handle edge case: removing head node
        dummy = ListNode(0)
        dummy.next = head
        helper(dummy)
        
        return dummy.next

    def removeNthFromEndWithoutDummy(self, head, n):
        """
        One-pass solution without dummy node (handles edge cases explicitly)
        Time: O(L), Space: O(1)
        :type head: ListNode
        :type n: int
        :rtype: ListNode
        """
        # Two pointers
        first = head
        second = head
        
        # Move first pointer n steps ahead
        for i in range(n):
            first = first.next
        
        # If first is None, we need to remove the head
        if first is None:
            return head.next
        
        # Move both pointers until first reaches the last node
        while first.next is not None:
            first = first.next
            second = second.next
        
        # Remove the target node
        second.next = second.next.next
        
        return head

# Helper functions for testing
def create_linked_list(arr):
    if not arr:
        return None
    
    head = ListNode(arr[0])
    current = head
    for val in arr[1:]:
        current.next = ListNode(val)
        current = current.next
    return head

def linked_list_to_array(head):
    result = []
    current = head
    while current:
        result.append(current.val)
        current = current.next
    return result

def print_removal(original, n, result):
    print(f"Original: {original}")
    print(f"Remove {n}th from end: {result}")
    print()

# Test all solutions
if __name__ == "__main__":
    solution = Solution()
    
    # Test case 1: [1,2,3,4,5], n=2 -> [1,2,3,5]
    print("Test Case 1: Remove 2nd from end")
    head1 = create_linked_list([1, 2, 3, 4, 5])
    original1 = linked_list_to_array(head1)
    
    head1_copy = create_linked_list([1, 2, 3, 4, 5])
    result1 = solution.removeNthFromEnd(head1_copy, 2)
    result1_array = linked_list_to_array(result1)
    print_removal(original1, 2, result1_array)
    
    # Test case 2: [1], n=1 -> []
    print("Test Case 2: Remove only node")
    head2 = create_linked_list([1])
    original2 = linked_list_to_array(head2)
    
    result2 = solution.removeNthFromEnd(head2, 1)
    result2_array = linked_list_to_array(result2)
    print_removal(original2, 1, result2_array)
    
    # Test case 3: [1,2], n=1 -> [1]
    print("Test Case 3: Remove last node")
    head3 = create_linked_list([1, 2])
    original3 = linked_list_to_array(head3)
    
    result3 = solution.removeNthFromEnd(head3, 1)
    result3_array = linked_list_to_array(result3)
    print_removal(original3, 1, result3_array)
    
    # Test case 4: [1,2], n=2 -> [2] (remove head)
    print("Test Case 4: Remove head node")
    head4 = create_linked_list([1, 2])
    original4 = linked_list_to_array(head4)
    
    result4 = solution.removeNthFromEnd(head4, 2)
    result4_array = linked_list_to_array(result4)
    print_removal(original4, 2, result4_array)
    
    # Test case 5: [1,2,3,4,5,6], n=3 -> [1,2,3,5,6]
    print("Test Case 5: Remove middle node")
    head5 = create_linked_list([1, 2, 3, 4, 5, 6])
    original5 = linked_list_to_array(head5)
    
    result5 = solution.removeNthFromEnd(head5, 3)
    result5_array = linked_list_to_array(result5)
    print_removal(original5, 3, result5_array)
    
    # Test different approaches on the same input
    print("="*50)
    print("Comparing different approaches on [1,2,3,4,5], n=2:")
    
    # Two-pass approach
    head_two_pass = create_linked_list([1, 2, 3, 4, 5])
    result_two_pass = solution.removeNthFromEndTwoPass(head_two_pass, 2)
    print(f"Two-pass approach: {linked_list_to_array(result_two_pass)}")
    
    # Without dummy approach
    head_no_dummy = create_linked_list([1, 2, 3, 4, 5])
    result_no_dummy = solution.removeNthFromEndWithoutDummy(head_no_dummy, 2)
    print(f"Without dummy: {linked_list_to_array(result_no_dummy)}")
    
    # Recursive approach
    head_recursive = create_linked_list([1, 2, 3, 4, 5])
    result_recursive = solution.removeNthFromEndRecursive(head_recursive, 2)
    print(f"Recursive approach: {linked_list_to_array(result_recursive)}")
    
    print("="*50)
    
    # Step-by-step visualization
    print("Step-by-step for [1,2,3,4,5], n=2 (remove 4):")
    print("1. dummy -> 1 -> 2 -> 3 -> 4 -> 5")
    print("2. Move first pointer 3 steps: first at 3, second at dummy")
    print("3. Move both until first reaches end: second at 2")
    print("4. Remove: second.next = second.next.next")
    print("5. Result: 1 -> 2 -> 3 -> 5")
    print()
    
    print("Algorithm Comparison:")
    print("1. One-pass with dummy: O(L) time, O(1) space - OPTIMAL")
    print("2. Two-pass: O(L) time, O(1) space - easier to understand")
    print("3. Without dummy: O(L) time, O(1) space - more edge case handling")
    print("4. Recursive: O(L) time, O(L) space - elegant but uses stack")
