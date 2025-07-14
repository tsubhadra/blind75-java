#https://neetcode.io/problems/merge-k-sorted-linked-lists?list=blind75
import heapq

# Definition for singly-linked list.
class ListNode(object):
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution(object):
    def mergeKLists(self, lists):
        """
        Optimal divide-and-conquer solution
        Time: O(N log k) where N is total nodes, k is number of lists
        Space: O(log k) for recursion stack
        :type lists: List[ListNode]
        :rtype: ListNode
        """
        if not lists:
            return None
        
        # Keep merging pairs until only one list remains
        while len(lists) > 1:
            merged_lists = []
            
            # Process pairs of lists
            for i in range(0, len(lists), 2):
                list1 = lists[i]
                list2 = lists[i + 1] if i + 1 < len(lists) else None
                merged_lists.append(self.mergeTwoLists(list1, list2))
            
            lists = merged_lists
        
        return lists[0]
    
    def mergeTwoLists(self, list1, list2):
        """Helper function to merge two sorted linked lists"""
        dummy = ListNode(0)
        current = dummy
        
        while list1 and list2:
            if list1.val <= list2.val:
                current.next = list1
                list1 = list1.next
            else:
                current.next = list2
                list2 = list2.next
            current = current.next
        
        # Attach remaining nodes
        current.next = list1 if list1 else list2
        return dummy.next
    
    def mergeKListsHeap(self, lists):
        """
        Min-heap solution
        Time: O(N log k) where N is total nodes, k is number of lists
        Space: O(k) for the heap
        :type lists: List[ListNode]
        :rtype: ListNode
        """
        if not lists:
            return None
        
        # Custom comparison class for heap
        class ListNodeWrapper:
            def __init__(self, node):
                self.node = node
            
            def __lt__(self, other):
                return self.node.val < other.node.val
        
        # Initialize heap with first node of each non-empty list
        heap = []
        for i, head in enumerate(lists):
            if head:
                heapq.heappush(heap, ListNodeWrapper(head))
        
        dummy = ListNode(0)
        current = dummy
        
        while heap:
            # Get the smallest node
            wrapper = heapq.heappop(heap)
            node = wrapper.node
            
            # Add to result
            current.next = node
            current = current.next
            
            # Add next node from same list to heap
            if node.next:
                heapq.heappush(heap, ListNodeWrapper(node.next))
        
        return dummy.next
    
    def mergeKListsRecursive(self, lists):
        """
        Recursive divide-and-conquer solution
        Time: O(N log k), Space: O(log k)
        :type lists: List[ListNode]
        :rtype: ListNode
        """
        if not lists:
            return None
        if len(lists) == 1:
            return lists[0]
        
        # Divide
        mid = len(lists) // 2
        left = self.mergeKListsRecursive(lists[:mid])
        right = self.mergeKListsRecursive(lists[mid:])
        
        # Conquer
        return self.mergeTwoLists(left, right)
    
    def mergeKListsBruteForce(self, lists):
        """
        Brute force: merge lists one by one
        Time: O(N * k) where N is total nodes, k is number of lists
        Space: O(1)
        :type lists: List[ListNode]
        :rtype: ListNode
        """
        if not lists:
            return None
        
        result = lists[0]
        for i in range(1, len(lists)):
            result = self.mergeTwoLists(result, lists[i])
        
        return result
    
    def mergeKListsArray(self, lists):
        """
        Array-based solution: collect all values, sort, and rebuild
        Time: O(N log N) where N is total nodes
        Space: O(N) for storing all values
        :type lists: List[ListNode]
        :rtype: ListNode
        """
        if not lists:
            return None
        
        # Collect all values
        values = []
        for head in lists:
            current = head
            while current:
                values.append(current.val)
                current = current.next
        
        # Sort values
        values.sort()
        
        # Build new linked list
        if not values:
            return None
        
        dummy = ListNode(0)
        current = dummy
        
        for val in values:
            current.next = ListNode(val)
            current = current.next
        
        return dummy.next

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

