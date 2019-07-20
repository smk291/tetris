package com.tetrisrevision;

import java.awt.*;
import java.util.Arrays;

abstract class Overlap {
  static boolean noOverlap(Point pt, PlayField field) {
    return !Bounds.xInBounds(pt) || !Bounds.yInBounds(pt) || field.cellIsEmpty(pt);
  }
}

abstract class Bounds {
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

abstract class Position {
  static boolean isInBoundsAndEmpty(TetrisPiece piece, PlayField field) {
    return Arrays.stream(piece.getPieceLocation()).allMatch(pt -> canBeFilled(pt, field));
  }

  static boolean isInBoundsAndEmptyNoRowMin(TetrisPiece piece, PlayField field) {
    return Arrays.stream(piece.getPieceLocation()).allMatch(pt -> pointIsValidNoMin(pt, field));
  }

  static boolean canBeFilled(Point p, PlayField field) {
    return Bounds.xInBounds(p) && Bounds.yInBounds(p) && Overlap.noOverlap(p, field);
  }

  static boolean isInBounds(Point p) {
    return Bounds.xInBounds(p) && Bounds.yInBounds(p);
  }

  static boolean isInBounds(int x, int y) {
    Point pt = new Point(x, y);

    return isInBounds(pt);
  }

  static boolean pointIsValidNoMin(Point p, PlayField field) {
    return Bounds.xInBounds(p) && Bounds.yInBoundsNoMin(p) && Overlap.noOverlap(p, field);
  }
}
