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
            return (
                    (!Test.Bounds.xInBounds(pt) || !Test.Bounds.yInBounds(pt)) ||
                            PlayField.cellIsEmpty(pt)
            );
        }
    }

    static class Position {
        static boolean fallingPositionIsValid() {
            return Arrays.stream(falling.getPieceLocation()).allMatch(Position::pointIsValid);
        }

        static boolean fallingPositionIsValidNoMin() {
            return Arrays.stream(falling.getPieceLocation()).allMatch(Position::pointIsValidNoMin);
        }

        static boolean pointIsValid(Point p) {
            return Bounds.xInBounds(p) &&
                    Bounds.yInBounds(p) &&
                    Overlap.noOverlap(p);
        }

        static boolean pointIsInBounds(Point p) {
            return Bounds.xInBounds(p) &&
                    Bounds.yInBounds(p);
        }

        static boolean pointIsInBounds(int x, int y) {
            Point pt = new Point(x, y);

            return pointIsInBounds(pt);
        }

        static boolean pointIsValidNoMin(Point p) {
            return Bounds.xInBounds(p) &&
                    Bounds.yInBoundsNoMin(p) &&
                    Overlap.noOverlap(p);
        }
    }
}
