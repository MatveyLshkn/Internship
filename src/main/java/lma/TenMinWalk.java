package lma;

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
}
