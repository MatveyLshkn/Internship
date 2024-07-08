package lma;

//https://www.codewars.com/kata/55bf01e5a717a0d57e0000ec
public class PersistentBugger {
    public static int persistence(long n) {
        int count = 0;
        while (n % 10 != n) {
            count++;
            int mult = 1;
            while (n > 0) {
                mult *= n % 10;
                n /= 10;
            }
            n = mult;

        }
        return count;
    }
}
