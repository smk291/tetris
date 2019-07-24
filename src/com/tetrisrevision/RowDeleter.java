package com.tetrisrevision;

import java.util.ArrayList;

abstract class RowDeleter {
  static int apply(ArrayList<Cell> testRows, Blocks2d field) {
    return apply(testRows.toArray(new Cell[0]), field);
  }

  static int apply(TetrisPiece piece, Blocks2d field) {
    return apply(piece.getCells(), field);
  }

  private static int apply(Cell[] points, Blocks2d field) {
    int startAt = -1;

    for (Cell c : points) {
      int row = (int) c.getY();

      if (field.rowIsFull(row) && row > startAt) startAt = row;
    }

    int rowIdxForFindingFloatingPieces = startAt;

    if (startAt > -1) {
      int shift = 0;

      while (!field.rowIsEmpty(startAt) && (startAt + shift) >= 0) {
        while (field.rowIsFull(startAt + shift)) shift--;

        field.copyRow(startAt + shift, startAt);

        startAt--;
      }

      for (int i = startAt, halt = startAt + shift; i >= 0 && i > halt; i--) field.emptyRow(i);
    }

    return rowIdxForFindingFloatingPieces;
  }
}
