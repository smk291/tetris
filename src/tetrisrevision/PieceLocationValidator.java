package tetrisrevision;

import tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PieceLocationValidator {
    private static int width;
    private static int height;
    private static PlayField p;
    private static ArrayList<ArrayList<Point>> sinking;
    private static TetrisPiece falling;
    private static Tetromino[] q;

    public static void setStaticVariables(int w, int h, PlayField p, ArrayList<ArrayList<Point>> s, TetrisPiece falling, Tetromino[] q) {
        width = w;
        height = h;
        PieceLocationValidator.p = p;
        sinking = s;
        PieceLocationValidator.falling = falling;
        PieceLocationValidator.q = q;
    }

    private boolean xInBounds(Point p) {
        return p.getX() > -1 && p.getX() < width;
    }

//    private boolean xInBoundsArray(Point[] ps) {
//        return Arrays.stream(ps).allMatch(p -> xInBounds(p));
//    }

    private boolean yInBounds(Point p) {
        return p.getY() > -2 && p.getY() < height;
    }

//    private boolean yInBoundsArray(Point[] ps) {
//        return Arrays.stream(ps).allMatch(p -> yInBounds(p));
//    }

    private boolean noOverlap(Point pt) {
        return p.cellIsEmpty(pt);
    }

//    private boolean noOverlapArray(Point[] pts) {
//        return Arrays.stream(pts).allMatch(pt -> p.cellIsEmpty(pt));
//    }

    public boolean positionIsValid() {
        return Arrays.stream(falling.getPieceLocation()).allMatch(this::pointIsValid);
    }

    public boolean pointIsValid(Point p) {
        return xInBounds(p) &&
                yInBounds(p) &&
                noOverlap(p);
    }

    public boolean pointsAreValid(Point[] ps) {
        return Arrays.stream(ps).allMatch(this::pointIsValid);
    }

    public boolean coordinatesAreValid(int x, int y) {
        return pointIsValid(new Point(x, y));
    }

    public boolean coordinatesAreInvalid(int x, int y) {
        return !pointIsValid(new Point(x, y));
    }
}
