package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

abstract public class ModifyPlayField {
    private static TetrisPiece falling;
    private static ArrayList<ArrayList<Point>> sinkingPieces;

    static void setStaticVariables(TetrisPiece falling, ArrayList<ArrayList<Point>> sinkingPieces) {
        ModifyPlayField.falling = falling;
        ModifyPlayField.sinkingPieces = sinkingPieces;
    }

    abstract public static class AddAndRemove {
        static void apply(Collection<Point> piece, Consumer<Point> consumer) {
            for (Point t : piece) {
                if (Test.Position.pointIsValid(t)) {
                    consumer.accept(t);
                }
            }
        }

        static void apply(Point[] piece, Consumer<Point> consumer) {
            for (Point t : piece) {
                if (Test.Position.pointIsValid(t)) {
                    consumer.accept(t);
                }
            }
        }

        public static void addFallingPiece() {
            apply(falling.getPieceLocation(), PlayField::fillCell);
        }

        public static void removeFallingPiece() {
            for (Point pt : falling.getPieceLocation()) {
                if (Test.Position.pointIsInBounds(pt)) {
                    PlayField.emptyCell(pt);
                }
            }
        }

        public static void addAllSinkingPieces() {
            for (ArrayList<Point> piece : sinkingPieces) {
                apply(piece, PlayField::fillCell);
            }
        }

        public static void removeSinkingPieces() {
            for (ArrayList<Point> piece : sinkingPieces) {
                for (Point pt : piece) {
                    if (Test.Position.pointIsInBounds(pt)) {
                        PlayField.emptyCell(pt);
                    }
                }
            }
        }

        static void addSinkingPiece(ArrayList<Point> piece) {
            apply(piece, PlayField::fillCell);
        }
    }

    abstract static class RowDeleter {
        RowDeleter() {
        }

        static int apply(ArrayList<Point> testRows) {
            return apply(testRows.toArray(new Point[0]));
        }

        static int apply(Point[] testRows) {
            int startAt = -1;

            for (Point c : testRows) {
                if (PlayField.rowIsFull((int) c.getY()) && c.getY() > startAt) {
                    startAt = (int) c.getY();
                }
            }

            int sinkToRow = startAt + 1;

            if (startAt > -1) {
                int shift = 0;

                while (!PlayField.rowIsEmpty(startAt) && (startAt + shift) >= 0) {
                    while (PlayField.rowIsFull(startAt + shift)) {
                        shift--;
                    }

                    PlayField.copyRow(startAt + shift, startAt);

                    startAt--;
                }

                for (int i = startAt, halt = startAt + shift; i >= 0 && i > halt; i--) {
                    PlayField.emptyRow(i);
                }
            }

            return sinkToRow;
        }
    }
}

