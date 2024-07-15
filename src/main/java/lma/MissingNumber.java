package lma;

import java.util.Arrays;

//https://leetcode.com/problems/missing-number/description/
public class MissingNumber {
    public int missingNumber(int[] nums) {
        int supposedSum = nums.length * (nums.length + 1) / 2; //arithmetic progression
        for (int i = 0; i < nums.length; i++) {
            supposedSum -= nums[i];
        }
        return supposedSum;
    }

    public int missingNumber2(int[] nums) {
        return Arrays.stream(nums)
                .reduce(nums.length * (nums.length + 1) / 2, (a, b) -> a - b);
    }
}
