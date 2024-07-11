package lma;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//https://www.codewars.com/kata/52c31f8e6605bcc646000082
public class TwoSum {
    public static int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(target - numbers[i])) {
                return new int[]{map.get(target - numbers[i]), i};
            } else {
                map.put(numbers[i], i);
            }
        }
        return null;
    }

    public static int[] twoSum2(int[] numbers, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        return IntStream.range(0, numbers.length)
                .mapToObj(i -> {
                    map.putIfAbsent(numbers[i], i);
                    return new int[]{map.getOrDefault(target - numbers[i], -1), i};
                }).filter(set -> set[0] != -1 && set[0] != set[1]).findAny().get();
    }
}