package com.tetrisrevision.actions;

import com.tetrisrevision.helpers.Constants;

public abstract class BoundsTester {
  public static boolean xInBounds(int x) {
    return x >= Constants.leftBound && x < Constants.width;
  }

  public static boolean yInBoundsNoUpperLimit(int y) {
    return y >= Constants.bottomRow;
  }

  public static boolean yInBounds(int y) {
    return y >= Constants.bottomRow && y < Constants.height;
  }
}
