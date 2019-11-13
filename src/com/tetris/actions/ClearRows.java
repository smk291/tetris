package com.tetris.actions;

import com.tetris.recordkeeping.GameRecordKeeping;
import com.tetris.things.RowList;
import com.tetris.things.ActiveBlock;

import java.util.ArrayList;

/**
 *  `RowDeleter` deletes `Row`s. (Crazy, right?)
 *  Because row deletion happens only after one `RowList`'s squares/`Row`s are added to another, the inserted `RowList`'s
 *  upper and lower `y` values (that is, the highest and lowest `Row` in the inserted `RowList`) also represent the
 *  highest and lowest `y` values where a `Row` may be full.
 *
 *  The function finds those bounds, finds the full rows, and "lowers" all `Row`s that need to be lowered.
 *
 *  The `sinkingPieceAnchors` are explained in the notes on `SinkingPieceFinder`
 */
public abstract class ClearRows {
  public static ArrayList<Integer> apply(
      RowList squaresAdded, ActiveBlock block, RowList playField, GameRecordKeeping score) {
    playField.sortByY();
    squaresAdded.sortByY();

    // Works only if `squaresAdded` has been inserted into `playField` in same position as its
    // position in `squaresAdded`(?)
    // The `lowest` and `highest` values can only be rows in `squaresAdded`.
    int lowestFullRow = playField.lowestFullRowIndexAfterInsertion(squaresAdded);
    int highestFullRow = playField.highestFullRowIndexAfterInsertion(squaresAdded);

    ArrayList<Integer> sinkingPieceAnchors = new ArrayList<>();

    if (lowestFullRow == -1) {
      score.resetCombo();

      return sinkingPieceAnchors;
    }

    score.incrCombo();

    for (int i = lowestFullRow, total = 0;
        playField.size() > 0 && i < playField.size() && i <= highestFullRow;
        i++) {
      int contigDeleted = playField.rowIsFull(i) ? playField.clearFullRowsAndShiftNonFull(i, total) : 0;

      if (contigDeleted != 0 && i != 0) {
        sinkingPieceAnchors.add(i);
      }

      highestFullRow -= contigDeleted;
      total += contigDeleted;

      score.computeAndAdd(contigDeleted, block, playField);
      score.incrLinesCleared(contigDeleted);
    }

    if (playField.get().size() == 0) {
      sinkingPieceAnchors.clear();
    }

    return sinkingPieceAnchors;
  }
}
