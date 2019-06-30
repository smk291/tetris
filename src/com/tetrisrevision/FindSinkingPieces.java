package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract class FindSinkingPieces {
    private static ArrayList<ArrayList<Point>> sinkingPieces;

    static void setStaticVariables(ArrayList<ArrayList<Point>> sinkingPieces) {
        FindSinkingPieces.sinkingPieces = sinkingPieces;
    }

    private static boolean cellAlreadySearched(Point t) {
        return sinkingPieces.stream().anyMatch(c -> c.contains(t));
    }

    static void findFloatingPieces(int startingRow) {
        if (!Test.Position.pointIsInBounds(0, startingRow)) {
            return;
        }

        SinkingPieceFinder.continueRecursing = true
        ;
        int rowAbove = startingRow - 1;

        IntStream.range(0, PlayField.getWidth()).forEach(x -> {
            SinkingPieceFinder.piece = new ArrayList<>();
            lookForSinkingPiecesByRow(new Point(x, rowAbove));
            lookForSinkingPiecesByRow(new Point(x, startingRow));
        });
    }

    private static void lookForSinkingPiecesByRow(Point pt) {
        if (!cellAlreadySearched(pt) && PlayField.getCell(pt).isFull()) {
            SinkingPieceFinder.store(pt);

            ArrayList<Point> newPiece = SinkingPieceFinder.piece.stream().map(c -> (Point) c.clone()).collect(Collectors.toCollection(ArrayList::new));

            if (SinkingPieceFinder.piece.size() > 0) {
                sinkingPieces.add(newPiece);
            }
        }
    }

    abstract static class SinkingPieceFinder {
        static boolean continueRecursing = true;
        static ArrayList<Point> piece = new ArrayList<>();

        static void store(Point pt) {
            if (!Test.Position.pointIsInBounds(pt))
                return;

            if (PlayField.getCell(pt).isFull()) {
                if (pt.getY() == 0) {
                    continueRecursing = false;

                    piece = new ArrayList<>();
                } else if (
                        continueRecursing &&
                                Test.Position.pointIsInBounds(pt) &&
                                piece.stream().noneMatch(pt::equals)
                ) {
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
}
