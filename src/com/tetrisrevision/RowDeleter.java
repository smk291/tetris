package com.tetrisrevision;

import java.util.ArrayList;

abstract class RowDeleter {
  static int apply(
      ArrayList<Block> testRows,
      TetrisPiece piece,
      Blocks2d field,
      GameRecordKeeping recordKeeping,
      TetrisGUI gui) {
    return apply(testRows.toArray(new Block[0]), piece, field, recordKeeping, gui);
  }

  static int apply(
      TetrisPiece piece, Blocks2d field, GameRecordKeeping recordKeeping, TetrisGUI gui) {
    return apply(piece.getCells(), piece, field, recordKeeping, gui);
  }

  private static int apply(
      Block[] blocks,
      TetrisPiece piece,
      Blocks2d blocks2d,
      GameRecordKeeping recordKeeping,
      TetrisGUI gui) {
    int startAt = -1;

    for (Block c : blocks) {
      int row = (int) c.getY();

      if (blocks2d.rowIsFull(row) && row > startAt) startAt = row;
    }

    int rowIdxForFindingFloatingPieces = startAt;

    if (startAt == -1) {
      recordKeeping.setComboCount(0);
    } else {
      recordKeeping.incrementCombo();
      int rowShift = 0;
      int rowsDeleted;

      while ((startAt - rowShift) >= 0 && !blocks2d.rowIsEmpty(startAt - rowShift)) {
        rowsDeleted = 0;

        while (blocks2d.rowIsFull(startAt - rowShift)) {
          rowsDeleted++;
          rowShift++;
        }

        recordKeeping.scoreDeletion(rowsDeleted, piece, blocks2d);
        recordKeeping.incrLinesCleared(rowsDeleted, gui);

        blocks2d.copyRow(startAt - rowShift, startAt);
//        blocks2d.emptyRow(startAt - rowShift);
        startAt--;
      }

      for (int i = startAt, halt = startAt - rowShift; i >= 0 && i > halt; i--) {
        blocks2d.emptyRow(i);
      }
    }

    return rowIdxForFindingFloatingPieces;
  }
}
