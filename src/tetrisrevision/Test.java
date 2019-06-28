package tetrisrevision;

import sun.plugin2.os.windows.OVERLAPPED;
import tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

abstract public class Test {
    private static PlayField p;
    private static ArrayList<ArrayList<Point>> sinking;
    private static TetrisPiece falling;
    private static Tetromino[] q;

    private Test() {}

    public static class Bounds {
        private static boolean xInBounds(Point pt) {
            return pt.getX() > -1 && pt.getX() < PlayField.getWidth();
        }

        // private boolean xInBoundsArray(Point[] ps) {
        // return Arrays.stream(ps).allMatch(p -> xInBounds(p));
        // }

        private static boolean yInBoundsNoMin(Point pt) {
            return pt.getY() >= -2 && pt.getY() < PlayField.getHeight();
        }

        private static boolean yInBounds(Point pt) {
            return pt.getY() >= 0 && pt.getY() < PlayField.getHeight();
        }

        // private boolean yInBoundsArray(Point[] ps) {
        // return Arrays.stream(ps).allMatch(p -> yInBounds(p));
        // }
    }

    public static class Overlap {
        private static boolean noOverlap(Point pt) {
            return (
                    Test.Bounds.xInBounds(pt) &&
                            Test.Bounds.yInBounds(pt) &&
                            p.cellIsEmpty(pt)
            );
        }

        // private boolean noOverlapArray(Point[] pts) {
        // return Arrays.stream(pts).allMatch(pt -> p.cellIsEmpty(pt));
        // }
    }

    public static void setStaticVariables(PlayField p, ArrayList<ArrayList<Point>> s, TetrisPiece falling, Tetromino[] q){
        Test.p = p;
        Test.sinking = s;
        Test.falling = falling;
        Test.q = q;
    }

    public static class Position {
        public static boolean fallingPositionIsValid() {
            return Arrays.stream(falling.getPieceLocation()).allMatch(Position::pointIsValid);
        }

        public static boolean fallingPositionIsValidNoMin () {
            Arrays.stream(falling.getPieceLocation()).forEach(pt -> {
                System.out.println("Test.Position.fallingPositionIsValidNoMin.xInBounds(" + pt.getX() + ") is " + Test.Bounds.xInBounds(pt));
                System.out.println("Test.Position.fallingPositionIsValidNoMin.yInBoundsNoMin(" + pt.getY() + ") is " + Test.Bounds.yInBoundsNoMin(pt));
                System.out.println("Test.Position.fallingPositionIsValidNoMin.NoOverlap(" + pt.getX() + ", " + pt.getY() + ") is " + Test.Overlap.noOverlap(pt));
            });

            return Arrays.stream(falling.getPieceLocation()).allMatch(Position::pointIsValidNoMin);
        }

        public static boolean pointIsValid(Point p) {
            return Bounds.xInBounds(p) &&
                    Bounds.yInBounds(p) &&
                    Overlap.noOverlap(p);
        }

        public static boolean pointIsValidNoMin(Point p) {
            return Bounds.xInBounds(p) &&
                    Bounds.yInBoundsNoMin(p) &&
                    Overlap.noOverlap(p);
        }

//        public static boolean pointsAreValid(Point[] ps) {
//            return Arrays.stream(ps).allMatch(Position::pointIsValid);
//        }
    }
}
