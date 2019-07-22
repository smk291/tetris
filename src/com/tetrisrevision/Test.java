package com.tetrisrevision;

import java.awt.*;
import java.util.Arrays;

abstract class OverlapTester {
  static boolean noOverlap(Point pt, PlayField field) {
    return !BoundsTester.xInBounds(pt) || !BoundsTester.yInBounds(pt) || field.cellIsEmpty(pt);
  }
}

abstract class OverlapTester2d {
  static boolean noOverlap(Cell cell, Blocks2d blocks) {
    return !BoundsTester2d.xInBounds(cell) || !BoundsTester2d.yInBounds(cell) || blocks.spaceIsEmpty(cell);
  }
}

abstract class BoundsTester {
  static boolean xInBounds(Point pt) {
    return pt.getX() > -1 && pt.getX() < PlayField.getWidth();
  }

  static boolean yInBoundsNoMin(Point pt) {
    return pt.getY() >= -2 && pt.getY() < PlayField.getHeight();
  }

  static boolean yInBounds(Point pt) {
    return pt.getY() >= 0 && pt.getY() < PlayField.getHeight();
  }
}

abstract class BoundsTester2d {
  static boolean xInBounds(Cell cell) {
    return cell.getX() >= 0 && cell.getX() < Blocks2d.getWidth();
  }

  static boolean yInBoundsNoMin(Cell cell) {
    return cell.getY() >= -2 && cell.getY() < Blocks2d.getHeight();
  }

  static boolean yInBounds(Point pt) {
    return pt.getY() >= 0 && pt.getY() < Blocks2d.getHeight();
  }
}

abstract class CellTester {
  static boolean emptyAndInBoundsAndNoOverlap(TetrisPiece piece, PlayField field) {
    return Arrays.stream(piece.getPoints()).allMatch(pt -> emptyAndInBoundsAndNoOverlap(pt, field));
  }

  static boolean emptyAndInBoundsAndNoOverlapNoMin(TetrisPiece piece, PlayField field) {
    return Arrays.stream(piece.getPoints()).allMatch(pt -> emptyAndInBoundsAndNoOverlapNoMin(pt, field));
  }

  static boolean emptyAndInBoundsAndNoOverlap(Point p, PlayField field) {
    return BoundsTester.xInBounds(p) && BoundsTester.yInBounds(p) && OverlapTester.noOverlap(p, field);
  }

  static boolean inBounds(Point p) {
    return BoundsTester.xInBounds(p) && BoundsTester.yInBounds(p);
  }

  static boolean inBounds(int x, int y) {
    Point pt = new Point(x, y);

    return inBounds(pt);
  }

  static boolean emptyAndInBoundsAndNoOverlapNoMin(Point p, PlayField field) {
    return BoundsTester.xInBounds(p) && BoundsTester.yInBoundsNoMin(p) && OverlapTester.noOverlap(p, field);
  }
}

abstract class CellTester2d {
  static boolean emptyAndInBoundsAndNoOverlap(TetrisPiece piece, Blocks2d blocks) {
    return Arrays.stream(piece.getCells()).allMatch(cell -> emptyAndInBoundsAndNoOverlap(cell, blocks));
  }

  static boolean emptyAndInBoundsAndNoOverlapNoMin(TetrisPiece piece, Blocks2d blocks) {
    return Arrays.stream(piece.getCells()).allMatch(cell -> emptyAndInBoundsAndNoOverlapNoMin(cell, blocks));
  }

  static boolean emptyAndInBoundsAndNoOverlap(Cell cell, Blocks2d blocks) {
    return BoundsTester2d.xInBounds(cell) && BoundsTester2d.yInBounds(cell) && OverlapTester2d.noOverlap(cell, blocks);
  }

  static boolean inBounds(Cell cell) {
    return BoundsTester2d.xInBounds(cell) && BoundsTester2d.yInBounds(cell);
  }

  static boolean inBounds(int x, int y) {
    Cell cell = new Cell(x, y);

    return inBounds(cell);
  }

  static boolean emptyAndInBoundsAndNoOverlapNoMin(Cell cell, Blocks2d blocks) {
    return BoundsTester2d.xInBounds(cell) && BoundsTester2d.yInBoundsNoMin(cell) && OverlapTester2d.noOverlap(cell, blocks);
  }
}
