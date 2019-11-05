package com.tetrisrevision.actions;

import com.tetrisrevision.recordkeeping.GameRecordKeeping;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;

import java.util.ArrayList;

public abstract class RowDeleter {

  public static ArrayList<Integer> apply(
      RowList blocksAdded, TetrisPiece piece, RowList playField, GameRecordKeeping score) {
    playField.sortByY();
    blocksAdded.sortByY();

    // Works only if `blocksAdded` has been inserted into `playField` in same position as its
    // position in `blocksAdded`(?)
    int lowestFullRow = playField.getLowestYIfShared(blocksAdded);
    int highestFullRow = playField.getHighestYIfShared(blocksAdded);

    ArrayList<Integer> sinkingPieceAnchors = new ArrayList<>();

    if (lowestFullRow == -1) {
      score.resetCombo();

      return sinkingPieceAnchors;
    }

    score.incrCombo();

    for (int i = lowestFullRow, total = 0;
        playField.size() > 0 && i < playField.size() && i <= highestFullRow;
        i++) {
      int contigDeleted = playField.rowIsFull(i) ? playField.deleteContiguousAndShift(i, total) : 0;

      if (contigDeleted != 0 && i != 0) {
        sinkingPieceAnchors.add(i);
      }

      highestFullRow -= contigDeleted;
      total += contigDeleted;

      score.computeAndAdd(contigDeleted, piece, playField);
      score.incrLinesCleared(contigDeleted);
    }

    if (playField.get().size() == 0) {
      sinkingPieceAnchors.clear();
    }

    return sinkingPieceAnchors;
  }
}
