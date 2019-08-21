package com.tetrisrevision;

abstract class RowDeleter {
  static double apply(RowList blocksAddedToBoard, TetrisPiece piece, RowList board, GameRecordKeeping recordKeeping, TetrisGUI gui) {
    double startAt = -1;

    for (Row r : blocksAddedToBoard) {
      if (board.isRowFull(r.getY()) && r.getY() > startAt)
        startAt = r.getY();
    }

    double rowIdxForFindingFloatingPieces = startAt;

    if (startAt == -1) {
      recordKeeping.setComboCount(0);
    } else {
      recordKeeping.incrementCombo();
      int rowShift = 0;
      int rowsDeleted;

      while ((startAt - rowShift) >= 0 && !board.isRowEmpty(startAt - rowShift)) {
        rowsDeleted = 0;

        while (board.isRowFull(startAt - rowShift)) {
          rowsDeleted++;
          rowShift++;
        }

        recordKeeping.computeScore(rowsDeleted, piece, board);
        recordKeeping.incrLinesCleared(rowsDeleted, gui);

        board.shiftRow(startAt - rowShift, startAt);

        startAt--;
      }

      for (Row r : board.subList((double) 0, startAt))
      {
        if (r.getY() <= startAt && r.getY() < startAt - rowShift)
        {
          board.remove(r);
        }
      }
    }

    return rowIdxForFindingFloatingPieces;
  }
}
