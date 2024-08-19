package lma;

//https://leetcode.com/problems/find-smallest-letter-greater-than-target/
public class FindSmallestLetterGreaterThanTarget {
    public char nextGreatestLetter(char[] letters, char target) {
        int[] charArray = new int[26];
        for(char letter : letters) {
            charArray[letter - 'a']++;
        }
        int pos = 1 + target - 'a';
        while(pos < charArray.length) {
            if(charArray[pos] > 0){
                return (char)('a' + pos);
            }
            pos++;
        }
        return letters[0];
    }
}