def print_merge_result(lists_arrays, result_array):
    print(f"Input lists: {lists_arrays}")
    print(f"Merged result: {result_array}")
    print()

# Test all solutions
if __name__ == "__main__":
    solution = Solution()
    
    # Test case 1: [[1,4,5],[1,3,4],[2,6]]
    print("Test Case 1: Multiple non-empty lists")
    lists1 = [
        create_linked_list([1, 4, 5]),
        create_linked_list([1, 3, 4]),
        create_linked_list([2, 6])
    ]
    lists1_arrays = [[1, 4, 5], [1, 3, 4], [2, 6]]
    
    # Test divide-and-conquer
    lists1_copy = [
        create_linked_list([1, 4, 5]),
        create_linked_list([1, 3, 4]),
        create_linked_list([2, 6])
    ]
    result1 = solution.mergeKLists(lists1_copy)
    result1_array = linked_list_to_array(result1)
    print("Divide-and-conquer:")
    print_merge_result(lists1_arrays, result1_array)
    
    # Test heap solution
    lists1_copy2 = [
        create_linked_list([1, 4, 5]),
        create_linked_list([1, 3, 4]),
        create_linked_list([2, 6])
    ]
    result1_heap = solution.mergeKListsHeap(lists1_copy2)
    result1_heap_array = linked_list_to_array(result1_heap)
    print("Heap solution:")
    print_merge_result(lists1_arrays, result1_heap_array)
    
    # Test case 2: Empty lists
    print("Test Case 2: Empty input")
    lists2 = []
    result2 = solution.mergeKLists(lists2)
    result2_array = linked_list_to_array(result2)
    print_merge_result([], result2_array)
    
    # Test case 3: Single empty list
    print("Test Case 3: Single empty list")
    lists3 = [None]
    result3 = solution.mergeKLists(lists3)
    result3_array = linked_list_to_array(result3)
    print_merge_result([None], result3_array)
    
    # Test case 4: Single non-empty list
    print("Test Case 4: Single non-empty list")
    lists4 = [create_linked_list([1, 2, 3])]
    lists4_arrays = [[1, 2, 3]]
    result4 = solution.mergeKLists(lists4)
    result4_array = linked_list_to_array(result4)
    print_merge_result(lists4_arrays, result4_array)
    
    # Test case 5: Lists with different lengths
    print("Test Case 5: Different length lists")
    lists5 = [
        create_linked_list([1]),
        create_linked_list([2, 3, 4, 5]),
        create_linked_list([6, 7])
    ]
    lists5_arrays = [[1], [2, 3, 4, 5], [6, 7]]
    result5 = solution.mergeKLists(lists5)
    result5_array = linked_list_to_array(result5)
    print_merge_result(lists5_arrays, result5_array)
    
    # Performance comparison
    print("="*60)
    print("Step-by-step divide-and-conquer for [[1,4,5],[1,3,4],[2,6]]:")
    print("Round 1: Merge pairs")
    print("  - Merge [1,4,5] and [1,3,4] -> [1,1,3,4,4,5]")
    print("  - Keep [2,6] as is")
    print("  - Lists: [[1,1,3,4,4,5], [2,6]]")
    print("Round 2: Merge remaining")
    print("  - Merge [1,1,3,4,4,5] and [2,6] -> [1,1,2,3,4,4,5,6]")
    print("  - Final result: [1,1,2,3,4,4,5,6]")
    print("="*60)
    
    print("Algorithm Comparison:")
    print("1. Divide-and-conquer: O(N log k) time, O(log k) space - OPTIMAL")
    print("2. Min-heap: O(N log k) time, O(k) space - good for streaming")
    print("3. Recursive D&C: O(N log k) time, O(log k) space - elegant")
    print("4. Brute force: O(N * k) time, O(1) space - simple but slow")
    print("5. Array sort: O(N log N) time, O(N) space - not preserving structure")
    print("\nWhere N = total number of nodes, k = number of lists")
