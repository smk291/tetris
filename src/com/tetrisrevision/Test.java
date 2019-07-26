package com.tetrisrevision;

import java.util.Arrays;

abstract class OverlapTester {
  static boolean noOverlap(Cell pt, Blocks2d field) {
    return !BoundsTester.xInBounds(pt) || !BoundsTester.yInBounds(pt) || field.cellIsEmpty(pt);
  }
}

abstract class BoundsTester {
  static boolean xInBounds(Cell cell) {
    return cell.getX() >= 0 && cell.getX() < Blocks2d.getWidth();
  }

  static boolean yInBoundsNoMin(Cell cell) {
    return cell.getY() >= -2 && cell.getY() < Blocks2d.getHeight();
  }

  static boolean yInBounds(Cell pt) {
    return pt.getY() >= 0 && pt.getY() < Blocks2d.getHeight();
  }
}

abstract class PlacementTester {
  static boolean cellsCanBeOccupied(TetrisPiece piece, Blocks2d field) {
    return Arrays.stream(piece.getCells()).allMatch(pt -> cellCanBeOccupied(pt, field));
  }

  static boolean inBounds(Cell p) {
    return BoundsTester.xInBounds(p) && BoundsTester.yInBounds(p);
  }

  static boolean isOutOfBounds(int x, int y) {
    Cell pt = new Cell(x, y);

    return !inBounds(pt);
  }

  static boolean cellCanBeOccupied(Cell p, Blocks2d field) {
    return BoundsTester.xInBounds(p)
        && BoundsTester.yInBoundsNoMin(p)
        && OverlapTester.noOverlap(p, field);
  }
}
