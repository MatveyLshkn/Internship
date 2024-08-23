package lma;

//https://leetcode.com/problems/permutation-in-string/description/
public class PermutationInString {
    public static boolean checkInclusion(String s1, String s2) {

        int map[] = new int[26];
        for (char ch : s1.toCharArray()) {
            map[ch - 'a']++;
        }

        int left = 0;
        int right = 0;
        int remains = s1.length();

        while (right < s2.length()) {

            if (map[s2.charAt(right) - 'a'] > 0) {
                remains--;
            }

            map[s2.charAt(right) - 'a']--;
            right++;

            if (remains == 0) {
                return true;
            }

            if (right - left == s1.length()) {

                if (map[s2.charAt(left) - 'a'] >= 0) {
                    remains++;
                }

                map[s2.charAt(left) - 'a']++;
                left++;

            }
        }

        return false;
    }
}
