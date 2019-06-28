package tetrisrevision;

import java.awt.*;
import java.util.Arrays;

public class PieceLocationValidator {
    private static int width;
    private static int height;
    private static PlayField p;
    private static Point[][] sinking;
    private static TetrisPiece falling;
    private static TetrisPiece[] q;

    PieceLocationValidator (int w, int h, PlayField p, Point[][] s, TetrisPiece falling, TetrisPiece[] q) {
        this.width = w;
        this.height = h;
        this.p = p;
        this.sinking = s;
        this.falling = falling;
        this.q = q;
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
        return Arrays.stream(falling.getPieceLocation()).allMatch(p -> pointIsValid(p));
    }

    public boolean pointIsValid(Point p) {
        return xInBounds(p) &&
                yInBounds(p) &&
                noOverlap(p);
    }

    public boolean pointsAreValid(Point[] ps) {
        return Arrays.stream(ps).allMatch(p -> pointIsValid(p));
    }

    public boolean coordinatesAreValid(int x, int y) {
        return pointIsValid(new Point(x, y));
    }

    public boolean coordinatesAreInvalid(int x, int y) {
        return !pointIsValid(new Point(x, y));
    }
}
