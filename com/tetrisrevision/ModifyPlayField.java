package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

abstract public class ModifyPlayField {
    private static PlayField p;
    private static TetrisPiece falling;
    private static ArrayList<ArrayList<Point>> sinkingPieces;

    public static void setStaticVariables(PlayField p, TetrisPiece falling, ArrayList<ArrayList<Point>> sinkingPieces) {
        ModifyPlayField.p = p;
        ModifyPlayField.falling = falling;
        ModifyPlayField.sinkingPieces = sinkingPieces;
    }

    abstract public static class AddAndRemove {
        public static void apply(Collection<Point> piece, Consumer<Point> consumer) {
            for (Point t : piece) {
                if (Test.Position.pointIsValid(t)) {
                    consumer.accept(t);
                }
            }
        }

        public static void apply(Point[] piece, Consumer<Point> consumer) {
            for (Point t : piece) {
                if (Test.Position.pointIsValid(t)) {
                    consumer.accept(t);
                }
            }
        }

//        public static void remove(Point[] piece) {
//            for (Point t : piece) {
//                if (Test.Position.pointIsValid(t)) {
//                    p.fillCell(t);
//                }
//            }
//        }

        public static void addFallingPiece() {
            apply(falling.getPieceLocation(), i -> p.fillCell(i));
        }

        public static void removeFallingPiece() {
            for (Point pt : falling.getPieceLocation()) {
                if (Test.Position.pointIsInBounds(pt)) {
                    p.emptyCell(pt);
                }
            }
        }

//        public static void addSinkingPieces(Collection<Integer> piecesIdx) {
//            for (Integer idx : piecesIdx) {
//                apply(sinkingPieces.get(idx), i -> p.fillCell(i));
//            }
//
//            piecesIdx.forEach(idx -> sinkingPieces.remove((int) idx));
//        }

        public static void addAllSinkingPieces() {
            for (ArrayList<Point> piece : sinkingPieces) {
                apply(piece, i -> p.fillCell(i));
            }
        }

        public static void removeSinkingPieces() {
            for (ArrayList<Point> piece : sinkingPieces) {
                for (Point pt : piece) {
                    if (Test.Position.pointIsInBounds(pt)) {
                        p.emptyCell(pt);
                    }
                }
            }
        }

        public static void addSinkingPiece(ArrayList<Point> piece) {
            apply(piece, i -> p.fillCell(i));
        }
    }

    abstract public static class RowDeleter {
        RowDeleter() {
        }

        public static int apply(Point[] testRows) {
            int startAt = -1;

            for (int i = 0, length = testRows.length; i < length; i++) {
                Point c = testRows[i];

                if (p.rowIsFull((int) c.getY()) && c.getY() > startAt) {
                    startAt = (int) c.getY();
                }
            }

            int sinkToRow = startAt + 1;

            if (startAt > -1) {
                int shift = 0;

                while (!p.rowIsEmpty(startAt) && (startAt + shift) >= 0) {
                    while (p.rowIsFull(startAt + shift)) {
                        shift--;
                    }

                    p.copyRow(startAt + shift, startAt);

                    startAt--;
                }

                for (int i = startAt, halt = startAt + shift; i >= 0 && i > halt; i--) {
                    p.emptyRow(i);
                }
            }

            return sinkToRow;
        }
    }
}

