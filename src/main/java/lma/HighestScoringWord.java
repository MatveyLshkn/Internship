package lma;


//https://www.codewars.com/kata/57eb8fcdf670e99d9b000272
public class HighestScoringWord {
    public static String high(String s) {
        int maxValue = 0;
        String maxString = "";
        StringBuilder sb = new StringBuilder();
        int value = 0;
        for (int i = 0; i <= s.length(); i++) {
            if (i == s.length() || s.charAt(i) == ' ') {
                if (maxValue < value) {
                    maxString = sb.toString();
                    maxValue = value;
                }
                sb = new StringBuilder();
                value = 0;
            } else {
                value += s.charAt(i) - 'a' + 1;
                sb.append(s.charAt(i));
            }
        }
        return maxString;
    }

    public static void main(String[] args) {
        System.out.println(high("aa b"));
    }
}
