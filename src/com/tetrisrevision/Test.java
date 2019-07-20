package com.tetrisrevision;

import java.awt.*;
import java.util.Arrays;

class Test {
  Position position;

  Test(TetrisPiece falling, PlayField playField) {
    Bounds bounds = new Bounds();
    Overlap overlap = new Overlap(bounds, playField);
    this.position = new Position(falling, bounds, overlap);
  }
}

class Overlap {
  private Bounds bounds;
  private PlayField playField;

  Overlap(Bounds bounds, PlayField playField) {
    this.bounds = bounds;
    this.playField = playField;
  }

  boolean noOverlap(Point pt) {
    return !bounds.xInBounds(pt) || !bounds.yInBounds(pt) || playField.cellIsEmpty(pt);
  }
}

class Bounds {
  boolean xInBounds(Point pt) {
    return pt.getX() > -1 && pt.getX() < PlayField.getWidth();
  }

  boolean yInBoundsNoMin(Point pt) {
    return pt.getY() >= -2 && pt.getY() < PlayField.getHeight();
  }

  boolean yInBounds(Point pt) {
    return pt.getY() >= 0 && pt.getY() < PlayField.getHeight();
  }
}

class Position {
  private TetrisPiece falling;
  private Bounds bounds;
  private Overlap overlap;

  Position(TetrisPiece falling, Bounds bounds, Overlap overlap) {
    this.falling = falling;
    this.overlap = overlap;
    this.bounds = bounds;
  }

  boolean isInBoundsAndEmpty() {
    return Arrays.stream(falling.getPieceLocation()).allMatch(this::canBeFilled);
  }

  boolean isInBoundsAndEmptyNoRowMin() {
    return Arrays.stream(falling.getPieceLocation()).allMatch(this::pointIsValidNoMin);
  }

  boolean canBeFilled(Point p) {
    return bounds.xInBounds(p) && bounds.yInBounds(p) && overlap.noOverlap(p);
  }

  boolean isInBounds(Point p) {
    return bounds.xInBounds(p) && bounds.yInBounds(p);
  }

  boolean isInBounds(int x, int y) {
    Point pt = new Point(x, y);

    return isInBounds(pt);
  }

  boolean pointIsValidNoMin(Point p) {
    return bounds.xInBounds(p) && bounds.yInBoundsNoMin(p) && overlap.noOverlap(p);
  }
}
