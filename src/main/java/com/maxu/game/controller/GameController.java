package com.maxu.game.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxu.game.bean.CheckerBoard;
import com.maxu.game.bean.Msg;
import com.maxu.game.bean.Position;
import com.maxu.game.utils.GameStrategy;
import com.maxu.game.utils.IsWin;

@Controller
public class GameController {

  @RequestMapping("/pos")
  @ResponseBody
  public Msg getPosition(@RequestParam("position") String position,
      HttpServletRequest request) {
    CheckerBoard checkerBoard = (CheckerBoard) request.getSession()
        .getAttribute("checkerBoard");
    String[] temp = position.split("_");
    int iswin = 0;
    checkerBoard.setChance(Position.stringToPosition(temp[0] + "," + temp[1]));
    iswin = IsWin.isWin(checkerBoard.getBoard());
    // System.out.println(iswin+"afasdf"+position);
    if (iswin == 2) {
      return Msg.quickMsg().add(2).add("null");
    }
    if (checkerBoard.getPieceNum() == 9) {
      return Msg.quickMsg().add(9).add("null");
    }

    Position result = checkerBoard.autoChance();
    iswin = IsWin.isWin(checkerBoard.getBoard());

    // checkerBoard.printCheckBoard();
    // System.out.println(iswin+":"+result.getX()+","+result.getY());
    // ((GameStrategy)checkerBoard.getStrategy()).printWeightBoard();

    if (iswin == 1) {
      return Msg.quickMsg().add(1).add(result.getX() + "_" + result.getY());
    }
    if (checkerBoard.getPieceNum() == 9) {
      return Msg.quickMsg().add(9).add(result.getX() + "_" + result.getY());
    }

    return Msg.quickMsg().add(0).add(result.getX() + "_" + result.getY());
  }

  @RequestMapping("/empty")
  @ResponseBody
  public Msg empty(HttpServletRequest request,
      @RequestParam(value = "chance", defaultValue = "1") Integer chance,
      @RequestParam(value = "easy", defaultValue = "1") Integer easy) {
    CheckerBoard checkerBoard = new CheckerBoard();
    request.removeAttribute("checkerBoard");
    GameStrategy gameStrategy = (GameStrategy) checkerBoard.getStrategy();
    if (easy == 1) {
      gameStrategy.easy = -1;
    } else {
      gameStrategy.easy = 1;
    }
    request.getSession().setAttribute("checkerBoard", checkerBoard);
    checkerBoard.empty();
    checkerBoard.setOrder((byte) -1);
    if (chance == 2) {
      checkerBoard.setOrder((byte) 1);
      Position result = checkerBoard.autoChance();
      return Msg.quickMsg().add(0).add(result.getX() + "_" + result.getY());
    }
    return Msg.quickMsg().add(0).add("null");
  }

}
