#https://neetcode.io/problems/reverse-a-linked-list?list=blind75
# Definition for singly-linked list.
class ListNode(object):
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution(object):
    def reverseList(self, head):
        """
        Iterative approach - O(n) time, O(1) space
        :type head: ListNode
        :rtype: ListNode
        """
        prev = None
        current = head
        
        while current:
            next_temp = current.next  # Store next node
            current.next = prev       # Reverse the link
            prev = current           # Move prev forward
            current = next_temp      # Move current forward
        
        return prev  # prev is now the new head

    def reverseListRecursive(self, head):
        """
        Recursive approach - O(n) time, O(n) space (due to call stack)
        :type head: ListNode
        :rtype: ListNode
        """
        # Base case: empty list or single node
        if not head or not head.next:
            return head
        
        # Recursively reverse the rest of the list
        new_head = self.reverseListRecursive(head.next)
        
        # Reverse the current connection
        head.next.next = head
        head.next = None
        
        return new_head

# Helper function to create a linked list from array
def create_linked_list(arr):
    if not arr:
        return None
    
    head = ListNode(arr[0])
    current = head
    for val in arr[1:]:
        current.next = ListNode(val)
        current = current.next
    return head

# Helper function to convert linked list to array for easy printing
def linked_list_to_array(head):
    result = []
    current = head
    while current:
        result.append(current.val)
        current = current.next
    return result

# Test the solutions
if __name__ == "__main__":
    solution = Solution()
    
    # Test case 1: [1,2,3,4,5]
    head1 = create_linked_list([1, 2, 3, 4, 5])
    print("Original:", linked_list_to_array(head1))
    
    reversed_head1 = solution.reverseList(head1)
    print("Reversed (iterative):", linked_list_to_array(reversed_head1))
    
    # Test case 2: [1,2] using recursive approach
    head2 = create_linked_list([1, 2])
    print("\nOriginal:", linked_list_to_array(head2))
    
    reversed_head2 = solution.reverseListRecursive(head2)
    print("Reversed (recursive):", linked_list_to_array(reversed_head2))
    
    # Test case 3: Empty list
    head3 = create_linked_list([])
    print("\nOriginal:", linked_list_to_array(head3))
    
    reversed_head3 = solution.reverseList(head3)
    print("Reversed (empty):", linked_list_to_array(reversed_head3))
