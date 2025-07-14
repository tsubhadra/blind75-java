#https://neetcode.io/problems/top-k-elements-in-list?list=blind75
import java.util.*;

public class TopKFrequentElements {
    
    // Approach 1: Using Min-Heap (Priority Queue) - O(n log k) time, O(n + k) space
    // Most efficient for large arrays when k is small
    public int[] topKFrequent1(int[] nums, int k) {
        // Step 1: Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Use min-heap to keep track of top k frequent elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> freqMap.get(a) - freqMap.get(b));
        
        for (int num : freqMap.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // Step 3: Extract elements from heap
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }
        
        return result;
    }
    
    // Approach 2: Using Max-Heap (Priority Queue) - O(n log n) time, O(n) space
    // Simple approach using max-heap
    public int[] topKFrequent2(int[] nums, int k) {
        // Step 1: Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Use max-heap to sort by frequency
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> freqMap.get(b) - freqMap.get(a));
        maxHeap.addAll(freqMap.keySet());
        
        // Step 3: Extract top k elements
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }
        
        return result;
    }
    
    // Approach 3: Using Bucket Sort - O(n) time, O(n) space
    // Most efficient overall approach
    public int[] topKFrequent3(int[] nums, int k) {
        // Step 1: Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Create buckets where index = frequency
        List<Integer>[] buckets = new List[nums.length + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Step 3: Place numbers in buckets by frequency
        for (int num : freqMap.keySet()) {
            int freq = freqMap.get(num);
            buckets[freq].add(num);
        }
        
        // Step 4: Collect top k elements from highest frequency buckets
        int[] result = new int[k];
        int index = 0;
        
        for (int i = buckets.length - 1; i >= 0 && index < k; i--) {
            for (int num : buckets[i]) {
                if (index < k) {
                    result[index++] = num;
                }
            }
        }
        
        return result;
    }
    
    // Approach 4: Using TreeMap - O(n log n) time, O(n) space
    // Alternative approach using TreeMap for frequency sorting
    public int[] topKFrequent4(int[] nums, int k) {
        // Step 1: Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Use TreeMap to sort by frequency (descending)
        Map<Integer, List<Integer>> freqToNums = new TreeMap<>(Collections.reverseOrder());
        for (int num : freqMap.keySet()) {
            int freq = freqMap.get(num);
            freqToNums.computeIfAbsent(freq, x -> new ArrayList<>()).add(num);
        }
        
        // Step 3: Extract top k elements
        int[] result = new int[k];
        int index = 0;
        
        for (List<Integer> nums_list : freqToNums.values()) {
            for (int num : nums_list) {
                if (index < k) {
                    result[index++] = num;
                }
            }
        }
        
        return result;
    }
    
    // Approach 5: Using Quick Select (Quickselect) - O(n) average, O(n²) worst case
    // Most complex but theoretically optimal
    public int[] topKFrequent5(int[] nums, int k) {
        // Step 1: Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Convert to array for quickselect
        int[] unique = new int[freqMap.size()];
        int i = 0;
        for (int num : freqMap.keySet()) {
            unique[i++] = num;
        }
        
        // Step 3: Use quickselect to find kth most frequent
        quickSelect(unique, 0, unique.length - 1, unique.length - k, freqMap);
        
        // Step 4: Return top k elements
        return Arrays.copyOfRange(unique, unique.length - k, unique.length);
    }
    
    private void quickSelect(int[] nums, int left, int right, int kSmallest, Map<Integer, Integer> freqMap) {
        if (left == right) return;
        
        int pivotIndex = partition(nums, left, right, freqMap);
        
        if (kSmallest == pivotIndex) {
            return;
        } else if (kSmallest < pivotIndex) {
            quickSelect(nums, left, pivotIndex - 1, kSmallest, freqMap);
        } else {
            quickSelect(nums, pivotIndex + 1, right, kSmallest, freqMap);
        }
    }
    
    private int partition(int[] nums, int left, int right, Map<Integer, Integer> freqMap) {
        int pivotFreq = freqMap.get(nums[right]);
        int i = left;
        
        for (int j = left; j < right; j++) {
            if (freqMap.get(nums[j]) < pivotFreq) {
                swap(nums, i, j);
                i++;
            }
        }
        
        swap(nums, i, right);
        return i;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // Utility method to print arrays
    public void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
    
    // Test cases
    public static void main(String[] args) {
        TopKFrequentElements solution = new TopKFrequentElements();
        
        // Test case 1: Standard example
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k1 = 2;
        System.out.println("Test 1 - nums: " + Arrays.toString(nums1) + ", k: " + k1);
        System.out.print("Min-Heap: ");
        solution.printArray(solution.topKFrequent1(nums1, k1)); // [1, 2]
        System.out.print("Max-Heap: ");
        solution.printArray(solution.topKFrequent2(nums1, k1)); // [1, 2]
        System.out.print("Bucket Sort: ");
        solution.printArray(solution.topKFrequent3(nums1, k1)); // [1, 2]
        
        // Test case 2: Single element
        int[] nums2 = {1};
        int k2 = 1;
        System.out.println("\nTest 2 - nums: " + Arrays.toString(nums2) + ", k: " + k2);
        System.out.print("Min-Heap: ");
        solution.printArray(solution.topKFrequent1(nums2, k2)); // [1]
        
        // Test case 3: All elements have same frequency
        int[] nums3 = {1, 2, 3, 4, 5};
        int k3 = 3;
        System.out.println("\nTest 3 - nums: " + Arrays.toString(nums3) + ", k: " + k3);
        System.out.print("Min-Heap: ");
        solution.printArray(solution.topKFrequent1(nums3, k3)); // Any 3 elements
        
        // Test case 4: Negative numbers
        int[] nums4 = {-1, -1, -2, -2, -2, -3};
        int k4 = 2;
        System.out.println("\nTest 4 - nums: " + Arrays.toString(nums4) + ", k: " + k4);
        System.out.print("Min-Heap: ");
        solution.printArray(solution.topKFrequent1(nums4, k4)); // [-2, -1]
        
        // Test case 5: Large numbers
        int[] nums5 = {1000000, 1000000, 999999, 999999, 999999, 999998};
        int k5 = 2;
        System.out.println("\nTest 5 - nums: " + Arrays.toString(nums5) + ", k: " + k5);
        System.out.print("Min-Heap: ");
        solution.printArray(solution.topKFrequent1(nums5, k5)); // [999999, 1000000]
        
        // Performance comparison
        System.out.println("\n--- Performance Comparison ---");
        int[] largeNums = new int[100000];
        Random rand = new Random();
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = rand.nextInt(1000); // Random numbers 0-999
        }
        int largeK = 10;
        
        long start = System.currentTimeMillis();
        solution.topKFrequent1(largeNums.clone(), largeK);
        long end = System.currentTimeMillis();
        System.out.println("Min-Heap approach time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        solution.topKFrequent2(largeNums.clone(), largeK);
        end = System.currentTimeMillis();
        System.out.println("Max-Heap approach time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        solution.topKFrequent3(largeNums.clone(), largeK);
        end = System.currentTimeMillis();
        System.out.println("Bucket Sort approach time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        solution.topKFrequent4(largeNums.clone(), largeK);
        end = System.currentTimeMillis();
        System.out.println("TreeMap approach time: " + (end - start) + "ms");
        
        start = System.currentTimeMillis();
        solution.topKFrequent5(largeNums.clone(), largeK);
        end = System.currentTimeMillis();
        System.out.println("QuickSelect approach time: " + (end - start) + "ms");
    }
}
