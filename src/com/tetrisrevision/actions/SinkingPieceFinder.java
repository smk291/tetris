package com.tetrisrevision.actions;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.RowList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

//formatter:off
/****
 *
 * SinkingPieceFinder contains the methods that, after a row is deleted,
 * look for sinking pieces. These are pieces that aren't tetrominos
 * and consist of blocks that aren't attached to a block on the lowest row.
 * Blocks that lack a connection to the lowest row are necessarily floating above it. Thus they sink.
 *
 * After row deletion, there are only two possible locations, in terms of row index, where the deletion could result in a floating/sinking piece:
 *   Either at the row index where a row was just deleted (startingRow)
 *   or at the row index below (startingRow + 1). See diagram below for illustration
 *
 * Ex:
 *
 1.                 2.                  3.                  4.                 5.                  6.
              row index
 ↓↓↓
 |■↓↓       | 14     |↓   ↓     | 14     |          | 14     |          | 15     |          | 15     |          | 15
 |□□□ ■     | 15     |■↓↓↓■↓↓↓↓↓| 15     |↓   ↓     | 15     |          | 15     |          | 15     |          | 15
 |   □□□□□□□| 16     |□□□□□□□□□□| 16     |■   ■     | 16     |↓↓↓↓↓↓↓↓↓↓| 16     |          | 16     |          | 16
 | □□□ □□□□□| 17     | □□□ □□□□□| 17     | □□□ □□□□□| 17     |■□□□■□□□□□| 17     |        ↓↓| 17     |          | 17
 |□ □ □ □ ■■| 18  →  |□ □ □ □ ■■| 18  →  |□ □ □ □ ■■| 18  →  |□ □ □ □ ■■| 18  →  |□ □ □ □ ■■| 18  →  |□ □ □ □   | 18
 |□□□□□□□□  | 19     |□□□□□□□□  | 19     |□□□□□□□□  | 19     |□□□□□□□□  | 19     |□□□□□□□□  | 19     |□□□□□□□□■■| 19
 |□ □ □ □ □□| 20     |□ □ □ □ □□| 20     |□ □ □ □ □□| 20     |□ □ □ □ □□| 20     |□ □ □ □ □□| 20     |□ □ □ □ □□| 20
 |□□□□□□□□  | 21     |□□□□□□□□  | 21     |□□□□□□□□  | 21     |□□□□□□□□  | 21     |□□□□□□□□  | 21     |□□□□□□□□  | 21
 |□ □ □ □ □□| 22     |□ □ □ □ □□| 22     |□ □ □ □ □□| 22     |□ □ □ □ □□| 22     |□ □ □ □ □□| 22     |□ □ □ □ □□| 22
 |□ □ □ □   | 23     |□ □ □ □   | 23     |□ □ □ □   | 23     |□ □ □ □   | 23     |□ □ □ □   | 23     |□ □ □ □   | 23
 ----------          ----------          ----------          ----------          ----------          ----------
 0123456789          0123456789          0123456789          0123456789          0123456789          0123456789
 cell index
 *
 * Steps 1-4 show how row deletion can result in floating pieces at the row index (r)
 * where the user just cleared a row: row 16 is deleted; the rows above it shift down
 * and cells 0 and 4 on row 15 drop to 16 and are no longer connected to line 23. Thus they sink.
 *
 * Steps 5-6 show how row deletion can result in floating pieces at r+1. Cells 8 and 9 on
 * row 18 are connected to row 17, 'hanging' from it. When row 17 is deleted, they're no longer
 * connected to any other cells and so they sink.
 *
 *
 ****/
//@formatter:on
public class SinkingPieceFinder {
  private final RowList tmpRowList = new RowList();
  private final int[][] skip = new int[Constants.height][Constants.width];
  private boolean willSink = true;

  public boolean doSkipCell(int x, int y) {
    if (skip[y][x] == 0) {
      skip[y][x] = 1;

      return false;
    }

    return true;
  }

  public void findSinkingPieces(int idx, RowList playField, ArrayList<RowList> sinkingPieces) {
    if (!BoundsTester.yInBoundsNoUpperLimit(idx)) {
      return;
    }

    runSearch(idx, sinkingPieces, playField);
    runSearch(idx + Constants.down, sinkingPieces, playField);
  }

  public void runSearch(int y, ArrayList<RowList> sinkingPieces, RowList playField) {
    IntStream.range(0, Constants.width)
        .forEach(
            x -> {
              tmpRowList.clear();
              willSink = true;

              getAdjacentBlocks(x, y, playField);

              if (!tmpRowList.get().isEmpty() && willSink) {
                playField.removeBlocks(tmpRowList);

                try {
                  sinkingPieces.add(tmpRowList.clone());
                } catch (CloneNotSupportedException e) {
                  e.printStackTrace();
                }
              }
            });
  }

  public void getAdjacentBlocks(int x, int y, @NotNull RowList rowList) {
    Optional<Block> b = rowList.getBlock(x, y);

    if (b.isEmpty() || doSkipCell(x, y)) {
      return;
    }

    if (willSink && y == Constants.bottomRow) {
      willSink = false;
    }

    tmpRowList.addBlock(y, new Block(b.get().getX(), b.get().getColor()));

    getAdjacentBlocks(x, y + Constants.up, rowList);
    getAdjacentBlocks(x, y + Constants.down, rowList);
    getAdjacentBlocks(x + Constants.right, y, rowList);
    getAdjacentBlocks(x + Constants.left, y, rowList);
  }
}
