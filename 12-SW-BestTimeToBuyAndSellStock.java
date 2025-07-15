//https://neetcode.io/problems/buy-and-sell-crypto?list=blind75
public class BestTimeToBuyAndSellStock {

    // SOLUTION 1: Optimal One-Pass Approach (Recommended)
    // Time: O(n), Space: O(1)
    // Key insight: Track minimum price seen so far and maximum profit
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int minPrice = prices[0];  // Minimum price seen so far
        int maxProfit = 0;        // Maximum profit achievable

        for (int i = 1; i < prices.length; i++) {
            // If current price is lower, update minimum buy price
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                // Calculate profit if we sell at current price
                int currentProfit = prices[i] - minPrice;
                maxProfit = Math.max(maxProfit, currentProfit);
            }
        }

        return maxProfit;
    }

    // SOLUTION 2: Alternative One-Pass with Cleaner Logic
    // Time: O(n), Space: O(1)
    public int maxProfitAlternative(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }

        return maxProfit;
    }

    // SOLUTION 3: Brute Force Approach (Not recommended)
    // Time: O(n²), Space: O(1)
    // Check all possible buy-sell combinations
    public int maxProfitBruteForce(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int maxProfit = 0;

        // Try all possible buy days
        for (int buyDay = 0; buyDay < prices.length - 1; buyDay++) {
            // Try all possible sell days after buy day
            for (int sellDay = buyDay + 1; sellDay < prices.length; sellDay++) {
                int profit = prices[sellDay] - prices[buyDay];
                maxProfit = Math.max(maxProfit, profit);
            }
        }

        return maxProfit;
    }

    // SOLUTION 4: Dynamic Programming Approach
    // Time: O(n), Space: O(n)
    // Track maximum profit up to each day
    public int maxProfitDP(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int n = prices.length;
        int[] minPriceUpTo = new int[n];  // Minimum price from day 0 to day i
        int[] maxProfitUpTo = new int[n]; // Maximum profit from day 0 to day i

        minPriceUpTo[0] = prices[0];
        maxProfitUpTo[0] = 0;

        for (int i = 1; i < n; i++) {
            minPriceUpTo[i] = Math.min(minPriceUpTo[i - 1], prices[i]);
            maxProfitUpTo[i] = Math.max(maxProfitUpTo[i - 1],
                    prices[i] - minPriceUpTo[i]);
        }

        return maxProfitUpTo[n - 1];
    }

    // SOLUTION 5: Kadane's Algorithm Variation
    // Time: O(n), Space: O(1)
    // Think of it as maximum subarray problem with price differences
    public int maxProfitKadane(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int maxProfit = 0;
        int maxEndingHere = 0;

        for (int i = 1; i < prices.length; i++) {
            // Daily price change
            int dailyChange = prices[i] - prices[i - 1];

            // Either start new transaction or continue existing one
            maxEndingHere = Math.max(dailyChange, maxEndingHere + dailyChange);

            // Update global maximum
            maxProfit = Math.max(maxProfit, maxEndingHere);
        }

        return maxProfit;
    }

    // SOLUTION 6: Two-Pointer Approach
    // Time: O(n), Space: O(1)
    // Use two pointers to track buy and sell positions
    public int maxProfitTwoPointer(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int buyPointer = 0;    // Points to best buy day so far
        int maxProfit = 0;

        for (int sellPointer = 1; sellPointer < prices.length; sellPointer++) {
            // If current price is lower than buy price, update buy pointer
            if (prices[sellPointer] < prices[buyPointer]) {
                buyPointer = sellPointer;
            } else {
                // Calculate profit and update maximum
                int currentProfit = prices[sellPointer] - prices[buyPointer];
                maxProfit = Math.max(maxProfit, currentProfit);
            }
        }

        return maxProfit;
    }

    // SOLUTION 7: Detailed Step-by-Step Approach (Educational)
    // Time: O(n), Space: O(1)
    // Shows the thinking process clearly
    public int maxProfitDetailed(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int minPrice = prices[0];
        int maxProfit = 0;
        int bestBuyDay = 0;
        int bestSellDay = 0;

        System.out.println("Day-by-day analysis:");
        System.out.printf("Day 0: Price = %d, Min price = %d, Max profit = %d%n",
                prices[0], minPrice, maxProfit);

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
                bestBuyDay = i;
                System.out.printf("Day %d: Price = %d, New min price found! Min = %d%n",
                        i, prices[i], minPrice);
            } else {
                int currentProfit = prices[i] - minPrice;
                if (currentProfit > maxProfit) {
                    maxProfit = currentProfit;
                    bestSellDay = i;
                    System.out.printf("Day %d: Price = %d, New max profit! Profit = %d (buy day %d, sell day %d)%n",
                            i, prices[i], maxProfit, bestBuyDay, bestSellDay);
                } else {
                    System.out.printf("Day %d: Price = %d, Profit would be %d (not better than %d)%n",
                            i, prices[i], currentProfit, maxProfit);
                }
            }
        }

        if (maxProfit > 0) {
            System.out.printf("Best strategy: Buy on day %d at price %d, sell on day %d at price %d%n",
                    bestBuyDay, prices[bestBuyDay], bestSellDay, prices[bestSellDay]);
        } else {
            System.out.println("Best strategy: Don't trade (no profitable opportunity)");
        }

        return maxProfit;
    }

    // Test method to verify solutions
    public static void main(String[] args) {
        BestTimeToBuyAndSellStock solution = new BestTimeToBuyAndSellStock();

        // Test case 1
        int[] prices1 = {10, 1, 5, 6, 7, 1};
        System.out.println("Input: [10,1,5,6,7,1]");
        System.out.println("Output: " + solution.maxProfit(prices1));
        System.out.println("Expected: 6 (buy at 1, sell at 7)");

        System.out.println("\nDetailed analysis:");
        solution.maxProfitDetailed(prices1);

        // Test case 2
        int[] prices2 = {10, 8, 7, 5, 2};
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Input: [10,8,7,5,2]");
        System.out.println("Output: " + solution.maxProfit(prices2));
        System.out.println("Expected: 0 (prices only decrease)");

        System.out.println("\nDetailed analysis:");
        solution.maxProfitDetailed(prices2);

        // Test case 3: Edge cases
        int[] prices3 = {1};
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Input: [1]");
        System.out.println("Output: " + solution.maxProfit(prices3));
        System.out.println("Expected: 0 (only one day)");

        int[] prices4 = {1, 2};
        System.out.println("\nInput: [1,2]");
        System.out.println("Output: " + solution.maxProfit(prices4));
        System.out.println("Expected: 1 (buy at 1, sell at 2)");

        int[] prices5 = {2, 1};
        System.out.println("\nInput: [2,1]");
        System.out.println("Output: " + solution.maxProfit(prices5));
        System.out.println("Expected: 0 (price decreases)");

        // Test case 4: Complex scenario
        int[] prices6 = {7, 1, 5, 3, 6, 4};
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Input: [7,1,5,3,6,4]");
        System.out.println("Output: " + solution.maxProfit(prices6));
        System.out.println("Expected: 5 (buy at 1, sell at 6)");

        // Performance comparison
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Algorithm Comparison:");

        int[] largePrices = new int[10000];
        for (int i = 0; i < 10000; i++) {
            largePrices[i] = (int) (Math.random() * 100);
        }

        long startTime = System.nanoTime();
        int result1 = solution.maxProfit(largePrices);
        long endTime = System.nanoTime();
        System.out.println("Optimal O(n): " + (endTime - startTime) / 1000000.0 + " ms, Result: " + result1);

        startTime = System.nanoTime();
        int result2 = solution.maxProfitKadane(largePrices);
        endTime = System.nanoTime();
        System.out.println("Kadane's variation: " + (endTime - startTime) / 1000000.0 + " ms, Result: " + result2);

        // Note: Brute force would be too slow for 10000 elements
        System.out.println("Brute force O(n²): Too slow for 10000 elements (would take ~50M operations)");
    }
}