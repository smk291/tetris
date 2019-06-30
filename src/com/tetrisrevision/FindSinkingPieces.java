package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

abstract class FindSinkingPieces {
    private static ArrayList<ArrayList<Point>> sinkingPieces;

    static void setStaticVariables(ArrayList<ArrayList<Point>> sinkingPieces) {
        FindSinkingPieces.sinkingPieces = sinkingPieces;
    }

    private static boolean cellAlreadySearched(Point t) {
        for (ArrayList<Point> c : sinkingPieces) {
            if (c.contains(t))
                return true;
        }

        return false;
    }

    static void findFloatingPieces(int startingRow) {
        SinkingPieceFinder.continueRecursing = true;

        if (!Test.Position.pointIsInBounds(new Point(0, startingRow))) {
            return;
        }

        int rowAbove = startingRow - 1;

        for (int x = 0; x < PlayField.getWidth(); x++) {
            SinkingPieceFinder.piece = new ArrayList<>();

            lookForSinkingPiecesByRow(x, rowAbove);
            lookForSinkingPiecesByRow(x, startingRow);
        }
    }

    private static void lookForSinkingPiecesByRow(int x, int y) {
        Cell[][] cells = PlayField.getCells();
        Point currentPoint = new Point(x, y);

        if (!cellAlreadySearched(currentPoint) && cells[y][x].isFull()) {
            SinkingPieceFinder.store(currentPoint);

            ArrayList<Point> newPiece = new ArrayList<>();

            for (Point c : SinkingPieceFinder.piece) {
                newPiece.add((Point) c.clone());
            }

            if (SinkingPieceFinder.piece.size() > 0) {
                sinkingPieces.add(newPiece);
            }
        }
    }

    abstract static class SinkingPieceFinder {
        static boolean continueRecursing = true;
        static ArrayList<Point> piece = new ArrayList<>();

        static void store(Point currentPoint) {
            if (!Test.Position.pointIsInBounds(currentPoint))
                return;

            Cell[][] cells = PlayField.getCells();

            Cell currentCell = cells[(int) currentPoint.getY()][(int) currentPoint.getX()];

            if (currentCell.isFull()) {
                if (currentPoint.getY() == 0) {
                    continueRecursing = false;

                    piece = new ArrayList<>();
                } else if (
                        continueRecursing &&
                                Test.Position.pointIsInBounds(currentPoint) &&
                                piece.stream().noneMatch(currentPoint::equals)
                ) {
                    SinkingPieceFinder.piece.add(currentPoint);

                    Point p1 = (Point) currentPoint.clone();
                    Point p2 = (Point) currentPoint.clone();
                    Point p3 = (Point) currentPoint.clone();
                    Point p4 = (Point) currentPoint.clone();

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
