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
 |□□□ ■     | 15     |■↓↓↓■↓↓↓↓↓| 15     |↓   ↓       | 15     |          | 15     |          | 15     |          | 15
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
  private int[][] searched;

  SinkingPieceFinder() {
    searched = new int[RowList.getHeight()][RowList.getWidth()];
  }

  private boolean getCellHasBeenTested(double x, double y) {
    return searched[(int) y][(int) x] == 1;
  }

  private void setCellHasBeenTested(double x, double y) {
    searched[(int) y][(int) x] = 1;
  }

  void find(double deletedRowIdx, RowList rowList, ArrayList<RowList> sinkingPieces) {
    if (PlacementTester.isOutOfBounds(0, deletedRowIdx)) return;

    double rowBelow = deletedRowIdx + 1;

    searched = new int[RowList.getHeight()][RowList.getWidth()];

    IntStream.range(0, RowList.getWidth())
        .forEach(
            x -> {
              runSearch(x, rowBelow, rowList, sinkingPieces);
              runSearch(x, deletedRowIdx, rowList, sinkingPieces);
            });
  }

  private void runSearch(double x, double y, RowList rowList, ArrayList<RowList> sinkingPieces) {
    piece = new RowList();
    connectedToLastRow = false;

    if (PlacementTester.inBounds(x, y))
      findConnectedBlocks(x, y, rowList, sinkingPieces);
  }

  private void findConnectedBlocks(double x, double y, RowList rowList, ArrayList<RowList> sinkingPieces) {
    if (rowList.getBlock(x, y).isPresent() && !getCellHasBeenTested(x, y)) {
      addConnectedBlocksToPiece(x, y, rowList);

      if (!connectedToLastRow) {
        sinkingPieces.add(piece);

        piece.clear();
      }
    }
  }

  private void addConnectedBlocksToPiece(double x, double y, RowList rowList) {
    if (getCellHasBeenTested(x, y)) return;

    Optional<Block> b = rowList.getBlock(x, y);

    if (b.isPresent()) {
      if ((int) y == RowList.getHeight() - 1) connectedToLastRow = true;

      setCellHasBeenTested(x, y);

      try {
        Block clonedBlock = b.get().clone();

        piece.addBlock(y, clonedBlock);
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }

      searchAdjacent(x, y + 1, rowList);
      searchAdjacent(x, y - 1, rowList);
      searchAdjacent(x + 1, y, rowList);
      searchAdjacent(x - 1, y, rowList);
    }
  }

  private void searchAdjacent(double x, double y, RowList rowList) {
    if (PlacementTester.inBounds(x, y ))
      addConnectedBlocksToPiece(x, y, rowList);
  }
}
