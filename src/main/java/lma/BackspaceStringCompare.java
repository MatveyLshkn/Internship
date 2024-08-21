package lma;

//https://leetcode.com/problems/backspace-string-compare/
public class BackspaceStringCompare {
    public boolean backspaceCompare(String s, String t) {
        int i = s.length() - 1;
        int j = t.length() - 1;
        int countDeleteS = 0;
        int countDeleteT = 0;

        while (true) {
            while (i >= 0) {
                if (s.charAt(i) == '#') {
                    countDeleteS++;
                    i--;
                } else if (countDeleteS > 0) {
                    countDeleteS--;
                    i--;
                } else {
                    break;
                }
            }
            while (j >= 0) {
                if (t.charAt(j) == '#') {
                    countDeleteT++;
                    j--;
                } else if (countDeleteT > 0) {
                    countDeleteT--;
                    j--;
                } else {
                    break;
                }
            }
            if (i >= 0 && j >= 0) {
                if (s.charAt(i) == t.charAt(j)) {
                    i--;
                    j--;
                } else {
                    return false;
                }
            } else {
                break;
            }
        }
        return i == j;
    }
}
