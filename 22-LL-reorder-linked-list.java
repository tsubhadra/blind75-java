#https://neetcode.io/problems/reorder-linked-list?list=blind75
# Definition for singly-linked list.
class ListNode(object):
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution(object):
    def reorderList(self, head):
        """
        Optimal solution: Find middle, reverse second half, merge
        Time: O(n), Space: O(1)
        :type head: ListNode
        :rtype: None Do not return anything, modify head in-place instead.
        """
        if not head or not head.next:
            return
        
        # Step 1: Find the middle of the linked list
        slow = fast = head
        while fast.next and fast.next.next:
            slow = slow.next
            fast = fast.next.next
        
        # Step 2: Split the list into two halves
        second_half = slow.next
        slow.next = None  # Cut the connection
        
        # Step 3: Reverse the second half
        second_half = self.reverseList(second_half)
        
        # Step 4: Merge the two halves alternately
        first_half = head
        while second_half:
            # Store next nodes
            temp1 = first_half.next
            temp2 = second_half.next
            
            # Connect current nodes
            first_half.next = second_half
            second_half.next = temp1
            
            # Move to next pair
            first_half = temp1
            second_half = temp2
    
    def reverseList(self, head):
        """Helper function to reverse a linked list"""
        prev = None
        current = head
        
        while current:
            next_temp = current.next
            current.next = prev
            prev = current
            current = next_temp
        
        return prev
    
    def reorderListWithArray(self, head):
        """
        Alternative solution using array (easier to understand)
        Time: O(n), Space: O(n)
        :type head: ListNode
        :rtype: None
        """
        if not head or not head.next:
            return
        
        # Step 1: Store all nodes in an array
        nodes = []
        current = head
        while current:
            nodes.append(current)
            current = current.next
        
        # Step 2: Reorder using two pointers
        left, right = 0, len(nodes) - 1
        
        while left < right:
            # Connect left node to right node
            nodes[left].next = nodes[right]
            left += 1
            
            if left == right:
                break
            
            # Connect right node to next left node
            nodes[right].next = nodes[left]
            right -= 1
        
        # Terminate the list
        nodes[left].next = None
    
    def reorderListRecursive(self, head):
        """
        Recursive solution (more complex but educational)
        Time: O(n), Space: O(n) due to recursion stack
        :type head: ListNode
        :rtype: None
        """
        if not head or not head.next:
            return
        
        def getLength(node):
            length = 0
            while node:
                length += 1
                node = node.next
            return length
        
        def reorderRecursive(node, length):
            if length == 1:
                return node.next
            if length == 2:
                return node.next.next
            
            # Get the tail of the reordered sublist
            tail = reorderRecursive(node.next, length - 2)
            
            # Current tail of the sublist
            current_tail = tail.next
            
            # Insert current_tail after current node
            tail.next = current_tail.next
            current_tail.next = node.next
            node.next = current_tail
            
            return tail
        
        length = getLength(head)
        reorderRecursive(head, length)

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

def print_transformation(original, reordered):
    print(f"Original:  {original}")
    print(f"Reordered: {reordered}")

# Test the solutions
if __name__ == "__main__":
    solution = Solution()
    
    # Test case 1: [1,2,3,4] -> [1,4,2,3]
    print("Test Case 1: Even length")
    head1 = create_linked_list([1, 2, 3, 4])
    original1 = linked_list_to_array(head1)
    solution.reorderList(head1)
    reordered1 = linked_list_to_array(head1)
    print_transformation(original1, reordered1)
    print()
    
    # Test case 2: [1,2,3,4,5] -> [1,5,2,4,3]
    print("Test Case 2: Odd length")
    head2 = create_linked_list([1, 2, 3, 4, 5])
    original2 = linked_list_to_array(head2)
    solution.reorderList(head2)
    reordered2 = linked_list_to_array(head2)
    print_transformation(original2, reordered2)
    print()
    
    # Test case 3: [1,2] -> [1,2]
    print("Test Case 3: Two nodes")
    head3 = create_linked_list([1, 2])
    original3 = linked_list_to_array(head3)
    solution.reorderList(head3)
    reordered3 = linked_list_to_array(head3)
    print_transformation(original3, reordered3)
    print()
    
    # Test case 4: [1] -> [1]
    print("Test Case 4: Single node")
    head4 = create_linked_list([1])
    original4 = linked_list_to_array(head4)
    solution.reorderList(head4)
    reordered4 = linked_list_to_array(head4)
    print_transformation(original4, reordered4)
    print()
    
    # Test case 5: Using array approach
    print("Test Case 5: Array approach [1,2,3,4,5,6]")
    head5 = create_linked_list([1, 2, 3, 4, 5, 6])
    original5 = linked_list_to_array(head5)
    solution.reorderListWithArray(head5)
    reordered5 = linked_list_to_array(head5)
    print_transformation(original5, reordered5)
    print()
    
    # Demonstrate step-by-step process
    print("="*50)
    print("Step-by-step breakdown for [1,2,3,4,5,6]:")
    print("1. Original: 1->2->3->4->5->6")
    print("2. Find middle: split at 3, get 1->2->3 and 4->5->6")
    print("3. Reverse second half: 1->2->3 and 6->5->4")
    print("4. Merge alternately: 1->6->2->5->3->4")
    print("="*50)
    
    print("\nAlgorithm Comparison:")
    print("1. Optimal (Find middle + Reverse + Merge): O(n) time, O(1) space")
    print("2. Array approach: O(n) time, O(n) space - easier to understand")
    print("3. Recursive: O(n) time, O(n) space - complex but educational")
