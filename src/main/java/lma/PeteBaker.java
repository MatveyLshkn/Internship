package lma;

import java.util.Map;

//https://www.codewars.com/kata/525c65e51bf619685c000059
public class PeteBaker {
    public static int cakes(Map<String, Integer> recipe, Map<String, Integer> available) {
        int max = Integer.MAX_VALUE;
        for (Map.Entry<String, Integer> entry : recipe.entrySet()) {
            if (available.containsKey(entry.getKey())) {
                int dif = available.get(entry.getKey()) / entry.getValue();
                max = Math.min(max, dif);
            } else return 0;
        }
        return max;
    }
}
