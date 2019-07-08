package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
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
 * After deleting a row, I pass the index of the deleted row to findFloatingPieces
 * After row deletion, there are only two possible locations, in terms of row index, of a floating/sinking piece:
 * 1. at the row index where a row was just deleted (startingRow)
 * 2. at the row index below (startingRow + 1).
 *
 * Ex:
 *

 1.                 2.                  3.
 row index
 |*         | 14    |          | 14     |          | 14
 |*** *     | 15    |*   *     | 15     |↓   ↓     | 15
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
 * Steps 5-6 show how row deletion can result in floating pieces at r+1. Two cells on
 * row 18 were connected to row 17, hanging from it. When row 17 is deleted, they're no longer
 * connected to any other pieces and so they fall
 *
 *
 */


abstract class FindSinkingPieces {
    private static ArrayList<ArrayList<Point>> sinkingPieces;
    private static ArrayList<Point> alreadySearched;

    static void setStaticVariables(ArrayList<ArrayList<Point>> sinkingPieces) {
        FindSinkingPieces.sinkingPieces = sinkingPieces;
    }

    // If a cell is present in the ArrayList of sinking pieces, I count
    // it as 'already searched'
    private static boolean cellAlreadySearched(Point t) {
        return sinkingPieces.stream().anyMatch(c -> c.contains(t));
    }

    static void findFloatingPieces(int startingRow) {
        if (!Test.Position.isInBounds(0, startingRow)) {
            return;
        }

        int rowAbove = startingRow - 1;

        alreadySearched = new ArrayList<>();

        // Loop through 0 - 9 -- all possible cell indices -- and look for floating pieces
        IntStream.range(0, PlayField.getWidth()).forEach(x -> {
            runSearch(x, rowAbove);
            runSearch(x, startingRow);
        });
    }

    private static void runSearch(int x, int y) {
        resetTrackingVariables();
        lookForSinkingPiecesByRow(new Point(x, y));
    }

    private static void resetTrackingVariables() {
        SinkingPieceFinder.piece = new ArrayList<>();
    }

    private static void lookForSinkingPiecesByRow(Point pt) {
        // If cell isn't in another sinking piece and the cell isn't empty, continue
        if (!cellAlreadySearched(pt) && PlayField.getCell(pt).isFull()) {
            SinkingPieceFinder.store(pt);

            ArrayList<Point> newPiece = SinkingPieceFinder.piece.stream().map(c -> (Point) c.clone()).collect(Collectors.toCollection(ArrayList::new));

            if (SinkingPieceFinder.piece.size() > 0) {
                sinkingPieces.add(newPiece);
            }
        }
    }

    abstract static class SinkingPieceFinder {
        static ArrayList<Point> piece = new ArrayList<>();

        static void store(Point pt) {
            if (!Test.Position.isInBounds(pt) || alreadySearched.stream().anyMatch(pt::equals))
                return;

            if (PlayField.getCell(pt).isFull()) {
                alreadySearched.add(pt);

                SinkingPieceFinder.piece.add(pt);

                Point p1 = (Point) pt.clone();
                Point p2 = (Point) pt.clone();
                Point p3 = (Point) pt.clone();
                Point p4 = (Point) pt.clone();

                p1.translate(0, 1);
                p2.translate(0, -1);
                p3.translate(1, 0);
                p4.translate(-1, 0);

                store(p1);
                store(p2);
                store(p3);
                store(p4);
            }
        }
    }
}