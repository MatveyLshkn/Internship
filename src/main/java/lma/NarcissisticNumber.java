package lma;

//https://www.codewars.com/kata/5287e858c6b5a9678200083c
public class NarcissisticNumber {
    public static boolean isNarcissistic(int number) {
        int numCopy = number;
        int digitCount = 0;
        while (numCopy > 0) {
            numCopy /= 10;
            digitCount++;
        }
        int ans = 0;
        numCopy = number;
        while (numCopy > 0) {
            ans += Math.pow(numCopy % 10, digitCount);
            numCopy /= 10;
        }
        return ans == number;
    }
}
