#https://neetcode.io/problems/buy-and-sell-crypto?list=blind75
def max_profit(prices):
    """
    Given an array of stock prices, return the maximum profit from buying 
    and selling once (buy before sell).
    
    Time Complexity: O(n)
    Space Complexity: O(1)
    """
    if len(prices) < 2:
        return 0
    
    min_price = prices[0]  # Minimum price seen so far
    max_profit = 0         # Maximum profit possible
    
    for i in range(1, len(prices)):
        current_price = prices[i]
        
        # Calculate profit if we sell today
        current_profit = current_price - min_price
        
        # Update maximum profit if current is better
        max_profit = max(max_profit, current_profit)
        
        # Update minimum price if current is lower
        min_price = min(min_price, current_price)
    
    return max_profit


def max_profit_verbose(prices):
    """
    Same algorithm but with detailed step-by-step output for demonstration
    """
    print(f"Input prices: {prices}")
    print("Finding maximum profit from single buy-sell transaction...\n")
    
    if len(prices) < 2:
        print("Not enough prices for a transaction")
        return 0
    
    min_price = prices[0]
    max_profit = 0
    best_buy_day = 0
    best_sell_day = 0
    
    print(f"Day 0: Price = {prices[0]}, Min price = {min_price}, Max profit = {max_profit}")
    
    for i in range(1, len(prices)):
        current_price = prices[i]
        current_profit = current_price - min_price
        
        print(f"Day {i}: Price = {current_price}")
        print(f"  If we sell today: profit = {current_price} - {min_price} = {current_profit}")
        
        if current_profit > max_profit:
            max_profit = current_profit
            best_sell_day = i
            # Find the day when we bought at min_price
            for j in range(i):
                if prices[j] == min_price:
                    best_buy_day = j
                    break
            print(f"  ✓ New max profit: {max_profit} (buy day {best_buy_day}, sell day {best_sell_day})")
        else:
            print(f"  Current max profit remains: {max_profit}")
        
        if current_price < min_price:
            min_price = current_price
            print(f"  ✓ New min price: {min_price}")
        
        print(f"  Current state: min_price = {min_price}, max_profit = {max_profit}")
        print()
    
    if max_profit > 0:
        print(f"Best strategy: Buy on day {best_buy_day} at ${prices[best_buy_day]}, sell on day {best_sell_day} at ${prices[best_sell_day]}")
    else:
        print("No profitable transaction possible")
    
    print(f"Maximum profit: {max_profit}")
    return max_profit


def max_profit_brute_force(prices):
    """
    Brute force solution for comparison - O(n²) time
    Check all possible buy-sell combinations
    """
    if len(prices) < 2:
        return 0
    
    max_profit = 0
    
    for i in range(len(prices)):
        for j in range(i + 1, len(prices)):
            profit = prices[j] - prices[i]
            max_profit = max(max_profit, profit)
    
    return max_profit


def max_profit_with_days(prices):
    """
    Returns both the maximum profit and the buy/sell days
    """
    if len(prices) < 2:
        return 0, -1, -1
    
    min_price = prices[0]
    max_profit = 0
    min_day = 0
    buy_day = 0
    sell_day = 0
    
    for i in range(1, len(prices)):
        current_price = prices[i]
        current_profit = current_price - min_price
        
        if current_profit > max_profit:
            max_profit = current_profit
            buy_day = min_day
            sell_day = i
        
        if current_price < min_price:
            min_price = current_price
            min_day = i
    
    return max_profit, buy_day, sell_day


def visualize_stock_chart(prices):
    """
    Create a simple ASCII visualization of the stock prices
    """
    if not prices:
        return
    
    max_price = max(prices)
    min_price = min(prices)
    
    if max_price == min_price:
        print("All prices are the same")
        return
    
    # Normalize prices to fit in display range
    height = 10
    normalized = []
    for price in prices:
        norm = int((price - min_price) / (max_price - min_price) * (height - 1))
        normalized.append(norm)
    
    print("Stock Price Chart:")
    print(f"Max: ${max_price}")
    
    # Draw chart from top to bottom
    for level in range(height - 1, -1, -1):
        line = f"{min_price + (max_price - min_price) * level / (height - 1):4.0f} "
        for norm_price in normalized:
            if norm_price >= level:
                line += "█"
            else:
                line += " "
        print(line)
    
    # Draw day numbers
    day_line = "     "
    for i in range(len(prices)):
        day_line += str(i % 10)
    print(day_line)
    print(f"Min: ${min_price}")


def analyze_trading_strategy():
    """
    Explain the trading strategy and why it works
    """
    print("Trading Strategy Analysis")
    print("=" * 40)
    print("The algorithm follows a simple but optimal strategy:")
    print()
    print("1. Keep track of the lowest price seen so far")
    print("2. For each day, calculate profit if we sell today")
    print("3. Update maximum profit if current profit is better")
    print("4. Update minimum price if current price is lower")
    print()
    print("Why this works:")
    print("• We want to buy at the lowest price and sell at the highest price")
    print("• Since we must buy before we sell, we track the minimum price up to each day")
    print("• For each day, we calculate the best profit if we sell that day")
    print("• This ensures we find the optimal buy-sell combination")
    print()


def test_solution():
    test_cases = [
        [10, 1, 5, 6, 7, 1],
        [10, 8, 7, 5, 2],
        [1, 2, 3, 4, 5],
        [5, 4, 3, 2, 1],
        [3, 3, 3, 3],
        [1, 2],
        [2, 1],
        [7, 1, 5, 3, 6, 4],
        [1, 4, 2],
        [2, 4, 1]
    ]
    
    print("=== Testing Best Time to Buy and Sell Stock ===\n")
    
    for i, prices in enumerate(test_cases, 1):
        print(f"Test Case {i}:")
        result = max_profit(prices)
        brute_force_result = max_profit_brute_force(prices)
        profit, buy_day, sell_day = max_profit_with_days(prices)
        
        print(f"Input: {prices}")
        print(f"Optimized result: {result}")
        print(f"Brute force result: {brute_force_result}")
        print(f"Results match: {result == brute_force_result}")
        
        if profit > 0:
            print(f"Best trade: Buy day {buy_day} (${prices[buy_day]}), Sell day {sell_day} (${prices[sell_day]})")
        else:
            print("No profitable trade possible")
        
        # Show detailed process for first few examples
        if i <= 2:
            print("\nDetailed process:")
            max_profit_verbose(prices.copy())
            print("\nVisualization:")
            visualize_stock_chart(prices)
        
        print("-" * 60)


def performance_comparison():
    """
    Compare optimized vs brute force performance
    """
    import time
    
    # Generate test data
    test_prices = list(range(100, 0, -1)) + list(range(1, 101))  # 200 elements
    
    # Optimized solution
    start = time.time()
    for _ in range(10000):
        max_profit(test_prices)
    optimized_time = time.time() - start
    
    # Brute force solution
    start = time.time()
    for _ in range(100):
        max_profit_brute_force(test_prices)
    brute_force_time = (time.time() - start) * 100  # Scale to match iterations
    
    print(f"Optimized O(n): {optimized_time:.4f} seconds")
    print(f"Brute force O(n²): {brute_force_time:.4f} seconds")
    print(f"Optimized is {brute_force_time/optimized_time:.2f}x faster")


if __name__ == "__main__":
    analyze_trading_strategy()
    test_solution()
    print("\n=== Performance Comparison ===")
    performance_comparison()
