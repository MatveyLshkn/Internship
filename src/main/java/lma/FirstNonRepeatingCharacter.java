package lma;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

//https://www.codewars.com/kata/52bc74d4ac05d0945d00054e
public class FirstNonRepeatingCharacter {
    public static String firstNonRepeatingLetter(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toLowerCase().toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((Character.isUpperCase(c) && map.get(Character.toLowerCase(c)) == 1) ||
                (map.containsKey(c) && map.get(c) == 1))
                return c + "";
        }
        return "";
    }

    public static String firstNonRepeatingLetter2(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toLowerCase().toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        OptionalInt ans = s.chars().filter(ch -> map.get((char) Character.toLowerCase(ch)) == 1).findAny();
        return ans.isPresent() ? (char) ans.getAsInt() + "" : "";
    }
}