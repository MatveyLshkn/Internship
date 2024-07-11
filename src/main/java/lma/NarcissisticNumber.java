package lma;

//https://www.codewars.com/kata/5287e858c6b5a9678200083c
public class NarcissisticNumber {
    public static boolean isNarcissistic(int number) {
        int numCopy = number;
        int digitCount = 0;
        while (numCopy > 0) {
            numCopy = numCopy / 10;
            digitCount++;
        }
        int ans = 0;
        numCopy = number;
        while (numCopy > 0) {
            ans += Math.pow(numCopy % 10, digitCount);
            numCopy = numCopy / 10;
        }
        return ans == number;
    }

    public static boolean isNarcissistic2(int number) {
        String numAsStr = number + "";
        int digitCount = numAsStr.length();
        int ans = numAsStr.chars().reduce(0, (a, b) -> (int) (a + Math.pow(b - '0', digitCount)));
        return ans == number;
    }
}
