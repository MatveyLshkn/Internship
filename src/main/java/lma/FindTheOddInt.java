package lma;

//https://www.codewars.com/kata/54da5a58ea159efa38000836
public class FindTheOddInt {
    public static int findIt(int[] a) {
        int odd = 0;
        for (int i : a) {
            odd ^= i;
        }
        return odd;
    }
}
