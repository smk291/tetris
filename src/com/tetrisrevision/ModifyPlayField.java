package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
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
            piece.stream().filter(Test.Position::pointIsValid).forEach(consumer);
        }

        static void apply(Point[] piece, Consumer<Point> consumer) {
            Arrays.stream(piece).filter(Test.Position::pointIsValid).forEach(consumer);
        }

        public static void addFallingPiece() {
            apply(falling.getPieceLocation(), PlayField::fillCell);
        }

        public static void removeFallingPiece() {
            Arrays.stream(falling.getPieceLocation()).filter(Test.Position::pointIsInBounds).forEach(PlayField::emptyCell);
        }

        public static void addAllSinkingPieces() {
            sinkingPieces.forEach(piece -> apply(piece, PlayField::fillCell));
        }

        public static void removeSinkingPieces() {
            sinkingPieces.forEach(piece -> piece.stream().filter(Test.Position::pointIsInBounds).forEach(PlayField::emptyCell));
        }

        static void addSinkingPiece(ArrayList<Point> piece) {
            apply(piece, PlayField::fillCell);
        }
    }

    abstract static class RowDeleter {
        static int apply(ArrayList<Point> testRows) {
            return apply(testRows.toArray(new Point[0]));
        }

        static int apply(Point[] testRows) {
            int startAt = -1;

            for (Point c : testRows) {
                int y = (int) c.getY();

                if (PlayField.rowIsFull(y) && y > startAt) {
                    startAt = y;
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
