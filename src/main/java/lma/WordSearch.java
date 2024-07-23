package lma;

import java.util.LinkedList;
import java.util.Queue;

public class WordSearch {
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (processWord(board, word, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean processWord(char[][] board, String word, int index, int startI, int startJ) {
        if (index == word.length()) {
            return true;
        }
        if (startI < 0 || startI >= board.length || startJ < 0 || startJ >= board[0].length
            || board[startI][startJ] != word.charAt(index)) {
            return false;
        }
        char temp = board[startI][startJ];
        board[startI][startJ] = ' ';
        int[][] ways = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] way : ways) {
            int newI = startI + way[0];
            int newJ = startJ + way[1];
            if (processWord(board, word, index + 1, newI, newJ)) {
                return true;
            }
        }
        board[startI][startJ] = temp;
        return false;
    }
}
