package com.tetrisrevision;

import java.util.ArrayList;

abstract class RowDeleter {
  static int apply(
      ArrayList<Block> testRows,
      TetrisPiece piece,
      PlayField field,
      GameRecordKeeping recordKeeping,
      TetrisGUI gui) {
    return apply(testRows.toArray(new Block[0]), piece, field, recordKeeping, gui);
  }

  static int apply(
          TetrisPiece piece, PlayField field, GameRecordKeeping recordKeeping, TetrisGUI gui) {
    return apply(piece.getCells(), piece, field, recordKeeping, gui);
  }

  private static int apply(
      Block[] blocks,
      TetrisPiece piece,
      PlayField playField,
      GameRecordKeeping recordKeeping,
      TetrisGUI gui) {
    int startAt = -1;

    for (Block c : blocks) {
      int row = (int) c.getY();

      if (playField.isFull(row) && row > startAt) startAt = row;
    }

    int rowIdxForFindingFloatingPieces = startAt;

    if (startAt == -1) {
      recordKeeping.setComboCount(0);
    } else {
      recordKeeping.incrementCombo();
      int rowShift = 0;
      int rowsDeleted;

      while ((startAt - rowShift) >= 0 && !playField.isEmpty(startAt - rowShift)) {
        rowsDeleted = 0;

        while (playField.isFull(startAt - rowShift)) {
          rowsDeleted++;
          rowShift++;
        }

        recordKeeping.computeScore(rowsDeleted, piece, playField);
        recordKeeping.incrLinesCleared(rowsDeleted, gui);

        playField.copy(startAt - rowShift, startAt);

        startAt--;
      }

      for (int i = startAt, halt = startAt - rowShift; i >= 0 && i > halt; i--) {
        playField.empty(i);
      }
    }

    return rowIdxForFindingFloatingPieces;
  }
}
