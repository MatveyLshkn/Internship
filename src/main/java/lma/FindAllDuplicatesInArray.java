package lma;


import java.util.ArrayList;
import java.util.List;

//https://leetcode.com/problems/find-all-duplicates-in-an-array/
public class FindAllDuplicatesInArray {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int pos = nums[i];
            if (pos < 0) {
                pos = -pos;
            }
            if (nums[pos - 1] < 0) {
                ans.add(pos);
            } else {
                nums[pos - 1] = -nums[pos - 1];
            }
        }
        return ans;
    }
}
