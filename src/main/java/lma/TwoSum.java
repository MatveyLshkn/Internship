package lma;

import java.util.Arrays;
import java.util.HashMap;

//https://www.codewars.com/kata/52c31f8e6605bcc646000082
public class TwoSum {
    public static int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(target - numbers[i])) {
                return new int[]{map.get(target - numbers[i]), i};
            }else {
                map.put(numbers[i], i);
            }
        }
        return null;
    }
}
