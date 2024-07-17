package lma;

import java.util.Arrays;

//https://leetcode.com/problems/majority-element/
public class MajorityElement {
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;
        for(int num : nums) {
            if(count == 0){
                candidate = num;
            }
            if(num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }

    public int majorityElementShortestSolution(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
    }
}
