package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

/****
 *
 * SinkingPieceDetector contains the methods that, after a row is deleted,
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

class SinkingPieceDetector {
  private ArrayList<Point> piece = new ArrayList<>();
  private boolean attachedToRow23 = false;
  private int[][] searched;

  SinkingPieceDetector() {
    searched = new int[PlayField.getHeight()][PlayField.getWidth()];
  }

  private boolean getCellHasBeenTested(Point pt) {
    return searched[(int) pt.getY()][(int) pt.getX()] == 1;
  }

  private void setCellHasBeenTested(Point pt) {
    searched[(int) pt.getY()][(int) pt.getX()] = 1;
  }

  /**
   * **
   *
   * <p>Search all cells in both the deleted row and the row below. See comments above for
   * explanation and example Reset variable Reset once to prevent each call to runSearch from doing
   * duplicate work If a cell has been searched in a previous call to findConnectedBlocks,
   * there's no reason to test it again because it's already in a sinking piece that has been stored
   * or is known not to be part of a sinking piece
   *
   * <p>**
   */
  void find(int deletedRowIdx, PlayField field, SinkingPieces sinkingPieces) {
    if (!CellTester.inBounds(0, deletedRowIdx)) return;

    int rowBelow = deletedRowIdx + 1;

    searched = new int[PlayField.getHeight()][PlayField.getWidth()];

    IntStream.range(0, PlayField.getWidth())
        .forEach(
            x -> {
              runSearch(x, rowBelow, field, sinkingPieces);
              runSearch(x, deletedRowIdx, field, sinkingPieces);
            });
  }

  /**
   * @param x x coord
   * @param y y coord
   * Reset variable Reset for each call to findConnectedFill Because each cell searched
   *     could be part of a new, separate, sinking piece Reset variable Reset for each call for same
   *     reasons Find all filled cells connected to the point
   *     <p>**
   */
  private void runSearch(int x, int y, PlayField field, SinkingPieces sinkingPieces) {
    piece = new ArrayList<>();
    attachedToRow23 = false;

    findConnectedBlocks(new Point(x, y), field, sinkingPieces);
  }

  /**
   * **
   *
   * <p>Starting from particular cell, look for connected pieces If point is in bounds, is full, and
   * hasn't already been examined by this method Find and add all connected points Add to list of
   * sinking pieces if it isn't itself attached to bottom row or attached direcetly or indirectly to
   * a cell that is
   *
   * <p>**
   */
  private void findConnectedBlocks(Point pt, PlayField field, SinkingPieces sinkingPieces) {
    if (CellTester.inBounds(pt) && field.getCell(pt).isFull() && !getCellHasBeenTested(pt)) {
      addConnectedBlocksToPiece(pt, field);

      if (!attachedToRow23) {
        sinkingPieces.getSinkingPieces().add(piece);
      }
    }
  }

  /**
   * ** Recursive method looks to left, right, up, and down for connected, filled cells If any
   * connected is on row 23, then the cells connected to it aren't a sinking piece I don't stop all
   * calls to the recursive function here, because doing so creates problems: If I stop the search,
   * a row may be incompletely searched. If it's incompletely searched The method won't re-search
   * the cells that were already searched And thus may be unable to see that it's connected to those
   * already-searched cells As a result, it may incorrectly identify cells as part of a sinking
   * cell, because they aren't Found to be connected to row 23 Look for cells connected in each
   * direction
   */
  private void addConnectedBlocksToPiece(Point pt, PlayField field) {
    if (!CellTester.inBounds(pt) || getCellHasBeenTested(pt)) return;

    if (field.getCell(pt).isFull()) {
      if ((int) pt.getY() == 23) attachedToRow23 = true;

      setCellHasBeenTested(pt);

      piece.add(pt);
//      Cell cell = cells.get((int) pt.getY()).get((int) pt.getX());

//      piece2d.add(cell);

      searchAdjacent((Point) pt.clone(), field, 0, 1);
      searchAdjacent((Point) pt.clone(), field, 0, -1);
      searchAdjacent((Point) pt.clone(), field, 1, 0);
      searchAdjacent((Point) pt.clone(), field, -1, 0);
    }
  }

  private void searchAdjacent(Point pt, PlayField field, int x, int y) {
    pt.translate(x, y);

    addConnectedBlocksToPiece(pt, field);
  }
}

class SinkingPieceDetector2d {
  private ArrayList<Cell> piece2d = new ArrayList<>();
  private boolean attachedToRow23 = false;
  private int[][] searched;

