package lma;

//https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii
public class BestTimeToBuySellStock2 {
    public int maxProfit(int[] prices) {
        int minPrice = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                maxProfit = maxProfit + prices[i] - minPrice;
                minPrice = prices[i];
            }
        }
        return maxProfit;
    }
}
