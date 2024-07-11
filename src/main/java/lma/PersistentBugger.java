package lma;

//https://www.codewars.com/kata/55bf01e5a717a0d57e0000ec
public class PersistentBugger {
    public static int persistence(long n) {
        int count = 0;
        while (n % 10 != n) {
            count++;
            long mult = 1;
            while (n > 0) {
                mult = mult * (n % 10);
                n = n / 10;
            }
            n = mult;
        }
        return count;
    }

    public static int persistence2(long n) {
        int count = 0;
        while (n % 10 != n) {
            count++;
            n = Long.toString(n).chars().reduce(1, (a, b) -> a * (b - '0'));
        }
        return count;
    }
}