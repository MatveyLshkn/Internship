package lma;

import java.util.ArrayList;
import java.util.List;

//https://leetcode.com/problems/interval-list-intersections/
public class IntervalListIntersections {
    public static int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> ans = new ArrayList<>();
        int first = 0;
        int second = 0;
        while (first < firstList.length && second < secondList.length) {
            if (secondList[second][0] <= firstList[first][1] && firstList[first][0] <= secondList[second][1])  {
                int[] pair = new int[2];
                pair[0] = Math.max(firstList[first][0], secondList[second][0]);
                pair[1] = Math.min(firstList[first][1], secondList[second][1]);
                ans.add(pair);
            }
            if (secondList[second][1] > firstList[first][1]) {
                first++;
            } else second++;
        }
        return ans.toArray(new int[ans.size()][]);
    }
}
