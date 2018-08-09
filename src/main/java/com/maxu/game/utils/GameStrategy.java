package com.maxu.game.utils;

import com.maxu.game.bean.CheckerBoard;
import com.maxu.game.bean.Position;

public class GameStrategy implements ChanceStrategy{
  int size = CheckerBoard.BOARD_SIZE * 2 + 2;
  byte[] everyLineScore = new byte[size]; // 先3行后3列再左右对角和右左对角
  byte[] everyLineInpiece = new byte[size]; // 顺序同上,判断某条线上是否有棋子有的话就赋值为１，否则赋值为０
  CheckerBoard checkerBoard;
  public int easy = 1;
  byte[][] weightBoard = new byte[CheckerBoard.BOARD_SIZE][CheckerBoard.BOARD_SIZE]; // 将一个棋盘的每个位置赋权值

  public Position chance(CheckerBoard checkerBoard) {
    this.checkerBoard = checkerBoard;
    this.setEveryLineScore();
    this.setWeightBoard();
    System.out.println();
    for (int i = 0; i < size; i++) {
      if (everyLineScore[i] == 2*easy) { // 优先考虑是否会成功
        return getPosition(i);
      } 
    }
    for(int i = 0;i < size;i++) {
      if (everyLineScore[i] == -2*easy) {// 然后考虑对方是否会成功
        return getPosition(i);
      }
    }
    Position position = Position.stringToPosition("0,0");
    int x = position.getX();
    int y = position.getY();
    // 然后根据权重考虑地方
    for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
      for (int j = 0; j < CheckerBoard.BOARD_SIZE; j++) {
        if (weightBoard[i][j] > weightBoard[position.getX()][position
            .getY()]) {
          position.setX((byte) i);
          position.setY((byte) j);
        }
      }
    }
    //如果所有高地的权值一样,随便找个地方
    if(x==position.getX()&&y==position.getY()) {
      for(int i =0;i < CheckerBoard.BOARD_SIZE;i++) {
        for(int j =0;j < CheckerBoard.BOARD_SIZE;j++) {
          if(checkerBoard.getBoard()[i][j]==0) {
            position.setX((byte) i);
            position.setY((byte) j);
            break;
          }
        }
      }
    }
    return position;
  }

  /**
   * 更新 everyLineScore和everyLineInpiece
   */
  private void setEveryLineScore() {
    byte[][] board = checkerBoard.getBoard();
    byte k = 0;
    for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
      everyLineScore[k++] = (byte) (board[i][0] + board[i][1] + board[i][2]);
      if (everyLineScore[k - 1] == 0 && board[i][1] == 0 && board[i][0] == 0) {
        everyLineInpiece[k - 1] = 1;
      } else {
        everyLineInpiece[k - 1] = 0;
      }
    }

    for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
      everyLineScore[k++] = (byte) (board[0][i] + board[1][i] + board[2][i]);
      if (everyLineScore[k - 1] == 0 && board[1][i] == 0 && board[0][i] == 0) {
        everyLineInpiece[k - 1] = 1;
      } else {
        everyLineInpiece[k - 1] = 0;
      }
    }
    everyLineScore[k++] = (byte) (board[0][0] + board[1][1] + board[2][2]);
    if (everyLineScore[k - 1] == 0 && board[1][1] == 0 && board[0][0] == 0) {
      everyLineInpiece[k - 1] = 1;
    } else {
      everyLineInpiece[k - 1] = 0;
    }
    everyLineScore[k++] = (byte) (board[0][2] + board[1][1] + board[2][0]);
    if (everyLineScore[k - 1] == 0 && board[1][1] == 0 && board[0][2] == 0) {
      everyLineInpiece[k - 1] = 1;
    } else {
      everyLineInpiece[k - 1] = 0;
    }
  }

  /**
   * 更新weightBoard
   */
  private void setWeightBoard() {
    byte[][] board = checkerBoard.getBoard();
    for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
      for (int j = 0; j < CheckerBoard.BOARD_SIZE; j++) {
        if (board[i][j] != 0) {
          weightBoard[i][j] = 0;
          continue;
        }

        weightBoard[i][j] = (byte) (everyLineInpiece[i]
            + everyLineInpiece[CheckerBoard.BOARD_SIZE + j]);
        if (everyLineScore[i] > 0) {
          weightBoard[i][j] += everyLineScore[i];
        }
        if (everyLineScore[j + CheckerBoard.BOARD_SIZE] > 0) {
          weightBoard[i][j] += everyLineScore[j + CheckerBoard.BOARD_SIZE];
        }

        if (i == j) {
          weightBoard[i][j] += everyLineInpiece[size - 2];
          if (everyLineScore[size - 2] > 0) {
            weightBoard[i][j] += everyLineScore[size - 2];
          }
        }
        if (i + j == 2) {
          weightBoard[i][j] += everyLineInpiece[size - 1];
          if (everyLineScore[CheckerBoard.BOARD_SIZE - 1] > 0) {
            weightBoard[i][j] += everyLineScore[size - 1];
          }
        }
      }
    }
  }

  /**
   * 根据输入的线(行，列，对角)，判断该在这个线中的哪个位置放置棋子，输入的行有一下特点 该行有己方２颗棋子或者有对方２颗棋子
   * 
   * @param line
   *          输入的线
   * @return 应该放棋子的位置
   */
  private Position getPosition(int line) {
    if (line < CheckerBoard.BOARD_SIZE) {
      for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
        if (checkerBoard.getBoard()[line][i] == 0) {
          return Position.stringToPosition(line + "," + i);
        }
      }
    } else if (line < 2 * CheckerBoard.BOARD_SIZE) {
      for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
        if (checkerBoard.getBoard()[i][line % CheckerBoard.BOARD_SIZE] == 0) {
          return Position
              .stringToPosition(i + "," + line % CheckerBoard.BOARD_SIZE);
        }
      }
    } else if (line == size - 2) {
      for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
        if (checkerBoard.getBoard()[i][i] == 0) {
          return Position.stringToPosition(i + "," + i);
        }
      }
    } else {
      for (int i = 0; i < CheckerBoard.BOARD_SIZE; i++) {
        if (checkerBoard.getBoard()[i][CheckerBoard.BOARD_SIZE - 1 - i] == 0) {
          return Position
              .stringToPosition(i + "," + (CheckerBoard.BOARD_SIZE - 1 - i));
        }
      }
    }
    return null;
  }

  public void printWeightBoard() {
    for(int i = 0;i< 3;i++) {
      for(int j = 0;j < 3;j++) {
        System.out.print(weightBoard[i][j]+ "  ");
      }
      System.out.println();
    }
  }
}