package com.tetrisrevision;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

/****
 *
 * SinkingPieceFinder contains the methods that, after a row is deleted,
 * look for sinking pieces. These are pieces that aren't tetrominos
 * and consist of blocks that aren't attached to a block on the lowest row.
 * Blocks that lack a connection to the lowest row are necessarily floating above it. Thus they sink.
 *
 * The logic is as follows:
 *
 * After deleting a row, I pass the index of the deleted row to SinkingPieceFinder
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
 ****/

class SinkingPieceFinder {
  private RowList piece = new RowList();
  private boolean connectedToLastRow = false;
  private int[][] skip;

  SinkingPieceFinder() {
    skip = new int[Constants.height()][Constants.width()];
  }

  private boolean skipCell(double x, double y) {
    return skip[(int) y][(int) x] == 1;
  }

  private void resetSkip() {
    skip = new int[Constants.height()][Constants.width()];
  }

  private void setSkip(double x, double y) {
    skip[(int) y][(int) x] = 1;
  }

  private void reset() {
    piece = new RowList();
    connectedToLastRow = false;
  }

  void find(double idx, RowList playField, ArrayList<RowList> sinkingPieces) {
    if (!BoundsTester.yInBoundsNoMin(idx))
      return;

    double rowBelow = idx + Constants.down();

    resetSkip();

    IntStream.range(0, Constants.width())
        .forEach(
            x -> {
              if (PlacementTester.inBounds(x, rowBelow) && !skipCell(x, rowBelow) && playField.getBlock(x, rowBelow).isPresent() ) {
                reset();

                addConnectedBlocksToPiece(x, rowBelow, playField);

                if (!connectedToLastRow) {
                  sinkingPieces.add(piece);
                }
              }

              if (PlacementTester.inBounds(x, idx) && !skipCell(x, idx) && playField.getBlock(x, idx).isPresent()) {
                reset();

                addConnectedBlocksToPiece(x, idx, playField);

                if (!connectedToLastRow) {
                  sinkingPieces.add(piece);
                }
              }
            });
  }

  private void addConnectedBlocksToPiece(double x, double y, RowList rowList) {
    Optional<Block> b = rowList.getBlock(x, y);

    if (b.isEmpty()){
      return;
    }

    setSkip(x, y);

    if (!connectedToLastRow && (int) y == Constants.bottomRow()) {
      connectedToLastRow = true;
    }

    try {
      piece.addBlock(y, b.get().clone());
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

    searchAdjacent(x, y + Constants.up(), rowList);
    searchAdjacent(x, y + Constants.down(), rowList);
    searchAdjacent(x + Constants.right(), y, rowList);
    searchAdjacent(x + Constants.left(), y, rowList);
  }

  private void searchAdjacent(double x, double y, RowList rowList) {
    if (PlacementTester.inBounds(x, y) && !skipCell(x, y)) {
      addConnectedBlocksToPiece(x, y, rowList);
    }
  }
}
