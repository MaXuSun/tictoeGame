package com.maxu.game.bean;

import com.maxu.game.utils.ChanceStrategy;
import com.maxu.game.utils.GameStrategy;

public class CheckerBoard {
  public final static byte BOARD_SIZE = 3;
  private byte[][] board = new byte[BOARD_SIZE][BOARD_SIZE];
  private byte order = 1;             // 记录放棋子的是电脑还是玩家，电脑为１，玩家为-1
  private ChanceStrategy strategy = new GameStrategy();    //电脑自动选择位置的策略
  private byte pieceNum = 0;          //棋盘上记录棋子的个数

  /**
   * 打印棋盘
   */
  public void printCheckBoard() {
    System.out.println("    1  2  3 ");
    for (int i = 0; i < BOARD_SIZE; i++) {
      System.out.print(i+1 + " ");
      for (int j = 0; j < BOARD_SIZE; j++) {
        if (board[i][j] == 1) {
          System.out.print("|X ");
        } else if (board[i][j] == -1) {
          System.out.print("|O ");
        } else {
          System.out.print("|  ");
        }
      }
      System.out.println("|");
    }
  }

  /**
   * 自动选择落子地方
   */
  public Position autoChance() {
    Position position = this.strategy.chance(this);
    setChance(position);
    return position;
  }

  /**
   * 将某个位置上设置一个棋子，并将下次下棋者转化为另一方
   * 
   * @param position　　　需要放置棋子的位置
   * @return　如果该位置有棋子，就返回false，否则返回true
   */
  public boolean setChance(Position position) {
    if (this.board[position.getX()][position.getY()] != 0) {
      return false;
    } else {
      this.board[position.getX()][position.getY()] = order;
      order = (byte) -order;
      pieceNum++;
      return true;
    }
  }
  
  public void empty() {
    for(int i =0;i < 3;i++) {
      for(int j = 0;j < 3;j++) {
        this.board[i][j] = 0;
      }
    }
    this.pieceNum = 0;
  }

  public byte[][] getBoard() {
    return this.board;
  }

  public void setStrage(GameStrategy chanceStrategy) {
    this.strategy = chanceStrategy;
  }

  public void setOrder(byte order) {
    this.order = order;
  }

  public byte getOrder() {
    return this.order;
  }

  public byte getPieceNum() {
    return pieceNum;
  }
  
  public ChanceStrategy getStrategy() {
    return this.strategy;
  }
}
