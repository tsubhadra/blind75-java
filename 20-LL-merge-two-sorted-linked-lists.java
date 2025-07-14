#https://neetcode.io/problems/merge-two-sorted-linked-lists?list=blind75
# Definition for singly-linked list.
class ListNode(object):
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution(object):
    def mergeTwoLists(self, list1, list2):
        """
        Iterative approach with dummy node - O(m+n) time, O(1) space
        :type list1: ListNode
        :type list2: ListNode
        :rtype: ListNode
        """
        # Create a dummy node to simplify the logic
        dummy = ListNode(0)
        current = dummy
        
        # Compare nodes from both lists and attach smaller one
        while list1 and list2:
            if list1.val <= list2.val:
                current.next = list1
                list1 = list1.next
            else:
                current.next = list2
                list2 = list2.next
            current = current.next
        
        # Attach remaining nodes (at most one list will have remaining nodes)
        current.next = list1 if list1 else list2
        
        return dummy.next

    def mergeTwoListsRecursive(self, list1, list2):
        """
        Recursive approach - O(m+n) time, O(m+n) space (call stack)
        :type list1: ListNode
        :type list2: ListNode
        :rtype: ListNode
        """
        # Base cases
        if not list1:
            return list2
        if not list2:
            return list1
        
        # Choose the smaller node and recursively merge the rest
        if list1.val <= list2.val:
            list1.next = self.mergeTwoListsRecursive(list1.next, list2)
            return list1
        else:
            list2.next = self.mergeTwoListsRecursive(list1, list2.next)
            return list2

    def mergeTwoListsInPlace(self, list1, list2):
        """
        In-place merge without dummy node - O(m+n) time, O(1) space
        :type list1: ListNode
        :type list2: ListNode
        :rtype: ListNode
        """
        # Handle edge cases
        if not list1:
            return list2
        if not list2:
            return list1
        
        # Ensure list1 starts with the smaller value
        if list1.val > list2.val:
            list1, list2 = list2, list1
        
        head = list1  # This will be our result head
        
        # Merge process
        while list1.next and list2:
            if list1.next.val <= list2.val:
                list1 = list1.next
            else:
                # Insert list2 node between list1 and list1.next
                temp = list1.next
                list1.next = list2
                list2 = list2.next
                list1.next.next = temp
                list1 = list1.next
        
        # Attach remaining nodes from list2
        if list2:
            list1.next = list2
        
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

# Test the solutions
if __name__ == "__main__":
    solution = Solution()
    
    # Test case 1: [1,2,4] and [1,3,4]
    print("Test Case 1:")
    list1 = create_linked_list([1, 2, 4])
    list2 = create_linked_list([1, 3, 4])
    print(f"List 1: {linked_list_to_array(list1)}")
    print(f"List 2: {linked_list_to_array(list2)}")
    
    # Test iterative approach
    list1 = create_linked_list([1, 2, 4])
    list2 = create_linked_list([1, 3, 4])
    merged = solution.mergeTwoLists(list1, list2)
    print(f"Merged (iterative): {linked_list_to_array(merged)}")
    
    # Test recursive approach
    list1 = create_linked_list([1, 2, 4])
    list2 = create_linked_list([1, 3, 4])
    merged_recursive = solution.mergeTwoListsRecursive(list1, list2)
    print(f"Merged (recursive): {linked_list_to_array(merged_recursive)}")
    
    # Test case 2: Empty lists
    print("\nTest Case 2:")
    list1 = create_linked_list([])
    list2 = create_linked_list([0])
    print(f"List 1: {linked_list_to_array(list1)}")
    print(f"List 2: {linked_list_to_array(list2)}")
    
    merged = solution.mergeTwoLists(list1, list2)
    print(f"Merged: {linked_list_to_array(merged)}")
    
    # Test case 3: Different lengths
    print("\nTest Case 3:")
    list1 = create_linked_list([1, 5, 6])
    list2 = create_linked_list([2, 3, 4, 7, 8])
    print(f"List 1: {linked_list_to_array(list1)}")
    print(f"List 2: {linked_list_to_array(list2)}")
    
    merged = solution.mergeTwoLists(list1, list2)
    print(f"Merged: {linked_list_to_array(merged)}")
    
    # Test case 4: One empty, one non-empty
    print("\nTest Case 4:")
    list1 = create_linked_list([])
    list2 = create_linked_list([1, 2, 3])
    print(f"List 1: {linked_list_to_array(list1)}")
    print(f"List 2: {linked_list_to_array(list2)}")
    
    merged = solution.mergeTwoLists(list1, list2)
    print(f"Merged: {linked_list_to_array(merged)}")
