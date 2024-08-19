package lma;

//https://leetcode.com/problems/squares-of-a-sorted-array/description/
public class SquaresOfSortedArray {
    public int[] sortedSquares(int[] nums) {
        int ans[] = new int[nums.length];
        int ansP = nums.length - 1;
        int right = nums.length - 1;
        int left = 0;
        while (left <= right) {
            if (Math.pow(nums[left], 2) > Math.pow(nums[right], 2)) {
                ans[ansP] = (int) Math.pow(nums[left], 2);
                left++;
            } else {
                ans[ansP] = (int) Math.pow(nums[right], 2);
                right--;
            }
            ansP--;
        }
        return ans;
    }
}