  SinkingPieceDetector2d() {
    searched = new int[Blocks2d.getHeight()][Blocks2d.getWidth()];
  }

  private boolean getCellHasBeenTested(Cell cell) {
    return searched[(int) cell.getY()][(int) cell.getX()] == 1;
  }

  private void setCellHasBeenTested(Cell cell) {
    searched[(int) cell.getY()][(int) cell.getX()] = 1;
  }

  /**
   * **
   *
   * <p>Search all cells in both the deleted row and the row below. See comments above for
   * explanation and example Reset variable Reset once to prevent each call to runSearch from doing
   * duplicate work If a cell has been searched in a previous call to findConnectedBlocks,
   * there's no reason to test it again because it's already in a sinking piece that has been stored
   * or is known not to be part of a sinking piece
   *
   * <p>**
   */
  void find(int deletedRowIdx, Blocks2d blocks2d, SinkingPieces2d sinkingPieces2d) {
    if (!CellTester2d.inBounds(0, deletedRowIdx)) return;

    int rowBelow = deletedRowIdx + 1;

    searched = new int[Blocks2d.getHeight()][Blocks2d.getWidth()];

    IntStream.range(0, Blocks2d.getWidth())
        .forEach(
            x -> {
              runSearch(x, rowBelow, blocks2d, sinkingPieces2d);
              runSearch(x, deletedRowIdx, blocks2d, sinkingPieces2d);
            });
  }

  /**
   * @param x x coord
   * @param y y coord
   * Reset variable Reset for each call to findConnectedFill Because each cell searched
   *     could be part of a new, separate, sinking piece Reset variable Reset for each call for same
   *     reasons Find all filled cells connected to the point
   *     <p>**
   */
  private void runSearch(int x, int y, Blocks2d blocks2d, SinkingPieces2d sinkingPieces2d) {
    piece2d = new ArrayList<>();
    attachedToRow23 = false;

    findConnectedBlocks(new Cell(x, y), blocks2d, sinkingPieces2d);
  }

  /**
   * **
   *
   * <p>Starting from particular cell, look for connected pieces If point is in bounds, is full, and
   * hasn't already been examined by this method Find and add all connected points Add to list of
   * sinking pieces if it isn't itself attached to bottom row or attached direcetly or indirectly to
   * a cell that is
   *
   * <p>**
   */
  private void findConnectedBlocks(Cell cell, Blocks2d blocks2d, SinkingPieces2d sinkingPieces2d) {
    if (CellTester2d.inBounds(cell) && blocks2d.getCell(cell).isFull() && !getCellHasBeenTested(cell)) {
      addConnectedBlocksToPiece(cell, blocks2d);

      if (!attachedToRow23) {
        sinkingPieces2d.getPieces().add(piece2d);
      }
    }
  }

  /**
   * ** Recursive method looks to left, right, up, and down for connected, filled cells If any
   * connected is on row 23, then the cells connected to it aren't a sinking piece I don't stop all
   * calls to the recursive function here, because doing so creates problems: If I stop the search,
   * a row may be incompletely searched. If it's incompletely searched The method won't re-search
   * the cells that were already searched And thus may be unable to see that it's connected to those
   * already-searched cells As a result, it may incorrectly identify cells as part of a sinking
   * cell, because they aren't Found to be connected to row 23 Look for cells connected in each
   * direction
   */
  private void addConnectedBlocksToPiece(Cell tmpCell, Blocks2d blocks2d) {
//    System.out.println(tmpCell);
//    System.out.println(tmpCell.getX() + ", " + tmpCell.getY());
//    System.out.println(CellTester2d.inBounds(tmpCell));

    if (!CellTester2d.inBounds(tmpCell) || getCellHasBeenTested(tmpCell))
      return;

    Cell cell = blocks2d.getCell(tmpCell);

    if (!CellTester2d.inBounds(cell) || getCellHasBeenTested(cell))
      return;

    if ((int) cell.getY() == 23)
      attachedToRow23 = true;

    setCellHasBeenTested(cell);

    piece2d.add(cell);

    searchAdjacent((Cell) cell.clone(), blocks2d, 0, 1);
    searchAdjacent((Cell) cell.clone(), blocks2d, 0, -1);
    searchAdjacent((Cell) cell.clone(), blocks2d, 1, 0);
    searchAdjacent((Cell) cell.clone(), blocks2d, -1, 0);
  }

  private void searchAdjacent(Cell cell, Blocks2d blocks2d, int x, int y) {
    cell.translate(x, y);

    addConnectedBlocksToPiece(cell, blocks2d);
  }
}
