package com.tetrisrevision.helpers;

public abstract class Constants {
  public static int down = -1;
  public static int up = 1;
  public static int left = -1;
  public static int right = 1;

  public static int width = 10;
  public static int height = 20;

  public static int topRow = Constants.height - 1;
  public static int bottomRow = 0;
  public static int leftBound = 0;
  public static int rightBound = 9;

  public static int clockwise = 1;
  public static int counterClockwise = -1;

  public static int initialTimerDelay = 1000;
  public static int movementLockDelay = 500;
  public static int rotationLockDelay = 250;

  public static int fromLeft(int i) {
    return left + right * i;
  }
  public static int fromRight(int i) {
    return rightBound + left * i;
  }
  public static int fromBottom(int i) {
    return Constants.bottomRow + Constants.up * i;
  }
  public static int fromTop(int i) {
    return Constants.topRow + Constants.down * i;
  }
  public static void setWidth (int n) {
    width = n;
  }
}
