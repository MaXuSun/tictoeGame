package com.maxu.game.bean;

public class Position {
  private byte x;       //行
  private byte y;       //列
  public Position(byte x,byte y) {
    this.x = x;
    this.y = y;
  }
  public byte getX() {
    return x;
  }
  public byte getY() {
    return y;
  }
  
  /**
   * 将一个字符串转化为位置，字符串格式应该是　x,y  形式
   * @param poString　传入的字符串
   * @return　一个Position实例
   */
  public static Position stringToPosition(String poString) {
    String[] strings = poString.split(",");
    return new Position(Byte.parseByte(strings[0]), Byte.parseByte(strings[1]));
  }
  
  public void setX(byte x) {
    this.x = x;
  }
  
  public void setY(byte y) {
    this.y = y;
  }
}
