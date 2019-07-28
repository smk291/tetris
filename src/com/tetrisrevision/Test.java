package com.tetrisrevision;

import java.util.Arrays;

abstract class OverlapTester {
  static boolean noOverlap(Block pt, Blocks2d field) {
    return !BoundsTester.xInBounds(pt) || !BoundsTester.yInBounds(pt) || field.cellIsEmpty(pt);
  }
}

abstract class BoundsTester {
  static boolean xInBounds(double x) {
    return x >= 0 && x < Blocks2d.getWidth();
  }

  static boolean xInBounds(Block block) {
    return xInBounds((int) block.getX());
  }

  static boolean yInBoundsNoMin(Block block) {
    return block.getY() >= -2 && block.getY() < Blocks2d.getHeight();
  }

  static boolean yInBounds(double y) {
    return y >= 0 && y < Blocks2d.getHeight();
  }

  static boolean yInBounds(Block pt) {
    return yInBounds((int) pt.getY());
  }
}

abstract class PlacementTester {
  static boolean cellsCanBeOccupied(TetrisPiece piece, Blocks2d field) {
    return Arrays.stream(piece.getCells()).allMatch(pt -> cellCanBeOccupied(pt, field));
  }

  static boolean inBounds(double x, double y) {
    return BoundsTester.xInBounds(x) && BoundsTester.yInBounds(y);
  }

  static boolean inBounds(Block p) {
    return BoundsTester.xInBounds(p) && BoundsTester.yInBounds(p);
  }

  static boolean isOutOfBounds(double x, double y) {
    return !inBounds(x, y);
  }

  static boolean isOutOfBounds(Block c) {
    return !inBounds(c.getX(), c.getY());
  }

  static boolean cellCanBeOccupied(Block p, Blocks2d field) {
    return BoundsTester.xInBounds(p)
        && BoundsTester.yInBoundsNoMin(p)
        && OverlapTester.noOverlap(p, field);
  }
}
