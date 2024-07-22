package lma;

import java.util.Arrays;

//https://leetcode.com/problems/product-of-array-except-self/
public class ProductOfArrayExceptSelf {
    public static int[] productExceptSelf(int[] nums) {
        int ans[] = new int[nums.length];
        Arrays.fill(ans, 1);
        int curr = 1;
        for(int i = 0; i < nums.length; i++) {
            ans[i] = ans[i] * curr;
            curr = curr * nums[i];
        }
        curr = 1;
        for(int i = nums.length - 1; i >= 0; i--) {
            ans[i] = ans[i] * curr;
            curr = curr * nums[i];
        }
        return ans;
    }
}