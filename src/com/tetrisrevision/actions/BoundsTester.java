package com.tetrisrevision.actions;

import com.tetrisrevision.helpers.Constants;

abstract class BoundsTester {
  static boolean xInBounds(int x) {
    return x >= Constants.leftBound && x < Constants.width;
  }

  static boolean yInBoundsNoMin(int y) {
    return y >= Constants.bottomRow;
  }

  static boolean yInBounds(int y) {
    return y >= Constants.bottomRow && y < Constants.height;
  }
}
