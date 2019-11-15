package com.tetris.game.actions;

import com.tetris.game.recordkeeping.GameRecordKeeping;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;

import java.util.*;

/**
 *  `RowDeleter` deletes `Row`s from the playfield. (Crazy, right?)
 *  Because row deletion happens only after one `RowList`'s squares/`Row`s are added to another, the inserted `RowList`'s
 *  upper and lower `y` values (that is, the highest and lowest `Row` in the inserted `RowList`) also represent the
 *  highest and lowest `y` values where a `Row` may be full.
 *
 *  The function finds those bounds, finds the full rows, and "lowers" all `Row`s that need to be lowered.
 *
 *  The `sinkingBlockAnchors` are explained in the notes on `SinkingBlockFinder`
 */
public abstract class RowDeleter {
  public static ArrayList<Integer> apply(
      RowList squaresAdded, ActiveBlock block, RowList playfield, GameRecordKeeping score) {
    playfield.sortByY();
    squaresAdded.sortByY();

    // Works only if `squaresAdded` has been inserted into `playfield` in same position as its
    // position in `squaresAdded`(?)
    // The `lowest` and `highest` values can only be rows in `squaresAdded`.
    int lowestFullRow = playfield.lowestFullSharedRow(squaresAdded);
    int highestFullRow = playfield.highestFullSharedRow(squaresAdded);

    ArrayList<Integer> sinkingBlockAnchors = new ArrayList<>();

    if (lowestFullRow == -1) {
      score.resetCombo();

      return sinkingBlockAnchors;
    }

    score.incrCombo();

    for (int i = lowestFullRow, total = 0;
        playfield.size() > 0 && i < playfield.size() && i <= highestFullRow;
        i++) {
      int contigDeleted = playfield.rowIsFullByIdx(i) ? playfield.clearFullRowsAndShiftNonFull(i, total) : 0;

      if (contigDeleted != 0 && i != 0) {
        sinkingBlockAnchors.add(i);
      }

      highestFullRow -= contigDeleted;
      total += contigDeleted;

      score.computeAndAdd(contigDeleted, block, playfield);
      score.incrLinesCleared(contigDeleted);
    }

    if (playfield.get().size() == 0) {
      sinkingBlockAnchors.clear();
    }

    return sinkingBlockAnchors;
  }
}

