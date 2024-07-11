package lma;

//https://leetcode.com/problems/climbing-stairs/description/
public class ClimbingStairs {
    public int climbStairs(int n) {
        if (n <= 3) return n;
        int curr = 0;
        int prev1 = 2;
        int prev2 = 3;
        for (int i = 4; i <= n; i++) {
            curr = prev1 + prev2;
            prev1 = prev2;
            prev2 = curr;
        }
        return curr;
    }
}
