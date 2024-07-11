package lma;

//https://www.codewars.com/kata/541c8630095125aba6000c00
public class SumOfDigits {
    public static int digital_root(int n) {
        if (n % 10 == n) return n;
        int sum = 0;
        while (n != 0) {
            int remains = n % 10;
            n = n / 10;
            sum += remains;
        }
        return digital_root(sum);
    }
}