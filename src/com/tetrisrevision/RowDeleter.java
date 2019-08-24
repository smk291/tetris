package com.tetrisrevision;

import java.util.ArrayList;

abstract class RowDeleter {

  static ArrayList<Integer> apply(
      RowList blocksAdded,
      TetrisPiece piece,
      RowList playField,
      GameRecordKeeping score,
      TetrisGUI gui) {
    playField.sortByY();
    blocksAdded.sortByY();

    // Works only if `blocksAdded` has been inserted into `playField` in same position as its position in `blocksAdded`
    int lowestFullRow = playField.getLowestYIfShared(blocksAdded);
    int highestFullRow = playField.getHighestYIfShared(blocksAdded);

    ArrayList<Integer> sinkingPieceAnchors = new ArrayList<>();

    if (lowestFullRow == -1) {
      score.resetCombo();

      return sinkingPieceAnchors;
    }

    score.incrCombo();

    for (int i = lowestFullRow, total = 0; playField.size() > 0 && i < playField.size() && i <= highestFullRow; i++) {
      int contigDeleted = 0;

      if (playField.rowIsFull(i)) {
        contigDeleted = playField.deleteContiguousAndShift(i, total);
      }

      if (contigDeleted != 0 && i != 0) {
        sinkingPieceAnchors.add(i);
      }

      highestFullRow -= contigDeleted;
      total += contigDeleted;

      score.computeAndAdd(contigDeleted, piece, playField);
      score.incrLinesCleared(contigDeleted, gui);
    }

    if (playField.get().size() == 0) {
      sinkingPieceAnchors.clear();
    }

    return sinkingPieceAnchors;
  }
}
