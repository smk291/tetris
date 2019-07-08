//package com.tetrisrevision;
//
//import com.tetrisrevision.console.DrawBoard;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
///****
// *
// * FindSinkingPieces contains the methods that, after a row is deleted,
// * look for sinking pieces. These are pieces that aren't tetrominos
// * and aren't attached to the lowest row. They're floating. Therefore
// * they sink.
// *
// * The logic is as follows:
// *
// * After deleting a row, I pass the index of the deleted row to findFloatingPieces
// * After row deletion, there are only two possible locations, in terms of row index, of a floating/sinking piece:
// * 1. at the row index where a row was just deleted (startingRow)
// * 2. at the row index below (startingRow + 1).
// *
// * Ex:
// *
//
// 1.                 2.                  3.
// row index
//  ↓↓↓                ↓↓↓
// |*↓↓       | 14    |↓↓↓       | 14     |          | 14
// |*** *     | 15    |*↓↓ *     | 15     |↓   ↓     | 15
// |   *******| 16    |**********| 16     |*   *     | 16
// | *** *****| 17    | *** *****| 17     | *** *****| 17
// |* * * * **| 18    |* * * * **| 18     |* * * * **| 18
// |********  | 19    |********  | 19     |********  | 19
// |* * * * **| 20    |* * * * **| 20     |* * * * **| 20
// |********  | 21    |********  | 21     |********  | 21
// |* * * * **| 22    |* * * * **| 22     |* * * * **| 22
// |* * * *   | 23    |* * * *   | 23     |* * * *   | 23
// ----------         ----------          ----------
// 0123456789         0123456789          0123456789
// cell index
//
// 4.                 5.                  6.
// |↓   ↓     | 15    |          | 15     |          | 15
// |↓   ↓     | 16    |          | 16     |          | 16
// |**********| 17    |        ↓↓| 17     |        ↓↓| 17
// |* * * * **| 18    |* * * * **| 18     |* * * * ↓↓| 18
// |********  | 19    |********  | 19     |**********| 19
// |* * * * **| 20    |* * * * **| 20     |* * * * **| 20
// |********  | 21    |********  | 21     |********  | 21
// |* * * * **| 22    |* * * * **| 22     |* * * * **| 22
// |* * * *   | 23    |* * * *   | 23     |* * * *   | 23
// ----------         ----------          ----------
// 0123456789         0123456789          0123456789
// *
// * Steps 1-4 show how row deletion can result in floating pieces at the row index (r)
// * where the user just cleared a row: row 16 is deleted; the rows above it shift down
// * and cells 0 and 4 on row 15 drop to 16 and are no longer connected to line 23. Thus they sink.
// *
// * Steps 5-6 show how row deletion can result in floating pieces at r+1. Cells 8 and 9 on
// * row 18 are connected to row 17, 'hanging' from it. When row 17 is deleted, they're no longer
// * connected to any other pieces and so they sink.
// *
// *
// */
//
//
//abstract class FindSinkingPieces {
//    private static ArrayList<ArrayList<Point>> sinkingPieces;
//    private static int[][] alreadySearchedArr = new int[PlayField.getHeight()][PlayField.getWidth()];
//    private static ArrayList<Point> piece = new ArrayList<>();
//    private static boolean attachedToBottom = false;
//    private static int counter = 0;
//    private static int secondCounter = 0;
//
//    static void setStaticVariables(ArrayList<ArrayList<Point>> sinkingPieces) {
//        FindSinkingPieces.sinkingPieces = sinkingPieces;
//    }
//
//    // If a cell is present in the ArrayList of sinking pieces, I count
//    // it as 'already searched'
//
//    // private static boolean cellAlreadySearched(Point t) {
//    //     return sinkingPieces.stream().anyMatch(c -> c.contains(t));
//    // }
//
//    static void findFloatingPieces(int startingRow) {
//        if (!Test.Position.isInBounds(0, startingRow)) {
//            return;
//        }
//
//        int rowAbove = startingRow - 1;
//
//        alreadySearchedArr = new int[PlayField.getHeight()][PlayField.getWidth()];
//
//        // Loop through 0 - 9 -- all possible cell indices -- and look for floating pieces
//        IntStream.range(0, PlayField.getWidth()).forEach(x -> {
//            runSearch(x, rowAbove);
//            runSearch(x, startingRow);
//        });
//    }
//
//    private static void runSearch(int x, int y) {
//        piece = new ArrayList<>();
//        attachedToBottom = true;
//        // counter = 0;
//        // secondCounter = 0;
//        lookForSinkingPiecesByRow(new Point(x, y));
//    }
//
//    private static void lookForSinkingPiecesByRow(Point pt) {
//        // If cell isn't in another sinking piece and the cell isn't empty, continue
//        if (PlayField.getCell(pt).isFull()) {
//            store(pt);
//
//            // ArrayList<Point> newPiece = piece.stream().map(c -> (Point) c.clone()).collect(Collectors.toCollection(ArrayList::new));
//
//            if (!attachedToBottom && piece.size() > 0 && piece.stream().noneMatch(p -> p.getY() == 23)) {
//                sinkingPieces.add(piece);
//                // sinkingPieces.add(newPiece);
//            }
//        }
//    }
//
//    private static void store(Point pt) {
//        // counter++;
//
//        if (!Test.Position.isInBounds(pt) || !attachedToBottom)
//            return;
//
//        if (PlayField.getCell(pt).isFull()) {
//            // secondCounter++;
//
//            if (alreadySearchedArr[(int) pt.getY()][(int) pt.getX()] == 1) {
//                attachedToBottom = false;
//
//                return;
//            }
//
//            alreadySearchedArr[(int) pt.getY()][(int) pt.getX()] = 1;
//
//            piece.add(pt);
//
//            Point p1 = (Point) pt.clone();
//            Point p2 = (Point) pt.clone();
//            Point p3 = (Point) pt.clone();
//            Point p4 = (Point) pt.clone();
//
//            p1.translate(0, 1);
//            p2.translate(0, -1);
//            p3.translate(1, 0);
//            p4.translate(-1, 0);
//
//            store(p1);
//            store(p2);
//            store(p3);
//            store(p4);
//        }
//    }
//
//    void printOut(Point pt) {
//        System.out.println("Counter: " + counter);
//        System.out.println("Second counter: " + secondCounter);
//        System.out.println(attachedToBottom ? "NO" : "YES");
//        System.out.println("Sinking Pieces: " + sinkingPieces.size());
//        System.out.println("{ " + (int) pt.getX() + ", " + (int) pt.getY() + " }");
//        System.out.print("Piece lengths: ");
//
//        for (ArrayList<Point> piece : sinkingPieces) {
//            System.out.print(piece.size() + " ");
//        }
//
//        for (ArrayList<Point> sinkingPiece : sinkingPieces) {
//            for (Point point : sinkingPiece) {
//                System.out.print("{ " + (int) point.getX() + ", " + (int) point.getY() + " }");
//            }
//
//            System.out.println();
//        }
//    }
//}