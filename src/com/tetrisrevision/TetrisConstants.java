package com.tetrisrevision;

public abstract class TetrisConstants {
  public static int down() {
    return -1;
  }

  public static int up() {
    return 1;
  }

  public static int left() {
    return -1;
  }

  public static int right() {
    return 1;
  }

  public static int width() {
    return 10;
  }

  public static int height() {
    return 20;
  }

  public static int topRow() {
    return TetrisConstants.height() - 1;
  }

  public static int bottomRow() {
    return 0;
  }

//  public static int initialDropDelay() {
//    return 800;
//  }

  public static int clockwise() {
    return 1;
  }

  public static int counterClockwise() {
    return -1;
  }

  public static int timerDelay() {
    return 500;
  }

  public static int leftBound() {
    return 0;
  }

//  public static int rightBound() {
//    return width() - 1;
//  }
}
