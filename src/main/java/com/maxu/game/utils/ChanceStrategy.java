package com.maxu.game.utils;

import com.maxu.game.bean.CheckerBoard;
import com.maxu.game.bean.Position;

public interface ChanceStrategy {
  /**
   * 根据输入的棋盘返回应该放子的位置
   * @param checkerBoard　　输入的棋盘
   * @return　　　放子的位置
   */
  public Position chance(CheckerBoard checkerBoard);
}
