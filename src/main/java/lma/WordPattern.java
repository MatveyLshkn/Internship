package lma;

import java.util.HashMap;

//https://leetcode.com/problems/word-pattern
public class WordPattern {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) {
            return false;
        }

        HashMap<Character, String> map1 = new HashMap<>();
        HashMap<String, Character> map2 = new HashMap<>();
        int index = 0;
        for (String word : words) {
            if ((map1.containsKey(pattern.charAt(index)) && !map1.get(pattern.charAt(index)).equals(word)) ||
                (map2.containsKey(word) && !map2.get(word).equals(pattern.charAt(index)))) {
                return false;
            }
            map1.put(pattern.charAt(index), word);
            map2.put(word, pattern.charAt(index));
            index++;
        }
        return true;
    }
}