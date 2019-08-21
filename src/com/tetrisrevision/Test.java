package com.tetrisrevision;

abstract class OverlapTester {
  static boolean noOverlap(double y, Block block, RowList field) {
    return !BoundsTester.xInBounds(block.getX()) || !BoundsTester.yInBounds(y) || field.isCellEmpty(block.getX(), y);
  }

  static boolean noOverlap(Row r, Block b, RowList f) {
    return noOverlap(r.getY(), b, f);
  }
}

abstract class BoundsTester {
  static boolean xInBounds(double x) {
    return x >= 0 && x < RowList.getWidth();
  }

  static boolean yInBoundsNoMin(double y) {
    return y >= -2 && y < RowList.getHeight();
  }

  static boolean yInBoundsNoMin(Row r) {
    return yInBoundsNoMin(r.getY());
  }

  static boolean yInBounds(double y) {
    return y >= 0 && y < RowList.getHeight();
  }
}

abstract class PlacementTester {
  static boolean cellsCanBeOccupied(TetrisPiece piece, RowList field) {
    for (Row r : piece.getBlocks())
    {
      for (Block b : r)
      {
        if (!cellCanBeOccupied(r.getY(), b, field))
        {
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

  static boolean isOutOfBounds(Row r, Block b) {
    return isOutOfBounds(r.getY(), b);
  }

  static boolean cellCanBeOccupied(Row r, Block b, RowList f)
  {
    return cellCanBeOccupied(r.getY(), b, f);
  }

  static boolean cellCanBeOccupied(double y, Block b, RowList f) {
    return BoundsTester.xInBounds(b.getX())
        && BoundsTester.yInBoundsNoMin(y)
        && OverlapTester.noOverlap(y, b, f);
  }
}
