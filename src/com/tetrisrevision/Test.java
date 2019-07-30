package com.tetrisrevision;

import java.util.Arrays;

abstract class OverlapTester {
  static boolean noOverlap(Block block, PlayField field) {
    return !BoundsTester.xInBounds(block.getX()) || !BoundsTester.yInBounds(block.getY()) || field.isEmpty(block.getX(), block.getY());
  }
}

abstract class BoundsTester {
  static boolean xInBounds(double x) {
    return x >= 0 && x < PlayField.getWidth();
  }

  static boolean yInBoundsNoMin(Block block) {
    return block.getY() >= -2 && block.getY() < PlayField.getHeight();
  }

  static boolean yInBounds(double y) {
    return y >= 0 && y < PlayField.getHeight();
  }
}

abstract class PlacementTester {
  static boolean cellsCanBeOccupied(TetrisPiece piece, PlayField field) {
    return Arrays.stream(piece.getCells()).allMatch(pt -> cellCanBeOccupied(pt, field));
  }

  static boolean inBounds(double x, double y) {
    return BoundsTester.xInBounds(x) && BoundsTester.yInBounds(y);
  }

  static boolean isOutOfBounds(double x, double y) {
    return !inBounds(x, y);
  }

  static boolean isOutOfBounds(Block block) {
    return !inBounds(block.getX(), block.getY());
  }

  static boolean cellCanBeOccupied(Block block, PlayField field) {
    return BoundsTester.xInBounds(block.getX())
        && BoundsTester.yInBoundsNoMin(block)
        && OverlapTester.noOverlap(block, field);
  }
}
