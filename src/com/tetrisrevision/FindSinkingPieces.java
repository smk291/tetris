package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

/****
 *
 * FindSinkingPieces contains the methods that, after a row is deleted,
 * look for sinking pieces. These are pieces that aren't tetrominos
 * and aren't attached to the lowest row. They're floating. Therefore
 * they sink.
 *
 * The logic is as follows:
 *
 * After deleting a row, I pass the index of the deleted row to resetVariablesAndRunSearch
 * After row deletion, there are only two possible locations, in terms of row index, of a floating/sinking piece:
 * 1. at the row index where a row was just deleted (startingRow)
 * 2. at the row index below (startingRow + 1).
 *
 * Ex:
 *

 1.                 2.                  3.

  ↓↓↓     row index  ↓↓↓
 |*↓↓       | 14    |↓↓↓       | 14     |          | 14
 |*** *     | 15    |*↓↓ *     | 15     |↓   ↓     | 15
 |   *******| 16    |**********| 16     |*   *     | 16
 | *** *****| 17    | *** *****| 17     | *** *****| 17
 |* * * * **| 18    |* * * * **| 18     |* * * * **| 18
 |********  | 19    |********  | 19     |********  | 19
 |* * * * **| 20    |* * * * **| 20     |* * * * **| 20
 |********  | 21    |********  | 21     |********  | 21
 |* * * * **| 22    |* * * * **| 22     |* * * * **| 22
 |* * * *   | 23    |* * * *   | 23     |* * * *   | 23
 ----------         ----------          ----------
 0123456789         0123456789          0123456789
 cell index

 4.                 5.                  6.
 |↓   ↓     | 15    |          | 15     |          | 15
 |↓   ↓     | 16    |          | 16     |          | 16
 |**********| 17    |        ↓↓| 17     |        ↓↓| 17
 |* * * * **| 18    |* * * * **| 18     |* * * * ↓↓| 18
 |********  | 19    |********  | 19     |**********| 19
 |* * * * **| 20    |* * * * **| 20     |* * * * **| 20
 |********  | 21    |********  | 21     |********  | 21
 |* * * * **| 22    |* * * * **| 22     |* * * * **| 22
 |* * * *   | 23    |* * * *   | 23     |* * * *   | 23
 ----------         ----------          ----------
 0123456789         0123456789          0123456789

 *
 * Steps 1-4 show how row deletion can result in floating pieces at the row index (r)
 * where the user just cleared a row: row 16 is deleted; the rows above it shift down
 * and cells 0 and 4 on row 15 drop to 16 and are no longer connected to line 23. Thus they sink.
 *
 * Steps 5-6 show how row deletion can result in floating pieces at r+1. Cells 8 and 9 on
 * row 18 are connected to row 17, 'hanging' from it. When row 17 is deleted, they're no longer
 * connected to any other pieces and so they sink.
 *
 * A piece is sinking if and only if it is a filled cell and is directly connected (via connections to
 * cells to left, right, top, and bottom) to the bottom row.
 *
 *
 *
 */


abstract class FindSinkingPieces {
    // This is the List of sinking pieces
    private static ArrayList<ArrayList<Point>> sinkingPieces;
    // This keeps track of points that have been searched and either added to the possible sinking piece or ignored
    private static int[][] searched = new int[PlayField.getHeight()][PlayField.getWidth()];
    // is cleared every time findSinkingPieceRuns; stores all filled cells connected to a given point
    private static ArrayList<Point> piece = new ArrayList<>();
    // if this is true, a piece isn't sinking
    private static boolean attachedToFloor = false;

    static void setStaticVariables(ArrayList<ArrayList<Point>> sinkingPieces) {
        FindSinkingPieces.sinkingPieces = sinkingPieces;
    }

    private static boolean pointHasBeenTested(Point pt) {
        return searched[(int) pt.getY()][(int) pt.getX()] == 1;
    }

    private static void recordPointHasBeenTested(Point pt) {
        searched[(int) pt.getY()][(int) pt.getX()] = 1;
    }

    static void resetVariablesAndRunSearch(int deletedRowIdx) {
        if (!Test.Position.isInBounds(0, deletedRowIdx))
            return;

        // Search all cells in both the deleted row and the row below. See comments above for explanation and example
        int rowBelow = deletedRowIdx + 1;

        // Reset variable
        // Reset once to prevent each call to runSearch from doing duplicate work
        // If a cell has been searched in a previous call to findConnectedFilledCells, there's no reason to test it again
        // because it's already in a sinking piece that has been stored or is known not to be part of a sinking piece
        searched = new int[PlayField.getHeight()][PlayField.getWidth()];

        IntStream.range(0, PlayField.getWidth()).forEach(x -> {
            runSearch(x, rowBelow);
            runSearch(x, deletedRowIdx);
        });
    }

    private static void runSearch(int x, int y) {
        // Reset variable
        // Reset for each call to findConnectedFill
        // Because each cell searched could be part of a new, separate, sinking piece
        piece = new ArrayList<>();
        // Reset variable
        // Reset for each call for same reasons
        attachedToFloor = false;
        // Find all filled cells connected to the point
        findConnectedFilledCells(new Point(x, y));
    }

    // Stating from particular cell, look for connected pieces
    private static void findConnectedFilledCells(Point pt) {
        // If point is in bounds, is full, and hasn't already been examined by this method
        if (Test.Position.isInBounds(pt) && PlayField.getCell(pt).isFull() && !pointHasBeenTested(pt)) {
            // Find and add all connected points
            addConnectedPointsToPiece(pt);

            // Add to list of sinking pieces if it isn't itself attached to bottom row or attached direcetly or indirectly to a cell that is
            if (!attachedToFloor)
                sinkingPieces.add(piece);
        }
    }

    // Recursive method looks to left, right, up, and down for connected, filled cells
    private static void addConnectedPointsToPiece(Point pt) {
        if (!Test.Position.isInBounds(pt) || pointHasBeenTested(pt))
            return;

        if (PlayField.getCell(pt).isFull()) {
            // If any connected is on row 23, then the cells connected to it aren't a sinking piece
            // I don't stop all calls to the recursive function here, because doing so creates problems:
            // If I stop the search, a row may be incompletely searched. If it's incompletely searched
            // The method won't re-search the cells that were already searched
            // And thus may be unable to see that it's connected to those already-searched cells
            // As a result, it may incorrectly identify cells as part of a sinking cell, because they aren't
            // Found to be connected to row 23
            if ((int) pt.getY() == 23)
                attachedToFloor = true;

            recordPointHasBeenTested(pt);

            piece.add(pt);

            // Look for cells connected in each direction
            searchAdjacent((Point) pt.clone(), 0, 1);
            searchAdjacent((Point) pt.clone(), 0, -1);
            searchAdjacent((Point) pt.clone(), 1, 0);
            searchAdjacent((Point) pt.clone(), -1, 0);
        }
    }

    private static void searchAdjacent(Point pt, int x, int y) {
        pt.translate(x, y);

        addConnectedPointsToPiece(pt);
    }
}