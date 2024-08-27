package lma;

//https://leetcode.com/problems/fruit-into-baskets/description/
public class FruitIntoBaskets {

    public int totalFruit(int[] fruits) {
        int max = 0;

        int type1 = fruits[0];
        int type2 = -1;

        int pos1 = 0;
        int pos2 = -1;

        int left = 0;
        int right = 1;

        for (; right < fruits.length; right++) {

            if (type2 == -1 && fruits[right] != type1) {
                type2 = fruits[right];
                pos2 = right;
                continue;
            }

            if (fruits[right] == type1) {
                pos1 = right;
            } else if (fruits[right] == type2) {
                pos2 = right;
            } else {
                max = Math.max(max, right - left);
                if (fruits[right - 1] == type1) {
                    left = pos2 + 1;
                    type2 = fruits[right];
                    pos2 = right;
                } else {
                    left = pos1 + 1;
                    type1 = fruits[right];
                    pos1 = right;
                }
            }
        }
        return Math.max(max, right - left);
    }
}
