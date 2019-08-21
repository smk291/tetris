package com.tetrisrevision;

abstract class RowDeleter {
  static double apply(RowList blocksAdded, TetrisPiece piece, RowList board, GameRecordKeeping recordKeeping, TetrisGUI gui) {
    int idx = board.lowestFullRow(blocksAdded);

    // No lines will be deleted
    if (idx == -1) {
      // Reset combo count
      recordKeeping.setComboCount(0);
    // Lines will be deleted
    } else {
      // Thus increment combo count
      recordKeeping.incrementCombo();

      int rowShift = 0; // `rowShift` is how far rows above a deleted row will shift downward. Each time row is deleted, rowShift increments
      int rowsDeleted; // `rowsDeleted` counts number of continguous rows deleted and can be smaller than `rowShift`

      for (int i = idx; i < board.size(); i++) {
        rowsDeleted = 0;

        while (board.isFullRow(i)) {
          board.remove(i);
          rowsDeleted++;
        }

        rowShift += rowsDeleted;

        recordKeeping.computeScore(rowsDeleted, piece, board);
        recordKeeping.incrLinesCleared(rowsDeleted, gui);

        Row r = board.get(i);

        if (r != null) {
          r.setY(r.getY() - rowShift);
        }
      }
    }

    return idx;
  }
}
