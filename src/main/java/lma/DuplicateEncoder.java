package lma;

import java.util.HashMap;
import java.util.Map;

//https://www.codewars.com/kata/54b42f9314d9229fd6000d9c
public class DuplicateEncoder {
    static String encode(String word) {
        char[] wordAsLowercaseCharArray = word.toLowerCase().toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (char c : wordAsLowercaseCharArray) {

            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        StringBuilder result = new StringBuilder();
        for (char c : wordAsLowercaseCharArray) {
            if (map.get(c) > 1) {
                result.append(")");
            } else result.append("(");
        }
        return result.toString();
    }
}
