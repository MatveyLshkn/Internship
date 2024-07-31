package lma;

//https://leetcode.com/problems/maximum-average-subarray-i/
public class MaximumAvgSubArray1 {
    public double findMaxAverage(int[] nums, int k) {
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum = sum + nums[i];
        }

        int maxSum = sum;
        for (int i = k; i < nums.length; i++) {
            sum = sum + nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, sum);
        }
        return (double) maxSum / k;
    }
}
