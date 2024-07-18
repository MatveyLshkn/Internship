package lma;

import java.util.Arrays;


//https://leetcode.com/problems/non-overlapping-intervals/description/
public class NonOverlappingIntervals {
    public static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        int prev = 0;
        int count = 1;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= intervals[prev][1]) {
                prev = i;
                count++;
            }
        }
        return intervals.length - count;
    }
}