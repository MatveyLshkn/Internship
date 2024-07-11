package lma;


import java.util.Arrays;
import java.util.Comparator;

//https://www.codewars.com/kata/57eb8fcdf670e99d9b000272
public class HighestScoringWord {
    public static String high(String s) {
        int maxValue = 0;
        String maxString = "";
        String currString = "";
        int value = 0;
        for (int i = 0; i <= s.length(); i++) {
            if (i == s.length() || s.charAt(i) == ' ') {
                if (maxValue < value) {
                    maxString = currString;
                    maxValue = value;
                }
                currString = "";
                value = 0;
            } else {
                value += s.charAt(i) - 'a' + 1;
                currString += s.charAt(i);
            }
        }
        return maxString;
    }

    public static String high2(String s) {
        return Arrays.stream(s.split(" "))
                .max(Comparator.comparing(str -> str.chars().map(ch -> ch - 'a' + 1).sum())).get();
    }

}
