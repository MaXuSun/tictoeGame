package com.maxu.game.bean;

public class Msg {
  public  int isWin;
  public  String position;
  public static Msg quickMsg() {
    return new Msg();
  }
  public Msg add(int isWin) {
    this.isWin = isWin;
    return this;
  }
  
  public Msg add(String position) {
    this.position = position;
    return this;
  }
}
