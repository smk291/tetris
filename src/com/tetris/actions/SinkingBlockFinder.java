package com.tetris.actions;

import com.tetris.constants.Constants;
import com.tetris.things.Square;
import com.tetris.things.RowList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

//formatter:off
/****
 *
 * SinkingPieceFinder contains the methods that, after a row is deleted, look for floating/sinking blocks. These are
 * blocks that aren't tetrominos and consist of squares that aren't attached to a square on the lowest row (i.e.
 * settled). Squares that aren't connected to the lowest row are necessarily floating above it. Thus they sink.
 *
 * After row deletion, there are only two possible locations, in terms of row index, where the deletion could result in a floating/sinking block:
 *   Either at the row index where a row was just deleted (startingRow)
 *   or at the row index below (startingRow + 1). See diagram below for illustration
 *
 * Ex:
 *
 1.                 2.                  3.                  4.                 5.                  6.
              y values
 .↓↓↓         ↓
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
 .----------          ----------          ----------          ----------          ----------          ----------
 .0123456789          0123456789          0123456789          0123456789          0123456789          0123456789     ← x values

 *
 * Steps 1-4 show how row deletion can result in floating blocks at the row index (r)
 * where the user just cleared a row: row 16 is deleted; the rows above it shift down, and
 * the squares in cells 0 and 4 on row 15 drop to 16. They are no longer connected to line 23.
 * Thus they sink.
 *
 * Steps 5-6 show how row deletion can result in floating blocks at r+1. Cells 8 and 9 on
 * row 18 are connected to row 17, 'hanging' from it. When row 17 is deleted, they're no longer
 * connected to any other cells and so they sink.
 *
 *
 ****/
//@formatter:on
public class SinkingBlockFinder {
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
    if (!BoundsTester.yInLowerBound(idx)) {
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

              getAdjacentSquares(x, y, playField);

              if (!tmpRowList.get().isEmpty() && willSink) {
                playField.removeSquares(tmpRowList);

                try {
                  sinkingPieces.add(tmpRowList.clone());
                } catch (CloneNotSupportedException e) {
                  e.printStackTrace();
                }
              }
            });
  }

  public void getAdjacentSquares(int x, int y, @NotNull RowList rowList) {
    Optional<Square> b = rowList.getSquare(x, y);

    if (b.isEmpty() || doSkipCell(x, y)) {
      return;
    }

    if (willSink && y == Constants.bottomRow) {
      willSink = false;
    }

    tmpRowList.addSquare(y, new Square(b.get().getX(), b.get().getColor()));

    getAdjacentSquares(x, y + Constants.up, rowList);
    getAdjacentSquares(x, y + Constants.down, rowList);
    getAdjacentSquares(x + Constants.right, y, rowList);
    getAdjacentSquares(x + Constants.left, y, rowList);
  }
}
