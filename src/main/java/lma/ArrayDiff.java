package lma;

import java.util.*;
import java.util.stream.Stream;

//https://www.codewars.com/kata/523f5d21c841566fde000009
public class ArrayDiff {
    public static int[] arrayDiff(int[] a, int[] b) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < a.length; i++) {
            set.add(a[i]);
        }
        for (int i = 0; i < b.length; i++) {
            if (set.contains(b[i])) {
                set.remove(b[i]);
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            if (set.contains(a[i])) {
                ans.add(a[i]);
            }
        }
        return ans.stream().mapToInt(i -> i).toArray();
    }

    public static int[] arrayDiff2(int[] a, int[] b) {
        return Arrays.stream(a).filter(aElem -> Arrays.stream(b).noneMatch(bElem -> bElem == aElem)).toArray();
    }
}
