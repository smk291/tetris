package tetrisrevision;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
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

        public static void remove(Point[] piece) {
            for (Point t : piece) {
                if (Test.Position.pointIsValid(t)) {
                    p.fillCell(t);
                }
            }
        }

        public static void addFallingPiece() {
            apply(falling.getPieceLocation(), i -> p.fillCell(i));
        }

        public static void removeFallingPiece() {
            apply(falling.getPieceLocation(), i -> p.emptyCell(i));
        }

        public static void addSinkingPieces(Collection<Integer> piecesIdx) {
            for (Integer idx : piecesIdx) {
                apply(sinkingPieces.get(idx), i -> p.fillCell(i));
            }

            piecesIdx.forEach(idx -> sinkingPieces.remove((int) idx));
        }

        public static void addAllSinkingPieces() {
            for (ArrayList<Point> piece : sinkingPieces) {
                apply(piece, i -> p.fillCell(i));
            }
        }

        public static void removeSinkingPieces() {
            for (ArrayList<Point> piece : sinkingPieces) {
                apply(piece, i -> p.emptyCell(i));
            }
        }

        public static void addSinkingPiece(ArrayList<Point> piece) {
            apply(piece, i -> p.fillCell(i));
        }
    }

    abstract public static class RowDeleter {
        RowDeleter() {}

        public static int apply(Point[] testRows) {
            int startAt = -1;

            Integer[] fullRows = Arrays.stream(testRows).filter(pt -> p.rowIsFull((int) pt.getY())).map(pt -> (int) pt.getY()).toArray(Integer[]::new);

            for (int row : fullRows) {
                if (row > startAt) {
                    startAt = row;
                }
            }

            int sinkToRow = startAt + 1;

            if (startAt > -1) {
                int shift = 0;

                while (!p.rowIsEmpty(startAt) && (startAt + shift) >= 0) {
                    while (p.rowIsFull(startAt + shift)) {
                        shift--;
                    }

                    p.copyRow(startAt, startAt + shift);
                }

                for (int i = startAt, halt = startAt + shift; i >= 0 && i > halt; i--) {
                    p.emptyRow(i);
                }
            }

            return sinkToRow;
        }
    }
}
