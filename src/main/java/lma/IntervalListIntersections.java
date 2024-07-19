package lma;

import java.util.ArrayList;
import java.util.List;

//https://leetcode.com/problems/interval-list-intersections/
public class IntervalListIntersections {
    public static int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> ans = new ArrayList<>();
        int firstIndex = 0;
        int secondIndex = 0;
        while (firstIndex < firstList.length && secondIndex < secondList.length) {
            if (secondList[secondIndex][0] <= firstList[firstIndex][1] &&
                firstList[firstIndex][0] <= secondList[secondIndex][1]) {
                int[] pair = new int[2];
                pair[0] = Math.max(firstList[firstIndex][0], secondList[secondIndex][0]);
                pair[1] = Math.min(firstList[firstIndex][1], secondList[secondIndex][1]);
                ans.add(pair);
            }
            if (secondList[secondIndex][1] > firstList[firstIndex][1]) {
                firstIndex++;
            } else {
                secondIndex++;
            }
        }
        return ans.toArray(new int[ans.size()][]);
    }
}
