package lma;

//https://www.codewars.com/kata/54521e9ec8e60bc4de000d6c
public class MaxSubarraySum {
    public static int sequence(int[] arr) {
        int max = 0;
        int currSum = 0;
        for (int num : arr) {
            currSum += num;
            if (currSum < 0) currSum = 0;
            max = Math.max(max, currSum);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(sequence(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
