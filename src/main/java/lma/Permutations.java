package lma;

import java.util.ArrayList;
import java.util.List;

//https://leetcode.com/problems/permutations/
public class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> ans = new ArrayList<>();
        permutation(nums, ans, new ArrayList<>());
        return ans;
    }

    private void permutation(int nums[], List<List<Integer>> ans, List<Integer> temp) {
        if (temp.size() == nums.length) {
            ans.add(new ArrayList<>(temp));
            return;
        }
        for (int num : nums) {
            if (temp.contains(num)) {
                continue;
            }
            temp.add(num);
            permutation(nums, ans, temp);
            temp.remove(temp.size() - 1);
        }
    }
}
