package com.tetrisrevision;

public abstract class Constants {
  public static int down = -1;
  public static int up = 1;
  public static int left = -1;
  public static int right = 1;

  public static int width = 10;
  public static int height = 20;

  static int topRow = Constants.height - 1;
  static int bottomRow = 0;
  static int bottomBoundOuter = Constants.bottomRow - 1;
  static int leftBound = 0;
  static int leftBoundInner = -1;
  static int rightBound = 9;
  static int rightBoundOuter = Constants.width;

  static int clockwise = 1;
  static int counterClockwise = -1;

  static int timerDelay = 500;

  static int fromLeft(int i) {
    return left + right * i;
  }

  static int fromRight(int i) {
    return rightBound + left * i;
  }

  static int fromBottom(int i) {
    return Constants.bottomRow + Constants.up * i;
  }

  static int fromTop(int i) {
    return Constants.topRow + Constants.down * i;
  }
}
