package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

abstract class RowDeleter {
  static int apply(ArrayList<Point> testRows, PlayField field) {
    return apply(testRows.toArray(new Point[0]), field);
  }

  static int apply(Point[] points, PlayField field) {
    int startAt = -1;

    for (Point c : points) {
      int row = (int) c.getY();

      if (field.rowIsFull(row) && row > startAt) {
        startAt = row;
      }
    }

    int rowIdxForFindingFloatingPieces = startAt;

    if (startAt > -1) {
      int shift = 0;

      while (!field.rowIsEmpty(startAt) && (startAt + shift) >= 0) {
        while (field.rowIsFull(startAt + shift)) shift--;

        field.copyRow(startAt + shift, startAt);

        startAt--;
      }

      for (int i = startAt, halt = startAt + shift; i >= 0 && i > halt; i--) {
        field.emptyRow(i);
      }
    }

    return rowIdxForFindingFloatingPieces;
  }
}
