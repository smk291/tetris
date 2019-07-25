package com.tetrisrevision;

import java.util.ArrayList;

abstract class RowDeleter {
  static int apply(ArrayList<Cell> testRows, TetrisPiece piece, Blocks2d field, GameRecordKeeping recordKeeping, TetrisGUI gui) {
    return apply(testRows.toArray(new Cell[0]), piece, field, recordKeeping, gui);
  }

  static int apply(TetrisPiece piece, Blocks2d field, GameRecordKeeping recordKeeping, TetrisGUI gui) {
    return apply(piece.getCells(), piece, field, recordKeeping, gui);
  }

  private static int apply(Cell[] cells, TetrisPiece piece, Blocks2d blocks2d, GameRecordKeeping recordKeeping, TetrisGUI gui) {
    int startAt = -1;

    for (Cell c : cells) {
      int row = (int) c.getY();

      if (blocks2d.rowIsFull(row) && row > startAt) startAt = row;
    }

    int rowIdxForFindingFloatingPieces = startAt;

    if (startAt > -1) {
      int shift = 0;

      while (!blocks2d.rowIsEmpty(startAt) && (startAt - shift) >= 0) {
        while (blocks2d.rowIsFull(startAt - shift)) shift++;

        blocks2d.copyRow(startAt - shift, startAt);

        recordKeeping.scoreDeletion(shift, piece, blocks2d);
        recordKeeping.incrLinesCleared(shift, gui);

        startAt--;
      }

      for (int i = startAt, halt = startAt - shift; i >= 0 && i > halt; i--) blocks2d.emptyRow(i);
    }

    return rowIdxForFindingFloatingPieces;
  }
}
