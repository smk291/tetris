package com.tetrisrevision.actions;

import com.tetrisrevision.helpers.Constants;

public abstract class BoundsTester {
  public static boolean xInBounds(double x) {
    return x >= Constants.leftBound && x < Constants.width;
  }

  public static boolean yInBoundsNoMin(double y) {
    return y >= Constants.bottomRow;
  }

  public static boolean yInBounds(double y) {
    return y >= Constants.bottomRow && y < Constants.height;
  }
}

