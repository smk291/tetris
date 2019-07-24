package com.tetrisrevision;

import java.util.ArrayList;
import java.util.stream.IntStream;

/****
 *
 * SinkingPieceFinder contains the methods that, after a row is deleted,
 * look for sinking pieces. These are pieces that aren't tetrominos
 * and consist of blocks that aren't attached to the lowest row, meaning that
 * no adjacent block to the left, right, top or bottom is connected to row 23.
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
  private ArrayList<Cell> piece = new ArrayList<>();
  private boolean attachedToRow23 = false;
  private int[][] searched;

  SinkingPieceFinder() {
    searched = new int[Blocks2d.getHeight()][Blocks2d.getWidth()];
  }

  private boolean getCellHasBeenTested(Cell cell) {
    return searched[(int) cell.getY()][(int) cell.getX()] == 1;
  }

  private void setCellHasBeenTested(Cell cell) {
    searched[(int) cell.getY()][(int) cell.getX()] = 1;
  }

  void find(int deletedRowIdx, Blocks2d blocks2d, SinkingPieces sinkingPieces) {
    if (!CellTester.inBounds(0, deletedRowIdx))
      return;

    int rowBelow = deletedRowIdx + 1;

    searched = new int[Blocks2d.getHeight()][Blocks2d.getWidth()];

    IntStream.range(0, Blocks2d.getWidth())
        .forEach(
            x -> {
              runSearch(x, rowBelow, blocks2d, sinkingPieces);
              runSearch(x, deletedRowIdx, blocks2d, sinkingPieces);
            });
  }

  private void runSearch(int x, int y, Blocks2d blocks2d, SinkingPieces sinkingPieces) {
    piece = new ArrayList<>();
    attachedToRow23 = false;
    Cell tmpCell = new Cell(x, y);
    
    if (CellTester.inBounds(tmpCell)) {
      Cell cell = (Cell) blocks2d.getCell(tmpCell).clone();
      findConnectedBlocks(cell, blocks2d, sinkingPieces);
    }
  }

  private void findConnectedBlocks(Cell pt, Blocks2d field, SinkingPieces sinkingPieces) {
    if (field.getCell(pt).isFull() && !getCellHasBeenTested(pt)) {
      addConnectedBlocksToPiece(pt, field);

      if (!attachedToRow23) {
        sinkingPieces.getPieces().add(piece);

        piece.forEach(cell -> {
          field.getCell(cell).setEmpty(true);
        });
      }
    }
  }

  private void addConnectedBlocksToPiece(Cell pt, Blocks2d field) {
    if (getCellHasBeenTested(pt)) return;

    if (field.getCell(pt).isFull()) {
      if ((int) pt.getY() == 23) attachedToRow23 = true;

      setCellHasBeenTested(pt);

      piece.add(pt);

      int y = (int) pt.getY();
      int x = (int) pt.getX();

      if (CellTester.inBounds(x, y + 1))
        searchAdjacent((Cell) pt.clone(), field, 0, 1);

      if (CellTester.inBounds(x, y - 1))
        searchAdjacent((Cell) pt.clone(), field, 0, -1);

      if (CellTester.inBounds(x + 1, y))
        searchAdjacent((Cell) pt.clone(), field, 1, 0);

      if (CellTester.inBounds(x - 1, y))
        searchAdjacent((Cell) pt.clone(), field, -1, 0);
    }
  }

  private void searchAdjacent(Cell pt, Blocks2d blocks2d, int x, int y) {
    pt.translate(x, y);

    addConnectedBlocksToPiece(pt, blocks2d);
  }
}
