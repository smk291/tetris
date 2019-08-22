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
  static int leftBound = 0;

  static int clockwise = 1;
  static int counterClockwise = -1;

  static int timerDelay = 500;

}
