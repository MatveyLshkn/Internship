package lma;

//https://leetcode.com/problems/search-a-2d-matrix/
public class Search2dMatrix {
    public boolean searchMatrix(int[][] matrix, int target) {
        int left = matrix[0].length - 1;
        int top = 0;
        while (left >= 0 && top < matrix.length) {
            if (target > matrix[top][left]) {
                top++;
            } else if (target == matrix[top][left]) {
                return true;
            } else {
                left--;
            }
        }
        return false;
    }
}
