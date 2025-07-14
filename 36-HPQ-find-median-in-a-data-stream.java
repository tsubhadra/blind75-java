#https://neetcode.io/problems/find-median-in-a-data-stream?list=blind75
import java.util.*;

/**
 * MedianFinder class to find median from a data stream
 * Uses two heaps approach for O(log n) insertion and O(1) median retrieval
 */
class MedianFinder {
    // Max heap for the smaller half of numbers
    private PriorityQueue<Integer> maxHeap;
    // Min heap for the larger half of numbers
    private PriorityQueue<Integer> minHeap;
    
    public MedianFinder() {
        // Max heap (default is min heap, so we reverse the comparator)
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
        // Min heap (default behavior)
        minHeap = new PriorityQueue<>();
    }
    
    /**
     * Adds a number to the data structure
     * Time Complexity: O(log n)
     */
    public void addNum(int num) {
        // Always add to maxHeap first
        maxHeap.offer(num);
        
        // Move the largest element from maxHeap to minHeap
        minHeap.offer(maxHeap.poll());
        
        // Balance the heaps - maxHeap should have at most 1 more element than minHeap
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    /**
     * Returns the median of all elements so far
     * Time Complexity: O(1)
     */
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            // Odd number of elements, median is the top of maxHeap
            return maxHeap.peek();
        } else {
            // Even number of elements, median is average of both heap tops
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }
    
    // Helper method to print current state (for debugging)
    public void printState() {
        System.out.println("MaxHeap (smaller half): " + maxHeap);
        System.out.println("MinHeap (larger half): " + minHeap);
        System.out.println("Current median: " + findMedian());
        System.out.println("---");
    }
}

/**
 * Alternative implementation with more explicit balancing logic
 */
class MedianFinderAlternative {
    private PriorityQueue<Integer> small; // max heap
    private PriorityQueue<Integer> large; // min heap
    
    public MedianFinderAlternative() {
        small = new PriorityQueue<>(Collections.reverseOrder());
        large = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        // Add to appropriate heap based on current medians
        if (small.isEmpty() || num <= small.peek()) {
            small.offer(num);
        } else {
            large.offer(num);
        }
        
        // Balance heaps
        if (small.size() - large.size() > 1) {
            large.offer(small.poll());
        } else if (large.size() - small.size() > 1) {
            small.offer(large.poll());
        }
    }
    
    public double findMedian() {
        if (small.size() > large.size()) {
            return small.peek();
        } else if (large.size() > small.size()) {
            return large.peek();
        } else {
            return (small.peek() + large.peek()) / 2.0;
        }
    }
}

/**
 * Test class with comprehensive examples
 */
public class MedianFinderTest {
    public static void main(String[] args) {
        System.out.println("=== Testing MedianFinder ===");
        
        MedianFinder medianFinder = new MedianFinder();
        
        // Test case 1: Basic functionality
        System.out.println("Test 1: Basic operations");
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        System.out.println("After adding 1, 2: " + medianFinder.findMedian()); // 1.5
        
        medianFinder.addNum(3);
        System.out.println("After adding 3: " + medianFinder.findMedian()); // 2.0
        
        // Test case 2: More complex sequence
        System.out.println("\nTest 2: Complex sequence");
        MedianFinder mf2 = new MedianFinder();
        
        int[] nums = {5, 15, 1, 3, 8, 7, 9, 10, 20, 6};
        for (int num : nums) {
            mf2.addNum(num);
            System.out.println("Added " + num + ", median: " + mf2.findMedian());
        }
        
        // Test case 3: Duplicate numbers
        System.out.println("\nTest 3: Duplicate numbers");
        MedianFinder mf3 = new MedianFinder();
        mf3.addNum(1);
        mf3.addNum(1);
        mf3.addNum(1);
        System.out.println("Three 1's, median: " + mf3.findMedian()); // 1.0
        
        mf3.addNum(2);
        System.out.println("Added 2, median: " + mf3.findMedian()); // 1.0
        
        // Test case 4: Negative numbers
        System.out.println("\nTest 4: Negative numbers");
        MedianFinder mf4 = new MedianFinder();
        mf4.addNum(-1);
        mf4.addNum(-2);
        mf4.addNum(-3);
        mf4.addNum(1);
        System.out.println("Numbers: -1, -2, -3, 1, median: " + mf4.findMedian()); // -1.5
        
        // Performance test
        System.out.println("\nPerformance Test:");
        MedianFinder perfTest = new MedianFinder();
        long startTime = System.nanoTime();
        
        for (int i = 0; i < 10000; i++) {
            perfTest.addNum((int) (Math.random() * 1000));
        }
        
        long endTime = System.nanoTime();
        System.out.println("Time to add 10,000 numbers: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("Final median: " + perfTest.findMedian());
    }
}

/**
 * Additional related problems and solutions
 */
class StreamStatistics {
    
    /**
     * Find Kth Largest Element in a Stream
     */
    static class KthLargest {
        private PriorityQueue<Integer> minHeap;
        private int k;
        
        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.minHeap = new PriorityQueue<>();
            
            for (int num : nums) {
                add(num);
            }
        }
        
        public int add(int val) {
            minHeap.offer(val);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
            return minHeap.peek();
        }
    }
    
    /**
     * Running Average from Data Stream
     */
    static class MovingAverage {
        private Queue<Integer> queue;
        private int size;
        private double sum;
        
        public MovingAverage(int size) {
            this.size = size;
            this.queue = new LinkedList<>();
            this.sum = 0;
        }
        
        public double next(int val) {
            queue.offer(val);
            sum += val;
            
            if (queue.size() > size) {
                sum -= queue.poll();
            }
            
            return sum / queue.size();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Testing KthLargest ===");
        KthLargest kthLargest = new KthLargest(3, new int[]{4, 5, 8, 2});
        System.out.println(kthLargest.add(3)); // 4
        System.out.println(kthLargest.add(5)); // 5
        System.out.println(kthLargest.add(10)); // 5
        System.out.println(kthLargest.add(9)); // 8
        
        System.out.println("\n=== Testing MovingAverage ===");
        MovingAverage movingAverage = new MovingAverage(3);
        System.out.println(movingAverage.next(1)); // 1.0
        System.out.println(movingAverage.next(10)); // 5.5
        System.out.println(movingAverage.next(3)); // 4.666...
        System.out.println(movingAverage.next(5)); // 6.0
    }
}
