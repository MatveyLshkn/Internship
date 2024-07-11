package lma;

import java.util.stream.Stream;

//https://www.codewars.com/kata/54da539698b8a2ad76000228
public class TenMinWalk {
    public static boolean isValid(char[] walk) {
        if (walk.length != 10) return false;
        int x = 0;
        int y = 0;
        for (char ch : walk) {
            if (ch == 'n') y++;
            else if (ch == 's') y--;
            else if (ch == 'w') x--;
            else if (ch == 'e') x++;
        }
        return x == 0 && y == 0;
    }

    public static boolean isValid2(char[] walk) {
        if (walk.length != 10) return false;
        String s = new String(walk);
        return s.chars().filter(c -> c == 'n').count() == s.chars().filter(c -> c == 's').count()
               && s.chars().filter(c -> c == 'w').count() == s.chars().filter(c -> c == 'e').count();
    }
}