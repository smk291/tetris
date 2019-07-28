package com.tetrisrevision;

import java.util.ArrayList;
import java.util.stream.IntStream;

/****
 *
 * SinkingPieceFinder contains the methods that, after a row is deleted,
 * look for sinking pieces. These are pieces that aren't tetrominos
 * and consist of blocks that aren't attached to the lowest row, meaning that
 * no adjacent block to the left, right, top or bottom is connected to last row.
 * Blocks that lack a connection to row 23 are necessarily floating above it. Thus they sink.
 *
 * The logic is as follows:
 *
 * After deleting a row, I pass the index of the deleted row to find
 * After row deletion, there are only two possible locations, in terms of row index, where the deletion could result in a floating/sinking piece:
 *   Either at the row index where a row was just deleted (startingRow)
 *   or at the row index below (startingRow + 1).
 *
 * Ex:
 *
 1.                 2.                  3.                  4.                 5.                  6.
           row index
 |■         | 14     |↓   ↓     | 14     |          | 14     |          | 15     |          | 15     |          | 15
 |■■■ ■     | 15     |■↓↓↓■↓↓↓↓↓| 15     |          | 15     |          | 15     |          | 15     |          | 15
 |↓↓↓□□□□□□□| 16     |■■■□□□□□□□| 16     |■   ■     | 16     |↓↓↓↓↓↓↓↓↓↓| 16     |          | 16     |          | 16
 | □□□ □□□□□| 17     | □□□ □□□□□| 17     |↓□□□↓□□□□□| 17     |■□□□■□□□□□| 17     |          | 17     |          | 17
 |□ □ □ □ ■■| 18  →  |□ □ □ □ ■■| 18  →  |□ □ □ □ ■■| 18  →  |□ □ □ □ ■■| 18  →  |□ □ □ □ ■■| 18  →  |□ □ □ □   | 18
 |□□□□□□□□  | 19     |□□□□□□□□  | 19     |□□□□□□□□  | 19     |□□□□□□□□  | 19     |□□□□□□□□↓↓| 19     |□□□□□□□□■■| 19
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
 * A piece is sinking if and only if it is a filled cell and is directly connected (via connections to
 * cells to left, right, top, and bottom) to the bottom row.
 *
 *
 *
 * sinkingPieces - is the List of sinking pieces
 * searched - keeps track of points that have been searched and either added to the possible sinking piece or ignored
 * piece - is cleared every time findSinkingPieceRuns; it stores all filled cells connected to a given point
 * attachedToRow23 - if this is true, a piece isn't sinking
 *
 ****/
class SinkingPieceFinder {
  private ArrayList<Block> piece = new ArrayList<>();
  private boolean connectedToLastRow = false;
  private int[][] searched;

  SinkingPieceFinder() {
    searched = new int[Blocks2d.getHeight()][Blocks2d.getWidth()];
  }

  private boolean getCellHasBeenTested(double x, double y) {
    return searched[(int) y][(int) x] == 1;
  }

  private void setCellHasBeenTested(double x, double y) {
    searched[(int) y][(int) x] = 1;
  }

  void find(int deletedRowIdx, Blocks2d blocks2d, SinkingPieces sinkingPieces) {
    if (PlacementTester.isOutOfBounds(0, deletedRowIdx)) return;

    int rowBelow = deletedRowIdx + 1;

    searched = new int[Blocks2d.getHeight()][Blocks2d.getWidth()];

    IntStream.range(0, Blocks2d.getWidth())
        .forEach(
            x -> {
              runSearch(x, rowBelow, blocks2d, sinkingPieces);
              runSearch(x, deletedRowIdx, blocks2d, sinkingPieces);
            });
  }

  private void runSearch(double x, double y, Blocks2d blocks2d, SinkingPieces sinkingPieces) {
    piece = new ArrayList<>();
    connectedToLastRow = false;

    if (PlacementTester.inBounds(x, y)) {
      findConnectedBlocks(x, y, blocks2d, sinkingPieces);
    }
  }

  private void findConnectedBlocks(double x, double y, Blocks2d blocks2d, SinkingPieces sinkingPieces) {
    if (blocks2d.getCell(x, y).isPresent() && !getCellHasBeenTested(x, y)) {
      addConnectedBlocksToPiece(x, y, blocks2d);

      if (!connectedToLastRow) {
        sinkingPieces.getPieces().add(piece);

        piece.forEach(blocks2d::removeCell);
      }
    }
  }

  private void addConnectedBlocksToPiece(double x, double y, Blocks2d blocks2d) {
    if (getCellHasBeenTested(x, y)) return;

    if (blocks2d.getCell(x, y).isPresent()) {
      if ((int) y == Blocks2d.getHeight() - 1) connectedToLastRow = true;

      setCellHasBeenTested(x, y);

      piece.add((Block) blocks2d.getCell(x, y).get().clone());

      searchAdjacent(x, y + 1, blocks2d);
      searchAdjacent(x, y - 1, blocks2d);
      searchAdjacent(x + 1, y, blocks2d);
      searchAdjacent(x - 1, y, blocks2d);
    }
  }

  private void searchAdjacent(double x, double y, Blocks2d blocks2d) {
    if (!PlacementTester.isOutOfBounds(x, y )) addConnectedBlocksToPiece(x, y, blocks2d);
  }
}
