package com.maxu.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxu.game.bean.CheckerBoard;
import com.maxu.game.bean.Msg;
import com.maxu.game.bean.Position;
import com.maxu.game.utils.IsWin;

@Controller
public class GameController {

  CheckerBoard checkerBoard = new CheckerBoard();

  @RequestMapping("/pos")
  @ResponseBody
  public Msg getPosition(@RequestParam("position") String position) {
    String[] temp = position.split("_");
    checkerBoard.setChance(Position.stringToPosition(temp[0] + "," + temp[1]));
    if(IsWin.isWin(checkerBoard.getBoard()) == 2) {
      return Msg.quickMsg().add(2).add("null");
    }
    if (checkerBoard.getPieceNum() == 9) {
      return Msg.quickMsg().add(9).add("null");
    }
    Position result = checkerBoard.autoChance();
    if(IsWin.isWin(checkerBoard.getBoard()) == 1) {
      return Msg.quickMsg().add(1).add(result.getX() + "_" + result.getY());
    }
    if (checkerBoard.getPieceNum() == 9) {
      return Msg.quickMsg().add(9).add(result.getX() + "_" + result.getY());
    }
    return Msg.quickMsg().add(0).add(result.getX() + "_" + result.getY());
  }

  @RequestMapping("/empty")
  @ResponseBody
  public void empty() {
    this.checkerBoard.empty();
    this.checkerBoard.setOrder((byte)-1);
  }

}
