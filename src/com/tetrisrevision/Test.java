package com.tetrisrevision;

abstract class BoundsTester {
  static boolean xInBounds(double x) {
    return x >= Constants.leftBound && x < Constants.width;
  }

  static boolean yInBoundsNoMin(double y) {
    return y >= Constants.bottomRow;
  }

  static boolean yInBounds(double y) {
    return y >= Constants.bottomRow && y < Constants.height;
  }
}

abstract class PlacementTester {
  static boolean cellsCanBeOccupied(TetrisPiece piece, RowList field) {
    for (Row r : piece.getBlocks().get()) {
      for (Block b : r.get()) {
        if (!cellCanBeOccupied(r.getY(), b.getX(), field)) {
          return false;
        }
      }
    }

    return true;
  }

  static boolean inBounds(double x, double y) {
    return BoundsTester.xInBounds(x) && BoundsTester.yInBounds(y);
  }

  static boolean cellCanBeOccupied(double y, double x, RowList f) {
    return BoundsTester.xInBounds(x)
        && BoundsTester.yInBoundsNoMin(y)
        && f.cellIsEmpty(x, y);
  }
}
