package com.tetrisrevision.actions;

import com.tetrisrevision.constants.Constants;

/*
 * These methods just test whether a given x or y value is within bounds.
 */
public abstract class BoundsTester {
  /**
   *
    * @param x
   * @return
   */
  public static boolean xInBounds(int x) {
    return x >= Constants.leftBound && x < Constants.width;
  }

  public static boolean yInLowerBound(int y) {
    return y >= Constants.bottomRow;
  }

  public static boolean yInBounds(int y) {
    return y >= Constants.bottomRow && y < Constants.height;
  }
}
