package com.tetrisrevision;

abstract class OverlapTester {
  static boolean noOverlap(double y, Block block, RowList field) {
    return !BoundsTester.xInBounds(block.getX()) || !BoundsTester.yInBounds(y) || field.isEmptyCell(block.getX(), y);
  }
}

abstract class BoundsTester {
  static boolean xInBounds(double x) {
    return x >= Constants.leftBound() && x < Constants.width();
  }

  static boolean yInBoundsNoMin(double y) {
    return y >= Constants.bottomRow();
  }

  static boolean yInBounds(double y) {
    return y >= Constants.bottomRow() && y < Constants.height();
  }
}

abstract class PlacementTester {
  static boolean cellsCanBeOccupied(TetrisPiece piece, RowList field) {
    for (Row r : piece.getBlocks().get()) {
      for (Block b : r.get()) {
        if (!cellCanBeOccupied(r.getY(), b, field)) {
          return false;
        }
      }
    }

    return true;
  }

  static boolean inBounds(double x, double y) {
    return BoundsTester.xInBounds(x) && BoundsTester.yInBounds(y);
  }

  static boolean isOutOfBounds(double x, double y) {
    return !inBounds(x, y);
  }

  static boolean isOutOfBounds(double y, Block block) {
    return !inBounds(block.getX(), y);
  }

  static boolean cellCanBeOccupied(double y, Block b, RowList f) {
    return BoundsTester.xInBounds(b.getX())
        && BoundsTester.yInBoundsNoMin(y)
        && OverlapTester.noOverlap(y, b, f);
  }
}
