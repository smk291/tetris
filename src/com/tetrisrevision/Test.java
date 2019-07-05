package com.tetrisrevision;

import java.awt.*;
import java.util.Arrays;

abstract class Test {
    private static TetrisPiece falling;

    static void setStaticVariables(TetrisPiece falling){
        Test.falling = falling;
    }

    private static class Bounds {
        private static boolean xInBounds(Point pt) {
            return pt.getX() > -1 && pt.getX() < PlayField.getWidth();
        }

        private static boolean yInBoundsNoMin(Point pt) {
            return pt.getY() >= -2 && pt.getY() < PlayField.getHeight();
        }

        private static boolean yInBounds(Point pt) {
            return pt.getY() >= 0 && pt.getY() < PlayField.getHeight();
        }
    }

    private static class Overlap {
        private static boolean noOverlap(Point pt) {
            return !Bounds.xInBounds(pt) ||
                    !Bounds.yInBounds(pt) ||
                    PlayField.cellIsEmpty(pt);
        }
    }

    static class Position {
        static boolean isInBoundsAndEmpty() {
            return Arrays.stream(falling.getPieceLocation()).allMatch(Position::canBeFilled);
        }

        static boolean isInBoundsAndEmptyNoRowMin() {
            return Arrays.stream(falling.getPieceLocation()).allMatch(Position::pointIsValidNoMin);
        }

        static boolean canBeFilled(Point p) {
            return Bounds.xInBounds(p) &&
                    Bounds.yInBounds(p) &&
                    Overlap.noOverlap(p);
        }

        static boolean isInBounds(Point p) {
            return Bounds.xInBounds(p) &&
                    Bounds.yInBounds(p);
        }

        static boolean isInBounds(int x, int y) {
            Point pt = new Point(x, y);

            return isInBounds(pt);
        }

        static boolean pointIsValidNoMin(Point p) {
            return Bounds.xInBounds(p) &&
                    Bounds.yInBoundsNoMin(p) &&
                    Overlap.noOverlap(p);
        }
    }
}
