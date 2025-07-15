//https://neetcode.io/problems/coin-change?list=blind75
public class Solution {
    
    // Approach 1: Dynamic Programming (Bottom-up) - Most intuitive
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        
        // dp[i] represents minimum coins needed to make amount i
        int[] dp = new int[amount + 1];
        
        // Initialize with impossible value (amount + 1 is always impossible)
        java.util.Arrays.fill(dp, amount + 1);
        
        // Base case: 0 coins needed to make amount 0
        dp[0] = 0;
        
        // For each amount from 1 to target amount
        for (int i = 1; i <= amount; i++) {
            // Try each coin
            for (int coin : coins) {
                if (coin <= i) {
                    // If we can use this coin, try using it
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        // If dp[amount] is still impossible value, return -1
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    // Approach 2: BFS (Breadth-First Search) - Alternative perspective
    public int coinChangeBFS(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        boolean[] visited = new boolean[amount + 1];
        
        queue.offer(0);
        visited[0] = true;
        int level = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            
            for (int i = 0; i < size; i++) {
                int current = queue.poll();
                
                for (int coin : coins) {
                    int next = current + coin;
                    
                    if (next == amount) {
                        return level;
                    }
                    
                    if (next < amount && !visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
        }
        
        return -1;
    }
    
    // Approach 3: Recursive with Memoization (Top-down DP)
    public int coinChangeRecursive(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        
        int[] memo = new int[amount + 1];
        java.util.Arrays.fill(memo, -2); // -2 means not computed, -1 means impossible
        
        int result = coinChangeHelper(coins, amount, memo);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int coinChangeHelper(int[] coins, int amount, int[] memo) {
        if (amount == 0) {
            return 0;
        }
        
        if (amount < 0) {
            return Integer.MAX_VALUE;
        }
        
        if (memo[amount] != -2) {
            return memo[amount] == -1 ? Integer.MAX_VALUE : memo[amount];
        }
        
        int minCoins = Integer.MAX_VALUE;
        
        for (int coin : coins) {
            int result = coinChangeHelper(coins, amount - coin, memo);
            if (result != Integer.MAX_VALUE) {
                minCoins = Math.min(minCoins, result + 1);
            }
        }
        
        memo[amount] = minCoins == Integer.MAX_VALUE ? -1 : minCoins;
        return minCoins;
    }
    
    // Approach 4: Optimized DP with early termination\n    public int coinChangeOptimized(int[] coins, int amount) {\n        if (amount == 0) {\n            return 0;\n        }\n        \n        // Sort coins in descending order for potential early termination\n        Integer[] sortedCoins = new Integer[coins.length];\n        for (int i = 0; i < coins.length; i++) {\n            sortedCoins[i] = coins[i];\n        }\n        java.util.Arrays.sort(sortedCoins, java.util.Collections.reverseOrder());\n        \n        int[] dp = new int[amount + 1];\n        java.util.Arrays.fill(dp, amount + 1);\n        dp[0] = 0;\n        \n        for (int i = 1; i <= amount; i++) {\n            for (int coin : sortedCoins) {\n                if (coin > i) {\n                    continue; // Skip coins larger than current amount\n                }\n                \n                if (dp[i - coin] != amount + 1) {\n                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);\n                }\n            }\n        }\n        \n        return dp[amount] > amount ? -1 : dp[amount];\n    }\n    \n    // Approach 5: Space-optimized BFS\n    public int coinChangeSpaceOptimizedBFS(int[] coins, int amount) {\n        if (amount == 0) {\n            return 0;\n        }\n        \n        java.util.Set<Integer> visited = new java.util.HashSet<>();\n        java.util.Queue<Integer> queue = new java.util.LinkedList<>();\n        \n        queue.offer(0);\n        visited.add(0);\n        int steps = 0;\n        \n        while (!queue.isEmpty()) {\n            steps++;\n            int size = queue.size();\n            \n            for (int i = 0; i < size; i++) {\n                int current = queue.poll();\n                \n                for (int coin : coins) {\n                    int next = current + coin;\n                    \n                    if (next == amount) {\n                        return steps;\n                    }\n                    \n                    if (next < amount && !visited.contains(next)) {\n                        visited.add(next);\n                        queue.offer(next);\n                    }\n                }\n            }\n        }\n        \n        return -1;\n    }\n    \n    // Helper method to demonstrate the DP process\n    public void demonstrateCoinChange(int[] coins, int amount) {\n        System.out.println(\"\\nDemonstrating Coin Change for coins=\" + \n                          java.util.Arrays.toString(coins) + \", amount=\" + amount);\n        \n        if (amount == 0) {\n            System.out.println(\"Amount is 0, result = 0\");\n            return;\n        }\n        \n        int[] dp = new int[amount + 1];\n        java.util.Arrays.fill(dp, amount + 1);\n        dp[0] = 0;\n        \n        System.out.println(\"Initial: dp[0] = 0, others = \" + (amount + 1));\n        \n        for (int i = 1; i <= amount; i++) {\n            System.out.print(\"Amount \" + i + \": \");\n            \n            for (int coin : coins) {\n                if (coin <= i && dp[i - coin] != amount + 1) {\n                    int newValue = dp[i - coin] + 1;\n                    System.out.print(\"coin \" + coin + \"->\" + newValue + \" \");\n                    dp[i] = Math.min(dp[i], newValue);\n                }\n            }\n            \n            System.out.println(\"=> dp[\" + i + \"] = \" + \n                             (dp[i] > amount ? \"impossible\" : dp[i]));\n        }\n        \n        System.out.println(\"Final result: \" + (dp[amount] > amount ? -1 : dp[amount]));\n    }\n    \n    // Method to find and print the actual coins used\n    public java.util.List<Integer> findCoinsUsed(int[] coins, int amount) {\n        java.util.List<Integer> result = new java.util.ArrayList<>();\n        \n        if (amount == 0) {\n            return result;\n        }\n        \n        int[] dp = new int[amount + 1];\n        int[] parent = new int[amount + 1]; // Track which coin was used\n        \n        java.util.Arrays.fill(dp, amount + 1);\n        java.util.Arrays.fill(parent, -1);\n        dp[0] = 0;\n        \n        for (int i = 1; i <= amount; i++) {\n            for (int coin : coins) {\n                if (coin <= i && dp[i - coin] + 1 < dp[i]) {\n                    dp[i] = dp[i - coin] + 1;\n                    parent[i] = coin;\n                }\n            }\n        }\n        \n        if (dp[amount] > amount) {\n            return result; // Impossible\n        }\n        \n        // Reconstruct the solution\n        int current = amount;\n        while (current > 0) {\n            int coin = parent[current];\n            result.add(coin);\n            current -= coin;\n        }\n        \n        return result;\n    }\n    \n    // Test method\n    public static void main(String[] args) {\n        Solution solution = new Solution();\n        \n        // Test cases\n        int[][] coinArrays = {\n            {1, 5, 10},\n            {2},\n            {1},\n            {1, 3, 4},\n            {2, 5},\n            {1, 2, 5}\n        };\n        \n        int[] amounts = {12, 3, 0, 6, 11, 11};\n        \n        for (int i = 0; i < coinArrays.length; i++) {\n            int[] coins = coinArrays[i];\n            int amount = amounts[i];\n            \n            System.out.println(\"\\nTest Case \" + (i + 1) + \":\");\n            System.out.println(\"Coins: \" + java.util.Arrays.toString(coins));\n            System.out.println(\"Amount: \" + amount);\n            \n            int result1 = solution.coinChange(coins, amount);\n            int result2 = solution.coinChangeBFS(coins, amount);\n            int result3 = solution.coinChangeRecursive(coins, amount);\n            int result4 = solution.coinChangeOptimized(coins, amount);\n            \n            System.out.println(\"DP Result: \" + result1);\n            System.out.println(\"BFS Result: \" + result2);\n            System.out.println(\"Recursive Result: \" + result3);\n            System.out.println(\"Optimized Result: \" + result4);\n            \n            if (result1 != -1) {\n                java.util.List<Integer> coinsUsed = solution.findCoinsUsed(coins, amount);\n                System.out.println(\"Coins used: \" + coinsUsed);\n            }\n            \n            // Demonstrate DP process for smaller examples\n            if (amount <= 12 && result1 != -1) {\n                solution.demonstrateCoinChange(coins, amount);\n            }\n            \n            System.out.println(\"---\");\n        }\n    }\n}
