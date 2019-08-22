package com.tetrisrevision;

public abstract class Constants {
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

  static int topRow() {
    return Constants.height() - 1;
  }

  static int bottomRow() {
    return 0;
  }

  static int clockwise() {
    return 1;
  }

  static int counterClockwise() {
    return -1;
  }

  static int timerDelay() {
    return 500;
  }

  static int leftBound() {
    return 0;
  }
}
