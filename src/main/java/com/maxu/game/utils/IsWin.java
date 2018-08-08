package com.maxu.game.utils;

import com.maxu.game.bean.CheckerBoard;

public class IsWin {
  public static int isWin(byte[][] board) {
    byte[] everyLineScore = new byte[8];
    for (int i = 0; i < 8; i++) {
      everyLineScore[i] = 0;
    }
    byte k = 0;
    for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
      everyLineScore[k++] = (byte) (board[i][0] + board[i][1] + board[i][2]);
    }
    for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
      everyLineScore[k++] = (byte) (board[0][i] + board[1][i] + board[2][i]);
    }
    everyLineScore[k++] = (byte) (board[0][0] + board[1][1] + board[2][2]);
    everyLineScore[k++] = (byte) (board[0][2] + board[1][1] + board[2][0]);

    for (int i = 0; i < 8; i++) {
      if (everyLineScore[i] == 3) {
        return 1;
      }
      if (everyLineScore[i] == -3) {
        return 2;
      }
    }
    return 0;
  }
}
